package com.dexels.navajo.tipi.components.swingimpl.tipimegatable;

import com.dexels.navajo.tipi.components.core.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.internal.*;
import java.io.*;
import java.util.List;
import javax.swing.event.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import java.awt.print.*;
import java.awt.geom.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiMegaTable extends TipiSwingDataComponentImpl {
  public TipiMegaTable() {
  }
  private JPanel myPanel = null;
  private boolean useTabs = true;

  private final Stack layers = new Stack();

  private final List tableInstances = new ArrayList();
  private final Map tableLayerMap = new HashMap();
  private final Map footerRendererMap = new HashMap();
  private final Map remarkPanelMap = new HashMap();

  private int page = 0;

  public Object createContainer() {
    /**@todo Implement this com.dexels.navajo.tipi.components.core.TipiComponentImpl abstract method*/
    myPanel = new PrintPanel();
    myPanel.setLayout(new BorderLayout());
    return myPanel;
  }

  public void addTableInstance(MessageTablePanel mtp,MessageTableFooterRenderer mfr, JComponent remarkPanel, TipiMegaTableLayer tmtl) {
    tableInstances.add(mtp);
    footerRendererMap.put(mtp,mfr);
    tableLayerMap.put(mtp,mfr);
    remarkPanelMap.put(mtp,remarkPanel);

    mtp.addChangeListener(new ChangeListener() {
      public void stateChanged(final ChangeEvent ce) {


        Thread t = new Thread() {
          public void run() {
            Map m = null;
             if (ce.getSource() instanceof Map) {
               m = (Map) ce.getSource();
             }
            refreshAllTables();
            try {
              performTipiEvent("onValueChanged", m, false);
            }
            catch (TipiException ex) {
              ex.printStackTrace();
            }

          }
        };
        t.start();
      }
    });
  }

  public void refreshAllTables() {
    if (myNavajo==null) {
      return;
    }
    TipiMegaTableLayer tmtl = (TipiMegaTableLayer)layers.peek();
    List updates = null;
    if (tmtl!=null) {
      String path = tmtl.getMessagePath();
      if (path!=null) {
        Message m = myNavajo.getMessage(path);
        if (m!=null) {
          try {
            // Often this should be enough, but not for the financial forms.
//            m.refreshExpression();
              updates = myNavajo.refreshExpression();
          }
          catch (NavajoException ex) {
            ex.printStackTrace();
          }
        } else {
          System.err.println("NULL MESSAGE?**********************");
        }
      } else {
          System.err.println("NUL PATH??******************8");
      }
    }
    for (int i = 0; i < tableInstances.size(); i++) {
      final MessageTablePanel mtp = (MessageTablePanel)tableInstances.get(i);
      final MessageTableFooterRenderer mtf = (MessageTableFooterRenderer)footerRendererMap.get(mtp);
      final RemarkPanel remarkPanel = (RemarkPanel)remarkPanelMap.get(mtp);
//      SwingUtilities.invokeLater(new Runnable() {
//        public void run() {
//          mtp.fireDataChanged();
//          mtp.prop
//          try {
            mtp.updateProperties(updates);
//          }
//          catch (NavajoException ex) {
//            ex.printStackTrace();
//          }
          if (mtf!=null) {
            mtf.flushAggregateValues();

          }
          mtp.repaintHeader();
          if (remarkPanel!=null) {
            remarkPanel.updateConditionalRemarks();
          }

//        }
//      });
           }
  }
  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws
      com.dexels.navajo.tipi.TipiException {
    super.load(elm, instance, context);
    loadLevels(elm);
  }

  public void updateLayers() {
    for (int i = layers.size()-1; i >= 0; i--) {
      TipiMegaTableLayer tmtl = (TipiMegaTableLayer)layers.get(i);
      tmtl.updateLayer();
    }
  }


  public XMLElement store() {
    XMLElement xx = super.store();
    for (int i = layers.size()-1; i >= 0; i--) {
      TipiMegaTableLayer tmtl = (TipiMegaTableLayer)layers.get(i);
      XMLElement cc = tmtl.store();
      xx.addChild(cc);
    }
    return xx;
  }

  public void flatten(String serviceName, String hostUrl, String username, String password, String pincode,String keystore,String keypass) throws NavajoException, TipiException, TipiBreakException {
    Navajo out = NavajoFactory.getInstance().createNavajo();
    Message outResult = NavajoFactory.getInstance().createMessage(out,"Answers",Message.MSG_TYPE_ARRAY);
    Message formData = myNavajo.getMessage("FormData");
    Message outMessage = formData.copy(out);
    Property pin = NavajoFactory.getInstance().createProperty(out,"Pincode",Property.STRING_PROPERTY,pincode,16,"",Property.DIR_IN);
    outMessage.addProperty(pin);
    out.addMessage(outMessage);
    out.addMessage(outResult);

    ArrayList al = myNavajo.getAllMessages();
    for (int i = 0; i < al.size(); i++) {
      flatten((Message)al.get(i),outResult);
    }
    System.err.println("FLATTENING FINISHED **********************************");
    out.write(System.err);
    System.err.println("END OF NAVAJO ****************************************");
    try {
      FileWriter fw = new FileWriter("c:/flatfile.xml");
      out.write(fw);
      fw.flush();
      fw.close();
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
    myContext.performTipiMethod(this,out,"*",serviceName,true,null,-1,hostUrl,username,password,keystore,keypass);
  }

  protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
    if ("flatten".equals(name)) {
      String serviceName = (String)compMeth.getEvaluatedParameter("serviceName",event).value;
      String hostUrl = (String)compMeth.getEvaluatedParameter("hostUrl",event).value;
      String username = (String)compMeth.getEvaluatedParameter("username",event).value;
      String password = (String)compMeth.getEvaluatedParameter("password",event).value;
      String pincode = (String)compMeth.getEvaluatedParameter("pincode",event).value;
      String keystore = (String)compMeth.getEvaluatedParameter("keystore",event).value;
      String keypass = (String)compMeth.getEvaluatedParameter("keypass",event).value;
        try {
          flatten(serviceName,hostUrl,username,password,pincode,keystore,keypass);
        }
        catch (NavajoException ex) {
          ex.printStackTrace();
        }
        catch (TipiException ex) {
          ex.printStackTrace();
        }
    }
    if ("showEditDialog".equals(name)) {
      Object table = compMeth.getEvaluatedParameter("table",event).value;
      Operand title = compMeth.getEvaluatedParameter("title",event);
      if (MessageTablePanel.class.isInstance(table)) {
        MessageTablePanel mtp = (MessageTablePanel)table;
        try {
          String titleString;
          titleString = title==null?"Aap":""+title.value;
          mtp.showEditDialog(titleString);
        }
        catch (NavajoException ex1) {
          ex1.printStackTrace();

        }
      }
    }
    if ("refreshRemarks".equals(name)) {
      refreshAllTables();
    }
//    if ("print".equals(name)) {
//      Operand printJob = compMeth.getEvaluatedParameter("printJob",event);
//
//      print((PrinterJob)(printJob.value));
//    }
//
        super.performComponentMethod(name,compMeth,event);
  }

  private final void flatten(Message in, Message out) {
    System.err.println("Flattening: "+in.getFullMessageName()+" out elements: "+out.getArraySize());
    Property p = in.getProperty("Code");
    if (p!=null && p.getValue()!=null) {
      ArrayList pl = in.getAllProperties();

      for (int i = 0; i < pl.size(); i++) {
        Property current = (Property)pl.get(i);
        if (!current.getType().equals(Property.EXPRESSION_PROPERTY) && current.isDirIn()) {
          Message m = NavajoFactory.getInstance().createMessage(out.getRootDoc(),"Answers");
          out.addMessage(m);
          Property codeCopy = p.copy(out.getRootDoc());
//          System.err.println("Remove the length restriction");
          // remove the length restriction
          p.setLength(255);
          codeCopy.setName("Id");
          codeCopy.setValue(codeCopy.getValue()+"/"+current.getName());

          Property copy = current.copy(out.getRootDoc());
          copy.setName("Value");
          m.addProperty(codeCopy);
          m.addProperty(copy);
        }
      }
    }
    ArrayList al = in.getAllMessages();
    for (int i = 0; i < al.size(); i++) {
      flatten((Message)al.get(i),out);
    }

  }

  private final void loadLevels(XMLElement elm) {
    Vector children = elm.getChildren();
   for (int i = children.size()-1; i >= 0; i--) {
     XMLElement child = (XMLElement) children.elementAt(i);
     if (child.getName().equals("layer")) {
       String type = child.getStringAttribute("type");
       TipiMegaTableLayer tmtl = null;
       if (type.equals("tab")) {
         tmtl = new TipiTabLayer(this);
       }
       if (type.equals("panel") || type.equals("scroll")) {
         tmtl = new TipiScrollLayer(this);
       }
       if (type.equals("table")) {
         tmtl = new TipiTableLayer(this);
       }
       if (type.equals("treetable")) {
         tmtl = new TipiTreeTableLayer(this);
       }
       if (tmtl!=null) {
         tmtl.loadLayer(child);
         layers.add(tmtl);
       }
     }
   }
  }

  private final void reload() {
    try {
      if (myNavajo != null) {
        loadData(getNavajo(), myContext);
      }
      else {
        System.err.println("Can not reload, no navajo!");
      }
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  protected Component getPrintingContainer() {
    return getSwingContainer().getComponent(0);
  }



  public void loadData(final Navajo n, TipiContext context) throws
      TipiException {
    myPanel.removeAll();
    footerRendererMap.clear();
    tableInstances.clear();
    Stack currentLayers = (Stack)layers.clone();
    Message current = null;
    TipiMegaTableLayer tmtl = (TipiMegaTableLayer)currentLayers.pop();

    current = n.getMessage(tmtl.getMessagePath());
    tmtl.loadData(n,null,currentLayers,myPanel);

    super.loadData(n, context);
  }
}
