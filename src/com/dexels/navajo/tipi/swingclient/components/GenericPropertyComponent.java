package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;

import javax.imageio.*;
import javax.imageio.plugins.jpeg.*;
import javax.imageio.stream.*;
import javax.swing.*;

import com.dexels.navajo.tipi.swingclient.components.validation.*;

import java.util.*;
import com.dexels.navajo.document.*;

import java.awt.event.*;
import java.awt.image.*;
import java.beans.*;

import com.dexels.navajo.document.databinding.*;
import com.dexels.navajo.document.types.*;

import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.JTextComponent;

import sun.security.provider.SystemSigner;

import java.io.*;
import java.net.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public class GenericPropertyComponent
    extends JPanel
    implements PropertyValidatable, ChangeMonitoring, Validatable {
  private JComponent currentComponent = null;
  private ConditionErrorParser cep = new ConditionErrorParser();
  private int labelWidth = 0;
  private int valign = SwingConstants.CENTER;
  private int halign = SwingConstants.LEADING;
  int max_img_width = 100;
  int max_img_height = 100;
  private String mySelectionType = null;
  private String myLabelText;
  private boolean addedCustomListeners = false;

//  private int height
  private int propertyWidth = 0;
  private boolean showLabel = true;
  private JLabel myLabel = null;

//  BorderLayout borderLayout = new BorderLayout();
  private Map failedPropertyIdMap = null;
  private ResourceBundle res = null;
  private final ArrayList focusListeners = new ArrayList();
  private int checkboxGroupColumnCount = 0;
  private int memoColumnCount = 0;
  private int memoRowCount = 0;
  private String forcedAlignment = null;

  MultipleSelectionPropertyCheckboxGroup myMultiple = null;
  MultipleSelectionPropertyList myMultipleList = null;
  TextPropertyField myField = null;
  DatePropertyField myDateField = null;
  PropertyCheckBox myCheckBox = null;
  IntegerPropertyField myIntField = null;
  FloatPropertyField myFloatField = null;
  PropertyPasswordField myPasswordField = null;
  ClockTimeField myClockTimeField = null;
  StopwatchTimeField myStopwatchTimeField = null;
  MoneyField myMoneyField = null;
  PercentageField myPercentageField = null;
  PropertyRadioSelection myRadioButtonField = null;
  PropertyTextArea myMemoField = null;
  PropertyHiddenField myHiddenField = null;
  MultipleSelectionPropertyPickList myPickList = null;
  URIPropertyField myURIField = null;
  MultiSelectPropertyBox myMultiSelectBox = null;
  PropertyBox myBox = null;
  JComponent myBinaryLabel = null;

  private boolean verticalScrolls = true;
  private boolean horizontalScrolls = false;
  private InputValidator myInputValidator = null;

//  private ArrayList myListeners = new ArrayList();

//  private int default_label_width = 50;
//  private int default_property_width = 50;
  private boolean hardEnabled = false;
  private boolean myVisibleState = true;
  private boolean myEnableState = true;
  private boolean use_checkbox = false;
  private JComponent currentPropertyComponent = null;
  private String myCapitalization = "off";
  private String myPropertyName = null;
  private int PREVIOUS_SELECTION_INDEX = -1;
  private boolean setPropFlag = false;
  private String vAlign = null;
  private String hAlign = null;
  private boolean isLoading = false;
  private String currentType = "";
  private boolean isLabelSet = false;
  private Property myProperty;
  private boolean isFocusable = false;
  private final ArrayList myPropertyEventListeners = new ArrayList();
  private boolean alwaysUseLabel = false;
  private Object evaluatedValue = null;
  private Dimension myPreferredSize = null;
  private Component currentLabelIndentStrut = null;
  private int forcedTotalWidth = -1;
  private boolean showDatePicker = true;
  private int visibleRowCount = 0;
  private JScrollPane memoFieldScrollPane;
  private Color listSelectionColor = new Color(220,220,255);
  private boolean orderListBySelected = false;

  private final ArrayList myKeyListeners = new ArrayList();
  private String forceFieldAlignment = null;
  private int limitFieldWidth = -1;

  private TypeFormatter formatter = null;
  
  private PropertyChangeListener myPropertyChangeListener = null;
  
  public GenericPropertyComponent() {
    try {
    	if(System.getProperty("com.dexels.navajo.propertyMap")!=null) {
    	      res = ResourceBundle.getBundle(System.getProperty("com.dexels.navajo.propertyMap"));
    	}
//      this.setPreferredSize(new Dimension(4,ComponentConstants.PREFERRED_HEIGHT));
//      setBackground(Color.pink);
    }
    catch (MissingResourceException ex) {
//      System.err.println("No resourcemap found.");
    }
    catch (Exception e) {
//        System.err.println("No access to find propertyMap");
    }
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
 
  }

  public void addPropertyKeyListener(KeyListener kl) {
      myKeyListeners.add(kl);
  }
  
  public void removePropertyKeyListener(KeyListener kl) {
      myKeyListeners.remove(kl);
  }

  public void setForcedAlignment(String align) {
	  forcedAlignment = align;
	  if(currentComponent instanceof PropertyField) {
			  PropertyField pf = (PropertyField)currentComponent;
		  pf.setForcedAlignment(align);
	  } 
	  if(getProperty()!=null) {
		  setProperty(getProperty());
	  }
  }
  
  private void setPropertyKeyListeners(JComponent c) {
      for (int i = 0; i < myKeyListeners.size(); i++) {
        KeyListener kl = (KeyListener)myKeyListeners.get(i);
        c.addKeyListener(kl);
    }
  }

  private void clearPropertyKeyListeners(JComponent currentPropertyComponent) {
      for (int i = 0; i < myKeyListeners.size(); i++) {
          KeyListener kl = (KeyListener)myKeyListeners.get(i);
          currentPropertyComponent.removeKeyListener(kl);
      }
      
  }  
  
  
  public void setMaxImageWidth(int w) {
      max_img_width = w;
  }

  public void setMaxImageHeight(int h) {
      max_img_height = h;
  }

  
  public void addCustomFocusListener(FocusListener l) {
    focusListeners.add(l);
  }

  public void removeCustomFocusListener(FocusListener l) {
    focusListeners.remove(l);
  }
//
//  public void doLayout() {
//    super.doLayout();
//  }
  
  public void setMaxImageDimension(int width, int height) {
      
  }

  public void setLabelVisible(boolean b) {
    if (b) {
      showLabel();
    }
    else {
      hideLabel();
    }
  }

  public void setHardEnabled(boolean b) {
    myEnableState = b;
    hardEnabled = true;
    setEnabled(myEnableState);
  }

  public final Property getProperty() {
    return myProperty;
  }

  protected void printComponent(Graphics g) {
//    Color cc = g.getColor();
//    g.setColor(Color.white);
//    g.fillRect(0,0,getWidth(),getHeight());
//    g.setColor(cc);
//    Color c = getBackground();
//    setBackground(Color.white);
    super.printComponent(g);
//    setBackground(c);
  }

  public void setProperty(final Property p) {
//
	  if ( !SwingUtilities.isEventDispatchThread() ) {
		  Thread.dumpStack();
	  }
	  
	  if(myProperty!=null) {
		  if(myPropertyChangeListener!=null) {
			  myProperty.removePropertyChangeListener(myPropertyChangeListener);
		  }
	  }
	  myPropertyChangeListener = new PropertyChangeListener(){

		public void propertyChange(PropertyChangeEvent evt) {
			System.err.println("======== Property initiated value change");
			firePropertyEvents((Property) evt.getSource(),"onValueChanged",null);
		}};
	  
//	  p.addPropertyDataListener(new PropertyDataListener(){
//
//		public void propertyDataChanged(Property p, String oldValue, String newValue) {
//			// TODO Auto-generated method stub
//			
//		}});
    myProperty = p;
    if (p == null) {
      return;
    }
    String caps = p.getSubType("capitalization");
    if (caps != null) {
      setCapitalization(caps);
    }

    currentType = p.getType();
    setPropFlag = true;
    String description = p.getDescription();
    if (description == null || "".equals(description)) {
      description = p.getName();
    }
    if (showLabel) {
      if (!isLabelSet) {
        setLabel(description);
      }
    }
    else {
      hideLabel();
    }
    constructPropertyComponent(p);
    if (hardEnabled) {
      setEnabled(myEnableState);
    }

    if(currentComponent instanceof PropertyField) {
		  PropertyField pf = (PropertyField)currentComponent;
		  pf.setForcedAlignment(forcedAlignment);
	}
    
    setPropFlag = false;
  }

  public void requestFocus() {
    if (currentComponent != null) {
      currentComponent.requestFocus();
    }

  }

  public boolean requestFocusInWindow() {
    if (currentComponent != null) {
      return currentComponent.requestFocusInWindow();
    }
    return false;
  }

  public final void setPropertyComponent(JComponent c) {
    setPropertyComponent(c, false);
  }

  public final void setPropertyComponent(JComponent c, boolean verticalWeight) {
    if (currentComponent == c) {
      return;
    }
    if (currentComponent != null) {
      remove(currentComponent);
//      System.err.println("Removing component: " + currentComponent.getClass());
    }
    currentComponent = c;
//    add(currentComponent, BorderLayout.CENTER);
    if (verticalWeight) {
      add(currentComponent, new GridBagConstraints(1, 0, 1, 1, 1, 1.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    }
    else {
      add(currentComponent, new GridBagConstraints(1, 0, 1, 1, 1, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    }
    revalidate();
  }
  
  public final void setListSelectionColor(Color c){
	listSelectionColor = c;
  }
  public final void setOrderListBySelected(boolean b){
	orderListBySelected = b;
  }

  public final void setLabel(final String s) {
    myLabelText = s;
    if (myLabel == null) {
      myLabel = new JLabel(s);
//      myLabel.setVerticalAlignment(JLabel.CENTER_ALIGNMENT);
      myLabel.setOpaque(false);
//      add(myLabel, BorderLayout.WEST);
      add(myLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    }
    else {
      myLabel.setText(s);
    }
    if (labelWidth != 0) {
      setLabelIndent(labelWidth);
    }
    myLabel.setHorizontalAlignment(halign);
    myLabel.setVerticalAlignment(valign);
    myLabel.setVisible(showLabel);
    isLabelSet = true;
  }

  public final void showLabel() {
    showLabel = true;
    if (myLabel != null) {
      myLabel.setVisible(showLabel);
    }
  }

  public final void hideLabel() {
    showLabel = false;
    if (myLabel != null) {
      remove(myLabel);
    }
    if (currentLabelIndentStrut != null) {
      remove(currentLabelIndentStrut);
    }
    myLabel = null;
  }

  public final void setVerticalLabelAlignment(final int alignment) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        valign = alignment;
        if (myLabel != null) {
          myLabel.setVerticalAlignment(alignment);
        }
      }
    });
  }

  public final void update() {
    // Bleh
  }

  // created for tipi, used to notify when a property has changed
  public void updatePropertyValue(PropertyChangeEvent e) {
//	   if(e.getNewValue().equals(myProperty.getTypedValue())) {
//		   // ignore
//	   } else {
		   constructPropertyComponent(myProperty);
		   
//	   }
	   
	  
  }
  
  public final void updateProperty() {
    if (PropertyControlled.class.isInstance(currentComponent)) {
      PropertyControlled pc = (PropertyControlled) currentComponent;
      pc.update();
    }
  }

  public void gainFocus() {
    if (PropertyControlled.class.isInstance(currentComponent)) {
      PropertyControlled pc = (PropertyControlled) currentComponent;
      pc.gainFocus();
    }
  }

  public final void setHorizontalLabelAlignment(final int alignment) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        halign = alignment;
        if (myLabel != null) {
          myLabel.setHorizontalAlignment(alignment);
        }
      }
    });
  }

  public final void setCheckBoxLabelPosition(final int alignment) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        if (myCheckBox != null) {
          myCheckBox.setHorizontalTextPosition(alignment);
        }
      }
    });
  }

  public final void checkForConditionErrors(Message msg) {
    if (PropertyControlled.class.isInstance(currentComponent)) {
      PropertyControlled pc = (PropertyControlled) currentComponent;
      if(pc.getProperty()==null) {
    	  System.err.println("Checking error for unloaded property!");
    	  Thread.dumpStack();
    	  try {
			msg.write(System.err);
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
      }
      String myName = pc.getProperty().getName();
      ArrayList errors = cep.getFailures(msg);
      /** @todo ADD NULL POINTER CHECK */failedPropertyIdMap = cep.getFailedPropertyIdMap();
      for (int i = 0; i < errors.size(); i++) {
        final String current = (String) errors.get(i);
        final String id = (String) failedPropertyIdMap.get(current);
        if ( (current.indexOf(myName) > -1)) {
          if (Validatable.class.isInstance(currentComponent)) {
            final Validatable f = (Validatable) currentComponent;
            SwingUtilities.invokeLater(new Runnable() {
              public void run() {
                f.setValidationState(BaseField.INVALID);
                f.setToolTipText(cep.getDescription(current));
                f.addConditionRuleId(id);
              }
            });
          }
          return;
        }
      }
    }
  }

  public final void resetComponentValidationStateByRule(final String id) {
    if (failedPropertyIdMap != null && id != null) {
      Iterator it = failedPropertyIdMap.keySet().iterator();
      final PropertyControlled pc = (PropertyControlled) currentComponent;
      String myName = pc.getProperty().getName();
      while (it.hasNext()) {
        String current = (String) it.next();
        if (current.indexOf(myName) > -1) {
          // I am invalid.
          final String current_id = (String) failedPropertyIdMap.get(current);
          SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              if (id.equals(current_id)) {
                if (Validatable.class.isInstance(currentComponent)) {
                  Validatable f = (Validatable) currentComponent;
                  f.setValidationState(BaseField.VALID);
                  f.setToolTipText(getToolTipText(pc.getProperty()));
                }
                if (IntegerPropertyField.class.isInstance(currentComponent)) { // Mmmm.. shouldn't be like this I guess,..
                  IntegerPropertyField f = (IntegerPropertyField) currentComponent;
                  f.setValidationState(BaseField.VALID);
                  f.setToolTipText(getToolTipText(pc.getProperty()));
                }
              }
            }
          });
        }
      }
    }
  }

  public final String getToolTipText(Property p) {
    String toolTip = "";
    if (p != null) {
      try {
        if (res != null) {
          toolTip = res.getString(p.getName());
          return toolTip;
        }
        else {
          toolTip = "unknown";
        }
      }
      catch (MissingResourceException e) {
        toolTip = p.getDescription();
        if (toolTip != null && !toolTip.equals("")) {
          return toolTip;
        }
        else {
          toolTip = p.getName();
          return toolTip;
        }
      }
    }
    else {
      toolTip = "unknown";
    }
    return toolTip;
  }

  public void setLabelIndent(final int lindent) {
    labelWidth = lindent;
    if (myLabel == null) {
      return;
    }
    if (currentLabelIndentStrut != null) {
      remove(currentLabelIndentStrut);
      currentLabelIndentStrut = null;
    }

    currentLabelIndentStrut = Box.createHorizontalStrut(lindent);
    add(currentLabelIndentStrut, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    //    int height = getPreferredSize().height;

    myLabel.setPreferredSize(new Dimension(lindent, ComponentConstants.PREFERRED_HEIGHT));
//    invalidate();
//    myLabel.invalidate();
  }

  public void setForcedTotalWidth(int width){
    forcedTotalWidth = width;
  }

  public int getLabelIndent() {
    return labelWidth;
  }

  public final boolean isLabelVisible() {
    if (myLabel != null) {
      return myLabel.isVisible();
    }
    return false;
  }

  private final void jbInit() throws Exception {
    this.setLayout(new GridBagLayout());
    setOpaque(false);
  }

  public void constructPropertyComponent(Property p) {
	  String type = p.getType();
	    if (type.equals(Property.EXPRESSION_PROPERTY)) {
	      try {
	        type = p.getEvaluatedType();
	        evaluatedValue = p.getTypedValue();
//	        System.err.println("Found evaluated type: " + type+ " vAL: "+evaluatedValue );
	      }
	      catch (NavajoException ex) {
	        ex.printStackTrace();
	      }
	    }
	  
    if ("true".equals(p.getSubType("hidden"))) {
      createPropertyHiddenField(p);
      return;
    }
    if(type==null) {
    	type = "string";
    }
    if (type.equals(Property.SELECTION_PROPERTY)) {
      if (!"+".equals(p.getCardinality())) {
        if ("radio".equals(mySelectionType)) {
          createRadioButtonPropertyField(p);
        }
        else if ("list".equals(mySelectionType)) {
        	createPropertyList(p);
          }
        else {
          createPropertyBox(p);
        }
        return;
      }
      else {
        if ("dropdown".equals(mySelectionType)) {
          createMultiSelectPropertyBox(p);
        }
        else if ("checkbox".equals(mySelectionType)) {
          createPropertyCheckboxList(p);
        }
        else if ("picklist".equals(mySelectionType)) {
          createPickList(p);
        }
        else {
          createPropertyList(p);

        }
        // if radio is passed, it will use checkboxes when cardinality = +
        // Not really pretty, but checkboxes are more similar to radiobuttons
        // than a list. This construction is used for the Questions in forms.
        // All questions have this hint.
        if ("radio".equals(mySelectionType)) {
          createPropertyCheckboxList(p);
        }

        return;
      }
    }
   

    if (type.equals(Property.BOOLEAN_PROPERTY)) {
      createPropertyCheckbox(p);
      return;
    }
    if (type.equals(Property.DATE_PROPERTY)) {
      createPropertyDateField(p);
      return;
    }
    if (type.equals(Property.INTEGER_PROPERTY)) {
      createIntegerField(p);
      return;
    }
    if (type.equals(Property.LONG_PROPERTY)) {
    	System.err.println("Creating long field! ");
        createLongField(p);
        return;
      }
    if (type.equals(Property.BINARY_PROPERTY)) {
      createBinaryComponent(p);
      return;
    }
    if (type.equals(Property.PASSWORD_PROPERTY)) {
      createPropertyPasswordField(p);
      return;
    }
    if (type.equals(Property.FLOAT_PROPERTY)) {
      createPropertyFloatField(p);
      return;
    }
    if (type.equals(Property.MONEY_PROPERTY)) {
      createMoneyPropertyField(p);
      return;
    }
    if (type.equals(Property.PERCENTAGE_PROPERTY)) {
      createPercentagePropertyField(p);
      return;
    }
    if (type.equals(Property.CLOCKTIME_PROPERTY)) {
      createClockTimeField(p);
      return;
    }
    if (type.equals(Property.STOPWATCHTIME_PROPERTY)) {
      createStopwatchTimeField(p);
      return;
    }
    if (type.equals(Property.MEMO_PROPERTY)) {
      createMemoField(p);
      return;
    }
    if (type.equals("string") && "true".equals(p.getSubType("uri"))) {
      createURIPropertyField(p);
      return;
    }
    if (type.equals("string") && "true".equals(p.getSubType("password"))) {
        createPropertyPasswordField(p);
        return;
      }
    if(type.equals(Property.TIPI_PROPERTY)) {
    	if(getFormatter()!=null) {
    		createTipiField(p);
    		  return;
    	}
    }
    
    createPropertyField(p);
    return;
  }

//
//  private final void constructPropertyComponent(Property p) {
//    if (p.getType().equals("selection")) {
//      if (!"+".equals(p.getCardinality())) {
//        if (use_checkbox) {
//        createRadioButtonPropertyField(p);
//      }
//      else {
//        createPropertyList(p);
//      }
//        return;
//      }
//      else {
//        if (use_checkbox) {
//          createPropertyCheckboxList(p);
//        }
//        else {
//          createPropertyList(p);
//        }
//        return;
//      }
//    }
//    String type = p.getType();
//    if (type.equals(Property.EXPRESSION_PROPERTY)) {
//      try {
//        type = p.getEvaluatedType();
//        System.err.println("Found evaluated type: "+type);
//        evaluatedValue = p.getTypedValue();
//     }
//      catch (NavajoException ex) {
//        ex.printStackTrace();
//      }
//    }
//
//    if (type.equals("boolean")) {
//      createPropertyCheckbox(p);
//      return;
//    }
//    if (type.equals("date")) {
//      createPropertyDateField(p);
//      return;
//    }
//    if (type.equals("integer")) {
//      createIntegerField(p);
//      return;
//    }
//    if (type.equals("binary")) {
//      createBinaryComponent(p);
//      return;
//    }
//    if (type.equals("password")) {
//      createPropertyPasswordField(p);
//      return;
//    }
//    if (type.equals("float")) {
//      createPropertyFloatField(p);
//      return;
//    }
//    if (type.equals("money")) {
//      createMoneyPropertyField(p);
//      return;
//    }
//    if (type.equals("percentage")) {
//      createPercentagePropertyField(p);
//      return;
//    }
//    if (type.equals("clocktime")) {
//      createClockTimeField(p);
//      return;
//    }
//    if (type.equals(Property.MEMO_PROPERTY)) {
//      createMemoField(p);
//      return;
//    }
//    createPropertyField(p);
//    return;
//  }



//  private final void createBinaryComponent(Property p) {
//    //Test case image..
//    /** @todo Shouldnt  the old component be removed first? */
//    Binary b = (Binary)p.getTypedValue();
//    if (b==null) {
//      myBinaryLabel = new BaseLabel();
//      ((BaseLabel)myBinaryLabel).setText("Null binary property");
//      addPropertyComponent(myBinaryLabel);
//      return;
//    }
//    byte[] data = b.getData();
//    String mime = b.getMimeType();
//    if (mime.indexOf("image")!=-1) {
//      ImageIcon img = new ImageIcon(data);
//      myBinaryLabel = new BaseLabel();
//      ((BaseLabel)myBinaryLabel).setIcon(img);
//      addPropertyComponent(myBinaryLabel);
//      return;
//    }
//    if (mime.indexOf("text")!=-1) {
//      myBinaryLabel = new JTextArea();
//      ((JTextArea)myBinaryLabel).setText(new String(data));
//      addPropertyComponent(myBinaryLabel);
//      return;
//    }
//    ImageIcon img = new ImageIcon(data);
//    myBinaryLabel = new BaseLabel();
//    ((BaseLabel)myBinaryLabel).setText("Unknown binary property. Mimetype: "+mime+" size: "+data.length);
//    addPropertyComponent(myBinaryLabel);
//    return;
//  }


  private void createTipiField(Property p) {
		String result = getFormatter().formatObject(p.getTypedValue(),p.getTypedValue().getClass());
		JLabel lh = new JLabel(result);
		addPropertyComponent(lh);
		   }

private final void createBinaryComponent(final Property p) {
    //Test case image..
    /** @todo Shouldnt  the old component be removed first? */
//    if (p.getLength() > 0) {
//      max_img_size = p.getLength();
//    }
//    System.err.println("Length: "+p.getLength());
    Binary b = (Binary) p.getTypedValue();
    if (b == null || b.getLength()<=0) {
//        System.err.println("Null-binary found!");
        myBinaryLabel = new JButton();
      ( (JButton) myBinaryLabel).addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            JFileChooser jf = new JFileChooser();
            jf.showOpenDialog(myBinaryLabel);
            File f = jf.getSelectedFile();
            if (f != null) {
              Binary b = new Binary(f);
                p.setAnyValue(b);
              setProperty(p);
            }
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      });
      myBinaryLabel.setEnabled(p.isDirIn());
      ( (JButton) myBinaryLabel).setText("<html>-</html>"); addPropertyComponent(myBinaryLabel, true);
      myBinaryLabel.setToolTipText(p.getDescription());
      return;
    }
 //   System.err.println("Getting binary data!");
