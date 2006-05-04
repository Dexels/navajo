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
//  private String myMethod = null;

  public Object createContainer() {
    /**@todo Implement this com.dexels.navajo.tipi.components.core.TipiComponentImpl abstract method*/
    myPanel = new JPanel();
    myPanel.setLayout(new BorderLayout());
    return myPanel;
  }

  public void addTableInstance(MessageTablePanel mtp,MessageTableFooterRenderer mfr, JComponent remarkPanel, TipiTableBaseLayer tmtl) {
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
    TipiTableBaseLayer tmtl = (TipiTableBaseLayer)layers.peek();
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
      TipiTableBaseLayer tmtl = (TipiTableBaseLayer)layers.get(i);
      tmtl.updateLayer();
    }
  }


  public XMLElement store() {
    XMLElement xx = super.store();
    for (int i = layers.size()-1; i >= 0; i--) {
      TipiTableBaseLayer tmtl = (TipiTableBaseLayer)layers.get(i);
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

    Message m2 = myNavajo.getMessage("SendForm").copy(out);
    out.addMessage(m2);

    Property pin = NavajoFactory.getInstance().createProperty(out,"Pincode",Property.STRING_PROPERTY,pincode,16,"",Property.DIR_IN);
    outMessage.addProperty(pin);
    out.addMessage(outMessage);
    out.addMessage(outResult);

    ArrayList al = myNavajo.getAllMessages();
    for (int i = 0; i < al.size(); i++) {
      flatten((Message)al.get(i),outResult);
    }
   
//    System.err.println("FLATTENING FINISHED **********************************");
//    out.write(System.err);
//    System.err.println("END OF NAVAJO ****************************************");

    myContext.performTipiMethod(this,out,"*",serviceName,true,null,-1,hostUrl,username,password,keystore,keypass);
  }

  protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
    if ("flatten".equals(name)) {
      String username = null;
      String password = null;
      String keystore = null;
      String keypass = null;
      String hostUrl = null;
      Operand host = compMeth.getEvaluatedParameter("hostUrl",event);
      if (host!=null) {
        username = (String)compMeth.getEvaluatedParameter("username",event).value;
       password = (String)compMeth.getEvaluatedParameter("password",event).value;
       keystore = (String)compMeth.getEvaluatedParameter("keystore",event).value;
      keypass = (String)compMeth.getEvaluatedParameter("keypass",event).value;
      hostUrl = (String)host.value;
      }
      String serviceName = (String)compMeth.getEvaluatedParameter("serviceName",event).value;
     String pincode = (String)compMeth.getEvaluatedParameter("pincode",event).value;



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
          mtp.showEditDialog(titleString, mtp.getSelectedRow());
        }
        catch (Exception ex1) {
          ex1.printStackTrace();

        }
      }
    }
    if ("export".equals(name)) {
        String filename = (String)compMeth.getEvaluatedParameter("filename",event).value;
        String delimiter = (String)compMeth.getEvaluatedParameter("delimiter",event).value;
       try {
            flattenToCsv(filename,delimiter);
        } catch (IOException e) {
            e.printStackTrace();
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
      System.err.println("Cat code: "+p.getValue());
      for (int i = 0; i < pl.size(); i++) {
        Property current = (Property)pl.get(i);
        if (!current.getType().equals(Property.EXPRESSION_PROPERTY) && current.isDirIn() && !"".equals(p.getValue())) {
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
       TipiTableBaseLayer tmtl = null;
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

  private final void flattenToCsv(String filename, String delimiter) throws IOException {
      Stack s = (Stack)layers.clone();

      FileWriter f = new FileWriter(filename);
      for (int i = 0; i < tableInstances.size(); i++) {
          final MessageTablePanel mtp = (MessageTablePanel)tableInstances.get(i);
          mtp.exportTable(f, delimiter,false);
      }
      f.close();
  }
  
  private final void reload() {
    try {
      if (myNavajo != null) {
        loadData(getNavajo(), myContext, getCurrentMethod());
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

  private ArrayList createSelectionList() {
      final ArrayList result = new ArrayList();
      runSyncInEventThread(new Runnable(){

        public void run() {
            for (Iterator iter = layers.iterator(); iter.hasNext();) {
                
                TipiTableBaseLayer element = (TipiTableBaseLayer) iter.next();
                int i  = element.getCurrentSelection();
                result.add(new Integer(i));
              }
       }});

      return result;
  }

  private void processSelectionList(final ArrayList l) {
      runSyncInEventThread(new Runnable(){

        public void run() {
            int j = 0;
            for (Iterator iter = layers.iterator(); iter.hasNext();) {
              TipiTableBaseLayer element = (TipiTableBaseLayer) iter.next();
              Integer iii = (Integer)l.get(j);
              if (iii.intValue()!=-1) {
                  element.setCurrentSelection(iii.intValue());
              } else {
                  System.err.println("Not really a selection, ignoring");
              }
              j++;
            }
        }});
   }


  public void loadData(final Navajo n, TipiContext context, String method) throws
      TipiException {
    myPanel.removeAll();
    footerRendererMap.clear(); 
    tableInstances.clear();
      Stack currentLayers = (Stack)layers.clone();
      ArrayList selectionList = createSelectionList();
      System.err.println("SELECTION LIST: "+selectionList);
      Message current = null;
    TipiTableBaseLayer tmtl = (TipiTableBaseLayer)currentLayers.pop();

    current = n.getMessage(tmtl.getMessagePath());
    tmtl.loadData(n,null,currentLayers,myPanel);

    
    super.loadData(n, context, method);
    processSelectionList(selectionList);
     }
  
  public static void main(String[] args) {
      Stack s = new Stack();
      s.push(new Integer(1));
      s.push(new Integer(2));
      s.push(new Integer(3));
      System.err.println("Stack: "+s);
      int i = 0;
      for (Iterator iter = s.iterator(); iter.hasNext();) {
          Integer element = (Integer) iter.next();
        System.err.println("Element# "+i++);
        System.err.println("::: "+element);
    }
  }
}
