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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexels.navajo.util.navadoc.config.PathConfig;

import sun.security.action.GetIntegerAction;

//import com.dexels.navajo.server.listener.soap.wsdl.Generate;

public class NavaDocOutputter {

  public static final String vcIdent = "$Id$";


  private NavaDocBaseDOM dom = null;


  // paths
  private File targetPath = null;
  private File targetFile = null;
  private File targetFileIn = null;
  private File targetFileOut = null;

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
//    System.err.println("About to create file: " + targetPath + File.separator + dom.getBaseName() + ".html");
    this.targetFile = new File(this.targetPath + File.separator +  this.dom.getBaseName() + ".html");
    if ( dom.domIn != null ) {
    	this.targetFileIn = new File(this.targetPath + File.separator +  this.dom.getBaseName() + "_input.html");
    }
    if ( dom.domOut != null ) {
    	this.targetFileOut = new File(this.targetPath + File.separator +  this.dom.getBaseName() + "_output.html");
    }
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
    	    	  
    	    this.targetFile = new File( d.baseUri, "doc" + File.separator +  this.dom.getBaseName() + ".html");
    	    if ( dom.domIn != null ) {
    	    	this.targetFileIn = new File( d.baseUri, "doc" + File.separator +  this.dom.getBaseName() + "_input.html");
    	    	this.targetFileIn.getParentFile().mkdirs();
    	    }
    	    if ( dom.domOut != null ) {
    	    	this.targetFileOut = new File( d.baseUri, "doc" + File.separator +  this.dom.getBaseName() + "_output.html");
    	    	this.targetFileOut.getParentFile().mkdirs();
    	    }
    	    targetFile.getParentFile().mkdirs();
    	    this.output();    
    	    
    	    if ( out != null ) {
    	    	BufferedReader bf = new BufferedReader( new FileReader(this.targetFile) );
    	    	String l = null;
    	    	while ( ( l = bf.readLine() ) != null  ) {
    	    		out.write(l);
    	    	}
    	    	bf.close();
    	    	out.close();
    	    }
    	    
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
      
      if ( dom.domIn != null ) {
    	  fw = new FileWriter(targetFileIn);
          result = toString (  dom.domIn.getDocumentElement() );
          fw.write( result );
          fw.close();
      }
      
      if ( dom.domOut != null ) {
    	  fw = new FileWriter(targetFileOut);
          result = toString (  dom.domOut.getDocumentElement() );
          fw.write( result );
          fw.close();
      }
    }
    catch (IOException ioe) {
      
    }
  } // private void output()

} // public class NavaDocOutputter
// EOF: $RCSfile$ //