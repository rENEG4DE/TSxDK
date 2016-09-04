package com.tsxbot.tsxdk.client.entity.gateway;

import com.tsxbot.tsxdk.client.entity.EntityGateway;
import com.tsxbot.tsxdk.query.QueryGateway;
import com.tsxbot.tsxdk.query.model.Query;
import com.tsxbot.tsxdk.query.model.QueryResponse;
import com.tsxbot.tsxdk.query.model.wrapper.ErrorResponse;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/*
 *  TSxBot2
 *  Coded by rENEG4DE 
 *  on 06. of Jun
 *  2016
 *  08:57
 */
public class ClientGateway extends EntityGateway {
    private final Future<Query.ResponseContainer> futureQueryResponse;
    public static final Integer[] EMPTY_INTEGER_ARRAY = {};

    public ClientGateway(QueryGateway gateway) {
        super(gateway, ClientGateway.class);
        final Query clientList = queryFactory.clientlist();
        futureQueryResponse = queryChannel.deployGetFuture(clientList);
    }

    public Integer[] getClientIds() throws ExecutionException, InterruptedException {
        final Optional<QueryResponse> optionalResultSet = getChannelSet();

        if (optionalResultSet.isPresent()) {
            return collectAll("clid", optionalResultSet.get(), Integer::parseInt, Integer[]::new);
        } else {
            return EMPTY_INTEGER_ARRAY;
        }
    }

    private Optional<Query.ResponseContainer> claimResponse() {
        try {
            return Optional.of(futureQueryResponse.get());
        } catch (InterruptedException e) {
            log.error("Claiming query-response got interrupted", e);
        } catch (ExecutionException e) {
            log.error("Execution of Future::get caused an exception", e);
        }

        return Optional.empty();
    }

    public ErrorResponse getError() {
        return claimResponse().get().getError();
    }

    private Optional<QueryResponse> getChannelSet() {
        final Optional<Query.ResponseContainer> response = claimResponse();
        return response.isPresent() ?
                response.get().getResultSet() :
                Optional.empty();
    }

}
