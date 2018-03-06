package com.dexels.navajo.reactive;

import org.junit.Test;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;

public class ReduceTest {

	public ReduceTest() {
		// TODO Auto-generated constructor stub
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
		BiFunction<DataItem, DataItem, DataItem> reducer = (acc,i)->{
			return DataItem.of(acc.message().withAddedSubMessage("names", i.message()));
		};
		
		input.scan(empty, reducer).blockingForEach(e->{
			System.err.println("> "+ImmutableFactory.getInstance().describe(e.message()));
		});
	}
}
