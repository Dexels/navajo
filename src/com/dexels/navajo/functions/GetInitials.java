package com.dexels.navajo.functions;


import com.dexels.navajo.parser.*;

import java.util.StringTokenizer;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public class GetInitials extends FunctionInterface {

    public GetInitials() {// Hallo
    }

    public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object a = this.getOperands().get(0);
        String text;
        try{
          text = (String) a;
        }catch(ClassCastException e){
          text = a.toString();
        }
        if(text == null){
          throw new TMLExpressionException("GetInitials(): failed because the input was null");
        }
        StringTokenizer tokens = new StringTokenizer(text, " ");
        String result = "";

        while(tokens.hasMoreTokens()){
          String name = tokens.nextToken();
          result = result + name.substring(0,1)+".";
        }
        return result.trim();
    }

    public String usage() {
        return "GetInitials(string)";
    }

    public String remarks() {
        return "This function returns the initials of a specified string field . Eg. GetInitials('aap noot mies') results in 'a n m'";
    }
}
