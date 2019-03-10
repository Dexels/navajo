package com.dexels.navajo.adapters.stream;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;

public class TestStreamReduce {
	
	private static final Logger logger = LoggerFactory.getLogger(TestStreamReduce.class);


	@Test
	public void testReduce() {
		logger.info(">>>> {}",Flowable.just("aap","noot","mies","wim","zus","jet")
			.reduce("",reducer())
			.blockingGet()
			);
		
	}

	private BiFunction<String,String,String> reducer() {
		return (red,item)->red+"-"+item;
	}
}
