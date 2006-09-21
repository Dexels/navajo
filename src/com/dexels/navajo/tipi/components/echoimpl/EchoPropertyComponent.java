package com.dexels.navajo.tipi.components.echoimpl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import nextapp.echo2.app.text.*;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.actions.PropertyEventListener;
import com.dexels.navajo.tipi.components.echoimpl.impl.BinaryPropertyImage;
import com.dexels.navajo.tipi.components.echoimpl.impl.MessageTable;
import com.dexels.navajo.tipi.components.echoimpl.impl.TipiEchoTextField;

import echopointng.ComboBox;
import echopointng.LabelEx;
import echopointng.SelectFieldEx;
import echopointng.able.Sizeable;

public class EchoPropertyComponent extends Grid implements TableCellRenderer {

	private static final int SELECTIONMODE_COMBO = 0;

	private static final int SELECTIONMODE_RADIO = 1;

	private Property myProperty = null;

	private boolean showLabel = true;

	private boolean useLabelForReadOnlyProperties = false;

	Label l = null;

	int label_indent = 100;

	int value_size = 0;

	private int selectionMode = SELECTIONMODE_COMBO;

	private Component currentComponent;

	private final ArrayList myPropertyEventListeners = new ArrayList();

	private boolean alwaysUseLabel = false;

	private boolean useCheckBoxes = false;

	protected boolean doUpdateRadioButtons = true;

	private int checkboxGroupColumnCount = 0;

	private int memoColumnCount = 0;

	private int memoRowCount = 0;

	private String myCapitalization = null;

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

	private void addPropertyComponent(Component comp) {
		GridLayoutData ld = new GridLayoutData();
		ld.setAlignment(new Alignment(Alignment.LEADING, Alignment.CENTER));
		addPropertyComponent(comp, ld);

	}

	private void addPropertyComponent(Component comp, LayoutData ld) {
		add(comp);
//		System.err.println("Adding component: " + comp);
		if (ld != null) {
			comp.setLayoutData(ld);
//			System.err.println("Layout: " + ld);
		} else {
			// ld = n
//			System.err.println("Null layout");
		}
	}

	private void addPropertyLabel(Property p) {
		if (p == null) {
			l = new Label(" ");
			l.setBackground(new Color(0, 255, 0));
			add(l);
			// currentComponent = l;
			return;
		}
		if (showLabel) {
			l = new Label();
//			System.err.println("Adding label::: ");
			if (p.getDescription() != null) {

				l.setText(p.getDescription());
			} else {
				l.setText(p.getName());
			}
			l.setTextAlignment(new Alignment(Alignment.LEADING,
					Alignment.DEFAULT));
			GridLayoutData gd = new GridLayoutData();
			gd
					.setAlignment(new Alignment(Alignment.LEADING,
							Alignment.DEFAULT));
			add(l);
			l.setLayoutData(gd);
//			System.err.println("SETTING COLUMN WIDTH::: " + label_indent);
			setColumnWidth(0, new Extent(label_indent, Extent.PX));
			if (value_size == 0) {
				setColumnWidth(1, new Extent(60, Extent.PERCENT));
			} else {
				setColumnWidth(1, new Extent(value_size, Extent.PX));
			}
			// currentComponent = l;

		} else {
//			System.err.println("Ignoring label!");
			// l = new Label("aap");
			// add(l);
		}

	}

