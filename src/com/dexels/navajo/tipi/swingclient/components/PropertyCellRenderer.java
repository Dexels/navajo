package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import com.dexels.navajo.document.*;

//import com.dexels.navajo.document.nanoimpl.*;
//import com.dexels.sportlink.client.swing.components.treetable.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels.com
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class PropertyCellRenderer implements TableCellRenderer, ListCellRenderer {

	private String myPropertyType = null;
	private PropertyBox myPropertyBox = null;
	private MultiSelectPropertyBox myMultiSelectPropertyBox = null;
	private TextPropertyField myPropertyField = null;
	private JLabel formatStringField = null;
	private PropertyCheckBox myPropertyCheckBox = null;
	private DatePropertyField myDatePropertyField = null;
	private IntegerPropertyField myIntegerPropertyField = null;
	private FloatPropertyField myFloatPropertyField = null;
	private MoneyField myMoneyPropertyField = null;
	private PercentageField myPercentagePropertyField = null;
	private ClockTimeField myClockTimeField = null;
	private StopwatchTimeField myStopwatchTimeField = null;
	private PropertyPasswordField myPasswordField = null;
	private Property myProperty = null;
	private Map<String,ColumnAttribute> columnAttributes;
	private MessageTable myTable;
//	private TableCellRenderer def = new DefaultTableCellRenderer();
	private final JLabel l;
	private JButton rowButton = new JButton();
	JLabel readOnlyField = new JLabel();

//	private static final int DEFAULT_INSET = 1;

	Border b = new LineBorder(Color.black, 2);
	Border c = new LineBorder(Color.lightGray, 2);

	public PropertyCellRenderer() {
		//
		myPropertyField = new TextPropertyField();
		l = new JLabel() {
			@Override
			public boolean isOpaque() {
				return true;
			}
		};
		l.setPreferredSize(new Dimension(l.getPreferredSize().width, 17));
		l.setFont(myPropertyField.getFont());

		rowButton.setMargin(new Insets(0, 1, 0, 1));
		rowButton.setFont(new Font(rowButton.getFont().getName(), Font.BOLD, rowButton.getFont().getSize()));
	}

	private Color selectedColor = new Color(220, 220, 255);
	// private Color selectedColor = new Color(255, 217, 115);
	private Color highColor = new Color(255, 255, 255);
	private Color lowColor = new Color(240, 240, 240);
	private JLabel myPropertyLabel;

	public void setHighColor(Color c) {
		highColor = c;
	}

	public void setLowColor(Color c) {
		lowColor = c;
	}

	public void setSelectedColor(Color c) {
		selectedColor = c;
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public Color getLowColor() {
		return lowColor;
	}

	public Color getHighColor() {
		return highColor;
	}

	public JComponent getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		// JPanel myPanel = new JPanel();
		// myPanel.setLayout(new GridBagLayout());
		// long tt = System.currentTimeMillis();

		TableModel tm = table.getModel();
		if (!table.hasFocus()) {
			hasFocus = false;
		}
		boolean disabled = false;
		if (table.getSelectedRow() == row) {
			isSelected = true;
		}
		// if(isSelected && table.getEditingColumn() == column){

		if (MessageTable.class.isInstance(table)) {
			myTable = (MessageTable) table;
			columnAttributes = myTable.getColumnAttributes();
		}

		if (value == null) {
			setComponentColor(l, isSelected, row, column, false, tm.getRowCount(), disabled);
			// myPanel.add(l,new
			// GridBagConstraints(0,0,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new
			// Insets(DEFAULT_INSET,DEFAULT_INSET,DEFAULT_INSET,DEFAULT_INSET),0,0));
			return l;
		}

		if (Integer.class.isInstance(value)) {
			rowButton.setText("" + (row + 1));
			// myPanel.add(rowButton,new
			// GridBagConstraints(0,0,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new
			// Insets(DEFAULT_INSET,DEFAULT_INSET,DEFAULT_INSET,DEFAULT_INSET),0,0));

			return rowButton;
		}

		if (Property.class.isInstance(value)) {
			// System.err.println("Yes, a property");
			myProperty = (Property) value;
			myPropertyType = myProperty.getType();

			JComponent propComponent = createComponent(myProperty);
			if (propComponent instanceof PropertyControlled) {
				PropertyControlled pc = (PropertyControlled) propComponent;
				pc.setProperty(myProperty);
			}
			setComponentColor(propComponent, isSelected, row, column, false, tm.getRowCount(), disabled);
			// setComponentColor(myPanel, isSelected, row, column,
			// false,tm.getRowCount(), disabled);
			if (hasFocus) {
				if (myTable != null && myTable.isCellEditable(row, column)) {
					propComponent.setBorder(b);
				} else {
					propComponent.setBorder(c);
				}
			} else {
				propComponent.setBorder(BorderFactory.createEmptyBorder());

			}
			// myPanel.add(propComponent,new
			// GridBagConstraints(0,0,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new
			// Insets(DEFAULT_INSET,DEFAULT_INSET+3,DEFAULT_INSET,DEFAULT_INSET),0,0));
			// myPanel.revalidate();
			// ((MessageTable)table).setRowHeight(myPanel.getPreferredSize().height);

			return propComponent;

		}

		return l;
	}

	private JComponent createComponent(Property p) {

	  String type = p.getType();
      if(type.equals(Property.EXPRESSION_PROPERTY)) {
          try {
        	  type = p.getEvaluatedType();
            if(type == null || "".equals(type)) {
            	type = myTable.getTypeHint(p.getName());
              if(type != null) {
                System.err.println("*******************\n**********************\n********************\n Hint found: " + myPropertyType);
              }
            }
            if(type == null || "".equals(type)) {
            	type = Property.STRING_PROPERTY;
            }
// disabled = true;
          } catch(Throwable ex1) {
            ex1.printStackTrace();
          }
        }
	  if(type.equals(Property.PASSWORD_PROPERTY)) {
          if(myPasswordField == null) {
            myPasswordField = new PropertyPasswordField();
          }
          return myPasswordField;
      }
      if(type.equals(Property.PASSWORD_PROPERTY)) {
          if(myStopwatchTimeField == null) {
        	  myStopwatchTimeField = new StopwatchTimeField();
          }
          return myStopwatchTimeField;
      }
      if(type.equals(Property.BOOLEAN_PROPERTY)) {
          if(myPropertyCheckBox == null) {
        	  myPropertyCheckBox = new PropertyCheckBox();
          }
          return myPropertyCheckBox;
      }
      if(type.equals(Property.PERCENTAGE_PROPERTY)) {
          if(myPercentagePropertyField == null) {
        	  myPercentagePropertyField = new PercentageField();
          }
          return myPercentagePropertyField;
      }
      if(type.equals(Property.MONEY_PROPERTY)) {
          if(myMoneyPropertyField == null) {
        	  myMoneyPropertyField = new MoneyField();
          }
          return myMoneyPropertyField;
      }
      if(type.equals(Property.INTEGER_PROPERTY)) {
          if(myIntegerPropertyField == null) {
        	  myIntegerPropertyField = new IntegerPropertyField();
          }
          return myIntegerPropertyField;
      }
      if(type.equals(Property.FLOAT_PROPERTY)) {
          if(myFloatPropertyField == null) {
        	  myFloatPropertyField = new FloatPropertyField();
          }
          return myFloatPropertyField;
      }
      if(type.equals(Property.CLOCKTIME_PROPERTY)) {
          if(myClockTimeField == null) {
        	  myClockTimeField = new ClockTimeField();
          }
          return myClockTimeField;
      }
      if(type.equals(Property.DATE_PROPERTY)) {
          if(myDatePropertyField == null) {
        	  myDatePropertyField = new DatePropertyField();
              myDatePropertyField.setShowCalendarPickerButton(false);

          }
          return myDatePropertyField;
      }
      if(type.equals(Property.SELECTION_PROPERTY)) {
          if(p.getCardinality().equals("+")){
            if (myMultiSelectPropertyBox == null) {
                myMultiSelectPropertyBox = new MultiSelectPropertyBox();
              }
            return myMultiSelectPropertyBox;
          }else{
            if (myPropertyBox == null) {
              myPropertyBox = new PropertyBox();
            }
            return myPropertyBox;
          }
      }

      
          if(type.equals(Property.STRING_PROPERTY)) {
              if(myProperty.isDirIn()) {
                if(myPropertyField == null) {
                  myPropertyField = new TextPropertyField();
                }
                myPropertyField.setCapitalizationMode(null);
                myPropertyField.setProperty(myProperty);
// setComponentColor(myPropertyField, isSelected, row, column, false,
// tm.getRowCount(), disabled);
// updateComponentBorder(myPropertyField,hasFocus, row, column);
                return myPropertyField;

              } else {
                if(formatStringField == null) {
                  formatStringField = new JLabel();
                  if(myPropertyField == null) {
                    myPropertyField = new TextPropertyField();
                  }
                  formatStringField.setFont(myPropertyField.getFont());
                }
// formatStringField.setOpaque(true);
                Object o = myProperty.getTypedValue();
                if(o != null) {
                  formatStringField.setText("" + o);
                } else {
                  formatStringField.setText("");
                }
// if(hasFocus) {
// formatStringField.setBorder(c);
// }
// setComponentColor(formatStringField, isSelected, row, column, false,
// tm.getRowCount(), disabled);
// updateComponentBorder(formatStringField,hasFocus, row, column);
//                
                return formatStringField;
              }
          }
          
          System.err.println("Mystery type: "+type);
        if(myPropertyLabel == null) {
        myPropertyLabel = new JLabel();
        }
        myPropertyLabel.setText(""+p.getTypedValue());
        return myPropertyLabel;
           
  }

//	private void updateComponentBorder(JComponent vc, boolean hasFocus, int row, int column) {
//		if (hasFocus) {
//			if (myTable != null && myTable.isCellEditable(row, column)) {
//				vc.setBorder(b);
//			} else {
//				vc.setBorder(c);
//			}
//		} else {
//			vc.setBorder(new CompoundBorder(new EmptyBorder(new Insets(0, 2, 0, 2)), null));
//		}
//	}

	public void setComponentColor(Component c, boolean isSelected, int row, int col, boolean loading, int rowcount, boolean isDisabled) {
		
		JComponent cc = (JComponent) c;
		cc.setOpaque(true);
		if (isSelected) {

			c.setBackground(selectedColor);
		} else {
			if (row % 2 == 0) {
				c.setBackground(highColor);
			} else {
				c.setBackground(lowColor);
			}
		}

		if (columnAttributes != null && myProperty!=null) {
			ColumnAttribute ca = columnAttributes.get(myProperty.getName());
			if (ca != null) {
				if (ca.getType().equals(ColumnAttribute.TYPE_ROWCOLOR)) {
					String color = ca.getParam(myProperty.getValue());
					if (color != null) {
						Color clr = Color.decode(color);
						if (isSelected) {
							// Darker color
							Color dark = clr.darker();
							clr = dark;
						}
						c.setBackground(clr);
						if (myTable != null) {
							myTable.setRowColor(row, clr);
						}
					}
				}
			} else {
				Color clr = (myTable != null ? myTable.getRowColor(row) : null);
				if (clr != null) {
					Color dark = clr.darker();
					c.setBackground(isSelected ? dark : clr);
				}
			}
		}
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		Selection sel = (Selection) value;
		l.setText(sel == null ? "" : sel.toString());
		setComponentColor(l, isSelected, index, -1, false, list.getModel().getSize(), false);
		return l;
	}

	// public Component getTreeCellRendererComponent(JTree tree, Object value,
	// boolean selected, boolean expanded, boolean leaf, int row, boolean
	// hasFocus) {
	// JTreeTable.TreeTableCellRenderer tt;
	// }

//	private String listPropertyType = null;

	public void setPropertyTypeForListCell(String s) {

	}

	public static void main(String[] args) {
		Locale[] locales = NumberFormat.getAvailableLocales();
		double myNumber = -1234.56;
		NumberFormat form;
		for (int j = 0; j < 4; ++j) {
			System.out.println("FORMAT");
			for (int i = 0; i < locales.length; ++i) {
				if (locales[i].getCountry().length() == 0) {
					continue; // Skip language-only locales
				}
				System.out.print(locales[i].getDisplayName());
				switch (j) {
				case 0:
					form = NumberFormat.getInstance(locales[i]);
					break;
				case 1:
					form = NumberFormat.getIntegerInstance(locales[i]);
					break;
				case 2:
					form = NumberFormat.getCurrencyInstance(locales[i]);
					break;
				default:
					form = NumberFormat.getPercentInstance(locales[i]);
					break;
				}
				if (form instanceof DecimalFormat) {
					System.out.print(": " + ((DecimalFormat) form).toPattern());
				}
				System.out.print(" -> " + form.format(myNumber));
				try {
					System.out.println(" -> " + form.parse(form.format(myNumber)));
				} catch (ParseException e) {
				}
			}
		}
	}

}
