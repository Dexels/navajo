package com.dexels.navajo.tipi.flickr;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import org.w3c.dom.svg.*;
import org.xml.sax.*;

import com.aetrion.flickr.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.flickr.*;
import com.dexels.navajo.tipi.swing.svg.impl.*;

public class TipiFlickrConnector extends ComponentImpl {

	 


	private String loadUrl() {
		try {
			String url =  PhotoManager.getInstance().getUrl(new String[]{myTag}, index);
			index++;
			return url;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FlickrException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return null;
		
	}



}
