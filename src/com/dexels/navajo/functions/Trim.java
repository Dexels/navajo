package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version $Id$
 */

public class Trim extends FunctionInterface {

  public static final String vcIdent = "$Id$";


  public Trim() {
  }

  public Object evaluate()
    throws com.dexels.navajo.parser.TMLExpressionException {

    final Object op = this.getOperands().get( 0 );

    if ( op == null ) {
      return ( "" );
    }

    if ( !( op instanceof java.lang.String ) ) {
      throw new TMLExpressionException( this, "String argument expected" );
    }

    final String s = (String) op;

    if ( s != null ) {
      return s.trim();
    } else {
      return s;
    }
  }

  public String usage() {
    return "Trim(string)";
  }

  public String remarks() {
    return "Trims a string";
  }

} // public class Trim extends FunctionInterface

// EOF: $RCSfile$ //