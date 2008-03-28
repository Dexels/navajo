package com.dexels.navajo.echoclient.components;

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
import nextapp.echo2.app.filetransfer.UploadEvent;
import nextapp.echo2.app.filetransfer.UploadListener;
import nextapp.echo2.app.filetransfer.UploadSelect;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.list.ListSelectionModel;
import nextapp.echo2.app.table.TableCellRenderer;
import nextapp.echo2.app.text.*;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.*;

import echopointng.*;
import echopointng.able.Sizeable;

public class EchoPropertyComponent extends Grid implements TableCellRenderer {

	private static final int SELECTIONMODE_COMBO = 0;

	private static final int SELECTIONMODE_RADIO = 1;

	private static final int SELECTIONMODE_CHECKBOXES = 2;

	private static final int SELECTIONMODE_LIST = 3;

	public static final String PROPERTY_BACKGROUND = "propertyBackground";
	private Property myProperty = null;

	private boolean showLabel = true;

	private boolean useLabelForReadOnlyProperties = false;

	LabelEx l = null;

	int label_indent = 100;

	int value_size = 0;

	private int selectionMode = SELECTIONMODE_COMBO;

	private Component currentComponent;

	private final List<PropertyEventListener> myPropertyEventListeners = new ArrayList<PropertyEventListener>();

	private boolean alwaysUseLabel = false;

	private boolean useCheckBoxes = false;

	protected boolean doUpdateRadioButtons = true;

	private int checkboxGroupColumnCount = 0;

	private int memoColumnCount = 0;

	private int memoRowCount = 0;

	private String myCapitalization = null;

	private int listboxRowCount;

	private int maxImageWidth = 100;

	private int maxImageHeight = 100;

	private boolean allowDateOverlay = false;

	private boolean allowLineWrap = false;

	private static final Extent PERCENTAGE = new Extent(97, Extent.PERCENT);
	private static final Extent SMALL_PERCENTAGE = new Extent(50, Extent.PERCENT);

	public EchoPropertyComponent() {
		super(2);
		setInsets(new Insets(3, 3, 3, 3));
	}

	public final void addPropertyEventListener(PropertyEventListener pel) {
		myPropertyEventListeners.add(pel);
	}

	public final void removePropertyEventListner(PropertyEventListener pel) {
		myPropertyEventListeners.remove(pel);
	}

	public final void firePropertyEvents(Property p, String eventType) {
		for (int i = 0; i < myPropertyEventListeners.size(); i++) {
			PropertyEventListener current = myPropertyEventListeners.get(i);
			current.propertyEventFired(p, eventType);
		}
	}
	
	public final void setAllowDateOverlay(boolean b) {
		allowDateOverlay = b;
	}

	public final void fireTipiEvent(String type) {
		// try {
		// System.err.println("Firing tipi event from property: "+type+" myProp:
		// "+myProperty.getFullPropertyName());
		// } catch (NavajoException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		firePropertyEvents(myProperty, type);
	}

	private void addPropertyComponent(Component comp) {
		GridLayoutData ld = new GridLayoutData();
		ld.setAlignment(new Alignment(Alignment.LEADING, Alignment.CENTER));

		addPropertyComponent(comp, ld);

	}

	private void addPropertyComponent(Component comp, LayoutData ld) {
		add(comp);
		// System.err.println("Adding component: " + comp);
		if (ld != null) {
			comp.setLayoutData(ld);
			// System.err.println("Layout: " + ld);
		} else {
			// ld = n
			// System.err.println("Null layout");
		}
	}

	private void addPropertyLabel(Property p) {
		if (p == null) {
			l = new LabelEx("");
			// l.setStyleName("Default");
			l.setLineWrap(false);
			// l.setBackground(new Color(0, 255, 0));
			add(l);
			// currentComponent = l;
			return;
		}
		if (showLabel) {
			l = new LabelEx();
			// System.err.println("Adding label::: ");
			if (p.getDescription() != null) {

				l.setText(p.getDescription());
			} else {
				l.setText(p.getName());
			}
			l.setTextAlignment(new Alignment(Alignment.LEADING, Alignment.DEFAULT));
			GridLayoutData gd = new GridLayoutData();
			gd.setAlignment(new Alignment(Alignment.LEADING, Alignment.DEFAULT));
			add(l);
			l.setLayoutData(gd);
			// System.err.println("SETTING COLUMN WIDTH::: " + label_indent);
			// System.err.println("SETTING VALUE WIDTH::: " + value_size);

			setColumnWidth(0, new Extent(label_indent, Extent.PX));
			if (value_size == 0) {
				setColumnWidth(1, SMALL_PERCENTAGE);
			} else {
				setColumnWidth(1, new Extent(value_size, Extent.PX));
			}

		} else {
			// System.err.println("Ignoring label!");
			// l = new Label("aap");
			// add(l);
		}

	}

