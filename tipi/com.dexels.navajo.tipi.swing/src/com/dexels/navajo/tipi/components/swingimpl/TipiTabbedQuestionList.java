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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final long serialVersionUID = 7291498630845123122L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTabbedQuestionList.class);
	
	private Component lastSelectedTab = null;
	private JTabbedPane tabbedPane;

	@Override
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

	@Override
	public void runAsyncInEventThread(Runnable runnable) {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
			SwingUtilities.invokeLater(runnable);
		}
	}

	@Override
	public void runSyncInEventThread(Runnable runnable) {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(runnable);
			} catch (InterruptedException e) {
				logger.error("Error detected",e);
			} catch (InvocationTargetException e) {
				logger.error("Error detected",e);
			}
		}
	}

	@Override
	public Object createContainer() {
		final TipiComponent me = this;
		// JTabbedPane jt = null;
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				tabbedPane = new JTabbedPane();
				tabbedPane.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent ce) {
						try {
							me.performTipiEvent("onTabChanged", null, false);
							lastSelectedTab = tabbedPane.getSelectedComponent();
						} catch (TipiException ex) {
							logger.error("Exception while switching tabs.",ex);
						}
					}
				});
			}
		});

		return tabbedPane;
	}

	@Override
	public void addToContainer(final Object c, final Object constraints) {
		// logger.debug("Adding to TipiTabbedQuestionList container:   "+c+" constraints: "+constraints);
		runSyncInEventThread(new Runnable() {
			@Override
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

	@Override
	public void setGroupValid(boolean valid, TipiBaseQuestionGroup group) {
		super.setGroupValid(valid, group);
		int i = myGroups.indexOf(group);
		if (i < 0) {
			logger.debug("Sh!34#@$!");
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
