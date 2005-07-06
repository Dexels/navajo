package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.document.*;

import java.awt.print.*;
import com.dexels.navajo.tipi.internal.*;
import java.awt.*;
import java.awt.geom.*;
import java.net.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiPanel
    extends TipiSwingDataComponentImpl implements Pageable, Printable {


  private PageFormat pf = null;
  private TipiSwingPanel myPanel = null;

  public Object createContainer() {
    myPanel = new TipiSwingPanel(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myPanel;
  }

  protected ImageIcon getIcon(URL u) {
    return new ImageIcon(u);
  }

  public void loadData(final Navajo n, final TipiContext tc, final String method) throws TipiException {
      runASyncInEventThread(new Runnable(){

        public void run() {
            try {
                doLoadData(n,tc,method);
            } catch (TipiException e) {
                e.printStackTrace();
            }
            
        }});
  }
  // Hack. dont know how to do this directly
  private void doLoadData(Navajo n, TipiContext tc, String method)  throws TipiException {
      super.loadData(n, tc, method);
  }
      
  public void setComponentValue(String name, final Object value) {
    if ("enabled".equals(name)) {
      getSwingContainer().setEnabled(value.equals("true"));
    }
    if (name.equals("image")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          ( (TipiSwingPanel) getContainer()).setImage(getIcon( (URL) value));
        }
      });
      return;
    }
    if (name.equals("image_alignment")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          ( (TipiSwingPanel) getContainer()).setImageAlignment((String)value);
        }
      });
      return;
    }

    super.setComponentValue(name, value);
  }


//
//  public void print(PrinterJob printJob) {
//     printJob.setPageable(this);
//     pf = printJob.defaultPage();
//         try {
//           printJob.print();
//         } catch (Exception ex) {
//             ex.printStackTrace();
//         }
//}
//       public int getNumberOfPages() {
//         return Pageable.UNKNOWN_NUMBER_OF_PAGES;
//       }
//
//       public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException {
//         return pf;
//       }
//
//       public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException {
//         System.err.println("Getting printable: "+pageIndex);
//         return this;
//       }
//
//       public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
//         Graphics2D g2d = (Graphics2D)graphics;
//         System.err.println("printing page: "+pageIndex);
//
//
//         AffineTransform t = g2d.getTransform();
//
//         AffineTransform t2 = t.getTranslateInstance(   pageFormat.getImageableX(),pageFormat.getImageableY());
//
//         System.err.println("imag: "+pageFormat.getImageableX());
//         System.err.println("myPanel: "+myPanel.getSize());
//
//         double xscale = pageFormat.getImageableWidth()/myPanel.getWidth();
//         double yscale = pageFormat.getImageableHeight()/myPanel.getHeight();
//
//         double scale = xscale;
//
//         t2.translate(0,-pageFormat.getImageableHeight()*pageIndex);
//         t2.scale(scale,scale);
//
//         System.err.println("xscale: "+xscale);
//         System.err.println("yscale: "+yscale);
//
//         g2d.transform(t2);
//
//         System.err.println("myPanel: "+myPanel.getSize());
//         System.err.println("width: "+pageFormat.getImageableWidth());
//         System.err.println("height: "+pageFormat.getImageableHeight());
//
//         myPanel.print(g2d);
//         g2d.setTransform(t);
//
//         System.err.println("Current index: "+pageIndex*scale*pageFormat.getImageableHeight());
//
//         if (((pageIndex-1)*scale*pageFormat.getImageableHeight())>myPanel.getHeight() || pageIndex >3) {
//           return Printable.NO_SUCH_PAGE;
//         } else {
//           return Printable.PAGE_EXISTS;
//         }
//       }

}
