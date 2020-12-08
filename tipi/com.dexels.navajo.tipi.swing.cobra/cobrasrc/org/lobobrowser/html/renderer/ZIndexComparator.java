/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.lobobrowser.html.renderer;

import java.util.Comparator;

class ZIndexComparator implements Comparator {
	//Note: It is assumed that objects don't change their 
	//z-indexes or ordinals after entering the sorted set.
	//They may do so after the sorted set is no longer valid.
	public int compare(Object object1, Object object2) {
		PositionedRenderable element1 = (PositionedRenderable) object1;
		PositionedRenderable element2 = (PositionedRenderable) object2;
		int zIndex1 = element1.renderable.getZIndex();
		int zIndex2 = element2.renderable.getZIndex();
		int diff = zIndex1 - zIndex2;
		if(diff != 0) {
			return diff;
		}
		return element1.ordinal - element2.ordinal;
	}
}
