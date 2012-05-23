package org.akrogen.tkui.css.tipi.properties;

/** Copyright (c) 2006 Timothy Wall  All Rights Reserved */
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

/**
 * Provide a method for consistently augmenting the appearance of a given
 * component by painting something on it <i>after</i> the component itself gets
 * painted. If not explicitly removed via {@link #dispose}, an instance of this
 * object will live as long as its target component.
 * <p>
 * By default, the decorator matches the location and size of the decorated
 * component, but the bounds can be adjusted by overriding
 * {@link #getDecorationBounds}.
 * <p>
 * The decoration is clipped to the bounds set on the decoration, which does not
 * necessarily need to be the same as the decorated component bounds. The
 * decoration may extend beyond the decorated component bounds, or it may be
 * reduced to a smaller region.
 */
// TODO: should probably do some locking on Component.getTreeLock()
// TODO: synch component cursor; unfortunately we can't detect changes
// and we need to change the cursor if the decoration exceeds the component's
// bounds
public abstract class AbstractComponentDecorator {

	public static final int TOP = 0;
	// Disabled for now, since it doesn't work properly
	private static final int BOTTOM = -1;
	/**
	 * Account for the difference between the decorator actual origin and the
	 * logical origin we want to pass to the {@link #paint} method.
	 */
	private Point originOffset = new Point(0, 0);

	private Painter painter;
	private JComponent component;
	private Container parent;
	private Listener listener;
	private int layerOffset;
	private int position;
	private Rectangle bounds;

	/** Create a decorator for the given component. */
	public AbstractComponentDecorator(JComponent c) {
		this(c, 1);
	}

	/**
	 * Create a decorator for the given component, indicating the layer offset
	 * from the target component. Negative values mean the decoration is painted
	 * <em>before</em> the target component is painted.
	 */
	public AbstractComponentDecorator(JComponent c, int layerOffset) {
		this(c, layerOffset, TOP);
	}

	/**
	 * Create a decorator with the given position within its layer. Use
	 * {@link #TOP} to cover other decorations, or {@link #BOTTOM} to be covered
	 * by other decorations.
	 * <p>
	 * WARNING: BOTTOM doesn't currently work, probably a JLayeredPane bug in
	 * either the code or documentation.
	 * 
	 * @see JLayeredPane
	 */
	public AbstractComponentDecorator(JComponent c, int layerOffset,
			int position) {
		component = c;
		this.layerOffset = layerOffset;
		this.position = position;
		this.bounds = null;
		parent = c.getParent();
		painter = new Painter();
		listener = new Listener();
		component.addHierarchyListener(listener);
		component.addComponentListener(listener);
		component.addPropertyChangeListener(listener);
		attach();
	}

	/**
	 * Set the text to be displayed when the mouse is over the decoration.
	 * 
	 * @see JComponent#setToolTipText(String)
	 */
	public void setToolTipText(String text) {
		painter.setToolTipText(text);
	}

	/**
	 * Return the currently set default tooltip text.
	 * 
	 * @see JComponent#setToolTipText
	 */
	public String getToolTipText() {
		return painter.getToolTipText();
	}

	/**
	 * Provide for different tool tips depending on the actual location over the
	 * decoration. Note that if you <em>only</em> override this method, you
	 * must also invoke {@link #setToolTipText(String)} with a non-<span
	 * class="javakeyword">null</span> argument.
	 * 
	 * @see JComponent#getToolTipText(MouseEvent)
	 */
	public String getToolTipText(MouseEvent e) {
		return getToolTipText();
	}

	/** Use this to change the visibility of the decoration. */
	public void setVisible(boolean visible) {
		painter.setVisible(visible);
	}

