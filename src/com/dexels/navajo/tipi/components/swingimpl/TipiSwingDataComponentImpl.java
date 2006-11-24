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
import com.dexels.navajo.tipi.components.swingimpl.parsers.*;

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
  protected TipiGradientPaint myPaint;
  private PageFormat pf = null;
  private String myHeader = null;
  private String myFooter = null;
  private int currentPage = -1;
  private double scale = 0;
  private int pageCount = -1;


  private final ArrayList myBreaks = new ArrayList();
  private boolean committedInUI;


  
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
      boolean noPrefSizes = false;
	try {
		noPrefSizes = "true".equals(System
				.getProperty("com.dexels.navajo.swingclient.NoPreferredSizes"));
	} catch (SecurityException e) {
		// assume false;
	}      
	if (noPrefSizes) {
          if (getContainer() instanceof JComponent) {
              JComponent cc = (JComponent)getContainer();
              LayoutManager m = cc.getLayout();
              if (m instanceof GridBagLayout) {
                cc.setPreferredSize(new Dimension(0,0));
            }
          }
      }

    try {
		getSwingContainer().add( (Component) c, constraints);
	} catch (Throwable e) {
		throw new RuntimeException("Illegal constraint while adding object: "+c+" to component: "+
				getPath()+" with constraint: "+constraints);
	}
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
//    getContext().debugLog("data    ", "Entering doLayout in tipi: " + getId());
    if (getContainer() != null) {
      if (JComponent.class.isInstance(getContainer())) {
        runASyncInEventThread(new Runnable() {
          public void run() {
//            getContext().debugLog("data    ", "Entering doLayout in tipi: " + getId());
            ( (JComponent) getContainer()).revalidate(); ( (JComponent) getContainer()).repaint(); getContext().debugLog("data    ", "Exiting doLayout in tipi: " + getId());
          }
        });
      }
    }
