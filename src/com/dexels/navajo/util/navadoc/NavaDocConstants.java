package com.dexels.navajo.util.navadoc;

/**
 * <p>Title: NavaDoc</p>
 * <p>Description: Navajo Web Services Automated Documentation Facility
 * useful constants</p>
 * <p>Copyright: Copyright (c) 2002 - 2003</p>
 * <p>Company: Dexels BV</p>
 * @author Matthew Eichler meichler@dexels.com
 * @version $Id$
 */

public class NavaDocConstants {

  private NavaDocConstants() {
  }

  // core stuff
  public static final String CONFIGURATION_ELEMENT = "configuration";
  public static final String LOG4JCONFIG_ELEMENT = "log4j:configuration";
  public static final String DOCSET_ELEMENT = "document-set";
  public static final String DESC_ELEMENT = "description";
  public static final String PATH_ELEMENT = "path";
  public static final String PROPERTY_ELEMENT = "property";

  public static final String CSS_URI_PROPERTY = "css-uri";
  public static final String BASE_URI_PROPERTY = "base-uri";

  public static final String BASE_SYS_PROPERTY = "base";

  // attribute names
  public static final String NAME_ATTR = "name";
  public static final String DEFAULT_ATTR = "default";
  public static final String VALUE_ATTR = "value";
  public static final String BASE_ATTR = "base";
  public static final String NOTES_ATTR = "notes";

  // path properties
  public static final String SVC_PATH_ELEMENT = "services";
  public static final String STYLE_PATH_ELEMENT = "stylesheet";
  public static final String TARGET_PATH_ELEMENT = "target";

  public static final String NAVASCRIPT_EXT = "xml";

  // Output and Indent Properties
  public static final String XHTML_PUBLICID = "-//W3C//DTD XHTML 1.0 Strict//EN";
  public static final String XHTML_SYSTEMID = "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd";
  public static final String XHTML_NAMESPACE="http://www.w3.org/1999/xhtml";
  public static final String OUTPUT_METHOD_PROP = "method";
  public static final String OUTPUT_METHOD_VALUE = "xhtml";
  public static String
    INDENT_AMOUNT = "{http://xml.apache.org/xslt}indent-amount";
  public static final String INDENT = "indent";
  public static final String DEFAULT_INDENT_AMOUNT = "2";

  public static final String DEFAULT_BASE_URI = "./";
  public static final String DEFAULT_CSS = "stylesheet.css";

  // Web
  public static final String WEB_CONTENT_TYPE = "text/html; charset=UTF-8";
  public static final String WEB_CONFIG_INITPARAM = "configuration";
  public static final String WEB_BASE_INITPARAM = "base";
  public static final String WEB_SNAME_PARAMETER = "sname";
  public static final String WEB_SET_PARAMETER = "set";

} // public class NavaDocConstants

// EOF: $RCSfile$ //
