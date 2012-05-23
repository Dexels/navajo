package com.dexels.navajo.tipi.swing.substance;

import java.awt.Frame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiSetSkin extends TipiAction {

	private static final long serialVersionUID = -461311020707210415L;

	protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
		final Operand valueOp = getEvaluatedParameter("value", event);
		myContext.runSyncInEventThread(new Runnable(){

			public void run() {
				String value = null;
				
				if (valueOp != null) {
					value = (String) valueOp.value;
				}
				System.err.println("Setting skin: "+value);
				UIManager.getLookAndFeelDefaults().put("ClassLoader", SubstanceBusinessBlackSteelLookAndFeel.class.getClassLoader());

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
			e.printStackTrace();
		}
	}

	private void setSubstanceSkin(String value) {
	
		SubstanceLookAndFeel.setSkin(value);

		Frame[] f = Frame.getFrames();

//		if (((SwingTipiContext) myContext).getAppletRoot() != null) {
//		} else {
			for (int i = 0; i < f.length; i++) {
				SwingUtilities.updateComponentTreeUI(f[i]);
			}
//		}

	}
}
