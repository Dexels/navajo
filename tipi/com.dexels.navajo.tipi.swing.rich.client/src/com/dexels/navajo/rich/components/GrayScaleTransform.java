/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.rich.components;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;

import javax.imageio.ImageIO;

public class GrayScaleTransform {

	public static BufferedImage getFadedImage(BufferedImage input, float factor) {
		try {
			BufferedImage grey = null;
			ColorSpace cp = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp cco = new ColorConvertOp(cp, null);
			grey = cco.filter(input, null);

			BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = output.createGraphics();

			if (factor > 1.0) {
				factor = 1.0f;
			}
			g2.drawImage(input, 0, 0, input.getWidth(), input.getHeight(), null);
			g2.setComposite(AlphaComposite.SrcOver.derive(factor));
			g2.drawImage(grey, 0, 0, grey.getWidth(), grey.getHeight(), null);

			return output;

		} catch (Exception e) {
			return input;
		}
	}

	public static void main(String[] argh) {
		try {
			BufferedImage source = ImageIO.read(new File("c:/test.png"));
			for (float f = 0.0f; f < 1.1; f += 0.1f) {
				ImageIO.write(GrayScaleTransform.getFadedImage(source, f), "png", new File("c:/fade" + (int) (10 * f)
						+ ".png"));
			}
		} catch (Exception e) {
		}
	}
}