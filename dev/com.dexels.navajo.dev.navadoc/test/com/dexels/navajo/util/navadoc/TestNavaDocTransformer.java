package com.dexels.navajo.util.navadoc;

import junit.framework.*;

import com.dexels.navajo.util.navadoc.*;

// XML stuff
import org.w3c.dom.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.parsers.ParserConfigurationException;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import com.dexels.navajo.util.navadoc.config.*;
import java.io.File;

public class TestNavaDocTransformer
    extends TestCase {

  public static final Logger logger =
      Logger.getLogger(NavaDoc.class.getName());

  NavaDocTestFixture fixture = null;

  private NavaDocConfigurator config =
      new NavaDocConfigurator();
  private DocumentSet dset;
  private File svcPath;
  private File styleSheet;

  public TestNavaDocTransformer(String s) {
    super(s);
    try {
      this.fixture = new NavaDocTestFixture(this);
    }
    catch (Exception e) {
      fail("failed to set-up fixture: " + e);
    }
  }

  protected void setUp() {
    fixture.setUp();
    try {
      config.configure();
      this.dset = (DocumentSet) config.getDocumentSetMap().get("Test Project");
      this.svcPath = dset.getPathConfiguration().getPath(NavaDocConstants.
          SVC_PATH_ELEMENT);
      this.styleSheet = dset.getPathConfiguration().getPath(NavaDocConstants.
          STYLE_PATH_ELEMENT);
    }
    catch (ConfigurationException ce) {
      this.fail(this.getClass() + "cannot configure: " +
                ce);
    }
  }

  protected void tearDown() {
    fixture.tearDown();
  }

  public void testGetTransformer() throws TransformerConfigurationException,
      ParserConfigurationException {

    logger.log(Priority.DEBUG,
        "testing NavaDocTransformer to get a fully realized XML Transformer");

    final DocumentSet dset = (DocumentSet)this.config.getDocumentSetMap().get(
        "Test Project");
    final File targetPath = dset.getPathConfiguration().getPath(
        NavaDocConstants.
        TARGET_PATH_ELEMENT);
    NavaDocTransformer transformer = new NavaDocTransformer(this.styleSheet,
        this.svcPath);

    Transformer t = transformer.getTransformer();

    this.assertEquals("xml", t.getOutputProperty("method"));

  }

  public void testTransformWebService() throws
      TransformerConfigurationException,
      ParserConfigurationException {

    logger.log(Priority.DEBUG,
               "testing NavaDocTransformer ability to transform web service");

    NavaDocTransformer transformer = new NavaDocTransformer( this.styleSheet, this.svcPath );

    transformer.transformWebService("euro");
    Document d = transformer.getDocument();
    NodeList nList = d.getElementsByTagName("span");

    assertEquals(2, nList.getLength());

  }

  public void testTransformWebServiceWithCss() throws
      TransformerConfigurationException,
      ParserConfigurationException {

    logger.log(Priority.DEBUG, "testing NavaDocTransformer with option CSS URI");

    NavaDocTransformer transformer = new NavaDocTransformer( this.styleSheet, this.svcPath );

    transformer.setCssUri("./am/I/stupid.css");
    transformer.transformWebService("euro");
    Document d = transformer.getDocument();
    NodeList nList = d.getElementsByTagName("link");
    Element e = (Element) nList.item(0);

    assertEquals("stylesheet", e.getAttribute("rel"));

  }

  public void testGetNotesFromBPFL() throws TransformerConfigurationException,
      ParserConfigurationException {

    logger.log(Priority.DEBUG, "testing get notes method, BPFL");

    NavaDocTransformer transformer = new NavaDocTransformer( this.styleSheet, this.svcPath );

    transformer.transformWebService("euro");
    String s = transformer.getNotes();

    assertEquals("Euro Calculator", s.substring(0, 15));

  }

  public void testGetNotesFromBPCL() throws TransformerConfigurationException,
      ParserConfigurationException {

    logger.log(Priority.DEBUG, "testing get notes method, BPCL");

    NavaDocTransformer transformer = new NavaDocTransformer( this.styleSheet, this.svcPath );

    transformer.transformWebService("ProcessBirthdateQueryMembers");
    String s = transformer.getNotes();

    assertEquals("Mit WEB.DE", s.substring(0, 10));

  }

  public void testSetIndent() throws TransformerConfigurationException,
      ParserConfigurationException {

    logger.log(Priority.DEBUG, "test setting the indent output property");

    NavaDocTransformer transformer = new NavaDocTransformer( this.styleSheet, this.svcPath );
    transformer.setIndent( this.dset.getProperty( NavaDocConstants.INDENT));

    assertTrue("indentation should be true",
               transformer.shouldIndent());
  }

} // public class TestNavaDocTransformer