	protected void attach() {
		Window w = SwingUtilities.getWindowAncestor(component);
		if (w instanceof RootPaneContainer) {
			JLayeredPane lp = ((RootPaneContainer) w).getLayeredPane();
			Component layeredChild = component;
			int layer = JLayeredPane.DRAG_LAYER.intValue();
			if (this instanceof BackgroundPainter) {
				layer = ((BackgroundPainter) this).layer;
				painter.setDecoratedLayer(layer);
			} else if (layeredChild == lp) {
				// Is this the best layer to use?
				painter.setDecoratedLayer(layer);
			} else {
				while (layeredChild != null && layeredChild.getParent() != lp) {
					layeredChild = layeredChild.getParent();
				}
				// Bug correction, manage case when
				// layeredChild is null;
				if (layeredChild == null)
					return;
				int base = lp.getLayer(layeredChild);
				// NOTE: JLayeredPane doesn't properly repaint an overlapping
				// child when an obscured child calls repaint() if the two
				// are in the same layer, so we use the next-higher layer
				// instead of simply using a different position within the
				// layer.
				layer = base + layerOffset;
				if (layerOffset < 0) {
					BackgroundPainter bp = (BackgroundPainter) lp
							.getClientProperty(BackgroundPainter.key(base));
					if (bp == null) {
						bp = new BackgroundPainter(lp, base);
					}
				}
				painter.setDecoratedLayer(base);
			}
			lp.add(painter, new Integer(layer), position);
		} else {
			// Always detach when the target component's window is null
			// or is not a suitable container,
			// otherwise we might prevent GC of the component
			Container parent = painter.getParent();
			if (parent != null) {
				parent.remove(painter);
			}
		}
		// Track size changes in any viewport parent
		if (parent != null) {
			parent.removeComponentListener(listener);
		}
		parent = component.getParent();
		if (parent != null) {
			parent.addComponentListener(listener);
		}
		synch();
	}

	/**
	 * Ensure the size of the decorator matches the current decoration bounds
	 * with appropriate clipping to viewports.
	 */
	protected void synch() {
		Container painterParent = painter.getParent();
		if (painterParent != null) {
			Rectangle visible = getVisibleRect(getComponent());
			Rectangle decorated = getDecorationBounds();
			// Amount we have to translate the Graphics context
			originOffset.x = decorated.x;
			originOffset.y = decorated.y;
			// Use the painter bounds to clip to any viewport bounds;
			// since doing so changes the actual origin, adjust our
			// origin offset accordingly
			Rectangle clipped = decorated.intersection(visible);
			if (decorated.x < visible.x)
				originOffset.x += visible.x - decorated.x;
			if (decorated.y < visible.y)
				originOffset.y += visible.y - decorated.y;
			Point pt = SwingUtilities.convertPoint(component, clipped.x,
					clipped.y, painterParent);
			if (clipped.width <= 0 || clipped.height <= 0) {
				setVisible(false);
			} else {
				setPainterBounds(pt.x, pt.y, clipped.width, clipped.height);
				setVisible(true);
			}
		}
		painter.revalidate();
		painter.repaint();
	}

	private Rectangle getVisibleRect(JComponent component) {
		Rectangle visible = component.getVisibleRect();
		if (visible.x != 0 || visible.y != 0
				|| visible.width != component.getWidth()
				|| visible.height != component.getHeight()) {
			return visible;
		}
		Container parent = component.getParent();
		if (parent instanceof JComponent) {
			visible = ((JComponent) parent).getVisibleRect();
			visible.x -= component.getX();
			visible.y -= component.getY();
		}
		return visible;
	}

	/** Change the current decorated bounds. */
	public void setDecorationBounds(int x, int y, int w, int h) {
		if (this.bounds == null) {
			this.bounds = new Rectangle(x, y, w, h);
		} else {
			this.bounds.setBounds(x, y, w, h);
		}
		synch();
	}

	/** Change the current decorated bounds. */
	public void setDecorationBounds(Rectangle bounds) {
		if (this.bounds == null) {
			this.bounds = bounds;
		} else {
			this.bounds.setBounds(bounds);
		}
		synch();
	}

	/**
	 * Return the bounds, relative to the decorated component, of the
	 * decoration. The default covers the entire component. Note that this
	 * method will be called from the constructor, so be careful when
	 * referencing derived class state.
	 */
	protected Rectangle getDecorationBounds() {
		if (bounds == null) {
			return new Rectangle(0, 0, getComponent().getWidth(),
					getComponent().getHeight());
		}
		return bounds;
	}

	private void setPainterBounds(int x, int y, int w, int h) {
		painter.setLocation(x, y);
		painter.setSize(w, h);
		repaint();
	}

	protected JComponent getComponent() {
		return component;
	}

	protected JComponent getPainter() {
		return painter;
	}

