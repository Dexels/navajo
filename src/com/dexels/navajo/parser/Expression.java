

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.parser;


import java.util.*;
import javax.xml.transform.stream.StreamResult;

import com.dexels.navajo.document.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.xml.*;

import org.w3c.dom.*;


public class Expression {

  public static Operand evaluate( String clause, Navajo inMessage, Object o, Message parent, Selection sel ) throws TMLExpressionException, SystemException {

    Object aap = null;

    try {
      if ( clause.trim().equals( "" ) )
        return new Operand( "", Property.STRING_PROPERTY, "" );

      // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream(clause);
      java.io.StringReader input = new java.io.StringReader( clause );
      TMLParser parser = new TMLParser( input );

      parser.setNavajoDocument( inMessage );
      parser.setMappableObject( o );
      parser.setParentMsg( parent );
      parser.setParentSel( sel );
      parser.Expression();
      aap = parser.jjtree.rootNode().interpret();

    } catch ( TMLExpressionException tmle ) {
      tmle.printStackTrace();
      throw tmle;
    } catch ( ParseException ce ) {
      ce.printStackTrace();
      throw new SystemException( SystemException.PARSE_ERROR, "Expression syntax error: " + clause + "\n" +
          "After token " + ce.currentToken.toString() + "\n" + ce.getMessage() );
    } catch ( Throwable t ) {
      t.printStackTrace();
      throw new TMLExpressionException( "Invalid expression: " + clause + ".\nCause: " + t.getMessage() );
    }

    // System.out.println("aap = " + aap);

    if ( aap == null )
      return new Operand( null, "", "" );
    else if ( aap instanceof Integer )
      return new Operand( ( (Integer) aap ).intValue() + "", Property.INTEGER_PROPERTY, "" );
    else if ( aap instanceof String )
      return new Operand( (String) aap, Property.STRING_PROPERTY, "" );
    else if ( aap instanceof Date )
      return new Operand( Util.formatDate( (Date) aap ), Property.DATE_PROPERTY, "" );
    else if ( aap instanceof Double ) {
      double d = ( (Double) aap ).doubleValue();

      return new Operand( d + "", Property.FLOAT_PROPERTY, "" );
    } else if ( aap instanceof ArrayList )
      return new Operand( ( (ArrayList) aap ).toString(), Property.SELECTION_PROPERTY, "" );
    else if ( aap instanceof Boolean )
      return new Operand( ( (Boolean) aap ).toString(), Property.BOOLEAN_PROPERTY, "" );
    else if ( aap.getClass().getName().startsWith( "[Ljava.util.Vector" ) ) {
      return new Operand( aap, Property.POINTS_PROPERTY, "" );
    } else
      throw new TMLExpressionException( "Invalid return type for expression, " + clause + ": " + aap.getClass().getName() );

  }

  public static Operand evaluate( String clause, Navajo inMessage, Object o, Message parent ) throws TMLExpressionException, SystemException {
    return evaluate( clause, inMessage, o, parent, null );
  }

  public static Operand evaluate( String clause, Navajo inMessage ) throws TMLExpressionException, SystemException {
    return evaluate( clause, inMessage, null, null, null );
  }

  public static Message match( String matchString, Navajo inMessage, Object o, Message parent ) throws TMLExpressionException, SystemException {

    try {
      StringTokenizer tokens = new StringTokenizer( matchString, ";" );
      String matchSet = tokens.nextToken();

      if ( matchSet == null )
        throw new TMLExpressionException( "Invalid usage of match: match=\"[match set];[match value]\"" );
      String matchValue = tokens.nextToken();

      if ( matchValue == null )
        throw new TMLExpressionException( "Invalid usage of match: match=\"[match set];[match value]\"" );
      Util.debugLog( "matchSet = " + matchSet + ", matchValue = " + matchValue );
      Operand value = evaluate( matchValue, inMessage, o, parent );

      Util.debugLog( "value = " + value.value );

      ArrayList properties;

      if ( parent == null )
        properties = inMessage.getProperties( matchSet );
      else
        properties = parent.getProperties( matchSet );
      for ( int i = 0; i < properties.size(); i++ ) {
        Property prop = (Property) properties.get( i );
        Element parentnode = (Element) prop.ref.getParentNode();

        Util.debugLog( "prop = " + prop + ", parent = " + parentnode );
        if ( prop.getValue().equals( value.value ) )
          return new Message( parentnode );
      }
    } catch ( NavajoException e ) {
      throw new SystemException( -1, e.getMessage() );
    }
    return null;
  }

