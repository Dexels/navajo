package com.dexels.navajo.tipi.swingclient.components.treetable;

import java.util.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.lazy.*;

public class FilteredMessageTreeTableModel
    extends MessageTreeTableModel
    implements MessageListener {

  private List propertyList = new ArrayList();
  private List exclusionList = new ArrayList();
  private ResourceBundle myBundle = null;

  public FilteredMessageTreeTableModel() {
    super(NavajoFactory.getInstance().createMessage(NavajoFactory.getInstance().createNavajo(), ""));
  }

  @Override
public void setRoot(Message m) {
    super.setRoot(m);
  }

  @Override
public void setHeaderTextResource(ResourceBundle res) {
    myBundle = res;
  }

  @Override
public void addExclusion(String s) {
    exclusionList.add(s);
  }

  @Override
public void addPropertyColumn(String s) {
    propertyList.add(s);
    delayedFireTableStructureChanged();
  }

  @Override
public void clearPropertyColumns() {
    propertyList.clear();
  }

  @Override
public void clearExclusions() {
    exclusionList.clear();
  }

  @Override
public void reset() {
    Message m = NavajoFactory.getInstance().createMessage(NavajoFactory.getInstance().createNavajo(), "");
    reset(m);
    fireTreeStructureChanged();
  }

  @Override
public void reset(Message newRoot) {
    setRoot(newRoot);
    clearExclusions();
    clearPropertyColumns();
    fireTreeStructureChanged();
  }

  @Override
public void reset(Message newRoot, String[] props) {
    reset(newRoot);
    for (int i = 0; i < props.length; i++) {
      addPropertyColumn(props[i]);
    }
  }

  @Override
public void reset(Message newRoot, String[] props, String[] exclusions) {
    reset(newRoot, props);
    for (int i = 0; i < exclusions.length; i++) {
      addExclusion(exclusions[i]);
    }
  }

  @Override
public int getColumnCount() {
    return propertyList.size() + 1;
  }

  @Override
public String getColumnName(int index) {
    if (index == 0) {
      return " ";
    }
    if (myBundle != null) {
      String s = (String) propertyList.get(index - 1);
      String r = myBundle.getString(s);
      if (r != null) {
        return r;
      }
    }

    return ( (String) propertyList.get(index - 1));
  }

  @Override
public int getChildCount(Object msg) {
    Message mm = (Message) msg;
    ArrayList al = mm.getAllMessages();
    int count = 0;
    for (int i = 0; i < al.size(); i++) {
      Message current = (Message) al.get(i);
      boolean complying = true;
      if (current.getType().equals("array")) {
        complying = false;
        count += getChildCount(current);
      }
      for (int j = 0; j < exclusionList.size(); j++) {
        String excl = (String) exclusionList.get(j);
        if (current.getName().startsWith(excl)) {
          complying = false;
        }
      }
      if (complying) {
        count++;
      }
    }
    return count;
  }

  @Override
public Object getChild(Object msg, int childIndex) {
    ArrayList al = ( (Message) msg).getAllMessages();
    int count = 0;
    for (int i = 0; i < al.size(); i++) {
      Message current = (Message) al.get(i);
      boolean complying = true;
      if (current.getType().equals("array")) {
        complying = false;
        Message chld = (Message) getChild(current, childIndex - count);
        if (chld != null) {
          return chld;
        }
        else {
          count += getChildCount(current);
        }
      }
      for (int j = 0; j < exclusionList.size(); j++) {
        String excl = (String) exclusionList.get(j);
        if (current.getName().startsWith(excl)) {
          complying = false;
        }
      }
      if (complying) {
        if (count == childIndex) {
          return al.get(i);
        }
        count++;
      }
    }
    return null;
  }

  @Override
public Object getValueAt(Object msg, int prop) {
    Message m = (Message) msg;
    if (prop == 0) {
      return null;
    }
    if (propertyList.size() == 0) {
      return null;
    }

    if ( (prop - 1) >= propertyList.size()) {
      return null;
    }

    String propName = ( (String) propertyList.get(prop - 1));
    Property p = m.getProperty(propName);
    if (p == null) {
      try {
        return NavajoFactory.getInstance().createProperty(NavajoFactory.getInstance().createNavajo(), "", Property.STRING_PROPERTY, "", 1, "", Property.DIR_IN);
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
        return null;
      }
    }
    else {
      return p;
    }
  }

  @Override
public Class getColumnClass(int column) {
    if (column == 0) {
      return TreeTableModel.class;
    }
    return Property.class;
  }

  @Override
public void messageLoaded(int startIndex, int endIndex, int newTotal) {
    super.messageLoaded(startIndex, endIndex, newTotal);
  }
}
