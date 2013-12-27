package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.interpolation.SplineInterpolator;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingViewport;
import com.dexels.navajo.tipi.internal.TipiEvent;

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

public class TipiSlottedViewport extends TipiSwingDataComponentImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8043914224991351974L;

	public TipiSlottedViewport() {
	}

	// private JPanel left = null;
	// private JPanel right = null;
//	private int axis = BoxLayout.X_AXIS;
//	private LayoutManager layout;
	private JPanel clientPanel;
	private TipiSwingViewport view;
	private Component currentComponent;

	// private int width = 1;
	// private int height = 2;
	//
	@Override
	public Object createContainer() {
		view = new TipiSwingViewport();
		// left = new JPanel();
		// right = new JPanel();
		// left.setLayout(new BorderLayout());
		// right.setLayout(new BorderLayout());
		clientPanel = new JPanel();
		// layout = new GridLayout();
		// layout = new BoxLayout(clientPanel, axis);
		// clientPanel.setLayout(layout);
		// clientPanel.add(left);
		// clientPanel.add(right);
		view.setOpaque(false);
		view.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				updateClientSize(view);
			}
		});

		view.setView(clientPanel);
		return view;
	}

	@Override
	public void setContainerLayout(final Object layout) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				clientPanel.setLayout((LayoutManager) layout);
			}
		});
	}

	@Override
	public void removeFromContainer(Object c) {
		clientPanel.remove((Component) c);
	}

	protected void updateClientSize(TipiSwingViewport view) {
		Dimension d = view.getSize();
//		Dimension e = new Dimension(d.width * view.getGridWidth(), d.height
//				* view.getGridHeight());
		Component[] ccc = clientPanel.getComponents();
		for (int i = 0; i < ccc.length; i++) {
			if (ccc[i] instanceof JComponent) {
				JComponent jc = (JComponent) ccc[i];
				jc.setPreferredSize(d);
			}
		}
		// clientPanel.setPreferredSize(e);

		clientPanel.doLayout();
	}

	@Override
	public void addToContainer(final Object c, final Object constraints) {
		// if (constraints == null) {
		// throw new
		// IllegalArgumentException("Constraint required when adding to a viewport");
		// }
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				clientPanel.add((Component) c, constraints);
				updateClientSize(view);
				currentComponent = (Component) c;
				refreshView();
				if (getChildCount() == 1) {
					view.setViewPosition(((Component) c).getLocation());

				}
			}
		});

		// String constr = constraints.toString();
		// Component cc = (Component) c;
		// if ("bottom".equals(constr)) {
		// setAxis("vertical");
		// right.add(cc, BorderLayout.CENTER);
		// }
		// if ("right".equals(constr)) {
		// setAxis("horizontal");
		// right.add(cc, BorderLayout.CENTER);
		// }
		// if ("top".equals(constr)) {
		// setAxis("vertical");
		// left.add(cc, BorderLayout.CENTER);
		// }
		// if ("left".equals(constr)) {
		// setAxis("horizontal");
		// left.add(cc, BorderLayout.CENTER);
		// }
		// left.setMinimumSize(minimumSize);
		// right.setMinimumSize(minimumSize);
		// left.setPreferredSize(minimumSize);
		// right.setPreferredSize(minimumSize);
	}

	//
	// private void setAxis(String ax) {
	// if ("vertical".equals(ax)) {
	// if (BoxLayout.Y_AXIS != axis) {
	// axis = BoxLayout.Y_AXIS;
	// updateLayout();
	// }
	// }
	// if ("horizontal".equals(ax)) {
	//
	// if (BoxLayout.X_AXIS != axis) {
	// axis = BoxLayout.X_AXIS;
	// updateLayout();
	// }
	// }
	// }

	/**
 * 
 */
	// private void updateLayout() {
	// clientPanel.setLayout(new BoxLayout(clientPanel, axis));
	// }
	@Override
	public void setComponentValue(String name, final Object object) {
		super.setComponentValue(name, object);
		// if (name.equals("x")) {
		// runSyncInEventThread(new Runnable() {
		// public void run() {
		// logger.debug("Setx: "+object);
		// view.setViewPosition(new Point((Integer) object, 0));
		// }
		// });
		// }
		//
		// if (name.equals("y")) {
		// runSyncInEventThread(new Runnable() {
		// public void run() {
		// logger.debug("Sety: "+object);
		// view.setViewPosition(new Point(0, (Integer) object));
		// }
		// });
		//
		// }
		// if (name.equals("dividersize")) {
		// int size = ( (Integer) object).intValue();
		// ((TipiSwingSplitPane) getContainer()).setDividerSize(size);
		// }
		// if (name.equals("onetouchexpandable")) {
		// boolean otex = ( (Boolean) object).booleanValue();
		// ((TipiSwingSplitPane) getContainer()).setOneTouchExpandable(otex);
		// }

	}

	@Override
	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
		if (name.equals("show")) {
			final TipiComponent tc = (TipiComponent) compMeth
					.getEvaluatedParameterValue("component", event);
			currentComponent = (Component) tc.getContainer();
			refreshView();

		}
		if (name.equals("showAnimated")) {
			final TipiComponent tc = (TipiComponent) compMeth
					.getEvaluatedParameterValue("component", event);
			int duration = (Integer) compMeth.getEvaluatedParameterValue(
					"duration", event);
			currentComponent = (Component) tc.getContainer();

			Animator myAnimator = new Animator(duration);
			myAnimator.setInterpolator(new SplineInterpolator(0f, 0.5f, 0.6f,
					1f));
			myAnimator.setAcceleration(0.45f);
			myAnimator.setDeceleration(0.45f);
			final Point initial = view.getViewPosition();
			final Point targetPos = currentComponent.getLocation();
			myAnimator.addTarget(new TimingTarget() {

				@Override
				public void begin() {
				}

				@Override
				public void end() {
				}

				@Override
				public void repeat() {
				}

				@Override
				public void timingEvent(float e) {
					view.setViewPosition(interpolate(initial, targetPos, e));
				}
			});

			myAnimator.start();
			refreshView();

		}
	}

	public Point interpolate(Point start, Point end, float fraction) {
		int resX = start.x + (int) ((end.x - start.x) * fraction);
		int resY = start.y + (int) ((end.y - start.y) * fraction);
		Point point = new Point(resX, resY);
		// logger.debug("point: "+point);
		return point;

	}

	private void refreshView() {
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				if (currentComponent != null) {
//					Point p = getLocation(currentComponent);
					// view.setViewPosition(p);
				} else {
					// view.setViewPosition(new Point(0,0));
				}
			}
		});
	}


}