	public void setProperty(Property p) {
		if (p != null) {
			// System.err.println("Setting property: "+p.getName()+" type:
			// "+p.getType()+" value: "+p.getTypedValue());

		}
		myProperty = p;
		removeAll();
		addPropertyLabel(p);
		String type = p.getType();
		if (type.equals(Property.SELECTION_PROPERTY)) {
			createSelectionProperty(p);
		}
		if (type.equals(Property.INTEGER_PROPERTY) || type.equals(Property.STRING_PROPERTY) || type.equals(Property.FLOAT_PROPERTY)) {
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
			gld.setAlignment(new Alignment(Alignment.LEADING, Alignment.DEFAULT));
			currentComponent.setLayoutData(gld);
		} else {
			System.err.println("Ayy, strange property: " + myProperty.getType());
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
			if (alwaysUseLabel) {
				Selection s = p.getSelected();
				// createLabel(myCapitalization);
				if (s == null || s.getName().equals(Selection.DUMMY_SELECTION)) {
					createLabel("-");
				} else {
					createLabel(s.getName());
				}
				return;
			}
			if (p.getCardinality().equals("1")) {
				switch (selectionMode) {
				case SELECTIONMODE_RADIO:
					createRadioButtons(p);
					break;
				case SELECTIONMODE_COMBO:
				default:
					createComboBox(p);

				}
			} else {
				switch (selectionMode) {
				case SELECTIONMODE_LIST:
					createMultiSelect(p);
					break;
				case SELECTIONMODE_CHECKBOXES:
				default:
					createCheckBoxes(p);
					break;

				}
			}
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}

	private void createGenericTextProperty(Property p) {
		boolean isEdit = p.isDirIn();
		// System.err.println("CREATING GENERIC TEXT. ISEDIT: "+isEdit+"affe!");
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

		if (m == null && (!isEdit || alwaysUseLabel)) {
			createLabel("-");
			return;
		}

		if (alwaysUseLabel) {
			createLabel(sd.format(m));
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
		if (m == null && !isEdit) {
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
				Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(tf.getClass(), "Default");
				tf.setStyle(ss);

				if (value_size != 0) {
					tf.setWidth(new Extent(value_size));
				} else {
					tf.setWidth(PERCENTAGE);
				}
				// tf.setStyleName("Default");
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
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(ll.getClass(), "Default");
		ll.setStyle(ss);

		ll.setEnabled(false);
		ll.setForeground(new Color(90, 90, 90));
		boolean res = ((Boolean) p.getTypedValue()).booleanValue();
		ll.setText(res ? "ja" : "nee");

		addPropertyComponent(ll);
		currentComponent = ll;
	}

	private void createPasswordField(Property p) {

		final PasswordField tf = new PasswordField();
		// tf.setStyleName("Default");
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(tf.getClass(), "Default");
		tf.setStyle(ss);

		if (value_size != 0) {
			tf.setWidth(new Extent(value_size - 4));
		} else {
			tf.setWidth(PERCENTAGE);
		}
		tf.setText(p.getValue());
		addPropertyComponent(tf);
		boolean isEdit = p.isDirIn();
		tf.setEnabled(isEdit);
		tf.getDocument().addDocumentListener(new DocumentListener() {
			public void documentUpdate(DocumentEvent e) {
				String text = tf.getText();
				myProperty.setValue(text);
				fireTipiEvent("onValueChanged");
			}
		});
		tf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PasswordField tf = (PasswordField) e.getSource();
				String text = tf.getText();
				myProperty.setValue(text);
				fireTipiEvent("onEnter");
				fireTipiEvent("onActionPerformed");
			}
		});

		currentComponent = tf;
	}

