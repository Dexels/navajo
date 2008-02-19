package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiTextArea
    extends TipiSwingComponentImpl {
  private TipiSwingTextArea myTextArea;

public TipiTextArea() {
  }

  public Object createContainer() {
    myTextArea = new TipiSwingTextArea();
	TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    JScrollPane jsp = new JScrollPane(myTextArea);
    addHelper(th);
    myTextArea.addKeyListener(new KeyListener() {
        public void keyTyped(KeyEvent e) {
            Map<String,Object> m = getEventMap(e);
            m.put("mode", "typed");
            if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                try {
                performTipiEvent("onEnter", m, true);
                } catch (TipiException e1) {
                    e1.printStackTrace();
                }
			}
            try {
                performTipiEvent("onKey", m, true);
            } catch (TipiException e1) {
                e1.printStackTrace();
            }
        }

        public void keyPressed(KeyEvent e) {
            Map<String,Object> m = getEventMap(e);
            m.put("mode", "typed");
            try {
                performTipiEvent("onKey", m, true);
            } catch (TipiException e1) {
                e1.printStackTrace();
            }
        }

        public void keyReleased(KeyEvent e) {
            Map<String,Object> m = getEventMap(e);
            m.put("mode", "released");
            try {
                performTipiEvent("onKey", m, true);
            } catch (TipiException e1) {
                e1.printStackTrace();
            }
        }
        
        public Map<String,Object> getEventMap(KeyEvent e) {
            Map<String,Object> hm = new HashMap<String,Object>();
            hm.put("code", new Integer(e.getKeyCode()));
            hm.put("modifiers", KeyEvent.getKeyModifiersText(e.getModifiers()));
            hm.put("key", KeyEvent.getKeyText(e.getKeyCode()));
            return hm;
        }
    });
    return jsp;
  }

  public void setComponentValue(final String name, final Object object) {
    if (name.equals("text")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
        	myTextArea.setText( (String) object);
        }
      });
      return;
    }
    super.setComponentValue(name, object);
  }

  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return myTextArea.getText();
    }
    return super.getComponentValue(name);
  }
  

	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);

		if (name.equals("append")) {
			Operand o = compMeth.getEvaluatedParameter("text", event);
			if (o != null) {
				String result = (String) o.value;
				myTextArea.append(result);
			}
		}
		if (name.equals("appendLine")) {
			Operand o = compMeth.getEvaluatedParameter("text", event);
			if (o != null) {
				String result = (String) o.value;
				myTextArea.setText(myTextArea.getText()+result+"\n");
			}
		}

	}

}