//    byte[] data = b.getData();
    String mime = b.guessContentType();
    if (mime.indexOf("image") != -1) {
        InputStream inp = b.getDataAsStream(); 
        BufferedImage mm;
        try {
            mm = ImageIO.read(inp);
            //ImageIcon img = new ImageIcon(mm);
            System.err.println("WIDTH: "+max_img_width+" height: "+max_img_height);
            myBinaryLabel = new BaseLabel();
            ( (BaseLabel) myBinaryLabel).setHorizontalAlignment(JLabel.CENTER); 
            ( (BaseLabel) myBinaryLabel).setVerticalAlignment(JLabel.CENTER); 
            ( (BaseLabel) myBinaryLabel).setIcon(getScaled(mm,max_img_width,max_img_height)); 
//            ( (BaseLabel) myBinaryLabel).setIcon(img);
            addPropertyComponent(myBinaryLabel);
       } catch (IOException e) {
            e.printStackTrace();
        }
       return;
    }
    if (mime.indexOf("text") != -1) {
      myBinaryLabel = new JTextArea();
      ( (JTextArea) myBinaryLabel).setText(new String(b.getData())); 
      addPropertyComponent(myBinaryLabel);
      return;
    }

    /**
     * I really dont know what this section does...
     */
/*
    ImageIcon img = new ImageIcon(data);
    myBinaryLabel = new BaseLabel();
    ( (BaseLabel) myBinaryLabel).setHorizontalAlignment(JLabel.CENTER);
    ( (BaseLabel) myBinaryLabel).setVerticalAlignment(JLabel.CENTER); 
    if (img != null) {
       ( (BaseLabel) myBinaryLabel).setIcon(getScaled(img));
    }
    else {
       ( (BaseLabel) myBinaryLabel).setText("Unknown binary property. Mimetype: " + mime + " size: " + data.length);
    }
    addPropertyComponent(myBinaryLabel);
    myBinaryLabel.setToolTipText(p.getDescription());
    return;
*/
    }

