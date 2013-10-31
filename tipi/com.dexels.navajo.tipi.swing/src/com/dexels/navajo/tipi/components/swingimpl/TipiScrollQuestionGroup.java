/*
 * Created on Feb 17, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.question.TipiBaseQuestionGroup;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingPanel;
import com.dexels.navajo.tipi.internal.TipiEvent;
/**
 * @deprecated
 * @author frank
 *
 */
public class TipiScrollQuestionGroup extends TipiBaseQuestionGroup implements
		TipiSwingComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2198419400200145729L;
	private JScrollPane jp;
	private JPanel jpanel;

	@Override
	public Object createContainer() {
		jp = new JScrollPane();
		jp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		jp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jpanel = new TipiSwingPanel(TipiScrollQuestionGroup.this);

		jpanel.setLayout(new BorderLayout());
		jp.getViewport().add(jpanel);
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return jp;
	}

	@Override
	public void addToContainer(Object c, Object constraints) {
		jpanel.add((Component) c, constraints);
	}

	@Override
	public void removeFromContainer(Object c) {
		jpanel.remove((Component) c);
	}

	@Override
	public void setContainerLayout(Object layout) {
		jpanel.setLayout((LayoutManager) layout);
	}

	public void highLight(Component c, Graphics g) {

	}

	@Override
	public Container getSwingContainer() {
		return (Container) getContainer();
	}

	@Override
	public void refreshLayout() {
		List<TipiComponent> elementList = new ArrayList<TipiComponent>();
		for (int i = 0; i < getChildCount(); i++) {
			TipiComponent current = getTipiComponent(i);
			if (current.isVisibleElement()) {
				removeFromContainer(current.getContainer());
			}
			elementList.add(current);
		}
		for (int i = 0; i < elementList.size(); i++) {
			final TipiComponent current = elementList.get(i);
			if (current.isVisibleElement()) {
				runSyncInEventThread(new Runnable() {
					@Override
					public void run() {
						addToContainer(current.getContainer(),
								current.getConstraints());
					}
				});
			}
		}
	}

	@Override
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

	@Override
	public void runAsyncInEventThread(Runnable r) {
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			SwingUtilities.invokeLater(r);
		}
	}

	public void setCursor(Cursor c) {

	}

	@Override
	public void setPaint(Paint p) {

	}

	public void setWaitCursor(boolean b) {

	}

	@Override
	public void showPopup(MouseEvent e) {

	}

	@Override
	public void animateTransition(TipiEvent te,
			TipiExecutable executableParent, List<TipiExecutable> exe,
			int duration) {

	}

}
