package com.tsxbot.tsxdk.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.tsxbot.tsxdk.model.TSServerConnectionModel;
import com.tsxbot.tsxdk.model.TSServerLogonModel;
import com.tsxbot.tsxdk.utility.Configuration;

/**
 * Created by Lenovo on 06.07.2016.
 */
public class TSxDKConfiguration extends AbstractModule {
    @Override
    protected void configure() {
    }

    private static final Configuration cfg = new Configuration();

    @Provides
    public Configuration getConfiguration() {
        return cfg;
    }

    @Provides
    public TSServerConnectionModel createTSServerConnectionModel(Configuration cfg) {
        return new TSServerConnectionModel(cfg.TSSERVER_HOST, cfg.TSSERVER_PORT);
    }

    @Provides
    public TSServerLogonModel createTsServerLogonModel(Configuration cfg) {
        return new TSServerLogonModel(cfg.TSSERVER_LOGIN, cfg.TSSERVER_PASSWORD);
    }
}
