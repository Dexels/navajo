package com.dexels.navajo.tipi.components;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
import com.dexels.navajo.document.*;
import java.awt.*;
import com.dexels.navajo.tipi.tipixml.*;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.*;

import org.apache.fop.apps.Driver;
import javax.xml.transform.sax.SAXResult;
import java.io.StringWriter;
import java.awt.print.PrinterJob;
import java.awt.print.PrinterException ;

import org.apache.fop.render.PrintRenderer;
import org.apache.fop.render.awt.AWTRenderer;
import org.apache.fop.layout.Page;
import org.apache.fop.apps.Version;
import org.apache.fop.apps.XSLTInputHandler;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;
import org.w3c.dom.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PrintComponent extends com.dexels.navajo.tipi.TipiComponent {
  private String myPath = null;
  private String myMode = null;
  private boolean showPreview = false;

  public PrintComponent() {
  }
  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
  }
  public void removeFromContainer(Component c) {
    throw new UnsupportedOperationException("Can not remove from container of class: "+getClass());
  }
  public void registerEvents() {
    /**@todo Implement this com.dexels.navajo.tipi.TipiComponent abstract method*/
  }
  public Container createContainer() {
    return null;
  }
  public void setComponentValue(String name, Object object) {
    if (name.equals("path")) {
    }
    if (name.equals("mode")) {
    }
    System.err.println("Ignored for now");
  }
  public Object getComponentValue(String name) {
    /**@todo Override this com.dexels.navajo.tipi.TipiComponent method*/
    return super.getComponentValue(name);
  }
  protected void performComponentMethod(String name, XMLElement invocation, TipiComponentMethod compMeth) {

    if (name.equals("print")) {

      //System.err.println("INVOCATION: "+invocation.toString());
      TipiMethodParameter path = compMeth.getParameter("printpath");
      TipiMethodParameter xsltFile = compMeth.getParameter("xsltFile");

      Message m = myContext.getMessageByPath(path.getValue());
      printMessage(m,getXsltStream(xsltFile.getValue()));
    }

    if(name.equals("printValue")) {
      //System.err.println("INVOCATION: "+invocation.toString());
      TipiMethodParameter path = compMeth.getParameter("printpath");
      TipiMethodParameter xsltFile = compMeth.getParameter("xsltFile");
      TipiMethodParameter valueName = compMeth.getParameter("valueName");
      System.err.println("PrintPath: " + path);
      System.err.println("XSLT     : " + xsltFile);
      TipiPathParser pp = new TipiPathParser((TipiComponent)this, myContext, path.getValue());
      System.err.println("Parsed path");
      Message m = pp.getMessage();
      printMessage(m,getXsltStream(xsltFile.getValue()));
    }
  }

  public InputStream getXsltStream(String s) {
    System.err.println("Looking for xslt file: "+s);
    URL u = getClass().getClassLoader().getResource(s);
    System.err.println("Url loaded: "+u);
    try {
      return u.openStream();
    }
    catch (IOException ex) {
      System.err.println("Opening failed!");
      return null;
    }
  }

  public void showPrintPreview(boolean state){
    showPreview = state;
  }

  public void printMessage(Message m, String xsltUrl) {
    printMessage(m,getXsltStream(xsltUrl));
  }

  public void printMessage(Message m, InputStream is) {
    try {
      PrinterJob printJob = PrinterJob.getPrinterJob ();
      PrinterJob    pj       = PrinterJob.getPrinterJob();
      PrintRenderer renderer = new PrintRenderer(pj);
      StringWriter sw = new StringWriter();
      Transformer  transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(is));
      //System.err.println("m.getRef(): " + m.getRef().getClass());
      com.dexels.navajo.document.nanoimpl.XMLElement elmnt = (com.dexels.navajo.document.nanoimpl.XMLElement) m.getRef();
      transformer.setOutputProperty("indent","yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.transform(new StreamSource(new StringReader(elmnt.toString())), new StreamResult(sw));

      // DEBUG write the FOP file to c:/fop.fo
      FileWriter fw = new FileWriter("c:/fop.fo");
      transformer.transform(new StreamSource(new StringReader(elmnt.toString())), new StreamResult(fw));
      fw.flush();
      fw.close();
      // The actual printing is done here
      Driver        driver   = new Driver();
      driver.setInputSource(new InputSource(new StringReader(sw.toString())));
      if(showPreview){

        //Runtime.getRuntime().exec("c:/fop-0.20.5rc3a/Fop.bat -fo c:/fop.fo -awt");
      }else{
        if (!printJob.printDialog()) {
         return;
       }
       driver.setRenderer(renderer);
       driver.run();
      }
     }
     catch (Exception ex) {
       ex.printStackTrace();
     }
  }

  public class PrintRenderer extends AWTRenderer
  {

      private static final int EVEN_AND_ALL = 0;
      private static final int EVEN = 1;
      private static final int ODD = 2;

      private int startNumber;
      private int endNumber;
      private int mode = EVEN_AND_ALL;
      private int copies = 1;
      private PrinterJob printerJob;

      PrintRenderer(PrinterJob printerJob)
      {
        super(null);

        this.printerJob = printerJob;
        startNumber =  0 ;
        endNumber   = -1;

        printerJob.setPageable(this);

        mode = EVEN_AND_ALL;
        String str = System.getProperty("even");
        if (str != null)
          {
            try
              {
                mode = Boolean.valueOf(str).booleanValue() ? EVEN : ODD;
              }
            catch (Exception e)
              {}

          }

      }

      public void stopRenderer(OutputStream outputStream)
      throws IOException {
          super.stopRenderer(outputStream);

          if(endNumber == -1)
              endNumber = getPageCount();

          Vector numbers = getInvalidPageNumbers();
          for (int i = numbers.size() - 1; i > -1; i--)
              removePage(Integer.parseInt((String)numbers.elementAt(i)));

          try {
              printerJob.print();
          } catch (PrinterException e) {
              e.printStackTrace();
              throw new IOException(
                  "Unable to print: " + e.getClass().getName() +
                  ": " + e.getMessage());
          }
      }

      public void renderPage(Page page) {
          pageWidth = (int)((float)page.getWidth() / 1000f);
          pageHeight = (int)((float)page.getHeight() / 1000f);
          super.renderPage(page);
      }


      private Vector getInvalidPageNumbers() {

          Vector vec = new Vector();
          int max = getPageCount();
          boolean isValid;
          for (int i = 0; i < max; i++) {
              isValid = true;
              if (i < startNumber || i > endNumber) {
                  isValid = false;
              } else if (mode != EVEN_AND_ALL) {
                  if (mode == EVEN && ((i + 1) % 2 != 0))
                      isValid = false;
                  else if (mode == ODD && ((i + 1) % 2 != 1))
                      isValid = false;
              }

              if (!isValid)
                  vec.add(i + "");
          }

          return vec;
      }
  }    // class PrintRenderer

}