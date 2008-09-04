package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

import javax.swing.*;
import javax.swing.event.*;
import com.dexels.navajo.document.*;

import java.lang.reflect.*;
import java.util.*;
import javax.swing.border.*;


public  class PropertyCellEditor 
    implements TableCellEditor, ListSelectionListener, PropertyChangeListener {

  private Property myProperty = null;

  private final ArrayList myListeners = new ArrayList();

  private JComponent lastComponent = null;
  private MessageTable myTable;

  private int lastRow = -1;
  private int lastColumn = -1;
  private boolean wasSelected = false;
//  private Property copy;
  private JButton rowButton = new JButton();
  private boolean isChangingSelection = false;
  private int lastSelectedRow = 0;


  MultiSelectPropertyBox myMultiSelectPropertyBox = null;
  String myPropertyType = null;
  PropertyBox myPropertyBox = null;
  PropertyField myPropertyField = null;
  PropertyCheckBox myPropertyCheckBox = null;
  DatePropertyField myDatePropertyField = null;
  IntegerPropertyField myIntegerPropertyField = null;
  FloatPropertyField myFloatPropertyField = null;
  MoneyField myMoneyPropertyField = null;
  PercentageField myPercentagePropertyField = null;
  ClockTimeField myClockTimeField = null;
  StopwatchTimeField myStopwatchTimeField = null;

private Object myOldValue;
  
  
  
  public PropertyCellEditor() {}

  public PropertyCellEditor(MessageTable mm) {
    myTable = mm;
    rowButton.setMargin(new Insets(0,1,0,1));
    rowButton.setFont(new Font(rowButton.getFont().getName(), Font.BOLD, rowButton.getFont().getSize()));
    rowButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try{
              myTable.showEditDialog("Edit", lastSelectedRow);
            }catch(Exception ex){
              ex.printStackTrace();
            }
          }
        });
  }

  public  Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    // Edit button in first column
	  Component doGetEditor = doGetEditor(value, isSelected, row, column);
	  if(doGetEditor instanceof PropertyField) {
		  PropertyField t = (PropertyField)doGetEditor;
		  t.selectAll();
		  System.err.println("TEXT: "+t.getText());
	  } else {
		  System.err.println("NOOOOOOOT: "+doGetEditor.getClass());
	  }
	  myTable.setCurrentEditingComponent(doGetEditor);
	  return doGetEditor;
  }

