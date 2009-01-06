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

public class TipiSetSkin extends TipiAction {

	protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
		final Operand valueOp = getEvaluatedParameter("value", event);
		myContext.runSyncInEventThread(new Runnable(){

			public void run() {
				String value = null;
				
				if (valueOp != null) {
					value = (String) valueOp.value;
				}
				System.err.println("Setting skin: "+value);
				
				if (value == null) {
					setDefaultSkin();
				} else {
					setSubstanceSkin(value);
				}
			}});
	
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
//		Map<String,SkinInfo> ss =  SubstanceLookAndFeel.getAllSkins();
	
		SubstanceLookAndFeel.setSkin(value);

		Frame[] f = Frame.getFrames();
//		try {
//			UIManager.setLookAndFeel(value);
//		} catch (UnsupportedLookAndFeelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if (((SwingTipiContext) myContext).getAppletRoot() != null) {
		} else {
			for (int i = 0; i < f.length; i++) {
				SwingUtilities.updateComponentTreeUI(f[i]);
			}
		}

	}
}
