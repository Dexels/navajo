package com.dexels.navajo.adapter;

import java.sql.Connection;
import java.sql.SQLException;

import org.dexels.grus.DbConnectionBroker;
import org.dexels.grus.GrusConnection;

public class TestDbConnectionBroker implements DbConnectionBroker {

    private String dbIdentifier;

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public boolean hasAutoCommit() {
        return false;
    }

    @Override
    public int getMaxCount() {
        return 0;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void destroy() {

    }

    @Override
    public int getUseCount() {
        return 0;
    }

    @Override
    public void freeConnection(GrusConnection gc) {

    }

    @Override
    public GrusConnection getGrusConnection() {
        return null;
    }

    @Override
    public String getDbIdentifier() {
        return dbIdentifier;
    }

    @Override
    public void setDbIdentifier(String dbIdentifier) {
        this.dbIdentifier = dbIdentifier;

    }

    @Override
    public void setSupportsAutoCommit(boolean b) {

    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void freeConnection(Connection conn) {

    }

    @Override
    public void setCloseAll() {

    }

}
