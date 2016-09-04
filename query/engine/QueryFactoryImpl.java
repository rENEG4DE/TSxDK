package com.tsxbot.tsxdk.query.engine;


import com.tsxbot.tsxdk.query.model.Query;

import static com.tsxbot.tsxdk.query.model.LibQuery.*;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 27. of Mai
 * 2016
 * 10:22
 */
public class QueryFactoryImpl implements QueryFactory {
    @Override
    public Query login(String name, String pw) {
        return new Query(String.format(LOGIN.get(), name, pw));
    }

    @Override
    public Query servercreate(String serverName) {
        return new Query(String.format(SERVERCREATE.get(), serverName));
    }

    @Override
    public Query channelcreate(String channelName, String password) {
        return new Query(String.format(CHANNELCREATE.get(), channelName, password));
    }

    @Override
    public Query serverprocessstop() {
        return new Query(SERVERPROCESSSTOP.get());
    }

    @Override
    public Query serverinfo() {
        return new Query(SERVERLIST.get());
    }

    @Override
    public Query stopvirtualserver(int serverId) {
        return new Query(String.format(SERVERSTOP.get(), serverId));
    }

    @Override
    public Query deletevirtualserver(int serverId) {
        return new Query(String.format(SERVERDELETE.get(), serverId));
    }

    @Override
    public Query channellist() {
        return new Query(CHANNELLIST.get());
    }

    @Override
    public Query use(int sid) {
        return new Query(String.format(USE.get(), sid));
    }

    @Override
    public Query serverstart(int sid) {
        return new Query(String.format(SERVERSTART.get(), sid));
    }

    @Override
    public Query privilegekeydelete(String token) {
        return new Query(String.format(PRIVILEGEKEYDELETE.get(), token));
    }

    @Override
    public Query channelinfo(int cid) {
        return new Query(String.format(CHANNELINFO.get(), cid));
    }

    @Override
    public Query channeldelete(int cid) {
        return new Query(String.format(CHANNELDELETE.get(), cid));
    }

    @Override
    public Query logout() {
        return new Query(LOGOUT.get());
    }

    @Override
    public Query clientlist() {
        return new Query(CLIENTLIST.get());
    }
}