package com.dexels.navajo.tipi.components.rich.ui;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.text.*;
import javax.swing.text.Position.*;

public class RichTextUI extends TextUI {

	@Override
	public void paint(Graphics g, JComponent c) {
		Graphics2D g2 = (Graphics2D) g.create();
		Rectangle bounds = c.getBounds();
		g2.setColor(Color.red);
		g2.fillRect(0, 0, bounds.width, bounds.height);
	}

	@Override
	public void damageRange(JTextComponent t, int p0, int p1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void damageRange(JTextComponent t, int p0, int p1, Bias firstBias, Bias secondBias) {
		// TODO Auto-generated method stub

	}

	@Override
	public EditorKit getEditorKit(JTextComponent t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNextVisualPositionFrom(JTextComponent t, int pos, Bias b, int direction, Bias[] biasRet) throws BadLocationException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getRootView(JTextComponent t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle modelToView(JTextComponent t, int pos) throws BadLocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle modelToView(JTextComponent t, int pos, Bias bias) throws BadLocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int viewToModel(JTextComponent t, Point pt) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int viewToModel(JTextComponent t, Point pt, Bias[] biasReturn) {
		// TODO Auto-generated method stub
		return 0;
	}

}
