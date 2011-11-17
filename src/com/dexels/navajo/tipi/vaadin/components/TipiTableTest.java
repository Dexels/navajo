package com.dexels.navajo.tipi.vaadin.components;

import java.util.Collection;
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractInMemoryContainer;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class TipiTableTest extends TipiLabel {

	private static final long serialVersionUID = 1561760767992331660L;
	private Label label;

	@Override
	public Object createContainer() {
		// Create a table and add a style to allow setting the row height in theme.
		final Table table = new Table();
		table.addStyleName("components-inside");

		/* Define the names and data types of columns.
		 * The "default value" parameter is meaningless here. */
		table.addContainerProperty("Sum",            Label.class,     null);
		table.addContainerProperty("Is Transferred", CheckBox.class,  null);
		table.addContainerProperty("Comments",       TextField.class, null);
		table.addContainerProperty("Details",        Button.class,    null);
		table.addContainerProperty("Something else",        NativeSelect.class,    null);

		/* Add a few items in the table. */
		for (int i=0; i<100; i++) {
		    // Create the fields for the current table row
		    Label sumField = new Label(String.format(
		                   "Sum is <b>$%04.2f</b><br/><i>(VAT incl.)</i>",
		                   new Object[] {new Double(Math.random()*1000)}),
		                               Label.CONTENT_XHTML);
		    CheckBox transferredField = new CheckBox("is transferred");
		    
		    // Multiline text field. This required modifying the 
		    // height of the table row.
		    TextField commentsField = new TextField();
		    commentsField.setRows(3);
		    
		    // The Table item identifier for the row.
		    Integer itemId = new Integer(i);
		    
		    // Create a button and handle its click. A Button does not
		    // know the item it is contained in, so we have to store the
		    // item ID as user-defined data.
		    Button detailsField = new Button("show details");
		    detailsField.setData(itemId);
		    detailsField.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
		            // Get the item identifier from the user-defined data.
		            Integer itemId = (Integer)event.getButton().getData();
		            getVaadinApplication().getMainWindow().showNotification("Link "+
		                                   itemId.intValue()+" clicked.");
		        }

		    });
		    detailsField.addStyleName("link");

		    NativeSelect ns = new NativeSelect("Other");
		    ns.addItem("Fiive");
		    ns.addItem("Six");
		    ns.setValue("Six");
		    // Create the table row.
		    table.addItem(new Object[] {sumField, transferredField,
		                                commentsField, detailsField,ns},
		                  itemId);
		}
		 
		// Show just three rows because they are so high.
		table.setPageLength(3);
		return table;
	}





}
