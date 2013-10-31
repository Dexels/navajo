package com.dexels.navajo.tipi.swing.substance;

import java.awt.Frame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiSetSkin extends TipiAction {

	private static final long serialVersionUID = -461311020707210415L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSetSkin.class);
	
	
	@Override
	protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
		final Operand valueOp = getEvaluatedParameter("value", event);
		myContext.runSyncInEventThread(new Runnable(){

			@Override
			public void run() {
				String value = null;
				
				if (valueOp != null) {
					value = (String) valueOp.value;
				}
				logger.info("Setting skin: "+value);
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
			final String defaultSkin = "org.pushingpixels.substance.api.skin.BusinessSkin";
			SubstanceLookAndFeel.setSkin(defaultSkin);
		} catch (Exception e) {
			logger.error("Error setting skin: ", e);
		}
	}

	private void setSubstanceSkin(String value) {
		logger.info("SETTING SKIN: "+value);

		if(value.startsWith("org.jvnet.substance.skin.")) {
			// old package, convert.
			String className = value.substring(value.lastIndexOf('.')+1,value.length());
			value = "org.pushingpixels.substance.api.skin."+className;
		}
		try {
			boolean b = SubstanceLookAndFeel.setSkin(value);
			if(!b) {
				setDefaultSkin();
			}
		} catch (Throwable e) {
			logger.error("Error setting skin to: "+value+" resetting to default.",e);
			setDefaultSkin();
		}

		Frame[] f = Frame.getFrames();
		for (int i = 0; i < f.length; i++) {
			SwingUtilities.updateComponentTreeUI(f[i]);
		}
	}
}
