package com.dexels.navajo.tipi.swing.substance;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.jvnet.substance.*;
import org.jvnet.substance.skin.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.internal.*;

public class LoadSkins extends TipiAction {

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

	private void setDefaultSkin() {
		try {
			SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.BusinessSkin");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setSubstanceSkin(String value) {
		Map<String,SkinInfo> ss =  SubstanceLookAndFeel.getAllSkins();
		for (String name : ss.keySet()) {
			System.err.println("Name: "+name);
		}
		
		
		SubstanceLookAndFeel.setSkin(value);

		Frame[] f = Frame.getFrames();
		try {
			UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (((SwingTipiContext) myContext).getAppletRoot() != null) {
		} else {
			for (int i = 0; i < f.length; i++) {
				SwingUtilities.updateComponentTreeUI(f[i]);
			}
		}

	}

	private Property createSkinProperty(Navajo rootDoc) throws NavajoException {
		Property p = NavajoFactory.getInstance().createProperty(rootDoc, "Skins",Property.CARDINALITY_SINGLE, "Known skins", Property.DIR_OUT);
		Map<String,SkinInfo> ss =  SubstanceLookAndFeel.getAllSkins();
		for (String name : ss.keySet()) {
			SkinInfo si = ss.get(name);
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
	
	private void injectSkinNavajo() throws NavajoException {
		Navajo n = createSkinNavajo();
		myContext.injectNavajo("Skins", n);
	}
	
	public static void main(String[] args) throws NavajoException {
		
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
