package com.dexels.navajo.server.monitoring;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;

public class JMXAgent implements javax.management.NotificationListener {

	public static void main(String [] args) {

	}

	private void echo(String s) {
		System.err.println(s);
	}

	public void handleNotification(Notification notification,
			Object handback) {
		echo("\nReceived notification:");
		echo("\tClassName: " + notification.getClass().getName());
		echo("\tSource: " + notification.getSource());
		echo("\tType: " + notification.getType());
		echo("\tMessage: " + notification.getMessage());
		if (notification instanceof AttributeChangeNotification) {
			AttributeChangeNotification acn =
				(AttributeChangeNotification) notification;
			echo("\tAttributeName: " + acn.getAttributeName());
			echo("\tAttributeType: " + acn.getAttributeType());
			echo("\tNewValue: " + acn.getNewValue());
			echo("\tOldValue: " + acn.getOldValue());
		}
	}
}
