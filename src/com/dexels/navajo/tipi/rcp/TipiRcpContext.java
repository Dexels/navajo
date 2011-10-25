package com.dexels.navajo.tipi.rcp;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import tipi.TipiApplicationInstance;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;

public class TipiRcpContext extends TipiContext {

	private final Composite compositeParent;
	private static final long serialVersionUID = -8780826070195136886L;

	public TipiRcpContext(TipiApplicationInstance myApplication, Composite compositeParent, List<TipiExtension> preload, TipiContext parent) {
		super(myApplication, preload, parent);
		this.compositeParent = compositeParent;
        RowLayout rowLayout = new RowLayout();
        rowLayout.wrap = false;
        rowLayout.pack = false;
        rowLayout.justify = true;
        rowLayout.type = SWT.VERTICAL;
        rowLayout.marginLeft = 5;
        rowLayout.marginTop = 5;
        rowLayout.marginRight = 5;
        rowLayout.marginBottom = 5;
        rowLayout.spacing = 0;
        compositeParent.setLayout(rowLayout);
        Label hoempapa = new Label(this.compositeParent,SWT.NORMAL);
        System.err.println("Creating rcp context");
		hoempapa.setText("Shazam");
	}


	@Override
	public void runSyncInEventThread(Runnable r) {
		Display.getCurrent().syncExec(r);

	}

	@Override
	public void runAsyncInEventThread(Runnable r) {
		Display.getCurrent().asyncExec(r);
	}

	@Override
	public void setSplash(Object s) {

	}

	@Override
	public void clearTopScreen() {

	}

	@Override
	public void setSplashVisible(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSplashInfo(String s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showInfo(String text, String title) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showQuestion(String text, String title, String[] options)
			throws TipiBreakException {
		// TODO Auto-generated method stub

	}

}
