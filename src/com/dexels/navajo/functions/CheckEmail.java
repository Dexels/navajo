package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import java.util.regex.Pattern;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class CheckEmail extends FunctionInterface {

  public String remarks() {
   return "This functions checks the syntactic validity of email adressess";
  }
  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

    Object o = getOperand(0);
    if (!(o instanceof String)) {
      throw new TMLExpressionException(this, "Invalid email address, string expected");
    }

    String email = (String) o;

    try {
     Pattern re = Pattern.compile("[A-z.\\-_0-9]+[@]{1}[A-z\\-_0-9]+[A-z.\\-_0-9]+[A-z\\-_0-9]{1}");
     boolean isMatch = re.matcher(email).matches();
     if(!isMatch) {
       return new Boolean(false);
     } else
       return new Boolean(true);
   }
   catch (Exception ree) {
     return new Boolean(false);
   }

  }
  public String usage() {
    return "CheckEmail(adress)";
  }

  public static void main(String [] args ) throws TMLExpressionException {
    CheckEmail ce = new CheckEmail();
    ce.reset();
    ce.insertOperand("Carlo.tiecken@dexels.nl.navajo.");
    Boolean b = (Boolean) ce.evaluate();
    System.err.println("result = " + b);
  }

}