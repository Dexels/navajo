/*
 * Created on Feb 16, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.echoimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Style;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.echoclient.components.Styles;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;
import com.dexels.navajo.tipi.components.question.TipiBaseQuestionGroup;
import com.dexels.navajo.tipi.components.question.TipiBaseQuestionList;

import echopointng.ButtonEx;
import echopointng.TabbedPane;
import echopointng.image.URLImageReference;
import echopointng.tabbedpane.DefaultTabModel;
/**
 * 
 * @author frank
 * @deprecated
 */
@Deprecated
public class TipiTabbedQuestionList extends TipiBaseQuestionList {
	private static final long serialVersionUID = -9212569710207074092L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTabbedQuestionList.class);
	
	private DefaultTabModel defaultTabModel = null;

    private TabbedPane myTabbedPane;

    private ImageReference validImage;

    private ImageReference inValidImage;

    private Style validStyle;
    private Style invalidStyle;
    
    protected Object getGroupConstraints(Message groupMessage) {
		Property name = groupMessage.getProperty("Name");
		if (name != null) {
			if (name.getValue() == null) {
				return "Unknown tab";
			} else {
				return name.getValue();
			}
		}
		return "Unknown tab";
    }
    public void loadData(final Navajo n,final String method) throws TipiException {
        clearQuestions();
//        lastSelectedTab = null;
        super.loadData(n, method);
        if(defaultTabModel.size()>0) {
        	myTabbedPane.setSelectedIndex(0);
        }
    }
//    public Object createContainer() {
//        final TipiComponent me = this;
//        myTabbedPane = new TabbedPane();
//        defaultTabModel = new DefaultTabModel();
//
//        validImage = new URLImageReference(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/echoimpl/ok.gif"));
//        inValidImage = new URLImageReference(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/echoimpl/cancel.gif"));
//        myTabbedPane.setModel(defaultTabModel);
//        TipiHelper th = new EchoTipiHelper();
//        th.initHelper(this);
//        addHelper(th);
//        return myTabbedPane;
//    }
    
    public Object createContainer() {
        myTabbedPane = new TabbedPane();
        myTabbedPane.setStyleName("Default");
        
    	Style sss = Styles.DEFAULT_STYLE_SHEET.getStyle(myTabbedPane.getClass(), "Default");
    	myTabbedPane.setStyle(sss);
    	logger.info("Looking for style for class: "+myTabbedPane.getClass());
    	if (sss!=null) {
        	logger.info("Style found : "+sss);
		} else {
			logger.info("Style not found");
		}
        defaultTabModel = new DefaultTabModel();
//        defaultTabModel.setSelectedBackground(new Color(255, 255, 255));
//        defaultTabModel.setSelectedForeground(new Color(0, 0, 0));
//        defaultTabModel.setForeground(new Color(153, 153, 153));
//        defaultTabModel.setRolloverForeground(new Color(68, 68, 68));
//        defaultTabModel.setSelectedRolloverForeground(new Color(68, 68, 68));
//        defaultTabModel.setSelectedFont(new Font(Font.ARIAL, Font.BOLD, new Extent(10, Extent.PT)));
//        defaultTabModel.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(10, Extent.PT)));
//        defaultTabModel.setRolloverFont(new Font(Font.ARIAL, Font.BOLD, new Extent(10, Extent.PT)));
//        defaultTabModel.setBackground(new Color(255, 255, 255));
//        defaultTabModel.setRolloverBackground(new Color(255, 255, 255));
//        defaultTabModel.setSelectedRolloverBackground(new Color(255, 255, 255));
        myTabbedPane.setModel(defaultTabModel);
        TipiHelper th = new EchoTipiHelper();
        th.initHelper(this);
        addHelper(th);
       // myTabbedPane.setTabSpacing(2);
//        myTabbedPane.setFont(new Font(Font.ARIAL,Font.PLAIN,new Extent(10, Extent.PT)));
//        myTabbedPane.addPropertyChangeListener(new PropertyChangeListener() {
//
//            public void propertyChange(PropertyChangeEvent evt) {
//            	logger.info("Boioioiong");
//            	logger.info("EVT: old: "+evt.getOldValue()+" new: "+evt.getNewValue());
//            }
//            });
        validImage = new URLImageReference(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/echoimpl/ok.gif"));
        inValidImage = new URLImageReference(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/echoimpl/cancel.gif"));

        
        validStyle = Styles.DEFAULT_STYLE_SHEET.getStyle(ButtonEx.class, "ValidQuestionGroupTab");
        invalidStyle = Styles.DEFAULT_STYLE_SHEET.getStyle(ButtonEx.class, "InvalidQuestionGroupTab");

        return myTabbedPane;
    }
    

    public void addToContainer(Object c, Object constraints) {
    	String tabName = ""+constraints;
//    	PushButton pb = new PushButton(tabName);
        defaultTabModel.addTab(tabName, (Component) c);
        myTabbedPane.setFont(new Font(Font.VERDANA, Font.PLAIN, new Extent(10, Extent.PT)));

        //        if (lastSelectedTab == null) {
//            lastSelectedTab = (Component) c;
//        }
        // logger.info("WIDTH: " + myTabbedPane.getWidth());
        // logger.info("HEIGHT: " + myTabbedPane.getWidth());
        // logger.info("Tab count: "+getChildCount());
        // ButtonEx notSelected
        // =(ButtonEx)defaultTabModel.getTabAt(getChildCount()-1,false);
        
//        ButtonEx selected = (ButtonEx) defaultTabModel.getTabAt(getChildCount() - 1, true);
//        selected.setBorder(new Border(1, new Color(50, 50, 50), Border.STYLE_GROOVE));

        // selected.setText("Selected");
        // notSelected.setText("Not selected");
        // logger.info("Sel: "+selected.toString());
        // logger.info("NSel: "+notSelected.toString());
        // selected.setForeground(new Color(0,0,0));
        // selected.setBackground(myTabbedPane.getBackground());
        // notSelected.setForeground(new Color(0,0,0));
        // notSelected.setBackground(new Color(150,150,150));
    }
    
    protected void clearQuestions() {
        while (defaultTabModel.size() > 0) {
            defaultTabModel.removeTabAt(0);
            
        }
    }

  
	public void setGroupValid(boolean valid, TipiBaseQuestionGroup group) {
        super.setGroupValid(valid, group);
        int i = myGroups.indexOf(group);
        if (!myGroups.contains(group)) {
            return;
        }
        if (i < 0) {
            logger.info("Sh!34#@$!");
            return;
        }
        ButtonEx selected = (ButtonEx) defaultTabModel.getTabAt(myTabbedPane,i, true);
        selected.setIcon(valid ? validImage : inValidImage);
        selected.setFont(new Font(Font.VERDANA,Font.PLAIN, new Extent(10,Extent.PX)));
        //        selected.setProperty("icon", /)
//        selected.setStyle(valid? validStyle: invalidStyle);
        logger.info("SETTING VALIDITY "+valid+" style: "+validStyle+" inv: "+invalidStyle);
	}

    public void setComponentValue(String name, Object object) {
        if (name.equals("background")) {
            Color background = (Color) object;
            myTabbedPane.setBackground(background);
            return;
        }

        super.setComponentValue(name, object);
    }
}
