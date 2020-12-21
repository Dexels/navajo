/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive;

import com.dexels.navajo.reactive.source.topology.FilterTransformerFactory;
import com.dexels.navajo.reactive.source.topology.GroupTransformerFactory;
import com.dexels.navajo.reactive.source.topology.ScanToListTransformerFactory;
import com.dexels.navajo.reactive.source.topology.SinkTransformerFactory;
import com.dexels.navajo.reactive.source.topology.TopicSourceFactory;

public class TopologyReactiveFinder extends CoreReactiveFinder {

	public TopologyReactiveFinder() {
		addReactiveSourceFactory(new TopicSourceFactory(),"topic");
		addReactiveTransformerFactory(new GroupTransformerFactory(),"group");
		addReactiveTransformerFactory(new SinkTransformerFactory(),"sink");
		addReactiveTransformerFactory(new FilterTransformerFactory(),"filter");
		addReactiveTransformerFactory(new ScanToListTransformerFactory(),"scanToList");
	}

}
