package com.dexels.navajo.tipi.components.echoimpl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import nextapp.echo2.app.*;
import nextapp.echo2.app.button.*;
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
import com.dexels.navajo.tipi.components.echoimpl.impl.BinaryPropertyImage;
import com.dexels.navajo.tipi.components.echoimpl.impl.MessageTable;
import com.dexels.navajo.tipi.components.echoimpl.impl.TipiEchoTextField;

import echopointng.ComboBox;
import echopointng.able.Sizeable;

public class EchoPropertyComponent extends Grid implements TableCellRenderer {

    private static final int SELECTIONMODE_COMBO = 0;
    private static final int SELECTIONMODE_RADIO = 1;

    private Property myProperty = null;

	private boolean showLabel = true;

	private boolean useLabelForReadOnlyProperties = false;
	Label l = null;

	int label_indent = 100;
	int value_size = 150;

    private int selectionMode = SELECTIONMODE_COMBO;
    
	private Component currentComponent;

	private final ArrayList myPropertyEventListeners = new ArrayList();
    private boolean alwaysUseLabel = false;
    private boolean useCheckBoxes = false;
    protected boolean doUpdateRadioButtons = true;

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
		removeAll();
		if (p == null) {
			l = new Label(" ");
				l.setBackground(new Color(0,255,0));
			add(l);
			currentComponent = l;
			return;
		}
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
			setColumnWidth(1,new Extent(value_size,Extent.PX));
			currentComponent = l;
			
		}

		String type = p.getType();
		if (type.equals(Property.SELECTION_PROPERTY)) {
			if (p.getCardinality().equals("1")) {
			    switch (selectionMode) {
                case SELECTIONMODE_COMBO:
                    createComboBox(p);
                    break;
                case SELECTIONMODE_RADIO:
                    createRadioButtons(p);
                    break;

                }
			} else {
                if (useCheckBoxes ) {
                    createCheckBoxes(p);
                } else {
                    createMultiSelect(p);
                }
			}
		}
		if (type.equals(Property.INTEGER_PROPERTY)
				|| type.equals(Property.STRING_PROPERTY)
				|| type.equals(Property.FLOAT_PROPERTY)
				|| type.equals(Property.DATE_PROPERTY)) {
			boolean isEdit = p.isDirIn();
			if (alwaysUseLabel) {
                createLabel(p);
            } else {
                if ((isEdit && !useLabelForReadOnlyProperties)) {
                    createTextField(p);

                } else {
                    createLabel(p);

                }
            }
		}
		if (type.equals(Property.BOOLEAN_PROPERTY)) {
			if (p.isDirIn()) {
                if (alwaysUseLabel) {
                    createBooleanLabel(p);
               } else {
                   createCheckBox(p);
               }
			} else {
				if (alwaysUseLabel || useLabelForReadOnlyProperties) {
					createBooleanLabel(p);
					
				} else {
					final TipiEchoTextField tf = new TipiEchoTextField(p.getValue());
					tf.setWidth(new Extent(value_size));
					tf.setEnabled(false);
					tf.setForeground(new Color(90,90,90));
					boolean res = ((Boolean)p.getTypedValue()).booleanValue();

					tf.setText(res?"ja":"nee");
					
					add(tf);
					currentComponent = tf;
				}
				
			}
		}

		if (type.equals(Property.MEMO_PROPERTY)) {
			createMemoProperty(p);
		}

		if (type.equals(Property.BINARY_PROPERTY)) {
			createBinaryImage(p);
		}

		if (type.equals(Property.PASSWORD_PROPERTY)) {
			createPasswordField(p);
		}
		GridLayoutData gld = new GridLayoutData();
		gld.setAlignment(new Alignment(Alignment.LEADING,Alignment.CENTER));
		currentComponent.setLayoutData(gld);
	}

    private void createBooleanLabel(Property p) {
        Label ll = new Label(p.getValue());
        ll.setEnabled(false);
        ll.setForeground(new Color(90,90,90));
        boolean res = ((Boolean)p.getTypedValue()).booleanValue();
        ll.setText(res?"ja":"nee");
        
        add(ll);
        currentComponent = ll;
    }

    private void createPasswordField(Property p) {
        final PasswordField tf = new PasswordField();
        tf.setWidth(new Extent(value_size));
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
        		myProperty.setValue(text);
        	}
        });
        currentComponent = tf;
    }

    private void createBinaryImage(Property p) {
        final Label ll = new Label(p.getValue());
        ll.setIcon(new BinaryPropertyImage(p));
        add(ll);
        currentComponent = ll;
    }

    private void createMemoProperty(Property p) {
        final TextArea cb = new TextArea();
       
        System.err.println("Creating memo property: "+value_size);
        cb.setWidth(new Extent(value_size,Extent.PX));
        cb.setText(p.getValue());
        add(cb);
        cb.setEnabled(p.isDirIn());
        cb.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent arg0) {
                System.err.println("Memo DIRECT action");
                myProperty.setValue(cb.getText());
                fireTipiEvent("onValueChanged");
            }});
        cb.getDocument().addDocumentListener(new DocumentListener(){

        	public void documentUpdate(DocumentEvent e) {
        	    System.err.println("Memo activity!");
                myProperty.setValue(cb.getText());
                fireTipiEvent("onValueChanged");
        	}});
        cb.addPropertyChangeListener(new PropertyChangeListener(){

            public void propertyChange(PropertyChangeEvent evt) {
                System.err.println("AAAAAAAAAAAP:"+evt);
            }});
        currentComponent = cb;

     
    }

    private void createCheckBox(Property p) {
        final CheckBox cb = new CheckBox();
        cb.setSelected(myProperty.getValue().equals("true"));
        add(cb);
        cb.addChangeListener(new ChangeListener(){

        	public void stateChanged(ChangeEvent e) {
        		myProperty.setValue(cb.isSelected());
                fireTipiEvent("onStateChanged");
        	}});
        
        // double. This one may not be necessary.
        cb.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		myProperty.setValue(cb.isSelected());
        	}});
        cb.setEnabled(myProperty.isDirIn());
        currentComponent = cb;
        boolean res = ((Boolean)p.getTypedValue()).booleanValue();
        cb.setSelected(res);
    }

    private void createLabel(Property p) {
        String value = p.getValue();
        if (value==null) {
        	value = "";
        }
        final Label tf = new Label(p.getValue());
        tf.setTextAlignment(new Alignment(Alignment.LEADING,Alignment.CENTER));
        add(tf);
        GridLayoutData gd = new GridLayoutData();
        gd.setAlignment(new Alignment(Alignment.LEADING,Alignment.CENTER));
        tf.setLayoutData(gd);
        currentComponent = tf;
    }

    private void createTextField(Property p) {
        final TipiEchoTextField tf = new TipiEchoTextField(p.getValue());
        tf.setWidth(new Extent(value_size));
        if (!p.isDirIn()) {
        	tf.setForeground(new Color(90,90,90));
        }
        add(tf);
        tf.setEnabled(p.isDirIn());
        tf.getDocument().addDocumentListener(new DocumentListener() {
        	public void documentUpdate(DocumentEvent e) {
                System.err.println("Mies: "+e);
                String text = tf.getText();
        		myProperty.setValue(text);
                fireTipiEvent("onStateChanged");

        	}
        });
        tf.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    System.err.println("Aap: "+e);
                TipiEchoTextField tf = (TipiEchoTextField) e
        				.getSource();
        		String text = tf.getText();
        		myProperty.setValue(text);
                fireTipiEvent("onValueChanged");
        	}
        });
        currentComponent = tf;
    }

    private void createMultiSelect(Property p) throws NavajoException {
        ListBox lb = new ListBox(p.getAllSelections().toArray());
        add(lb);
        lb.setEnabled(p.isDirIn());
        lb.setWidth(new Extent(value_size));
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
    
    private void createRadioButtons(final Property p) throws NavajoException {
        final Component r = new Column();
        final Map buttons = new HashMap();
//        if (p.isDirIn()) {
            add(r);
                ArrayList ss = p.getAllSelections();
                for (int i = 0; i < ss.size(); i++) {
                    Selection cc = (Selection)ss.get(i);
                    final RadioButton rb = new RadioButton();
                    rb.setEnabled(p.isDirIn());
//                    rb.setModel(model);
                    buttons.put(cc.getValue(), rb);
                    r.add(rb);
                    rb.setText(cc.getName());
                    rb.setActionCommand(cc.getValue());
                    rb.setSelected(cc.isSelected());
                    rb.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent arg0) {
                            try {
                                System.err.println("radiobutton activity: "+rb.getActionCommand());
                                if (doUpdateRadioButtons) {
                                    doUpdateRadioButtons = false;
                                    updateRadioButtonList(rb,buttons,p);
                                    fireTipiEvent("onStateChanged");
                                    doUpdateRadioButtons = true;
                                }
                            } catch (NavajoException e) {
                                e.printStackTrace();
                            }
                        }});
                }
            currentComponent = r;
        
