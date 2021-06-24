/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.twitter.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.twitter.TwitterAdapter;

import winterwell.jtwitter.TwitterException;

public class CheckTwitterToken  extends FunctionInterface {

    
    @Override
    public String remarks() {
        return "Returns a boolean indicating whether the supplied token is a valid and active Twitter token";
    }

    @Override
    public Object evaluate() throws TMLExpressionException {
        if (operand(0) == null)
            return null;
        
        String token1 = getStringOperand(0);
        String token2 = getStringOperand(1);
        
        if (token1 == null || token2 == null) {
            return false;
        }
        
        TwitterAdapter ta = new TwitterAdapter();
        ta.setToken1(token1);
        ta.setToken2(token2);
        
        try {
            ta.getStatus();
        } catch (TwitterException te) {
            if (te.getMessage().indexOf("code 89") > -1) {
                return false;
            }
            // At this point, we not really sure. We got an exception, but it's not 
            // an invalid account (code 89).
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        
        String t1 =  "ls3bL6D9BnYWIG05I0ddlyeyfl5cIWCLOrZdcqLN0E";
        String t2 =  "325415660-5I612uxCvPyYkQUuROwlxhDYSFT4jTqzt6hHcZxz";
        
        CheckTwitterToken ctt = new CheckTwitterToken();
        ctt.reset();
        ctt.insertStringOperand(t1);
        ctt.insertStringOperand(t2);
        
        Boolean result;
        try {
            result = (Boolean) ctt.evaluate();
            System.out.println("Result = " + result);
        } catch (TMLExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
