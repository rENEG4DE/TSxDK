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
public class ChannelGateway extends EntityGateway {

    public ChannelGateway(QueryGateway gateway) {
        super(gateway, ChannelGateway.class);
        dataQuery = queryFactory.channellist();
        futureQueryResponse = queryChannel.deployGetFuture(dataQuery);
    }

    private Optional<QueryResponse> getChannelSet() {
        final Optional<Query.ResponseContainer> response = claimResponse();
        return response.isPresent() ?
                response.get().getResultSet() :
                Optional.empty();
    }

    public Integer[] getChannelIds() throws ExecutionException, InterruptedException {
        final Optional<QueryResponse> optionalResultSet = getChannelSet();

        if (optionalResultSet.isPresent()) {
            return collectAll("cid", optionalResultSet.get(), Integer::parseInt, Integer[]::new);
        } else {
            return EMPTY_INTEGER_ARRAY;
        }
    }

}
