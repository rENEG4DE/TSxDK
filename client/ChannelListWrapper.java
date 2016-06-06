package com.tsxbot.tsxdk.client;

import com.tsxbot.tsxdk.query.QueryChannel;
import com.tsxbot.tsxdk.query.QueryGateway;
import com.tsxbot.tsxdk.query.engine.QueryFactory;
import com.tsxbot.tsxdk.query.model.Query;
import com.tsxbot.tsxdk.query.model.QueryResultSet;
import com.tsxbot.tsxdk.query.model.wrapper.MultiEntityResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/*
 *  TSxBot2
 *  Coded by rENEG4DE 
 *  on 06. of Jun
 *  2016
 *  08:57
 */
public class ChannelListWrapper extends ClientWrapper {

    public ChannelListWrapper(QueryGateway gateway) {
        super(gateway);
    }

    public Integer[] getChannelIds () throws ExecutionException, InterruptedException {
        final Query channelList = queryFactory.channellist();
        final Future<Query.ResponseContainer> responseContainer = queryChannel.deployGetFuture(channelList);

        return MultiEntityResponse.create(
                (QueryResultSet) responseContainer.get().getResultSet().get()
        ).stream().map(entry -> Integer.parseInt(entry.getValue().get("cid"))).toArray(Integer[]::new);
    }
}
