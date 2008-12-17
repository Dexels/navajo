package com.dexels.navajo.rich.components;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.image.renderable.*;
import java.io.*;

import javax.imageio.*;
import javax.media.jai.*;

public class FrameIt {

	public static void main(String[] args) {
		try {
			int framesize = 10;
			double angle = 14;
			Color frameColor = Color.white;
			BufferedImage test = ImageIO.read(new File("C:/Documents and Settings/Frank Lyaruu/Mijn documenten/Mijn afbeeldingen/minichroc.jpg"));
			BufferedImage framed = new BufferedImage(test.getWidth() + 2 * framesize, test.getHeight() + 2 * framesize,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = framed.createGraphics();
			g.setColor(frameColor);
			g.fillRect(0, 0, framed.getWidth(), framed.getHeight());
			g.drawImage(test, framesize, framesize, test.getWidth(), test.getHeight(), null);
			SoftwarePerspectiveTransform.setQ(1);
//			framed = SoftwarePerspectiveTransform.transform(framed, angle, true, SoftwarePerspectiveTransform.FLIP_RIGHT);
			
			
//			Warp warpPerspective = new WarpPerspective(getPerspectiveTransform(angle, new Dimension(framed.getWidth(),framed.getHeight())));
			Dimension size = new Dimension(framed.getWidth(),framed.getHeight());
			System.err.println("SIZE: "+size);
			Warp warpPerspective = new WarpPerspective(getPerspectiveTransform(angle, size));
			RenderedOp rop = createWarpImage(framed, warpPerspective);
			
			
			PlanarImage pa = rop.createInstance();
			
			ImageIO.write(pa, "png", new File("C:/Documents and Settings/Frank Lyaruu/Mijn documenten/Mijn afbeeldingen/minichroc2.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//  ulx: radius - Math.sin(t)*radius     uly: 0 - Math.cos(t)*back
//  urx: radius + Math.sin(t)*radius     ury: 0 + Math.cos(t)*back
//  lrx: radius + Math.sin(t)*radius     lry: height - Math.cos(t)*back
//  llx: radius - Math.sin(t)*radius     lly: height + Math.cos(t)*back

	private static PerspectiveTransform  getPerspectiveTransform(double t, Dimension size) {
		
		PerspectiveTransform tp = new PerspectiveTransform();
//		tp.setToRotation(t);
//		if(true) {
//			return tp.getSquareToQuad(0, 0, t, t, t, t, t, t).scale(size.getWidth(), size.getHeight());
//		}
//		
//		tp.setToScale(1/size.getWidth()*10, 1/size.getHeight()*10);
//		if(true) {
//		return tp; //.getSquareToQuad(0, 0, t, t, t, t, t, t).scale(size.getWidth(), size.getHeight());
//	}
		double radius = size.getWidth() / 2;
		double back = size.getHeight() / 10;
		System.err.println("Size: "+size);
		double x0 = 0;
		double y0 = 0;
		double x1 = size.getWidth();
		double y1 = 0;
		double x2 = size.getWidth();
		double y2 = size.getHeight();
		double x3 = 0;
		double y3 = size.getHeight();
		
		
//		double x0p = Math.max(0,radius - Math.sin(t)*radius);
//		double y0p = Math.max(0,0 - Math.cos(t)*back);
//		
//		double x1p = Math.max(0,radius - Math.sin(t)*radius);
//		double y1p = Math.max(0,0 + Math.cos(t)*back);
//		
//		double x2p = Math.max(0,radius + Math.sin(t)*radius);
//		double y2p = Math.max(0,size.getHeight() - Math.cos(t)*back);
//		
//		double x3p = Math.max(0,radius - Math.sin(t)*radius);
//		double y3p = Math.max(0,size.getHeight() + Math.cos(t)*back);
//		PerspectiveTransform quadToQuad = new  PerspectiveTransform(new double[]{t,x0p,y0p,x1p,y1p,x2p,y2p,x3p,y3p}); // PerspectiveTransform.getQuadToQuad(x0,y0,x1,y1,x2,y2,x3,y3, 
//				     x0p,y0p,x1p,y1p,x2p,y2p,x3p,y3p);
//		quadToQuad.scale(size.getWidth(), size.getHeight());
//		PerspectiveTransform quadToQuad = new  PerspectiveTransform(new double[]{x0p,y0p,x1p,y1p,x2p,y2p,x3p,y3p});
//
		double x0p = 10;
		double y0p = 10;
		
		double x1p = 50;
		double y1p = 50;
		
		double x2p = 10;
		double y2p = 50;
		
		double x3p = 50;
		double y3p = 100;

		
		PerspectiveTransform quadToQuad = PerspectiveTransform.getQuadToQuad(x0,y0,x1,y1,x2,y2,x3,y3,x0p, y0p, x1p, y1p, x2p, y2p, x3p, y3p);
	//	quadToQuad.preConcatenate(AffineTransform.getScaleInstance(size.getWidth(), size.getHeight()));
		return quadToQuad;
	}

	public static RenderedOp createWarpImage(
			RenderedImage img,
			Warp warp){
			ParameterBlock pb = new ParameterBlock();
			pb.addSource(img);
			pb.add(warp);
			pb.add(Interpolation.getInstance(Interpolation.INTERP_NEAREST));
			return JAI.create("warp", pb);
			}
}
