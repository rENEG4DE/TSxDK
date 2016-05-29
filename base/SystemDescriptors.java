package com.tsxbot.tsxdk.base;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 15. of Mai
 * 2016
 * 20:27
 */
public enum SystemDescriptors implements TSX.SystemDescriptor {
    IO, QUERY, UTILITY;

    @Override
    public String get() {
        return name();
    }
}
