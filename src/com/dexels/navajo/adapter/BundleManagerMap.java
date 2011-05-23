package com.dexels.navajo.adapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

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

	@Override
	public void load(Access access) throws MappableException, UserException {
		// TODO Auto-generated method stub

	}

	private XMLElement createFunctionBundleXml() {
		XMLElement xe = new CaseSensitiveXMLElement("functiondef");
		List<XMLElement> flist = FunctionFactoryFactory.getInstance().getAllFunctionElements(FunctionInterface.class.getName(),"functionDefinition");
		System.err.println("LIST SIZE: "+flist.size());
		for (XMLElement xmlElement : flist) {
			xe.addChild(xmlElement);
		}
		return xe;
	}

	public Binary getFunctionBinary() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer out = new OutputStreamWriter(baos);
		XMLElement xe = createFunctionBundleXml();
		try {
			xe.write(out);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Binary result = new Binary(baos.toByteArray());
		result.setExtension("xml");
		result.setMimeType("text/xml");
		return result;
	}
	
	@Override
	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub

	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub

	}

}
