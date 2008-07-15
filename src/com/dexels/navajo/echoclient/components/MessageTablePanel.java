package com.dexels.navajo.echoclient.components;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.echoclient.components.PageNavigator.*;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Style;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.extras.app.*;

public class MessageTablePanel extends SplitPane {
	private PageNavigator pageNavigator;
	private MessageTable myTable;
	private TransitionPane myTransitionPane;
	
	
	public MessageTablePanel() {
    	setOrientation(ORIENTATION_VERTICAL);
    	setSeparatorPosition(new Extent(25));
		pageNavigator = new PageNavigator();
		pageNavigator.initialize();
		
		myTransitionPane = new TransitionPane();
		myTable = new MessageTable();
		// ben er klaar mee
		pageNavigator.setTable(myTable);
		add(pageNavigator);
        add(myTransitionPane);
        myTransitionPane.setDuration(2000);
        myTransitionPane.setType(TransitionPane.TYPE_CAMERA_PAN_RIGHT);
        myTransitionPane.add(myTable);
    	Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(MessageTablePanel.class, "Default");
    	myTable.setStyle(ss);
    	myTable.setSelectionEnabled(true);
    	myTable.setRolloverEnabled(false);
        myTable.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                	Message m = myTable.getSelectedMessage();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        myTable.addTableEditorListener(new TableEditorListener(){

            public void propertyChanged(Property p, String eventType, int column, int row) {
            	Map<String,Object> event = new HashMap<String,Object>();
                event.put("column", new Integer(column));
                event.put("row", new Integer(row));
                event.put("new", p.getValue());
                event.put("message", p.getParentMessage());
                event.put("name", p.getName());
 
            }});
        
        
       pageNavigator.addPageIndexChangeListener(new PageIndexChangeListener(){

		public void pageIndexChanged(PageIndexChangeEvent e) {
			System.err.println("Swtitching");
			myTransitionPane.remove(myTable);
			myTransitionPane.add(myTable);
			
		}});
	}

//	addColumn(x.id,x.label,x.editable, x.width);
	public void addColumn(String id, String label, boolean editable, int width) {
		myTable.addColumn(id, label, editable, width);
	}
	
	public MessageTable getMyTable() {
		return myTable;
	}

	public PageNavigator getPageNavigator() {
		return pageNavigator;
	}

	public Message getMessage() {
		return myTable.getMessage();
	}

	public void setMessage(Message message) {
		myTable.setMessage(message);
	}

	public int getRowsPerPage() {
		return myTable.getRowsPerPage();
	}

	public void setRowsPerPage(int rowsPerPage) {
		myTable.setRowsPerPage(rowsPerPage);
	}

	public Message getSelectedMessage() {
		return myTable.getSelectedMessage();
	}

	public void setSelectedMessage(Message selectedMessage) {
		myTable.setSelectedMessage(selectedMessage);
	}

	public void addSelectionListener(ActionListener al) {
		myTable.addSelectionListener(al);
	}

	public void removeSelectionListener(ActionListener al) {
		myTable.addSelectionListener(al);
	}
}
