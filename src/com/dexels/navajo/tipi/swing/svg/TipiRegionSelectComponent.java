package com.dexels.navajo.tipi.swing.svg;

import java.awt.*;

import javax.swing.*;

import com.dexels.navajo.tipi.swing.svg.impl.*;

public class TipiRegionSelectComponent extends TipiSvgComponent {

	String[] regions = new String[] {
			"Groningen","Friesland","Drenthe","Overijssel","Gelderland","NoordHolland","ZuidHolland","Utrecht","NoordBrabant","Zeeland","Flevoland","Limburg"
	};
	
	String regionString = "Groningen,Friesland,Drenthe,Overijssel,Gelderland,NoordHolland,ZuidHolland,Utrecht,NoordBrabant,Zeeland,Flevoland,Limburg";
	
	private JComboBox comboBox;
	
	@Override
	public Object createContainer() {
		JPanel lp = new JPanel();
		lp.setLayout(new BorderLayout());
		comboBox = new JComboBox(regions);
		lp.add(comboBox,BorderLayout.SOUTH);
		SvgBatikComponent svgPanel = (SvgBatikComponent) super.createContainer();
		lp.add(svgPanel,BorderLayout.CENTER);
		svgPanel.setRegisteredIds(regionString);
		svgPanel.init(getClass().getClassLoader().getResource("nederland.svg"));
		
//		for (int i = 0; i < regions.length; i++) {
//			svgPanel.registerId(regions[i]);
//		}
		return lp;

	}

	@Override
	public void onClick(String targetId) {
		super.onClick(targetId);
		System.err.println("Click detected: ");
		comboBox.setSelectedItem(targetId);
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		// TODO Auto-generated method stub
		super.setComponentValue(name, object);
	}

	@Override
	protected Object getComponentValue(String name) {
		// TODO Auto-generated method stub
		return super.getComponentValue(name);
	}

}
