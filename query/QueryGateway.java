package com.tsxbot.tsxdk.query;

import com.google.inject.Inject;
import com.tsxbot.tsxdk.query.QueryChannel;
import com.tsxbot.tsxdk.query.engine.QueryFactory;

public class QueryGateway {
    private final QueryChannel queryChannel;
    private final QueryFactory queryFactory;

    @Inject
    public QueryGateway(QueryChannel queryChannel, QueryFactory queryFactory) {
        this.queryChannel = queryChannel;
        this.queryFactory = queryFactory;
    }

    public QueryChannel getQueryChannel() {
        return queryChannel;
    }

    public QueryFactory getQueryFactory() {
        return queryFactory;
    }
}
