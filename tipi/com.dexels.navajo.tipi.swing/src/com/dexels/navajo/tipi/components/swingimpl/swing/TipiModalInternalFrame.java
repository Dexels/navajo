package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TipiModalInternalFrame extends JExtendedInternalFrame {

	private static final long serialVersionUID = -5790556670928088082L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiModalInternalFrame.class);
	
	public TipiModalInternalFrame(String title, JRootPane rootPane,
			Component desktop, Component contentComponent, Dimension size) {
		super(title, false, true, false, false);
		Dimension rootSize = desktop.getSize();
		setBounds((rootSize.width - size.width) / 2,
				(rootSize.height - size.height) / 2, size.width, size.height);
	}

	public static void main(String args[]) {
		final JFrame frame = new JFrame("Modal Internal Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JDesktopPane desktop = new JDesktopPane();

		ActionListener showModal = new ActionListener() {
			Integer ZERO = new Integer(0);
			Integer ONE = new Integer(1);

			public void actionPerformed(ActionEvent e) {

				// Manually construct an input popup
				JOptionPane optionPane = new JOptionPane("Print?",
						JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);

				// Construct a message internal frame popup
				JInternalFrame modal = new TipiModalInternalFrame(
						"Really Modal", frame.getRootPane(), desktop,
						optionPane, new Dimension(200, 200));

				modal.setVisible(true);

				Object value = optionPane.getValue();
				if (value.equals(ZERO)) {
					logger.info("Selected Yes");
				} else if (value.equals(ONE)) {
					logger.info("Selected No");
				} else {
					logger.debug("Input Error");
				}
			}
		};

		JInternalFrame internal = new JInternalFrame("Opener");
		desktop.add(internal);

		JButton button = new JButton("Open");
		button.addActionListener(showModal);

		Container iContent = internal.getContentPane();
		iContent.add(button, BorderLayout.CENTER);
		internal.setBounds(25, 25, 200, 100);
		internal.setVisible(true);

		Container content = frame.getContentPane();
		content.add(desktop, BorderLayout.CENTER);
		frame.setSize(500, 300);
		frame.setVisible(true);
	}

	public static void showInternalMessage(JRootPane rootPane,
			Component defaultDesktop, String title, String text, int poolSize,
			int messageType) {
		JOptionPane.showInternalMessageDialog(defaultDesktop, text, title,
				messageType);
	}
}