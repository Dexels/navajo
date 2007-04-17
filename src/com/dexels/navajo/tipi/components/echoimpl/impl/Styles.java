/* 
 * This file is part of the Echo Web Application Framework (hereinafter "Echo").
 * Copyright (C) 2002-2005 NextApp, Inc.
 *
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 */

package com.dexels.navajo.tipi.components.echoimpl.impl;

import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.StyleSheet;
import nextapp.echo2.app.componentxml.ComponentXmlException;
import nextapp.echo2.app.componentxml.StyleSheetLoader;

/**
 * Look-and-feel information.
 */
public class Styles {

	public static final String IMAGE_PATH = "/com/dexels/navajo/tipi/components/echoimpl/resource/image/";
//	public static final String STYLE_PATH = "/com/dexels/navajo/tipi/components/echoimpl/resource/style/";
	public static final String STYLE_PATH = "/";

	/**
	 * Default application style sheet.
	 */
	public static final StyleSheet DEFAULT_STYLE_SHEET;
	static {
		try {
	         DEFAULT_STYLE_SHEET = StyleSheetLoader.load(STYLE_PATH + "Default.stylesheet", 
                    Thread.currentThread().getContextClassLoader());
  			if(DEFAULT_STYLE_SHEET!=null) {
  				System.err.println("LOADED STYLESHEET: "+DEFAULT_STYLE_SHEET.toString());
  			}
 //			DEFAULT_STYLE_SHEET = StyleSheetLoader.load("Default.stylesheet", Thread.currentThread().getContextClassLoader());
		} catch (ComponentXmlException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	// Images
	public static final ImageReference NEXTAPP_LOG_IMAGE = new ResourceImageReference(IMAGE_PATH + "NextAppLogo.png");
	public static final ImageReference ECHO2_IMAGE = new ResourceImageReference(IMAGE_PATH + "Echo2.png");
	public static final ImageReference SPORTLINK_IMAGE = new ResourceImageReference(IMAGE_PATH + "sportlinklogo.gif");
	public static final ImageReference DEXELS_IMAGE = new ResourceImageReference(IMAGE_PATH + "logodexels.gif");
	public static final ImageReference DEXELS_BACKGROUND = new ResourceImageReference(IMAGE_PATH + "dexbackground.png");
	public static final ImageReference WEBMAIL_EXAMPLE_IMAGE = new ResourceImageReference(IMAGE_PATH + "WebMailExample.png");
	public static final ImageReference ICON_24_LEFT_ARROW = new ResourceImageReference(IMAGE_PATH + "navigate_left.png");
	public static final ImageReference ICON_24_RIGHT_ARROW = new ResourceImageReference(IMAGE_PATH + "navigate_right.png");
	public static final ImageReference ICON_24_LEFT_ARROW_DISABLED = new ResourceImageReference(IMAGE_PATH + "navigate_left.png");
	public static final ImageReference ICON_24_RIGHT_ARROW_DISABLED = new ResourceImageReference(IMAGE_PATH + "navigate_right.png");
	public static final ImageReference ICON_24_LEFT_ARROW_ROLLOVER = new ResourceImageReference(IMAGE_PATH + "navigate_left.png");
	public static final ImageReference ICON_24_RIGHT_ARROW_ROLLOVER = new ResourceImageReference(IMAGE_PATH	+ "navigate_right.png");
//	public static final ImageReference ICON_24_EXIT = new ResourceImageReference(IMAGE_PATH + "Icon24Exit.gif");
	public static final ImageReference ICON_24_EXIT = new ResourceImageReference(IMAGE_PATH + "exit.png");
	public static final ImageReference ICON_24_SUBSCRIPTIONS = new ResourceImageReference(IMAGE_PATH + "note.png");
	public static final ImageReference ICON_24_ADD_SUBSCRIPTION = new ResourceImageReference(IMAGE_PATH + "note_add.png");
	public static final ImageReference ICON_24_UPDATE_SUBSCRIPTION = new ResourceImageReference(IMAGE_PATH + "note_new.png");		
	public static final ImageReference ICON_24_DELETE_SUBSCRIPTION = new ResourceImageReference(IMAGE_PATH + "note_delete.png");
	public static final ImageReference ICON_24_ADD_ADDRESS = new ResourceImageReference(IMAGE_PATH + "mail_add.png");
	public static final ImageReference ICON_24_DELETE_ADDRESS = new ResourceImageReference(IMAGE_PATH + "mail_delete.png");
	public static final ImageReference ICON_24_ADDRESSES = new ResourceImageReference(IMAGE_PATH + "mail.png");
	public static final ImageReference ICON_24_EDIT_PASSWORD = new ResourceImageReference(IMAGE_PATH + "id_card.png");
	public static final ImageReference ICON_24_MAIL_REPLY = new ResourceImageReference(IMAGE_PATH + "Icon24MailReply.gif");
//	public static final ImageReference ICON_24_REFRESH = new ResourceImageReference(IMAGE_PATH + "Icon24Refresh.gif");
	public static final ImageReference ICON_24_REFRESH = new ResourceImageReference(IMAGE_PATH + "recycle.png");
	public static final ImageReference ICON_24_DELETE = new ResourceImageReference(IMAGE_PATH + "garbage.png");	
	public static final ImageReference ICON_24_MAIL_COMPOSE = new ResourceImageReference(IMAGE_PATH + "Icon24MailCompose.gif");
//	public static final ImageReference ICON_24_NO = new ResourceImageReference(IMAGE_PATH + "Icon24No.gif");
	public static final ImageReference ICON_24_NO = new ResourceImageReference(IMAGE_PATH + "navigate_cross_red.png");
//	public static final ImageReference ICON_24_YES = new ResourceImageReference(IMAGE_PATH + "Icon24Yes.gif");
	public static final ImageReference ICON_24_YES = new ResourceImageReference(IMAGE_PATH + "navigate_check_green.png");
}
