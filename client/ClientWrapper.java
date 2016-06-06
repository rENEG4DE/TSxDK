package com.tsxbot.tsxdk.client;

import com.tsxbot.tsxdk.query.QueryChannel;
import com.tsxbot.tsxdk.query.QueryGateway;
import com.tsxbot.tsxdk.query.engine.QueryFactory;

/*
 *  TSxBot2
 *  Coded by rENEG4DE 
 *  on 06. of Jun
 *  2016
 *  09:32
 */
public class ClientWrapper {
    protected final QueryFactory queryFactory;
    protected final QueryChannel queryChannel;

    public ClientWrapper(QueryGateway gateway) {
        this.queryChannel = gateway.getQueryChannel();
        this.queryFactory = gateway.getQueryFactory();
    }
}
