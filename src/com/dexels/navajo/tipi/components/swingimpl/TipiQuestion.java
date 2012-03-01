/*
 * Created on Apr 11, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.dexels.navajo.tipi.components.question.TipiBaseQuestion;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingPanel;
/**
 * @deprecated
 * @author frank
 *
 */
public class TipiQuestion extends TipiBaseQuestion {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4666897405672564798L;
	private TipiSwingPanel myPanel;

	public void setValid(boolean b, String msg) {

		if (b == false) {
			Border bbb = BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.red), msg);
			((JComponent) getContainer()).setBorder(bbb);
		} else {
			Border bbb = BorderFactory.createEmptyBorder(2, 2, 2, 2);
			((JComponent) getContainer()).setBorder(bbb);
		}
	}

	public Object createContainer() {

		myPanel = new TipiSwingPanel(TipiQuestion.this);
		// TipiHelper th = new TipiSwingHelper();
		// th.initHelper(this);
		// addHelper(th);
		myPanel.setLayout(new GridBagLayout());
		myPanel.setFocusable(false);
		// ((Container)object).setPreferredSize(new Dimension(800,100));
		return myPanel;
	}

	public void addToContainer(Object c, Object constraints) {

		myPanel.add((Component) c, constraints);
	}

	public void removeFromContainer(final Object c) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				((Container) getContainer()).remove((Component) c);
			}
		});
	}

	public void setContainerLayout(final Object layout) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				((Container) getContainer()).setLayout((LayoutManager) layout);
			}
		});
	}

	protected void setQuestionBorder(String val) {
		if (val == null) {
			((JPanel) getContainer()).setBorder(BorderFactory
					.createTitledBorder("" + val));
		} else {
			((JPanel) getContainer()).setBorder(BorderFactory
					.createLoweredBevelBorder());
		}
	}

	public void runSyncInEventThread(Runnable r) {
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(r);
			} catch (InvocationTargetException ex) {
				throw new RuntimeException(ex);
			} catch (InterruptedException ex) {
			}
		}
	}

	public void runAsyncInEventThread(Runnable r) {
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			SwingUtilities.invokeLater(r);
		}
	}

	public void setQuestionVisible(boolean b) {
		((JComponent) getContainer()).setVisible(isRelevant());

	}

}
