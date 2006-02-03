package com.dexels.navajo.util.navadoc;


import org.custommonkey.xmlunit.DifferenceListener;
import org.w3c.dom.Node;
import org.custommonkey.xmlunit.Difference;

/**
 * <p>Title: NavaDocDifferenceListener</p>
 * <p>Description: for XML unit testing of NavaDoc's which
 * skips certain comparisons, typically comments and meta tags</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author Matthew Eichler
 * @version $Id$
 */

// logging

public class NavaDocDifferenceListener implements DifferenceListener {

  
  public NavaDocDifferenceListener() {
    super();
  }

  public int differenceFound(
    String controlVal, String resultVal,
    Node controlNode, Node resultNode,
    Difference diff ) {

    if ( ( controlNode.getNodeName().compareToIgnoreCase( "meta" ) == 0 ) &&
      ( resultNode.getNodeName().compareToIgnoreCase( "meta" ) == 0 ) ) {
     
      return ( DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR );
    }

    if ( ( controlNode.getNodeType() == Node.COMMENT_NODE ) &&
      ( resultNode.getNodeType() == Node.COMMENT_NODE ) ) {
    
      return ( DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR );
    }

    return ( DifferenceListener.RETURN_ACCEPT_DIFFERENCE );

  } // public int differenceFound()

  public void skippedComparison( Node parm1, Node parm2 ) {

    /** @todo: Implement this org.custommonkey.xmlunit.DifferenceListener method*/
    throw new java.lang.UnsupportedOperationException( "Method skippedComparison() not yet implemented." );
  }

  public boolean haltComparison( Difference diff ) {

    // OK, just the default behaviour
    return ( !diff.isRecoverable() );

  } // public boolean haltComparison()

} // public class NavaDocDiferrenceListener

// EOF: $RCSfile$ //