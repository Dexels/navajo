package com.dexels.navajo.document.stream.json;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.davidmoten.rx2.Bytes;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class JSONObservableTest {

	@Test
	public void testRxJSON() {
		long l = Bytes.from(JSONObservableTest.class.getClassLoader().getResourceAsStream("person.json"))
			.lift(JSON.parseFlowable(10))
			.flatMap(e->e)
			.doOnNext(e->System.err.println("Event: "+e))
					
			.count()
			.blockingGet();
		Assert.assertEquals(364, l);
		//			.blockingForEach(e->System.err.println("Element: "+e+" -> "));
	}
	
	@Test
	public void testSingleJSON() {
		Flowable<byte[]> dataSource = Bytes.from(JSONObservableTest.class.getClassLoader().getResourceAsStream("testlist.json"),10);
		JsonNode node = dataSource
				.compose(JSON.collectBytesToSingle())
				.firstOrError()
				.map(input->JSON.parseObjectNodes(input))
				.blockingGet();
		
			
		ArrayNode nde = (ArrayNode) node.get("Objects");
		int size = nde.size();
		Assert.assertEquals(5, size);
		//			.blockingForEach(e->System.err.println("Element: "+e+" -> "));
	}
	
	private Iterable<JsonNode> listIterable(Function<Integer,Flowable<byte[]>> creator) {
		return new Iterable<JsonNode>() {

			@Override
			public Iterator<JsonNode> iterator() {
				return new Iterator<JsonNode>() {

					@Override
					public boolean hasNext() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public JsonNode next() {
						// TODO Auto-generated method stub
						return null;
					}
					
				};
			}
			
		};
	}
	private boolean mightHaveMore(JsonNode node) {
		ArrayNode nde = (ArrayNode) node.get("Objects");
		int size = nde.size();
		return size >= 500;
		
	}
}
