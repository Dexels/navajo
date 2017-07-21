package com.dexels.navajo.functions;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;


public class ToPattern extends FunctionInterface {

  public ToPattern() {}

  @Override
public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
      String s = (String) this.getOperands().get(0);
      
      if (s == null)
          return null;

      int options = 0;
      if (this.getOperands().size() > 1) {
          String soptions = (String) this.getOperands().get(1);
          if (soptions.indexOf('i') > 0) {
              options = options | Pattern.CASE_INSENSITIVE;
          }
      }
      try {
          return Pattern.compile(s);
      } catch (PatternSyntaxException e) { 
          throw new TMLExpressionException("Invalid regex!");
      }
  }

  @Override
public String usage() {
      return "ToPattern(String pattern, string options)";
  }

  @Override
public String remarks() {
      return "Returns a regex pattern of the given string. Optionally use RegexQuote to quote a (part of) the regex ";
  }

}