	private void createBinaryImage(final Property p) {
		Column g = new Column();
		final Button ll = new Button();
		// ll.setStyleName("Default");
		final UploadSelect bb = new UploadSelect();
		bb.setSendButtonDisplayed(false);
		bb.setWidth(new Extent(Math.max(150, maxImageWidth), Extent.PX));
		int cc = bb.getComponentCount();
		final Binary b = (Binary) p.getTypedValue();
		bb.setVisible(false);
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(ll.getClass(), "BinaryImage");
		if (b == null || b.isEmpty()) {
			if (p.getDescription() != null || "".equals(p.getDescription())) {
				ll.setText("[" + p.getDescription() + "]");
			} else {
				ll.setText("...");
			}
			ll.setIcon(null);
		}
		ll.setStyle(ss);
		ll.setId("aap" + new Random(System.currentTimeMillis()).nextDouble());
		if (b != null && !b.isEmpty()) {
			ll.setIcon(new BinaryPropertyImage(p, maxImageWidth, maxImageHeight));
			ll.setText("");
		}
		ll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Binary current = (Binary) myProperty.getTypedValue();
				if (current == null || current.isEmpty()) {
					bb.setVisible(true);
					ll.setVisible(false);

				} else {
					firePropertyEvents(p, "onBinarySelected");
				}
			}
		});
		g.add(ll);
		ColumnLayoutData cld = new ColumnLayoutData();
		cld.setHeight(new Extent(15, Extent.PX));
		cld.setInsets(new Insets(2, 2, 2, 2));
		g.add(bb);
		bb.setLayoutData(cld);
		try {
			bb.addUploadListener(new UploadListener() {

				public void fileUpload(UploadEvent e) {
					Binary bbb = new Binary(e.getInputStream(), false);
					p.setAnyValue(bbb);
					ll.setIcon(new BinaryPropertyImage(p, maxImageHeight, maxImageWidth));
					ll.setText("");
					bb.setVisible(false);
					ll.setVisible(true);

				}

				public void invalidFileUpload(UploadEvent arg0) {

				}
			});
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addPropertyComponent(g);

		currentComponent = g;
	}

	private void createMemoProperty(Property p) {
		int row = memoRowCount == 0 ? 5 : memoRowCount;
		int column = memoColumnCount == 0 ? 80 : memoColumnCount;
		if (alwaysUseLabel) {
			createLabel(p.getValue());
			return;
		}
		final TextArea cb = new TextArea(new StringDocument(), "", column, row);
		// cb.setStyleName("Default");
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(cb.getClass(), "Default");
		cb.setStyle(ss);

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
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(cb.getClass(), "Default");
		cb.setStyle(ss);
		// cb.setStyleName("Default");
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
		tf.setBackground(null);
		tf.setLineWrap(allowLineWrap );
		// tf.setStyleName("Default");
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(tf.getClass(), "Default");
		tf.setStyle(ss);

		tf.setTextAlignment(new Alignment(Alignment.LEADING, Alignment.CENTER));
		GridLayoutData gd = new GridLayoutData();
		gd.setAlignment(new Alignment(Alignment.LEADING, Alignment.CENTER));
		addPropertyComponent(tf, gd);
		currentComponent = tf;
	}

	private void createTextField(Property p) {
		final TipiEchoTextField tf = new TipiEchoTextField(p.getValue());
		// tf.setStyleName("Default");
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(tf.getClass(), "Default");
		tf.setStyle(ss);

		if (value_size != 0) {
			tf.setWidth(new Extent(value_size - 4));
		} else {
			tf.setWidth(PERCENTAGE);
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
				// System.err.println("Capitalization: " + myCapitalization);
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
				// System.err.println("Aap: "+e);
				TipiEchoTextField tf = (TipiEchoTextField) e.getSource();
				if ("upper".equals(myCapitalization)) {
					tf.setText(tf.getText().toUpperCase());
				}
				if ("lower".equals(myCapitalization)) {
					tf.setText(tf.getText().toLowerCase());
				}

				String text = tf.getText();
				myProperty.setValue(text);
				fireTipiEvent("onEnter");
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

		if (allowDateOverlay ) {
			Date d = (Date) p.getTypedValue();
			Calendar cal = Calendar.getInstance();
			if (d != null) {
				cal.setTime(d);
			}
			final DateField df = new DateField(cal);
			df.setDateFormat(sd);
			if (d != null) {
				df.getTextField().setText(sd.format(d));
			} else {
				df.getTextField().setText("");
			}
			if (value_size != 0) {
				df.getTextField().setWidth(new Extent(value_size - 4));
			} else {
				df.getTextField().setWidth(new Extent(97, Extent.PERCENT));
			}
			if (!p.isDirIn()) {
				df.getTextField().setForeground(new Color(90, 90, 90));
			}
			// System.err.println("Created textfield. Dirin: "+p.isDirIn());
			addPropertyComponent(df);

			df.setEnabled(p.isDirIn());
			df.getTextField().setEnabled(p.isDirIn());
			df.addPropertyChangeListener(new PropertyChangeListener() {

				public void propertyChange(PropertyChangeEvent e) {
					System.err.println("Boing:" + e.getPropertyName());
					myProperty.setAnyValue(df.getSelectedDate().getTime());
				}
			});
			currentComponent = df;

		} else {
			final TipiEchoTextField tf = new TipiEchoTextField("");
			Date d = (Date) p.getTypedValue();
			tf.setStyleName("Default");
			addPropertyComponent(tf);
			currentComponent = tf;
			if (d != null) {
				tf.setText(sd.format(d));
			} else {
				tf.setText("");
			}
			if (value_size != 0) {
				tf.setWidth(new Extent(value_size - 4));
			} else {
				tf.setWidth(new Extent(97, Extent.PERCENT));
			}
			Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(tf.getClass(), "Default");
			tf.setStyle(ss);

			tf.getDocument().addDocumentListener(new DocumentListener() {
				public void documentUpdate(DocumentEvent e) {
					// System.err.println("Mies: "+e);
					// System.err.println("Capitalization: " +
					// myCapitalization);
					
					String text = tf.getText();
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

		}

	}

	private void createClocktimeField(final Property p) {
		final TipiEchoTextField tf = new TipiEchoTextField(p.getValue());
		// tf.setStyleName("Default");
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(tf.getClass(), "Default");
		tf.setStyle(ss);

		if (value_size != 0) {
			tf.setWidth(new Extent(value_size));
		} else {
			tf.setWidth(PERCENTAGE);
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

	private void createMultiSelect(final Property p) throws NavajoException {
		final ListBoxEx lb = new ListBoxEx(p.getAllSelections().toArray());
		// lb.setStyleName("Default");
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(lb.getClass(), "Default");
		lb.setStyle(ss);
		lb.setHeight(new Extent(listboxRowCount + 1, Extent.EM));
		addPropertyComponent(lb);
		lb.setEnabled(p.isDirIn());
		if (value_size != 0) {
			lb.setWidth(new Extent(value_size));
		} else {
			lb.setWidth(PERCENTAGE);
		}

		lb.setSelectionMode(ListSelectionModel.MULTIPLE_SELECTION);
		for (int i = 0; i < p.getAllSelections().size(); i++) {
			Selection current = (Selection) p.getAllSelections().get(i);
			lb.setSelectedIndex(i, current.isSelected());
		}
		lb.getSelectionModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ce) {
				try {
					for (int i = 0; i < p.getAllSelections().size(); i++) {
						Selection current = (Selection) p.getAllSelections().get(i);
						current.setSelected(lb.isSelectedIndex(i));
						// .setSelectedIndex(i, current.isSelected());
					}
				} catch (NavajoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				fireTipiEvent("onValueChanged");
			}
		});
		currentComponent = lb;
	}

	private void createRadioButtons(final Property p) throws NavajoException {
		final Component r = new Column();
		final Map<String,RadioButton> buttons = new HashMap<String,RadioButton>();
		// if (p.isDirIn()) {
		Style ssss = Styles.DEFAULT_STYLE_SHEET.getStyle(r.getClass(), "Default");
		r.setStyle(ssss);

		addPropertyComponent(r);
		ArrayList<Selection> ss = p.getAllSelections();
		for (int i = 0; i < ss.size(); i++) {
			Selection cc = ss.get(i);
			final RadioButton rb = new RadioButton();
			// rb.setStyleName("Default");
			Style sss = Styles.DEFAULT_STYLE_SHEET.getStyle(rb.getClass(), "Default");
			rb.setStyle(sss);

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

	public void setMultiRowCount(int row) {
		listboxRowCount = row;
		if (getProperty() != null) {
			setProperty(getProperty());
		}
	}

	private void createCheckBoxes(final Property p) throws NavajoException {
		final ContainerEx r = new ContainerEx();

		final Map<Selection,CheckBox> buttons = new HashMap<Selection,CheckBox>();
		addPropertyComponent(r);
		ArrayList<Selection> ss = p.getAllSelections();
		for (int i = 0; i < ss.size(); i++) {
			Selection cc = (Selection) ss.get(i);
			final CheckBox rb = new CheckBox();
			rb.setLineWrap(false);
			// rb.setModel(model);
			// rb.setStyleName("Default");
			Style tt = Styles.DEFAULT_STYLE_SHEET.getStyle(rb.getClass(), "Default");
			rb.setStyle(tt);

			buttons.put(cc, rb);
			r.add(rb);
			rb.setText(cc.getName());
			rb.setWidth(new Extent(value_size, Extent.PX));
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

	protected void updateRadioButtonList(RadioButton rb, Map<String,RadioButton> buttons, Property p) throws NavajoException {
		p.clearSelections();
		p.setSelected(rb.getActionCommand());

		// if (!rb.isSelected()) {
		// return;
		// }
		for (Iterator<RadioButton> iter = buttons.values().iterator(); iter.hasNext();) {
			RadioButton element = iter.next();
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

	protected void updateCheckboxButtonList(CheckBox rb, Map<Selection,CheckBox> buttons, Property p) throws NavajoException {
		p.clearSelections();
		for (Iterator<Selection> iter = buttons.keySet().iterator(); iter.hasNext();) {
			Selection sel = iter.next();
			CheckBox element = buttons.get(sel);
			sel.setSelected(element.isSelected());
		}
	}

	private void createComboBox(Property p) throws NavajoException {
		if (p.isDirIn()) {
			final SelectFieldEx lb = new SelectFieldEx(p.getAllSelections().toArray());
			Style ssss = Styles.DEFAULT_STYLE_SHEET.getStyle(lb.getClass(), "Default");
			lb.setStyle(ssss);

			// lb.setStyleName("Default");

			add(lb);
			lb.setEnabled(p.isDirIn());
			if (value_size != 0) {
				setValueSize(value_size);
				// lb.setWidth(new Extent(value_size-4));
			} else {
				// lb.setWidth(new Extent(200,Extent.PX));
			}
			// PropertyImpl ppp = (PropertyImpl) p;
			List<Selection> ss = p.getAllSelections();
			for (int i = 0; i < ss.size(); i++) {
				Selection cc = ss.get(i);
				if (cc.isSelected()) {
					System.err.println("Sett");
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
			} else {
				tf.setWidth(PERCENTAGE);
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

	public Component getTableCellRendererComponent(final Table table, final Object value, final int column, final int row) {
		// try {
		MessageTable myTable = (MessageTable) table;
		boolean isSelected = table.getSelectionModel().isSelectedIndex(row);
		if (isSelected) {
			System.err.println("Setting SELECTED zebra to: " + column + " / " + row);
		}

		if (value instanceof Boolean) {
			final RadioButton rb = new RadioButton();
			rb.setSelected(((Boolean) value).booleanValue());
			rb.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					System.err.println("CHAGGA!");
					((MessageTable) table).setSelectedIndex(row);
				}
			});
			EchoPropertyComponent.setZebra(rb, column, row, false);
			rb.setEnabled(true);
			myTable.mapRowSelection(rb, row);
			return rb;
		}

		showLabel = false;

		final MessageTable messageTable = (MessageTable) table;
		EchoPropertyComponent epc = new EchoPropertyComponent();
		Extent tableColumnwidth = messageTable.getColumnSize(column);
		epc.setAlwaysUseLabel(!messageTable.isColumnEditable(column));

		epc.setUseLabelForReadOnlyProperties(true);
		epc.setLabelVisible(false);
		Property property = (Property) value;
		property.setDirection(Property.DIR_IN);
		epc.setProperty(property);

		setInsets(new Insets(0, 0, 0, 0));
		// epc.setWidth(tableColumnwidth);

		// boolean editable = ((Property) value).isDirIn();
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
		// EchoPropertyComponent.setZebra(this,column, row, isSelected);
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

	public boolean getLabelVisible() {
		return showLabel;
	}

	public void setLabelIndent(int indent) {
		label_indent = indent;
		if (getComponents().length > 1) {
			super.setColumnWidth(0, new Extent(label_indent, Extent.PX));
		}
		if (l != null) {
			l.setWidth(new Extent(label_indent, Extent.PX));
		}
	}

	public int getLabelIndent() {
		return label_indent;

	}

	public void setValueSize(int indent) {
		value_size = indent;
		if (getComponents().length > 1) {
			super.setColumnWidth(1, new Extent(value_size, Extent.PX));
		}
		if (currentComponent != null && currentComponent instanceof Sizeable) {
			((Sizeable) currentComponent).setWidth(new Extent(indent, Extent.PX));
		} else {
			if (currentComponent instanceof TextComponent) {
				TextComponent tc = (TextComponent) currentComponent;
				tc.setWidth(new Extent(indent, Extent.PX));
			}
			if (currentComponent instanceof SelectFieldEx) {
				SelectFieldEx s = (SelectFieldEx) currentComponent;
				s.setWidth(new Extent(indent, Extent.PX));
			}
			if (currentComponent instanceof ListBoxEx) {
				ListBoxEx s = (ListBoxEx) currentComponent;
				s.setWidth(new Extent(indent, Extent.PX));
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
		// System.err.println("In SETBACKGROUND of EcHOPropertyCOmponent:
		// "+arg0.toString());
		super.setBackground(arg0);
		if (currentComponent != null) {
			currentComponent.setBackground(arg0);
		}

	}

	public boolean isUseLabelForReadOnlyProperties() {
		return useLabelForReadOnlyProperties;
	}

	public void setUseLabelForReadOnlyProperties(boolean useLabelForReadOnlyProperties) {
		this.useLabelForReadOnlyProperties = useLabelForReadOnlyProperties;
	}

	public void setAlwaysUseLabel(boolean b) {
		this.alwaysUseLabel = b;
	}

	public void setUseCheckBoxes(boolean b) {
		this.useCheckBoxes = b;
	}

	public void setStyle(Style s) {
		super.setStyle(s);
		if (currentComponent != null) {
			currentComponent.setStyle(s);
		}
		if (l != null) {
			l.setStyle(s);
		}
	}

	public static void setZebra(Component c, int column, int row, boolean selected) {
		if (selected) {
			c.setStyle(Styles.DEFAULT_STYLE_SHEET.getStyle(c.getClass(), "SelectedRow"));
		} else {
			if (row % 2 == 0) {
				Style evenRow = Styles.DEFAULT_STYLE_SHEET.getStyle(c.getClass(), "EvenRow");
				c.setStyle(evenRow);

			} else {
				Style oddRow = Styles.DEFAULT_STYLE_SHEET.getStyle(c.getClass(), "OddRow");
				c.setStyle(oddRow);
			}

		}
	}

	public void setZebra(int column, int row, boolean selected) {

		if (selected) {
			setStyle(Styles.DEFAULT_STYLE_SHEET.getStyle(this.getClass(), "SelectedRow"));
		} else {
			if (row % 2 == 0) {
				Style evenRow = Styles.DEFAULT_STYLE_SHEET.getStyle(this.getClass(), "EvenRow");
				setStyle(evenRow);

			} else {
				Style oddRow = Styles.DEFAULT_STYLE_SHEET.getStyle(this.getClass(), "OddRow");
				setStyle(oddRow);
			}

		}
	}

	public void setPropertyBackground(Color c) {
		setBackground(c);
		Component cn = currentComponent;
		if (c != null) {
			System.err.println("Setting to color: " + c + " ckass: " + cn.getClass());
			cn.setBackground(c);
		} else {
			System.err.println("NULL FOUND!");
		}
		GridLayoutData gg = (GridLayoutData) currentComponent.getLayoutData();
		gg.setBackground(c);
	}

	public Color getPropertyBackground() {
		return getBackground();
	}

	// public void setPropertyBackground(Color c) {
	// setBackground(c);
	// Component cn = (Component) currentComponent;
	// if (c != null) {
	// cn.setBackground(c);
	// }
	// GridLayoutData gg = (GridLayoutData) currentComponent.getLayoutData();
	// gg.setBackground(c);
	// }
	//
	// public Color getPropertyBackground() {
	// return getBackground();
	// }

	public void setSelectiontype(String type) throws NavajoException {
		if ("radio".equals(type)) {
			selectionMode = SELECTIONMODE_RADIO;
		} else if ("checkbox".equals(type)) {
			selectionMode = SELECTIONMODE_CHECKBOXES;
		} else if ("list".equals(type)) {
			selectionMode = SELECTIONMODE_LIST;
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

	public void setPropertyValue(Object object) {
		if (getProperty() == null) {
			return;
		}
		String type = getProperty().getType();

		getProperty().setAnyValue(object);

		getProperty().setType(type);
		setProperty(getProperty());

	}

	public int getMaxImageHeight() {
		return maxImageHeight;
	}

	public void setMaxImageHeight(int maxImageHeight) {
		this.maxImageHeight = maxImageHeight;
	}

	public int getMaxImageWidth() {
		return maxImageWidth;
	}

	public void setMaxImageWidth(int maxImageWidth) {
		this.maxImageWidth = maxImageWidth;
	}

	public void setAllowLineWrap(boolean allowLineWrap) {
		this.allowLineWrap = allowLineWrap;
	}

}
