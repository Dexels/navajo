/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;

public class ReduceTest {

	
	private static final Logger logger = LoggerFactory.getLogger(ReduceTest.class);

	public ReduceTest() {
	}

	public ImmutableMessage createMessage(int suffix) {
		return ImmutableFactory.empty()
				.with("name", "personname_"+suffix, Property.STRING_PROPERTY)
				.with("address", "address_"+suffix, Property.STRING_PROPERTY);
	}
	
	@Test
	public void testReduce() {
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		Flowable<DataItem> input = Flowable.just(createMessage(1), createMessage(2),createMessage(3)).map(DataItem::of);

		DataItem empty = DataItem.of(ImmutableFactory.empty());
		BiFunction<DataItem, DataItem, DataItem> reducer = (acc,i)->DataItem.of(acc.message().withAddedSubMessage("names", i.message()));
		input.scan(empty, reducer).blockingForEach(e->logger.info("> {}",ImmutableFactory.getInstance().describe(e.message())));
	}
}