	/** Force a refresh of the underlying component and its decoration. */
	public void repaint() {
		component.repaint();
		painter.repaint();
	}

	/** Stop decorating. */
	public void dispose() {
		component.removeHierarchyListener(listener);
		component.removeComponentListener(listener);
		component.removePropertyChangeListener(listener);
		if (parent != null) {
			parent.removeComponentListener(listener);
		}
		Container painterParent = painter.getParent();
		if (painterParent != null) {
			Rectangle bounds = painter.getBounds();
			painterParent.remove(painter);
			painterParent.repaint(bounds.x, bounds.y, bounds.width,
					bounds.height);
		}
		component.repaint();
		component = null;
	}

	/**
	 * Define the decoration's appearance. The point (0,0) represents the upper
	 * left corner of the decorated component. The default clip mask will be the
	 * extents of the decoration bounds, as indicated by
	 * {@link #getDecorationBounds()}, which defaults to the decorated
	 * component bounds.
	 */
	public abstract void paint(Graphics g);

	public String toString() {
		return super.toString() + " on " + getComponent();
	}

	private static Field nComponents;
	static {
		try {
			nComponents = Container.class.getDeclaredField("ncomponents");
			nComponents.setAccessible(true);
		} catch (Exception e) {
			nComponents = null;
		}
	}

	private static boolean useSimpleBackground() {
		return nComponents == null;
	}

	/** Used to hook into the Swing painting architecture. */
	private class Painter extends JComponent {
		private int base;

		public JComponent getComponent() {
			return AbstractComponentDecorator.this.getComponent();
		}

		public void setDecoratedLayer(int base) {
			this.base = base;
		}

		public int getDecoratedLayer() {
			return base;
		}

		public boolean isBackgroundDecoration() {
			return layerOffset < 0;
		}

		public void paint(Graphics g) {
			if (!component.isShowing())
				return;
			Graphics2D g2d = (Graphics2D) g;
			AffineTransform xform = g2d.getTransform();
			try {
				g2d.translate(-originOffset.x, -originOffset.y);
				AbstractComponentDecorator.this.paint(g);
			} finally {
				g2d.setTransform(xform);
			}
		}

		public String getToolTipText(MouseEvent e) {
			return AbstractComponentDecorator.this.getToolTipText(e);
		}

		public String toString() {
			return "Painter for " + AbstractComponentDecorator.this;
		}
	}

	/**
	 * Provides a shared background painting mechanism for multiple decorations.
	 * This ensures that the background is only painted once if more than one
	 * background decorator is applied.
	 */
	private static class BackgroundPainter extends AbstractComponentDecorator {
		private static String key(int layer) {
			return "backgroundPainter" + layer;
		}

		private String key;
		private int layer;

		public BackgroundPainter(JLayeredPane p, int layer) {
			super(p, 0, TOP);
			this.layer = layer;
			key = key(layer);
			p.putClientProperty(key, this);
		}

		private int hideChildren(Container c) {
			if (c == null)
				return 0;
			int value = c.getComponentCount();
			try {
				nComponents.set(c, new Integer(0));
			} catch (Exception e) {
				return c.getComponentCount();
			}
			return value;
		}

		private void restoreChildren(Container c, int count) {
			if (c != null) {
				try {
					nComponents.set(c, new Integer(count));
				} catch (Exception e) {
				}
			}
		}

		private void paintBackground(Graphics g, Component parent, JComponent jc) {
			int x = jc.getX();
			int y = jc.getY();
			int w = jc.getWidth();
			int h = jc.getHeight();
			paintBackground(g.create(x, y, w, h), jc);
		}

		private void paintBackground(Graphics g, JComponent jc) {
			if (jc.isOpaque()) {
				if (useSimpleBackground()) {
					g.setColor(jc.getBackground());
					g.fillRect(0, 0, jc.getWidth(), jc.getHeight());
				} else {
					int count = hideChildren(jc);
					boolean db = jc.isDoubleBuffered();
					if (db)
						jc.setDoubleBuffered(false);
					jc.paint(g);
					if (db)
						jc.setDoubleBuffered(true);
					restoreChildren(jc, count);
				}
			}
			Component[] kids = jc.getComponents();
			for (int i = 0; i < kids.length; i++) {
				if (kids[i] instanceof JComponent) {
					paintBackground(g, jc, (JComponent) kids[i]);
				}
			}
		}

