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

import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.dexels.navajo.util.navadoc.config.DocumentSet;


public class NavaDocIndexDOM extends NavaDocBaseDOM {

  public static final String vcIdent = "$Id$";

  
  private String title = null;
  private Element table = null;
  private Element tbody = null;
  private Element firstRefRow = null;

  /**
   * Contruct a new index DOM, document headers will be
   * set-up
   *
   * @param String project name, null is OK
   * @param String URI to CSS style-sheet, null is OK
   */

  public NavaDocIndexDOM( final DocumentSet dset )
    throws ParserConfigurationException {

    super(dset);

    this.title = dset.getName() + ": Web Services Index";
    this.setHeaders( this.title );
    this.addBody( "index-body" );

    // Page Body Header
    final Element h1 = this.dom.createElement( "h1" );

    h1.setAttribute( "class", "index-page-heading" );
    final Text titleText = this.dom.createTextNode( title );

    h1.appendChild( titleText );
    this.body.appendChild( h1 );

    // Project Description
    final Element desc = this.dom.createElement( "p" );
    desc.setAttribute( "class", "index-page-description" );
    final Text dText = this.dom.createTextNode( dset.getDescription() );
    desc.appendChild( dText );
    this.body.appendChild(desc);

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
    final String href = this.baseUri + sname + ".html";
    this.addEntryRow( sname, notes, href);

  } // public void addEntry()

  /**
   * Adds an web services as an index entry to the DOM, constructing
   * the correct HREF link from the HttpServlet
   *
   * @param String name of web service
   * @param String optional notes/description, null is OK
   * @param additional path info from the servlet [HttpServletRequest.getPathInfo()]
   */
  public void addEntry( String sname, String notes, String uri ) {
    final String href = uri + "?sname=" + sname;
    this.addEntryRow( sname, notes, href);
  }

  // ----------------------------------------------------------- private methods

  /**
   * creates the row in the index table given the service name,
   * notes and link information
   *
   * @param String name of web service
   * @param String optional notes/description, null is OK
   * @param URI link data
   */

  private void addEntryRow( String sname, String notes, String href ) {
    final Element tr = this.dom.createElement( "tr" );

    tr.setAttribute( "class", "index-body-row" );

    final Element tdLeft = this.dom.createElement( "td" );

    tdLeft.setAttribute( "class", "index-service-name" );
    final Element a = this.dom.createElement( "a" );

    a.setAttribute( "href", href );
    a.setAttribute( "class", "web-service-href" );
    final Text serviceText = this.dom.createTextNode( sname );

    a.appendChild( serviceText );

    final Element tdRight = this.dom.createElement( "td" );

    tdRight.setAttribute( "class", "index-service-description" );

    if ( notes == null ) {
      notes = "";
    }
    Text notesText = this.dom.createTextNode( notes );

    tdLeft.appendChild( a );
    tdRight.appendChild( notesText );
    tr.appendChild( tdLeft );
    tr.appendChild( tdRight );
    this.tbody.appendChild( tr );

    if ( this.firstRefRow == null ) {
      this.firstRefRow = tr;
    }

  }

} // public class NavaDocIndexDOM

// EOF: $RCSfile$ //
