package com.dexels.navajo.adapter.functions;

import org.dexels.grus.GrusProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.sqlmap.SQLMapConstants;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.script.api.UserException;

public class GetSequenceValue extends FunctionInterface {
    private final static Logger logger = LoggerFactory.getLogger(GetSequenceValue.class);

   
    public GetSequenceValue() {}
    
    @Override
	public String remarks() {
        return "Gets the next value for the given sequence in the given database";
    }

    @Override
	public String usage() {
        return "GetSequenceValue([TransactionContext], [([Username + Password] + @ + [DataSource] + :)]sequencename). Returns the sequence value. Built for tipi";
    }

    @Override
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
            datasource = sql.substring(0, sql.indexOf(SingleValueQuery.DATASOURCEDELIMITER));
            sequencename = sql.substring(sql.indexOf(SingleValueQuery.DATASOURCEDELIMITER) + 1);
        } else
            throw new TMLExpressionException(this, "Invalid argument: " + o1);

        
        // Use SingleValueQuery to execute the statement
        SingleValueQuery query = new SingleValueQuery();
        query.reset();
        query.setAccess(getAccess());
     

        String dbIdentifier = getDbIdentifier(datasource, transactionContext);
        if (sequencename != null) {
            if (SQLMapConstants.POSTGRESDB.equals(dbIdentifier) || SQLMapConstants.ENTERPRISEDB.equals(dbIdentifier)) {
                sql = "SELECT nextval('" + sequencename + "')";
            } else {
            	sql = "SELECT " + sequencename + ".nextval FROM dual";
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

    private String getDbIdentifier(String datasource, Integer transactionContext) {
        if (GrusProviderFactory.getInstance() != null) {
            try {
                if (transactionContext != null && transactionContext != -1 ) {
                    return GrusProviderFactory.getInstance().getDatabaseIdentifier(transactionContext);
                }
                return GrusProviderFactory.getInstance().getDatabaseIdentifier(getAccess().getTenant(), datasource);
            } catch (UserException e) {
                logger.error("Exception in determining database identifier", e);
            }
        }
        logger.warn("Unable to identifiy database identifier due to missing GrusFactoryProvider");
        return null;
    }
}
