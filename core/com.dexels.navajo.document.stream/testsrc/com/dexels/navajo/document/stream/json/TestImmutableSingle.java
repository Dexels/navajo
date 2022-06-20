/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.json;

import java.util.Optional;

import org.junit.Test;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.NavajoStreamToMutableMessageStream;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.xml.XML;
import com.github.davidmoten.rx2.Bytes;

public class TestImmutableSingle {

	
	@Test
	public void testImmutable() {
		
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
		
		Bytes.from(TestImmutableSingle.class.getClassLoader().getResourceAsStream("tml_without_binary.xml"),8192)
		.lift(XML.parseFlowable(5))
		.concatMap(e->e)
		.lift(StreamDocument.parse())
		.concatMap(e->e)
		.compose(StreamDocument.eventsToImmutable(Optional.of("Club")))
		.blockingForEach(e->{
			String result = ImmutableFactory.getInstance().describe(e);
			System.err.println("AA>> "+result);
		});
		

	}


	
	@Test
	public void testEmptyImmutable() {
		
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
		
		Bytes.from(TestImmutableSingle.class.getClassLoader().getResourceAsStream("tml_without_binary.xml"),8192)
		.lift(XML.parseFlowable(5))
		.concatMap(e->e)
		.lift(StreamDocument.parse())
		.concatMap(e->e)
		.compose(StreamDocument.eventsToImmutable(Optional.empty()))
		.blockingForEach(e->{
			String result = ImmutableFactory.getInstance().describe(e);
			System.err.println("AA>> "+result);
		});
		

	}

	@Test
	public void testMutable() {
		Bytes.from(TestImmutableSingle.class.getClassLoader().getResourceAsStream("tml_without_binary.xml"),8192)
			.lift(XML.parseFlowable(5))
			.concatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.lift(NavajoStreamToMutableMessageStream.toMutable(Optional.empty()))
			.concatMap(e->e)
			.blockingForEach(e->{
				e.write(System.err);
			});
			
	}


}
