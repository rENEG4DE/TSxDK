package com.tsxbot.tsxdk.modules;

import com.google.inject.Singleton;
import common.utility.Configuration;
import com.tsxbot.tsxdk.io.IO;
import com.tsxbot.tsxdk.io.IOImpl;
import com.tsxbot.tsxdk.io.SocketConnection;
import com.tsxbot.tsxdk.io.SocketConnectionImpl;
import com.tsxbot.tsxdk.model.TSServerConnectionModel;
import com.tsxbot.tsxdk.query.QueryChannel;
import com.tsxbot.tsxdk.query.QueryChannelImpl;
import com.tsxbot.tsxdk.query.QueryEngine;
import com.tsxbot.tsxdk.query.QueryEngineImpl;

/*
For learning-purposes, all bindings go here (to begin with)
 */
public class GuiceBindings extends com.google.inject.AbstractModule {
    protected void configure() {
        bind(IO.class).to(IOImpl.class);
        bind(SocketConnection.class).to(SocketConnectionImpl.class);
        bind(QueryEngine.class).to(QueryEngineImpl.class);
        bind(TSServerConnectionModel.class).toProvider(Configuration.class).in(Singleton.class);
        bind(QueryChannel.class).to(QueryChannelImpl.class);
    }
}
