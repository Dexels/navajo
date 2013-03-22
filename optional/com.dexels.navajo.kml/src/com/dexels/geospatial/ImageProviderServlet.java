package com.dexels.geospatial;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class ImageProviderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	public ImageProviderServlet(){		
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int width = 500;
		int height = 250;
		Color foreground = Color.white;
		int fontSize = 12;
		int lineHeight = 12;
		String[] lines = new String[5];
		boolean debug = false;
		
		Enumeration<String> parms = req.getParameterNames();
		while(parms.hasMoreElements()){
			String name = parms.nextElement();
			String value = req.getParameter(name); 
			if("w".equals(name)){
				width = Integer.parseInt(value);
			}
			if("h".equals(name)){
				height = Integer.parseInt(value);
			}
			if("foreground".equals(name)){
				foreground = Color.decode("#" + value);
			}
			if("fontsize".equals(name)){
				fontSize = Integer.parseInt(value);
			}
			if("lh".equals(name)){
				lineHeight = Integer.parseInt(value);
			}
			if("l1".equals(name)){
				lines[0] = value;
			}
			if("l2".equals(name)){
				lines[1] = value;
			}
			if("l3".equals(name)){
				lines[2] = value;
			}
			if("l4".equals(name)){
				lines[3] = value;
			}
			if("l5".equals(name)){
				lines[4] = value;
			}
			if("debug".equals(name)){
				debug = "1".equals(value);
			}
		}
	
		BufferedImage img = constructImage(width, height, fontSize, lineHeight, lines, foreground, debug);
		resp.setContentType("image/png");
		resp.setStatus(HttpServletResponse.SC_OK);	
		OutputStream out = resp.getOutputStream();
		ImageIO.write(img, "png", out);		
	}
	
	
	private BufferedImage constructImage(int width, int height, int fontSize, int lineHeight, String[] lines, Color foreground, boolean debug){
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)bi.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Font f = new Font("Dialog", Font.BOLD, fontSize);
		g.setFont(f);
		g.setColor(foreground);
		JComponent c = new JLabel();
		FontMetrics fm = c.getFontMetrics(f);
		Rectangle2D bounds = null;
		
		int linesWithText = 0;
		for(int i=0;i<lines.length;i++){
//			int y = lineHeight + i*lineHeight;
//			int x = 5;			
			if(lines[i] != null){
				if(bounds == null){
					bounds = fm.getStringBounds(lines[i], g);
				}else{
					Rectangle2D.union(bounds, fm.getStringBounds(lines[i], g), bounds);
				}
				linesWithText++;
			}
		}		
		if(bounds==null) {
			return bi;
		}
		
		int x = (int)((width / 2.0) - (bounds.getWidth() / 2));
		double y_offset = (height / 2.0); 
		
		if(linesWithText > 2){
			y_offset = y_offset - (linesWithText * lineHeight / 2.0);
		}
		if(linesWithText == 1){
			y_offset = y_offset + 0.5 * lineHeight;
		}
		
		if(debug){
			g.setColor(Color.red);
			g.fillRect(0, 0, width-1, height-1);
			g.setColor(Color.green);			
		}
		
		int index = 0;
		for(int i=0;i<lines.length;i++){
			int y = (int)(y_offset + lineHeight*index);	
			if(lines[i] != null){
				g.drawString(lines[i], x, y);
				index++;
			}
		}	
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		return bi;
	}

	
}
