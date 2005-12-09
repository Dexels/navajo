package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.components.echoimpl.impl.TipiOptionPane;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiEchoShowInfo extends TipiAction {
	public TipiEchoShowInfo() {
	}

	protected void execute(TipiEvent e)
			throws com.dexels.navajo.tipi.TipiBreakException,
			com.dexels.navajo.tipi.TipiException {
		Operand text = getEvaluatedParameter("text", e);
		// TipiScreen s = (TipiScreen) myContext.getDefaultTopLevel();
		// Window w = (Window) s.getTopLevel();
		// final WindowPane wp = new WindowPane("Monkey",new
		// Extent(400,Extent.PX),new Extent(300,Extent.PX));
		// w.getContent().add(wp);
		//    
		// wp.setModal(true);
		// Row cp = new Row();
		// wp.add(cp);
		// cp.add(new Label((String) text.value));
		// Button b = new Button("Ok");
		// cp.add(b);
		// b.addActionListener(new ActionListener(){
		//
		// public void actionPerformed(ActionEvent arg0) {
		// wp.dispose();
		//			
		// }});
		// wp.setDefaultCloseOperation(WindowPane.DISPOSE_ON_CLOSE);
		//
		// wp.setVisible(true);

		TipiOptionPane.showInfo(myContext, (String) text.value, "Info:",
				"Close");

	}

}
