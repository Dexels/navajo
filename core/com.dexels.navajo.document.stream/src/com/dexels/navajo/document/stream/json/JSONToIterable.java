package com.dexels.navajo.document.stream.json;

import java.io.IOException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.json.async.NonBlockingJsonParser;

public class JSONToIterable {

	private NonBlockingJsonParser parser;
	
	private final static Logger logger = LoggerFactory.getLogger(JSONToIterable.class);
	private Throwable error;
	/**
	 * 'Iterable API' for the async parser, essentially transforms byte arrays to Iterables of 0-or-more
	 * JsonTokens. Note that I filter out the NOT_AVAILABLE tokens, for now they are implied by the end
	 * of the Iterable.
	 * When the json stream is done it will emit one 'null' token, I would have preferred an actual type,
	 * but that seems to be the way Jackson works. For now, I (ab)use the NOT_AVAILABLE token as stream terminator,
	 * as it is pretty tricky to use null-values in for example RxJava
	 * @param factory
	 */
	public JSONToIterable(JsonFactory factory) {
		try {
			this.parser = (NonBlockingJsonParser) factory.createNonBlockingByteArrayParser();
		} catch (IOException e) {
			// this would be weird, right
			logger.error("Quite unexpected IO error without any IO whatsoever: ", e);
		}
	}

	public Throwable error() {
		return this.error;
	}
	public Iterable<JSONEvent> feed(byte[] data) throws IOException {
		parser.feedInput(data, 0, data.length);
		return emitAvailableTokens();
	}

	private Iterable<JSONEvent> emitAvailableTokens() {
		return new Iterable<JSONEvent>() {

			@Override
			public Iterator<JSONEvent> iterator() {
				return new Iterator<JSONEvent>(){
					boolean available = true;
					boolean terminated = false;
					boolean terminating = false;
//					JsonToken currentToken = null;
					JSONEvent currentEvent = null;

					private JSONEvent getNextToken() {
						if(currentEvent!=null) {
							return currentEvent;
						}
						try {
							JsonToken jt = parser.nextToken();
							currentEvent = jt == null? null : new JSONEvent(jt,parser.getCurrentName(), parser.getText());
//							currentEvent = new JSONEvent(jt,parser.getCurrentValue());
						} catch (IOException e) {
							logger.error("Error parsing, storing exception: ", e);
							error = e;
						}
						if(currentEvent==null ) {
							terminating = true;
						}
						if(currentEvent==null) {
							available = false;
							currentEvent = new JSONEvent(null, "","");
						}
						return currentEvent;
					}
					@Override
					public boolean hasNext() {
						if(terminating) 
							return true;
						if(!available || terminated) return false;
						while(available &&  currentEvent==null &&!terminating && !terminated) {
							currentEvent = getNextToken();
						}
						return  currentEvent.token()!=JsonToken.NOT_AVAILABLE;
					}

					@Override
					public JSONEvent next() {
						if(terminating) {
							terminated = true;
							terminating = false;
							currentEvent = new JSONEvent(null, "","");
						}
						JSONEvent result;
						result = getNextToken();
//						if(result==null) {
//							System.err.println("whoops");
//						}
						currentEvent = null;
						return result;
					}};
			}
			
		};
	}
	
	public Iterable<JSONEvent> endOfInput() {
		parser.endOfInput();
		try {
			return emitAvailableTokens();
		} catch(Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

}
