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

  public Object createContainer() {
    /**@todo Implement this com.dexels.navajo.tipi.components.core.TipiComponentImpl abstract method*/
    myPanel = new JPanel();
    myPanel.setLayout(new BorderLayout());
    return myPanel;
  }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws
      com.dexels.navajo.tipi.TipiException {
    super.load(elm, instance, context);
    loadLevels(elm);
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

  public void flatten(String serviceName) throws NavajoException {
    Navajo out = NavajoFactory.getInstance().createNavajo();
    Message outResult = NavajoFactory.getInstance().createMessage(out,"ResultMessage",Message.MSG_TYPE_ARRAY);
    Message formData = myNavajo.getMessage("FormData");
    out.addMessage(formData.copy(out));
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
  }

  protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) {
    String serviceName = (String)compMeth.getEvaluatedParameter("serviceName",event).value;
    if ("flatten".equals(name)) {
        try {
          flatten(serviceName);
        }
        catch (NavajoException ex) {
          ex.printStackTrace();
        }
    }
    if ("showEditDialog".equals(name)) {
      Object table = compMeth.getEvaluatedParameter("table",event).value;
      if (MessageTablePanel.class.isInstance(table)) {
        MessageTablePanel mtp = (MessageTablePanel)table;
        try {
          mtp.showEditDialog();
        }
        catch (NavajoException ex1) {
          ex1.printStackTrace();

        }
      }
    }

        super.performComponentMethod(name,compMeth,event);
  }

  private void flatten(Message in, Message out) {
    System.err.println("Flattening: "+in.getFullMessageName()+" out elements: "+out.getArraySize());
    Property p = in.getProperty("Code");
    if (p!=null && p.getValue()!=null) {
      ArrayList pl = in.getAllProperties();
      Message m = NavajoFactory.getInstance().createMessage(out.getRootDoc(),"ResultMessage");
      out.addMessage(m);

      for (int i = 0; i < pl.size(); i++) {
        Property current = (Property)pl.get(i);
        if (current.getValue()!=null && !current.getType().equals(Property.EXPRESSION_PROPERTY)&& (current.getName().equals("Code") || current.getName().startsWith("Column"))) {
          Property copy = current.copy(out.getRootDoc());
          m.addProperty(copy);
        }
      }
    }
    ArrayList al = in.getAllMessages();
    for (int i = 0; i < al.size(); i++) {
      flatten((Message)al.get(i),out);
    }

  }

  private void loadLevels(XMLElement elm) {
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

  private void reload() {
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


  public void loadData(final Navajo n, TipiContext context) throws
      TipiException {
    myPanel.removeAll();
    Stack currentLayers = (Stack)layers.clone();
    Message current = null;
    TipiMegaTableLayer tmtl = (TipiMegaTableLayer)currentLayers.pop();

    current = n.getMessage(tmtl.getMessagePath());
    tmtl.loadData(n,null,currentLayers,myPanel);

    super.loadData(n, context);
  }

}
