/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingTab;


public class TipiTab extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = -6335980072890167067L;
	
	private TipiSwingTab myTab;

	@Override
	public Object createContainer() {
		myTab = new TipiSwingTab();
		return myTab;
	}

	@Override
	public Object getComponentValue(String name) {
		if (name.equals("visible")) {
			if (getTipiParent() instanceof TipiTabs)
			{
				TipiTabs tabs = (TipiTabs) getTipiParent();
				return tabs.isChildVisible(this);
			}
			else
			{
				throw new UnsupportedOperationException("Cannot ask visible of a TipiTab which is a child of a non-TipiTabs component!");
			}
		}

		return super.getComponentValue(name);
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if (name.equals("tabIcon")) {
			// Don't know why. The propertyChangeListener seems to miss it when
			// the icon is being set to null.
			myTab.setIconUrl(object);
		}
		if (name.equals("tabText")) {
			// Don't know why. The propertyChangeListener seems to miss it when
			// the icon is being set to null.
			myTab.setTabText((String)object);
		}
		if (name.equals("tabToolTip")) {
			// Don't know why. The propertyChangeListener seems to miss it when
			// the icon is being set to null.
			myTab.setTabTooltip((String)object);
		}
		if (name.equals("visible")) {
			throw new UnsupportedOperationException("Setting visible is not supported on a TipiTab. Use 'showTab' of its parent instead.");
		}
		super.setComponentValue(name, object);
	}

}
