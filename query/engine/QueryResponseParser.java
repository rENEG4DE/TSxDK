package com.tsxbot.tsxdk.query.engine;

import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.tsxbot.tsxdk.query.model.QueryResponse;
import com.tsxbot.tsxdk.query.model.QueryResultSet;
import com.tsxbot.tsxdk.query.model.wrapper.ErrorResponse;
import com.tsxbot.tsxdk.query.model.wrapper.SingleEntityResponse;
import com.tsxbot.tsxdk.utility.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ulli Gerhard on 09.10.2015.
 * does this need to be static ?
 */
class QueryResponseParser {
    private static final Logger log = LoggerFactory.getLogger(QueryResponseParser.class);
    private static final Configuration cfg = new Configuration();

    public static QueryResponse parse(String response) throws IllegalArgumentException {
        log.debug("Parsing response ({})", response);
        if (!isValidResponse(response)) {
            throw new IllegalArgumentException("Not a valid TS-query-response: (" + response + ")");
        }

        final QueryResponse result;

        switch (identifyType(response)) {
            case TYPE_RESULT: {
                result = parseResult(response);
            }
            break;
            case TYPE_ERROR: {
                result = parseError(response);
            }
            break;
            default:
                throw new IllegalArgumentException("Could not parse response: (" + response + ")");
        }

        return result;
    }

    private static ResponseType identifyType(String response) {
        return response.startsWith("error") ? ResponseType.TYPE_ERROR : ResponseType.TYPE_RESULT;
    }

    private static ErrorResponse parseError(String response) {
        return new ErrorResponse(new SingleEntityResponse(parseResult(response)));
    }

    private static QueryResultSet parseResult(String response) {
        final Table<Integer, String, String> table = HashBasedTable.create();
        final int[] row = {0};
        final Stopwatch watch = Stopwatch.createStarted();

        Arrays.stream(response.split("[|]"))
                .map(s -> {
                    final Properties properties = new Properties();
                    try {
                        //seems a complex conversion is done fine by java.util.Properties - no need to substitute
                        properties.load(new StringReader(s.replaceAll("[ ]", "\n")));
                    } catch (IOException e) {
                        log.error("Error transforming response-string", e);
                    }
                    return (Map) properties;
                })
                .forEach(map -> {
                            map.forEach((key, val) -> table.put(row[0], (String) key, (String) val));
                            row[0]++;
                        }
                );

        if (cfg.showProfiling()) {
            final long microsPassed = watch.elapsed(TimeUnit.MICROSECONDS);
            log.debug("Parsing result took {} microseconds", microsPassed);
        }

        return new QueryResultSet(table);
    }

    private static boolean isValidResponse(String response) {
        return !Strings.isNullOrEmpty(response);
    }

    private enum ResponseType {
        TYPE_RESULT,
        TYPE_ERROR
    }
}