  public static void main( String args[] ) throws NavajoException,
      MappableException,
      java.io.IOException,
      TMLExpressionException {

    // String aap = "[toppie/verzekering/premie].month + 'EL_KPS_EP1'";
    // String aap = "[toppie/verzekering/premie] < Date('1980-01-01')";
    // String aap = "Contains([/toppie/verzekering.*/waarde], 13)";
    // String aap = "[toppie/verzekering0/selectie:name]";
    // String aap = "{23,43,43} + 5";
    // String aap = "[toppie/verzekering.*/waarde]";
    // String aap = "FORALL[toppie/verzekering.*/waarde], `([$x] > 10) AND ([$x] < 25)`)";
    String aap = "DateAdd([/toppie/verzekering0/premie],30,'YEAR')";

    try {
      Navajo doc = new Navajo();
      Message toppie = Message.create( doc, "toppie" );

      doc.addMessage( toppie );
      Message verzekering = Message.create( doc, "verzekering0" );

      toppie.addMessage( verzekering );
      Property premie = Property.create( doc, "premie", Property.INTEGER_PROPERTY, "10", 0, "", Property.DIR_IN );

      verzekering.addProperty( premie );
      Property waarde = Property.create( doc, "waarde", Property.INTEGER_PROPERTY, 10 + "", 0, "", Property.DIR_IN );

      verzekering.addProperty( waarde );
      Property sel = Property.create( doc, "selectie", "+", "Selecteer iets", Property.DIR_IN );

      verzekering.addProperty( sel );
      Selection opt = Selection.create( doc, "optie1", "martin", true );

      sel.addSelection( opt );
      opt = Selection.create( doc, "optie2", "arjen", false );
      sel.addSelection( opt );
      verzekering = Message.create( doc, "verzekering1" );
      toppie.addMessage( verzekering );
      waarde = Property.create( doc, "waarde", Property.INTEGER_PROPERTY, 12 + "", 0, "", Property.DIR_IN );
      verzekering.addProperty( waarde );

      XMLDocumentUtils.toXML( doc.getMessageBuffer(), null, null, new StreamResult( System.out ) );

      Message parent = doc.getMessage( "/toppie/verzekering0" );
      Message msg = match( "/toppie/verzekering.*/waarde;[premie]", doc, null, parent );

      Util.debugLog( "msg: " + msg );
      Util.debugLog( msg.getName() );
      // Util.debugLog("condition = " + Condition.evaluate(aap, doc));
      // Util.debugLog("aap = " + aap);
      // Operand op = evaluate(aap, doc);
      // PointsMap p = new PointsMap();
      // p.load(null, null, null, null, null);
      /**
       aap = "StringField('aap; noot; mies', ';', 2)";
       Operand op = evaluate(aap, doc, null, null);
       Util.debugLog("Result type: " + op.type);
       Util.debugLog("oValue = " + op.oValue);
       if (op.type.equals(Property.INTEGER_PROPERTY)) {
       int result = Integer.parseInt(op.value);
       Util.debugLog("result: " + result);
       } else if (op.type.equals(Property.FLOAT_PROPERTY)) {
       float result = Float.parseFloat(op.value);
       Util.debugLog("result: " + result);
       } else {
       String result = op.value;
       Util.debugLog("result: " + result);
       }
       */
    } catch ( SystemException e ) {
      Util.debugLog( ">>>>>>>" + e.getMessage() + "<<<<<<<" );
      System.exit( 1 );
    }

  }
}