//        } else {
//            final TipiEchoTextField tf = new TipiEchoTextField(p.getValue());
//            tf.setWidth(new Extent(value_size));
//            tf.setEnabled(false);
//            tf.setForeground(new Color(90,90,90));
//            
//            String txt = "-";
//            if (!Selection.DUMMY_SELECTION.equals(p.getSelected().getName())) {
//                txt = p.getSelected().toString();
//            } else {
//                p.clearSelections();
//            }
//            tf.setText(txt);
//            
//            add(tf);
//            currentComponent = tf;
//        }
    }


    private void createCheckBoxes(final Property p) throws NavajoException {
        final Component r = new Column();
        final Map buttons = new HashMap();
            add(r);
                ArrayList ss = p.getAllSelections();
                for (int i = 0; i < ss.size(); i++) {
                    Selection cc = (Selection)ss.get(i);
                    final CheckBox rb = new CheckBox();
//                    rb.setModel(model);
                    buttons.put(cc, rb);
                    r.add(rb);
                    rb.setText(cc.getName());
                    rb.setActionCommand(cc.getValue());
                    rb.setSelected(cc.isSelected());
                    rb.setEnabled(p.isDirIn());
                    System.err.println("Created checkbox, actionCommand: "+cc.getValue());
                    rb.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent arg0) {
                            System.err.println("checkbox activity!");
                                try {
                                updateCheckboxButtonList(rb,buttons,p);
                                fireTipiEvent("onStateChanged");
                            } catch (NavajoException e) {
                                e.printStackTrace();
                            }
                        }});

                    
                }
            currentComponent = r;
        }

    
    
    protected void updateRadioButtonList(RadioButton rb, Map buttons, Property p) throws NavajoException {
        p.clearSelections();
        p.setSelected(rb.getActionCommand());
        
//        if (!rb.isSelected()) {
//            return;
//        }
        for (Iterator iter = buttons.values().iterator(); iter.hasNext();) {
            RadioButton element = (RadioButton) iter.next();
            Selection s = p.getSelectionByValue(element.getActionCommand());
            if (s!=null) {
                s.setSelected(element.isSelected());
            }
            if (element==rb) {
                System.err.println("Setting: "+element.getActionCommand());
//                element.setSelected(true);
            } else {
                System.err.println("Clearing: "+element.getActionCommand());
                element.setSelected(false);
            }
        }
        }    
    
    protected void updateCheckboxButtonList(CheckBox rb, Map buttons, Property p) throws NavajoException {
        p.clearSelections();
        for (Iterator iter = buttons.keySet().iterator(); iter.hasNext();) {
            Selection sel = (Selection)iter.next();
            CheckBox element = (CheckBox) buttons.get(sel);
            sel.setSelected(element.isSelected());
        }
        }    
    
    private void createComboBox(Property p) throws NavajoException {
        if (p.isDirIn()) {
        	final SelectField lb = new SelectField(p.getAllSelections().toArray());
        	add(lb);
        	lb.setEnabled(p.isDirIn());
        	lb.setWidth(new Extent(value_size));
        	PropertyImpl ppp = (PropertyImpl)p;
        		ArrayList ss = p.getAllSelections();
        		for (int i = 0; i < ss.size(); i++) {
        			Selection cc = (Selection)ss.get(i);
        			if (cc.isSelected()) {
        				lb.setSelectedIndex(i);
        				break;
        			}
        		}
        		if (p.getAllSelectedSelections().size()==0) {
        			p.setSelected((Selection)ss.get(0));
        		}
        	lb.addActionListener(new ActionListener() {

        		public void actionPerformed(ActionEvent e) {
        			try {
        				int index = lb.getSelectedIndex();
        				
        				if(index>=0) {
        					Selection s = (Selection) lb.getSelectedItem();
        					if (s != null) {
        						myProperty.setSelected(s);
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

        	currentComponent = lb;
        
        } else {
        	final TipiEchoTextField tf = new TipiEchoTextField(p.getValue());
        	tf.setWidth(new Extent(value_size));
        	tf.setEnabled(false);
        	tf.setForeground(new Color(90,90,90));
        	
        	String txt = "-";
        	if (!Selection.DUMMY_SELECTION.equals(p.getSelected().getName())) {
        		txt = p.getSelected().toString();
        	} else {
        		p.clearSelections();
        	}
        	tf.setText(txt);
        	
        	add(tf);
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
			MessageTable mt = (MessageTable)table;
//			System.err.println("Sel: "+mt.getSelectedIndex());
//			System.err.println("row: "+row);
			final Extent w = table.getColumnModel().getColumn(column).getWidth();
//			epc.addPropertyChangeListener(new PropertyChangeListener(){
//
//				public void propertyChange(PropertyChangeEvent evt) {
//					System.err.println("Table RENDERER EVENT: "+value+" -> "+column+"/"+row+" width: "+w);
//				}});
			epc.setWidth(w);
			epc.setZebra(column,row,false);
// TODO FIX DISABLED ZEBRA			
//			epc.setBackground(null);
//			epc.currentComponent.setBackground(null);
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
	}

	public void setValueSize(int indent) {
		value_size = indent;
 		if(getComponents().length>1) {
 			super.setColumnWidth(1,new Extent(value_size,Extent.PX));
 		}
 		if (currentComponent!=null && currentComponent instanceof Sizeable) {
 			((Sizeable)currentComponent).setWidth(new Extent(indent,Extent.PX));
		}
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

    public void setAlwaysUseLabel(boolean b) {
        this.alwaysUseLabel  = b;
    }

    public void setUseCheckBoxes(boolean b) {
        this.useCheckBoxes = b;
    }
    public void setZebra(int column, int row, boolean selected) {
		if (selected) {
			setBackground(new Color(200,200,255));
		} else {
			if (row % 2 ==0) {
				setBackground(new Color(255,255,255));
			} else {
				setBackground(new Color(230,230,230));
			}
		}
	}

    public void setSelectiontype(String type) throws NavajoException {
        if ("radio".equals(type)) {
            selectionMode = SELECTIONMODE_RADIO;
        } else {
            selectionMode = SELECTIONMODE_COMBO;
        }
        if (getProperty()!=null) {
            setProperty(getProperty());
        }
    
    }

}
