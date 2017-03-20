package com.tsxbot.tsxdk.query;

import com.google.common.base.Stopwatch;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.tsxbot.tsxdk.base.SystemDescriptors;
import com.tsxbot.tsxdk.base.TSX;
import com.tsxbot.tsxdk.query.engine.QueryEngine;
import com.tsxbot.tsxdk.query.model.Query;

import java.util.Optional;
import java.util.concurrent.*;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 25. of Mai
 * 2016
 * 06:54
 */
public class QueryChannelImpl extends TSX implements QueryChannel {
    private final QueryEngine engine;

    private Query current;

    @Inject
    public QueryChannelImpl(QueryEngine engine) {
        super(SystemDescriptors.QUERY, QueryChannel.class);
        this.engine = engine;
    }

    @Override
    public void deploy(Query query) {
        this.current = query;
        engine.deploy(query);
    }

    @Override
    public void deployAndSync(Query query) {
//        deploy(query);
//        deployAndSync(query);
//        deployAndBlock(query, 10);
//        deployGetFuture(query);
//        expect(100l);
//        expect();
//        await();
//        shutdown();
        deploy(query);
        await();
    }

    @Override
    public void deployAndBlock(Query query, long milliseconds) {
        deploy(query);
        engine.stallOut(milliseconds);
    }

//    @Override
//    public Future<Query.ResponseContainer> deployGetFuture(Query query) {
//        final ExecutorService svc = Executors.newSingleThreadExecutor();
//        deploy(query);
//        return svc.submit(() -> {
////            svc.awaitTermination(cfg.QUERY_MAXIMUMRESPONSETIMEOUT, TimeUnit.MILLISECONDS);
//            expect(query);
//            return query.getResponse();
//        });
//    }

//    Fuck this Future-stuff for now, should be rethough thoroughly
    public CompletableFuture<Query.ResponseContainer> deployGetFuture(Query query) {
        deploy(query);
        return CompletableFuture.supplyAsync(() -> expect().orElse(null));
    }

    private Optional<Query.ResponseContainer> expect(Long microsecondDelay, Query query) {
        final Stopwatch watch = Stopwatch.createStarted();
        try {
            log.debug("Waiting for response of query {}", query.getQueryString());

            if (!query.latchAwait(microsecondDelay)) {
                log.debug("Response-timeout {}μs (query={})", watch.elapsed(TimeUnit.MICROSECONDS), query.getQueryString());
                return Optional.empty();
            } else {
                if (cfg.showProfiling()) {
                    log.debug("Got response after {}μs (query={})  ", watch.elapsed(TimeUnit.MICROSECONDS), query.getQueryString());
                }
                return Optional.of(query.getResponse());
            }
        } catch (InterruptedException e) {
            warnInterrupted();
            return Optional.empty();
        }
    }

    private Optional<Query.ResponseContainer> expect(Long microsecondDelay) {
        return expect(microsecondDelay, current);
    }

    private Optional<Query.ResponseContainer> expect(Query query) {
        return expect(cfg.QUERY_MAXIMUMRESPONSETIMEOUT, query);
    }

    @Override
    public Optional<Query.ResponseContainer> expect() {
        final Optional<Query.ResponseContainer> result = expect(cfg.QUERY_MAXIMUMRESPONSETIMEOUT);
        if (result.isPresent()) {
            return result;
        } else {
            warnInterrupted();
            return Optional.empty();
        }
    }

    @Override
    public void await() {
        if (!expect(cfg.QUERY_MAXIMUMRESPONSETIMEOUT).isPresent()) {
            warnInterrupted();
            //discard logic for unresponded query
        }
        EventBus bus = new EventBus();
    }

    @Override
    public void shutdown() {
        engine.shutdown();
    }

    private void warnInterrupted() {
        log.warn("Response to Query ({}) took too long - interrupted", current.getQueryString());
    }
}
