package com.dexels.navajo.util.navadoc;

/**
 * <p>Title: NavaDocIndexDOM</p>
 * <p>Description: DOM Representing the index of services the
 * documentor has collected and documented in HTML</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author Matthew Eichler
 * @version $Id$
 */

import com.dexels.navajo.util.navadoc.NavaDocBaseDOM;

// XML stuff
import org.w3c.dom.*;
import org.w3c.dom.html.*;
import javax.xml.parsers.*;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class NavaDocIndexDOM extends NavaDocBaseDOM {

  public static final String vcIdent = "$Id$";

  public static final Logger logger =
    Logger.getLogger( NavaDocIndexDOM.class.getName() );

  private String title = null;
  private Element table = null;
  private Element tbody = null;

  /**
   * Contruct a new index DOM, document headers will be
   * set-up
   *
   * @param String project name, null is OK
   * @param String URI to CSS style-sheet, null is OK
   */

  public NavaDocIndexDOM( String pname, String uri )
    throws ParserConfigurationException {

    super();

    this.projectName = pname;
    this.cssUri = uri;
    this.title = this.projectName + ": Web Services Index";
    this.setHeaders( this.title );
    this.addBody( "index-body" );

    // Page Body Header
    Element h1 = this.dom.createElement( "h1" );
    h1.setAttribute( "class", "index-page-heading" );
    Text titleText = this.dom.createTextNode( title );
    h1.appendChild( titleText );
    this.body.appendChild( h1 );

    // Table
    this.table = this.dom.createElement( "table" );
    this.table.setAttribute( "align", "center" );
    this.table.setAttribute( "border", "1" );
    this.table.setAttribute( "class", "index-table" );
    this.body.appendChild( this.table );

    // Table Header
    Element thead = this.dom.createElement( "thead" );
    thead.setAttribute( "class", "index-thead" );
    Element thRow = this.dom.createElement( "tr" );
    thRow.setAttribute( "class", "index-table-heading-row" );
    Element thLeft = this.dom.createElement( "th" );
    thLeft.setAttribute( "class", "index-table-heading-cell" );
    Element thRight = this.dom.createElement( "th" );
    thRight.setAttribute( "class", "index-table-heading-cell" );
    Text textLeft = this.dom.createTextNode( "Service" );
    Text textRight = this.dom.createTextNode( "Description" );
    thLeft.appendChild( textLeft );
    thRight.appendChild( textRight );
    thRow.appendChild( thLeft );
    thRow.appendChild( thRight );
    thead.appendChild( thRow );
    this.table.appendChild( thead );

    // Table body
    this.tbody = this.dom.createElement( "tbody" );
    this.tbody.setAttribute( "class", "index-tbody" );
    this.table.appendChild( this.tbody );

  } // public NavaDocIndexDOM()

  /**
   * Adds an web services as an index entry to the DOM, constructing
   * the correct HREF link
   *
   * @param String name of web service
   * @param String optional notes/description, null is OK
   */

  public void addEntry( String sname, String notes ) {

    Element tr = this.dom.createElement( "tr" );
    tr.setAttribute( "class", "index-body-row" );

    Element tdLeft = this.dom.createElement( "td" );
    tdLeft.setAttribute( "class", "index-service-name" );
    Text serviceText  = this.dom.createTextNode( sname );

    Element tdRight = this.dom.createElement( "td" );
    tdRight.setAttribute( "class", "index-service-description" );
    Text notesText  = this.dom.createTextNode( notes );

    tdLeft.appendChild( serviceText );
    tdRight.appendChild( notesText );
    tr.appendChild( tdLeft );
    tr.appendChild( tdRight );
    this.tbody.appendChild( tr );

  } // public void addEntry()

} // public class NavaDocIndexDOM

// EOF: $RCSfile$ //
