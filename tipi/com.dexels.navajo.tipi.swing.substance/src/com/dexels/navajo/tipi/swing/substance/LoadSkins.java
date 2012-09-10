package com.dexels.navajo.tipi.swing.substance;

import java.util.Map;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SkinInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class LoadSkins extends TipiAction {

	private static final long serialVersionUID = 5481600392557969470L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(LoadSkins.class);

	protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
		String serviceName = (String) getEvaluatedParameterValue("service", event);
		try {
			Navajo n = SubstanceSkinManager.createSkinNavajo();
			myContext.injectNavajo(serviceName, n);
		} catch (NavajoException e) {
			e.printStackTrace();
			throw new TipiException("Error loading substance skins!",e);
		}
		
	}

	private Property createSkinProperty(Navajo rootDoc) throws NavajoException {
		Property p = NavajoFactory.getInstance().createProperty(rootDoc, "Skins",Property.CARDINALITY_SINGLE, "Known skins", Property.DIR_OUT);
		Map<String,SkinInfo> ss =  SubstanceLookAndFeel.getAllSkins();
		for (String name : ss.keySet()) {
			SkinInfo si = ss.get(name);
			logger.debug("Skin name: "+name+" classname: "+si.getClassName());
			Selection s = NavajoFactory.getInstance().createSelection(rootDoc, name, si.getClassName(), false);
			p.addSelection(s);
		}		
		return p;
	}

	private Navajo createSkinNavajo() throws NavajoException {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Skin");
		n.addMessage(m);
		Property p = createSkinProperty(n);
		m.addProperty(p);
		return n;
	}
	

	public static 			void main(String[] args) throws NavajoException {
		
		Navajo n = new LoadSkins().createSkinNavajo();
		n.write(System.err);

		Map<String,SkinInfo> ss =  SubstanceLookAndFeel.getAllSkins();
		for (String name : ss.keySet()) {
			System.err.println("Name: "+name);
			SkinInfo si = ss.get(name);
			System.err.println("DisplName: "+si.getDisplayName());
			System.err.println("Classname: "+si.getClassName());
		}
		
	}
	
}
