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

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;


public class Trim extends FunctionInterface {

  public static final String vcIdent = "$Id$";

  public static final Logger logger =
    Logger.getLogger( Trim.class.getName() );

  public Trim() {
  }

  public Object evaluate()
    throws com.dexels.navajo.parser.TMLExpressionException {

    final Object op = this.getOperands().get( 0 );

    if ( op == null ) {
      this.logger.log( Priority.DEBUG, "tried to Trim() a null Object" );
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