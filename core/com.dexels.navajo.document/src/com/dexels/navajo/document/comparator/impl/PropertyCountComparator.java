/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.comparator.impl;

import java.util.Comparator;

import com.dexels.navajo.document.Message;

class PropertyCountComparator implements Comparator<Message> {


	@Override
	public int compare(Message o1, Message o2) {
		if(o1==null || o2==null) {
			return 0;
		}
		int count1 = o1.getProperties().size();
		int count2 = o2.getProperties().size();
		if(count1==count2) {
			return 0;
		}
		return count1-count2;
	}

}