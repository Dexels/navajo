/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingclient.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.PropertyTypeException;

public class PropertyCellEditor implements TableCellEditor,
		ListSelectionListener, PropertyChangeListener, Serializable {

	private static final long serialVersionUID = 6821318390794986959L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(PropertyCellEditor.class);
	private Property myProperty = null;

	private final ArrayList<CellEditorListener> myListeners = new ArrayList<CellEditorListener>();

	private JComponent lastComponent = null;
	private MessageTable myTable;
	private JButton rowButton = new JButton();
	private int lastSelectedRow = 0;

	private MultiSelectPropertyBox myMultiSelectPropertyBox = null;
	private String myPropertyType = null;
	private PropertyBox myPropertyBox = null;
	private PropertyField myPropertyField = null;
	private PropertyCheckBox myPropertyCheckBox = null;
	private DatePropertyField myDatePropertyField = null;
	private IntegerPropertyField myIntegerPropertyField = null;
	private FloatPropertyField myFloatPropertyField = null;
	private MoneyField myMoneyPropertyField = null;
	private PercentageField myPercentagePropertyField = null;
	private ClockTimeField myClockTimeField = null;
	private StopwatchTimeField myStopwatchTimeField = null;

	private Object myOldValue;

	public PropertyCellEditor() {
	}

	public PropertyCellEditor(MessageTable mm) {
		myTable = mm;
		rowButton.setMargin(new Insets(0, 1, 0, 1));
		rowButton.setFont(new Font(rowButton.getFont().getName(), Font.BOLD,
				rowButton.getFont().getSize()));
		rowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					myTable.showEditDialog("Edit", lastSelectedRow);
				} catch (Exception ex) {
					logger.error("Error: ", ex);
				}
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// Edit button in first column
		Component doGetEditor = doGetEditor(value, isSelected, row, column);
		if (doGetEditor instanceof PropertyField) {
			PropertyField t = (PropertyField) doGetEditor;
			t.selectAll();
		} else {
		}
		myTable.setCurrentEditingComponent(doGetEditor);
		return doGetEditor;
	}

	private Component doGetEditor(Object value, boolean isSelected, int row,
			int column) {
		lastSelectedRow = row;
		if (myProperty != null) {
			myProperty.removePropertyChangeListener(this);
		}
		if (Integer.class.isInstance(value)) {
			rowButton.setText("" + (row + 1));
			return rowButton;
		}

		Border b = new LineBorder(Color.black, 2);

		if (Property.class.isInstance(value)) {
			myProperty = (Property) value;
			myOldValue = myProperty.getTypedValue();
			myProperty.addPropertyChangeListener(this);
			myPropertyType = myProperty.getType();
			if (myPropertyType.equals(Property.SELECTION_PROPERTY)) {

				if (myProperty.getCardinality().equals("+")) {
					if (myMultiSelectPropertyBox == null) {
						myMultiSelectPropertyBox = new MultiSelectPropertyBox();
						myMultiSelectPropertyBox
								.addItemListener(new ItemListener() {
									@Override
									public void itemStateChanged(ItemEvent e) {
										logger.debug(">>> "+ e.getStateChange());
									}
								});
					}

					myMultiSelectPropertyBox.setProperty(myProperty);
					myMultiSelectPropertyBox.setEditable(myProperty.isDirIn());
					lastComponent = myMultiSelectPropertyBox;
					setComponentColor(myMultiSelectPropertyBox, isSelected,
							row, column);
					myMultiSelectPropertyBox.setBorder(b);
					myMultiSelectPropertyBox.requestFocus();
					return myMultiSelectPropertyBox;

				} else {

					if (myPropertyBox == null) {
						myPropertyBox = new PropertyBox();
						myPropertyBox.addKeyListener(new KeyAdapter() {

							@Override
							public void keyPressed(KeyEvent k) {
								// if(k.getKeyCode()==KeyEvent.VK_)
							}
						});

						myPropertyBox.addItemListener(new ItemListener() {
							@Override
							public void itemStateChanged(ItemEvent e) {
								logger.debug(">>> " + e.getStateChange());
								if (ItemEvent.SELECTED == e.getStateChange()) {
									try {
										myTable.fireChangeEvents(
												myPropertyBox.getProperty(),
												null,
												myProperty.getTypedValue());
									} catch (NavajoException e1) {
										logger.error("Error: ", e1);
									}
									stopCellEditing();
								}
							}
						});
					}
					myPropertyBox.setProperty(myProperty);
					myPropertyBox.setEditable(myProperty.isDirIn());
					lastComponent = myPropertyBox;
					setComponentColor(myPropertyBox, isSelected, row, column);
					myPropertyBox.setBorder(b);
					myPropertyBox.requestFocusInWindow();
					final PropertyBox x = myPropertyBox;
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							x.setPopupVisible(true);

						}
					});

					return myPropertyBox;
				}
				/** @todo etc */
			}
			if (myPropertyType.equals(Property.BOOLEAN_PROPERTY)) {
				if (myPropertyCheckBox == null) {
					myPropertyCheckBox = new PropertyCheckBox();
					myPropertyCheckBox.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								myTable.fireChangeEvents(getProperty(),
										!myPropertyCheckBox.isSelected(),
										myPropertyCheckBox.isSelected());
							} catch (NavajoException e1) {
								logger.error("Error: ", e1);
							}
							// Since a change in a checkbox can cause immediate
							// changes in sorting, stopCellEditing mode to allow
							// proper redrawing of this cell
							stopCellEditing();
						}
					});
				}
				myPropertyCheckBox.setProperty(myProperty);
				myPropertyCheckBox.setEnabled(myProperty.isDirIn());
				lastComponent = myPropertyCheckBox;
				setComponentColor(myPropertyCheckBox, isSelected, row, column);
				// logger.info("RETURNING BOOLEAN EDITOR");
				myPropertyCheckBox.setBorder(b);
				myPropertyCheckBox.requestFocusInWindow();
				return myPropertyCheckBox;
			}

			if (myPropertyType.equals(Property.DATE_PROPERTY)) {
				if (myDatePropertyField == null) {
					myDatePropertyField = new DatePropertyField(false);
				}
				myDatePropertyField.setEditable(myProperty.isDirIn());
				myDatePropertyField.setProperty(myProperty);

				myDatePropertyField.setTable(myTable);
				myDatePropertyField.setSelectedCell(row, column);

				lastComponent = myDatePropertyField;
				setComponentColor(myDatePropertyField, isSelected, row, column);
				myDatePropertyField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						if (e.getOppositeComponent() != myTable) {
							stopCellEditing();
						}
					}
				});
				myDatePropertyField.setBorder(b);
				myDatePropertyField.requestFocusInWindow();
				myDatePropertyField.selectAll();
				return myDatePropertyField;
			}

			if (myPropertyType.equals(Property.INTEGER_PROPERTY)) {
				if (myIntegerPropertyField == null) {
					myIntegerPropertyField = new IntegerPropertyField("false");
				}
				myIntegerPropertyField.setEditable(myProperty.isDirIn());
				myIntegerPropertyField.setProperty(myProperty);
				lastComponent = myIntegerPropertyField;
				setComponentColor(myIntegerPropertyField, isSelected, row,
						column);
				myIntegerPropertyField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						if (e.getOppositeComponent() != myTable) {
							stopCellEditing();
						}
					}

					@Override
					public void focusGained(FocusEvent e) {
						
					}
				});
				
				myIntegerPropertyField.selectAll();
				myIntegerPropertyField.setBorder(b);
				myIntegerPropertyField.setRequestFocusEnabled(true);
				myIntegerPropertyField.requestFocusInWindow();
				try {
					myIntegerPropertyField.getProperty().setValue(
							myIntegerPropertyField.getText());
				} catch (PropertyTypeException ex) {
					logger.info(ex.getMessage(),ex);
					myIntegerPropertyField.setText(""
							+ myIntegerPropertyField.getProperty()
									.getTypedValue());
				}
				return myIntegerPropertyField;
			}

			if (myPropertyType.equals(Property.FLOAT_PROPERTY)) {
				if (myFloatPropertyField == null) {
					myFloatPropertyField = new FloatPropertyField(false);
				}
				myFloatPropertyField.setEditable(myProperty.isDirIn());
				lastComponent = myFloatPropertyField;
				myFloatPropertyField.setProperty(myProperty);
				setComponentColor(myFloatPropertyField, isSelected, row, column);
				myFloatPropertyField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						if (e.getOppositeComponent() != myTable) {
							stopCellEditing();
						}
					}
				});
				myFloatPropertyField.selectAll();
				myFloatPropertyField.setBorder(b);
				myFloatPropertyField.requestFocusInWindow();
				return myFloatPropertyField;
			}

			if (myPropertyType.equals(Property.MONEY_PROPERTY)) {
				logger.info("Editing money!");
				if (myMoneyPropertyField == null) {
					myMoneyPropertyField = new MoneyField(false);
				}
				myMoneyPropertyField.setEditable(myProperty.isDirIn());
				lastComponent = myMoneyPropertyField;
				myMoneyPropertyField.setProperty(myProperty);
				setComponentColor(myMoneyPropertyField, isSelected, row, column);
				// final String contents = myMoneyPropertyField.getText();
				myMoneyPropertyField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						if (e.getOppositeComponent() != myTable) {
							stopCellEditing();
						}
					}
				});
				myMoneyPropertyField.editProperty();
				myMoneyPropertyField.selectAll();
				myMoneyPropertyField.setBorder(b);
				myMoneyPropertyField.requestFocusInWindow();
				return myMoneyPropertyField;
			}

			if (myPropertyType.equals(Property.PERCENTAGE_PROPERTY)) {
				logger.info("Editing percentage!");
				if (myPercentagePropertyField == null) {
					myPercentagePropertyField = new PercentageField(false);
				}
				myPercentagePropertyField.setEditable(myProperty.isDirIn());
				lastComponent = myPercentagePropertyField;
				myPercentagePropertyField.setProperty(myProperty);
				setComponentColor(myPercentagePropertyField, isSelected, row,
						column);
				// final String contents = myPercentagePropertyField.getText();
				myPercentagePropertyField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						if (e.getOppositeComponent() != myTable) {
							stopCellEditing();
						}
					}
				});
				// myPercentagePropertyField.set(null);
				// myPercentagePropertyField.selectAll();
				myPercentagePropertyField.editProperty();
				myPercentagePropertyField.setBorder(b);
				myPercentagePropertyField.requestFocusInWindow();
				return myPercentagePropertyField;
			}

			if (myPropertyType.equals(Property.STOPWATCHTIME_PROPERTY)) {
				logger.info("Editing a stopwatchtime!");
				if (myStopwatchTimeField == null) {
					myStopwatchTimeField = new StopwatchTimeField(false);
				}
				myStopwatchTimeField.setEditable(myProperty.isDirIn());

				lastComponent = myStopwatchTimeField;
				myStopwatchTimeField.setProperty(myProperty);
				setComponentColor(myStopwatchTimeField, isSelected, row, column);
				myStopwatchTimeField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						if (e.getOppositeComponent() != myTable) {
							stopCellEditing();
						}
					}
				});
				// myStopwatchTimeField.focusGained(null);
				myStopwatchTimeField.selectAll();
				myStopwatchTimeField.setBorder(b);
				myStopwatchTimeField.requestFocusInWindow();


				return myStopwatchTimeField;
			}

			if (myPropertyType.equals(Property.CLOCKTIME_PROPERTY)) {
				logger.info("Editing a time!");
				if (myClockTimeField == null) {
					myClockTimeField = new ClockTimeField(false);
				}
				myClockTimeField.setEditable(myProperty.isDirIn());
				myClockTimeField.showSeconds(false);
				lastComponent = myClockTimeField;
				myClockTimeField.setProperty(myProperty);
				setComponentColor(myClockTimeField, isSelected, row, column);
				myClockTimeField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						if (e.getOppositeComponent() != myTable) {
							stopCellEditing();
						}
					}
				});
				// myClockTimeField.focusGained(null);
				myClockTimeField.selectAll();
				myClockTimeField.setBorder(b);
				myClockTimeField.requestFocusInWindow();

				return myClockTimeField;
			}

			if (myPropertyField == null) {
				myPropertyField = new TextPropertyField(false);
				myPropertyField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						if (e.getOppositeComponent() != myTable) {
							stopCellEditing();
						}
					}
				});
			}
			myPropertyField.setEditable(myProperty.isDirIn());
			myPropertyField.setProperty(myProperty);
			lastComponent = myPropertyField;
			setComponentColor(myPropertyField, true, row, column);
			myPropertyField.setBorder(b);
			myPropertyField.requestFocusInWindow();
			// myPropertyField.selectAll();
			return myPropertyField;

		}

		return myPropertyField;
	}

	private final void setComponentColor(Component c, boolean isSelected,
			int row, int column) {
		if (c == null) {
			return;
		}
		JComponent cc = (JComponent) c;
		if (isSelected) {
			cc.setBorder(new LineBorder(Color.black, 2));
		} else {
			cc.setBorder(null);
		}

	}

	// private fireUpdate(

	@Override
	public void cancelCellEditing() {
		for (int i = 0; i < myListeners.size(); i++) {
			CellEditorListener ce = myListeners.get(i);
			ce.editingCanceled(new ChangeEvent(myTable));
		}
	}

	@Override
	public boolean stopCellEditing() {
		if (lastComponent != null
				&& ((PropertyControlled) lastComponent).getProperty() != null) {
			updateProperty();
			((PropertyControlled) lastComponent).setProperty(null);

		}
		myTable.removeEditor();

		/** @todo Occasional null pointer here. Fix */

		if (lastComponent != null
				&& ((PropertyControlled) lastComponent).getProperty() != null
				&& ((PropertyControlled) lastComponent).getProperty().getType()
						.equals(Property.SELECTION_PROPERTY)) {
			logger.info("Clearing component: "
					+ lastComponent.getClass());
			((PropertyControlled) lastComponent).setProperty(null);

		}

		return true;
	}

	public void updateProperty() {
		// Thread.dumpStack();
		if (lastComponent != null) {
			try {
				Property p = ((PropertyControlled) lastComponent).getProperty();
				if (myProperty == p && myProperty != null) {
					checkPropertyUpdate(p, p.getTypedValue());
				}
			} catch (PropertyTypeException ex1) {
				logger.info(ex1.getMessage());
			} catch (NavajoException e) {
				logger.error("Error: ",e);
			}
		} else {
			// logger.info("Que?");
		}
	}

	private void checkPropertyUpdate(Property p, Object old)
			throws NavajoException {
		((PropertyControlled) lastComponent).update();
		Object newValue = p.getTypedValue();
		if (old == null) {
			if (newValue != null) {
				myTable.fireChangeEvents(p, old, newValue);
			}
		} else {
			if (!old.equals(newValue)) {
				myTable.fireChangeEvents(p, old, newValue);
			}
		}
	}

	@Override
	public Object getCellEditorValue() {
		return myProperty;
	}

	@Override
	public boolean isCellEditable(EventObject e) {

		if (myTable != null) {
			boolean b = myTable.getModel().isCellEditable(
					myTable.getSelectedRow(), myTable.getSelectedColumn());
			return b;
		}
		return false;
	}

	public Object getOldValue() {
		return myOldValue;
	}

	public Property getProperty() {
		return myProperty;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		/** @todo: Implement this javax.swing.event.ListSelectionListener method */
		throw new java.lang.UnsupportedOperationException(
				"Method valueChanged() not yet implemented.");
	}

	@Override
	public boolean shouldSelectCell(EventObject parm1) {
		return true;
	}

	@Override
	public void addCellEditorListener(CellEditorListener ce) {
		myListeners.add(ce);
	}

	@Override
	public void removeCellEditorListener(CellEditorListener ce) {
		myListeners.remove(ce);
	}

	public boolean requestFocusInWindow() {
		if (lastComponent != null) {
			return lastComponent.requestFocusInWindow();
		} else {
			logger.info(">> no last component ");
		}
		return false;
	}

	@Override
	public void propertyChange(PropertyChangeEvent p) {
		final PropertyControlled tc = (PropertyControlled) lastComponent;
		if (lastComponent != null) {
			if (myProperty == tc.getProperty()) {
				runSyncInEventThread(new Runnable() {
					@Override
					public void run() {
						tc.setProperty(myProperty);
					}
				});
			}
		}
	}

	public void runSyncInEventThread(Runnable r) {
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(r);
			} catch (InvocationTargetException ex) {
				throw new RuntimeException(ex);
			} catch (InterruptedException ex) {
			}
		}
	}

}
