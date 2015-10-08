package com.dexels.navajo.tipi.swingclient.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

@SuppressWarnings("rawtypes")
public class PropertyCellRenderer implements TableCellRenderer, ListCellRenderer {

    private final static Logger logger = LoggerFactory.getLogger(PropertyCellRenderer.class);
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

    private MessageTable myTable;

    private final JLabel l;
    private JButton rowButton = new JButton();
    JLabel readOnlyField = new JLabel();

    Border b = new LineBorder(Color.black, 2);
    Border c = new LineBorder(Color.lightGray, 2);

    public PropertyCellRenderer() {
        //
        myPropertyField = new TextPropertyField();
        l = new JLabel() {
            private static final long serialVersionUID = 4291863967079004796L;

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

    public JComponent getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {

        TableModel tm = table.getModel();
        if (!table.hasFocus()) {
            hasFocus = false;
        }
        boolean disabled = false;
        if (table.getSelectedRow() == row) {
            isSelected = true;
        }

        if (MessageTable.class.isInstance(table)) {
            myTable = (MessageTable) table;

        }

        if (value == null) {
            // logger.info("Row: "+row+" column: "+column);
            setComponentColor(l, isSelected, row, column, false, tm.getRowCount(), disabled);

            return l;
        }

        if (Integer.class.isInstance(value)) {
            rowButton.setText("" + (row + 1));
            return rowButton;
        }

        if (Property.class.isInstance(value)) {
            // logger.info("Yes, a property");
            myProperty = (Property) value;
            myPropertyType = myProperty.getType();

            JComponent propComponent = createComponent(myProperty);
            if (propComponent instanceof PropertyControlled) {
                PropertyControlled pc = (PropertyControlled) propComponent;
                pc.setProperty(myProperty);
            }
            setComponentColor(propComponent, isSelected, row, column, false, tm.getRowCount(), disabled);
            if (hasFocus) {
                if (myTable != null && myTable.isCellEditable(row, column)) {
                    propComponent.setBorder(b);
                } else {
                    propComponent.setBorder(c);
                }
            } else {
                propComponent.setBorder(BorderFactory.createEmptyBorder());

            }

            return propComponent;

        }

        return l;
    }

    private JComponent createComponent(Property p) {

        String type = p.getType();
        if (type.equals(Property.EXPRESSION_PROPERTY)) {
            try {
                type = p.getEvaluatedType();
                if (type == null || "".equals(type)) {
                    type = myTable.getTypeHint(p.getName());
                    if (type != null) {
                        System.err
                                .println("*******************\n**********************\n********************\n Hint found: "
                                        + myPropertyType);
                    }
                }
                if (type == null || "".equals(type)) {
                    type = Property.STRING_PROPERTY;
                }
            } catch (Throwable ex1) {
            	logger.error("Error: ", ex1);
            }
        }
        if (type.equals(Property.PASSWORD_PROPERTY)) {
            if (myPasswordField == null) {
                myPasswordField = new PropertyPasswordField();
            }
            return myPasswordField;
        }
        if (type.equals(Property.PASSWORD_PROPERTY)) {
            if (myStopwatchTimeField == null) {
                myStopwatchTimeField = new StopwatchTimeField();
            }
            return myStopwatchTimeField;
        }
        if (type.equals(Property.BOOLEAN_PROPERTY)) {
            if (myPropertyCheckBox == null) {
                myPropertyCheckBox = new PropertyCheckBox();
            }
            return myPropertyCheckBox;
        }
        if (type.equals(Property.PERCENTAGE_PROPERTY)) {
            if (myPercentagePropertyField == null) {
                myPercentagePropertyField = new PercentageField();
            }
            return myPercentagePropertyField;
        }
        if (type.equals(Property.MONEY_PROPERTY)) {
            if (myMoneyPropertyField == null) {
                myMoneyPropertyField = new MoneyField();
            }
            return myMoneyPropertyField;
        }
        if (type.equals(Property.INTEGER_PROPERTY)) {
            if (myIntegerPropertyField == null) {
                myIntegerPropertyField = new IntegerPropertyField();
            }
            return myIntegerPropertyField;
        }
        if (type.equals(Property.FLOAT_PROPERTY)) {
            if (myFloatPropertyField == null) {
                myFloatPropertyField = new FloatPropertyField();
            }
            return myFloatPropertyField;
        }
        if (type.equals(Property.CLOCKTIME_PROPERTY)) {
            if (myClockTimeField == null) {
                myClockTimeField = new ClockTimeField();
            }
            return myClockTimeField;
        }
        if (type.equals(Property.DATE_PROPERTY)) {
            if (myDatePropertyField == null) {
                myDatePropertyField = new DatePropertyField();
                myDatePropertyField.setShowCalendarPickerButton(false);

            }
            return myDatePropertyField;
        }
        if (type.equals(Property.SELECTION_PROPERTY)) {
            if (p.getCardinality().equals("+")) {
                if (myMultiSelectPropertyBox == null) {
                    myMultiSelectPropertyBox = new MultiSelectPropertyBox();
                }
                return myMultiSelectPropertyBox;
            } else {
                if (myPropertyBox == null) {
                    myPropertyBox = new PropertyBox();
                }
                return myPropertyBox;
            }
        }

        if (type.equals(Property.STRING_PROPERTY)) {
            if (myProperty.isDirIn()) {
                if (myPropertyField == null) {
                    myPropertyField = new TextPropertyField();
                }
                myPropertyField.setCapitalizationMode(null);
                myPropertyField.setProperty(myProperty);

                return myPropertyField;

            } else {
                if (formatStringField == null) {
                    formatStringField = new JLabel();
                    if (myPropertyField == null) {
                        myPropertyField = new TextPropertyField();
                    }
                    formatStringField.setFont(myPropertyField.getFont());
                }
                Object o = myProperty.getTypedValue();
                if (o != null) {
                    formatStringField.setText("" + o);
                } else {
                    formatStringField.setText("");
                }

                return formatStringField;
            }
        }

        // logger.info("Mystery type: "+type);
        if (myPropertyLabel == null) {
            myPropertyLabel = new JLabel();
        }
        myPropertyLabel.setText("" + p.getTypedValue());
        return myPropertyLabel;

    }

    public void setComponentColor(Component c, boolean isSelected, int row, int col, boolean loading, int rowcount,
            boolean isDisabled) {
        if (row < 0) {
            return;
        }
        String a=  " ";
        JComponent cc = (JComponent) c;
        cc.setOpaque(true);
       

        Color clr = (myTable != null ? myTable.getRowBackgroundColor(row, col) : null);
        if (clr != null) {
            c.setBackground(isSelected ? clr.darker() : clr);
        } else {
            if (isSelected) {
                c.setBackground(selectedColor);
            } else {
                if (row % 2 == 0) {
                    c.setBackground(highColor);
                } else {
                    c.setBackground(lowColor);
                }
            }
        }

        clr = (myTable != null ? myTable.getRowForegroundColor(row, col) : null);
        if (clr != null) {
            c.setForeground(isSelected ? clr.darker() : clr);
        }

    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        Selection sel = (Selection) value;
        l.setText(sel == null ? "" : sel.toString());
        setComponentColor(l, isSelected, index, -1, false, list.getModel().getSize(), false);
        return l;
    }

    public void setPropertyTypeForListCell(String s) {

    }

    public static void main(String[] args) {
        Locale[] locales = NumberFormat.getAvailableLocales();
        double myNumber = -1234.56;
        NumberFormat form;
        for (int j = 0; j < 4; ++j) {
            logger.info("FORMAT");
            for (int i = 0; i < locales.length; ++i) {
                if (locales[i].getCountry().length() == 0) {
                    continue; // Skip language-only locales
                }
                logger.info(locales[i].getDisplayName());
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
                logger.info(" -> " + form.format(myNumber));
                try {
                    logger.info(" -> " + form.parse(form.format(myNumber)));
                } catch (ParseException e) {
                }
            }
        }
    }

}
