package com.dexels.navajo.tipi.components.swingimpl;

import java.lang.reflect.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.internal.*;
import java.awt.event.*;
import java.awt.print.*;
import java.awt.geom.*;
import com.dexels.navajo.document.*;
import java.awt.image.*;
import com.dexels.navajo.swingclient.*;
import com.dexels.navajo.tipi.components.swingimpl.tipimegatable.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiSwingDataComponentImpl
    extends TipiDataComponentImpl
    implements TipiSwingComponent, Pageable, Printable {
  private int gridsize = 10;
  private Object result = null;
  protected TipiPopupMenu myPopupMenu = null;
  private PageFormat pf = null;
  private String myHeader = null;
  private String myFooter = null;
  private int currentPage = -1;
  private double scale = 0;
  private int pageCount = -1;
  public void initContainer() {
    if (getContainer() == null) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          setContainer(createContainer());
        }
      });
    }
  }

  public void setWaitCursor(boolean b) {
    Container cc = (Container) getSwingContainer();
    if (cc != null) {
       (cc).setCursor(b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
    }
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent current = getTipiComponent(i);
      if (TipiSwingComponent.class.isInstance(current)) {
         ( (TipiSwingComponent) current).setWaitCursor(b);
      }
    }
  }

  public void addToContainer(final Object c, final Object constraints) {
    getSwingContainer().add( (Component) c, constraints);
  }

  public void removeFromContainer(final Object c) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        getSwingContainer().remove( (Component) c);
      }
    });
  }

  public void setContainerLayout(final Object layout) {
    runSyncInEventThread(new Runnable() {
      public void run() {
         ( (Container) getContainer()).setLayout( (LayoutManager) layout);
      }
    });
  }

  public void highLight(Component c, Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.red);
    g2.setStroke(new BasicStroke(3.0f));
    Dimension d = c.getSize();
