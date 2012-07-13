package com.dexels.navajo.functions;

import com.dexels.navajo.adapter.sqlmap.SQLMapConstants;
import com.dexels.navajo.parser.TMLExpressionException;

public class GetSequenceValue extends SingleValueQuery {
    public GetSequenceValue() {}
    
    public String remarks() {
        return "Gets the next value for the given sequence in the given database";
    }

    public String usage() {
        return "GetSequenceValue([TransactionContext], [([Username + Password] + @ + [DataSource] + :)]sequencename). Returns the sequence value. Built for tipi";
    }

    public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Integer transactionContext = -1;
        String datasource = null;
        String sequencename = null;
        
        Object o1 = getOperand(0);
        String sql = "";
        if (o1 instanceof Integer) {  // TransactionContext set.
            transactionContext = ((Integer) o1).intValue();
            Object o2 = getOperand(1);
            if (!(o2 instanceof String))
                throw new TMLExpressionException(this, "Invalid argument: " + o2);
            sequencename = (String) o2;
        } else if (o1 instanceof String) { // No TransactionContext set.
            sql = (String) o1;
            datasource = sql.substring(0, sql.indexOf(DATASOURCEDELIMITER));
            sequencename = sql.substring(sql.indexOf(DATASOURCEDELIMITER) + 1);
        } else
            throw new TMLExpressionException(this, "Invalid argument: " + o1);

        
        // Use SingleValueQuery to execute the statement
        SingleValueQuery query = new SingleValueQuery();
        query.reset();
        // Insert parameters
        if (transactionContext != -1) {
            query.insertOperand(transactionContext);
            query.insertOperand("SELECT 1 FROM dual");
        } else {
            // Filter the sequence out
            // Contains datasource specification
            query.insertOperand((datasource != null ? datasource : "") + ":SELECT 1 FROM dual");
        }

        query.evaluate();
        String dbIdenitifier = query.getDbIdentifier();
        if (sequencename != null) {
            if (dbIdenitifier.equals(SQLMapConstants.ORACLEDB)) {
                sql = "SELECT " + sequencename + ".nextval FROM dual";
            } else if (dbIdenitifier.equals(SQLMapConstants.POSTGRESDB)) {
                sql = "SELECT nextval('" + sequencename + "') FROM dual";
            }
        }
        
        // Reuse the query object for the actual work
        query.reset();
        if (transactionContext != -1) {
            query.insertOperand(transactionContext);
        }
        if (datasource != null) {
            sql = datasource + ":" + sql;
        }
        query.insertOperand(sql);
        
        return query.evaluate();
    }
}
