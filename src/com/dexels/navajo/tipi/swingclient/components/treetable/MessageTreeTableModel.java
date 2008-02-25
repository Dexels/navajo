package com.dexels.navajo.tipi.swingclient.components.treetable;

import java.util.*;

//import com.dexels.navajo.swingclient.components.lazy.MessageListener;
import javax.swing.tree.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.lazy.*;
import java.awt.Color;

public class MessageTreeTableModel
    extends AbstractTreeTableModel
    implements MessageListener {

  private List propertyList = new ArrayList();
  private List exclusionList = new ArrayList();
  private ResourceBundle myBundle = null;
  private MessageTreeTable myTreeTable = null;
  private Map nodeColor = new HashMap();

  public void doRecursiveExpand() {
    doRecursiveExpand(getRoot());
  }

  public void setTreeTable(MessageTreeTable mtt) {
    myTreeTable = mtt;
  }

  public void doRecursiveExpand(Object startNode) {
  }

  public MessageTreeTableModel() {
    super(NavajoFactory.getInstance().createMessage(NavajoFactory.getInstance().createNavajo(), ""));
  }

  public MessageTreeTableModel(Message m) {
    super(m);
  }

  public void setRoot(Message m) {
    super.setRoot(m);
  }

  public void setHeaderTextResource(ResourceBundle res) {
    myBundle = res;
  }

  public void addExclusion(String s) {
    exclusionList.add(s);
  }

  public void addPropertyColumn(String s) {
    propertyList.add(s);
    delayedFireTableStructureChanged();
  }

  public void clearPropertyColumns() {
    propertyList.clear();
  }

  public void clearExclusions() {
    exclusionList.clear();
  }

  public void reset() {
    Message m = NavajoFactory.getInstance().createMessage(null, "");
    reset(m);
    fireTreeStructureChanged();
  }

  public void reset(Message newRoot) {
    setRoot(newRoot);
    clearExclusions();
    clearPropertyColumns();
  }

  public void reset(Message newRoot, String[] props) {
    reset(newRoot);
    for (int i = 0; i < props.length; i++) {
      addPropertyColumn(props[i]);
    }
  }

  public void reset(Message newRoot, String[] props, String[] exclusions) {
    reset(newRoot, props);
    for (int i = 0; i < exclusions.length; i++) {
      addExclusion(exclusions[i]);
    }
  }

  public int getColumnWidth(int index){
    if(index < myTreeTable.getColumnCount()){
      return myTreeTable.getColumnModel().getColumn(index).getWidth();
    }else {
      return 50;
    }
  }

  public int getColumnCount() {
    return propertyList.size() + 1;
  }

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

  public int getChildCount(Object msg) {

    if (!Message.class.isInstance(msg)) {
      return 0;
    }
    Message mm = (Message) msg;
    int count = 0;
    if (msg == getRoot()) {
      if (mm.getType().equals(Message.MSG_TYPE_ARRAY)) {
        return mm.getArraySize();
      }
      else {
        return 0;
      }

    }

    for (int i = 0; i < mm.getArraySize(); i++) {
      Message current = mm.getMessage(i);
      if (current == null) {
        count++;
        continue;
      }
      if (current.getType().equals(Message.MSG_TYPE_ARRAY)) {
        count += getChildCount(current);
      }
      else {
        count++;
      }
    }

    return count;
  }

  public Object getChild(Object msg, int childIndex) {
    if (!Message.class.isInstance(msg)) {
      return null;
    }
    Message mm = (Message) msg;
    if (msg == getRoot()) {
      Message cc = mm.getMessage(childIndex);
      if (cc == null) {
        return "...";
      }
      else {
        return cc;
      }

    }
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

  public Object getValueAt(Object msg, int prop) {
    if (!Message.class.isInstance(msg)) {
      return "*";
    }

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
        return null;
      }
    }
    else {
      return p;
    }
  }

  public Class getColumnClass(int column) {
    if (column == 0) {
      return TreeTableModel.class;
    }
    return Property.class;
  }

  public void setNodeColor(String depth, Color c){
    nodeColor.put(depth, c);
  }

  public Color getNodeColor(String depth){
    Object o = nodeColor.get(depth);
    if(o == null){
      return Color.black;
    }else{
      return (Color)o;
    }
  }


  public void messageLoaded(int startIndex, int endIndex, int oldTotal) {

    int rrr = myTreeTable.getSelectionModel().getLeadSelectionIndex();
    int max = Math.min(endIndex, ( ( (LazyMessage) getRoot()).getTotal()));
    int[] children = new int[max - startIndex];
    Message[] msgs = new Message[max - startIndex];
    TreePath path = new TreePath(getRoot());
    for (int i = 0; i < children.length; i++) {
      children[i] = startIndex + i;
      msgs[i] = ( (Message) getRoot()).getMessage(i + startIndex);
    }
    int rowCount = ( (TreeNode) getRoot()).getChildCount();
    fireTreeNodesChanged(getRoot(), new Object[] { ( (Message) getRoot())}
                         , children, msgs);

    if (rowCount != oldTotal) {
      int[] indices = new int[oldTotal - rowCount];
      Message[] mssgs = new Message[oldTotal - rowCount];
      for (int i = 0; i < oldTotal - rowCount; i++) {
        indices[i] = rowCount + i;
        mssgs[i] = ( (Message) getRoot()).getMessage(rowCount + i);
      }
      fireTreeNodesRemoved(getRoot(), new Object[] { ( (Message) getRoot())}
                           , indices, new Message[] {});
    }

    for (int i = startIndex; i < max; i++) {
      if ( ( (Message) getRoot()).getMessage(i) != null) {
        TreePath tp = path.pathByAddingChild( ( (Message) getRoot()).getMessage(i));
        myTreeTable.expandPath(tp, true);
      }
    }
    myTreeTable.getSelectionModel().setLeadSelectionIndex(rrr);
  }

  private final void expandAllMessages() {
    Message root = ( (Message) getRoot());
    TreePath path = new TreePath(getRoot());
    for (int i = 0; i < root.getArraySize(); i++) {
      if (root.getMessage(i) != null) {
        myTreeTable.expandPath(path.pathByAddingChild(root.getMessage(i)), true);
      }
    }
  }

}
