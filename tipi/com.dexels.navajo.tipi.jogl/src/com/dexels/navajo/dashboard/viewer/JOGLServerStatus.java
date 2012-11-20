package com.dexels.navajo.dashboard.viewer;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.net.URL;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.jogamp.opengl.util.awt.TextRenderer;

public class JOGLServerStatus {
//	private HashMap<String, String[]> authentication = new HashMap<String, String[]>();
	private TextRenderer textRenderer;
	Navajo status;
	Navajo serverLoad;
	String serverName = "";
	URL postman;
	
	private final static Logger logger = LoggerFactory
			.getLogger(JOGLServerStatus.class);
	

	public JOGLServerStatus() {
		Font font = new Font("SansSerif", Font.PLAIN, 18);
		textRenderer = new TextRenderer(font, true, false);
	}

	public void setStatusNavajo(Navajo n) {
		this.status = n;
	}

	public void setServerLoad(Navajo n) {
		this.serverLoad = n;
//		Message load = serverLoad.getMessage("Result");
	}

	public void setServerName(String name) {
		this.serverName = name;
	}

	public String getServerName() {
		return this.serverName;
	}

	public void setPostman(URL url) {
		this.postman = url;
	}

	/**
	 * @param username  
	 */
	public void setUsername(String username) {

	}

	/**
	 * @param password  
	 */
	public void setPassword(String password) {

	}

	/**
	 * @param mode  
	 */
	public void draw(GL gl1, float width, float height, int mode) {
		GL2 gl = gl1.getGL2();
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(0f, 0f, 0f, .9f);
		gl.glVertex2d(50, 50);
		gl.glVertex2d(50, height - 50);
		gl.glVertex2d(width - 50, height - 50);
		gl.glVertex2d(width - 50, 50);
		gl.glEnd();

		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glColor4f(1f, 1f, 1f, 1f);
		gl.glLineWidth(10f);
		gl.glVertex2d(50, 50);
		gl.glVertex2d(50, height - 50);
		gl.glVertex2d(width - 50, height - 50);
		gl.glVertex2d(width - 50, 50);
		gl.glEnd();

		drawString(gl, serverName, 100, (int) (height - 100), Color.white, 1f, 1f);

		if (status != null) {
			Message statusMessage = status.getMessage("NavajoStatus").getMessage("Kernel");
			if(postman != null){
				drawString(gl, postman.toString(), 100, (int) (height - 200), Color.green, 1f, 1f);
			}

			drawString(gl, statusMessage.getProperty("Now").getValue(), 100, (int) (height - 120), Color.white, 1f, 1f);

			// Labels
			drawString(gl, "Totaal verzoeken", 100, (int) (height - 220), Color.gray, 1f, 1f);
			drawString(gl, "Verzoekfrequentie", 100, (int) (height - 240), Color.gray, 1f, 1f);
			drawString(gl, "Aantal gebruikers", 100, (int) (height - 260), Color.gray, 1f, 1f);
			drawString(gl, "Uitgever", 100, (int) (height - 280), Color.gray, 1f, 1f);
			drawString(gl, "Versie", 100, (int) (height - 300), Color.gray, 1f, 1f);
			drawString(gl, "Server start", 100, (int) (height - 320), Color.gray, 1f, 1f);

			Float rr = Float.parseFloat(statusMessage.getProperty("RequestRate").getValue());
			double rounded = (int) (10.0 * rr) / 10.0;

			drawString(gl, statusMessage.getProperty("TotalRequests").getValue(), 250, (int) (height - 220), Color.white, 1f, 1f);
			drawString(gl, rounded + " v/s", 250, (int) (height - 240), Color.white, 1f, 1f);
			drawString(gl, "" + statusMessage.getMessage("Users").getArraySize(), 250, (int) (height - 260), Color.white, 1f, 1f);
			drawString(gl, statusMessage.getProperty("Vendor").getValue(), 250, (int) (height - 280), Color.white, 1f, 1f);
			drawString(gl, statusMessage.getProperty("Version").getValue(), 250, (int) (height - 300), Color.white, 1f, 1f);
			drawString(gl, statusMessage.getProperty("StartTime").getValue() + "(up: " + statusMessage.getProperty("Uptime").getValue() + " dag(en)", 250, (int) (height - 320), Color.white, 1f, 1f);
		}
		if (serverLoad != null) {
			drawServerLoad(gl, width, height);
		}
	}

