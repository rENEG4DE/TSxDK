package com.tsxbot.tsxdk.query.model.wrapper;

import com.tsxbot.tsxdk.query.model.QueryResponse;
import com.tsxbot.tsxdk.query.model.QueryResultSet;

/*
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 30. of Mai
 *  2016
 *  11:19
 *  ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
 */
public interface WrappedResponse {
    public <T> T create(QueryResponse response);
}
