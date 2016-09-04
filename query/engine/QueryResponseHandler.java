package com.tsxbot.tsxdk.query.engine;

import com.tsxbot.tsxdk.io.IO;
import com.tsxbot.tsxdk.query.model.Query;
import com.tsxbot.tsxdk.query.model.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 27. of Mai
 * 2016
 * 10:22
 */
class QueryResponseHandler implements Runnable {
    private final Logger log = LoggerFactory.getLogger(QueryResponseHandler.class);
    private final QueryEngine engine;
    private final IO io;

    public QueryResponseHandler(QueryEngine engine) {
        this.engine = engine;
        io = engine.getIO();
    }

    @Override
    public void run() {
        io.in().ifPresent(this::handleContent);
    }

    private void handleContent(String content) {
        try {
            final QueryResponse response = QueryResponseParser.parse(content);
            final Query top = engine.getCurrentQuery();
            response.assignTo(top);
            if (top.isSatisfied()) engine.notifyQuerySatisfied();
        } catch (Exception e) {
            log.error("An Exception occured on trying to handle query-response-content", e);
        }
    }

    public static QueryResponseHandler create(QueryEngine queryEngine) {
        return new QueryResponseHandler(queryEngine);
    }
}
