package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiTabbable;
import com.dexels.navajo.tipi.internal.TipiEvent;


public class TipiTabs extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = 6765450974743393182L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTabs.class);
	
	private Component lastSelectedTab = null;

	private final Map<Component, Boolean> visibilityMap = new HashMap<Component, Boolean>();
	private final Map<Component, String> constraintMap = new HashMap<Component, String>();
	private final List<Component> childList = new LinkedList<Component>();

	// horrible, but necessary.
	private boolean isRebuilding = false;

	public Object createContainer() {
		// final TipiComponent me = this;

		final JTabbedPane jt = new JTabbedPane() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1661243154472687618L;

			private Dimension checkMax(Dimension preferredSize) {
				Dimension maximumSize = getMaximumSize();
				if (maximumSize == null) {
					return preferredSize;
				}
				return new Dimension(Math.min(preferredSize.width,
						maximumSize.width), Math.min(preferredSize.height,
						maximumSize.height));
			}

			private Dimension checkMin(Dimension preferredSize) {
				Dimension minimumSize = getMinimumSize();
				if (minimumSize == null) {
					return preferredSize;
				}
				return new Dimension(Math.max(preferredSize.width,
						minimumSize.width), Math.max(preferredSize.height,
						minimumSize.height));
			}

			public Dimension checkMaxMin(Dimension d) {
				return checkMin(checkMax(d));
			}

			public Dimension getPreferredSize() {
				return checkMaxMin(super.getPreferredSize());
			}
		};
		// jt.setBackground(new Color(0.0f,0.8f,0.0f,0.2f));
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);

		jt.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ce) {
				if (isRebuilding) {
					return;
				}
				setWaitCursor(true);
				TipiTabs.this.performTipiEvent("onTabChanged", null, false,
						new Runnable() {

							public void run() {
								setWaitCursor(false);

							}
						});

				if (lastSelectedTab == null) {
					logger.debug("last selected was null");

				}
				lastSelectedTab = jt.getSelectedComponent();
				if (lastSelectedTab == null) {
					logger.debug("last selected is null");
					getAttributeProperty("selectedindex").setAnyValue(-1);
				} else {
					getAttributeProperty("selectedindex").setAnyValue(
							jt.getSelectedIndex());
					lastSelectedTab.doLayout();
				}
				if (myContext.getTopLevel() instanceof TipiApplet) {
//					TipiApplet ta = (TipiApplet) myContext.getTopLevel();
//					JPanel component = (JPanel) ta.getContentPane()
//							.getComponent(0);

				}
			}
		});
		return jt;
	}

	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event) {
		if (name.equals("enableTab")) {

			Operand path = compMeth.getEvaluatedParameter("tabname", event);
			Operand value = compMeth.getEvaluatedParameter("value", event);
			String tabName = (String) path.value;
			final boolean enabled = ((Boolean) value.value).booleanValue();
			final TipiComponent t = getTipiComponent(tabName);
			if (t != null) {
				runSyncInEventThread(new Runnable() {
					public void run() {
						Container c = (Container) t.getContainer();
						JTabbedPane p = (JTabbedPane) getContainer();

						int index = p.indexOfComponent(c);
						p.setEnabledAt(index, enabled);
						if (!enabled && p.getSelectedIndex() == index) {
							switchToAnotherTab();
						}
					}
				});
			} else {
				logger.debug("Sorry could not find tab: " + tabName);
			}
		}
		if (name.equals("showTab")) {

			Operand component = compMeth.getEvaluatedParameter("component",
					event);
			Operand value = compMeth.getEvaluatedParameter("value", event);

			final boolean visible = ((Boolean) value.value).booleanValue();
			final TipiComponent t = (TipiComponent) component.value;
			if (t != null) {
				runSyncInEventThread(new Runnable() {
					public void run() {
						// Container c = (Container) t.getContainer();
						// JTabbedPane p = (JTabbedPane) getContainer();

						setChildVisible(t, visible);
						rebuildTabs();
					}
				});
			} else {
				logger.debug("Sorry could not find tab");
			}
		}
	}

	// current tab is disabled. Try to switch to the lastselected, otherwise to
	// the first enabled tab.
	private final void switchToAnotherTab() {
		JTabbedPane p = (JTabbedPane) getContainer();
		int lastIndex = p.indexOfComponent(lastSelectedTab);
		if (lastIndex >= 0 && lastIndex < p.getTabCount()
				&& p.isEnabledAt(lastIndex)) {
			p.setSelectedIndex(lastIndex);
			return;
		}
		for (int i = 0; i < p.getTabCount(); i++) {
			if (p.isEnabledAt(i)) {
				p.setSelectedIndex(i);
				return;
			}
		}
	}

	public void addToContainer(final Object c, final Object constraints) {
		runSyncInEventThread(new Runnable() {

			public void run() {
				String stringConstraints = (String) constraints;
				if (stringConstraints == null) {
					stringConstraints = "Tab#" + (visibilityMap.size() + 1);
				}
				JComponent jc = (JComponent) c;
				visibilityMap.put(jc, true);
				childList.add(jc);
				constraintMap.put(jc, stringConstraints);
				Dimension d = jc.getPreferredSize();
				if (d != null) {
					if (d.width <= 0 || d.height <= 0) {
						jc.setPreferredSize(null);
					}
					jc.revalidate();

				}
				final int nextIndex = childList.size() - 1;
				final JTabbedPane pane = (JTabbedPane) getContainer();
				if (jc instanceof TipiTabbable) {
					TipiTabbable tb = (TipiTabbable) jc;
					Icon tabIcon = tb.getTabIcon();
					Color back = tb.getTabBackgroundColor();
					Color fore = tb.getTabForegroundColor();
					String tip = tb.getTabTooltip();
					String text = tb.getTabText();
					if(text==null) {
						text = stringConstraints;
					}
					if (tabIcon != null) {
						pane.addTab(text, tabIcon, jc, tip);
					} else {
						pane.addTab(text, jc);
					}
					if (back != null) {
						pane.setBackgroundAt(nextIndex, back);
					}
					if (fore != null) {
						pane.setBackgroundAt(nextIndex, fore);
					}
					tb.setIndex(nextIndex);
					tb.addPropertyChangeListener(new PropertyChangeListener() {

						public void propertyChange(final PropertyChangeEvent evt) {
							if (evt.getPropertyName().equals("tabIcon")) {
								runSyncInEventThread(new Runnable() {
									public void run() {
										pane.setIconAt(nextIndex,
												(Icon) evt.getNewValue());
									}
								});
							}

						}
					});

				} else {
					pane.addTab(stringConstraints, jc);
				}

				pane.setEnabledAt(pane.indexOfComponent(jc), jc.isEnabled());
				if (lastSelectedTab == null) {
					lastSelectedTab = jc;
				}
			}
		});

	}

	public void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
		if (name.equals("selected")) {
			String sel = (String) object;
			final TipiComponent tc = getTipiComponent(sel);
			runSyncInEventThread(new Runnable() {
				public void run() {
					((JTabbedPane) getContainer())
							.setSelectedComponent((Component) (tc
									.getContainer()));
				}
			});
		}
		if (name.equals("selectedindex")) {
			final Integer sel = (Integer) object;
			runSyncInEventThread(new Runnable() {
				public void run() {
					((JTabbedPane) getContainer()).setSelectedIndex(sel
							.intValue());
				}
			});
		}
		if (name.equals("placement")) {
			final String sel = (String) object;
			runSyncInEventThread(new Runnable() {
				public void run() {
					setTabPlacement(sel);
				}
			});
			// ((JTabbedPane)getContainer()).setSelectedComponent(tc.getContainer
			// ());
		}
		/** @todo Override this com.dexels.navajo.tipi.TipiComponent method */
	}

	public void setTabPlacement(String sel) {
		int placement = -1;
		if (sel.equals("top")) {
			placement = SwingConstants.TOP;
		}
		if (sel.equals("bottom")) {
			placement = SwingConstants.BOTTOM;
		}
		if (sel.equals("left")) {
			placement = SwingConstants.LEFT;
		}
		if (sel.equals("right")) {
			placement = SwingConstants.RIGHT;
		}
		((JTabbedPane) getContainer()).setTabPlacement(placement);
	}

	public Object getComponentValue(String name) {
		/** @todo Override this com.dexels.navajo.tipi.TipiComponent method */
		if (name.equals("selected")) {
			Component c = ((JTabbedPane) getContainer()).getSelectedComponent();
			TipiComponent tc = getChildByContainer(c);
			return tc;
		}
		if (name.equals("lastselected")) {
			TipiComponent tc = getChildByContainer(lastSelectedTab);
			return tc;
		}
		if (name.equals("selectedindex")) {
			return new Integer(
					((JTabbedPane) getContainer()).getSelectedIndex());
		}
		if (name.equals("lastselectedindex")) {
			return new Integer(getIndexOfTab(lastSelectedTab));
		}

		return super.getComponentValue(name);
	}

	private int getIndexOfTab(Component c) {
		JTabbedPane pane = (JTabbedPane) getContainer();
		for (int i = 0; i < pane.getComponentCount(); i++) {
			if (pane.getComponent(i) == c) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void removeFromContainer(Object c) {
		visibilityMap.remove(c);
		childList.remove(c);
		constraintMap.remove(c);

		super.removeFromContainer(c);
	}

	public void setChildVisible(TipiComponent child, boolean visible) {
		Component c = (Component) child.getContainer();
		visibilityMap.put(c, visible);
	}

	public void rebuildTabs() {
		runSyncInEventThread(new Runnable() {

			public void run() {
				isRebuilding = true;
				try {
					JTabbedPane jt = (JTabbedPane) getContainer();
					jt.removeAll();

					for (Component c : childList) {
						boolean isVisible = visibilityMap.get(c);
						if (isVisible) {
							jt.addTab(constraintMap.get(c), c);
						}
					}
					if (lastSelectedTab != null) {
						logger.debug("last selected null!");
						boolean found = false;
						for (int i = 0; i < jt.getComponentCount(); i++) {
							if (jt.getComponent(i) == lastSelectedTab) {
								found = true;
								break;
							}
						}
						if (found) {
							jt.setSelectedComponent(lastSelectedTab);
						}
					} else {
						jt.setSelectedIndex(0);
					}
				} finally {
					isRebuilding = false;
				}
			}
		});

	}

	// public void load(XMLElement elm, XMLElement instance, TipiContext
	// context) throws com.dexels.navajo.tipi.TipiException {
	// Vector children = elm.getChildren();
	// for (int i = 0; i < children.size(); i++) {
	// XMLElement child = (XMLElement) children.elementAt(i);
	// if (child.getName().equals("tipi-instance")) {
	// String windowName = (String)child.getAttribute("name");
	// String title = (String)child.getAttribute("title");
	// Tipi t = addTipiInstance(context,null,child);
	// JTabbedPane p = (JTabbedPane)getContainer();
	// p.addTab(title, t.getContainer());
	// }
	// }
	//
	// super.load(elm,instance, context);
	// }
}