	private void drawServerLoad(GL gl1, float width, float height) {
		GL2 gl = gl1.getGL2();
		Message load = serverLoad.getMessage("Result");
		float x_offset = 100f;
		float y_offset = 100f;
		int max_index = determineMaxAt(load); 
		float max = Float.parseFloat(load.getMessage(max_index).getProperty("ServerLoad").getValue());
		String maxTime = load.getMessage(max_index).getProperty("TimeStamp").getValue();
		float max_height = height / 4f;
		float multiplier = max_height / max;
		float width_factor = (width - (2 * x_offset)) / load.getArraySize();

		gl.glPushMatrix();

		// Draw the background for the graph
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(1f, 1f, 1f, .1f);
		gl.glVertex2d(x_offset, y_offset);
		gl.glVertex2d(width - x_offset, y_offset);
		gl.glVertex2d(width - x_offset, 1.5 * y_offset + max_height);
		gl.glVertex2d(x_offset, 1.5 * y_offset + max_height);
		gl.glEnd();
		
		// Draw the index lines, ten, which is the number of max threads
		int max_threads = 10;
		float y_skip = (max_height) / max_threads;		
		gl.glBegin(GL.GL_LINES);
		gl.glColor4f(0f, 0f, 0f, .4f);
		for(int i=0;i<max_threads;i++){
			gl.glVertex2d(x_offset, y_offset + i*y_skip);
			gl.glVertex2d(width - x_offset, y_offset + i*y_skip);		
		}
		gl.glEnd();
		
		
		// Draw the bounding box for the graph
		gl.glBegin(GL2.GL_LINE_LOOP);
		gl.glColor4f(1f, 1f, 1f, .6f);
		gl.glVertex2d(x_offset, y_offset);
		gl.glVertex2d(width - x_offset, y_offset);
		gl.glVertex2d(width - x_offset, 1.5 * y_offset + max_height);
		gl.glVertex2d(x_offset, 1.5 * y_offset + max_height);
		gl.glEnd();

		// Draw the actual graph
		gl.glBegin(GL2.GL_QUADS);
		for (int i = load.getArraySize() - 1; i >= 0; i--) {
			float value = Float.parseFloat(load.getMessage(i).getProperty("ServerLoad").getValue());

			gl.glColor4f(1f, 1f, 0f, .8f);
			gl.glVertex2d(x_offset + width_factor * i, y_offset);
			gl.glVertex2d(width_factor - 1f + x_offset + width_factor * i, y_offset);
			gl.glColor4f(1f, 0f, 0f, .8f);
			gl.glVertex2d(width_factor - 1f + x_offset + width_factor * i, y_offset + multiplier * value);
			gl.glVertex2d(x_offset + width_factor * i, y_offset + multiplier * value);

		}
		gl.glEnd();
		
		// Lines test, for users (threadcount), ten threads is considered the max;
		
		multiplier = max_height / max_threads;
		gl.glBegin(GL.GL_LINES);
		float prev_value = Float.parseFloat(load.getMessage(load.getArraySize()-1).getProperty("ThreadCount").getValue());
		gl.glColor4f(1f, 1f, 1f, .9f);		
		for (int i = load.getArraySize() - 1; i >= 0; i--) {			
			if(i < load.getArraySize()-1){
				prev_value = Float.parseFloat(load.getMessage(i+1).getProperty("ThreadCount").getValue());			
			}
			float value = Float.parseFloat(load.getMessage(i).getProperty("ThreadCount").getValue());	
			if(value > max_threads){
				value = max_threads;
			}
			if(prev_value > max_threads){
				prev_value = max_threads;
			}
			
			gl.glVertex2d(width_factor/2f + x_offset + width_factor * (i+1 + (i < load.getArraySize()-1?0:-1)), y_offset + multiplier * prev_value);
			gl.glVertex2d(width_factor/2f + x_offset + width_factor * i, y_offset + multiplier * value);
		}
		gl.glEnd();
		
		// Daw users index
		for(int i=0;i<=max_threads;i++){
			drawString(gl, "" + i, (int)(width - x_offset + 5), (int)(y_offset + i*y_skip), Color.white, .8f, .6f);
		}
		
		// Draw Max line
		gl.glBegin(GL.GL_LINES);
		gl.glColor4f(1f, 0f, 0f, .5f);		
		gl.glVertex2d(x_offset, y_offset + max_height);
		gl.glVertex2d(width - x_offset, y_offset + max_height);		
		gl.glEnd();

		gl.glPopMatrix();
		

		try {
			String last = load.getMessage(load.getArraySize()-1).getProperty("TimeStamp").getValue();
			float text_scale = .8f;
			Rectangle2D bounds = textRenderer.getBounds(last);
			drawString(gl, last, (int) (width - text_scale*bounds.getWidth() - x_offset), 85, Color.white, .8f, text_scale);
			
			String first = load.getMessage(0).getProperty("TimeStamp").getValue(); 
			bounds = textRenderer.getBounds(first);
			drawString(gl, first, (int) (x_offset), 85, Color.white, .8f, text_scale);
			
			String text = "Load history";
			bounds = textRenderer.getBounds(text);
			drawString(gl, text, (int) (x_offset + 10), (int)(1.5 * y_offset + max_height - text_scale*bounds.getHeight()), Color.white, .8f, text_scale);
			
			drawString(gl, "max: "+max + " v/s, at " + maxTime, (int)(x_offset + 10), (int)(y_offset + max_height + 5), Color.white, .8f, .6f);
		} catch (Exception e) {
			logger.error("Error: ",e);
		}

	}

	private int determineMaxAt(Message stats) {
		float max = 0;
		int index = -1;
		for (int i = 0; i < stats.getArraySize(); i++) {
			float value = Float.parseFloat(stats.getMessage(i).getProperty("ServerLoad").getValue());
			if(value > max){
				index = i;
			}
			max = Math.max(max, value);
			
		}
		return index;
	}
	
	

	/*
	 * Draw a string in OpenGL
	 */
	/**
	 * @param gl  
	 */
	private void drawString(GL gl, String text, int xpos, int ypos, Color color, float alpha, float scale) {
		float r = color.getRed() / 255f;
		float g = color.getGreen() / 255f;
		float b = color.getBlue() / 255f;
		textRenderer.begin3DRendering();

		textRenderer.setColor(r, g, b, alpha);
		textRenderer.draw3D(text, xpos, ypos, -1f, scale);
		textRenderer.end3DRendering();
	}
}
