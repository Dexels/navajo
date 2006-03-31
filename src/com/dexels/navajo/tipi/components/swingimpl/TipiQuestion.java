package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import java.util.*;
import com.dexels.navajo.tipi.tipixml.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.actions.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.parser.*;
import javax.swing.border.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiQuestion extends TipiPanel {
    private String messagePath = null;

    private String questionDefinitionName = null;

    // private TipiQuestionGroup questionGroupPath = null;
    private TipiBaseQuestionGroup questionGroup = null;

    private final ArrayList mySubQuestions = new ArrayList();

    private String enabledCondition = null;

    private String visibleCondition = null;

    private String validationCondition = null;

    private Message myMessage = null;

    private String myId;

    public TipiQuestion() {
        super();
    }

    public Object createContainer() {

        Object object = super.createContainer();
        ((Container)object).setLayout(new GridBagLayout());
//        ((Container)object).setPreferredSize(new Dimension(800,100));
        return object;
    }

    
    public void setComponentValue(String name, Object object) {
        if (name.equals("messagePath")) {
//            System.err.println("Messagepath: >" + object + "<");
            messagePath = (String) object;
        }
        if (name.equals("questionDefinitionName")) {
//            System.err.println("questionDefinitionName: >" + object + "<");
            questionDefinitionName = (String) object;
        }
        super.setComponentValue(name, object);
    }

    public void setQuestionGroup(TipiBaseQuestionGroup tqg) {
        questionGroup = tqg;
    }

    public void addToContainer(Object c, Object constraints) {
//        super.addToContainer(c, BorderLayout.CENTER);
        super.addToContainer(c, new GridBagConstraints(0,0,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
    }

    public void loadData(final Navajo n, final TipiContext context) throws TipiException {
        // runSyncInEventThread(new Runnable() {
        // public void run() {
        enabledCondition = null;
        visibleCondition = null;
        validationCondition = null;
        //
        removeInstantiatedChildren();
        //
        // removeAllChildren();
        //
        mySubQuestions.clear();
        Message m = n.getMessage(messagePath);
        myMessage = m;
        if (m == null) {
            return;
        }
        Property titleProperty = m.getProperty("Title");
        if (titleProperty != null) {
            String val = titleProperty.getValue();
            ((JPanel) getContainer()).setBorder(BorderFactory.createTitledBorder("" + val));
        } else {
            ((JPanel) getContainer()).setBorder(BorderFactory.createLoweredBevelBorder());
        }
        Property visibleConditionProperty = m.getProperty("VisibleCondition");
        if (visibleConditionProperty != null) {
            visibleCondition = visibleConditionProperty.getValue();
        }
        Property enabledConditionProperty = m.getProperty("EnabledCondition");
        if (enabledConditionProperty != null) {
            enabledCondition = enabledConditionProperty.getValue();
        }
        Property validationConditionProperty = m.getProperty("ValidationCondition");
        if (validationConditionProperty != null) {
            validationCondition = validationConditionProperty.getValue();
        }
        ArrayList properties = getRecursiveProperties();
//        System.err.println("******************* "+properties);
        for (int i = 0; i < properties.size(); i++) {
            PropertyComponent o = (PropertyComponent) properties.get(i);
//            System.err.println("Property found: "+o.getPath());
//            System.err.println("Property name: "+o.getPropertyName());
            Property pp = m.getProperty(o.getPropertyName());
            JComponent jj = (JComponent)o.getContainer();
             if (pp!=null) {
                o.setProperty(pp);
//                System.err.println("Setting property: "+pp.getValue());
                
            } else {
                System.err.println("No such property");
            }
             System.err.println("Property:::: "+jj.getPreferredSize());
            o.addTipiEventListener(new TipiEventListener() {
                public boolean performTipiEvent(String eventtype, Map source, boolean sync) throws TipiException {
                    runASyncInEventThread(new Runnable() {
                        public void run() {
                            updateQuestionGroup();
                        }
                    });
                    return true;
                }

                public void eventStarted(TipiExecutable te, Object event) {
                }

                public void eventFinished(TipiExecutable te, Object event) {
                }
            });
//            o.validate();
//             System.err.println("Property: "+o);
        }
        // TODO: Change this to a recursive search:

        
        
/*        
        TipiDataComponent tdc = (TipiDataComponent) getTipiComponent("SubQuestions");
        Message question = m.getMessage("Question");
        if (question != null && tdc != null) {
            for (int i = 0; i < question.getArraySize(); i++) {
                Message current = question.getMessage(i);
                Property idProp = current.getProperty("Id");
                if (idProp == null) {
                    System.err.println("No id property found. Message: ");
                    current.write(System.err);
                    continue;
                }
                myId = idProp.getValue();
                try {
                    TipiQuestion tc = (TipiQuestion) TipiInstantiateTipi.instantiateByDefinition(tdc, false, myId, questionDefinitionName, null);
                    tc.setValue("messagePath", current.getFullMessageName());
                    tc.setPrefix(current.getFullMessageName());
                    tc.setValue("questionDefinitionName", questionDefinitionName);
                    tc.loadData(n, myContext);
                    tc.setQuestionGroup(questionGroup);
                    mySubQuestions.add(tc);
                } catch (TipiException ex) {
                    ex.printStackTrace();
                }
            }
        }
*/
//        ((JPanel) getContainer()).doLayout();
//        System.err.println("PREFERRED "+((JPanel) getContainer()).getPreferredSize() );
//        SwingTipiContext.debugSwingTree((Component)getContainer(), 5);
    }

    
    public void oldLoadData(Navajo n, TipiContext tc) throws TipiException {
        if (n == null) {
            throw new TipiException("Loading with null Navajo! ");
        }
        for (int i = 0; i < properties.size(); i++) {
            PropertyComponent current = (PropertyComponent) properties.get(i);
            Property p;
            if (prefix != null) {
                p = n.getProperty(prefix + "/" + current.getPropertyName());
                current.setProperty(p);
            } else {
                p = n.getProperty(current.getPropertyName());
                if (p != null) {
                    current.setProperty(p);
                }
            }
        }
        if (n == null) {
            System.err.println("NULL NAVAJO!");
            return;
        }
        myNavajo = n;
    }

    public void swingDebug(Component c) {
        System.err.println("DEBUGGING COMPONENT: "+c);
        System.err.println("Class: "+c.getClass());
        System.err.println("Visible: "+c.isVisible());
        if (c instanceof Container) {
            System.err.println("It is a container");
            Container cc = (Container)c;
            for (int i = 0; i < cc.getComponents().length; i++) {
                swingDebug(cc.getComponents()[i]);
            }
            
        }
        System.err.println("END OF DEBUG");
    }
    public void updateQuestionGroup() {
        // if (questionGroupPath != null) {
        // Operand o = myContext.evaluate(questionGroupPath, this, null,
        // myMessage);
        // TipiQuestionGroup tqg = (TipiQuestionGroup) o.value;
        if (questionGroup != null) {
            System.err.println("updatin: " + questionGroup.getPath());
            questionGroup.updateQuestions();
        } else {
            System.err.println("Not found");
        }
        // }
    }

    public boolean isRelevant() {
         if (true) {
         return true;
         }
        if (visibleCondition != null) {
            // System.err.println("Evaluating: visibleCondition: " +
            // visibleCondition);
            // System.err.println("ID: "+myId);
            Operand o = myContext.evaluate(visibleCondition, this, null, myMessage);
            if (o != null) {
                return ((Boolean) o.value).booleanValue();
            }
            return false;
        }
        return true;
    }

    public void updateSubQuestions() {
        if (true) {
            return;
            }
        if (visibleCondition != null) {
            getSwingContainer().setVisible(isRelevant());
            // ((JComponent)getSwingContainer()).scrollRectToVisible(getSwingContainer().getBounds());
        }
        boolean invalidFound = false;
        for (int i = 0; i < mySubQuestions.size(); i++) {
            TipiQuestion tq = (TipiQuestion) mySubQuestions.get(i);
            tq.updateSubQuestions();
            if (tq.isValid() == false) {
                invalidFound = true;
                System.err.println("FOUND AN INVALID CHILD: " + tq.getPath());
            }
        }
        if (invalidFound) {
            setValid(false, "Subvraag incorrect");
        } else {
            if (!isValid()) {
                setValid(false);
            } else {
                setValid(true);
            }
        }
    }

    public boolean isRecursiveValid() {
        if (!isValid()) {
            return false;
        }
        if (!isRelevant()) {
            return true;
        }
        for (int i = 0; i < mySubQuestions.size(); i++) {
            TipiQuestion tq = (TipiQuestion) mySubQuestions.get(i);
            if (tq.isRecursiveValid() == false) {
                return false;
            }
        }
        return true;
    }

    public void setValid(boolean b) {
        Property errorMessageProperty = myMessage.getProperty("ErrorMessage");
        String errorMessage = null;
        String msg = "Error";
        if (errorMessageProperty != null) {
            errorMessage = errorMessageProperty.getValue();
        }
        if (errorMessage != null) {
            Operand o = myContext.evaluate(errorMessage, this, null, myMessage);
            msg = "" + o.value;
        }
        setValid(b, msg);
    }

    public void setValid(boolean b, String msg) {

        if (b == false) {
            Border bbb = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.red), msg);
            ((JComponent) getSwingContainer()).setBorder(bbb);
        } else {
            Border bbb = BorderFactory.createEmptyBorder(2, 2, 2, 2);
            ((JComponent) getSwingContainer()).setBorder(bbb);
        }
    }

    public boolean isValid() {
        if (!isRelevant()) {
            return true;
        }
        //
        // return true;
        //
        if (validationCondition != null) {
            // System.err.println("validationCondition: " +
            // validationCondition);
            Operand o = myContext.evaluate(validationCondition, this, null, myMessage);
            if (o != null) {
                boolean result = ((Boolean) o.value).booleanValue();
                return result;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void load(XMLElement def, XMLElement instance, TipiContext context) throws TipiException {
        super.load(def, instance, context);
    }

    public void setContainerLayout(Object layout) {
    }
}