//  private final void createBinaryComponent(Property p) {
//    //Test case image..
//    if(p.getLength() > 0){
//      max_img_size = p.getLength();
//    }
//    byte[] data = (byte[]) p.getTypedValue();
//    ImageIcon img = new ImageIcon(data);
//    myBinaryLabel = new BaseLabel();
//    myBinaryLabel.setIcon(getScaled(img));
//    myBinaryLabel.setVerticalAlignment(JLabel.CENTER);
//    myBinaryLabel.setHorizontalAlignment(JLabel.CENTER);
//    addPropertyComponent(myBinaryLabel);
//  }

	public static ImageIcon scale(ImageInputStream infile, ImageOutputStream outfile, int width, int height, boolean keepAspect, float quality)
			throws IOException {

		BufferedImage original = ImageIO.read(infile);
		if (original == null) {
			throw new IOException("Unsupported file format!");
		}

			BufferedImage scaled = scale(width, height, keepAspect, original);
			return new ImageIcon(scaled);

	}

public static BufferedImage scale(int width, int height, boolean keepAspect, BufferedImage original) {
	int originalWidth = original.getWidth();
	int originalHeight = original.getHeight();
	System.err.println("ORIGW: "+originalWidth+" origw: "+originalHeight);
	if (width > originalWidth) {
		width = originalWidth;
	}
	if (height > originalHeight) {
		height = originalHeight;
	}
	
	
	float factorX = (float)originalWidth / width;
	float factorY = (float)originalHeight / height;
	if(keepAspect) {
		factorX = Math.max(factorX, factorY);
		factorY = factorX;
	}
	
	// The scaling will be nice smooth with this filter
	AreaAveragingScaleFilter scaleFilter =
		new AreaAveragingScaleFilter(Math.round(originalWidth / factorX),
				Math.round(originalHeight / factorY));
	ImageProducer producer = new FilteredImageSource(original.getSource(),
			scaleFilter);
	ImageGenerator generator = new ImageGenerator();
	producer.startProduction(generator);
	BufferedImage scaled = generator.getImage();
	return scaled;
}


  private final ImageIcon getScaled(BufferedImage icon, int maxWidth, int maxHeight) {
	 BufferedImage bi = scale(maxWidth, maxHeight, false, icon);
    if (icon == null) {
      return null;
    }
    return new ImageIcon(bi);
    
   
  }

  public void setComponentBackground(Color c) {
    if (currentPropertyComponent != null) {
      currentPropertyComponent.setBackground(c);
    }
  }

  protected final void addPropertyComponent(JComponent c) {
    addPropertyComponent(c, false);
  }

  protected final void addPropertyComponent(JComponent c, boolean verticalWeight) {
      if (currentPropertyComponent!=null) {
        clearPropertyKeyListeners(currentPropertyComponent);
    }
      setPropertyKeyListeners(c);
    setPropertyComponent(c, verticalWeight);
    currentPropertyComponent = c;

    if (!addedCustomListeners) {
      c.addFocusListener(new FocusAdapter() {
        public void focusGained(FocusEvent e) {
          for (int i = 0; i < focusListeners.size(); i++) {
            FocusListener fl = (FocusListener) focusListeners.get(i);
            fl.focusGained(e);
          }
        }

        public void focusLost(FocusEvent e) {
          for (int i = 0; i < focusListeners.size(); i++) {
            FocusListener fl = (FocusListener) focusListeners.get(i);
            fl.focusLost(e);
          }
        }
      });
      addedCustomListeners = true;
//      System.err.println("Component class: "+c.getClass());
      if (c instanceof JTextField) {
    	  JTextField jj = (JTextField)c;
//    	  System.err.println("Text component found. forceAlignment: "+forceFieldAlignment+" limit: "+limitFieldWidth);
    	  if ("left".equals(forceFieldAlignment)) {
        	  jj.setHorizontalAlignment(SwingConstants.LEFT);
    	  }
    	  if ("right".equals(forceFieldAlignment)) {
        	  jj.setHorizontalAlignment(SwingConstants.RIGHT);
    	  }
    	  if ("center".equals(forceFieldAlignment)) {
        	  jj.setHorizontalAlignment(SwingConstants.CENTER);
    	  }
    	  if (limitFieldWidth>0) {
    		  System.err.println("Limiting field size to: "+limitFieldWidth);
//    		  jj.setSize(new Dimension(limitFieldWidth, jj.getPreferredSize().height));
    		  jj.setColumns(limitFieldWidth);
    		  
    		  jj.setBackground(new Color(255,0,0));
    		  this.doLayout();
    	  }
		}
      }
    

    if (currentPropertyComponent != null && myProperty != null && !isFocusable) {
      currentPropertyComponent.setFocusable(myProperty.isDirIn());
    }
  }




