package com.tsxbot.tsxdk.query.engine;

import com.tsxbot.tsxdk.query.model.Query;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 29. of Mai
 * 2016
 * 00:29
 */
public interface QueryFactory {
    Query login(String name, String pw);

    Query servercreate(String serverName);

    Query channelcreate(String channelName, String password);

    Query serverprocessstop();

    Query serverinfo();

    Query servernotifyregister(String eventName);

    Query stopvirtualserver(int serverId);

    Query deletevirtualserver(int serverId);

    Query channellist();

    Query use(int sid);

    Query serverstart(int sid);

    Query privilegekeydelete(String token);

    Query channelinfo(int cid);

    Query channeldelete(int cid);

    Query logout();

    Query clientlist();
}
