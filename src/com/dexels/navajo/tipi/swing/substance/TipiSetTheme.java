package com.dexels.navajo.tipi.swing.substance;

import java.awt.Frame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.SubstanceLookAndFeel;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiSetTheme extends TipiAction {

	protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
		Operand valueOp = getEvaluatedParameter("value", event);
		String value = null;
		if(valueOp!=null) {
			value = (String) valueOp.value;
		}
		if (value==null) {
			setDefaultLnF();
		} else {
			setSubstanceTheme(value);
		}
	}

	private void setDefaultLnF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setSubstanceTheme(String value) {
		// TODO Auto-generated method stub
		SubstanceLookAndFeel.setCurrentTheme(value);
		Frame[] f = Frame.getFrames();
		try {
			UIManager.setLookAndFeel(new SubstanceLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < f.length; i++) {
			SwingUtilities.updateComponentTreeUI(f[i]);
				
		}
	}

}