public void setFocusable(boolean b) {
    isFocusable = b;
    setRequestFocusEnabled(b);
  }

  public final boolean getFocusable() {
    return isFocusable;
  }

  private final void createPropertyBox(Property p) {
    if (myBox == null) {
      myBox = new PropertyBox();
//      myBox.addActionListener(new java.awt.event.ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//          myBox_actionPerformed(e);
//        }
//      });
      myBox.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myBox_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myBox_focusLost(e);
        }
      });
      myBox.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          myBox_itemStateChanged(e);
        }
      });
    }
    addPropertyComponent(myBox);
    myBox.loadProperty(p);
  }

  private final void createPropertyList(Property p) {
    if (myMultipleList == null) {
      myMultipleList = new MultipleSelectionPropertyList();
      myMultipleList.addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent e) {
            myMultipleList_valueChanged(e);
          }

        });    }
    if (visibleRowCount != 0) {
      myMultipleList.setVisibleRowCount(visibleRowCount);
    }
    myMultipleList.setVerticalScrolls(verticalScrolls);
    myMultipleList.setHorizontalScrolls(horizontalScrolls);


	myMultipleList.setSelectedColor(listSelectionColor);
	myMultipleList.setOrderBySelected(orderListBySelected);
    addPropertyComponent(myMultipleList, true);
    myMultipleList.setProperty(p);
  }
	
  public int getVisibleRowCount() {
		return visibleRowCount;
	}

  private final void createPropertyCheckboxList(Property p) {
    if (myMultiple == null) {
      myMultiple = new MultipleSelectionPropertyCheckboxGroup();
      myMultiple.addCheckboxListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          fireTipiEvent("onValueChanged");
        }
      });

    }
    myMultiple.setVerticalScrolls(verticalScrolls);
    myMultiple.setHorizontalScrolls(horizontalScrolls);
    if (checkboxGroupColumnCount > 0) {
      myMultiple.setColumnMode(true);
      myMultiple.setColumns(checkboxGroupColumnCount);
    }
    else {
      myMultiple.setColumnMode(false);
    }
    addPropertyComponent(myMultiple);
    myMultiple.setProperty(p);
  }

  public void setCheckboxGroupColumnCount(int count) {
    checkboxGroupColumnCount = count;
    if (getProperty() != null) {
      setProperty(getProperty());
    }
  }

  private final void createMultiSelectPropertyBox(Property p) {
    if (myMultiSelectBox == null) {
      myMultiSelectBox = new MultiSelectPropertyBox();
      myMultiSelectBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myMultiSelectBox_actionPerformed(e);
        }
      });
      myMultiSelectBox.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myBox_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myBox_focusLost(e);
        }
      });
      myMultiSelectBox.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          myBox_itemStateChanged(e);
        }
      });
    }
    addPropertyComponent(myMultiSelectBox, true);
    myMultiSelectBox.loadProperty(p);
  }

  private final void createPickList(Property p) {
    if (myPickList == null) {
      myPickList = new MultipleSelectionPropertyPickList();
    }
//    myPickList.setVerticalScrolls(verticalScrolls);
//    myPickList.setHorizontalScrolls(horizontalScrolls);
//	System.err.println("PICKLIST: "+visibleRowCount);
     addPropertyComponent(myPickList, true);
    myPickList.setProperty(p);
    if (visibleRowCount>0) {
        myPickList.setVisibleRowCount(visibleRowCount);
    }
  }

  private final void createPropertyCheckbox(Property p) {
    if (myCheckBox == null) {
      myCheckBox = new PropertyCheckBox();
      addPropertyComponent(myCheckBox);
      myCheckBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myCheckBox_actionPerformed(e);
        }
      });
      myCheckBox.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myCheckBox_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myCheckBox_focusLost(e);
        }
      });
      myCheckBox.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          myCheckBox_itemStateChanged(e);
        }
      });
    }
    myCheckBox.setProperty(p);
    if (!showLabel) {
      myCheckBox.setText(myLabelText);
    }
  }

  private final void createIntegerField(Property p) {
//	  System.err.println("Create int field: "+p.getValue()+" type: "+p.getType()+"aaa: "+p.getTypedValue().getClass());
//	  Thread.dumpStack();
    if (myIntField == null) {
      myIntField = new IntegerPropertyField();
      myIntField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myField_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myField_focusLost(e);
        }
      });
      myIntField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myField_actionPerformed(e);
        }
      });
    }
    addPropertyComponent(myIntField);
    myIntField.setProperty(p);
  }
  private final void createLongField(Property p) {
	    if (myIntField == null) {
	      myIntField = new IntegerPropertyField(true);
	      myIntField.addFocusListener(new java.awt.event.FocusAdapter() {
	        public void focusGained(FocusEvent e) {
	          myField_focusGained(e);
	        }

	        public void focusLost(FocusEvent e) {
	          myField_focusLost(e);
	        }
	      });
	      myIntField.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          myField_actionPerformed(e);
	        }
	      });
	    }
	    myIntField.setLongMode(true);
	    addPropertyComponent(myIntField);
	    myIntField.setProperty(p);
	  }
  
  
  private final void createPropertyFloatField(Property p) {
    if (myFloatField == null) {
      myFloatField = new FloatPropertyField();
      myFloatField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myField_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myField_focusLost(e);
        }
      });
      myFloatField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myField_actionPerformed(e);
        }
      });
    }
    addPropertyComponent(myFloatField);
    myFloatField.setProperty(p);
  }

  private final void createPropertyDateField(Property p) {
    if (myDateField == null) {
      myDateField = new DatePropertyField();
      myDateField.setShowCalendarPickerButton(showDatePicker);
      myDateField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myDateField_actionPerformed(e);
        }
      });
      myDateField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myDateField_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myDateField_focusLost(e);
        }
      });
    }
    addPropertyComponent(myDateField);
    myDateField.setProperty(p);
  }

  private final void createPropertyField(Property p) {
    if (myField == null) {
      myField = new TextPropertyField();

      myField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myField_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myField_focusLost(e);
        }
      });
      myField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myField_actionPerformed(e);
        }
      });
      myField.setCapitalizationMode(myCapitalization);
    }
    myField.setInputValidator(myInputValidator);
    myField.setProperty(p);
    myField.setCaretPosition(0);
    if (alwaysUseLabel) {
      myField.setBorder(BorderFactory.createCompoundBorder());
//        myField.setBorder(null);
    }
    addPropertyComponent(myField);
  }

  private final void createURIPropertyField(Property p) {
    if (myURIField == null) {
      myURIField = new URIPropertyField();

      myURIField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myURIField_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myURIField_focusLost(e);
        }
      });
      myURIField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myURIField_actionPerformed(e);
        }
      });
      myURIField.setCapitalizationMode(myCapitalization);
    }
    myURIField.setInputValidator(myInputValidator);
    myURIField.setProperty(p);
    addPropertyComponent(myURIField);
  }

  private final void createPropertyPasswordField(Property p) {
    if (myPasswordField == null) {
      myPasswordField = new PropertyPasswordField();
      myPasswordField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myPasswordField_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myPasswordField_focusLost(e);
        }
      });
      myPasswordField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myPasswordField_actionPerformed(e);
        }
      });
    }
    myPasswordField.setInputValidator(myInputValidator);
    myPasswordField.setProperty(p);
    addPropertyComponent(myPasswordField);
  }

  private final void createPropertyHiddenField(Property p) {
    if (myHiddenField == null) {
      myHiddenField = new PropertyHiddenField();
      myHiddenField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myPasswordField_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myPasswordField_focusLost(e);
        }
      });
      myHiddenField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myPasswordField_actionPerformed(e);
        }
      });
    }
    myHiddenField.setInputValidator(myInputValidator);
    myHiddenField.setProperty(p);
    addPropertyComponent(myHiddenField);
  }

  private final void createMoneyPropertyField(Property p) {
    if (myMoneyField == null) {
      myMoneyField = new MoneyField();
      myMoneyField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myMoneyField_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myMoneyField_focusLost(e);
        }
      });
      myMoneyField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myMoneyField_actionPerformed(e);
        }
      });
    }
    myMoneyField.setProperty(p);
    addPropertyComponent(myMoneyField);
  }

  private final void createPercentagePropertyField(Property p) {
    if (myPercentageField == null) {
      myPercentageField = new PercentageField();
//      myPercentageField.addFocusListener(new java.awt.event.FocusAdapter() {
//        public final void focusGained(FocusEvent e) {
//          myPercentageField_focusGained(e);
//        }
//
//        public final void focusLost(FocusEvent e) {
//          myPercentageField_focusLost(e);
//        }
//      });
//      myPercentageField.addActionListener(new java.awt.event.ActionListener() {
//        public final void actionPerformed(ActionEvent e) {
//          myPercentageField_actionPerformed(e);
//        }
//      });
    }
    myPercentageField.setProperty(p);
    addPropertyComponent(myPercentageField);
  }

  private final void createRadioButtonPropertyField(Property p) {
    if (myRadioButtonField == null) {
      myRadioButtonField = new PropertyRadioSelection();
      myRadioButtonField.addFocusListener(new java.awt.event.FocusAdapter() {
        public final void focusGained(FocusEvent e) {
          myRadioButtonField_focusGained(e);
        }

        public final void focusLost(FocusEvent e) {
          myRadioButtonField_focusLost(e);
        }
      });
      myRadioButtonField.addActionListener(new java.awt.event.ActionListener() {
        public final void actionPerformed(ActionEvent e) {
          myRadioButtonField_actionPerformed(e);
        }
      });
//      myRadioButtonField.add1  myRadioButtonField_itemStateChanged
    }
    myRadioButtonField.setProperty(p);
    addPropertyComponent(myRadioButtonField, true);
  }

  private final void createClockTimeField(Property p) {
    if (myClockTimeField == null) {
      myClockTimeField = new ClockTimeField();
      myClockTimeField.addFocusListener(new java.awt.event.FocusAdapter() {
        public final void focusGained(FocusEvent e) {
          myClockTimeField_focusGained(e);
        }

        public final void focusLost(FocusEvent e) {
          myClockTimeField_focusLost(e);
        }
      });
      myClockTimeField.addActionListener(new java.awt.event.ActionListener() {
        public final void actionPerformed(ActionEvent e) {
          myClockTimeField_actionPerformed(e);
        }
      });
    }
    if ("true".equals(p.getSubType("showseconds"))) {
      myClockTimeField.showSeconds(true);
    }
    else {
      myClockTimeField.showSeconds(false);
    }
    myClockTimeField.setProperty(p);
    addPropertyComponent(myClockTimeField);
  }

  private final void createStopwatchTimeField(Property p) {
    if (myStopwatchTimeField == null) {
      myStopwatchTimeField = new StopwatchTimeField();
      myStopwatchTimeField.addFocusListener(new java.awt.event.FocusAdapter() {
        public final void focusGained(FocusEvent e) {
          myStopwatchTimeField_focusGained(e);
        }

        public final void focusLost(FocusEvent e) {
          myStopwatchTimeField_focusLost(e);
        }
      });
      myStopwatchTimeField.addActionListener(new java.awt.event.ActionListener() {
        public final void actionPerformed(ActionEvent e) {
          myStopwatchTimeField_actionPerformed(e);
        }
      });
    }
    myStopwatchTimeField.setProperty(p);
    addPropertyComponent(myStopwatchTimeField);
  }

  private final void createMemoField(final Property p) {
    if (myMemoField == null) {
      myMemoField = new PropertyTextArea();
      myMemoField.addFocusListener(new java.awt.event.FocusAdapter() {
        public final void focusGained(FocusEvent e) {
          fireTipiEvent("onFocusGained");
        }

        public final void focusLost(FocusEvent e) {
          fireTipiEvent("onFocusLost");
          p.setValue(myMemoField.getText());
        }
      });
      //      myMemoField.addActionListener(new java.awt.event.ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//          myClockTimeField_actionPerformed(e);
//        }
//      });
    }

    if (memoColumnCount != 0) {
      myMemoField.setColumns(memoColumnCount);
    }
    if (memoRowCount != 0) {
      myMemoField.setRows(memoRowCount);
    }
    else {
      myMemoField.setRows(8);
    }
    if (!p.isDirIn()) {
      myMemoField.setBackground(Color.lightGray);
    }
    myMemoField.setLineWrap(true);
    myMemoField.setWrapStyleWord(true);
//    myMemoField.setMinimumSize(new Dimension(100,16));
    // TODO I don't like this. Memo field should take care of its own scrolling
    // It would require a refactor of the v2 which I don't want to do. Never mind.

    memoFieldScrollPane = new JScrollPane() {
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }
    };

    memoFieldScrollPane.getViewport().add(myMemoField);
    memoFieldScrollPane.setHorizontalScrollBarPolicy(horizontalScrolls ? JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED : JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    memoFieldScrollPane.setVerticalScrollBarPolicy(verticalScrolls ? JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED : JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    myMemoField.setProperty(p);
        addPropertyComponent(memoFieldScrollPane, true);
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

  final void myMultiSelectBox_actionPerformed(ActionEvent e) {
    if (!setPropFlag) {
      fireTipiEvent("onActionPerformed");
    }
  }

  final void myBox_actionPerformed(ActionEvent e) {
    if (!setPropFlag) {
      if (e.getActionCommand().equals("comboBoxChanged")) {
        fireTipiEvent("onValueChanged");
        PREVIOUS_SELECTION_INDEX = myBox.getSelectedIndex();
      }
      else {
        fireTipiEvent("onActionPerformed");
      }
    }
  }

  final void myBox_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  final void myBox_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");
  }

  final void myMultipleList_valueChanged(ListSelectionEvent e) {
	  if (!e.getValueIsAdjusting()) {
		  fireTipiEvent("onValueChanged");
	}
  }

  final void myBox_itemStateChanged(ItemEvent e) {
    if (!setPropFlag) {
      fireTipiEvent("onStateChanged");
    }
  }

  final void myRadioButtonField_actionPerformed(ActionEvent e) {
//    System.err.println("RadioButton. Event: " + e.getActionCommand());
    if (!setPropFlag) {
//      if (e.getActionCommand().equals("comboBoxChanged")) {
      fireTipiEvent("onValueChanged");
      PREVIOUS_SELECTION_INDEX = myRadioButtonField.getSelectedIndex();
    }
    else {
      fireTipiEvent("onActionPerformed");
    }
//    }
  } 

  final void myRadioButtonField_itemStateChanged(ItemEvent e) {
    if (!setPropFlag) {
      fireTipiEvent("onStateChanged");
    }
  }

  final void myRadioButtonField_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  final void myRadioButtonField_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");
  }

