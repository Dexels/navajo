package com.dexels.navajo.tipi.swingx;

import java.util.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.tips.*;

import com.dexels.navajo.server.statistics.*;
import com.dexels.navajo.tipi.components.swingimpl.*;

public class TipiTipPanel extends TipiSwingDataComponentImpl {

	private TipOfTheDayModel tipModel;

	@Override
	public Object createContainer() {
		DefaultTip t = new DefaultTip();
		t.setTip("<html><b>Heee</b>, <i>hoooo</i> heeee hooo");
		t.setTipName("Mofo!");
		List<TipOfTheDayModel.Tip> aap = new ArrayList<TipOfTheDayModel.Tip>();
		aap.add(t);
		tipModel = new DefaultTipOfTheDayModel(aap);
		System.err.println("TIPP: "+tipModel.getTipCount());
		System.err.println("TIPP: "+tipModel.getTipAt(0).getTipName());
		JXTipOfTheDay p = new JXTipOfTheDay(tipModel);
		p.setCurrentTip(0);
		//		p.l
		//p.nextTip();
//		p.showDialog(null);
		return p;
	}

}