//    getContext().debugLog("data    ", "Exiting doLayout in tipi: " + getId());
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
    if (SwingUtilities.isEventDispatchThread() || !committedInUI) {
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
      }
    }
  }

  public void runASyncInEventThread(Runnable r) {
    if (SwingUtilities.isEventDispatchThread() || !committedInUI) {
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
    ((UserInterface)((SwingTipiContext)myContext).getUserInterface()).showCenteredDialog(jd);
  }

  private int currentBreak = 0;

  private final double breakingRange=.8;

  private int calculateBreaks(Component myPanel, PageFormat pageFormat, double scale, ArrayList breaks) {
    int idx = 0;
//    Component myPanel = getSwingContainer();
    if (myBreaks.size()==0) {
      return -1;
    }
    breaks.addAll(myBreaks);
//    if (true) {
      return myBreaks.size();
//    }
    // Not reachable from here. Will be removed
//    if (!(myPanel instanceof PageBreakable)) {
//      System.err.println("Not pagebreakable. class: "+myPanel.getClass());
//      return -1;
//    }
//
//    PageBreakable bp = (PageBreakable)myPanel;
//
//
//
//    breaks.add(new Integer(959));
//    breaks.add(new Integer(2061));
//    breaks.add(new Integer(3180));
//    breaks.add(new Integer(4000));
//    if (true) {
//      return 4;
//    }
//    double height = myPanel.getHeight()*scale;
//    int pageHeight = (int)pageFormat.getImageableHeight()-headOffset-footOffset;
//    int pageBreakPoint = (int)(pageHeight * breakingRange);
//    int div=0;
//    int page = 0;
//    System.err.println("panelHeight: "+myPanel.getHeight()+" = scaled: "+height+"+pageHeight: "+pageHeight+"  ");
//    while (idx<height && div!=-1) {
//      System.err.println("Current point: "+div);
//      System.err.println("Looking for a break between: "+(pageBreakPoint+idx)+" and: "+(pageHeight+idx));
//      div = bp.getDivisionPoint(pageBreakPoint+idx,pageHeight+idx);
//      System.err.println("Dividing at: "+div);
//      breaks.add(new Integer(div));
//      idx+=div;
//      page++;
//    }
//    return page;
  }

  protected Component getPrintingContainer() {
    return getSwingContainer();
  }

//  private final ArrayList breaks = new ArrayList();

  private void updateScaling(PageFormat pf) {
    Component myPanel = getSwingContainer();
    double xscale = pf.getImageableWidth() / myPanel.getWidth();
    double yscale = pf.getImageableHeight() / myPanel.getHeight();
    scale = xscale;
//    breaks.clear();
//    int pppage = calculateBreaks(getPrintingContainer(),pf,scale,breaks);
//    System.err.println("xscale: "+xscale);

    int pppage = myBreaks.size();

//    System.err.println("**  Break overview:");
//
//    for (int i = 0; i < myBreaks.size(); i++) {
//      System.err.println("**  "+myBreaks.get(i));
//    }
//    System.err.println("**  End.");
//
    double height = myPanel.getHeight() * scale;
    if (pppage > 0) {
      pageCount = pppage;
    } else {
      double count = height / pf.getImageableHeight();
      pageCount = (int) Math.ceil(count);
    }
  }

  public ImageIcon getPreview(String header, String footer, int page) {
    myHeader = header;
    myFooter = footer;
    PageFormat pf = new PageFormat();
    disableDoubleBuffering(getSwingContainer());

    pf.getPaper().setImageableArea(72,72,pf.getWidth()-144,pf.getHeight()-144);
    Component myPanel = getSwingContainer();
    Rectangle bounds = myPanel.getBounds();
//    System.err.println("Bounds: "+bounds);
//myPanel.setBounds(0,0,1000,10000);
    updateScaling(pf);
//    myPanel.doLayout();
//    System.err.println("Bounds2: "+myPanel.getBounds());
//myPanel.setBounds(bounds);
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


  int headOffset = 20;
    int footOffset = 12;


  public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
    Graphics2D g2d = (Graphics2D) graphics;
    if (pageIndex >= pageCount) {
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

//    int currentbreak;

    if (pageIndex >= myBreaks.size()) {
      currentBreak = (int)pageFormat.getImageableHeight();
    } else {
      currentBreak = ((Integer)myBreaks.get(pageIndex)).intValue();

    }
//
//
//    Rectangle bounds = new Rectangle(0,0,(int)pageFormat.getImageableWidth(),(int)pageFormat.getImageableHeight());
    int lastBreak = 0;

    if (pageIndex != 0 && myBreaks.size()>0) {
      lastBreak = ( (Integer) myBreaks.get(pageIndex - 1)).intValue();
    }
    Shape s = g2d.getClip();
    int pageHeight = Math.min((int)((currentBreak - lastBreak)*scale),(int)(pageFormat.getImageableHeight()-headOffset-footOffset));
    Rectangle totalPageArea = new Rectangle( (int) (pageFormat.getImageableX()), (int) (pageFormat.getImageableY()), (int) (pageFormat.getImageableWidth()),
                                      (int) (pageFormat.getImageableHeight()));

   Rectangle pageArea = new Rectangle( (int) (pageFormat.getImageableX()), (int) (pageFormat.getImageableY()) + headOffset, (int) (pageFormat.getImageableWidth()),
                                      pageHeight);

    g2d.setColor(Color.black);
    g2d.clipRect(pageArea.x,pageArea.y,pageArea.width,pageArea.height);
    t2.scale(scale, scale);
    t2.translate(0, -lastBreak);
    g2d.transform(t2);
    myPanel.print(g2d);
    g2d.setTransform(t);

    g2d.setClip(totalPageArea);
    g2d.drawString(footer, (int) (pageFormat.getImageableX()+5), (int) (pageFormat.getImageableHeight() + pageFormat.getImageableY() -footOffset+10));
    g2d.drawString(header,(int) (pageFormat.getImageableX()+5), (int) (pageFormat.getImageableY())+12);

    g2d.setClip(s);
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
     return this;
  }

  public int getNumberOfPages() {
    if (pageCount >= 0) {
      return pageCount;
    }
    return Pageable.UNKNOWN_NUMBER_OF_PAGES;
  }
  public void setPaint(Paint p){
    this.myPaint = (TipiGradientPaint)p;
  }

  public TipiGradientPaint getPaint(){
    return myPaint;
  }

  protected void setComponentValue(String name, Object object) {
    if (name.equals("breaks")) {
      myBreaks.clear();
      StringTokenizer st = new StringTokenizer(""+object,";");
      while (st.hasMoreTokens()) {
        String s = st.nextToken();
        Integer br = new Integer(Integer.parseInt(s));
        myBreaks.add(br);
      }
    }
    if (name.equals("visible")) {
        boolean val = "true".equals(object);
        getSwingContainer().setVisible(val);
    }
        super.setComponentValue(name,object);
  }
  
  public void commitToUi() {
      super.commitToUi();
      committedInUI = true;
  }
  
}
