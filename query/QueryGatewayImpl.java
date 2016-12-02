package com.tsxbot.tsxdk.query;

import com.google.inject.Inject;
import com.tsxbot.tsxdk.query.engine.QueryFactory;

public class QueryGatewayImpl extends QueryGateway {

    @Inject
    public QueryGatewayImpl(QueryChannel queryChannel, QueryFactory queryFactory) {
        super(QueryGateway.class, queryChannel, queryFactory);
    }

    @Override
    public void login() {
        queryChannel.deployAndBlock(queryFactory.login(cfg.TSSERVER_LOGIN, cfg.TSSERVER_PASSWORD), 10);
    }

    @Override
    public void use(int sid) {
        queryChannel.deployAndBlock(queryFactory.use(sid), 20);
    }

    @Override
    public void shutdown() {
        queryChannel.deployAndSync(queryFactory.logout());
        queryChannel.shutdown();
    }
}
