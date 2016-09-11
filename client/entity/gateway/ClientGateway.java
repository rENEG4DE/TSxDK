package com.tsxbot.tsxdk.client.entity.gateway;

import com.tsxbot.tsxdk.client.entity.EntityGateway;
import com.tsxbot.tsxdk.query.QueryGateway;
import com.tsxbot.tsxdk.query.model.Query;
import com.tsxbot.tsxdk.query.model.QueryResponse;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

/*
 *  TSxBot2
 *  Coded by rENEG4DE 
 *  on 06. of Jun
 *  2016
 *  08:57
 */
public class ClientGateway extends EntityGateway {

    public ClientGateway(QueryGateway gateway) {
        super(gateway, ClientGateway.class);
        dataQuery = queryFactory.clientlist();
        futureQueryResponse = queryChannel.deployGetFuture(dataQuery);
    }

    private Optional<QueryResponse> getClientSet() {
        final Optional<Query.ResponseContainer> response = claimResponse();
        return response.isPresent() ?
                response.get().getResultSet() :
                Optional.empty();
    }

    public Integer[] getClientIds() throws ExecutionException, InterruptedException {
        final Optional<QueryResponse> optionalResultSet = getClientSet();

        if (optionalResultSet.isPresent()) {
            return collectAll("clid", optionalResultSet.get(), Integer::parseInt, Integer[]::new);
        } else {
            return EMPTY_INTEGER_ARRAY;
        }
    }

}
