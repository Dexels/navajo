package com.dexels.navajo.tipi.components.echoimpl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.PasswordField;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.TextArea;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ChangeEvent;
import nextapp.echo2.app.event.ChangeListener;
import nextapp.echo2.app.event.DocumentEvent;
import nextapp.echo2.app.event.DocumentListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.list.ListSelectionModel;
import nextapp.echo2.app.table.TableCellRenderer;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.nanoimpl.PropertyImpl;
import com.dexels.navajo.tipi.actions.PropertyEventListener;
import com.dexels.navajo.tipi.components.echoimpl.impl.TipiEchoTextField;

import echopointng.ComboBox;

public class EchoPropertyComponent extends Grid implements TableCellRenderer {
	private Property myProperty = null;

	private boolean showLabel = true;

	private boolean useLabelForReadOnlyProperties = false;
	Label l = null;

	int label_indent = 100;

	private Component currentComponent;

	private final ArrayList myPropertyEventListeners = new ArrayList();

	public EchoPropertyComponent() {
		super(2);
	}
	
	public final void addPropertyEventListener(PropertyEventListener pel) {
		myPropertyEventListeners.add(pel);
	}

	public final void removePropertyEventListner(PropertyEventListener pel) {
		myPropertyEventListeners.remove(pel);
	}

	public final void firePropertyEvents(Property p, String eventType) {
		for (int i = 0; i < myPropertyEventListeners.size(); i++) {
			PropertyEventListener current = (PropertyEventListener) myPropertyEventListeners
					.get(i);
			current.propertyEventFired(p, eventType);
		}
	}

	public final void fireTipiEvent(String type) {
		firePropertyEvents(myProperty, type);
	}

