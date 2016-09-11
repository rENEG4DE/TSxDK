package com.tsxbot.tsxdk.query;

import com.tsxbot.tsxdk.query.model.Query;

import java.util.Optional;
import java.util.concurrent.Future;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 25. of Mai
 * 2016
 * 07:12
 */
public interface QueryChannel {
    /**
     * Deploys the given query to the TS-server
     * @param query The query to deploy
     */
    void deploy(Query query);

    /**
     * Deploys the given query to the TS-server and {@link #await() awaits} the response
     * @param query The query to deploy
     */
    void deployAndSync(Query query);

    /**
     * Deploys the given query and blocks query-output for a given time
     * <p>
     * Best used for fast-responded commands like <b>login</b> or <b>use</b>,<p>
     * since those return a result before they have done their work.
     * @param query The query to deploy
     * @param milliseconds The amount of <b>milliseconds</b> to wait
     */
    void deployAndBlock(Query query, long milliseconds);

    /**
     * Deploys the given query returning a Future
     * that wraps the response to be
     * @param query The query to deploy
     * @return Future &lt Query.ResponseContainer&gt that will hold the query-result
     */
    Future<Query.ResponseContainer> deployGetFuture(Query query);

    /**
     * Waits for the response to the current query
     * <p>
     * Best used for ResultSet-returning commands like clientlist,
     * <b>not suitable for fast-responded commands like login or use</b>
     * @return An Optional<Query.ResponseContainer> that, soon as answered,
     * <p> contains the result to the deployed query
     */
    Optional<Query.ResponseContainer> expect();

    /**
     * Waits for the response to the current query
     * <p>
     * Best used for ResultSet-returning commands like clientlist,
     * <b>Note that commands like "login" or "use" currently (TS3-Server v3.0.12.3)
     * return immediately, not after their work is done</b>
     */
    void await();

    /**
     * Shuts down the connected query-engine
     */
    void shutdown();
}