package com.tsxbot.tsxdk.client.entity;

import com.tsxbot.tsxdk.base.SystemDescriptors;
import com.tsxbot.tsxdk.base.TSX;
import com.tsxbot.tsxdk.query.QueryChannel;
import com.tsxbot.tsxdk.query.QueryGateway;
import com.tsxbot.tsxdk.query.engine.QueryFactory;
import com.tsxbot.tsxdk.query.model.QueryResponse;
import com.tsxbot.tsxdk.query.model.QueryResultSet;
import com.tsxbot.tsxdk.query.model.wrapper.MultiEntityResponse;

import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;

/*
 *  TSxBot2
 *  Coded by rENEG4DE 
 *  on 06. of Jun
 *  2016
 *  09:32
 */
public class EntityGateway extends TSX {
    protected final QueryFactory queryFactory;
    protected final QueryChannel queryChannel;

    protected EntityGateway(QueryGateway gateway, Class<?> clazz) {
        super(SystemDescriptors.GATEWAY, clazz);
        this.queryChannel = gateway.getQueryChannel();
        this.queryFactory = gateway.getQueryFactory();
    }

    protected <T> T[] collectAll(String field,
                                 QueryResponse resultSet,
                                 Function<String, T> convert,
                                 IntFunction<T[]> dest) {
        return MultiEntityResponse.create((QueryResultSet) resultSet)
                .stream().map(Map.Entry::getValue)
//                .map(entry -> convert.apply(entry.get(field)))
                .map((__) -> __.get(field))
                .map(convert)
                .toArray(dest);
    }
}
