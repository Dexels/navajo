package com.dexels.navajo.adapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class BundleManagerMap implements Mappable {

		
	private final static Logger logger = LoggerFactory
			.getLogger(BundleManagerMap.class);

	@Override
	public void load(Access access) throws MappableException, UserException {

	}

	private XMLElement createFunctionBundleXml() {
		XMLElement xe = new CaseSensitiveXMLElement("functiondef");
		List<XMLElement> flist = FunctionFactoryFactory.getInstance().getAllFunctionElements(FunctionInterface.class.getName(),"functionDefinition");
		for (XMLElement xmlElement : flist) {
			xe.addChild(xmlElement);
		}
		return xe;
	}

	private XMLElement createAdapterBundleXml() {
		XMLElement xe = new CaseSensitiveXMLElement("adapterdef");
		List<XMLElement> flist = FunctionFactoryFactory.getInstance().getAllAdapterElements(Object.class.getName(),"functionDefinition");
		for (XMLElement xmlElement : flist) {
			xe.addChild(xmlElement);
		}
		return xe;
	}

	
	public Binary getFunctionBinary() {
		XMLElement xe = createFunctionBundleXml();
		return createBinary(xe);
	}

	
	public Binary getAdapterBinary() {
		XMLElement xe = createAdapterBundleXml();
		return createBinary(xe);
	}

	private Binary createBinary(XMLElement xe) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer out = new OutputStreamWriter(baos);
		try {
			xe.write(out);
			out.flush();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
		Binary result = new Binary(baos.toByteArray());
		result.setExtension("xml");
		result.setMimeType("text/xml");
		return result;
	}
	
	@Override
	public void store() throws MappableException, UserException {

	}

	@Override
	public void kill() {

	}

}
