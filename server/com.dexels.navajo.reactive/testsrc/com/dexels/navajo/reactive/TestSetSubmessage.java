/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.mappers.SetSingle;

public class TestSetSubmessage {
	
	
	@Before
	public void setup() {
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
	}
	@Test
	public void setSubMessage() {
		SetSingle ss = new SetSingle();
		ImmutableMessage msg = ImmutableFactory.empty()
				.with("name", "bob", "string")
				.with("BankAccount/balance", 11, "integer");
		String before = ImmutableFactory.createParser().describe(msg);
		System.err.println("Result: "+before);
		
		ReactiveParameters param = ReactiveParameters.empty(ss)
			.withConstant("BankAccount/balance", 10, "integer");
		
		DataItem dd = ss.execute(param)
			.apply(new StreamScriptContext("tenant","service","deployment"))
			.apply(DataItem.of(msg));
		String result = ImmutableFactory.createParser().describe(dd.message());
		System.err.println("Result: "+result);
		Optional<Object> name = dd.message().value("name");
		Assert.assertTrue(name.isPresent());
	}
}
