package com.tsxbot.tsxdk.model;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 15. of Mai
 * 2016
 * 20:44
 */
public class TSServerLogonModel {
    private final String login;
    private final String password;

    public TSServerLogonModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "TSServerLogonModel{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
