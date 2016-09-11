package com.tsxbot.tsxdk.modules;

import com.tsxbot.tsxdk.io.IO;
import com.tsxbot.tsxdk.io.IOImpl;
import com.tsxbot.tsxdk.io.SocketConnection;
import com.tsxbot.tsxdk.io.SocketConnectionImpl;
import com.tsxbot.tsxdk.query.QueryChannel;
import com.tsxbot.tsxdk.query.QueryChannelImpl;
import com.tsxbot.tsxdk.query.QueryGateway;
import com.tsxbot.tsxdk.query.QueryGatewayImpl;
import com.tsxbot.tsxdk.query.engine.QueryEngine;
import com.tsxbot.tsxdk.query.engine.QueryEngineImpl;
import com.tsxbot.tsxdk.query.engine.QueryFactory;
import com.tsxbot.tsxdk.query.engine.QueryFactoryImpl;

public class TSxDKBindings extends com.google.inject.AbstractModule {
    protected void configure() {
        install(new TSxDKConfiguration());
        bind(IO.class).to(IOImpl.class);
        bind(SocketConnection.class).to(SocketConnectionImpl.class);
        bind(QueryEngine.class).to(QueryEngineImpl.class);
        bind(QueryChannel.class).to(QueryChannelImpl.class);
        bind(QueryFactory.class).to(QueryFactoryImpl.class);
        bind(QueryGateway.class).to(QueryGatewayImpl.class);
    }
}
