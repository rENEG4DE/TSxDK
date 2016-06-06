package com.tsxbot.tsxdk.query.model.wrapper;


import com.google.inject.assistedinject.Assisted;
import com.tsxbot.tsxdk.query.model.QueryResponse;
import com.tsxbot.tsxdk.query.model.QueryResultSet;

import java.util.Map;
import java.util.stream.Stream;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 27. of Mai
 *  2016
 *  10:22
 */
public class MultiEntityResponse extends ResponseWrapper {
    private final QueryResultSet resultSet;

    private MultiEntityResponse(QueryResultSet queryResponse) {
        super(queryResponse);
        this.resultSet = queryResponse;
    }

    public Map<String, String> getEntity(int idx) {
        return resultSet.getTable().row(idx);
    }

    public Stream<Map.Entry<Integer, Map<String, String>>> stream() {
        return resultSet.getTable().rowMap().entrySet().stream();
    }

    public static MultiEntityResponse create(QueryResultSet response) {
        return new MultiEntityResponse(response);
    }
}
