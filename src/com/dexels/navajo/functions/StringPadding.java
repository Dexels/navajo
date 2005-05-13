package com.dexels.navajo.functions;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.dexels.navajo.parser.*;

/**
 * @author: Matthijs Philip.
 * @company: Dexels BV
 * @created: 13-05-2005
 *  
 */

public class StringPadding extends FunctionInterface  {
    
    public String remarks() {
        return "This function ads whitespace (spaces) to a string until it has the specified size";
      }
    
    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        String object = (String) getOperand(0);
       
        int padSize = Integer.parseInt((String) getOperand(1));
        
        while (object.length() < padSize) {           
            object = object + " ";
        }

        return object;
      }
  

  public String usage() {
    return "StringPadding(String Object, Integer PadToSize)";
  }

  public static void main(String [] args) throws Exception {
    System.out.println("Starting mofo..");
    StringPadding f = new StringPadding();
    f.reset();
    String myProperty = "Voetbal";
    f.insertOperand(myProperty);
    f.insertOperand("5"); 
    Object o = f.evaluate();
    String apenoot = o.toString();
    System.out.println("o.length : " + apenoot.length());
    System.out.println("o = " + o + ", type = " + o.getClass().getName());
  }
}

