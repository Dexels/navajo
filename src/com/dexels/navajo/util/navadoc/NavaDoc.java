package com.dexels.navajo.util.navadoc;

/**
 * <p>Title: NavaDoc</p>
 * <p>Description: Automated documentation utility for Navajo Web Services</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Matthew Eichler
 * @version $Revision$
 */
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.dexels.navajo.util.navadoc.config.ConfigurationException;
import com.dexels.navajo.util.navadoc.config.DocumentSet;
import com.dexels.navajo.util.navadoc.config.NavaDocConfigurator;

public class NavaDoc {

  public static final String vcIdent =
      "$Id$";

  public static final Logger logger =
      Logger.getLogger(NavaDoc.class.getName());

  private NavaDocConfigurator config = new NavaDocConfigurator();

  private ServicesList list = null;
  private NavaDocTransformer transformer = null;
  private NavaDocIndexDOM index = null;

  /**
   * Outside mediator object which controls all the
   * inner documentation objects, looping through the
   * list of services and generating HTML content.
   * NavaDoc also keeps a nice index page of all the services
   * found
   *
   * @throws ConfigurationException when required configuration
   * objects are wrong or don't exist
   */

  public NavaDoc() throws ConfigurationException {

    config.configure();

    final Map setMap = config.getDocumentSetMap();
    final Set keys = setMap.keySet();
    final Iterator iter = keys.iterator();
    while (iter.hasNext()) {
      final String name = (String) iter.next();
      final DocumentSet dset = (DocumentSet) setMap.get(name);
      NavaDoc.logger.log(Priority.DEBUG,
                      "working on documentation for set named '" +
                      name + "'");

      // set-up an index DOM
      try {
        this.index = new NavaDocIndexDOM(dset);
      }
      catch (ParserConfigurationException ex) {
        throw new ConfigurationException(ex.toString(),
                                         this.config.getConfigUri());
      }

      // document this directory
      this.setTransformer(dset);
      this.document(dset);

      // output index pages
      NavaDocOutputter idxOut =
          new NavaDocOutputter(this.index,
                               dset.getPathConfiguration().getPath(
          NavaDocConstants.
          TARGET_PATH_ELEMENT));
    }

  } // public NavaDoc()

  /**
   * Provides a count of all the web services found based
   * on the configuration.  This was useful for testing
   * purposes
   *
   * @return int count of web services
   */

  public int count() {
    return (this.list.size());
  } // public int count()

  public static void main(String[] args) throws ConfigurationException {

    // get some basic logging started
    BasicConfigurator.configure();
    Logger.getLogger(NavaDoc.class).log(Priority.DEBUG, "basic logging started");

    final NavaDoc documenter = new NavaDoc();

    logger.log(Priority.INFO, "finished");

  }

  // ----------------------------------------------------------- private methods

  private void setTransformer(final DocumentSet dset) throws
      ConfigurationException {

    final File sPath = dset.getPathConfiguration().getPath(NavaDocConstants.
        SVC_PATH_ELEMENT);
    final File styleSheet = dset.getPathConfiguration().getPath(
        NavaDocConstants.STYLE_PATH_ELEMENT);
    final String indent = (dset.getProperty(NavaDocConstants.INDENT) != null) ?
        dset.getProperty(NavaDocConstants.INDENT) :
        NavaDocConstants.DEFAULT_INDENT_AMOUNT;
    final String cssUri = dset.getProperty("css-uri");

    try {
      this.transformer = new NavaDocTransformer(
          styleSheet, sPath, indent);
      this.list = new ServicesList(sPath);

      // set optional parameters, nulls OK
      this.transformer.setProjectName(dset.getName());
      this.transformer.setCssUri(cssUri);

    }
    catch (Exception e) {
      throw new ConfigurationException(e.getMessage(), this.config.getConfigUri());

    }

  }

  /**
   * does all the work of going through the list of web
   * services and using the transformer to generate the
   * documentation.  There will be a single document combining
   * BPFL and BPCL documentation for a logical web service.
   * A link in the index page is created after each service
   * has an HTML page generated.
   */

  private void document(final DocumentSet dset) {

    final File tPath = dset.getPathConfiguration().getPath(NavaDocConstants.
        TARGET_PATH_ELEMENT);
    final File sPath = dset.getPathConfiguration().getPath(NavaDocConstants.
        SVC_PATH_ELEMENT);
    final Iterator iter = this.list.iterator();

    while (iter.hasNext()) {

      final String sname = (String) iter.next();

      this.transformer.transformWebService(sname);
      NavaDocOutputter outputter =
          new NavaDocOutputter(this.transformer, tPath);

      this.index.addEntry(sname, this.transformer.getNotes());
    }

  } // private void document()

} // public class NavaDoc
// EOF: $RCSfile$ //