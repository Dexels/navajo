package com.dexels.navajo.functions;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;


public class QuotePattern extends FunctionInterface {

  public QuotePattern() {}

  @Override
public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
      String s = (String) this.getOperands().get(0);

      if (s == null)
        return null;

      return Pattern.quote(s);
     
  }

  @Override
public String usage() {
      return "QuotePattern(String)";
  }

  @Override
public String remarks() {
      return "Make a string regex-safe by using Pattern.quote";
  }

}
