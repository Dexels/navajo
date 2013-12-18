/*
 * Created on Feb 16, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.vaadin.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.question.TipiBaseQuestionGroup;
import com.dexels.navajo.tipi.components.question.TipiBaseQuestionList;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;


public class TipiTabbedQuestionList extends TipiBaseQuestionList {
//    private Component lastSelectedTab = null;

//    private DefaultTabModel defaultTabModel = null;

	private static final long serialVersionUID = -1524641568991571455L;

	private TabSheet myTabbedPane;

    private Resource validImage;

    private Resource inValidImage;

    private final String validStyle = "valid";
    private final String invalidStyle = "invalid";
    
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTabbedQuestionList.class);
	
    @Override
	protected Object getGroupConstraints(Message groupMessage) {
        Property name = groupMessage.getProperty("Name");
        if (name != null) {
            if (name.getValue() == null) {
                return "Unknown tab";
            }
            return name.getValue();
        }
        return "Unknown tab";
    }
    @Override
	public void loadData(final Navajo n,final String method) throws TipiException {
        clearQuestions();
        super.loadData(n, method);
    }
    
    @Override
	public Object createContainer() {
    	myTabbedPane = new TabSheet();
        myTabbedPane.setSizeFull();
//        myTabbedPane.setStyleName("Default");
        myTabbedPane.addStyleName("tabbed-questionlist");

//        defaultTabModel = new DefaultTabModel();
//        TipiHelper th = new EchoTipiHelper();
//        th.initHelper(this);
//        addHelper(th);

        validImage = new ThemeResource("../oao/validIcon.gif");
        inValidImage = new ThemeResource("../oao/invalidIcon.gif");

//        validStyle = Styles.DEFAULT_STYLE_SHEET.getStyle(ButtonEx.class, "ValidQuestionGroupTab");
//        invalidStyle = Styles.DEFAULT_STYLE_SHEET.getStyle(ButtonEx.class, "InvalidQuestionGroupTab");

        return myTabbedPane;
    }
    

    @Override
	public void addToContainer(Object c, Object constraints) {
//        defaultTabModel.addTab(tabName, (Component) c);
//		tabSheet.addComponent(component);
		Component component = (Component)c;
    	component.setSizeFull();
//		tabSheet.addTab(component, ""+constraints, null);
    	myTabbedPane.addComponent(component);
		component.setSizeFull();
		Tab t =  myTabbedPane.addTab(component, ""+constraints, null);
//		t.setStyleName("tabbed-question");
		t.getComponent().addStyleName("tabbed-question-"+""+constraints);
//		myTabbedPane.getTabCaption(component).
		//		t.getComponent().setWidth("130px");
//		final Component tabComponent = t.getComponent();
		//    	myTabbedPane.addTab( (Component)c, tabName, null);
    }
    
    protected void clearQuestions() {

    	myTabbedPane.removeAllComponents();
    }

   
    
	@Override
	public void setGroupValid(boolean valid, TipiBaseQuestionGroup group) {
        super.setGroupValid(valid, group);
        int i = myGroups.indexOf(group);
        if (!myGroups.contains(group)) {
            return;
        }
        if (i < 0) {
            logger.error("Sh!34#@$!");
            return;
        }
        Tab t = myTabbedPane.getTab(i);
        t.setIcon(valid ? validImage : inValidImage);
        t.getComponent().setStyleName(valid?validStyle:invalidStyle);
	}

}