private Component doGetEditor(Object value, boolean isSelected, int row, int column) {
//	System.err.println("Starting edit: "+row+" col: "+column+" value: "+value+" selected: "+isSelected);
	  lastSelectedRow = row;
	  if(myProperty!=null) {
		  myProperty.removePropertyChangeListener(this);
	  }
	  if(Integer.class.isInstance(value)){
      rowButton.setText(""+(row+1));
      return rowButton;
    }


    Border b = new LineBorder(Color.black, 2);
//    System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>> Editor: " + isSelected + ", " + row + ", " + column);
//    myTable = (MessageTable) table;
    lastRow = row;
    lastColumn = column;
    wasSelected = isSelected;
//    myTable.setEditingColumn(column);
//    myTable.setEditingRow(row);
    if (Property.class.isInstance(value)) {
      myProperty = (Property) value;
      myOldValue = myProperty.getTypedValue();
      myProperty.addPropertyChangeListener(this);
      myPropertyType = (String) myProperty.getType();
      if (myPropertyType.equals(Property.SELECTION_PROPERTY)) {

        try {
          if (row == 0) {
//            System.err.println("\n\n=====================================================================================");
            for (int i = 0; i < myProperty.getAllSelections().size(); i++) {
//              System.err.println("EDITOR SEL: " + ( (Selection) myProperty.getAllSelections().get(i)).getName() + " selected: " + ( (Selection) myProperty.getAllSelections().get(i)).isSelected());
            }
//            System.err.println("=====================================================================================");
          }
        }
        catch (Exception e) {
          e.printStackTrace();
        }

        if(myProperty.getCardinality().equals("+")){
          if (myMultiSelectPropertyBox == null) {
            myMultiSelectPropertyBox = new MultiSelectPropertyBox();
            myMultiSelectPropertyBox.addItemListener(new ItemListener() {
              public void itemStateChanged(ItemEvent e) {
                System.err.println(">>> " + e.getStateChange());
                if (e.SELECTED == e.getStateChange()) {
//                ( (PropertyControlled) e.getSource()).update();
                  if (!isChangingSelection) {
                    System.err.println("COMBOBOX FIRED TOWARDS EDITOR");
//                    stopCellEditing();
                  }

                }
              }
            });
          }
          isChangingSelection = true;
          
          myMultiSelectPropertyBox.setProperty(myProperty);
          myMultiSelectPropertyBox.setEditable(myProperty.isDirIn());
          lastComponent = myMultiSelectPropertyBox;
          setComponentColor(myMultiSelectPropertyBox, isSelected, row, column);
          myMultiSelectPropertyBox.setBorder(b);
          myMultiSelectPropertyBox.requestFocus();
          isChangingSelection = false;
          return myMultiSelectPropertyBox;

        }else{


          if (myPropertyBox == null) {
            myPropertyBox = new PropertyBox();
            myPropertyBox.addKeyListener(new KeyAdapter(){

				public void keyPressed(KeyEvent k) {
//					if(k.getKeyCode()==KeyEvent.VK_)
				}
            	});
            
            myPropertyBox.addItemListener(new ItemListener() {
              public void itemStateChanged(ItemEvent e) {
                System.err.println(">>> " + e.getStateChange());
                if (e.SELECTED == e.getStateChange()) {
//                	( (PropertyControlled) e.getSource()).update();
//                  if (!isChangingSelection) {
//                    System.err.println("COMBOBOX FIRED TOWARDS EDITOR");
                	//updateProperty();
                		try {
                			myTable.fireChangeEvents(myPropertyBox.getProperty(), null, myProperty.getTypedValue());
//                    		checkPropertyUpdate(myProperty, myPropertyBox.getLastSelection());
					} catch (NavajoException e1) {
						e1.printStackTrace();
					}
                	stopCellEditing();
//                  }
//
                }
          }}
              );
          }
          isChangingSelection = true;
          myPropertyBox.setProperty(myProperty);
          myPropertyBox.setEditable(myProperty.isDirIn());
          lastComponent = myPropertyBox;
          setComponentColor(myPropertyBox, isSelected, row, column);
          myPropertyBox.setBorder(b);
          myPropertyBox.requestFocusInWindow();
          final PropertyBox x = myPropertyBox;
          SwingUtilities.invokeLater(new Runnable(){

			public void run() {
		          x.setPopupVisible(true);
		          
			}});
          
          isChangingSelection = false;
          return myPropertyBox;
        }
        /** @todo etc */
      }
      if (myPropertyType.equals(Property.BOOLEAN_PROPERTY)) {
        if (myPropertyCheckBox == null) {
          myPropertyCheckBox = new PropertyCheckBox();
          myPropertyCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//              stopCellEditing();
              System.err.println("CHECKBOX FIRED TOWARDS EDITOR");
              
              try {
				myTable.fireChangeEvents(getProperty(), !myPropertyCheckBox.isSelected(), myPropertyCheckBox.isSelected());
			} catch (NavajoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

            }
          });
        }
//        myPropertyCheckBox.addFocusListener(new FocusAdapter(){
//            public void focusLost(FocusEvent e){
//              ((PropertyControlled)e.getSource()).update();
//              stopCellEditing();
//              System.err.println("CHECKOBOX FIRED TOWARDS EDITOR");
//            }
//           });

        myPropertyCheckBox.setProperty(myProperty);
        myPropertyCheckBox.setEnabled(myProperty.isDirIn());
        lastComponent = myPropertyCheckBox;
        setComponentColor(myPropertyCheckBox, isSelected, row, column);
        System.err.println("RETURNING BOOLEAN EDITOR");
        myPropertyCheckBox.setBorder(b);
        myPropertyCheckBox.requestFocusInWindow();
        return myPropertyCheckBox;
      }

      if (myPropertyType.equals(Property.DATE_PROPERTY)) {
        if (myDatePropertyField == null) {
          myDatePropertyField = new DatePropertyField();
        }
        myDatePropertyField.setEditable(myProperty.isDirIn());
        myDatePropertyField.setProperty(myProperty);

        myDatePropertyField.setTable(myTable);
        myDatePropertyField.setSelectedCell(row, column);

        lastComponent = myDatePropertyField;
        setComponentColor(myDatePropertyField, isSelected, row, column);
        myDatePropertyField.addFocusListener(new FocusAdapter() {
          public void focusLost(FocusEvent e) {
             ( (PropertyControlled) e.getSource()).update();
       	  if(e.getOppositeComponent()!=myTable) {
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
          myIntegerPropertyField = new IntegerPropertyField();
        }
        myIntegerPropertyField.setEditable(myProperty.isDirIn());
        myIntegerPropertyField.setProperty(myProperty);
        lastComponent = myIntegerPropertyField;
        setComponentColor(myIntegerPropertyField, isSelected, row, column);
        myIntegerPropertyField.addFocusListener(new FocusAdapter() {
          public void focusLost(FocusEvent e) {
//                ((PropertyControlled)e.getSource()).update();
//        	  System.err.println("Forcing stopppp: "+e.getOppositeComponent());
        	  
        	  if(e.getOppositeComponent()!=myTable) {
                  stopCellEditing();
        	  }
          }
          public void focusGained(FocusEvent e) {
//            ((PropertyControlled)e.getSource()).update();
//    	  System.err.println("Gettin FocusEvent: "+e.getOppositeComponent());
    	  
//        stopCellEditing();
      }
        });
//        myIntegerPropertyField.addKeyListener(new KeyAdapter() {
//          public void keyPressed(KeyEvent e) {
//            System.err.println("Pressed a key in integerpropfield");
//            ( (PropertyControlled) lastComponent).update();
//          }
//        });
//        System.err.println("returning integer propertyfield");
        myIntegerPropertyField.selectAll();
        myIntegerPropertyField.setBorder(b);
        myIntegerPropertyField.setRequestFocusEnabled(true);
        myIntegerPropertyField.requestFocusInWindow();
        try {
//          myIntegerPropertyField.update();
          myIntegerPropertyField.getProperty().setValue(myIntegerPropertyField.getText());
        }
        catch (PropertyTypeException ex) {
          System.err.println(ex.getMessage());
          ex.printStackTrace();
          myIntegerPropertyField.setText(""+myIntegerPropertyField.getProperty().getTypedValue());
        }
         return myIntegerPropertyField;
      }

      if (myPropertyType.equals(Property.FLOAT_PROPERTY)) {
        if (myFloatPropertyField == null) {
          myFloatPropertyField = new FloatPropertyField();
        }
        myFloatPropertyField.setEditable(myProperty.isDirIn());
        lastComponent = myFloatPropertyField;
        myFloatPropertyField.setProperty(myProperty);
        setComponentColor(myFloatPropertyField, isSelected, row, column);
        myFloatPropertyField.addFocusListener(new FocusAdapter() {
          public void focusLost(FocusEvent e) {
//               System.err.println("Floatfield focus lost!");
//               System.err.println(">>>>"+((FloatPropertyField)e.getSource()).getText());
//               ((FloatPropertyField)e.getSource()).update();
        	  if(e.getOppositeComponent()!=myTable) {
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
        System.err.println("Editing money!");
        if (myMoneyPropertyField == null) {
          myMoneyPropertyField = new MoneyField();
        }
        myMoneyPropertyField.setEditable(myProperty.isDirIn());
        lastComponent = myMoneyPropertyField;
        myMoneyPropertyField.setProperty(myProperty);
        setComponentColor(myMoneyPropertyField, isSelected, row, column);
//        final String contents = myMoneyPropertyField.getText();
        final MoneyField m = myMoneyPropertyField;
        myMoneyPropertyField.addFocusListener(new FocusAdapter() {
          public void focusLost(FocusEvent e) {
        	  m.update();
        	  if(e.getOppositeComponent()!=myTable) {
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
        System.err.println("Editing percentage!");
        if (myPercentagePropertyField == null) {
          myPercentagePropertyField = new PercentageField();
        }
        myPercentagePropertyField.setEditable(myProperty.isDirIn());
        lastComponent = myPercentagePropertyField;
        myPercentagePropertyField.setProperty(myProperty);
        setComponentColor(myPercentagePropertyField, isSelected, row, column);
//         final String contents = myPercentagePropertyField.getText();
        myPercentagePropertyField.addFocusListener(new FocusAdapter() {
          public void focusLost(FocusEvent e) {
        	  if(e.getOppositeComponent()!=myTable) {
                  stopCellEditing();
        	  }
          }
        });
//        myPercentagePropertyField.set(null);
//        myPercentagePropertyField.selectAll();
        myPercentagePropertyField.editProperty();
        myPercentagePropertyField.setBorder(b);
        myPercentagePropertyField.requestFocusInWindow();
        return myPercentagePropertyField;
      }

      if (myPropertyType.equals(Property.STOPWATCHTIME_PROPERTY)) {
        System.err.println("Editing a stopwatchtime!");
        if (myStopwatchTimeField == null) {
          myStopwatchTimeField = new StopwatchTimeField();
        }
        myStopwatchTimeField.setEditable(myProperty.isDirIn());

        lastComponent = myStopwatchTimeField;
        myStopwatchTimeField.setProperty(myProperty);
        setComponentColor(myStopwatchTimeField, isSelected, row, column);
        myStopwatchTimeField.addFocusListener(new FocusAdapter() {
          public void focusLost(FocusEvent e) {
//               System.err.println("Floatfield focus lost!");
//               System.err.println(">>>>"+((FloatPropertyField)e.getSource()).getText());
//               ((FloatPropertyField)e.getSource()).update();
        	  if(e.getOppositeComponent()!=myTable) {
                  stopCellEditing();
        	  }
          }
        });
//        myStopwatchTimeField.focusGained(null);
        myStopwatchTimeField.selectAll();
        myStopwatchTimeField.setBorder(b);
        myStopwatchTimeField.requestFocusInWindow();


//        Dit werkt niet... raar is dat

//        myClockTimeField.addKeyListener(new KeyAdapter() {
//          public void keyPressed(KeyEvent e) {
//            System.err.println("Key: " + e.getKeyText(e.getKeyCode()));
//            if ("Left".equals(e.getKeyText(e.getKeyCode())) || "Right".equals(e.getKeyText(e.getKeyCode())) || "Tab".equals(e.getKeyText(e.getKeyCode()))) {
//              if (myTable.isCellEditable(myTable.getSelectedRow(), myTable.getSelectedColumn())) {
//                myTable.editCellAt(myTable.getSelectedRow(), myTable.getSelectedColumn());
//              }
//            }
//          }
//        });

        return myStopwatchTimeField;
      }

      if (myPropertyType.equals(Property.CLOCKTIME_PROPERTY)) {
        System.err.println("Editing a time!");
        if (myClockTimeField == null) {
          myClockTimeField = new ClockTimeField();
        }
        myClockTimeField.setEditable(myProperty.isDirIn());
        myClockTimeField.showSeconds(false);
        lastComponent = myClockTimeField;
        myClockTimeField.setProperty(myProperty);
        setComponentColor(myClockTimeField, isSelected, row, column);
        myClockTimeField.addFocusListener(new FocusAdapter() {
          public void focusLost(FocusEvent e) {
//               System.err.println("Floatfield focus lost!");
//               System.err.println(">>>>"+((FloatPropertyField)e.getSource()).getText());
//               ((FloatPropertyField)e.getSource()).update();
        	  if(e.getOppositeComponent()!=myTable) {
                  stopCellEditing();
        	  }
          }
        });
//        myClockTimeField.focusGained(null);
        myClockTimeField.selectAll();
        myClockTimeField.setBorder(b);
        myClockTimeField.requestFocusInWindow();


//        Dit werkt niet... raar is dat

//        myClockTimeField.addKeyListener(new KeyAdapter() {
//          public void keyPressed(KeyEvent e) {
//            System.err.println("Key: " + e.getKeyText(e.getKeyCode()));
//            if ("Left".equals(e.getKeyText(e.getKeyCode())) || "Right".equals(e.getKeyText(e.getKeyCode())) || "Tab".equals(e.getKeyText(e.getKeyCode()))) {
//              if (myTable.isCellEditable(myTable.getSelectedRow(), myTable.getSelectedColumn())) {
//                myTable.editCellAt(myTable.getSelectedRow(), myTable.getSelectedColumn());
//              }
//            }
//          }
//        });

        return myClockTimeField;
      }

      if (myPropertyField == null) {
        myPropertyField = new TextPropertyField();
        myPropertyField.addFocusListener(new FocusAdapter() {
          public void focusLost(FocusEvent e) {
//              ((PropertyControlled)e.getSource()).update();
        	  if(e.getOppositeComponent()!=myTable) {
                  stopCellEditing();
        	  }
//            System.err.println("PROPERTYFIELD FIRED TOWARDS EDITOR");
          }
        });
      }
      myPropertyField.setEditable(myProperty.isDirIn());
      myPropertyField.setProperty(myProperty);
      lastComponent = myPropertyField;
      setComponentColor(myPropertyField, true, row, column);
      myPropertyField.setBorder(b);
      myPropertyField.requestFocusInWindow();
//      myPropertyField.selectAll();
      return myPropertyField;

    }

    return myPropertyField;
}

  private final void setComponentColor(Component c, boolean isSelected, int row, int column) {
    if (c == null) {
      return;
    }
    JComponent cc = (JComponent) c;
    if (isSelected) {
      cc.setBorder(new LineBorder(Color.black, 2));
    }
    else {
      cc.setBorder(null);
    }
//    if (isSelected) {
 //   c.setBackground(new Color(200, 200, 235));
//    } else {
//      if (row%2==0) {
//        c.setBackground(Color.white);
//      } else {
//        c.setBackground(new Color(240,240,240));
//      }
//    }
  }

//  private fireUpdate(

  public  void cancelCellEditing() {
    for (int i = 0; i < myListeners.size(); i++) {
      CellEditorListener ce = (CellEditorListener) myListeners.get(i);
      ce.editingCanceled(new ChangeEvent(myTable));
    }
  }

  
  
  
  public  boolean stopCellEditing() {
//    System.err.println("--------------------------------------------------------------->> Entering stopCellEditor!!!");
//    if (lastComponent != null) {
//		  System.err.println("Editing!");
	      if(lastComponent!=null && ((PropertyControlled)lastComponent).getProperty()!=null) {
		      updateProperty();
	     	  ( (PropertyControlled) lastComponent).setProperty(null);
	     	  
	      }
 	   //      FocusListener[] fl = lastComponent.getFocusListeners();
//      for (int i = 0; i < fl.length; i++) {
//        lastComponent.removeFocusListener(fl[i]);
//      }

      myTable.removeEditor();

      /** @todo Occasional null pointer here. Fix */

      if (lastComponent != null && ((PropertyControlled) lastComponent).getProperty() != null && ((PropertyControlled) lastComponent).getProperty().getType().equals(Property.SELECTION_PROPERTY)) {
    	  System.err.println("Clearing component: "+lastComponent.getClass());
    	  ( (PropertyControlled) lastComponent).setProperty(null);
       
    }
    

//    if(lastRow > -1){
//      for (int i = 0; i < myListeners.size(); i++) {
//        CellEditorListener ce = (CellEditorListener) myListeners.get(i);
//        ce.editingStopped(new MessageTableChangeEvent(myTable, lastRow, lastColumn));
//      }
//    }
    return true;
  }

	public void updateProperty() {
//		Thread.dumpStack();
		if (lastComponent != null) {
			try {
				Property p = ((PropertyControlled)lastComponent).getProperty();
				if(myProperty == p && myProperty!=null) {
					checkPropertyUpdate(p,p.getTypedValue());
				}
			} catch (PropertyTypeException ex1) {
				System.err.println(ex1.getMessage());
			} catch (NavajoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
//			System.err.println("Que?");
		}
	}

	private void checkPropertyUpdate(Property p, Object old) throws NavajoException {
//		System.err.println("VALU: "+((PropertyControlled) lastComponent).getProperty().getTypedValue());
		((PropertyControlled) lastComponent).update();
//		System.err.println("VALU2: "+((PropertyControlled) lastComponent).getProperty().getTypedValue());
//		Thread.dumpStack();
		Object newValue = p.getTypedValue();
		if (old==null) {
			if(newValue!=null) {
				myTable.fireChangeEvents(p, old, newValue);
			}
		} else {
			if(!old.equals(newValue)) {
				myTable.fireChangeEvents(p, old, newValue);
			}
		}
	}

  public  Object getCellEditorValue() {
    return myProperty;
  }


  public boolean isCellEditable(EventObject e) {

		if (myTable != null) {
			boolean b = myTable.getModel().isCellEditable(myTable.getSelectedRow(), myTable.getSelectedColumn());
			return b;
		}
		return false;
	}

//  public  Property getInitialProperty() {
//    return copy;
//  }

  public Object getOldValue() {
	  return myOldValue;
  }
  
  public  Property getProperty() {
    return myProperty;
  }

  public  void valueChanged(ListSelectionEvent e) {
    /**@todo: Implement this javax.swing.event.ListSelectionListener method*/
    throw new java.lang.UnsupportedOperationException("Method valueChanged() not yet implemented.");
  }

  public  boolean shouldSelectCell(EventObject parm1) {
    return true;
  }

  public  void addCellEditorListener(CellEditorListener ce) {
    myListeners.add(ce);
  }

  public  void removeCellEditorListener(CellEditorListener ce) {
    myListeners.remove(ce);
  }
  

  public boolean requestFocusInWindow() {
	  if (lastComponent != null) {
      return lastComponent.requestFocusInWindow();
    } else {
    	System.err.println(">> no last component ");
    }
    return false;
  }

public void propertyChange(PropertyChangeEvent p) {
	final PropertyControlled tc = (PropertyControlled)lastComponent;
	if(lastComponent!=null) {
		if(myProperty==tc.getProperty()) {
			runSyncInEventThread(new Runnable(){
				public void run() {
					tc.setProperty(myProperty);
				}});
		}
	}
}

public void runSyncInEventThread(Runnable r) {
    if (SwingUtilities.isEventDispatchThread()) {
      r.run();
    }
    else {
      try {
        SwingUtilities.invokeAndWait(r);
      }
      catch (InvocationTargetException ex) {
        throw new RuntimeException(ex);
      }
      catch (InterruptedException ex) {
      }
    }
  }

}
