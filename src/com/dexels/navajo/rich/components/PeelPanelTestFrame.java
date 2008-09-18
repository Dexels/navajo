package com.dexels.navajo.rich.components;

import java.awt.*;

import javax.swing.*;

public class PeelPanelTestFrame extends JFrame {
	private PeelPanel peelPanel = new PeelPanel();

	public PeelPanelTestFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setTitle("Testapplication");
		initUI();
	}

	private final void initUI() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(peelPanel, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		PeelPanelTestFrame mf = new PeelPanelTestFrame();
		mf.setVisible(true);
	}
}
