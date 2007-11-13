package com.dexels.navajo.tipi.xmmp.swingimpl;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.dexels.navajo.tipi.xmmp.*;


public class SwingChatComponent extends JPanel {
	JTextArea in = new JTextArea();
	JTextField out = new JTextField();
	JButton send = new JButton();
	JButton connect = new JButton();
	MessageSender myMessageSender = null;

	public SwingChatComponent() {
		in.setRows(5);
		send.setText("Send");
		setLayout(new BorderLayout());
		add(in,BorderLayout.CENTER);
		JPanel rest = new JPanel();
		add(rest,BorderLayout.SOUTH);
		rest.setLayout(new BorderLayout());
		rest.add(out,BorderLayout.CENTER);
		rest.add(send,BorderLayout.EAST);
		rest.add(connect,BorderLayout.WEST);
		send.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(myMessageSender!=null) {
					myMessageSender.messageSent(out.getText());
					appendMessage(">"+out.getText()+"<");
				}
			}});
		connect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
			}});
		}
	
	public void setMessageListener(MessageSender ms) {
		myMessageSender = ms;
	}
	
	public void appendMessage(String m) {
		in.insert(m+"\n",in.getText().length());
	}

}
