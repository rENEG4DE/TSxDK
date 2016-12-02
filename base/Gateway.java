package com.tsxbot.tsxdk.base;

import com.tsxbot.tsxdk.query.QueryChannel;
import com.tsxbot.tsxdk.query.engine.QueryFactory;

/**
 * TSxBot2
 * Coded by Lenovo
 * on ${}
 */
/*
 * TSxBot2
 * Coded by rENEG4DE
 * on 29. of Mai
 * 2016
 * 09:21
 */
public class Gateway extends TSX{
    protected final QueryChannel queryChannel;
    protected final QueryFactory queryFactory;

    public Gateway(Class<?> clazz,
                   QueryChannel queryChannel,
                   QueryFactory queryFactory) {
        super(SystemDescriptors.GATEWAY, clazz);
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
