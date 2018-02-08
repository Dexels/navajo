package com.dexels.navajo.functions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public final class DateTime extends FunctionInterface {

    @Override
	public String remarks() {
        return "Returns a string containing the current Date Time Stamp. Uses java.time.format.DateTimeFormater and can"
        		 + "hanndle all the patters of that class.";
    }

    @Override
	public String usage() {
        return "DateTime( | String pattern)";
    }

    @Override
	public final String evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    		
    		String pattern = "";
    		DateTimeFormatter dtf;
    		
    		if(this.getOperands().isEmpty()){
    			// Getting Default Pattern
    			System.out.println("Getting Default Parser");
    			pattern = "dd-MM-yyyy HH:mm:ss";
    		}else {
    			if(this.getOperands().size() == 1) {
    				Object o = this.getOperand(0);
    				if(o instanceof String)
    					pattern = (String) this.getOperand(0);
    				else
    					throw new TMLExpressionException(this, "error: arguments must be a String ");
    			}else
    				throw new TMLExpressionException(this, "error: can take 0 or 1 arguments ");
    		}
    		
    		try{
    			dtf = DateTimeFormatter.ofPattern(pattern);
    		}catch(Exception e){
    			// Pattern not found. Setting default Pattern
    			System.out.println("Pattern not found. Setting default pattern : dd-MM-yyyy HH:mm:ss");
    			dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    		}
    		
	    	LocalDateTime now = LocalDateTime.now();
	    	System.out.println(dtf.format(now));
	    	
	    	return dtf.format(now);
    }

    public static void main(String[] args) throws Exception {
	    	
	    	DateTime dateTime = new DateTime();
	    	
	    	// Test using invalid arguments
	    	System.out.println("--------- Testing invalid arguments 1---------");
	    	try {
			dateTime.reset();
			dateTime.insertOperand(1);
			dateTime.evaluate();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	    	
	    	// Test using multiple arguments
	    	System.out.println("--------- Testing multiple arguments---------");
	    	try {
			dateTime.reset();
			dateTime.insertOperand("yyyy/MM/dd HH:mm:ss");
			dateTime.insertOperand("yyyy/MM/dd HH:mm:ss");
			dateTime.evaluate();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	    	
	    	// Test using default parameter
	    	System.out.println("--------- Testing default pattern ---------");
	    	try {
			dateTime.reset();
			dateTime.evaluate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    	
	    	// Test using valid pattern
	    	System.out.println("--------- Testing valid pattern yyyy/MM/dd HH:mm:ss---------");
	    	try {
			dateTime.reset();
			dateTime.insertOperand("\"yyyy/MM/dd HH:mm:ss\"");
			dateTime.evaluate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    	
	    	// Test using valid pattern
	    	System.out.println("--------- Testing valid pattern dd-mm-yyyy ---------");
	    	try {
			dateTime.reset();
			dateTime.insertOperand("\"dd-mm-yyyy\"");
			dateTime.evaluate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    	
	    	// Test using valid pattern
	    	System.out.println("--------- Testing valid pattern HH:mm:ss ---------");
	    	try {
			dateTime.reset();
			dateTime.insertOperand("\"HH:mm:ss\"");
			dateTime.evaluate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    	
	    	// Test using invalid pattern
	    	System.out.println("--------- Testing invalid pattern cc---------");
	    	try {
			dateTime.reset();
			dateTime.insertOperand("cc");
			dateTime.evaluate();
		} catch (Exception e) {
			e.printStackTrace();
		}     	
	    	
    }
}