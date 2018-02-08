package com.dexels.navajo.adapter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.dexels.grus.DbConnectionBroker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.adapter.sqlmap.SQLMapConstants;
import com.dexels.navajo.script.api.UserException;

public class TestSQLMapPostgres {
    private SQLMap map;

    @Before
    public void setup() {
        map = new SQLMap();
        
        // Create a ConnectionBroker that we then set to PostgreSQL
        DbConnectionBroker legacyDbConnectionBroker = new TestDbConnectionBroker();
        legacyDbConnectionBroker.setDbIdentifier(SQLMapConstants.POSTGRESDB);
        map.myConnectionBroker = legacyDbConnectionBroker;
    }

    @After
    public void destroy() {
        map = null;
    }

    @Test
    public void testPostgresQueryRowNum() throws UserException {
        String query = "SELECT 1 FROM dual WHERE\n rownum            = 1";

        map.setQuery(query);
        assertTrue(map.getQuery().contains("LIMIT"));
        assertFalse(map.getQuery().contains("rownum"));
    }
    
    @Test
    public void testPostgresQueryNextVal() throws UserException {
        String query = "    VALUES (  attribute_seq.nextval, ?, ?, ?, ? )";

        map.setQuery(query);
        assertTrue(map.getQuery().contains("nextval('attribute_seq'), ?"));
        assertFalse(map.getQuery().contains(".nextval"));
    }
    
    @Test
    public void testPostgresQuerySysdate() throws UserException {
        String query = " ( SELECT FLOOR(MONTHS_BETWEEN(sysdate,(organizationmember.relationstart)) / 12) FROM dual ";

        map.setQuery(query);
        assertTrue(map.getQuery().contains("LOCALTIMESTAMP"));
        assertFalse(map.getQuery().contains("sysdate"));
    }
    
    @Test
    public void testPostgresQueryNumber() throws UserException {
        String query = "  CAST(amount AS NUMBER(20,2)) AS amount";

        map.setQuery(query);
        assertTrue(map.getQuery().contains("NUMERIC"));
        assertFalse(map.getQuery().contains("NUMBER"));
    }
}
