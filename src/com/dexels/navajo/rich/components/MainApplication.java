package com.dexels.navajo.rich.components;

import javax.swing.JFrame;
import javax.swing.UIManager;



public class MainApplication {

	public MainApplication(){
		try{
//			UIManager.setLookAndFeel(new SubstanceLookAndFeel());
		}catch(Exception e){
			e.printStackTrace();
		}
		MainFrame mf = new MainFrame();
		mf.setSize(800,600);
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mf.show();
	}
	
	public static void main(String[] args){
		
		new MainApplication();
	}
	
}
