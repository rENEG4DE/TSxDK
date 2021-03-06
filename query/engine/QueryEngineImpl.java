package com.tsxbot.tsxdk.query.engine;

import com.google.inject.Inject;
import com.tsxbot.tsxdk.base.SystemDescriptors;
import com.tsxbot.tsxdk.base.TSX;
import com.tsxbot.tsxdk.io.IO;
import com.tsxbot.tsxdk.query.model.Query;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 27. of Mai
 * 2016
 * 10:22
 */
public class QueryEngineImpl extends TSX implements QueryEngine {
    private final IO tsIO;
    private final ScheduledThreadPoolExecutor executor;
    private final QueryResponseHandler responseHandler;
    private final ConcurrentLinkedQueue<Query> deployedQueries = new ConcurrentLinkedQueue<>();

    private final int deployDelay = 1000 / cfg.QUERY_PERSEC;
    private long lastDeploy;
    private long nextOut;

    @Inject
    public QueryEngineImpl(IO tsIO) {
        super(SystemDescriptors.QUERY, QueryEngine.class);
        log.info("Starting");
        this.tsIO = tsIO;
        this.responseHandler = QueryResponseHandler.create(this);
        discardTSBootMessages();
        executor = new ScheduledThreadPoolExecutor(1);
        final int rcvPeriod = cfg.QUERY_RECVPERIOD;
        executor.scheduleAtFixedRate(responseHandler, 0L, rcvPeriod, TimeUnit.MILLISECONDS);
        log.debug("Response-handler started with a period of {} ms", rcvPeriod);
        log.info("Running");
    }

    private void discardTSBootMessages() {
        log.debug("Discarding startup messages");
        String cur;
        do {
            cur = tsIO.in().orElse("");
            try {
                TimeUnit.MILLISECONDS.sleep(cfg.QUERY_RECVPERIOD);
            } catch (InterruptedException e) {
                log.error("discardTSBootMessages-sleep was interrupted", e);
            }
            log.debug("Discarded ({})", cur);
        } while (!cur.startsWith("Welcome"));
    }

    @Override
    public synchronized void deploy(Query query) {
        restrainQueryLoad();
        registerDeployedQuery(query);
        tsIO.out(query.getQueryString());
    }

    private void restrainQueryLoad() {
        long currentTime = System.currentTimeMillis();

        //Handle stallOut
        if (nextOut > currentTime) {
            try {
                TimeUnit.MILLISECONDS.sleep(nextOut - currentTime);
            } catch (InterruptedException e) {
                log.error("Something aweful happened", e);
            }
            currentTime = nextOut;
            nextOut = 0L;
        }

        //Handle cfg.QUERY_PERSEC
        final long difference = currentTime - lastDeploy;
        final long suspend = deployDelay - difference;

        if (suspend > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(suspend);
            } catch (InterruptedException e) {
                log.error("restrainQueryLoad-sleep was interrupted", e);
            }
        }
    }

    private void registerDeployedQuery(Query query) {
        deployedQueries.add(query);
        query.setDeployed();
        this.lastDeploy = query.getDeployStamp();
    }

    @Override
    public IO getIO() {
        return tsIO;
    }

    @Override
    public Query getCurrentQuery() {
        return deployedQueries.peek();
    }

    @Override
    public void notifyQuerySatisfied() {
        deployedQueries.remove();
    }

    @Override
    public long getTimeSinceLastDeploy() {
        return System.currentTimeMillis() - lastDeploy;
    }

    @Override
    public void shutdown() {
        log.info("Shutting down");
        executor.shutdown();
        tsIO.shutdown();
        log.info("Shutdown complete");
    }

    @Override
    public void stallOut(long milliseconds) {
        log.debug("Stalling query-output for {} ms", milliseconds);
        nextOut = System.currentTimeMillis() + milliseconds;
    }
}
