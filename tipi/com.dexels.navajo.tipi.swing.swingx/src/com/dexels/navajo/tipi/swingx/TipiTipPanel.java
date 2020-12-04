/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingx;

import java.util.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.tips.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.components.swingimpl.*;

public class TipiTipPanel extends TipiSwingDataComponentImpl {

	
	private static final long serialVersionUID = 8655363825407388712L;
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTipPanel.class);
	private TipOfTheDayModel tipModel;

	@Override
	public Object createContainer() {
		DefaultTip t = new DefaultTip();
		t.setTip("<html><b>Heee</b>, <i>hoooo</i> heeee hooo");
		t.setTipName("Mofo!");
		List<TipOfTheDayModel.Tip> aap = new ArrayList<TipOfTheDayModel.Tip>();
		aap.add(t);
		tipModel = new DefaultTipOfTheDayModel(aap);
		logger.info("TIPP: " + tipModel.getTipCount());
		logger.info("TIPP: " + tipModel.getTipAt(0).getTipName());
		JXTipOfTheDay p = new JXTipOfTheDay(tipModel);
		p.setCurrentTip(0);
		// p.l
		// p.nextTip();
		// p.showDialog(null);
		return p;
	}

}
