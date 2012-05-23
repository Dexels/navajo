/*
 * Created on Feb 16, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.question.TipiBaseQuestionGroup;
import com.dexels.navajo.tipi.components.question.TipiBaseQuestionList;
/**
 * @deprecated
 * @author frank
 *
 */
public class TipiTabbedQuestionList extends TipiBaseQuestionList {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7291498630845123122L;
	private Component lastSelectedTab = null;
	private JTabbedPane tabbedPane;

	protected Object getGroupConstraints(Message groupMessage) {
		Property name = groupMessage.getProperty("Name");
		if (name == null) {
			return "Unknown tab";

		} else {
			if (name.getValue() != null) {
				return name.getValue();
			}

		}
		return name.getValue();
	}

	public void runAsyncInEventThread(Runnable runnable) {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
			SwingUtilities.invokeLater(runnable);
		}
	}

	public void runSyncInEventThread(Runnable runnable) {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(runnable);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public Object createContainer() {
		final TipiComponent me = this;
		// JTabbedPane jt = null;
		runSyncInEventThread(new Runnable() {

			public void run() {
				tabbedPane = new JTabbedPane();
				tabbedPane.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent ce) {
						try {
							me.performTipiEvent("onTabChanged", null, false);
							lastSelectedTab = tabbedPane.getSelectedComponent();
						} catch (TipiException ex) {
							System.err
									.println("Exception while switching tabs.");
							ex.printStackTrace();
						}
					}
				});
			}
		});

		return tabbedPane;
	}

	public void addToContainer(final Object c, final Object constraints) {
		// System.err.println("Adding to TipiTabbedQuestionList container:   "+c+" constraints: "+constraints);
		runSyncInEventThread(new Runnable() {
			public void run() {
				tabbedPane = (JTabbedPane) getContainer();
				// pane.addTab( (String) constraints, new JButton("AAAP"));
				tabbedPane.addTab((String) constraints, (Component) c);
				// tabbedPane.setIconAt(tabbedPane.getTabCount()-1,tabbedPane.getTabCount()%2==0?new
				// ImageIcon(getContext().getResourceURL("com/dexels/navajo/tipi/components/swingimpl/swing/ok.gif")):new
				// ImageIcon(getContext().getResourceURL("com/dexels/navajo/tipi/components/swingimpl/swing/cancel.gif")));
				// pane.setEnabledAt(pane.indexOfComponent( (Component) c), (
				// (Component) c).isEnabled());
				if (lastSelectedTab == null) {
					lastSelectedTab = (Component) c;
				}
			}
		});
	}

	public void setGroupValid(boolean valid, TipiBaseQuestionGroup group) {
		super.setGroupValid(valid, group);
		int i = myGroups.indexOf(group);
		if (i < 0) {
			System.err.println("Sh!34#@$!");
		}
		tabbedPane
				.setIconAt(
						i,
						valid ? new ImageIcon(
								getContext()
										.getResourceURL(
												"com/dexels/navajo/tipi/components/swingimpl/swing/ok.gif"))
								: new ImageIcon(
										getContext()
												.getResourceURL(
														"com/dexels/navajo/tipi/components/swingimpl/swing/cancel.gif")));

	}

}
