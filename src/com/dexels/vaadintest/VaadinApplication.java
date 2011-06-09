package com.dexels.vaadintest;

import com.vaadin.Application;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class VaadinApplication extends Application {
	@Override
	public void init() {
		final Window mainWindow = new Window("Vaadintest Application");
		Label label = new Label("Hello Vaadin user");
		Button button = new Button("Boom!");
		button.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				mainWindow.showNotification("Bomp");
				Window uu = new Window("Show me the money!");
				uu.setModal(true);
				mainWindow.addWindow(uu);
			}
		});
		mainWindow.addComponent(label);
		mainWindow.addComponent(button);
		setMainWindow(mainWindow);
//		DialogBox db = new DialogBox();
//		db.set(new Label("Hoei"));
		
	}

}
