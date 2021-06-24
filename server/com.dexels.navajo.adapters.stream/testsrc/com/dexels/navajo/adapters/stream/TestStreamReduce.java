/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
