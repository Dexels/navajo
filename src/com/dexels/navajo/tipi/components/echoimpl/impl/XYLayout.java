package com.dexels.navajo.tipi.components.echoimpl.impl;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.LayoutData;
import echopointng.able.Insetable;
import echopointng.able.Positionable;
import echopointng.able.Sizeable;

public class XYLayout extends DefaultTipiLayoutManager {

	public void layoutContainer(Component parent) {
		for (int i = 0; i < parent.getComponentCount(); i++) {
			Component currentChild = parent.getComponent(i);
			LayoutData l = currentChild.getLayoutData();
			layoutChild(parent, currentChild, l);
		}
	}

	private void layoutChild(Component parent, Component currentChild,
			LayoutData l) {
		XYLayoutConstraint xyl = (XYLayoutConstraint) l;
		if (parent instanceof Sizeable) {
			System.err.println("Size: " + ((Sizeable) parent).getWidth()
					+ " / " + ((Sizeable) parent).getHeight());
		}
		// System.err.println("Layout: "+parent.);
		if (currentChild instanceof Positionable) {
			Positionable p = (Positionable) currentChild;
			if (xyl == null) {
				System.err.println("No constraint. Skipping");
				return;
			}
			p.setLeft(new Extent(xyl.getX(), Extent.PX));
			p.setTop(new Extent(xyl.getY(), Extent.PX));
			p.setPosition(Positionable.ABSOLUTE);
		} else {
			if (currentChild instanceof Insetable) {
				Insetable p = (Insetable) currentChild;
				if (xyl == null) {
					System.err.println("No constraint. Skipping");
					return;
				}
				p.setOutsets(new Insets(new Extent(xyl.getX(), Extent.PX),
						new Extent(xyl.getY(), Extent.PX), new Extent(0),
						new Extent(0)));
				// p.setTop(new Extent(xyl.getY(),Extent.PX));
				// p.setPosition(Positionable.RELATIVE);
			}

		}
		if (currentChild instanceof Sizeable) {
			System.err.println("Hij is sizeable, ouwe");
			Sizeable s = (Sizeable) currentChild;
			if (xyl != null && xyl.getW() != -1 && xyl.getH() != -1) {
				s.setWidth(new Extent(xyl.getW(), Extent.PX));
				s.setHeight(new Extent(xyl.getH(), Extent.PX));
			}
		}
	}

}
