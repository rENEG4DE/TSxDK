package com.tsxbot.tsxdk.client.entity;

import com.tsxbot.tsxdk.base.Gateway;
import com.tsxbot.tsxdk.query.QueryGateway;
import com.tsxbot.tsxdk.query.model.Query;
import com.tsxbot.tsxdk.query.model.QueryResponse;
import com.tsxbot.tsxdk.query.model.QueryResultSet;
import com.tsxbot.tsxdk.query.model.wrapper.ErrorResponse;
import com.tsxbot.tsxdk.query.model.wrapper.MultiEntityResponse;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.IntFunction;

/*
 *  TSxBot2
 *  Coded by rENEG4DE 
 *  on 06. of Jun
 *  2016
 *  09:32
 */
public class EntityGateway extends Gateway {
    protected final Integer[] EMPTY_INTEGER_ARRAY = {};

    protected Query dataQuery;
    protected Future<Query.ResponseContainer> futureQueryResponse;

    protected EntityGateway(QueryGateway gateway, Class<?> clazz) {
        super(clazz, gateway.getQueryChannel(), gateway.getQueryFactory());
    }

    protected void update () {
        update(dataQuery);
        try {
            futureQueryResponse.get();
        } catch (InterruptedException e) {
            log.error("Future was interrupted", e);
        } catch (ExecutionException e) {
            log.error("Exception while Future-execution", e);
        }
    }

    protected void update (Query query) {
        dataQuery = query;
        futureQueryResponse = queryChannel.deployGetFuture(dataQuery);
    }

    protected <T> T[] collectAll(String field,
                                 QueryResponse resultSet,
                                 Function<String, T> convert,
                                 IntFunction<T[]> dest) {
        return MultiEntityResponse.create((QueryResultSet) resultSet)
                .stream().map(Map.Entry::getValue)
//                .map(entry -> convert.apply(entry.get(field)))
                .map((__) -> __.get(field))
                .map(convert)
                .toArray(dest);
    }

    protected ErrorResponse getError() {
        final Optional<Query.ResponseContainer> optResponse = claimResponse();

        if (optResponse.isPresent()) {
            return optResponse.get().getError();
        }

        log.error("Could not retrieve response to query {}", dataQuery.getQueryString());

        throw new IllegalStateException("Query was not responded correctly");
    }

    protected Optional<Query.ResponseContainer> claimResponse() {
        try {
//            final Query.ResponseContainer value = futureQueryResponse.get();
//            log.debug("Future-Implementation:{}", futureQueryResponse.getClass());
            return Optional.of(futureQueryResponse.get());
        } catch (InterruptedException e) {
            log.error("Claiming query-response got interrupted", e);
        } catch (ExecutionException e) {
            log.error("Error while computing future-result", e);
        } catch (CancellationException e) {
            log.error("Retrieving future-result was cancelled");
        }

        return Optional.empty();
    }
}
