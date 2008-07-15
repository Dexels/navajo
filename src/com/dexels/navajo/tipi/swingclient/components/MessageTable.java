package com.dexels.navajo.tipi.swingclient.components;

import java.beans.*;
import java.io.*;
import java.lang.reflect.*;
import java.text.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.lazy.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.swingclient.components.sort.*;

import java.util.List;
import java.awt.Point;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */
public class MessageTable
    extends JTable
    implements MessageListener, CellEditorListener, Ghostable, ChangeMonitoring, PropertyChangeListener, ListSelectionListener, CopyCompatible {
  private final PropertyCellEditor myEditor = new PropertyCellEditor(this);

  private PropertyCellRenderer myRenderer = new PropertyCellRenderer();
  private Message myMessage = null;
  private final MessageTableModel myModel;
  private MouseAdapter headerMouseAdapter = null;
  private final ArrayList actionListeners = new ArrayList();
  private boolean doRepaint = true;
  private Map columnAttributes;
  private final Map rowColorMap = new HashMap();
  protected String columnPathString = null;
  private boolean changed;
  protected boolean savePathJustChanged = false;
  private JScrollPane myScroll = null;
  private boolean autoStoreSizes = false;
  private String primaryKeyColumn = null;
  private boolean showHeader = true;
  private final ArrayList changelisteners = new ArrayList();
  private boolean enabled;
  private boolean ghosted;
  private MessageTableFooter tableFooter = null;
  protected final Map columnSizeMap = new HashMap();
  private final HashMap cachedColumns = new HashMap();
  private final Map replacementMap = new HashMap();
  private final TableSorter mySorter;
  private boolean refreshAfterEdit = false;
  private AdvancedMessageTablePanel topLevelParent;
  private EditRowDialog bd = null;

  private final ArrayList columnDividers = new ArrayList();

private Component myCurrentEditingComponent;
	
  public MessageTable() {
    setAutoCreateColumnsFromModel(false);
    myModel = new MessageTableModel();
//    myModel.setMessageTable(this);
    mySorter = new TableSorter(myModel);
    setModel(mySorter);
    
    setColumnModel(new MessageTableColumnModel());
    setDefaultEditor(Property.class, myEditor);
    setDefaultRenderer(Property.class, myRenderer);
    setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    setPreferredScrollableViewportSize(getPreferredSize());
//    setSurrendersFocusOnKeystroke(true);
    getTableHeader().setDefaultRenderer(new CustomTableHeaderRenderer());
    headerMouseAdapter = mySorter.addMouseListenerToHeaderInTable(this);

    this.addCellEditorListener(this);
    this.getTableHeader().setReorderingAllowed(false);
    addMouseListener(new MouseAdapter() {
      public final void mouseClicked(MouseEvent m) {
        if (m.getClickCount() > 1) {
          fireActionEvent();
        }
      }
    });
    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if ("Left".equals(e.getKeyText(e.getKeyCode())) || "Right".equals(e.getKeyText(e.getKeyCode()))) {
          if (isCellEditable(getSelectedRow(), getSelectedColumn())) {
            editCellAt(getSelectedRow(), getSelectedColumn());
          }
        }
      }
    });
    addFocusListener(new FocusListener() {
      public void focusLost(FocusEvent e) {
    	  System.err.println("TABLE: lOST FOCUS: Partner: "+e.getOppositeComponent());
  		 if(e.getOppositeComponent()==myCurrentEditingComponent) {
  			 System.err.println("INTERNAL!!!");
  			 return;
  		 }
    	  //  		refreshSelectedCell();
        if (isEditing()) {
//        	System.err.println("Stopping edit!");
          Property p = myEditor.getProperty();
          myEditor.updateProperty();
//          try {
//			System.err.println("Path: "+p.getFullPropertyName());
//		} catch (NavajoException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//          if( !(e.getSource() instanceof MessageTable) &&  p!=null && !Property.SELECTION_PROPERTY.equals(p.getType())) {
//        	  // DON'T DO THIS FOR SELECTION PROPERTIES.
//        	  System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> STOP EDITING: " + e.getSource());
//        	  myEditor.stopCellEditing();
//          } 
        }
      }

	public void focusGained(FocusEvent e) {
		System.err.println("TABLE: GAINED FOCUS"+e.getOppositeComponent());
//		refreshSelectedCell();
	}
    });
    setupTableActions();
  }

  public void setShowRowHeaders(boolean b) {
    myModel.setShowRowHeaders(b);
  }

  public boolean isShowingRowHeaders() {
    return myModel.isShowingRowHeaders();
  }

  public void setTopLevelParent(AdvancedMessageTablePanel amtp) {
    topLevelParent = amtp;
  }

  public void determineMinumumColumnWidth(int column) {
    if (getMessage() != null) {
      int min = 0;
      for (int i = 0; i < getMessage().getArraySize(); i++) {
        Message cur = getMessage().getMessage(i);
        if (cur != null) {
          Property p = cur.getProperty(getColumnId(column));
          if (p != null) {
            if (!p.getType().equals(Property.SELECTION_PROPERTY)) {
              String value = p.getValue();
              if (value != null) {
                int w = getStringWidth(value);
                if (w > min) {
                  min = w + 4;
                }
              }
            }
            else {
              try {
                ArrayList sels = p.getAllSelections();
                for (int j = 0; j < sels.size(); j++) {
                  Selection s = (Selection) sels.get(j);
                  int w = getStringWidth(s.getName());
                  if (w > min) {
                    min = w + 4;
                  }
                }
              }
              catch (NavajoException ne) {
//                System.err.println("Whoops something wrong...");
              }
            }
          }
        }
      }
//      System.err.println("Setting: " + min);
      if (min > 0) {
        this.setColumnWidth(column, min);
        createDefaultFromModel(getMessage());
      }
    }
  }

  class EditRowDialog
      extends JDialog {
    MessageTable myTable;
    private Message m, backup;
    JScrollPane scroll = new JScrollPane();
    JPanel mp = new JPanel();
    private int row = 0;

    public EditRowDialog(MessageTable t, int row, Frame parent) {
      super(parent);
      try {
        myTable = t;
        m = t.getSelectedMessage();
        backup = m.copy();
        initUI();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }

    public EditRowDialog(MessageTable t, int row, Dialog parent) {
      super(parent);
      try {
        myTable = t;
        m = t.getSelectedMessage();
        backup = m.copy();
        initUI();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }

    private boolean anythingChanged() {
      Component[] comps = mp.getComponents();
      for (int i = 0; i < comps.length; i++) {
        if (GenericPropertyComponent.class.isInstance(comps[i])) {
          GenericPropertyComponent gpc = (GenericPropertyComponent) comps[i];
          if (gpc.hasChanged()) {
            return true;
          }
        }
      }
      return false;
    }

    private void nextRow() {
      try {
        if (anythingChanged() && topLevelParent != null) {
//          System.err.println("Something got changed!!");
          topLevelParent.getSelectedMessage().getProperty("Update").setValue("true");
          topLevelParent.getChangedMessages().add(topLevelParent.getSelectedMessage());
        }
        if (myTable.getSelectedRow() + 1 < myTable.getRowCount()) {
          row = myTable.getSelectedRow() + 1;
          myTable.setRowSelectionInterval(row, row);
          m = myTable.getSelectedMessage();
          backup = m.copy();
          setMessage();
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }

    private void previousRow() {
      try {
        if (anythingChanged() && topLevelParent != null) {
//          System.err.println("Something got changed!!");
          topLevelParent.getSelectedMessage().getProperty("Update").setValue("true");
          topLevelParent.getChangedMessages().add(topLevelParent.getSelectedMessage());
        }
        if (myTable.getSelectedRow() > 0) {
          row = myTable.getSelectedRow() - 1;
          myTable.setRowSelectionInterval(row, row);
          m = myTable.getSelectedMessage();
          backup = m.copy();
          setMessage();
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }

    }

    private void save() {
      if (myTable.getSelectedRow() > -1) {
        row = myTable.getSelectedRow();
      }
      if (topLevelParent != null) {
        if (m.getProperty("Update") != null) {
          m.getProperty("Update").setValue("true");
          topLevelParent.getChangedMessages().add(m);
          topLevelParent.setStateUpdated();
          topLevelParent.commit();
          if (!topLevelParent.hasConditionErrors()) {
            try {
              backup = m.copy();
            }
            catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
    }

    public void setMessage() {
      m = myMessage.getMessage(row);
      if(topLevelParent!=null) {
          topLevelParent.setRowSelectionInterval(row, row);
      }
      final MessageTableModel mtm = getMessageModel();
      mp.removeAll();
      for (int i = 0; i < mtm.getColumnCount(); i++) {
        String columnId = mtm.getColumnId(i);
        if (columnId == null) {
          continue;
        }
        Property current = m.getProperty(columnId);
        if (current == null) {
          continue;
        }
        GenericPropertyComponent gpc = new GenericPropertyComponent();
        gpc.setLabelIndent(100);
        //gpc.setForcedTotalWidth(scroll.getWidth() - 20);
//        gpc.setHorizontalScrolls(false);
        gpc.setProperty(current);
        gpc.addPropertyEventListener(new PropertyEventListener() {
          public void propertyEventFired(Property p, String type, Validatable v, boolean internal) {
            mtm.fireTableRowsUpdated(getSelectedRow(), getSelectedRow());
          }
        });
        mp.add(gpc, new GridBagConstraints(0, i, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
      }
//      revalidate();
//      repaint();
      this.dispatchEvent(new ComponentEvent(this, ComponentEvent.COMPONENT_RESIZED));
    }

    public void initUI() {
      try {
        getContentPane().setLayout(new BorderLayout());

        JToolBar tb = new JToolBar();

        getContentPane().add(scroll, BorderLayout.CENTER);
        scroll.getViewport().add(mp);
        getContentPane().add(tb, BorderLayout.SOUTH);
        mp.setLayout(new GridBagLayout());
        this.setSize(new Dimension(450, 350));
        tb.setFloatable(false);
        final JDialog finbd = this;
        JButton cancelButton = new JButton(new ImageIcon(UserInterface.class.getResource("components/cancel.png")));
        JButton okButton = new JButton(new ImageIcon(UserInterface.class.getResource("components/ok.png")));

        JButton saveButton = new JButton(new ImageIcon(UserInterface.class.getResource("components/save.png")));
        JButton nextRowButton = new JButton(new ImageIcon(UserInterface.class.getResource("components/next-browse.gif")));
        JButton previousRowButton = new JButton(new ImageIcon(UserInterface.class.getResource("components/previous-browse.gif")));

//        nextRowButton.setMargin(new Insets(7, 7, 7, 7));
//        previousRowButton.setMargin(new Insets(7, 7, 7, 7));
        tb.add(previousRowButton);
        tb.add(nextRowButton);
        tb.add(saveButton);
        tb.add(okButton);
        tb.add(cancelButton);
        tb.setOrientation(JToolBar.HORIZONTAL);
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.RIGHT);
        tb.setLayout(fl);

        okButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            save();
            finbd.setVisible(false);
          }
        });

        nextRowButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            nextRow();
          }
        });

        previousRowButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            previousRow();
          }
        });
        saveButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            save();
          }
        });

        cancelButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            ArrayList props = m.getAllProperties();
            for (int i = 0; i < props.size(); i++) {
              Property current = (Property) props.get(i);
              Property back = backup.getProperty(current.getName());
              if (back == null) {
                System.err.println("Weird. Property not found in backup");
                continue;
              }
              current.setValue(back.getValue());
              if (current.getType().equals(Property.SELECTION_PROPERTY)) {
                try {
                  ArrayList al = back.getAllSelectedSelections();
                  current.clearSelections();
                  for (int j = 0; j < al.size(); j++) {
                    Selection currentsel = (Selection) al.get(j);
                    System.err.println("Selected selection in backup: " + currentsel.getName() + " val: " + currentsel.getValue());
                    Selection sss = current.getSelection(currentsel.getName());
                    if (sss != null) {
                      current.setSelected(sss);
                    }
                  }
                }
                catch (NavajoException ex) {
                  ex.printStackTrace();
                }
              }
            }
            finbd.setVisible(false);
          }
        });

        final MessageTableModel mtm = getMessageModel();
        for (int i = 0; i < mtm.getColumnCount(); i++) {
          String columnId = mtm.getColumnId(i);
          if (columnId == null) {
            continue;
          }
          Property current = m.getProperty(columnId);
          if (current == null) {
            continue;
          }
          GenericPropertyComponent gpc = new GenericPropertyComponent();
          gpc.setLabelIndent(100);
          //gpc.setForcedTotalWidth(scroll.getWidth() - 20);
//          gpc.setHorizontalScrolls(false);
          gpc.setProperty(current);
          gpc.addPropertyEventListener(new PropertyEventListener() {
            public void propertyEventFired(Property p, String type, Validatable v, boolean internal) {
              mtm.fireTableRowsUpdated(getSelectedRow(), getSelectedRow());
            }
          });
          mp.add(gpc, new GridBagConstraints(0, i, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
        }
//        revalidate();
//        repaint();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void showEditDialog(String title, int row) throws NavajoException {
//    System.err.println("Entering showEditDialog");
    setRowSelectionInterval(row, row);
    int labelindent = 170;
    Container toplevel = getTopLevelAncestor();
    if (toplevel == null) {
      return;
    }
    if (Dialog.class.isInstance(toplevel)) {
      bd = new EditRowDialog(this, row, (Dialog) toplevel);
    }
    if (Frame.class.isInstance(toplevel)) {
      bd = new EditRowDialog(this, row, (Frame) toplevel);
    }
    if (bd == null) {
      return;
    }
    bd.setModal(true);
    final Message m = getSelectedMessage();
    if (m == null) {
      return;
    }

    final Message backup = m.copy();

//    System.err.println("Showing dialog");
    bd.setTitle(title);
//    bd.pack();
    bd.setLocationRelativeTo(this.getTopLevelAncestor());
    bd.setVisible(true);
    int[] sel = getSelectedRows();

    fireDataChanged();
    for (int i = 0; i < sel.length; i++) {
      getSelectionModel().addSelectionInterval(sel[i], sel[i]);
    }
    fireDataChanged();
  }

  private int getStringWidth(String s) {
    if (s != null) {
      FontMetrics m = getFontMetrics(getFont());
      return m.charsWidth(s.toCharArray(), 0, s.length());
    }
    else {
      return -1;
    }
  }

  public void setHighColor(Color c) {
    myRenderer.setHighColor(c);
  }

  public void setLowColor(Color c) {
    myRenderer.setLowColor(c);
  }

  public void setSelectedColor(Color c) {
    myRenderer.setSelectedColor(c);
  }

  public Color getSelectedColor() {
	  return myRenderer.getSelectedColor();
  }
  
  public Color getHighColor() {
	  return myRenderer.getHighColor();
  }
  
  public Color getLowColor() {
	  return myRenderer.getLowColor();
  } 
  
  public boolean isSortingAllowed() {
    return isSortingAllowed;
  }

  public void setSortingAllowed(boolean b) {
    isSortingAllowed = b;
  }

  private int headerHeight = 10;
  private boolean isSortingAllowed = true;

  public int getHeaderHeight() {
//    System.err.println("Current headerHeight: " + headerHeight);
    return headerHeight;
  }

  public void setHeaderHeight(int h) {
    if (h > headerHeight) {
      headerHeight = h;
    }
  }

  public void resetHeaderHeight() {
    headerHeight = 0;
  }

  public void addChangeListener(ChangeListener c) {
    changelisteners.add(c);
  }

  public void removeChangeListener(ChangeListener c) {
    changelisteners.remove(c);
  }

  public void setMessageTableFooter(MessageTableFooter m) {
    tableFooter = m;
  }

  public MessageTableFooter getMessageTableFooter() {
    return tableFooter;
  }

  public void exportTable_OLD(String filename, String delimiter) {
    try {
      File f = new File(filename);
      FileWriter fw = new FileWriter(f);
      int cols = getColumnCount();
      String header = "";
      for (int i = 0; i < cols; i++) {
        header = header + getColumnId(i) + delimiter;
      }
      fw.write(header + "\n");
      for (int j = 0; j < getRowCount(); j++) {
        String data = "";
        for (int k = 0; k < cols; k++) {
          String value = getValueAt(j, k).toString();
          if ("null".equals(value) || value == null) {
            value = "";
          }
          data = data + value + delimiter;
        }
        if (j == getRowCount() - 1) {
          fw.write(data);
        }
        else {
          fw.write(data + "\n");
        }
      }
      fw.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void exportTable(String filename, String delimiter) {
    File f = new File(filename);
    FileWriter fw = null;
    try {
      fw = new FileWriter(f);
      exportTable(fw, delimiter);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      if (fw != null) {
        try {
          fw.close();
        }
        catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  public void exportTable(FileWriter fw, String delimiter) {
    exportTable(fw, delimiter, true);
  }

  public void exportTable(FileWriter fw, String delimiter, boolean useHeader) {
    try {
      Message data = getMessageAsPresentedOnTheScreen(false);
      int cols = getColumnCount();
      String header = "";
      for (int i = 0; i < cols; i++) {
        header = header + getColumnId(i) + delimiter;
      }
      if (useHeader) {
        fw.write(header + "\n");
      }
      SimpleDateFormat navajoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      for (int i = 0; i < data.getArraySize(); i++) {
        String line = "";
        ArrayList props = data.getMessage(i).getAllProperties();
        boolean nonNullFound = false;
        for (int j = 0; j < props.size(); j++) {
          String value = "";
          Property p = (Property) props.get(j);
          if (p.getType().equals(Property.SELECTION_PROPERTY)) {
            value = p.getSelected().getValue();
          }
          else {
                        // value = p.getValue();
                         Object val = p.getTypedValue();
                        // System.err.println("TYPE: "+p.getType()+" val: "+p.getValue()+" :::: "+val);
                         if (val instanceof Date) {
                             ///System.err.println("PArsing date: ");
                            value = navajoDateFormat.format((Date)val);
                            //System.err.println("Valllll: "+value);
                         } else {
                            value = "" + val;
                        }
                    }
          if (value == null || value.equals("null")) {
//            System.out.println("Null priperty at index: "+i+","+j+"");
//            data.getMessage(i).write(System.err);
            value = "";
          }
          else {
            value = value.replaceAll(delimiter, " ");
            nonNullFound = true;
          }
          line = line + value + delimiter;
        }
        if (nonNullFound) {
          if (i == data.getArraySize() - 1) {
            fw.write(line);
          }
          else {
            fw.write(line + "\n");
          }
        }
        else {
          System.err.println("Skipped an all-null line.");
        }
      }
      fw.write("\n");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void setScrollPane(JScrollPane jp) {
    myScroll = jp;
  }

  public final Color getRowColor(int row) {
    if (row >= myModel.getRowCount()) {
      return Color.green;
    }
    int i = mapRowNumber(row);
    Integer in = new Integer(i);
    Color c = (Color) rowColorMap.get(in);
//    System.err.println("Looking for color... "+in);
    if (c == null && !rowColorMap.containsKey(in)) {
      createRowColor(i);
      return (Color) rowColorMap.get(in);
    }
    else {
      return c;
    }
  }

  // Only used from mockup...
  public final void setRenderer(PropertyCellRenderer tr) {
//    System.err.println("WHooze touching myRenderer!! " + tr.getClass());
    myRenderer = tr;
  }

  private final void createRowColor(int row) {
    Color c;

    int i = mapRowNumber(row);
    Message m = getMessageRow(i);
    Iterator it = columnAttributes.keySet().iterator();
    if (it == null || !it.hasNext()) {
      setRowColor(i, null);
    }
    while (it.hasNext()) {

      String key = (String) it.next();
      Property p = m.getProperty(key);
//      System.err.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~>>> Looking for color of row: " + key);
      if (p != null) {
        ColumnAttribute ca = (ColumnAttribute) columnAttributes.get(key);
        if (ca != null) {
//          System.err.println("Got column atributes");
          if (ca.getType().equals(ColumnAttribute.TYPE_ROWCOLOR)) {
            String color = ca.getParam(p.getValue());
            if (color != null) {
//              System.err.println("Found color: " + color);
              Color clr = Color.decode(color);
              setRowColor(i, clr);
            }
            else {
              setRowColor(i, null);
            }
          }
          if (ca.getType().equals(ColumnAttribute.TYPE_FREEROWCOLOR)) {
            Set s = ca.getParamKeys();
            Iterator iter = s.iterator();
            while (iter.hasNext()) {
              String itkey = (String) iter.next();
              if (p.getValue().indexOf(itkey) >= 0) {
                String color = ca.getParam(itkey);
                if (color != null) {
                  Color clr = Color.decode(color);
                  setRowColor(i, clr);
                }
                else {
                  setRowColor(i, null);
                }
              }
            }
          }

        }
        else {
          setRowColor(i, null);
        }
      }
    }
  }

  public final void setRowColor(int row, Color c) {
    int i = mapRowNumber(row);
    if (c == null) {
    }
    rowColorMap.put(new Integer(i), c);
  }

  public MessageTableModel getMessageModel() {
    return myModel;
  }

  public final void resetColorMap() {
    rowColorMap.clear();
  }

  public final void fireDataChanged() {
     ( (TableSorter) getModel()).tableChanged(new TableModelEvent(getModel()));
  }

  public final void fireTableStructureChanged() {
    myModel.fireTableStructureChanged();
  }

  public void setCachedSelectionColumn(String name, String new_name, String column_name) {
    // Alleen nog met landen, dit moet worden uitgebreid in MainFrame!!!
    cachedColumns.put(name, SwingClient.getUserInterface().getCachedSelectionProperty(new_name));
    this.addColumn(new_name, column_name, true);
    this.removeColumn(name);
    replacementMap.put(new_name, name);

  }

  private final void replaceProperties() {
    try {
      Message m = getMessage();
      if (cachedColumns.isEmpty()) {
        return;
      }
      for (int i = 0; i < m.getArraySize(); i++) {
        Message cur = m.getMessage(i);
        Iterator it = cachedColumns.keySet().iterator();
        /** Todo Property.copy() implementeren in Document */while (it.hasNext()) {
          String key = (String) it.next();
          Property replaced = cur.getProperty(key);
          Property p = (Property) cachedColumns.get(key);
          Property q = NavajoFactory.getInstance().createProperty(NavajoFactory.getInstance().createNavajo(), p.getName(), "1", p.getDescription(), "in");
          ArrayList a = p.getAllSelections();
          for (int j = 0; j < a.size(); j++) {
            Selection s = (Selection) a.get(j);
            Selection r = NavajoFactory.getInstance().createSelection(NavajoFactory.getInstance().createNavajo(), s.getName(), s.getValue(), false);
            if (replaced != null) {
              if (r.getValue() != null && replaced.getValue() != null && r.getValue().equals(replaced.getValue().trim())) {
                r.setSelected(true);
              }
            }
            q.addSelection(r);
          }
//        try {
//          System.err.println("----------------------------------->>>>,<<< Replaced: " + replaced.getValue() + ", key is: '" + key + "'");
//          if(replaced != null){
//            System.err.println("Setting cache property selection to: " + replaced.getValue());
//            p.clearSelections();
//            p.setSelected(replaced.getValue());
//          }
//        }
//        catch (NavajoException ex) {
//        }
          if (q != null) {
            cur.addProperty(q);
          }
        }
      }

      fireDataChanged();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*
   * I really don't know, using this from tipi to
   * suppres the auto-saving that happens in the setMessage(m) just below this one
   */
  public void setSavePathJustChanged(boolean cgn) {
    savePathJustChanged = cgn;
  }

  public void setMessage(Message m) {
	if(myMessage!=null) {
		myMessage.removePropertyChangeListener(this);
	}
    try {
      if (!savePathJustChanged) {
        saveColumnsNavajo();
      }
      else {
        savePathJustChanged = false;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      System.err.println("WARNING: Could not save columns in MessageTable.setMessage(Message m)");
    }
    changed = false;
    if (m == null) {
      myMessage = null;
      return;
    }

    MessageTableModel mtm = getMessageModel();
    mtm.clearMessage();
    reallocateIndexes();
    mtm.setMessage(m);
    reallocateIndexes();
    if (m.getArraySize() > 0) {
//      resizeColumns(m);
    }
    myMessage = m;
    
    
    
    resetColorMap();
    if (m.getArraySize() > 0) {
      mtm.fireTableRowsInserted(0, m.getArraySize() - 1);
    }
    if (!showHeader) {
      if (myScroll != null) {
        myScroll.setColumnHeader(null);
      }
    }

//    System.err.println("ColumnM: "+getColumnModel().getTotalColumnWidth());

    createDefaultColumnsFromMessageModel();
//    setPreferredScrollableViewportSize(new Dimension(getColumnModel().getTotalColumnWidth(), getRowHeight() * getRowCount()));
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        replaceProperties();
      	  }
    });
		
    if (bd != null && bd.isShowing()) {
      bd.setMessage();
    }
    

	myMessage.addPropertyChangeListener(this);

  }

  public void createDefaultColumnsFromMessageModel() {
	createDefaultColumnsFromModel();
  }

public void updateTableSize() {
//	   createDefaultColumnsFromModel();
	   setPreferredScrollableViewportSize(new Dimension(getColumnModel().getTotalColumnWidth(), getRowHeight() * getRowCount()));
  }
  
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    super.paintComponent(g);
    Stroke s = g2.getStroke();
    for (int i = 0; i < columnDividers.size(); i++) {
      ColumnDivider cd = (ColumnDivider) columnDividers.get(i);
      int x = getXofColumn(cd.index);
      g2.setStroke(new BasicStroke(cd.width));
      g.drawLine(x, 0, x, getSize().height);
    }
    g2.setStroke(s);
  }


  private int getXofColumn(int index) {
    int count = 0;
    if (index > getColumnCount()) {
      return 0;
    }
    for (int i = 0; i < index; i++) {
      count += getColumnWidth(i);
    }
    return count;
  }

  public void clearColumnDividers() {
    columnDividers.clear();
  }

  public void addColumnDivider(int index, float width) {
    ColumnDivider cd = new ColumnDivider(index, width);
    columnDividers.add(cd);
  }

  public final void repaintHeader() {
    getTableHeader().repaint();
  }

  /**
   * Tsjezus wat lelijk... edit:arnoud.. kots
   */
  protected void configureEnclosingScrollPane() {
    if (showHeader) {
      super.configureEnclosingScrollPane();
      return;
    }
    Container p = getParent();
    if (p instanceof JViewport) {
      Container gp = p.getParent();
      if (gp instanceof JScrollPane) {
        JScrollPane scrollPane = (JScrollPane) gp;
        // Make certain we are the viewPort's view and not, for
        // example, the rowHeaderView of the scrollPane -
        // an implementor of fixed columns might do this.
        JViewport viewport = scrollPane.getViewport();
        if (viewport == null || viewport.getView() != this) {
          return;
        }
        scrollPane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        scrollPane.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
      }
    }
  }

  protected final void setSavedColumnSizes() {
    try {
      loadColumnsNavajo();
    }
    catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
  }

  public void resizeColumns(final Message m) {
//    final MessageTableModel mtm = getMessageModel();
	  if(!SwingUtilities.isEventDispatchThread()) {
		  throw new IllegalStateException("should be called from event thread");
	  }
  			if (columnPathString == null) {
			  setDefaultColumnSizes(m);
			}
			else {
			  File columnFile = new File(columnPathString);
			  if (columnFile.exists()) {
			    setSavedColumnSizes();
			  }
			}
	
	}
  
  public void loadColumnSizes() {
	  if(!SwingUtilities.isEventDispatchThread()) {
		  throw new IllegalStateException("should be called from event thread");
	  }

	  ( (MessageTableColumnModel) getColumnModel()).loadSizes(columnSizeMap);
			
  }

  public final void createDefaultFromModel(Message m) {
	  if(!SwingUtilities.isEventDispatchThread()) {
		  throw new IllegalStateException("should be called from event thread");
	  }
	  
//    System.err.println("in createDefaultFromModel()");
    MessageTableColumnModel tcm = new MessageTableColumnModel();
    tcm.addColumnModelListener(tableFooter);
    if (tableFooter != null) {
      tableFooter.setTable(this);
      tableFooter.setColumnModel(tcm);
      tableFooter.setVisible(true);
    }
    tcm.setMessage(m, myModel);
//    tcm.loadSizes(columnSizeMap);
//    for (int i = 0; i < tcm.getColumnCount(); i++) {
//		TableColumn tc = tcm.getColumn(i);
//		System.err.println("Column: "+i+" : "+tc.getPreferredWidth()+"  "+tc.getWidth());
//	}
    setColumnModel(tcm);
    setDefaultEditor(Object.class, myEditor);
  }

  public final void setDefaultColumnSizes(Message m) {
    if (m.getArraySize() == 0) {
      MessageTableColumnModel tcm = new MessageTableColumnModel();
      createDefaultFromModel(null);
      setMessage(m);
      setColumnModel(tcm);
    }
    else {
      createDefaultFromModel(m);
      int maxadvance = -1;
      MessageTableColumnModel tcm = (MessageTableColumnModel) getColumnModel();
//      for (int i = 0; i < tcm.getColumnCount(); i++) {
//        TableColumn tc = tcm.getColumn(i);
      if (myModel.isShowingRowHeaders()) {
        tcm.getColumn(0).setWidth(45);
        tcm.getColumn(0).setMaxWidth(45);
        tcm.getColumn(0).setMinWidth(45);
        
      }
//      }
    }
  }

  public final void setEqualColumnSizes() {
      int width = getWidth();
//      System.err.println("Width: "+width);
//      System.err.println("columncount: "+getColumnCount());
      int divwidth = 50;
      if (width > 0) {
          divwidth = width/getColumnCount();
      }
       MessageTableColumnModel tmcm = (MessageTableColumnModel)getColumnModel();
      for (int i = 0; i < tmcm.getColumnCount(); i++) {
        TableColumn tc = tmcm.getColumn(i);
        tc.setPreferredWidth(divwidth);
//        tc.setMinWidth(70);
      }
//      fireTableStructureChanged();
  }


  public void setSavedColumnTitle(String column, String title){
	  FileInputStream fis =  null;
    try{
      File columnFile = new File(columnPathString);
      if (columnFile.exists()) {
//        System.err.println("Getting coldefs..");
        fis = new FileInputStream(columnPathString);
        Navajo n = NavajoFactory.getInstance().createNavajo(fis);
        Message cdef = n.getMessage("columndef");
        if (cdef == null) {
          if (columnFile != null && columnFile.exists()) {
            columnFile.delete();
            return;
          }
        }
//        System.err.println("Found " + cdef.getArraySize() + " columns");
        for(int i=0;i<cdef.getArraySize();i++){
          Message current = cdef.getMessage(i);
//          System.err.println("Checking message for column " + column);
//          current.write(System.err);

          Property id = current.getProperty("id");
          Property name = current.getProperty("name");
          if(id != null) {
            if(column.equals(id.getValue())){
              name.setValue(title);
//              System.err.println("Renamed column: " + column + " to " + title);
            }
          }
        }

        File f = new File(columnPathString);
        f.getParentFile().mkdirs();
        FileWriter fw = new FileWriter(f);
        n.write(fw);
        fw.flush();
        fw.close();
      }
    }catch(SecurityException e){
    	// whatever
    }catch(Exception e){
      e.printStackTrace();
    } finally {
    	if ( fis != null ) {
    		try {
				fis.close();
			} catch (IOException e) {
				// NOT INTERESTED.
			}
    	}
    }
  }

  public void loadColumnsNavajo() throws FileNotFoundException {
    try {
		File columnFile = new File(columnPathString);
		if ( !columnFile.exists() ) {
			return;
		}
		FileInputStream fis = new FileInputStream(columnPathString);
		Navajo n = NavajoFactory.getInstance().createNavajo(fis);
		Message cdef = n.getMessage("columndef");
		if (cdef == null) {
		  if (columnFile != null && columnFile.exists()) {
		    columnFile.delete();
		    return;
		  }
		}
		loadColumnsNavajo(n);
		try {
		  fis.close();
		}
		catch (IOException ex) {
		  System.err.println("Error closing columns file.");
		}
	} catch (SecurityException e) {
		// TODO Auto-generated catch block
	}
  }

  protected final void loadColumnsNavajo(Navajo n) {
    Message cdef = n.getMessage("columndef");
    removeAllColumns();
    for (int i = 0; i < cdef.getArraySize(); i++) {
      Message m = cdef.getMessage(i);
      String id = (String) m.getProperty("id").getValue();
      String name = (String) m.getProperty("name").getValue();
      String editable = (String) m.getProperty("editable").getValue();
      addColumn(id, name, "true".equals(editable));
    }
    createDefaultColumnsFromModel();

//    MessageTableColumnModel tcm = (MessageTableColumnModel) getColumnModel();
    final int sortedColumn = Integer.parseInt( (String) cdef.getProperty("sortedColumn").getValue());
    final boolean sortedDirection = cdef.getProperty("sortedDirection").getValue().equals("true");
   // System.err.println("sortedDirection: " + cdef.getProperty("sortedDirection").getValue());
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				System.err.println("BEWARE OF THE WOBBLE!");
				doSort(sortedColumn, sortedDirection);
			}
		});
    int substractCount = 0;
    if (myModel.isShowingRowHeaders()) {
      substractCount = 1;
    }
    for (int i = 0; i < cdef.getArraySize() + substractCount; i++) {
      if (i == 0 && myModel.isShowingRowHeaders()) {
               //System.err.println("Setting editcolumn to width: 45");
        setColumnWidth(i, 45);
      }
      else {
        Message m = cdef.getMessage(i - substractCount);
        int width = Integer.parseInt( (String) m.getProperty("width").getValue());
         //System.err.println("Setting " + i + " to width: " + width);
        setColumnWidth(i, width);
      }
    }
  }

  public final void setColumnAttributes(Map m) {
//    System.err.println("MessageTable columnAttributes set.");
    columnAttributes = m;
  }

  public Map getColumnAttributes() {
    return columnAttributes;
  }

  public final int addColumn(String id, String title, boolean editable) {
    MessageTableModel mtm = getMessageModel();
    return mtm.addColumn(id, title, editable);
  }

  public final int addColumn(String id, String title, boolean editable, int size) {
    MessageTableModel mtm = getMessageModel();
    int index = mtm.addColumn(id, title, editable);
    setColumnWidth(index, size);
    return index;
//    setColumnWidth();
  }

  public void setTypeHint(String id, String type) {
    MessageTableModel mtm = getMessageModel();
    mtm.setTypeHint(id, type);
  }

  public String getTypeHint(String id) {
    MessageTableModel mtm = getMessageModel();
    String type = mtm.getTypeHint(id);
//    System.err.println("Getting type hint: " + type);
    return type;
  }

  public final void messageChanged() {
    resetColorMap();
    MessageTableModel mtm = getMessageModel();
    mtm.messageChanged();
  }

  public final void removeColumn(String id) {
    resetColorMap();
    MessageTableModel mtm = getMessageModel();
    mtm.removeColumn(id);
  }

  public final void removeAllColumns() {
    getMessageModel().removeAllColumns();
  }

  public final void addListSelectionListener(ListSelectionListener l) {
    getSelectionModel().addListSelectionListener(l);
  }

	public void removeListSelectionListener(ListSelectionListener l) {
		  getSelectionModel().removeListSelectionListener(l);
	}

  
  public final void addCellEditorListener(CellEditorListener ce) {
    myEditor.addCellEditorListener(ce);
  }

  public final void removeCellEditorListener(CellEditorListener ce) {
    myEditor.removeCellEditorListener(ce);
  }

  public void centerInParent(Container parent, JDialog jd) {
    int x;
    int y;
//      Container parent = jd.getParent();
    Point topLeft = parent.getLocationOnScreen();
    Dimension parentSize = parent.getSize();
    Dimension ownSize = jd.getSize();
    if (parentSize.width < ownSize.width) {
      x = ( (parentSize.width - ownSize.width) / 2) + topLeft.x;
    }
    else {
      x = topLeft.x;
    }
    if (parentSize.height < ownSize.height) {
      y = ( (parentSize.height - ownSize.height) / 2) +

          topLeft.y;
    }
    else {
      y = topLeft.y;
    }
   // System.err.println("Setting to: " + x + "- " + y);
    jd.setLocation(x, y);
    jd.requestFocus();
  }

  public Message getMessageRow(int row) {
    MessageTableModel mtm = getMessageModel();
    return mtm.getMessageRow(mapRowNumber(row));
  }

  public int getRowCount() {
    MessageTableModel mtm = getMessageModel();
    if (mtm == null) {
      return 0;
    }
    return mtm.getRowCount();
  }

  public Message getSelectedMessage() {
    int s = getSelectedRow();
    if (s >= 0) {
      return getMessageRow(s);
    }
    return null;
  }

  public CellEditor getCurrentEditor() {
    return myEditor;
  }

  public int mapRowNumber(int row) {
    if (TableSorter.class.isInstance(getModel())) {
      TableSorter ts = (TableSorter) getModel();
      return ts.getRowIndex(row);
    }
    return row;
  }

  public final void rebuildSort() {
    resetColorMap();
    if (TableSorter.class.isInstance(getModel())) {
      TableSorter ts = (TableSorter) getModel();
      ts.checkModel();
    }
  }

  public boolean hasCachedSelectionProperties() {
    return replacementMap.size() > 0;
  }

  public ArrayList<Message> getSelectedMessages() {
    int rows = getRowCount();
    ArrayList<Message> selectedMsgs = new ArrayList<Message>();
    for (int i = 0; i < rows; i++) {
      if (isRowSelected(i)) {
        Message selected = getMessageRow(i);
        selectedMsgs.add(selected);
      }
    }
    if (selectedMsgs.size() < 1) {
      return null;
    }
    return selectedMsgs;
  }

  public final void messageLoaded(int startIndex, int endIndex, int newTotal) {
    MessageTableModel mtm = getMessageModel();
    mtm.messageLoaded(startIndex, endIndex, newTotal);
  }

  public Message getMessage() {
    return myMessage;
  }

//	public Binary getArrayMessageReport(Message m, String[] propertyNames, int[] columnWidths, String format) throws NavajoException {

  public Binary getTableReport(String format, String orientation, int[] margins) throws NavajoException {
	  Message m = getMessageAsPresentedOnTheScreen(false);
	  if(m==null) {
		  throw NavajoFactory.getInstance().createNavajoException("No message loaded, can not get message!");
	  }
      int count = getColumnModel().getColumnCount();
      if(isShowingRowHeaders()) {
    	  count--;
      }
	  int[] widths = new int[count];
	  String[] names = new String[count];
	  String[] titles = new String[count];
	  for (int i = 0; i < count; i++) {
		  int j = i;
		  if(isShowingRowHeaders()) {
			  j++;
		  }
		  TableColumn tt = getColumnModel().getColumn(j);
		  int width = tt.getWidth();
		  String name = getColumnId(j);
		  String title = getColumnName(j);
		  widths[i]=width;
		  names[i]=name;
		  titles[i]=title;
		  System.err.println("Adding width: "+width);
		  System.err.println("Adding name: "+name);
	  } 
	  
	  Binary result = NavajoClientFactory.getClient().getArrayMessageReport(m, names, titles, widths, format, orientation,margins);
	  return result;
  }
  
  public Message getMessageAsPresentedOnTheScreen(boolean includeInvisibleColumns) {
    if (myMessage == null) {
      return null;
    }
    Navajo newNavajo = NavajoFactory.getInstance().createNavajo();
    Message constructed = NavajoFactory.getInstance().createMessage(newNavajo, myMessage.getName(), Message.MSG_TYPE_ARRAY);
    for (int i = 0; i < getRowCount(); i++) {
      Message elt = this.getMessageRow(i);
      if (Message.MSG_DEFINITION.equals(elt.getType())) {
        continue;
      }
      Message newRow = NavajoFactory.getInstance().createMessage(newNavajo, constructed.getName(), Message.MSG_TYPE_ARRAY_ELEMENT);
      if (includeInvisibleColumns) {
        ArrayList ps = elt.getAllProperties();
        for (int j = 0; j < ps.size(); j++) {
          Property p = (Property) ps.get(j);

          if (p != null) {

            Property q = null;

            if (p.getType() == Property.SELECTION_PROPERTY && p.getCardinality().equals("+")) {
              try {
                q = NavajoFactory.getInstance().createProperty(newNavajo, p.getName(), "string", "", 255, p.getDescription(), "out");
                ArrayList sels = p.getAllSelectedSelections();
                String value = ( (Selection) sels.get(0)).getName();
                for (int g = 1; g < sels.size(); g++) {
                  value = value + "/" + ( (Selection) sels.get(g)).getName();
                }
                q.setValue(value);

              }
              catch (Exception e) {
                e.printStackTrace();
              }
            }
            else {
              q = p.copy(newNavajo);
              q.setAnyValue(p.getTypedValue());
              try {
                q.setType(p.getEvaluatedType());
              }
              catch (NavajoException e) {
                e.printStackTrace();
                q.setType(Property.STRING_PROPERTY);
              }
            }

            newRow.addProperty(q);
          }
        }
      }
      else {
        for (int j = 0; j < getColumnCount(); j++) {
          String prop = getColumnId(j);
          Property p = elt.getProperty(prop);
          if (p != null) {
            Property q = null;

            if (p.getType() == Property.SELECTION_PROPERTY && p.getCardinality().equals("+")) {
              try {
                q = NavajoFactory.getInstance().createProperty(newNavajo, p.getName(), "string", "", 255, p.getDescription(), "out");
                ArrayList sels = p.getAllSelectedSelections();
                String value = ( (Selection) sels.get(0)).getName();
                for (int g = 1; g < sels.size(); g++) {
                  value = value + "/" + ( (Selection) sels.get(g)).getName();
                }
                q.setValue(value);

              }
              catch (Exception e) {
                e.printStackTrace();
              }
            }
            else {
              q = p.copy(newNavajo);
              q.setAnyValue(p.getTypedValue());
              try {
                q.setType(p.getEvaluatedType());
              }
              catch (NavajoException e) {
                e.printStackTrace();
                q.setType(Property.STRING_PROPERTY);
              }
            }

            newRow.addProperty(q);
          }
        }
      }
      constructed.addElement(newRow);
    }
    return constructed;
  }



  public final void tableChanged(TableModelEvent parm1) {
    super.tableChanged(parm1);
  }

  public final void addActionListener(ActionListener e) {
    actionListeners.add(e);
  }

  public final void removeActionListener(ActionListener e) {
    actionListeners.remove(e);
  }

  public final void setHeaderVisible(boolean b) {
    showHeader = b;
    getTableHeader().setVisible(b);
    repaint();
  }
  public final boolean isHeaderVisible() {
	  return showHeader;
  }
  
  public final void fireActionEvent() {
    int r = getSelectedRow();
    Message m = getSelectedMessage();
    if (m != null) {
      for (int i = 0; i < actionListeners.size(); i++) {
        ActionListener current = (ActionListener) actionListeners.get(i);
        current.actionPerformed(new ActionEvent(this, r, m.getFullMessageName()));
      }
    }
  }

  public final void editingCanceled(ChangeEvent parm1) {
  }

  public void editingStopped(ChangeEvent parm1) {
	    Object oldVal = myEditor.getOldValue();
	    Property current = myEditor.getProperty();

	    if( current==null) {
	    	return;
	    }

	    try {
//	    Object oldVal = init.getTypedValue();
	    Object newVal = current.getTypedValue();
	    System.err.println("Current name: "+current.getName()+" oldVal: "+oldVal+" new: "+newVal);
	    if(oldVal==null) {
	    	if(newVal==null) {
	    		// nada
	    	} else {
	    		if(!newVal.equals(oldVal)) {
	    			fireChangeEvents(current, oldVal,newVal);
	    		}
	    	}
	    	
	    } else {
			if(!oldVal.equals(newVal)) {
				fireChangeEvents(current, oldVal,newVal);
			}
	    }
			} catch (NavajoException e1) {
				e1.printStackTrace();
			}

	    //System.err.println("Editing stopped, in MT");
	    changed = true;
//	    if (current != null && Property.SELECTION_PROPERTY.equals(current.getType())) {
//	      // a selection property
//	      if (replacementMap.containsKey(current.getName())) {
//	        //System.err.println("You're edititing a cached property");
//	        try {
//	          getSelectedMessage().getProperty( (String) replacementMap.get(current.getName())).setValue(current.getSelected().getValue());
//	        }
//	        catch (NavajoException ex3) {
//	          ex3.printStackTrace();
//	        }
//	      }
//	      try {
//	        Selection initSel = init.getSelected();
//	        Selection currentSel = current.getSelected();
//	        if (! (initSel != null && currentSel != null && initSel.getValue().equals(currentSel.getValue()))) {
////	          System.err.println("Ready for select");
//	          try {
//	            fireChangeEvents(init, current);
//	            return;
//	          }
//	          catch (NavajoException ex) {
//	            ex.printStackTrace();
//	          }
//	        }
//	      }
//	      catch (Exception ex1) {
//	        ex1.printStackTrace();
//	      }
//	    }
//	    else {
//	      if (init != null && current != null) {
//	        if (init.getValue() != null && current.getValue() != null) {
//	          if (!init.getValue().equals(current.getValue())) {
//	            try {
//	              fireChangeEvents(init, current);
//	            }
//	            catch (NavajoException ex) {
//	              ex.printStackTrace();
//	            }
//	          }
//	          else {
//	           // System.err.println("Ignoring equal..");
//	          }
//	        }
//	        else {
//	          try {
//	            fireChangeEvents(init, current);
//	          }
//	          catch (NavajoException ex) {
//	            ex.printStackTrace();
//	          }
//	        }
//	      }
//	    }
	    try {
//	      List props = myMessage.getRootDoc().refreshExpression();

	      /** @todo Think this one is redundant in MegaTables, as all tables
	       * will be updated anyway */
	      if (getRefreshAfterEdit()) {
	        // This *MAY* not be enough, as expressions may have references to properties
	        // outside this table. (Only important if these properties also have references
	        // to properties in this table.
//	        List props = myMessage.getRootDoc().refreshExpression();
//	        myMessage.refreshExpression();
//	        NavajoFactory.getInstance().getExpressionEvaluator().
//	        System.err.println("# of properties changed: "+props.size());


//	        getMessageModel().updateProperties(props);

	        updateExpressions();
//	        fireDataChanged();
	      }
//	            System.err.println("refreshed. Items changed: "+props.size());
//	            for (int i = 0; i < props.size(); i++) {
//	              Property currentProp = (Property)props.get(i);
//	             firePropertyChanged(currentProp);
//	            }
	    }
	    catch (Throwable ex2) {
	      ex2.printStackTrace();
	    }

	  }

  public void updateExpressions() throws NavajoException {
    List props = myMessage.getRootDoc().refreshExpression();
//        myMessage.refreshExpression();
//        NavajoFactory.getInstance().getExpressionEvaluator().
   // System.err.println("# of properties changed: " + props.size());

    getMessageModel().updateProperties(props);
  }

  public void stopCellEditing() {
	  if(myEditor!=null) {
		  myEditor.stopCellEditing();
	  }
  }
  
  public void updateProperties(List<Property> l) {
    getMessageModel().updateProperties(l);
  }

  public void setRefreshAfterEdit(boolean b) {
    refreshAfterEdit = b;
  }

  public boolean getRefreshAfterEdit() {
    return refreshAfterEdit;
  }

  /**
   * @deprecated
   * @param p
   */
  public void firePropertyChanged(Property p) {
    getMessageModel().firePropertyChanged(p,"value");
  }

  protected final void fireChangeEvents(Property p, Object oldValue, Object newValue) throws NavajoException {

	  if (changelisteners.size() == 0) {
		  return;
	  }
	  Map m = new HashMap();
	  m.put("name", p.getName());
	  m.put("row", new Integer(getSelectedRow()));
	  m.put("column", new Integer(getSelectedColumn()));
	  m.put("new", newValue);
	  m.put("old", oldValue);
	  m.put("path", p.getFullPropertyName());
	  m.put("message", p.getParentMessage());

	  for (int i = 0; i < changelisteners.size(); i++) {
		  ChangeListener cl = (ChangeListener) changelisteners.get(i);
		  ChangeEvent e = new ChangeEvent(m);
		  cl.stateChanged(e);
	  }

  }
  
  protected final void fireChangeEvents(Property init, Property current) throws NavajoException {

    if (changelisteners.size() == 0) {
      return;
    }
    Map<String,Object> m = new HashMap<String,Object>();
    m.put("name", current.getName());
    m.put("row", new Integer(getSelectedRow()));
    m.put("column", new Integer(getSelectedColumn()));
    m.put("new", current.getTypedValue());
    m.put("old", init.getTypedValue());
    m.put("path", current.getFullPropertyName());
    m.put("message", current.getParentMessage());
    m.put("property", current);

    for (int i = 0; i < changelisteners.size(); i++) {
      ChangeListener cl = (ChangeListener) changelisteners.get(i);
      ChangeEvent e = new ChangeEvent(m);
      cl.stateChanged(e);
    }
  }

  public boolean isGhosted() {
    return ghosted;
  }

  public final void setGhosted(boolean g) {
    ghosted = g;
    super.setEnabled(enabled && (!ghosted));
  }

  public final void setEnabled(boolean e) {
    enabled = e;
    super.setEnabled(enabled && (!ghosted));
  }

  public final void addPropertyFilter(String propName, Property value) {
    addPropertyFilter(propName, value, "==");
  }

  public final boolean hasPropertyFilters(){
  	return getMessageModel().hasPropertyFilters();
  }
  
  public final void addPropertyFilter(String propName, Property value, String operator) {
    getMessageModel().addPropertyFilter(propName, value, operator);
    ( (TableSorter) getModel()).reallocateIndexes();
  }

  public final void clearPropertyFilters() {
    getMessageModel().clearPropertyFilters();
    ( (TableSorter) getModel()).reallocateIndexes();
  }

  public void reallocateIndexes() {
     ( (TableSorter) getModel()).reallocateIndexes();
  }

  public final void performFilters() {
    getMessageModel().performFilters();
  }

  public final void removeFilters() {
    getMessageModel().clearPropertyFilters(); 
  }

  private boolean doRepaint() {
    return doRepaint;
  }

  private void setRepaint(boolean b) {
    doRepaint = b;
  }

  public final void setSortingState(int columnIndex, boolean ascending) {
    getMessageModel().setSortingState(columnIndex, ascending);
  }

  protected final void doSort(int column, boolean ascending) {
	  
    if (isEditing()) {
      return;
    }
    if (column < 0) {
      return;
    }
    if (column >= getColumnCount()) {
      return;
    }
    TableSorter ts = (TableSorter) getModel();
    ts.doSort(this, column, ascending);
  }

  public int getSortedColumn() {
    return myModel.getSortedColumn();
  }

  public boolean getSortingDirection() {
    return myModel.getSortingDirection();
  }

  public String getColumnId(int index) {
    return myModel.getColumnId(index);
  }

  public String getColumnName(int index) {
    if (index < getColumnCount()) {
      return myModel.getColumnName(index);
    }
    else {
      return null;
    }
  }

  public int getColumnWidth(int index) {
//    if (!SwingUtilities.isEventDispatchThread()) {
//      System.err.println("Illegal Thread: " + Thread.currentThread().getName());
//    }
    MessageTableColumnModel tc = (MessageTableColumnModel) getColumnModel();
    TableColumn tcm = tc.getColumn(index);
    return tcm.getWidth();
//    return tcm.getPreferredWidth();
  }

  public final void setColumnWidth(int index, int width) {
    if (index == 0 && myModel.isShowingRowHeaders()) {
      columnSizeMap.put(new Integer(index), new Integer(45));
    }
    else {
      columnSizeMap.put(new Integer(index), new Integer(width));
    }
  }

  public final void setColumnDefinitionSavePath(String path) {
    if (columnPathString != null && !columnPathString.equals(path)) {
    //  System.err.println("Setting savePAthJustChanged to  true!");
      savePathJustChanged = true;
    }
    columnPathString = path;
  }

// not pretty, but needed for v2
  public String getColumnDefinitionSavePath() {
    return columnPathString;
  }

  public void setReadOnly(boolean b) {
    myModel.setReadOnly(b);
//    setCellSelectionEnabled(!b);
  }

  public final void setAutoStoreColumnSizes(String path, boolean value) {
    if (value) {
      setColumnDefinitionSavePath(path);
      for (int i = 0; i < getColumnModel().getColumnCount(); i++) {
        TableColumn tc = this.getColumnModel().getColumn(i);
//        tc.addPropertyChangeListener(this);
        autoStoreSizes = true;
      }
    }
    else {
      for (int i = 0; i < getColumnModel().getColumnCount(); i++) {
        TableColumn tc = this.getColumnModel().getColumn(i);
//        tc.removePropertyChangeListener(this);
        autoStoreSizes = false;
      }
    }
  }

  public void removeColumnsNavajo() {
    try {
		try {
		  File f = new File(columnPathString);
		  String cps = columnPathString;
		  columnPathString = null;
		  if (f.exists()) {
		    f.delete();
		    this.setMessage(getMessage());
		  }
		  columnPathString = cps;
		}
		catch (Exception e) {
		  e.printStackTrace();
		}
	} catch (SecurityException e) {
		// TODO Auto-generated catch block
//		e.printStackTrace();
	}
  }

  public void saveColumnsNavajo() throws IOException, NavajoException {
    try {
		if (columnPathString == null) {
		  return;
		}
		Navajo cdef = saveColumnDefNavajo();
		File f = new File(columnPathString);
		int count = getColumnCount();
		if (count > 0 && getMessage() != null && getMessage().getArraySize() > 0) {
		  f.getParentFile().mkdirs();
		  FileWriter fw = new FileWriter(f);
		  cdef.write(fw);
		  fw.flush();
		  fw.close();
		}
		else {
//      System.err.println("WARNING: Did not save columns because I have none... or no rows");
		}
	} catch (SecurityException e) {
		// TODO Auto-generated catch block
//		e.printStackTrace();
	}
  }

  public Navajo saveColumnDefNavajo() throws NavajoException {
    Navajo cdef = NavajoFactory.getInstance().createNavajo();
    Message m = NavajoFactory.getInstance().createMessage(cdef, "columndef", Message.MSG_TYPE_ARRAY);
    cdef.addMessage(m);
    int sortedColumn = getSortedColumn();
    boolean sortedDirection = getSortingDirection();
    m.addProperty(NavajoFactory.getInstance().createProperty(cdef, "sortedColumn", Property.INTEGER_PROPERTY, "" + sortedColumn, 0, "", Property.DIR_IN));
    m.addProperty(NavajoFactory.getInstance().createProperty(cdef, "sortedDirection", Property.BOOLEAN_PROPERTY, "" + sortedDirection, 0, "", Property.DIR_IN));
    //System.err.println("Setting sort direction to: " + sortedDirection);
    int count = getColumnCount();
    int start = 0;
    if (myModel.isShowingRowHeaders()) {
      start = 1;
    }
    for (int i = start; i < count; i++) {
      String id = getColumnId(i);
      int width = getColumnWidth(i);
      String name = getColumnName(i);
      boolean editable = myModel.isColumnEditable(i);
      String ed = editable ? "true" : "false";
      Message col = NavajoFactory.getInstance().createMessage(cdef, "column");
      col.addProperty(NavajoFactory.getInstance().createProperty(cdef, "id", Property.STRING_PROPERTY, "" + id, 0, "", Property.DIR_IN));
      col.addProperty(NavajoFactory.getInstance().createProperty(cdef, "width", Property.INTEGER_PROPERTY, "" + width, 0, "", Property.DIR_IN));
      col.addProperty(NavajoFactory.getInstance().createProperty(cdef, "name", Property.STRING_PROPERTY, "" + name, 0, "", Property.DIR_IN));
      col.addProperty(NavajoFactory.getInstance().createProperty(cdef, "editable", Property.STRING_PROPERTY, "" + ed, 0, "", Property.DIR_IN));
      m.addMessage(col);
    }
    return cdef;
  }

  public Object getValueAt(int row, int column) {
    if (row >= getRowCount() || column >= getColumnCount()) {
      return null;
    }
    /**@todo Override this javax.swing.JTable method*/
    return super.getValueAt(row, column);
  }

  public void resetChanged() {
    //System.err.println("SETTING changed = false IN MESSAGETABLE.");
    changed = false;
  }

  public boolean hasChanged() {
    return changed;
  }

  public final void propertyChange(PropertyChangeEvent e) {
		Property val = (Property) e.getSource();
		getMessageModel().firePropertyChanged(val,"value");
		
	}

  public void setConstraint(String id) {
    primaryKeyColumn = id;
  }

  public String getConstraint() {
    return primaryKeyColumn;
  }

  /**
   * copyObject
   *
   * @return Object
   * @todo Implement this com.dexels.navajo.swingclient.components.CopyCompatible method
   */
  public Object copyObject() {
    Message returnMsg = null;
    String cols = "";
    for (int k = 0; k < getColumnCount(); k++) {
      if (k > 0) {
        cols = cols + ";" + getColumnId(k);
      }
      else {
        cols = cols + getColumnId(k);
      }
    }
    //System.err.println("COLUMS IN COPY: " + cols);
    try {
      ArrayList selectedMsgs = getSelectedMessages();
      if (selectedMsgs != null) {
        Message m1 = (Message) selectedMsgs.get(0);
        Navajo n = NavajoFactory.getInstance().createNavajo();
        Message m = NavajoFactory.getInstance().createMessage(n, m1.getName(), Message.MSG_TYPE_ARRAY);
        n.addMessage(m);
        for (int i = 0; i < selectedMsgs.size(); i++) {
          m.addElement( (Message) selectedMsgs.get(i));
        }
        returnMsg = m;
      }
      else {
        returnMsg = getMessage();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    StringWriter sw = new StringWriter();
    try {
        returnMsg.write(sw);
    } catch (NavajoException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return cols + "<tml>" + sw.toString() + "</tml>";
  }

//  public boolean isCellEditable(int row, int column) {
//    return false;
//  }

  public void setupTableActions()

  {
    InputMap im = getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    //  Have the enter key work the same as the tab key
    KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
    KeyStroke invtab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.SHIFT_DOWN_MASK);
    KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
    im.put(enter, im.get(tab));
    //  Disable the right arrow key
//    KeyStroke right = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
//    im.put(right, "none");
//    //  Override the default tab behaviour

    //  Tab to the next editable cell. When no editable cells goto next cell.

    final Action oldTabAction = getActionMap().get(im.get(tab));
    Action tabAction = new AbstractAction() {
      public void actionPerformed(ActionEvent e)

      {
        oldTabAction.actionPerformed(e);
        JTable table = (JTable) e.getSource();
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        int row = table.getSelectedRow();
        int column = table.getSelectedColumn();
        if(rowCount==0) {
        	transferFocus();
        	return;
        }
        if(row > -1 && column > -1) {
		    while (!table.isCellEditable(row, column)) {
		      column += 1;
		      if (column == columnCount) {
		        column = 0;
		        row += 1;
		      }
		      if (row == rowCount) {
		        row = 0;
		      }
		      //  Back to where we started, get out.
		      if (row == table.getSelectedRow() && column == table.getSelectedColumn()) {
		        break;
		      }
		    }
        }
        table.changeSelection(row, column, false, false);
      }
    };
    getActionMap().put(im.get(tab), tabAction);
    final Action oldInvTabAction = getActionMap().get(im.get(invtab));
    Action invTabAction = new AbstractAction() {
      public void actionPerformed(ActionEvent e)

      {
        oldInvTabAction.actionPerformed(e);
        JTable table = (JTable) e.getSource();
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        int row = table.getSelectedRow();
        int column = table.getSelectedColumn();
        if(table.getRowCount()==0) {
        	transferFocusBackward();
        	return;
        }
        
        while (!table.isCellEditable(row, column)) {
          System.err.println("COL: "+column+" row: "+row);
          column -= 1;
          if (column <= -1) {
            column = columnCount - 1;
            row -= 1;
          }
          if (row < 0) {
            row = rowCount - 1;
          }
          //  Back to where we started, get out.
          if (row == table.getSelectedRow() && column == table.getSelectedColumn()) {
            break;
          }
        }
        table.changeSelection(row, column, false, false);
      }
    };
    getActionMap().put(im.get(invtab), invTabAction);

  }

//public static void main(String[] args)
//
//{
//
//    TableActions frame = new TableActions();
//
//    frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
//
//    frame.pack();
//
//    frame.setLocationRelativeTo( null );
//
//    frame.setVisible(true);
//
//}
//
//
  class ColumnDivider {
    int index;
    float width;
    public ColumnDivider(int index, float width) {
      this.index = index;
      this.width = width;
    }
  }

//  protected void printComponent(Graphics g) {
//    Color cc = g.getColor();
//    g.setColor(Color.white);
//    g.fillRect(0, 0, getWidth(), getHeight());
//    g.setColor(cc);
//    Color c = getBackground();
//    setBackground(Color.white);
//    super.printComponent(g);
//    setBackground(c);
//  }

  public int getSelectedIndex() {
	  return getSelectedRow();
  }
  
	public void setSelectedIndex(int selectedIndex) {
		getSelectionModel().setSelectionInterval(selectedIndex, selectedIndex);
	}

	public void editCellAt(Property p) {
		Message m = p.getParentMessage();
		int row = getMessageModel().getRowOfMessage(m);
		int column = getMessageModel().getColumnOfProperty(row, p);
		//		stopCellEditing();
//		repaint();
//		getMessageModel().fireTableCellUpdated(getEditingRow(), getEditingColumn());
//		fireTableStructureChanged();
//		requestFocusInWindow();
//		transferFocus();
		int oldColumn = getSelectedColumn();
		int oldRow = getSelectedRow();
//		getMessageModel().fireTableCellUpdated(oldRow, oldColumn);
//		getMessageModel().fireTableCellUpdated(row, column);
		System.err.println("Starting edit at row: "+row+" column: "+column+" old sel: "+oldRow+" oldcol: "+oldColumn);
		
		getColumnModel().getSelectionModel().setSelectionInterval(column, column);
		getSelectionModel().setSelectionInterval(row, row);
	
		if(!hasFocus()) {
			grabFocus();
		}
		System.err.println("I REPEAT Starting edit at row: "+row+" column: "+column+" old sel: "+oldRow+" oldcol: "+oldColumn);
		requestFocusInWindow();
		editCellAt(row, column);
//		getMessageModel().fireTableCellUpdated(oldRow, oldColumn);
		
	}


	private void refreshSelectedCell() {
		int oldColumn = getSelectedColumn();
		int oldRow = getSelectedRow();
		getMessageModel().fireTableCellUpdated(oldRow, oldColumn);
	}

	public void setCurrentEditingComponent(Component doGetEditor) {
		// TODO Auto-generated method stub
		myCurrentEditingComponent = doGetEditor;
	}



}