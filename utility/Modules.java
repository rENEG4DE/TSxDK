package com.tsxbot.tsxdk.utility;/*
 * TSxBot2
 * Coded by Ulli Gerhard
 * on Freitag, 02 of Dezember, 2016
 * 05:08
 */

import com.google.inject.Injector;

import java.util.StringJoiner;

public class Modules {
    public static String listInjectorMeta(Injector injector) {
        final StringJoiner joiner = new StringJoiner(",\n\t", "Module-meta: {\n\t", "\n}");
        return injector.getAllBindings().entrySet().stream()
                .reduce(joiner,
                        (stringJoiner, entry) -> stringJoiner.add(entry.getKey().getTypeLiteral()
                                + "    :        "
                                + entry.getValue().getKey()
                                + " : "
                                + entry.getValue().getProvider()),
                        StringJoiner::merge).toString();
    }
}


/*
// Graveyard

//                pluginHandle.getRuntime().getInjector().getAllBindings().entrySet().stream().forEach(entry ->
//                {
//                    builder.append(entry.getKey() + ": " + entry.getValue());
////                    builder.append(entry.toString());
//                    builder.append("\n");
//                });

                Joiner.MapJoiner joiner = Joiner.on(",\n").withKeyValueSeparator("=");
                log.info("Plugin offers classes {}", joiner.join(pluginHandle.getRuntime().getInjector().getBindings()));
                log.info("Plugin offers classes {}",
                        pluginHandle.getRuntime().
                );

//                log.info("Plugin offers classes {}",
//                        pluginHandle.getRuntime().getInjector().getAllBindings().entrySet().stream().flatMap(
//
//                        ));

 */
