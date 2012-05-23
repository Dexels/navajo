package com.dexels.navajo.tipi.jabber.lockimpl.test;

import java.beans.*;

import org.jivesoftware.smack.*;

import com.dexels.navajo.tipi.jabber.lockimpl.*;

public class TestLock {

	/**
	 * @param args
	 * @throws XMPPException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws XMPPException, InterruptedException {
		King k = new King();
		runDruif("Albert", 1000, 3000, k);
		runDruif("Bert", 500, 2000, k);
		runDruif("Charlie", 3000, 2000, k);
		runDruif("Dirk", 3000, 5000, k);

		Thread.sleep(6000);
	}

	/**
	 * @return
	 * @throws XMPPException
	 * @throws InterruptedException
	 */
	private static void runDruif(final String name, final int before, final int timeout, final King k) throws XMPPException,
			InterruptedException {
		new Thread() {
			public void run() {
				final JabberLockImpl l = new JabberLockImpl();
				try {
					l.connect("spiritus.dexels.nl", 5222, "conference", "dexels.nl", name);
				} catch (XMPPException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				l.debug("Hello!");
				try {
					Thread.sleep(before);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				l.debug("I want to be king now!");
				boolean b = l.setLockRequest("king");

				if (!b) {
					l.debug("I am no king :-(");
				} else {
					l.debug("I am king! :-) (I used to be " + l.getPreferredNick() + ")");
					k.setKing(l.getPreferredNick());
				}
				l.addLockingListener(new PropertyChangeListener() {

					public void propertyChange(PropertyChangeEvent evt) {
						System.err.println("L :" + name + " Prop: " + evt);

						boolean oldValue = (Boolean) evt.getOldValue();
						boolean newValue = (Boolean) evt.getNewValue();
						if (oldValue == newValue) {
							l.debug("I think nothing happened  :-|");
						}
						if (newValue) {
							l.debug("I think " + name + " is king now! ?:|");
						} else {
							l.debug("I think " + name + " is no longer king! ?:|");
						}
					}
				});

				try {
					Thread.sleep(timeout);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (l.isLocked()) {
					k.removeKing(l.getPreferredNick());
					l.debug(name + " no longer wants to be king. I want to be " + l.getPreferredNick() + " again");
					l.unlock();
				} else {
					l.debug("I've never been king! :-( :-(");
				}
				l.debug("Bye");
				l.disconnect();
			}

		}.start();

	}

}
