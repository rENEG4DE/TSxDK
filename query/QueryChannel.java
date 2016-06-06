package com.tsxbot.tsxdk.query;

import com.tsxbot.tsxdk.query.engine.QueryFactory;
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
    void deploy (Query query);
    void deployAndSync(Query query);
    void deployAndWait(Query query, long milliseconds);

    Future<Query.ResponseContainer> deployGetFuture(Query query);
    Optional<Query.ResponseContainer> expect (Long maxDelay);
    Optional<Query.ResponseContainer> expect ();
    void await();

    void shutdown();
}