//    Rectangle r = c.getBounds();
//    g2.drawRect(r.x + 1, r.y + 1, r.width - 2, r.height - 2);
    int inset = 5;
    g2.drawRect(inset, inset, d.width - 2 * inset, d.height - 2 * inset);
    g2.setStroke(new BasicStroke(1.0f));
  }

  public void setCursor(Cursor c) {
    if (getSwingContainer() != null) {
      getSwingContainer().setCursor(c);
    }
  }

  protected Object getComponentValue(String name) {
    if (name != null) {
      if (name.equals("currentPage")) {
        return new Integer(currentPage);
      }
    }
    return super.getComponentValue(name);
  }

  public void print(Printable p) {
    if (getSwingContainer() != null) {
      PrinterJob printJob = PrinterJob.getPrinterJob();
      printJob.setPrintable(p);
      if (printJob.printDialog()) {
        try {
          printJob.print();
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      if (printJob.printDialog()) {
        try {
          printJob.print();
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }

  public void replaceLayout(TipiLayout tl) {
    super.replaceLayout(tl);
    ( (Container) getContainer()).repaint(); if (JComponent.class.isInstance(getContainer())) {
       ( (JComponent) getContainer()).revalidate();
    }
  }

  public void showPopup(MouseEvent e) {
     ( (JPopupMenu) myPopupMenu.getSwingContainer()).show(getSwingContainer(), e.getX(), e.getY());
  }

  protected void doLayout() {
    getContext().debugLog("data    ", "Entering doLayout in tipi: " + getId());
    if (getContainer() != null) {
      if (JComponent.class.isInstance(getContainer())) {
        runASyncInEventThread(new Runnable() {
          public void run() {
            getContext().debugLog("data    ", "Entering doLayout in tipi: " + getId());
            ( (JComponent) getContainer()).revalidate(); ( (JComponent) getContainer()).repaint(); getContext().debugLog("data    ", "Exiting doLayout in tipi: " + getId());
          }
        });
      }
    }
    getContext().debugLog("data    ", "Exiting doLayout in tipi: " + getId());
  }

  public Object getContainerLayout() {
    return getSwingContainer().getLayout();
  }

  public Container getSwingContainer() {
    return (Container) getContainer();
  }

  public void refreshLayout() {
    ArrayList elementList = new ArrayList();
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent current = (TipiComponent) getTipiComponent(i);
      if (current.isVisibleElement()) {
        removeFromContainer(current.getContainer());
      }
      elementList.add(current);
    }
    for (int i = 0; i < elementList.size(); i++) {
      final TipiComponent current = (TipiComponent) elementList.get(i);
      if (current.isVisibleElement()) {
        runSyncInEventThread(new Runnable() {
          public void run() {
            addToContainer(current.getContainer(), current.getConstraints());
          }
        });
      }
    }
  }

  public void runSyncInEventThread(Runnable r) {
    if (SwingUtilities.isEventDispatchThread()) {
      r.run();
    }
    else {
      try {
        SwingUtilities.invokeAndWait(r);
      }
      catch (InvocationTargetException ex) {
        throw new RuntimeException(ex);
      }
      catch (InterruptedException ex) {
        System.err.println("Interrupted");
      }
    }
  }

  public void runASyncInEventThread(Runnable r) {
    if (SwingUtilities.isEventDispatchThread()) {
      r.run();
    }
    else {
      SwingUtilities.invokeLater(r);
    }
  }

  protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
    if ("print".equals(name)) {
      Operand printJob = compMeth.getEvaluatedParameter("printJob", event);
      String header = (String) compMeth.getParameter("header").getValue();
      String footer = (String) compMeth.getParameter("footer").getValue();
//    String header = headerOperand==null?"":(String)headerOperand.value;
//    String footer = footerOperand==null?"":(String)footerOperand.value;
      print( (PrinterJob) (printJob.value), header, footer);
    }
    if ("printpreview".equals(name)) {
      String header = (String) compMeth.getParameter("header").getValue();
      String footer = (String) compMeth.getParameter("footer").getValue();
      showPreview(header,footer);
    }
    super.performComponentMethod(name, compMeth, event);
  }

  public void showPreview(String header,String footer) {
    TipiPrintPreview tpp = new TipiPrintPreview(header,footer);
    tpp.setSwingDataComponent(this);
    JDialog jd = new JDialog(SwingClient.getUserInterface().getMainFrame());
    jd.getContentPane().add(tpp);
    jd.pack();
    ((SwingTipiUserInterface)SwingClient.getUserInterface()).showCenteredDialog(jd);
  }

  private int currentBreak = 0;

  private final double breakingRange=.8;

  private int calculateBreaks(Component myPanel, PageFormat pageFormat, double scale, ArrayList breaks) {
    int idx = 0;
//    Component myPanel = getSwingContainer();
    if (!(myPanel instanceof PageBreakable)) {
      System.err.println("Not pagebreakable. class: "+myPanel.getClass());
      return -1;
    }
    PageBreakable bp = (PageBreakable)myPanel;
    double height = myPanel.getHeight()*scale;
    int pageHeight = (int)pageFormat.getImageableHeight()-headOffset-footOffset;
    int pageBreakPoint = (int)(pageHeight * breakingRange);
    int div=0;
    int page = 0;
    System.err.println("panelHeight: "+myPanel.getHeight()+" = scaled: "+height+"+pageHeight: "+pageHeight+"  ");
    while (div<height && div!=-1) {
//      System.err.println("Current point: "+div);
//      System.err.println("Looking for a break between: "+(pageBreakPoint+idx)+" and: "+(pageHeight+idx));
      div = bp.getDivisionPoint(pageBreakPoint+idx,pageHeight+idx);
//      System.err.println("Dividing at: "+div);
      breaks.add(new Integer(div));
      idx+=div;
      page++;
    }
    return page;
  }

  protected Component getPrintingContainer() {
    return getSwingContainer();
  }

  private final ArrayList breaks = new ArrayList();

  private void updateScaling(PageFormat pf) {
    Component myPanel = getSwingContainer();
    System.err.println("imag: " + pf.getImageableX());
    System.err.println("myPanel: " + myPanel.getSize());
    System.err.println("Header: " + myHeader);
    System.err.println("Footer: " + myFooter);
    System.err.println("Width: "+myPanel.getWidth());
    System.err.println("Height: "+myPanel.getHeight());
    System.err.println("prefWidth: "+myPanel.getWidth());
    System.err.println("prefHeight: "+myPanel.getHeight());

    double xscale = pf.getImageableWidth() / myPanel.getWidth();
    double yscale = pf.getImageableHeight() / myPanel.getHeight();
    scale = xscale;
    breaks.clear();
    int pppage = calculateBreaks(getPrintingContainer(),pf,scale,breaks);



    double height = myPanel.getHeight() * scale;
    if (pppage > 0) {
      pageCount = pppage;
    } else {
      double count = height / pf.getImageableHeight();
      pageCount = (int) Math.ceil(count);
      System.err.println("My calculation indicate that you need: " + pageCount + " pages");
      System.err.println("xscale: " + xscale);
      System.err.println("yscale: " + yscale);
    }
  }

  public ImageIcon getPreview(String header, String footer, int page) {
    myHeader = header;
    myFooter = footer;
    PageFormat pf = new PageFormat();
    disableDoubleBuffering(getSwingContainer());

    pf.getPaper().setImageableArea(72,72,pf.getWidth()-144,pf.getHeight()-144);
    updateScaling(pf);

    BufferedImage bi = new BufferedImage((int)pf.getWidth(),(int)pf.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
//    bi.set
    Graphics2D g = (Graphics2D)bi.getGraphics();
    Color c = g.getColor();
    g.setColor(Color.white);
    g.fillRect(0,0,bi.getWidth(),bi.getHeight());
    g.setColor(Color.black);
    Stroke s = g.getStroke();
    g.setStroke(new BasicStroke(1,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER,1,new float[]{1f,0f},0));
    g.drawRect(72,72,(int)pf.getWidth()-144,(int)pf.getHeight()-144);
    g.setStroke(s);
    g.setColor(c);
    g.setClip(72,72,(int)pf.getWidth()-144,(int)pf.getHeight()-144);
    try {
      print(g, pf,page);
    }
    catch (PrinterException ex) {
      ex.printStackTrace();
    } finally {
      enableDoubleBuffering(getSwingContainer());

    }
    ImageIcon ii = new ImageIcon(bi);
    return ii;
  }

  public void print(PrinterJob printJob, String header, String footer) {
    if (printJob == null) {
      printJob = PrinterJob.getPrinterJob();
    }
    pf = printJob.defaultPage();
    disableDoubleBuffering(getSwingContainer());

    updateScaling(pf);
    myHeader = header;
    myFooter = footer;
    System.err.println("Starting printjob with header: " + header);
    System.err.println("Starting printjob with footer: " + footer);
    System.err.println("With # of pages: "+getNumberOfPages());
    printJob.setPageable(this);
    try {
      printJob.print();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      enableDoubleBuffering(getSwingContainer());
    }
  }


  public static void disableDoubleBuffering(Component c) {
    RepaintManager currentManager = RepaintManager.currentManager(c);
    currentManager.setDoubleBufferingEnabled(false);
  }

  public static void enableDoubleBuffering(Component c) {
    RepaintManager currentManager = RepaintManager.currentManager(c);
    currentManager.setDoubleBufferingEnabled(true);
  }


  int headOffset = 25;
    int footOffset = 25;


  public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
    Graphics2D g2d = (Graphics2D) graphics;
    System.err.println("printing page: " + pageIndex);
    if (pageIndex >= pageCount) {
      System.err.println("Not printing page: " + pageIndex);
      return Printable.NO_SUCH_PAGE;
    }
    // Count pages from one, instead of zero.
    currentPage = pageIndex + 1;
    Component myPanel = getSwingContainer();
    AffineTransform t = g2d.getTransform();
    AffineTransform t2 = t.getTranslateInstance(pageFormat.getImageableX(), pageFormat.getImageableY()+headOffset);
    Operand headerOp = evaluate(myHeader, this, null);
    Operand footerOp = evaluate(myFooter, this, null);
    String header = (String) (headerOp == null ? "" : "" + headerOp.value);
    String footer = (String) (footerOp == null ? "" : "" + footerOp.value);

    int currentbreak;

    if (pageIndex >= breaks.size()) {
      currentbreak = (int)pageFormat.getImageableHeight();
    } else {
      currentBreak = ((Integer)breaks.get(pageIndex)).intValue();

    }


    Rectangle bounds = new Rectangle(0,0,(int)pageFormat.getImageableWidth(),(int)pageFormat.getImageableHeight());
//    t2.translate(0, -pageFormat.getImageableHeight() * pageIndex);

    if (pageIndex!=0) {
      int lastBreak = ((Integer)breaks.get(pageIndex-1)).intValue();
      t2.translate(0, -lastBreak);
      System.err.println("Translated to lastBreak: "+lastBreak);
    }

    System.err.println("scale: "+scale);


    for (int i = 0; i < breaks.size(); i++) {
      Integer current = (Integer)breaks.get(i);
//      g2d.drawString("Break at: "+current.intValue(),0,current.intValue());
    }

      Shape s = g2d.getClip();

      Rectangle pageArea = new Rectangle((int) (pageFormat.getImageableX()),(int) (pageFormat.getImageableY())+headOffset,(int) (pageFormat.getImageableWidth()),(int) (pageFormat.getImageableHeight())-headOffset-footOffset);
      g2d.setColor(Color.black);
      g2d.drawString(footer,(int) (pageFormat.getImageableX()), (int) (pageFormat.getImageableHeight() + pageFormat.getImageableY() -footOffset+20));
      g2d.drawString(header,(int) (pageFormat.getImageableX()), (int) (pageFormat.getImageableY()+headOffset-10));
      System.err.println("Current index: " + pageIndex * scale * pageFormat.getImageableHeight());
      g2d.setClip(s.getBounds().intersection(pageArea));
      System.err.println("Clip: "+s.getBounds().intersection(pageArea));



      t2.scale(scale, scale);
      g2d.transform(t2);

//      g2d.translate(0,-headOffset);


    System.err.println("myPanel: " + myPanel.getSize());
//    System.err.println("layout: "+myPanel.getLayout().toString());
    System.err.println("width: " + pageFormat.getImageableWidth());
    System.err.println("height: " + pageFormat.getImageableHeight());
    myPanel.print(g2d);
    g2d.setTransform(t);

//    if (s!=null) {
//      Rectangle r = s.getBounds();
//      System.err.println("My clip: " + r);
//      g2d.setClip(r.x, 0, r.width, r.height + 144);
//    }
//
//    g2d.setClip(null);

//    if ( ( (pageIndex + 1) * scale * pageFormat.getImageableHeight()) > myPanel.getHeight() || pageIndex > 3) {
    g2d.drawString(footer, (int) (pageFormat.getImageableX()+40), (int) (pageFormat.getImageableHeight() + pageFormat.getImageableY() -footOffset+10));
    g2d.drawString(header,(int) (pageFormat.getImageableX()+40), (int) (pageFormat.getImageableY()+headOffset-10));

    if (pageIndex >= pageCount) {
      return Printable.NO_SUCH_PAGE;
    }
    else {
      return Printable.PAGE_EXISTS;
    }
  }

  public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException {
    return pf;
  }

  public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException {
    System.err.println("Getting printable: " + pageIndex);
    return this;
  }

  public int getNumberOfPages() {
    if (pageCount >= 0) {
      return pageCount;
    }
    return Pageable.UNKNOWN_NUMBER_OF_PAGES;
  }
}