	public void setProperty(Property p) throws NavajoException {
		myProperty = p;
		if (p == null) {
			return;
		}
		removeAll();
		if (showLabel) {
			l = new Label();
			if (p.getDescription() != null) {
				l.setText(p.getDescription());
			} else {
				l.setText(p.getName());
			}
			add(l);
//			System.err.println("SETTING COLUMN WIDTH::: "+label_indent);
			setColumnWidth(0,new Extent(label_indent,Extent.PX));
			currentComponent = l;
			
		}

		String type = p.getType();
		if (type.equals(Property.SELECTION_PROPERTY)) {
			if (p.getCardinality().equals("1")) {
				final SelectField lb = new SelectField(p.getAllSelections().toArray());
				add(lb);
				lb.setEnabled(p.isDirIn());

				Selection s = p.getSelected();
				PropertyImpl ppp = (PropertyImpl)p;
//				System.err.println("PROPERTY:\n=========="+ppp.toXml(null).toString());
				if (s!=null) {
					
//					System.err.println("SELECTED PROPERTY FOUND: "+s.getName()+" / "+s.getValue()+" isSel: "+s.isSelected());
					int ind = p.getAllSelectedSelections().indexOf(s);
					if (ind>=0) {
						lb.setSelectedIndex(ind);
					}
				} else {
					System.err.println("No selected property");
				}
				//				lb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				lb.getSelectionModel().addChangeListener(new ChangeListener() {

					public void stateChanged(ChangeEvent e) {
						try {
							System.err.println("Listbox changing....");
							int index = lb.getSelectedIndex();
							
							if(index>=0) {
								Selection s = (Selection) lb.getSelectedItem();
								if (s != null) {
									System.err.println("Selected index: " + index
											+ " value: " + s.getValue() + " name: "
											+ s.getName());
									myProperty.setSelected(s.getName());
								} else {
									myProperty.clearSelections();
								}
							} else {
								myProperty.clearSelections();
							}
							fireTipiEvent("onValueChanged");
					
						} catch (NavajoException ex) {
							ex.printStackTrace();
						}
						
					}});
//				lb.addActionListener(new ActionListener(){
//
//					public void actionPerformed(ActionEvent e) {
//						try {
//							System.err.println("Listbox changing....");
//							Selection s = (Selection) lb.getSelectedItem();
//							int index = lb.getSelectedIndex();
//							if (s != null) {
//								System.err.println("Selected index: " + index
//										+ " value: " + s.getValue() + " name: "
//										+ s.getName());
//								myProperty.setSelected(s.getName());
//							} else {
//								myProperty.clearSelections();
//							}
//							fireTipiEvent("onValueChanged");
//						} catch (NavajoException ex) {
//							ex.printStackTrace();
//						}
//					}
//
//				});
				currentComponent = lb;
			} else {
				ListBox lb = new ListBox(p.getAllSelections().toArray());
				add(lb);
				lb.setEnabled(p.isDirIn());
				lb.setSelectionMode(ListSelectionModel.MULTIPLE_SELECTION);
				for (int i = 0; i < p.getAllSelections().size(); i++) {
					Selection current = (Selection) p.getAllSelections().get(i);
					lb.setSelectedIndex(i, current.isSelected());
				}
				lb.getSelectionModel().addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent ce) {
//						System.err.println("Noot: ");
						fireTipiEvent("onValueChanged");
					}
				});
				currentComponent = lb;
			}
		}
		if (type.equals(Property.INTEGER_PROPERTY)
				|| type.equals(Property.STRING_PROPERTY)
				|| type.equals(Property.FLOAT_PROPERTY)
				|| type.equals(Property.DATE_PROPERTY)) {
			boolean isEdit = p.isDirIn();

			if (isEdit || !useLabelForReadOnlyProperties) {
				final TipiEchoTextField tf = new TipiEchoTextField(p.getValue());
				tf.setWidth(new Extent(100));
				if (!p.isDirIn()) {
					tf.setForeground(new Color(90,90,90));
				}
				add(tf);
				tf.setEnabled(p.isDirIn());
				tf.getDocument().addDocumentListener(new DocumentListener() {
					public void documentUpdate(DocumentEvent e) {
//						System.err
//								.println("DOCUMENTUPDATE OF TipiEchoTextField");
						String text = tf.getText();
//						System.err.println("Found text: " + text);
//						System.err.println("Old value: "
//								+ myProperty.getValue());
						myProperty.setValue(text);

					}
				});
				tf.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TipiEchoTextField tf = (TipiEchoTextField) e
								.getSource();
//						System.err
//								.println("ACTIONPERFORMED OF TipiEchoTextField");
						String text = tf.getText();
//						System.err.println("Found text: " + text);
//						System.err.println("Old value: "
//								+ myProperty.getValue());
						myProperty.setValue(text);
					}
				});
				currentComponent = tf;

			} else {
				final Label tf = new Label(p.getValue());
				tf.setTextAlignment(new Alignment(Alignment.LEADING,Alignment.CENTER));
				add(tf);
				GridLayoutData gd = new GridLayoutData();
				gd.setAlignment(new Alignment(Alignment.LEADING,Alignment.CENTER));
//				gd.setBackground(new Color(100,200,240));
				tf.setLayoutData(gd);
				currentComponent = tf;

			}
		}
		if (type.equals(Property.BOOLEAN_PROPERTY)) {
			final CheckBox cb = new CheckBox();
			cb.setSelected(myProperty.getValue().equals("true"));
			add(cb);
			cb.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					myProperty.setValue(cb.isSelected());
				}});
			cb.setEnabled(myProperty.isDirIn());
			currentComponent = cb;
		}

		if (type.equals(Property.MEMO_PROPERTY)) {
			final TextArea cb = new TextArea();
			cb.setText(p.getValue());
			add(cb);
			cb.setEnabled(p.isDirIn());
			cb.getDocument().addDocumentListener(new DocumentListener(){

				public void documentUpdate(DocumentEvent e) {
					myProperty.setValue(cb.getText());
				}});
			currentComponent = cb;
		}

		if (type.equals(Property.PASSWORD_PROPERTY)) {
			final PasswordField tf = new PasswordField();
			tf.setWidth(new Extent(100));
			tf.setText(p.getValue());
			add(tf);
			boolean isEdit = p.isDirIn();
			tf.setEnabled(isEdit);
			tf.getDocument().addDocumentListener(new DocumentListener() {
				public void documentUpdate(DocumentEvent e) {
					String text = tf.getText();
					myProperty.setValue(text);
				}
			});
			tf.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TipiEchoTextField tf = (TipiEchoTextField) e.getSource();
					String text = tf.getText();
//					System.err.println("Found text: " + text);
//					System.err.println("Old value: " + myProperty.getValue());
					myProperty.setValue(text);
				}
			});
			currentComponent = tf;
		}
	}

	public Component getTableCellRendererComponent(final Table table, final Object value,
			final int column, final int row) {
		try {
			showLabel = false;
			EchoPropertyComponent epc = new EchoPropertyComponent();
			epc.setUseLabelForReadOnlyProperties(true);
			epc.setLabelVisible(false);
			epc.setProperty((Property) value);
//			epc.setBackground((row % 2 == 0) ? new Color(255, 255, 255)
//					: new Color(230, 230, 230));
			final Extent w = table.getColumnModel().getColumn(column).getWidth();
//			epc.addPropertyChangeListener(new PropertyChangeListener(){
//
//				public void propertyChange(PropertyChangeEvent evt) {
//					System.err.println("Table RENDERER EVENT: "+value+" -> "+column+"/"+row+" width: "+w);
//				}});
			epc.setWidth(w);
			epc.setBackground(null);
			epc.currentComponent.setBackground(null);
			return epc;
		} catch (NavajoException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void setLabelVisible(boolean b) {
		showLabel = b;
	}

	public void setLabelIndent(int indent) {
		label_indent = indent;
 		if(getComponents().length>1) {
 			super.setColumnWidth(0,new Extent(label_indent,Extent.PX));
 		}
//		try {
//			setProperty(getProperty());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public Property getProperty() {
		return myProperty;
	}

	public void setBackground(Color arg0) {
		super.setBackground(arg0);
		if (currentComponent != null) {
			currentComponent.setBackground(arg0);
		}
	}

	public boolean isUseLabelForReadOnlyProperties() {
		return useLabelForReadOnlyProperties;
	}

	public void setUseLabelForReadOnlyProperties(
			boolean useLabelForReadOnlyProperties) {
		this.useLabelForReadOnlyProperties = useLabelForReadOnlyProperties;
	}

}
