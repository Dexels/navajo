package com.dexels.navajo.tipi.swingx;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.JXLoginPanel.JXLoginDialog;
import org.jdesktop.swingx.action.*;

import com.dexels.navajo.tipi.components.swingimpl.*;

public class TipiJXTaskItem extends TipiSwingDataComponentImpl {

	@Override
	public Object createContainer() {
		JXTaskPane p = new JXTaskPane();
		p.setTitle("Unspecified title");
		return p;
	}
}