	public void setProperty(Property p) {
		myProperty = p;
		removeAll();
		addPropertyLabel(p);
		String type = p.getType();
		if (type.equals(Property.SELECTION_PROPERTY)) {
			createSelectionProperty(p);
		}
		if (type.equals(Property.INTEGER_PROPERTY)
				|| type.equals(Property.STRING_PROPERTY)
				|| type.equals(Property.FLOAT_PROPERTY)) {
			createGenericTextProperty(p);
		}

		if (type.equals(Property.DATE_PROPERTY)) {
			createDateProperty(p);
		}
		if (type.equals(Property.MONEY_PROPERTY)) {
			createMoneyProperty(p);
		}
		if (type.equals(Property.PERCENTAGE_PROPERTY)) {
			createPercentageProperty(p);
		}
		if (type.equals(Property.CLOCKTIME_PROPERTY)) {
			createClockTimeProperty(p);
		}
		if (type.equals(Property.STOPWATCHTIME_PROPERTY)) {
			createStopwatchTimeProperty(p);
		}

		if (type.equals(Property.BOOLEAN_PROPERTY)) {
			createBooleanProperty(p);
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
		setLabelIndent(label_indent);
		if (currentComponent != null) {
			GridLayoutData gld = new GridLayoutData();
			// System.err.println("");
			gld
					.setAlignment(new Alignment(Alignment.LEADING,
							Alignment.DEFAULT));
			currentComponent.setLayoutData(gld);
		} else {
			System.err
					.println("Ayy, strange property: " + myProperty.getType());
		}
		if (label_indent > 0 && showLabel && getComponents().length > 1) {
			LabelEx ll = new LabelEx("");
			ll.setWidth(new Extent(label_indent, Extent.PX));
			ll.setHeight(new Extent(1, Extent.PX));
			ll.setVisible(false);
			add(ll);
		}
	}

	private void createSelectionProperty(Property p) {
		try {
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
				if (useCheckBoxes) {
					createCheckBoxes(p);
				} else {
					createMultiSelect(p);
				}
			}
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}

	private void createGenericTextProperty(Property p) {
		boolean isEdit = p.isDirIn();
//		 System.err.println("CREATING GENERIC TEXT. ISEDIT: "+isEdit+"affe!");
		if (alwaysUseLabel) {
			createLabel(p.getValue());
		} else {
			if ((isEdit || !useLabelForReadOnlyProperties)) {
				// System.err.println("My property type: "+p.getType());
				createTextField(p);

			} else {
				createLabel(p.getValue());

			}
		}
	}

	private void createMoneyProperty(Property p) {
		boolean isEdit = p.isDirIn();
		Money m = (Money) p.getTypedValue();
		if (m == null) {
			createLabel("-");
			return;
		}

		if (alwaysUseLabel) {
			createLabel(m.formattedString());
		} else {
			if ((isEdit || !useLabelForReadOnlyProperties)) {
				createTextField(p);

			} else {
				createLabel(m.formattedString());

			}
		}
	}

	private void createDateProperty(Property p) {
		boolean isEdit = p.isDirIn();
		Date m = (Date) p.getTypedValue();
		SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");

		if (m == null && !isEdit || alwaysUseLabel) {
			createLabel("-");
			return;
		}

		if (alwaysUseLabel) {
			createLabel("");
		} else {
			if ((isEdit || !useLabelForReadOnlyProperties)) {
				createDateTextField(p);

			} else {
				createLabel(sd.format(m));

			}
		}
	}

	private void createPercentageProperty(Property p) {
		boolean isEdit = p.isDirIn();
		Percentage m = (Percentage) p.getTypedValue();
		if (m == null) {
			createLabel("-");
			return;
		}

		if (alwaysUseLabel) {
			createLabel(m.formattedString());
		} else {
			if ((isEdit || !useLabelForReadOnlyProperties)) {
				createTextField(p);

			} else {
				createLabel(m.formattedString());

			}
		}
	}

	private void createStopwatchTimeProperty(Property p) {
		boolean isEdit = p.isDirIn();
		StopwatchTime m = (StopwatchTime) p.getTypedValue();
		if (m == null) {
			createLabel("-");
			return;
		}

		if (alwaysUseLabel) {
			createLabel(m.toString());
		} else {
			if ((isEdit || !useLabelForReadOnlyProperties)) {
				createTextField(p);

			} else {
				createLabel(m.toString());

			}
		}
	}

	private void createClockTimeProperty(Property p) {
		boolean isEdit = p.isDirIn();
		ClockTime m = (ClockTime) p.getTypedValue();
		if (m == null) {
			createLabel("-");
			return;
		}
		if (alwaysUseLabel) {
			createLabel(m.toString());
		} else {
			if ((isEdit || !useLabelForReadOnlyProperties)) {
				createTextField(p);
				// createClocktimeField(p);
			} else {
				createLabel(m.toString());

			}
		}
	}

	private void createBooleanProperty(Property p) {
		// StreamImageReference rir = new StreamImageReference();

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
				if (value_size != 0) {
					tf.setWidth(new Extent(value_size));
				}
				tf.setEnabled(false);
				tf.setForeground(new Color(90, 90, 90));
				boolean res = ((Boolean) p.getTypedValue()).booleanValue();

				tf.setText(res ? "ja" : "nee");
				addPropertyComponent(tf);
				currentComponent = tf;
			}

		}
	}

	private void createBooleanLabel(Property p) {
		Label ll = new Label(p.getValue());
		ll.setEnabled(false);
		ll.setForeground(new Color(90, 90, 90));
		boolean res = ((Boolean) p.getTypedValue()).booleanValue();
		ll.setText(res ? "ja" : "nee");

		addPropertyComponent(ll);
		currentComponent = ll;
	}

	private void createPasswordField(Property p) {

		final PasswordField tf = new PasswordField();
		if (value_size != 0) {
			tf.setWidth(new Extent(value_size));
		}
		tf.setText(p.getValue());
		addPropertyComponent(tf);
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
				PasswordField tf = (PasswordField) e.getSource();
				String text = tf.getText();
				myProperty.setValue(text);
				fireTipiEvent("onValueChanged");
			}
		});
		currentComponent = tf;
	}

	private void createBinaryImage(Property p) {
		final Label ll = new Label(p.getValue());
		ll.setIcon(new BinaryPropertyImage(p));
		addPropertyComponent(ll);

		currentComponent = ll;
	}

	private void createMemoProperty(Property p) {
		int row = memoRowCount == 0 ? 5 : memoRowCount;
		int column = memoColumnCount == 0 ? 80 : memoColumnCount;
		if (alwaysUseLabel) {
			createLabel(p.getValue());
			return;
		}
		final TextArea cb = new TextArea(new StringDocument(), "", column, row);
		// System.err.println("Creating memo property: "+column+" row: "+row+"
		// val: "+value_size);
		cb.setWidth(new Extent(memoColumnCount, Extent.EM));
		cb.setText(p.getValue());
		// cb.setBackground(new Color(255,0,0));
		// setBackground(new Color(0,0,255));

		addPropertyComponent(cb);
		cb.setEnabled(p.isDirIn());
		cb.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// System.err.println("Memo DIRECT action");
				if ("upper".equals(myCapitalization)) {
					cb.setText(cb.getText().toUpperCase());
				}
				if ("lower".equals(myCapitalization)) {
					cb.setText(cb.getText().toLowerCase());
				}
				myProperty.setValue(cb.getText());
				fireTipiEvent("onValueChanged");
			}
		});
		cb.getDocument().addDocumentListener(new DocumentListener() {

			public void documentUpdate(DocumentEvent e) {
				// System.err.println("Memo activity!");
				myProperty.setValue(cb.getText());
				fireTipiEvent("onValueChanged");
			}
		});
		cb.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				// System.err.println("AAAAAAAAAAAP:"+evt);
			}
		});
		currentComponent = cb;

	}

	private void createCheckBox(Property p) {
		final CheckBox cb = new CheckBox();
		cb.setSelected("true".equals(myProperty.getValue()));
		addPropertyComponent(cb);
		cb.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				myProperty.setValue(cb.isSelected());
				fireTipiEvent("onStateChanged");
			}
		});

		// double. This one may not be necessary.
		cb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myProperty.setValue(cb.isSelected());
			}
		});
		cb.setEnabled(myProperty.isDirIn());
		currentComponent = cb;
		// boolean res = ((Boolean)p.getTypedValue()).booleanValue();
		// cb.setSelected(res);
	}

	private void createLabel(String value) {
		if (value == null) {
			value = "";
		}
		final Label tf = new Label(value);
		tf.setTextAlignment(new Alignment(Alignment.LEADING, Alignment.CENTER));
		GridLayoutData gd = new GridLayoutData();
		gd.setAlignment(new Alignment(Alignment.LEADING, Alignment.CENTER));
		addPropertyComponent(tf, gd);
		currentComponent = tf;
	}

	private void createTextField(Property p) {
		final TipiEchoTextField tf = new TipiEchoTextField(p.getValue());
		if (value_size != 0) {
			tf.setWidth(new Extent(value_size-4));
		}
		if (!p.isDirIn()) {
			tf.setForeground(new Color(90, 90, 90));
		}
		// System.err.println("Created textfield. Dirin: "+p.isDirIn());
		addPropertyComponent(tf);

		tf.setEnabled(p.isDirIn());
		tf.getDocument().addDocumentListener(new DocumentListener() {
			public void documentUpdate(DocumentEvent e) {
				// System.err.println("Mies: "+e);
//				System.err.println("Capitalization: " + myCapitalization);
				String text = tf.getText();
				if ("upper".equals(myCapitalization)) {
					if (!text.equals(text.toUpperCase())) {
						tf.setText(tf.getText().toUpperCase());
					}
				}
				if ("lower".equals(myCapitalization)) {
					if (!text.equals(text.toLowerCase())) {
						tf.setText(tf.getText().toLowerCase());
					}
				}
				text = tf.getText();
				myProperty.setValue(text);
				fireTipiEvent("onValueChanged");

			}
		});
		tf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				 System.err.println("Aap: "+e);
				TipiEchoTextField tf = (TipiEchoTextField) e.getSource();
				if ("upper".equals(myCapitalization)) {
					tf.setText(tf.getText().toUpperCase());
				}
				if ("lower".equals(myCapitalization)) {
					tf.setText(tf.getText().toLowerCase());
				}

				String text = tf.getText();
				myProperty.setValue(text);
				fireTipiEvent("onValueChanged");
			}
		});
		// tf.addF
		// tf.addActionListener(new Foc() {
		// public void actionPerformed(ActionEvent e) {
		// // System.err.println("Aap: "+e);
		// TipiEchoTextField tf = (TipiEchoTextField) e.getSource();
		// String text = tf.getText();
		// myProperty.setValue(text);
		// fireTipiEvent("onValueChanged");
		// }
		// });
		currentComponent = tf;
		// GridLayoutData gd = new GridLayoutData();
		// gd.setBackground(new Color(255,0,0));
		// tf.setLayoutData(gd);
	}

	private void createDateTextField(Property p) {
		final SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");

		Date d = (Date) p.getTypedValue();
		final TipiEchoTextField tf = new TipiEchoTextField("");
		if (d != null) {
			tf.setText(sd.format(d));
		}
		if (value_size != 0) {
			tf.setWidth(new Extent(value_size-4));
		}
		if (!p.isDirIn()) {
			tf.setForeground(new Color(90, 90, 90));
		}
		// System.err.println("Created textfield. Dirin: "+p.isDirIn());
		addPropertyComponent(tf);

		tf.setEnabled(p.isDirIn());
		tf.getDocument().addDocumentListener(new DocumentListener() {
			public void documentUpdate(DocumentEvent e) {
				// System.err.println("Mies: "+e);
//				System.err.println("Capitalization: " + myCapitalization);
				String text = tf.getText();
				if ("upper".equals(myCapitalization)) {
					if (!text.equals(text.toUpperCase())) {
						tf.setText(tf.getText().toUpperCase());
					}
				}
				if ("lower".equals(myCapitalization)) {
					if (!text.equals(text.toLowerCase())) {
						tf.setText(tf.getText().toLowerCase());
					}
				}
				text = tf.getText();
				try {
					Date result = sd.parse(text);
					myProperty.setValue(result);

				} catch (ParseException p) {
					// ignore
					myProperty.setValue((Date) null);
				}

				fireTipiEvent("onStateChanged");

			}
		});

		currentComponent = tf;

	}

	private void createClocktimeField(final Property p) {
		final TipiEchoTextField tf = new TipiEchoTextField(p.getValue());
		if (value_size != 0) {
			tf.setWidth(new Extent(value_size));
		}
		if (!p.isDirIn()) {
			tf.setForeground(new Color(90, 90, 90));
		}
		addPropertyComponent(tf);
		tf.setEnabled(p.isDirIn());
		tf.getDocument().addDocumentListener(new DocumentListener() {
			public void documentUpdate(DocumentEvent e) {
				String text = tf.getText();
				ClockTime ct = new ClockTime(text);
				String oldVal = p.getValue();
				String ser = ct.toString();
				if (!text.equals(ser)) {
					tf.setText(ser);
				}
				myProperty.setAnyValue(ct);
				fireTipiEvent("onValueChanged");

			}
		});
		tf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = tf.getText();
				ClockTime ct = new ClockTime(text);
				String oldVal = p.getValue();
				String ser = ct.toString();
				if (!text.equals(ser)) {
					tf.setText(ser);
				}
				myProperty.setAnyValue(ct);
				fireTipiEvent("onValueChanged");

			}
		});
		currentComponent = tf;
		GridLayoutData gd = new GridLayoutData();
		gd.setBackground(new Color(255, 0, 0));
		tf.setLayoutData(gd);
	}

	private void createMultiSelect(Property p) throws NavajoException {
		ListBox lb = new ListBox(p.getAllSelections().toArray());
		addPropertyComponent(lb);
		lb.setEnabled(p.isDirIn());
		if (value_size != 0) {
			lb.setWidth(new Extent(value_size));
		}
		lb.setSelectionMode(ListSelectionModel.MULTIPLE_SELECTION);
		for (int i = 0; i < p.getAllSelections().size(); i++) {
			Selection current = (Selection) p.getAllSelections().get(i);
			lb.setSelectedIndex(i, current.isSelected());
		}
		lb.getSelectionModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ce) {
				// System.err.println("Noot: ");
				fireTipiEvent("onValueChanged");
			}
		});
		currentComponent = lb;
	}

	private void createRadioButtons(final Property p) throws NavajoException {
		final Component r = new Column();
		final Map buttons = new HashMap();
		// if (p.isDirIn()) {
		addPropertyComponent(r);
		ArrayList ss = p.getAllSelections();
		for (int i = 0; i < ss.size(); i++) {
			Selection cc = (Selection) ss.get(i);
			final RadioButton rb = new RadioButton();
			rb.setEnabled(p.isDirIn());
			// rb.setModel(model);
			buttons.put(cc.getValue(), rb);
			r.add(rb);
			rb.setText(cc.getName());
			rb.setActionCommand(cc.getValue());
			rb.setSelected(cc.isSelected());
			rb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						// System.err.println("radiobutton activity:
						// "+rb.getActionCommand());
						if (doUpdateRadioButtons) {
							doUpdateRadioButtons = false;
							updateRadioButtonList(rb, buttons, p);
							fireTipiEvent("onValueChanged");
							doUpdateRadioButtons = true;
						}
					} catch (NavajoException e) {
						e.printStackTrace();
					}
				}
			});
		}
		currentComponent = r;

		// } else {
		// final TipiEchoTextField tf = new TipiEchoTextField(p.getValue());
		// tf.setWidth(new Extent(value_size));
		// tf.setEnabled(false);
		// tf.setForeground(new Color(90,90,90));
		//            
		// String txt = "-";
		// if (!Selection.DUMMY_SELECTION.equals(p.getSelected().getName())) {
		// txt = p.getSelected().toString();
		// } else {
		// p.clearSelections();
		// }
		// tf.setText(txt);
		//            
		// add(tf);
		// currentComponent = tf;
		// }
	}

	public void setMemoRowCount(int row) {
		memoRowCount = row;
		if (getProperty() != null) {
			setProperty(getProperty());
		}
	}

	public void setMemoColumnCount(int column) {
		memoColumnCount = column;
		if (getProperty() != null) {
			setProperty(getProperty());
		}
	}

	private void createCheckBoxes(final Property p) throws NavajoException {
		final Component r = new Column();
		final Map buttons = new HashMap();
		addPropertyComponent(r);
		ArrayList ss = p.getAllSelections();
		for (int i = 0; i < ss.size(); i++) {
			Selection cc = (Selection) ss.get(i);
			final CheckBox rb = new CheckBox();
			// rb.setModel(model);
			buttons.put(cc, rb);
			r.add(rb);
			rb.setText(cc.getName());
			rb.setActionCommand(cc.getValue());
			rb.setSelected(cc.isSelected());
			rb.setEnabled(p.isDirIn());
			// System.err.println("Created checkbox, actionCommand:
			// "+cc.getValue());
			rb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// System.err.println("checkbox activity!");
					try {
						updateCheckboxButtonList(rb, buttons, p);
						fireTipiEvent("onStateChanged");
					} catch (NavajoException e) {
						e.printStackTrace();
					}
				}
			});

		}
		currentComponent = r;
	}

	protected void updateRadioButtonList(RadioButton rb, Map buttons, Property p)
			throws NavajoException {
		p.clearSelections();
		p.setSelected(rb.getActionCommand());

		// if (!rb.isSelected()) {
		// return;
		// }
		for (Iterator iter = buttons.values().iterator(); iter.hasNext();) {
			RadioButton element = (RadioButton) iter.next();
			Selection s = p.getSelectionByValue(element.getActionCommand());
			if (element == rb) {
				s.setSelected(true);
				rb.setSelected(true);
				// element.setSelected(true);
			} else {
				element.setSelected(false);
				s.setSelected(false);
			}
		}
	}

	protected void updateCheckboxButtonList(CheckBox rb, Map buttons, Property p)
			throws NavajoException {
		p.clearSelections();
		for (Iterator iter = buttons.keySet().iterator(); iter.hasNext();) {
			Selection sel = (Selection) iter.next();
			CheckBox element = (CheckBox) buttons.get(sel);
			sel.setSelected(element.isSelected());
		}
	}

	private void createComboBox(Property p) throws NavajoException {
		if (p.isDirIn()) {
			final SelectFieldEx lb = new SelectFieldEx(p.getAllSelections()
					.toArray());
			
			
			add(lb);
			lb.setEnabled(p.isDirIn());
			if (value_size != 0) {
				lb.setWidth(new Extent(value_size-4));
			}
			// PropertyImpl ppp = (PropertyImpl) p;
			ArrayList ss = p.getAllSelections();
			for (int i = 0; i < ss.size(); i++) {
				Selection cc = (Selection) ss.get(i);
				if (cc.isSelected()) {
					lb.setSelectedIndex(i);
					break;
				}
			} 
			if (p.getAllSelectedSelections().size() == 0) {
				p.setSelected((Selection) ss.get(0));
			}
			lb.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					try {
						int index = lb.getSelectedIndex();

						if (index >= 0) {
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

				}
			});

			currentComponent = lb;

		} else {
			final TipiEchoTextField tf = new TipiEchoTextField(p.getValue());
			if (value_size != 0) {
				tf.setWidth(new Extent(value_size));
			}
			tf.setEnabled(false);
			tf.setForeground(new Color(90, 90, 90));

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

	public Component getTableCellRendererComponent(final Table table,
			final Object value, final int column, final int row) {
		// try {
		showLabel = false;
		final MessageTable messageTable = (MessageTable) table;
		EchoPropertyComponent epc = new EchoPropertyComponent();
		Extent tableColumnwidth = messageTable.getColumnSize(column);
		epc.setUseLabelForReadOnlyProperties(true);
		epc.setLabelVisible(false);
		epc.setProperty((Property) value);
		// epc.setWidth(tableColumnwidth);

		boolean editable = ((Property) value).isDirIn();
		epc.addPropertyEventListener(new PropertyEventListener() {
			public void propertyEventFired(Property p, String eventType) {
				messageTable.fireTableEdited(p, column, row);

			}
		});
		// MessageTable mt = (MessageTable) table;
		// System.err.println("Sel: "+mt.getSelectedIndex());
		// System.err.println("row: "+row);
		// final Extent w = table.getColumnModel().getColumn(column).getWidth();
		// epc.addPropertyChangeListener(new PropertyChangeListener(){
		//
		// public void propertyChange(PropertyChangeEvent evt) {
		// System.err.println("Table RENDERER EVENT: "+value+" ->
		// "+column+"/"+row+" width: "+w);
		// }});
		int widthVal = tableColumnwidth.getValue();
		epc.setValueSize(widthVal + 4);
		epc.setWidth(new Extent(widthVal + 4, Extent.PX));

		// epc.setWidth(new Extent(widthVal,Extent.PX));
		// if (epc.currentComponent instanceof TextComponent) {
		// ((TextComponent)(epc.currentComponent)).setWidth(new
		// Extent(widthVal,Extent.PX));
		// }
		// if (epc.currentComponent instanceof SelectField) {
		// ((SelectField)(epc.currentComponent)).setWidth(new
		// Extent(widthVal,Extent.PX));
		// }
		epc.setColumnWidth(0, new Extent(2, Extent.PX));
		epc.setZebra(column, row, false);
		// TODO FIX DISABLED ZEBRA
		// epc.setBackground(null);
		// epc.currentComponent.setBackground(null);
		return epc;
		// } catch (NavajoException ex) {
		// ex.printStackTrace();
		// }
		// return null;
	}

	public void setLabelVisible(boolean b) {
		showLabel = b;
	}

	public void setLabelIndent(int indent) {
		label_indent = indent;
		if (getComponents().length > 1) {
			super.setColumnWidth(0, new Extent(label_indent, Extent.PX));
		}
	}

	public void setValueSize(int indent) {
		value_size = indent;
		if (getComponents().length > 1) {
			super.setColumnWidth(1, new Extent(value_size, Extent.PX));
		}
		if (currentComponent != null && currentComponent instanceof Sizeable) {
			((Sizeable) currentComponent)
					.setWidth(new Extent(indent, Extent.PX));
		} else {
			if (currentComponent instanceof TextComponent) {
				TextComponent tc = (TextComponent) currentComponent;
				tc.setWidth(new Extent(indent, Extent.PX));
			}

		}
		// setWidth(new Extent(indent, Extent.PX));
	}

	// public void setTextWidth(int width) {
	// }

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
		this.alwaysUseLabel = b;
	}

	public void setUseCheckBoxes(boolean b) {
		this.useCheckBoxes = b;
	}

	public void setZebra(int column, int row, boolean selected) {
		if (selected) {
			setPropertyBackground(new Color(200, 200, 255));
		} else {
			if (row % 2 == 0) {
				setPropertyBackground(new Color(255, 255, 255));
			} else {
				setPropertyBackground(new Color(230, 230, 230));
			}
		}
	}

	public void setPropertyBackground(Color c) {
		setBackground(c);
		Component cn = (Component) currentComponent;
		if (c != null) {
			cn.setBackground(c);
		}

	}

	public void setSelectiontype(String type) throws NavajoException {
		if ("radio".equals(type)) {
			selectionMode = SELECTIONMODE_RADIO;
		} else {
			selectionMode = SELECTIONMODE_COMBO;
		}
		if (getProperty() != null) {
			setProperty(getProperty());
		}

	}

	public void setCheckboxGroupColumnCount(int i) {
		// Ignored for now
	}

	public final void setCapitalization(String mode) {
		myCapitalization = mode;

	}

}
