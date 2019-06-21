package com.dexels.navajo.birt;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.ServiceHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BirtHandler extends ServiceHandler { 
	
	private static final Logger logger = LoggerFactory.getLogger(BirtHandler.class);
	
	public BirtHandler() {
		
	}
	
	@Override
	public String getIdentifier() {
		return "birt";
	}
	
	public Navajo makeNavajoBirt( Binary b ) {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message msg = NavajoFactory.getInstance().createMessage(n,"Birt");
		msg.setType("simple");
		n.addMessage(msg);
		Property prop = NavajoFactory.getInstance().createProperty(n, "Template", Property.BINARY_PROPERTY, "","");
		prop.setSubType("extension=rptdesign");
		prop.setValue(b);
		msg.addProperty(prop);
		return n;
	}
	
	public Binary getBirtBinary( Navajo n ) {
		Binary template = null;
		BirtUtils report = new BirtUtils();
		Binary result = new Binary();
		try {
			result = report.createEmptyReport(n,template);
		}
		catch(Exception e) { 
			logger.error("Unable to create report.",e);
		}
		return result;
	}
	
	public Navajo getInDoc( Access a ) {
		Navajo result = a.getInDoc();
		return result;
	}
	
	@Override
	public boolean needsRecompile( Access a ) {
		return false;
	}
	
	@Override
    public final Navajo doService( Access a ) {
		Navajo inDoc = getInDoc( a );
		inDoc.removeInternalMessages();
		Binary birtBinary = getBirtBinary(inDoc);
		Navajo navajoBirt = makeNavajoBirt(birtBinary);
		return navajoBirt;
	}

	@Override
	public void setNavajoConfig( NavajoConfigInterface navajoConfig ) {
		// Not interested in the NavajoConfig
	}

}
