package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.JTable;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import com.dexels.navajo.tipi.swingclient.components.sort.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public class MessageTableFooter extends JTableHeader {
   public MessageTableFooter(JTable table, TableCellRenderer tc) {
     super.setReorderingAllowed(false);
     super.setTable(table);
     setDefaultRenderer(tc);
     setBorder(null);
     setOpaque(false);
     table.setOpaque(false);
   }

}

