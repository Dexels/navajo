/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.json;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.json.async.NonBlockingJsonParser;

public class JSONTest {


	@Test
	public void simpleTest() throws IOException {
		byte[] json_chunk1 = "{\"name\":\"El".getBytes(StandardCharsets.UTF_8);
	    byte[] json_chunk2 = "vis\"}".getBytes(StandardCharsets.UTF_8);

		JsonFactory s = new JsonFactory();
//		NonBlockingJsonParser n = new NonBlockingJsonParser();
//		s.createNonBlockingByteArrayParser().getNonBlockingInputFeeder().
		NonBlockingJsonParser nbj = ((NonBlockingJsonParser)s.createNonBlockingByteArrayParser());
		nbj.feedInput(json_chunk1, 0, json_chunk1.length);
		JsonToken t = null;
		do {
			t = nbj.nextToken();
			System.err.println("t: "+t);
		} while(t!=null &&t != JsonToken.NOT_AVAILABLE);

		nbj.feedInput(json_chunk2, 0, json_chunk2.length);
//		nbj.endOfInput();
		do {
			t = nbj.nextToken();
			System.err.println("t: "+t);
		} while(t!= null && t != JsonToken.NOT_AVAILABLE);
	}
	
	@Test
	public void testJSONIterable() throws IOException {
		JSONToIterable jo = new JSONToIterable(new JsonFactory());
		
	    byte[] json_chunk1 = "{\"name\":\"El".getBytes(StandardCharsets.UTF_8);
	    byte[] json_chunk2 = "vis\"}".getBytes(StandardCharsets.UTF_8);
	    for (JSONEvent b : jo.feed(json_chunk1)) {
			System.err.println("token: "+b);
		}
	    for (JSONEvent b : jo.feed(json_chunk2)) {
			System.err.println("token: "+b);
		}

	    for (JSONEvent b : jo.endOfInput()) {
			System.err.println("token: "+b);
		}
	    
	}
	
	

}
