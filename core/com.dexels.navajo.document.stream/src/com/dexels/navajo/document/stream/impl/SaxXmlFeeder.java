package com.dexels.navajo.document.stream.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.xml.XmlInputHandler;
import com.fasterxml.aalto.AsyncByteArrayFeeder;
import com.fasterxml.aalto.AsyncXMLInputFactory;
import com.fasterxml.aalto.AsyncXMLStreamReader;
import com.fasterxml.aalto.stax.InputFactoryImpl;

public class SaxXmlFeeder implements AsyncByteArrayFeeder {

	private final XmlInputHandler handler;
	private final AsyncByteArrayFeeder wrappedFeeder;
	private final AsyncXMLStreamReader<AsyncByteArrayFeeder> parser;

	private final static Logger logger = LoggerFactory.getLogger(SaxXmlFeeder.class);
	private Throwable failedWith = null;
	
	public SaxXmlFeeder(XmlInputHandler handler) {
		this.handler = handler;
		AsyncXMLInputFactory f = new InputFactoryImpl();
		try {
			this.parser = f.createAsyncFor(new byte[] {});
		} catch (XMLStreamException e) {
			throw new RuntimeException("Unexpected initialization problem for XML streams: ",e);
		}
		this.wrappedFeeder = parser.getInputFeeder();

	}

	@Override
	public void endOfInput() {
		wrappedFeeder.endOfInput();
		updateSax();
	}
	
	public Throwable getException() {
		return this.failedWith;
	}

	private void updateSax() {
		int e = -1;
		try {
			while (parser.hasNext() && e != AsyncXMLStreamReader.EVENT_INCOMPLETE) {
				e = parser.next();
				switch (e) {
				case XMLStreamConstants.START_DOCUMENT:
					handler.startDocument();
					break;
				case XMLStreamConstants.END_DOCUMENT:
					handler.endDocument();
					break;
				case XMLStreamConstants.START_ELEMENT:
					Map<String, String> attributes = new HashMap<>();
					for (int i = 0; i < parser.getAttributeCount(); i++) {
						attributes.put(parser.getAttributeLocalName(i), parser.getAttributeValue(i));
					}

					handler.startElement(parser.getLocalName(), Collections.unmodifiableMap(attributes));
					break;
				case XMLStreamConstants.END_ELEMENT:
					handler.endElement(parser.getLocalName());
					break;

				case XMLStreamConstants.CHARACTERS:
					handler.text(parser.getText());
				}
			}
		} catch (XMLStreamException ex) {
			logger.error("Xml parsing problem: ", ex);
		}
	}

	@Override
	public boolean needMoreInput() {
		return wrappedFeeder.needMoreInput();
	}

	@Override
	public void feedInput(byte[] bytes, int pos, int len)  {
		try {
			wrappedFeeder.feedInput(bytes, pos, len);
		} catch (XMLStreamException e) {
			logger.error("XML problem in SaxXML");
			failedWith = e;
		}
		updateSax();
	}
	
	public void feed(byte[] bytes) {
		feedInput(bytes, 0, bytes.length);
	}

}
