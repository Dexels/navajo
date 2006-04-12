package com.dexels.navajo.util.navadoc;

/**
 * <p>Title: NavaDocOuputter</p>
 * <p>Description: Responsible for outputting the results
 * document somewhere, usually the filesystem</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author Matthew Eichler
     * @version $Id$
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NavaDocOutputter {

  public static final String vcIdent = "$Id$";


  private NavaDocBaseDOM dom = null;

  // paths
  private File targetPath = null;
  private File targetFile = null;

  // -------------------------------------------------------------- constructors

  /**
   * Contructs a NavaDocOutputter based on the current transformation
   * result
   *
   * @param a NavaDocTransformer which contains the result DOM
   * @param File path to target directory where output goes
   */

  public NavaDocOutputter(NavaDocBaseDOM d, File p) {

    this.dom = d;
    this.targetPath = p;
    System.err.println("About to create file: " + targetPath + File.separator + dom.getBaseName() + ".html");
    this.targetFile = new File(this.targetPath + File.separator +  this.dom.getBaseName() + ".html");
    targetFile.getParentFile().mkdirs();
    this.output();

  } // public NavaDocOutputter()

  // for writing to servlet output

  public NavaDocOutputter(final NavaDocBaseDOM d, final PrintWriter out)   {
    this.dom = d;
    try {
    	String result = toString ( this.dom.getDocument().getDocumentElement() );
    	out.write( result );
    	out.close();
    } catch ( Exception ex ) {
      ex.printStackTrace(System.err);
    }
  }

  // ------------------------------------------------------------ public methods

  /**
   * Returns the full path of the target output
   * as a File object.  Convenient for testing
   *
   * @return outputted target File path
   */

  public File getTargetFile() {
    return (this.targetFile);
  } // public File getTargetFile()

  // ----------------------------------------------------------- private methods
  
  private static String printElement(Node n) {

      if (n == null)
          return "";

      if (n instanceof Element) {

          StringBuffer result = new StringBuffer();
          String tagName = n.getNodeName();

          result.append("<" + tagName);
          NamedNodeMap map = n.getAttributes();

          if (map != null) {
              for (int i = 0; i < map.getLength(); i++) {
                  result.append(" ");
                  Attr attr = (Attr) map.item(i);
                  String name = attr.getNodeName();
                  String value = attr.getNodeValue();

                  result.append(name + "=\"" + value + "\"");
              }
          }
          NodeList list = n.getChildNodes();

          if (list.getLength() > 0)
              result.append(">\n");
          else
              result.append("/>\n");

          for (int i = 0; i < list.getLength(); i++) {
              Node child = list.item(i);

              result.append(printElement(child));
          }
          if (list.getLength() > 0)
              result.append("</" + tagName + ">\n");
          return result.toString();
      } else {
          return n.getNodeValue();
      }
  }

  public static String toString(Node n) {
      StringBuffer result = new StringBuffer();

      result.append(printElement(n));
      return result.toString();
  }
  
  /**
   * outputs the resulting DOM to a file in the target directory
   */
  private void output() {

    // Instantiate an Xalan XML serializer and use it
    // to serialize the output DOM to a file
    // using a default output format.

    try {
      FileWriter fw = new FileWriter(targetFile);
      String result = toString ( dom.getDocument().getDocumentElement() );
      fw.write( result );
      fw.close();

    }
    catch (IOException ioe) {
      
    }
  } // private void output()

} // public class NavaDocOutputter
// EOF: $RCSfile$ //