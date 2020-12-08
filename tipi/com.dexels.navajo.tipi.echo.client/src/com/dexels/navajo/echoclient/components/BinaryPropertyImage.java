/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.echoclient.components;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.StreamImageReference;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.functions.scale.ImageScaler;

public class BinaryPropertyImage extends StreamImageReference {

	// private byte[] datas;

	private static final long serialVersionUID = -2372917153390080371L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(BinaryPropertyImage.class);
	private final Property myProperty;
	private final Binary myBinary;
	private static final int BUFFER_SIZE = 1000;
	private int height = 0;
	private int width = 0;

	public BinaryPropertyImage(Property p, int width, int height) {
		Binary b = (Binary) p.getTypedValue();
		myBinary = b;
		myProperty = p;
		this.height = height;
		this.width = width;
	}

	@Override
	public Extent getHeight() {
		return new Extent(height, Extent.PX);
	}

	@Override
	public Extent getWidth() {
		return new Extent(width, Extent.PX);
	}

	@Override
	public String getContentType() {
		if (myBinary == null) {

			return "image/gif";
		}
		String contentType = myBinary.guessContentType();
		logger.info("Binary image: Content Type: " + contentType);
		logger.info("Length: " + myBinary.getLength());
		if (contentType != null) {
			return contentType;
		}
		return "image/jpeg";
	}

	@Override
	public void render(OutputStream out) throws IOException {
		InputStream in = null;
		if (myBinary == null) {
			logger.info("Oh dear, bad binary");
			in = getClass()
					.getClassLoader()
					.getResource(
							"com/dexels/navajo/tipi/components/echoimpl/resource/image/bw_questionmark.gif")
					.openStream();
		} else {
			// Binary b = new Binary(myBinary.getDataAsStream(),false);

			Binary b = ImageScaler.scaleToMax(myBinary, width, height,"png");
			if (b != null) {
				logger.info("Good binary: rendering. Size: "
						+ myBinary.getLength() + " width: " + width
						+ " height: " + height);
				in = b.getDataAsStream();
				if (in == null) {
					return;
				}

			} else {
				logger.info("Trouble, not rendering");
				return;
			}
		}
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = 0;

		try {
			do {
				bytesRead = in.read(buffer);
				if (bytesRead > 0) {
					out.write(buffer, 0, bytesRead);
				}
			} while (bytesRead > 0);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
				}
			}
		}
	}

	@Override
	public String getRenderId() {
		try {
			if (myProperty != null) {
				return myProperty.getFullPropertyName()
						+ new Random(System.currentTimeMillis()).nextDouble();
			} else {
				return "unknown_property";

			}
		} catch (NavajoException e) {
			return "unknown_property";
		}
	}

}