		private List findOpaque(Component root) {
			List list = new ArrayList();
			if (root.isOpaque() && root instanceof JComponent) {
				list.add(root);
				((JComponent) root).setOpaque(false);
			}
			if (root instanceof Container) {
				Component[] kids = ((Container) root).getComponents();
				for (int i = 0; i < kids.length; i++) {
					list.addAll(findOpaque(kids[i]));
				}
			}
			return list;
		}

		private List findDoubleBuffered(Component root) {
			List list = new ArrayList();
			if (root.isDoubleBuffered() && root instanceof JComponent) {
				list.add(root);
				((JComponent) root).setDoubleBuffered(false);
			}
			if (root instanceof Container) {
				Component[] kids = ((Container) root).getComponents();
				for (int i = 0; i < kids.length; i++) {
					list.addAll(findDoubleBuffered(kids[i]));
				}
			}
			return list;
		}

		private void paintForeground(Graphics g, JComponent jc) {
			List opaque = findOpaque(jc);
			List db = findDoubleBuffered(jc);
			jc.paint(g);
			for (Iterator i = opaque.iterator(); i.hasNext();) {
				((JComponent) i.next()).setOpaque(true);
			}
			for (Iterator i = db.iterator(); i.hasNext();) {
				((JComponent) i.next()).setDoubleBuffered(true);
			}
		}

		/** Walk the list of "background" decorators and paint them. */
		public void paint(Graphics g) {

			JLayeredPane lp = (JLayeredPane) getComponent();
			Component[] kids = lp.getComponents();
			// Construct an area of the intersection of all decorators
			Area area = new Area();
			List painters = new ArrayList();
			List components = new ArrayList();
			for (int i = kids.length - 1; i >= 0; i--) {
				if (kids[i] instanceof Painter) {
					Painter p = (Painter) kids[i];
					if (p.isBackgroundDecoration()
							&& p.getDecoratedLayer() == layer && p.isShowing()) {
						painters.add(p);
						area.add(new Area(p.getBounds()));
					}
				} else if (lp.getLayer(kids[i]) == layer
						&& kids[i] instanceof JComponent) {
					components.add(kids[i]);
				}
			}
			if (painters.size() == 0) {
				dispose();
				return;
			}
			g.setClip(area);

			// Paint background for that area
			for (Iterator i = components.iterator(); i.hasNext();) {
				JComponent c = (JComponent) i.next();
				paintBackground(g, lp, c);
			}

			// Paint the bg decorators
			for (Iterator i = painters.iterator(); i.hasNext();) {
				Painter p = (Painter) i.next();
				p.paint(g.create(p.getX(), p.getY(), p.getWidth(), p
						.getHeight()));
			}
			// Paint foreground for the area
			for (Iterator i = components.iterator(); i.hasNext();) {
				JComponent c = (JComponent) i.next();
				paintForeground(g.create(c.getX(), c.getY(), c.getWidth(), c
						.getHeight()), c);
			}
		}

		public void dispose() {
			getComponent().putClientProperty(key, null);
			super.dispose();
		}

		public String toString() {
			return key + " on " + getComponent();
		}
	}

	/** Tracks changes to component configuration. */
	private final class Listener extends ComponentAdapter implements
			HierarchyListener, PropertyChangeListener {
		public void hierarchyChanged(HierarchyEvent e) {
			if ((e.getChangeFlags() & HierarchyEvent.PARENT_CHANGED) != 0) {
				attach();
			}
		}

		public void propertyChange(PropertyChangeEvent e) {
			if (JLayeredPane.LAYER_PROPERTY.equals(e.getPropertyName())) {
				attach();
			}
		}

		public void componentMoved(ComponentEvent e) {
			// FIXME figure out why attach works and synch doesn't
			// when painting a selection marquee over a decorated background
			attach();
		}

		public void componentResized(ComponentEvent e) {
			// FIXME figure out why attach works and synch doesn't
			// when painting a selection marquee over a decorated background
			attach();
		}

		public void componentHidden(ComponentEvent e) {
			setVisible(false);
		}

		public void componentShown(ComponentEvent e) {
			setVisible(true);
		}
	}
}
