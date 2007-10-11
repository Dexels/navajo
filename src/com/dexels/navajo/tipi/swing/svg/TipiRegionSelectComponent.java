package com.dexels.navajo.tipi.swing.svg;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.w3c.dom.svg.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.swing.svg.impl.*;

public class TipiRegionSelectComponent extends TipiSvgComponent {

	String[] regions = new String[] {
			"Groningen","Friesland","Drenthe","Overijssel","Gelderland","NoordHolland","ZuidHolland","Utrecht","NoordBrabant","Zeeland","Flevoland","Limburg"
	};
	
	String regionString = "Groningen,Friesland,Drenthe,Overijssel,Gelderland,NoordHolland,ZuidHolland,Utrecht,NoordBrabant,Zeeland,Flevoland,Limburg";
	
	private JComboBox comboBox;

	private SvgBatikComponent svgPanel;
	
	@Override
	public Object createContainer() {
		JPanel lp = new JPanel();
		lp.setLayout(new BorderLayout());
		comboBox = new JComboBox(regions);
		lp.add(comboBox,BorderLayout.SOUTH);
		comboBox.addItemListener(new ItemListener(){
 
			@Override
			public void itemStateChanged(ItemEvent e) {
				String sel = (String) e.getItem();
				if(e.getStateChange()==ItemEvent.SELECTED) {
//					svgPanel.setAttribute((String) e.getItem(),"fill","#ddd");
//					svgPanel.moveToFirst((String) e.getItem());
					svgPanel.setAttribute(null,sel,"stroke","#c00");
					svgPanel.setAttribute(null,sel,"fill","#6ff");
					
					svgPanel.moveToFirst(sel);
					try {
						Map m = new HashMap();
						m.put("id", sel);
						performTipiEvent("onSelect", m, true);
					} catch (TipiException e1) {
						e1.printStackTrace();
					}
				}
				if(e.getStateChange()==ItemEvent.DESELECTED) {
//					svgPanel.setAttribute((String) e.getItem(),"fill","#ff6");
					svgPanel.setAttribute(null,sel,"stroke","#ff6");
					//svgPanel.moveToFirst((String) e.getItem());

				}

			}});
		svgPanel = (SvgBatikComponent) super.createContainer();
		lp.add(svgPanel,BorderLayout.CENTER);
		svgPanel.setRegisteredIds(regionString);
		svgPanel.init(getClass().getClassLoader().getResource("nederland.svg"));
		svgPanel.addSvgDocumentListener(new SvgDocumentAdapter(){

			@Override
			public void onDocumentLoadingFinished() {
				super.onDocumentLoadingFinished();
				for (int i = 0; i < regions.length; i++) {
					svgPanel.registerId(regions[i],svgPanel.getDocument());
				}
			}
			
		});		
		
		return lp;

	}

	@Override
	public void onClick(final String targetId) {
		super.onClick(targetId);
			SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				comboBox.setSelectedItem(targetId);
			}});
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		// TODO Auto-generated method stub
		if (name.equals("selected")) {
			comboBox.setSelectedItem((String)object);
		}

		super.setComponentValue(name, object);
	}

	@Override
	protected Object getComponentValue(String name) {
		// TODO Auto-generated method stub
		if (name.equals("selected")) {
			return comboBox.getSelectedItem();
		}
		return super.getComponentValue(name);
	}

}
