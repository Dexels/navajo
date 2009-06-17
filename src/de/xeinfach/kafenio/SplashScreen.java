package de.xeinfach.kafenio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * Class that displays a splash screen
 * Is run in a separate thread so that the applet continues to load in the background
 * @author Karsten Pawlik
 */
public class SplashScreen extends JWindow implements Runnable {
	private static int timeout = 3000;

	/**
	 * Default constructor, sets a default time of 3000 milliseconds to display
	 */
	public SplashScreen() {
		this(timeout);
	}

	/**
	 * creates a new splash screen using the given values.
	 * @param newTimeout maximum timeout in seconds. (can be interrupted by calling the destroy-method
	 */
	public SplashScreen(int newTimeout) {
		try {
			timeout = newTimeout;
			if (timeout > 0) {
				JPanel panel = new JPanel(new BorderLayout());
				ImageIcon icon = new ImageIcon(SplashScreen.class.getResource("kafenio_editor_splash.gif"));
				panel.add(new JLabel(icon), BorderLayout.CENTER);
				getContentPane().add(panel);
				pack();
				Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
				setLocation((int) (d.getWidth() - getWidth()) / 2, (int) (d.getHeight() - getHeight()) / 2);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if (!isShowing()) {
			show();
		}
		try {
			int secs = 1;
			while (secs <= timeout) {
				for(int k=0; k < 1000; k++) { 
					Thread.sleep(1);
				}
				secs++;
			}
			destroy();
		}
		catch (InterruptedException e) {
		}
	}
	
	/**
	 * destroys the splashscreen
	 */
	public void destroy() {
		dispose();
	}
}
