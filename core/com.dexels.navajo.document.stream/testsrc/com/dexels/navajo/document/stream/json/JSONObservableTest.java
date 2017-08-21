package com.dexels.navajo.document.stream.json;

import org.junit.Assert;
import org.junit.Test;

import com.github.davidmoten.rx2.Bytes;

public class JSONObservableTest {

	@Test
	public void testRxJSON() {
		long l = Bytes.from(JSONObservableTest.class.getClassLoader().getResourceAsStream("person.json"))
			.lift(JSON.parseFlowable(10))
			.flatMap(e->e)
			.count()
			.blockingGet();
		Assert.assertEquals(364, l);
		//			.blockingForEach(e->System.err.println("Element: "+e+" -> "));
	}
}
