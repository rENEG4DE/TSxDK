package com.tsxbot.tsxdk.base;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tsxbot.system.modules.GuiceClientSystemBindings;
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
    protected final Injector clientInjector;
    protected final Logger log;
    protected final Configuration cfg;

    protected TSX(SystemDescriptor subSystem, Class<?> clazz) {
        clientInjector = Guice.createInjector(new GuiceClientSystemBindings());
        log = LoggerFactory.getLogger(createLoggerDescriptor(subSystem, clazz));
        cfg = clientInjector.getInstance(Configuration.class);
        clientInjector.getInstance(Configuration.class);
    }

    protected void throwFatal(String message, Throwable t) {
        log.error("Fatal: " + message, t);
        System.exit(-1);
    }

    public static String createLoggerDescriptor(SystemDescriptor subSystem, Class<?> clazz) {
        return subSystem.get() + "::" + clazz.getSimpleName();
    }

    public interface SystemDescriptor extends Supplier<String> {
    }
}