//  final void myRadioButtonField_itemStateChanged(ItemEvent e) {
//    if (!setPropFlag) {
//      fireTipiEvent("onStateChanged");
//    }
//  }

  final void myField_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  final void myField_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");
  }

  final void myField_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");
  }

  final void myURIField_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  final void myURIField_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");
  }

  final void myURIField_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");
  }

  final void myPasswordField_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  final void myPasswordField_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");
  }

  final void myPasswordField_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");
  }

  final void myDateField_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");
  }

  final void myDateField_focusGained(FocusEvent e) {
//      System.err.println(" IN myDateField_focusGained ");
    fireTipiEvent("onFocusGained");
  }

  final void myDateField_focusLost(FocusEvent e) {
//      System.err.println(" IN myDateField_focusLOST ");
    fireTipiEvent("onFocusLost");
  }

  final void myCheckBox_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");
  }

  final void myCheckBox_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  final void myCheckBox_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");
  }

  final void myCheckBox_itemStateChanged(ItemEvent e) {
    fireTipiEvent("onStateChanged");
  }

  final void myClockTimeField_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  final void myClockTimeField_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");
  }

  final void myClockTimeField_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");
  }

  final void myStopwatchTimeField_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  final void myStopwatchTimeField_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");
  }

  final void myStopwatchTimeField_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");
  }

  final void myMoneyField_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  final void myMoneyField_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");
  }

  final void myMoneyField_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");
  }

  final void myPercentageField_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  final void myPercentageField_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");
  }

  final void myPercentageField_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");
  }

  protected final void fireTipiEvent(String type) {
    firePropertyEvents(myProperty, type, null);
  }

  public void setEnabled(boolean value) {
    if (myProperty != null) {
      if (myProperty.getType().equals("selection") && !"+".equals(myProperty.getCardinality())) {
        if (use_checkbox) {
          if (myRadioButtonField != null) {
            myRadioButtonField.setFocusable(value);
            myRadioButtonField.setEnabled(value); ;
          }
        }
        else {
          if (myBox != null) {
            myBox.setEnabled(value);
            myBox.setFocusable(value);
          }
        }
        return;
      }
      if (myProperty.getType().equals("selection") && "+".equals(myProperty.getCardinality())) {
        if (use_checkbox) {
          if (myMultiple != null) {
            myMultiple.setFocusable(value);
            myMultiple.setEnabled(value); ;
          }
        }
        else {
          if (myMultipleList != null) {
            myMultipleList.setFocusable(value);
            myMultipleList.setEnabled(value);
          }
        }
        return;
      }
      if (myProperty.getType().equals("boolean")) {
        if (myCheckBox != null) {
          myCheckBox.setFocusable(value);
          myCheckBox.setEnabled(value);
        }
        return;
      }
      if (myProperty.getType().equals("date")) {
        if (myDateField != null) {
          myDateField.setFocusable(value);
          myDateField.setEnabled(value);
          myDateField.setEditable(value);
        }
        return;
      }
      
      if (myProperty.getType().equals("string") && "true".equals(myProperty.getSubType("uri"))) {
    	   if (myURIField != null) {
	    		   myURIField.setFocusable(value);
	    		   myURIField.setEnabled(value);
	    		   myURIField.setEditable(value);
    	        }
    	        return;
     }      
      
      if (myProperty.getType().equals("integer")) {
        if (myIntField != null) {
          myIntField.setFocusable(value);
          myIntField.setEnabled(value);
          myIntField.setEditable(value);
        }
        return;
      }
      if (myProperty.getType().equals("float")) {
        if (myFloatField != null) {
          myFloatField.setFocusable(value);
          myFloatField.setEnabled(value);
          myFloatField.setEditable(value);
        }
        return;
      }
      if (myProperty.getType().equals("clocktime")) {
        if (myClockTimeField != null) {
          myClockTimeField.setFocusable(value);
          myClockTimeField.setEnabled(value);
          myClockTimeField.setEditable(value);
        }
        return;
      }
      if (myProperty.getType().equals("money")) {
        if (myMoneyField != null) {
          myMoneyField.setFocusable(value);
          myMoneyField.setEnabled(value);
          myMoneyField.setEditable(value);
        }
        return;
      }
      if (myProperty.getType().equals("percentage")) {
        if (myPercentageField != null) {
          myPercentageField.setFocusable(value);
          myPercentageField.setEnabled(value);
          myPercentageField.setEditable(value);
        }
        return;
      }
      if (myProperty.getType().equals("password")) {
        if (myPasswordField != null) {
          myPasswordField.setFocusable(value);
          myPasswordField.setEnabled(value);
          myPasswordField.setEditable(value);
        }
        return;
      }
//      if (myProperty.getType().equals("password")) {
//        if (myPasswordField!=null) {
//          myPasswordField.setFocusable(value);
//          myPasswordField.setEnabled(value);
//          myPasswordField.setEditable(value);
//        }
//        return;
//      }
      if (myField != null) {
        myField.setFocusable(value);
        myField.setEnabled(value);
        myField.setEditable(value);
      }
      return;
    }
    else {
    }
  }

  public final void setCursor(Cursor cursor) {
    super.setCursor(cursor);
    if (currentPropertyComponent != null) {
      currentPropertyComponent.setCursor(cursor);

    }
  }

  public final void addPropertyEventListener(PropertyEventListener pel) {
    myPropertyEventListeners.add(pel);
  }

  public final void removePropertyEventListner(PropertyEventListener pel) {
    myPropertyEventListeners.remove(pel);
  }

  protected final void firePropertyEvents(Property p, String eventType, Validatable v) {
    for (int i = 0; i < myPropertyEventListeners.size(); i++) {
      PropertyEventListener current = (PropertyEventListener) myPropertyEventListeners.get(i);
      current.propertyEventFired(p, eventType, v);
    }
  }

  public void setSelectionType(String s) {
    mySelectionType = s;
  }

  public void setCapitalization(String mode) {
    myCapitalization = mode;
    if (myField != null) {
      myField.setCapitalizationMode(mode);
    }

  }

  public String getCapitalization() {
	    return myCapitalization;
	  }  

  
  public final void setVisibleRows(int count) {
    visibleRowCount = count;
    if (myMultipleList != null) {
      myMultipleList.setVisibleRowCount(count);
    }
    if (myPickList!=null) {
        myPickList.setVisibleRowCount(count);
    }
  }

  public void setShowDatePicker(boolean b) {
    showDatePicker = b;
    if (myDateField != null) {
      myDateField.setShowCalendarPickerButton(b);
    }

  }

  public void setVerticalScrolls(boolean b) {
    verticalScrolls = b;
    if (myMultipleList != null) {
      myMultipleList.setVerticalScrolls(b);
    }
    if (myMultiple != null) {
      myMultiple.setVerticalScrolls(b);
    }
    if (myMemoField != null) {
      // No null check, if there is no scroll pane, it deserves to crash
      memoFieldScrollPane.setVerticalScrollBarPolicy(verticalScrolls ? JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED : JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    }
  }

  public void setHorizontalScrolls(boolean b) {
    horizontalScrolls = b;
    if (myMultipleList != null) {
      myMultipleList.setHorizontalScrolls(b);
    }
    if (myMultiple != null) {
      myMultiple.setHorizontalScrolls(b);
    }
    if (myMemoField != null) {
      memoFieldScrollPane.setHorizontalScrollBarPolicy(horizontalScrolls ? JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED : JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
  }

  public void resetChanged() {
  }

  public final boolean hasChanged() {
    if (ChangeMonitoring.class.isInstance(currentComponent)) {
      return ( (ChangeMonitoring) currentComponent).hasChanged();
    }
    System.err.println("Returning true by default... Should this happen? ..no I changed it to false");
    if (currentComponent == null) {
      System.err.println("No current component");
    }
    else {
      System.err.println("Current component class: " + currentComponent.getClass());
    }
    return false;
  }

  public final boolean hasFocus() {
    if (currentComponent != null) {
      return currentComponent.hasFocus();
    }
    return false;
  }

  public boolean doValidate() {
    if (currentComponent == null) {
      return true;
    }
    else {
      if (currentComponent instanceof InputValidator) {
        return ( (PropertyField) currentComponent).doValidate();
      }
      if (currentComponent instanceof PropertyPasswordField) {
        return ( (PropertyPasswordField) currentComponent).doValidate();
      }
      return true;
    }
  }

  public final void setInputValidator(InputValidator iv) {
    myInputValidator = iv;
  }

  /**
   * addValidationRule
   *
   * @param state int
   * @todo Implement this com.dexels.navajo.swingclient.components.Validatable method
   */
  public void addValidationRule(int state) {
    if (Validatable.class.isInstance(currentComponent)) {
       ( (Validatable) currentComponent).addValidationRule(state);
    }

  }

  /**
   * setValidationMessage
   *
   * @param msg Message
   * @todo Implement this com.dexels.navajo.swingclient.components.Validatable method
   */
  public final void setValidationMessage(Message msg) {
    if (Validatable.class.isInstance(currentComponent)) {
       ( (Validatable) currentComponent).setValidationMessage(msg);
    }

  }

  /**
   * checkValidation
   *
   * @todo Implement this com.dexels.navajo.swingclient.components.Validatable method
   */
  public final void checkValidation() {
    if (Validatable.class.isInstance(currentComponent)) {
       ( (Validatable) currentComponent).checkValidation();
    }
  }

  /**
   * checkValidation
   *
   * @param msg Message
   * @todo Implement this com.dexels.navajo.swingclient.components.Validatable method
   */
  public final void checkValidation(Message msg) {
    if (Validatable.class.isInstance(currentComponent)) {
       ( (Validatable) currentComponent).checkValidation(msg);
    }
  }

  /**
   * clearValidationRules
   *
   * @todo Implement this com.dexels.navajo.swingclient.components.Validatable method
   */
  public final void clearValidationRules() {
    if (Validatable.class.isInstance(currentComponent)) {
       ( (Validatable) currentComponent).clearValidationRules();
    }
  }

  /**
   * setValidationState
   *
   * @param state int
   * @todo Implement this com.dexels.navajo.swingclient.components.Validatable method
   */
  public final void setValidationState(int state) {
    if (Validatable.class.isInstance(currentComponent)) {
       ( (Validatable) currentComponent).setValidationState(state);
    }
  }

  /**
   * getValidationState
   *
   * @return int
   * @todo Implement this com.dexels.navajo.swingclient.components.Validatable method
   */
  public final int getValidationState() {
    if (Validatable.class.isInstance(currentComponent)) {
      return ( (Validatable) currentComponent).getValidationState();
    }
    return 0;
  }

  /**
   * setToolTipText
   *
   * @param text String
   * @todo Implement this com.dexels.navajo.swingclient.components.Validatable method
   */
  public final void setToolTipText(String text) {
    if (Validatable.class.isInstance(currentComponent)) {
       ( (Validatable) currentComponent).setToolTipText(text);
    }
  }

  /**
   * addConditionRuleId
   *
   * @param id String
   * @todo Implement this com.dexels.navajo.swingclient.components.Validatable method
   */
  public final void addConditionRuleId(String id) {
    if (Validatable.class.isInstance(currentComponent)) {
       ( (Validatable) currentComponent).addConditionRuleId(id);
    }
  }

  /**
   * getConditionRuleIds
   *
   * @return ArrayList
   * @todo Implement this com.dexels.navajo.swingclient.components.Validatable method
   */
  public final ArrayList getConditionRuleIds() {
    if (Validatable.class.isInstance(currentComponent)) {
      return ( (Validatable) currentComponent).getConditionRuleIds();
    }
    return null;
  }

  public void setAlwaysUseLabel(boolean b) {
    alwaysUseLabel = b;
    setProperty(getProperty());
  }
  public Dimension getMinimumSize() {
      return getPreferredSize();
  }
  public Dimension getPreferredSize() {
    if (myProperty == null || currentComponent == null) {
//        System.err.println("no prop or no component");
      return limitTo(super.getPreferredSize(), getMaximumSize());
    }
    Dimension labelDimension = null;
    if (showLabel && myLabel != null) {
      labelDimension = myLabel.getPreferredSize();
      if (currentLabelIndentStrut != null) {
        labelDimension = new Dimension(Math.max(labelDimension.width, labelWidth), labelDimension.height);
      }
      if (labelDimension != null && labelWidth > labelDimension.width) {
        labelDimension.width = labelWidth;
      }
    }
    labelWidth = -1;
    Dimension componentDimension = currentComponent.getPreferredSize();
    if (componentDimension == null) {
//        System.err.println("Component without dimension");
      if (myPreferredSize != null) {
        return limitTo(new Dimension(myPreferredSize.width, super.getPreferredSize().height), getMaximumSize());
      }
      return limitTo(super.getPreferredSize(), getMaximumSize());
    }
    if (labelDimension != null) {
      int height = Math.max(labelDimension.height, componentDimension.height);
      int w = labelDimension.width + componentDimension.width;
      if (myPreferredSize != null) {
        w = myPreferredSize.width;
      }

      return limitTo(new Dimension(w, height), getMaximumSize());
    }
    else {
      return limitTo(componentDimension, getMaximumSize());
    }
  }

  
  public void setTextFieldColumns(int columnCount) {
	  if (currentComponent!=null && currentComponent instanceof JTextField) {
		JTextField j = (JTextField)currentComponent;
		j.setColumns(Math.max(columnCount,3));
	}
  }
  
  
  private Dimension limitTo(Dimension preferredSize, Dimension maximumSize) {
    if (maximumSize == null) {
      if(forcedTotalWidth > -1){
        return new Dimension(forcedTotalWidth, preferredSize.height);
      }
      return preferredSize;
    }
    if(forcedTotalWidth > -1){
      return new Dimension(forcedTotalWidth, Math.min(preferredSize.height, maximumSize.height));
    }
    return new Dimension(Math.min(preferredSize.width, maximumSize.width), Math.min(preferredSize.height, maximumSize.height));
  }

  public void setPreferredSize(Dimension preferredSize) {
    myPreferredSize = preferredSize;
  }


public void forceFieldAlignment(String forceFieldAlignment) {
	this.forceFieldAlignment  = forceFieldAlignment;
}

public void limitFieldWidth(int i) {
	this.limitFieldWidth  = i;
	
}

public void setMaxWidth(int i) {
	Dimension d = getPreferredSize();
	if (d!=null) {
		d.width = i;
	} else {
		setMaximumSize(new Dimension(i,Integer.MAX_VALUE));
	}
	
}

public void requestPropertyFocus() {
	SwingUtilities.invokeLater(new Runnable(){

		public void run() {
			if(currentComponent != null){
				 currentComponent.requestFocusInWindow();
			}				
		}});
}

public void setComponentBorder(Border b) {
	if(currentComponent != null){
		 currentComponent.setBorder(b);
	}	
}
//  private InputValidator myInputValidator = null;

public boolean hasHorizontalScrolls() {
	return horizontalScrolls;
}

public boolean hasVerticalScrolls() {
	return verticalScrolls;
}

public int getMaxImageHeight() {
	// TODO Auto-generated method stub
	return max_img_height;
}

public int getMaxImageWidth() {
	// TODO Auto-generated method stub
	return max_img_width;
}

public int getMaxWidth() {
	// TODO Auto-generated method stub
	return Integer.MAX_VALUE;
}

public int getMemoColumnCount() {
	// TODO Auto-generated method stub
	return memoColumnCount;
}

public String getSelectionType() {
	return mySelectionType;
}

public boolean hasShowDatePicker() {
	// TODO Auto-generated method stub
	return showDatePicker;
}

public int getCheckboxGroupColumnCount() {
	return checkboxGroupColumnCount;
}

public TypeFormatter getFormatter() {
	return formatter;
}

public void setFormatter(TypeFormatter formatter) {
	this.formatter = formatter;
}
}