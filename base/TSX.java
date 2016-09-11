package com.tsxbot.tsxdk.base;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tsxbot.tsxdk.modules.TSxDKBindings;
import com.tsxbot.tsxdk.utility.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * TSxBot2
 * Coded by Ulli Gerhard
 * on 15. of Mai
 * 2016
 * 12:15
 */
public abstract class TSX {
    protected final Injector injector;
    protected final Logger log;
    protected final Configuration cfg;

    protected TSX(SystemDescriptor subSystem, Class<?> clazz) {
        log = LoggerFactory.getLogger(createLoggerDescriptor(subSystem, clazz));
//        For dependency-reasons, we can not use the topmost Guice-module here, so we need to create a low-level-one
//        injector = Guice.createInjector(new GuiceClientSystemBindings());
        injector = Guice.createInjector(new TSxDKBindings());
        cfg = injector.getInstance(Configuration.class);
    }

    public static String createLoggerDescriptor(SystemDescriptor subSystem, Class<?> clazz) {
        return subSystem.get() + "::" + clazz.getSimpleName();
    }

    protected void throwFatal(String message, Throwable t) {
        log.error("Fatal: " + message, t);
        System.exit(-1);
    }

    public interface SystemDescriptor extends Supplier<String> {
    }
}
