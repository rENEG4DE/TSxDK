package com.tsxbot.tsxdk.modules;

import com.google.inject.Singleton;
import com.tsxbot.tsxdk.query.QueryGateway;
import com.tsxbot.tsxdk.utility.Configuration;
import com.tsxbot.tsxdk.io.IO;
import com.tsxbot.tsxdk.io.IOImpl;
import com.tsxbot.tsxdk.io.SocketConnection;
import com.tsxbot.tsxdk.io.SocketConnectionImpl;
import com.tsxbot.tsxdk.model.TSServerConnectionModel;
import com.tsxbot.tsxdk.query.*;
import com.tsxbot.tsxdk.query.engine.QueryEngine;
import com.tsxbot.tsxdk.query.engine.QueryEngineImpl;
import com.tsxbot.tsxdk.query.engine.QueryFactory;
import com.tsxbot.tsxdk.query.engine.QueryFactoryImpl;

/*
For learning-purposes, all bindings go here (to begin with)
 */
public class GuiceTSxDKBindings extends com.google.inject.AbstractModule {
    protected void configure() {
        bind(IO.class).to(IOImpl.class);
        bind(SocketConnection.class).to(SocketConnectionImpl.class);
        bind(QueryEngine.class).to(QueryEngineImpl.class);
        bind(TSServerConnectionModel.class).toProvider(Configuration.class).in(Singleton.class);
        bind(QueryChannel.class).to(QueryChannelImpl.class);
        bind(QueryFactory.class).to(QueryFactoryImpl.class);
        bind(QueryGateway.class);
    }
}
