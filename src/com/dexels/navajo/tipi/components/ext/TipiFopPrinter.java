package com.dexels.navajo.tipi.components.ext;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.*;
import java.awt.print.*;
import org.apache.fop.apps.*;
import org.apache.fop.layout.*;
import org.apache.fop.render.awt.*;
import org.xml.sax.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiFopPrinter
    extends TipiComponentImpl {
  private String myPath = null;
  private String myMode = null;
  private boolean showPreview = false;
  public TipiFopPrinter() {
  }

  public Object createContainer() {
    return null;
  }

  protected void performComponentMethod(String name, TipiComponentMethod compMeth) {
    if (name.equals("print")) {
      Operand path = compMeth.getEvaluatedParameter("printpath");
      Operand xsltFile = compMeth.getEvaluatedParameter("xsltFile");
      Message m = (Message)myContext.parse(this,"message",path.value.toString());
      /** @todo BEware. Unclear. Maybe already evaluated */
//      getMessageByPath(path.value.toString());
      printMessage(m, getXsltStream(xsltFile.value.toString()));
    }
    if (name.equals("printValue")) {
      Operand path = compMeth.getEvaluatedParameter("printpath");
      Operand xsltFile = compMeth.getEvaluatedParameter("xsltFile");
      Operand valueName = compMeth.getEvaluatedParameter("valueName");
      System.err.println("PrintPath: " + path.value);
      System.err.println("XSLT     : " + xsltFile.value);
      System.err.println("Parsed path");
      printMessage( (Message) path.value, getXsltStream(xsltFile.value.toString()));
    }
  }

  public InputStream getXsltStream(String s) {
    System.err.println("Looking for xslt file: " + s);
    URL u = getClass().getClassLoader().getResource(s);
    System.err.println("Url loaded: " + u);
    try {
      return u.openStream();
    }
    catch (IOException ex) {
      System.err.println("Opening failed!");
      return null;
    }
  }

  public void showPrintPreview(boolean state) {
    showPreview = state;
  }

  public void printMessage(Message m, String xsltUrl) {
    printMessage(m, getXsltStream(xsltUrl));
  }

  public void printMessage(Message m, InputStream is) {
    try {
      FileWriter mess = new FileWriter("c:/message.xml");
      m.write(mess);
      mess.flush();
      mess.close();
      PrinterJob printJob = PrinterJob.getPrinterJob();
      PrinterJob pj = PrinterJob.getPrinterJob();
      PrintRenderer renderer = new PrintRenderer(pj);
      StringWriter sw = new StringWriter();
      Transformer transformer = SAXTransformerFactory.newInstance().newTransformer(new StreamSource(is));
      com.dexels.navajo.document.nanoimpl.XMLElement elmnt = (com.dexels.navajo.document.nanoimpl.XMLElement) m.getRef();
      transformer.setOutputProperty("indent", "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.transform(new StreamSource(new StringReader(elmnt.toString())), new StreamResult(sw));
      // DEBUG write the FOP file to c:/fop.fo
      FileWriter fw = new FileWriter("c:/fop.fo");
      fw.write(sw.toString());
      fw.flush();
      fw.close();
      // The actual printing is done here
      Driver driver = new Driver();
      driver.setInputSource(new InputSource(new StringReader(sw.toString())));
      if (showPreview) {
      }
      else {
        if (!printJob.printDialog()) {
          return;
        }
        driver.setRenderer(renderer);
        driver.run();
      }
      sw = null;
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public class PrintRenderer
      extends AWTRenderer {
    private static final int EVEN_AND_ALL = 0;
    private static final int EVEN = 1;
    private static final int ODD = 2;
    private int startNumber;
    private int endNumber;
    private int mode = EVEN_AND_ALL;
    private int copies = 1;
    private PrinterJob printerJob;
    PrintRenderer(PrinterJob printerJob) {
      super(null);
      this.printerJob = printerJob;
      startNumber = 0;
      endNumber = -1;
      printerJob.setPageable(this);
      mode = EVEN_AND_ALL;
      String str = System.getProperty("even");
      if (str != null) {
        try {
          mode = Boolean.valueOf(str).booleanValue() ? EVEN : ODD;
        }
        catch (Exception e) {}
      }
    }

    public void stopRenderer(OutputStream outputStream) throws IOException {
      super.stopRenderer(outputStream);
      if (endNumber == -1) {
        endNumber = getPageCount();
      }
      Vector numbers = getInvalidPageNumbers();
      for (int i = numbers.size() - 1; i > -1; i--) {
        removePage(Integer.parseInt( (String) numbers.elementAt(i)));
      }
      try {
        printerJob.print();
      }
      catch (PrinterException e) {
        e.printStackTrace();
        throw new IOException(
            "Unable to print: " + e.getClass().getName() +
            ": " + e.getMessage());
      }
    }

    public void renderPage(Page page) {
      pageWidth = (int) ( (float) page.getWidth() / 1000f);
      pageHeight = (int) ( (float) page.getHeight() / 1000f);
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
        }
        else if (mode != EVEN_AND_ALL) {
          if (mode == EVEN && ( (i + 1) % 2 != 0)) {
            isValid = false;
          }
          else if (mode == ODD && ( (i + 1) % 2 != 1)) {
            isValid = false;
          }
        }
        if (!isValid) {
          vec.add(i + "");
        }
      }
      return vec;
    }
  }
}
