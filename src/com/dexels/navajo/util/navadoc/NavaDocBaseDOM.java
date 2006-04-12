package com.dexels.navajo.util.navadoc;

/**
 * <p>Title: NavaDocBaseDOM</p>
 * <p>Description: a base HTML DOM for holding the results
 * of Navajo documentation</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author Matthew Eichler
 * @version $Id$
 */

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Comment;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.dexels.navajo.util.navadoc.config.DocumentSet;

public class NavaDocBaseDOM {

  public static final String vcIdent =
      "$Id$";

  // DOM Document Builder
  protected DocumentBuilder dBuilder = null;
  protected Document dom = null;
  protected Element root = null;
  protected Element body = null;

  // optional properties for XHTML document headers
  protected String projectName = null;
  protected String cssUri = NavaDocConstants.DEFAULT_CSS;
  protected String baseUri = NavaDocConstants.DEFAULT_BASE_URI;
  protected DocumentSet dset = null;

  // base name for page
  protected String baseName = "index";

  /**
   * Create a new base NavaDoc style DOM with the
   * default base name
   *
   * @throws ParserConfigurationException
   */

  public NavaDocBaseDOM() throws ParserConfigurationException {

    // get a DOM document builder
    this.dBuilder =
        (DocumentBuilderFactory.newInstance()).newDocumentBuilder();

    this.newDocument();

  } // public NavaDocBaseDOM()

  /**
   * Creates a new base NavaDoc style DOM with a
   * give base name, usually the logical name of a
   * service.  This was helpful for testing
   *
   * @param base name
   * @throws ParserConfigurationException
   */

  public NavaDocBaseDOM(String name) throws ParserConfigurationException {

    // get a DOM document builder
    this.dBuilder =
        (DocumentBuilderFactory.newInstance()).newDocumentBuilder();

    this.newDocument();
    this.baseName = name;

  }

  public NavaDocBaseDOM(DocumentSet s) throws ParserConfigurationException {
    this.dset = s;

    // get a DOM document builder
    this.dBuilder =
        (DocumentBuilderFactory.newInstance()).newDocumentBuilder();

    this.newDocument();
    this.projectName = this.dset.getName();
    if (dset.getProperty(NavaDocConstants.CSS_URI_PROPERTY) != null) {
      this.cssUri = dset.getProperty(NavaDocConstants.CSS_URI_PROPERTY);
    }
    if (dset.getProperty(NavaDocConstants.BASE_URI_PROPERTY) != null) {
      this.baseUri = dset.getProperty(NavaDocConstants.BASE_URI_PROPERTY);
    }

  }

  /**
   * set Project Name for the document, this is optional
   *
   * @param String Project Name
   */

  public void setProjectName(String pName) {
    this.projectName = pName;
  }

  /**
   * get the DOM document for this object
   *
   * @return DOM document
   */

  public Document getDocument() {
    return (this.dom);
  }

  /**
   * Get the base name for the page
   * mostly used for the base name of the output file
   * later on
   * @return String base name
   */
  public String getBaseName() {
    return (this.baseName);
  }

  /**
   * set the URI for a CSS cascading style sheet reference
   * which will go in the document header section
   * this is optional
   *
   * @param URI refering some CSS
   */
  public void setCssUri(String uri) {
    this.cssUri = uri;
  }

  // ----------------------------------------------------  protected methods

  /**
   * starts a new document
   */
  protected void newDocument() {

    // start a new document
    DOMImplementation domImpl = this.dBuilder.getDOMImplementation();

    final DocumentType dtype = domImpl.createDocumentType("html",
        NavaDocConstants.XHTML_PUBLICID,
        NavaDocConstants.XHTML_SYSTEMID);
    this.dom = domImpl.createDocument(
        NavaDocConstants.XHTML_NAMESPACE, "html", dtype);
    this.root = this.dom.getDocumentElement();
  }

  /**
   * Set the basic headers for this HTML DOM
   *
   * @param String title of the document to go into the header
   */
  protected void setHeaders(String t) {

    Comment cvsId = this.dom.createComment(
        " $Id$ ");

    //this.root.appendChild(cvsId);

    this.root.setAttribute("class", "navadoc");
    this.root.setAttribute("xmlns", "http://www.w3.org/1999/xhtml");

    Element header = this.dom.createElement("head");

    Element metaGen = this.dom.createElement("meta");

    metaGen.setAttribute("name", "generator");
    metaGen.setAttribute("content",
                         this.filterDollarSigns(NavaDoc.vcIdent));
    header.appendChild(metaGen);

    Element title = this.dom.createElement("title");
    Text tText = this.dom.createTextNode(t);

    title.appendChild(tText);
    header.appendChild(title);

    if ( (this.cssUri != null) && (this.cssUri.length() > 0)) {
      Element css = this.dom.createElement("link");

      css.setAttribute("rel", "stylesheet");
      css.setAttribute("type", "text/css");
      css.setAttribute("href", this.cssUri);
      header.appendChild(css);
    }

    root.appendChild(header);

  } // protected void setHeaders()

  // adds body node to the document when we're ready
  protected void addBody(String cname) {

    this.body = this.dom.createElement("body");
    body.setAttribute("class", cname);
    this.root.appendChild(body);

  } // protected void addBody()

  // utility for filtering $'s out of CVS ID strings
  protected String filterDollarSigns(String s) {
    StringBuffer sb = new StringBuffer(s);
    int i = 0;

    while ( (i = s.indexOf('$')) > -1) {
      sb.deleteCharAt(i);
      s = sb.toString();
    }
    return (sb.toString());
  } // protected String filterDollarSigns()

} // public class NavaDocBaseDOM
// EOF: $RCSfile$ //