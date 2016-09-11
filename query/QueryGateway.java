package com.tsxbot.tsxdk.query;

import com.tsxbot.tsxdk.base.Gateway;
import com.tsxbot.tsxdk.query.engine.QueryFactory;

/**
 * Created by Lenovo on 11.09.2016.
 */
public abstract class QueryGateway extends Gateway{
    public QueryGateway(Class<?> clazz, QueryChannel queryChannel, QueryFactory queryFactory) {
        super(clazz, queryChannel, queryFactory);
    }

    public abstract void login();
    public abstract void use(int sid);
    public abstract void shutdown();
}
