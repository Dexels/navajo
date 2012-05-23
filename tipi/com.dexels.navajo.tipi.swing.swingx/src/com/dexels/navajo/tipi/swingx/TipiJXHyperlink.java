package com.dexels.navajo.tipi.swingx;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.jdesktop.swingx.JXHyperlink;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;

public class TipiJXHyperlink extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = 7386071333508347015L;

	@Override
	public Object createContainer() {
		JXHyperlink p = new JXHyperlink();
		p.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					performTipiEvent("onActionPerformed", null, false);
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}
		});
		return p;
	}

	public final void setComponentValue(final String name, final Object object) {
		super.setComponentValue(name, object);
		runSyncInEventThread(new Runnable() {
			public void run() {

				if (name.equals("icon")) {
					if (object == null) {
						System.err.println("Ignoring null icon");
					} else {
						if (object instanceof URL) {
							((JXHyperlink) getContainer()).setIcon(getIcon(object));
						}
					}
				}
			}
		});
	}

	protected ImageIcon getIcon(Object u) {
		if (u == null) {
			return null;
		}
		if (u instanceof URL) {
			return new ImageIcon((URL) u);
		}
		if (u instanceof Binary) {
			Image i;
			try {
				i = ImageIO.read(((Binary) u).getDataAsStream());
				ImageIcon ii = new ImageIcon(i);
				return ii;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
