package com.dexels.navajo.tipi.swing.substance;

import java.awt.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.internal.*;

public class TipiSetTheme extends TipiAction {

	protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
		final Operand valueOp = getEvaluatedParameter("value", event);

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					String value = null;
					if (valueOp != null) {
						value = (String) valueOp.value;
					}
					if (value == null) {
						setDefaultLnF();
					} else {
						setSubstanceTheme(value);
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});

	}

	private void setDefaultLnF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSubstanceTheme(String value) {
	
//		SubstanceLookAndFeel.setCurrentTheme(value);
		Frame[] f = Frame.getFrames();
		try {	
			UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessLookAndFeel");
//			UIManager.setLookAndFeel(new SubstanceLookAndFeel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (((SwingTipiContext) myContext).getAppletRoot() != null) {
		} else {
			for (int i = 0; i < f.length; i++) {
				SwingUtilities.updateComponentTreeUI(f[i]);
			}
		}

	}

}
