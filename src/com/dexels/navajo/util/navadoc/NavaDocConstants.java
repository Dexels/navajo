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

  // attribute names
  public static final String NAME_ATTR = "name";

  // path properties
  public static final String SVC_PATH_PROPERTY = "services-path";
  public static final String STYLE_PATH_PROPERTY = "stylesheet-path";
  public static final String TARGET_PATH_PROPERTY = "target-path";

  // Output and Indent Properties
  public static final String OUTPUT_METHOD_PROP = "method";
  public static final String OUTPUT_METHOD_VALUE = "xhtml";
  public static String
    INDENT_AMOUNT = "{http://xml.apache.org/xslt}indent-amount";
  public static final String INDENT = "indent";


} // public class NavaDocConstants

// EOF: $RCSfile$ //
