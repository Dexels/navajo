/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.xml.XMLEvent;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;
import com.fasterxml.aalto.AsyncByteArrayFeeder;
import com.fasterxml.aalto.AsyncXMLInputFactory;
import com.fasterxml.aalto.AsyncXMLStreamReader;
import com.fasterxml.aalto.stax.InputFactoryImpl;

public class SaxXmlFeeder implements AsyncByteArrayFeeder {

	private final AsyncByteArrayFeeder wrappedFeeder;
	private final AsyncXMLStreamReader<AsyncByteArrayFeeder> parser;

	private final static Logger logger = LoggerFactory.getLogger(SaxXmlFeeder.class);
	private Throwable failedWith = null;
	
	public SaxXmlFeeder() {
		AsyncXMLInputFactory f = new InputFactoryImpl();
		this.parser = f.createAsyncForByteArray();
		this.wrappedFeeder = parser.getInputFeeder();
	}

	@Override
	public void endOfInput() {
		wrappedFeeder.endOfInput();
	}
	
	public Throwable getException() {
		return this.failedWith;
	}

	public Iterable<XMLEvent> parse(byte[] buffer) throws XMLStreamException {
//		try {
			wrappedFeeder.feedInput(buffer,0,buffer.length);
//		} catch (XMLStreamException e) {
//			logger.error("XML problem in SaxXML",e);
//			failedWith = e;
//		}
//		updateSax();
		
		return getIterable();
	}

	public Iterable<XMLEvent> getIterable() {
		return new Iterable<XMLEvent>(){
			
			@Override
			public Iterator<XMLEvent> iterator() {
				return new Iterator<XMLEvent>(){
					private int currentToken  = -1;
					
					@Override
					public boolean hasNext() {
						try {
							if(currentToken!=-1) {
								return true;
							}
							currentToken = parser.next();
							boolean hasNext = parser.hasNext() && currentToken!=AsyncXMLStreamReader.EVENT_INCOMPLETE;
							
							return hasNext;
						} catch (XMLStreamException e) {
							throw new RuntimeException("XML Error", e);
						}
					}

					@Override
					public XMLEvent next() {
						if(currentToken == -1) {
							try {
								currentToken = parser.next();
							} catch (Throwable e) {
								logger.error("Error: ", e);
								failedWith = e;
								return null;
							}
						}

						try{
							switch (currentToken) {
							case XMLStreamConstants.START_DOCUMENT:
								return new XMLEvent(XmlEventTypes.START_DOCUMENT, null, null);
							case XMLStreamConstants.END_DOCUMENT:
								return new XMLEvent(XmlEventTypes.END_DOCUMENT, null, null);
							case XMLStreamConstants.START_ELEMENT:
								Map<String, String> attributes = new HashMap<>();
								for (int i = 0; i < parser.getAttributeCount(); i++) {
									attributes.put(parser.getAttributeLocalName(i), parser.getAttributeValue(i));
								}
								return new XMLEvent(XmlEventTypes.START_ELEMENT, parser.getLocalName(), Collections.unmodifiableMap(attributes));
							case XMLStreamConstants.END_ELEMENT:
								return new XMLEvent(XmlEventTypes.END_ELEMENT, parser.getLocalName(), null);
							case XMLStreamConstants.CHARACTERS:
								return new XMLEvent(XmlEventTypes.TEXT, parser.getText(), null);
						}
						return null;
					} finally {
						currentToken = -1;
					}
					}
					
				};
			}};
	}

	@Override
	public boolean needMoreInput() {
		return wrappedFeeder.needMoreInput();
	}


	@Override
	public void feedInput(byte[] bb,int start, int length) throws XMLStreamException {
		wrappedFeeder.feedInput(bb,start,length);
	}
}
