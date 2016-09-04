package com.tsxbot.tsxdk.client.entity;

import java.util.Map;

/*
 *  TSxBot2
 *  Coded by rENEG4DE 
 *  on 10. of Jun
 *  2016
 *  10:47
 */
public class EntityModel {
    final Map<String, String> rawData;

    public EntityModel(Map<String, String> rawData) {
        this.rawData = rawData;
    }

    public String getString(String identifier) {
        return rawData.get(identifier);
    }

    public Integer getInteger(String identifier) {
        return Integer.parseInt(rawData.get(identifier));
    }
}
