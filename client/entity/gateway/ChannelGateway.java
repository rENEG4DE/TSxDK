package com.tsxbot.tsxdk.client.entity.gateway;

import com.tsxbot.tsxdk.client.entity.EntityGateway;
import com.tsxbot.tsxdk.query.QueryGateway;
import com.tsxbot.tsxdk.query.model.Query;
import com.tsxbot.tsxdk.query.model.QueryResponse;
import com.tsxbot.tsxdk.query.model.wrapper.ErrorResponse;

import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/*
 *  TSxBot2
 *  Coded by rENEG4DE 
 *  on 06. of Jun
 *  2016
 *  08:57
 */
public class ChannelGateway extends EntityGateway {
    private final Future<Query.ResponseContainer> futureQueryResponse;
    public static final Integer[] EMPTY_INTEGER_ARRAY = {};

    public ChannelGateway(QueryGateway gateway) {
        super(gateway, ChannelGateway.class);
        final Query channelList = queryFactory.channellist();
        futureQueryResponse = queryChannel.deployGetFuture(channelList);
    }

    public ErrorResponse getError() {
        return claimResponse().get().getError();
    }

    private Optional<QueryResponse> getChannelSet() {
        return claimResponse().get().getResultSet();
    }

    public Integer[] getChannelIds() throws ExecutionException, InterruptedException {
        final Optional<QueryResponse> optionalResultSet = getChannelSet();

        if (optionalResultSet.isPresent()) {
            return collectAll("cid", optionalResultSet.get(), Integer::parseInt, Integer[]::new);
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
            log.error("Error while computing future-result", e);
        } catch (CancellationException e) {
            log.error("Retrieving future-result was cancelled");
        }

        return Optional.empty();
    }

}
