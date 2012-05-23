package com.dexels.rdf.visualization;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;


public class RDFGraphPainter {
	private final float TEXT_LARGE = 1f;
	private final float TEXT_MEDIUM = .75f;
	private final float TEXT_SMALL = .65f;
	private int max_depth = 4;
	RDFGraph graph;
	PointRotationPanel rt;
	private Color predicateTextColor = Color.black;
	private Color objectTextColor = Color.black;

	public RDFGraphPainter(RDFGraph graph) {
		this.graph = graph;
	}

	public void setMaxDepth(int depth) {
		this.max_depth = depth;
		graph.repaintCanvas();
	}

	public void paintRDFOBject(GL gl1, int mode, RDFObject rdfObject) {
		GL2 gl = gl1.getGL2();
		float cx = (int) graph.width / 2;
		float cy = (int) graph.height / 2;
		float text_scale = TEXT_LARGE;
		float radius = graph.radius;
		int inset_x = 25;
		int inset_y = 10;

		if (rdfObject == null) {
			String text = "no object loaded";
			Rectangle2D.Double bounds = (Rectangle2D.Double) graph.textRenderer.getBounds(text);
			graph.drawString(gl, text, (int) (cx - (text_scale * bounds.width / 2)), (int) (cy - (text_scale * bounds.height / 2)), Color.black, 1.0f, text_scale);
			return;
		}

		if (mode == GL2.GL_SELECT) {
			gl.glLoadName(rdfObject.getId());
		}

		int child_count = rdfObject.getObjects().size() + rdfObject.getSubjects().size();
		rt = new PointRotationPanel(child_count);
		rt.setScale(1f, 1f);

		ArrayList<Point2D.Double> locations = rt.getPoints(.8);
		int index = 0;

		// Draw object around it
		for (int i = 0; i < rdfObject.getObjects().size(); i++) {
			text_scale = TEXT_MEDIUM;
			RDFObject o = rdfObject.getObjects().get(i);
			ArrayList<String> connections = rdfObject.getConnectionsForObject(o);
			Point2D.Double loc = locations.get(index);

			// Draw connector
			gl.glBegin(GL.GL_LINES);
			gl.glColor4f(0f, 0f, 0f, 0f);
			gl.glVertex2d(cx, cy);
			gl.glColor4f(0f, 0f, 0f, .5f);
			gl.glVertex2d(cx - radius * loc.x, cy - radius * loc.y);
			gl.glEnd();

			index++;

			if (mode == GL2.GL_SELECT) {
				gl.glPushName(o.getId());
			}

			String txt = o.getName();
			Rectangle2D.Double b = (Rectangle2D.Double) graph.textRenderer.getBounds(txt);
			drawRect(gl, (int) (cx - radius * loc.x - (text_scale * b.width / 2)) - inset_x, (int) (cy - radius * loc.y - (text_scale * b.height / 2)) - inset_y, text_scale * b.width + 2 * inset_x, text_scale * b.height + 2 * inset_y, Color.decode("#75c5e7"), .8f);
			graph.drawString(gl, txt, (int) (cx - radius * loc.x - (text_scale * b.width / 2)), (int) (cy - radius * loc.y - (text_scale * b.height / 2)), objectTextColor, 1.0f, text_scale);

			// Draw connection info
			String connection = "";
			if (connections.size() > 0) {
				connection = connections.get(0);
				if (connections.size() > 1) {
					for (int j = 1; j < connections.size(); j++) {
						connection = connection + " / " + connections.get(j);
					}
				}
			}
			text_scale = TEXT_SMALL;
			b = (Rectangle2D.Double) graph.textRenderer.getBounds(connection);
			gl.glTranslated((cx - 0.5 * radius * loc.x), (cy - 0.5 * radius * loc.y), 0);
//			graph.drawString(gl, connection, (int) (cx - 0.5 * radius * loc.x - (text_scale * b.width / 2)), (int) (cy - 0.5 * radius * loc.y - (text_scale * b.height / 2)), Color.black, 1.0f, text_scale);
			graph.drawString(gl, connection, (int) - (text_scale * b.width / 2), (int)  - (text_scale * b.height / 2), predicateTextColor, 1.0f, text_scale);
			gl.glTranslated(-(cx - 0.5 * radius * loc.x), -(cy - 0.5 * radius * loc.y), 0);
			if (mode == GL2.GL_SELECT) {
				gl.glPopName();
			}
		}

		// Draw subjects around it
		for (int i = 0; i < rdfObject.getSubjects().size(); i++) {
			text_scale = TEXT_MEDIUM;
			RDFObject o = rdfObject.getSubjects().get(i);
			ArrayList<String> connections = rdfObject.getConnectionsForObject(o);
			Point2D.Double loc = locations.get(index);

			// Draw connector
			gl.glBegin(GL.GL_LINES);
			gl.glColor4f(0f, 0f, 0f, .5f);
			gl.glVertex2d(cx, cy);
			gl.glColor4f(0f, 0f, 0f, 0f);
			gl.glVertex2d(cx - radius * loc.x, cy - radius * loc.y);
			gl.glEnd();

			index++;

			if (mode == GL2.GL_SELECT) {
				gl.glPushName(o.getId());
			}

			String txt = o.getName();
			Rectangle2D.Double b = (Rectangle2D.Double) graph.textRenderer.getBounds(txt);
			drawRect(gl, (int) (cx - radius * loc.x - (text_scale * b.width / 2)) - inset_x, (int) (cy - radius * loc.y - (text_scale * b.height / 2)) - inset_y, text_scale * b.width + 2 * inset_x, text_scale * b.height + 2 * inset_y, Color.decode("#bcff97"), .8f);
			graph.drawString(gl, txt, (int) (cx - radius * loc.x - (text_scale * b.width / 2)), (int) (cy - radius * loc.y - (text_scale * b.height / 2)), objectTextColor, 1.0f, text_scale);

			// Draw connection info
			String connection = "";
			if (connections.size() > 0) {
				connection = connections.get(0);
				if (connections.size() > 1) {
					for (int j = 1; j < connections.size(); j++) {
						connection = connection + " / " + connections.get(j);
					}
				}
			}
			text_scale = TEXT_SMALL;
			b = (Rectangle2D.Double) graph.textRenderer.getBounds(connection);
			graph.drawString(gl, connection, (int) (cx - 0.5 * radius * loc.x - (text_scale * b.width / 2)), (int) (cy - 0.5 * radius * loc.y - (text_scale * b.height / 2)), predicateTextColor, 1.0f, text_scale);

			if (mode == GL2.GL_SELECT) {
				gl.glPopName();
			}
		}

		// Draw current object at the center
		text_scale = TEXT_LARGE;
		String text = rdfObject.getName();
		Rectangle2D.Double bounds = (Rectangle2D.Double) graph.textRenderer.getBounds(text);
		drawRect(gl, (int) (cx - (text_scale * bounds.width / 2)) - inset_x, (int) (cy - (text_scale * bounds.height / 2)) - inset_y, text_scale * bounds.width + 2 * inset_x, text_scale * bounds.height + 2 * inset_y, Color.decode("#FFFFFF"), 1f);
		graph.drawString(gl, text, (int) (cx - (text_scale * bounds.width / 2)), (int) (cy - (text_scale * bounds.height / 2)), objectTextColor, 1.0f, text_scale);

		// Draw details if available
		HashMap<String, String> details = rdfObject.getProperties();
		if (details.size() > 0) {
			Iterator<String> it = details.keySet().iterator();
			int ypos = 10;
			text_scale = TEXT_SMALL;
			while (it.hasNext()) {
				String key = it.next();
				String value = details.get(key);
				graph.drawString(gl, key, 10, ypos, objectTextColor, 1.0f, text_scale);
				graph.drawString(gl, value, 120, ypos, objectTextColor, 1.0f, text_scale);
				ypos += 15;
			}
		} else {
			text_scale = TEXT_SMALL;
			graph.drawString(gl, "No details available", 10, 10, objectTextColor, 1.0f, text_scale);
		}
	}

	public void paintRDFOBjectDeep(GL gl1, int mode, RDFObject rdfObject, float scale, int level, double cx, double cy, String excludeName) {
		GL2 gl = gl1.getGL2();
		float text_scale = TEXT_LARGE;
		float radius = graph.radius;
		int inset_x = 25;
		int inset_y = 10;
		int next_level = level + 1;

		if (rdfObject == null) {
			String text = "no object loaded";
			Rectangle2D.Double bounds = (Rectangle2D.Double) graph.textRenderer.getBounds(text);
			graph.drawString(gl, text, (int) (cx - (text_scale * bounds.width / 2)), (int) (cy - (text_scale * bounds.height / 2)), objectTextColor, 1.0f, text_scale);
			return;
		}

		if (mode == GL2.GL_SELECT) {
			gl.glLoadName(rdfObject.getId());
		}

		if (level == 0) {
			excludeName = rdfObject.getUrl();
		}

		int child_count = rdfObject.getObjects().size() + rdfObject.getSubjects().size();
		rt = new PointRotationPanel(child_count);
		rt.setScale(1f, 1f);

		ArrayList<Point2D.Double> locations = rt.getPoints(.8 + level / 1.8f);
		int index = 0;

		// Draw object around it
		for (int i = 0; i < rdfObject.getObjects().size(); i++) {
			text_scale = TEXT_MEDIUM;
			RDFObject o = rdfObject.getObjects().get(i);
			ArrayList<String> connections = rdfObject.getConnectionsForObject(o);
			Color connColor = determineConnectorColor(connections);
			Point2D.Double loc = locations.get(index);

			// Draw connector
			gl.glBegin(GL.GL_LINES);
			gl.glColor4f(connColor.getRed()/255, connColor.getGreen()/255, connColor.getBlue()/255, 0f);
			gl.glVertex2d(cx, cy);
			gl.glColor4f(connColor.getRed()/255, connColor.getGreen()/255, connColor.getBlue()/255, 1f);
			gl.glVertex2d(cx - scale * radius * loc.x, cy - scale * radius * loc.y);
			gl.glEnd();

			index++;

			if (mode == GL2.GL_SELECT) {
				gl.glPushName(o.getId());
			}

			String txt = o.getName();
			Rectangle2D.Double b = (Rectangle2D.Double) graph.textRenderer.getBounds(txt);
			drawRect(gl, (int) (cx - scale * radius * loc.x - (scale * text_scale * b.width / 2) - scale * inset_x), (int) (cy - scale * radius * loc.y - (scale * text_scale * b.height / 2) - scale * inset_y), scale * (text_scale * b.width + 2 * inset_x), scale * (text_scale * b.height + 2 * inset_y), determineObjectColor(o), 1f);
			if (level <= 2) {
				text_scale *= scale;
				graph.drawString(gl, txt, (int) (cx - scale * radius * loc.x - (text_scale * b.width / 2)), (int) (cy - scale * radius * loc.y - (text_scale * b.height / 2)), objectTextColor, 1.0f, text_scale);
			}
			// Draw connection info
			String connection = "";
			if (connections.size() > 0) {
				connection = connections.get(0);
				if (connections.size() > 1) {
					for (int j = 1; j < connections.size(); j++) {
						connection = connection + " / " + connections.get(j);
					}
				}
			}
			if (level == 0) {
				text_scale = TEXT_SMALL;
				b = (Rectangle2D.Double) graph.textRenderer.getBounds(connection);
				graph.drawString(gl, connection, (int) (cx - 0.5 * radius * loc.x - (text_scale * b.width / 2)), (int) (cy - 0.5 * radius * loc.y - (text_scale * b.height / 2)), predicateTextColor, 1.0f, text_scale);
			}
			if (mode == GL2.GL_SELECT) {
				gl.glPopName();
			}

			int grand_children = o.getObjects().size() + o.getSubjects().size();
			if (level < max_depth && !o.getUrl().equals(excludeName) && grand_children > 0) {
				paintRDFOBjectDeep(gl, mode, o, .6f * scale, next_level, cx - scale * radius * loc.x, cy - scale * radius * loc.y, excludeName);
			}
		}

		// Draw subjects around it
		for (int i = 0; i < rdfObject.getSubjects().size(); i++) {
			text_scale = TEXT_MEDIUM;
			RDFObject o = rdfObject.getSubjects().get(i);
			ArrayList<String> connections = rdfObject.getConnectionsForObject(o);
			Color connColor = determineConnectorColor(connections);
			Point2D.Double loc = locations.get(index);

			// Draw connector
			gl.glBegin(GL.GL_LINES);
			gl.glColor4f(connColor.getRed()/255, connColor.getGreen()/255, connColor.getBlue()/255, 0f);
			gl.glVertex2d(cx, cy);
			gl.glColor4f(connColor.getRed()/255, connColor.getGreen()/255, connColor.getBlue()/255, 1f);
			gl.glVertex2d(cx - scale * radius * loc.x, cy - scale * radius * loc.y);
			gl.glEnd();

			index++;

			if (mode == GL2.GL_SELECT) {
				gl.glPushName(o.getId());
			}

			String txt = o.getName();
			Rectangle2D.Double b = (Rectangle2D.Double) graph.textRenderer.getBounds(txt);
			drawRect(gl, (int) (cx - scale * radius * loc.x - (scale * text_scale * b.width / 2) - scale * inset_x), (int) (cy - scale * radius * loc.y - (scale * text_scale * b.height / 2) - scale * inset_y), scale * (text_scale * b.width + 2 * inset_x), scale * (text_scale * b.height + 2 * inset_y), determineObjectColor(o), 1f);
			//			
			if (level <= 2) {
				text_scale *= scale;
				graph.drawString(gl, txt, (int) (cx - scale * radius * loc.x - (text_scale * b.width / 2)), (int) (cy - scale * radius * loc.y - (text_scale * b.height / 2)), objectTextColor, 1.0f, text_scale);
			}

			// Draw connection info
			String connection = "";
			if (connections.size() > 0) {
				connection = connections.get(0);
				if (connections.size() > 1) {
					for (int j = 1; j < connections.size(); j++) {
						connection = connection + " / " + connections.get(j);
					}
				}
			}
			
			
			if (level == 0) {
				text_scale = TEXT_SMALL;
				b = (Rectangle2D.Double) graph.textRenderer.getBounds(connection);
//				float angle = -45f + i * (360f/locations.size());
//				gl.glTranslated((cx - 0.5 * radius * loc.x), (cy - 0.5 * radius * loc.y), 0);
//				gl.glRotatef(angle, 0f, 0f, 1f);
				graph.drawString(gl, connection, (int) (cx - 0.5 * radius * loc.x - (text_scale * b.width / 2)), (int) (cy - 0.5 * radius * loc.y - (text_scale * b.height / 2)), predicateTextColor, 1.0f, text_scale);
//				graph.drawString(gl, connection, (int) -(text_scale * b.width / 2), (int) -(text_scale * b.height / 2), Color.red, 1.0f, text_scale);				
//				gl.glRotatef(-angle, 0f, 0f, 1f);
//				gl.glTranslated(-(cx - 0.5 * radius * loc.x), -(cy - 0.5 * radius * loc.y), 0);
			}
			if (mode == GL2.GL_SELECT) {
				gl.glPopName();
			}
			int grand_children = o.getObjects().size() + o.getSubjects().size();
			if (level < max_depth && !o.getUrl().equals(excludeName) && grand_children > 0) {
				paintRDFOBjectDeep(gl, mode, o, .6f * scale, next_level, cx - scale * radius * loc.x, cy - scale * radius * loc.y, excludeName);
			}
		}

		// Draw current object at the center
		if (level == 0) {
			text_scale = TEXT_LARGE;
			String text = rdfObject.getName();
			Rectangle2D.Double bounds = (Rectangle2D.Double) graph.textRenderer.getBounds(text);
			drawRect(gl, (int) (scale * (cx - (text_scale * bounds.width / 2)) - inset_x), (int) (scale * (cy - (text_scale * bounds.height / 2)) - inset_y), scale * (text_scale * bounds.width + 2 * inset_x), scale * (text_scale * bounds.height + 2 * inset_y), determineObjectColor(rdfObject), 1f);
			graph.drawString(gl, text, (int) (scale * (cx - (text_scale * bounds.width / 2))), (int) (scale * (cy - (text_scale * bounds.height / 2))), objectTextColor, 1.0f, text_scale);
		}

		// Draw details if available, details will be put in a tipi table
		// HashMap<String, String> details = rdfObject.getProperties();
		// if (details.size() > 0 && level == 0) {
		// Iterator<String> it = details.keySet().iterator();
		// int ypos = 10 - graph.panPoint.y;
		// text_scale = TEXT_SMALL;
		// while (it.hasNext()) {
		// String key = it.next();
		// String value = details.get(key);
		// graph.drawString(gl, key, 10 - graph.panPoint.x, ypos, Color.black,
		// 1.0f, text_scale);
		// graph.drawString(gl, value, 120- graph.panPoint.x, ypos, Color.black,
		// 1.0f, text_scale);
		// ypos += 15;
		// }
		// } else if (level == 0) {
		// text_scale = TEXT_SMALL;
		// graph.drawString(gl, "No details available", 10, 10, Color.black,
		// 1.0f, text_scale);
		// }
	}

	private Color determineObjectColor(RDFObject o) {
		Color objColor = Color.white;

		String type = o.getType();
		if ("tipi".equals(type)) {
			objColor = Color.decode("#ff9000");
		}
		if ("navajo".equals(type)) {
			objColor = Color.decode("#e20090");
		}
		if ("service".equals(type)) {
			objColor = Color.decode("#2e9dd7");
		}
		if ("dbo".equals(type)) {
			objColor = Color.decode("#95d72e");
		}
		if ("base".equals(type)) {
			objColor = Color.decode("#95d72e");
		}
		return objColor;
	}
	
	private Color determineConnectorColor(ArrayList<String> connections){
		Color connColor = Color.white;
		
		int r =0;
		int g =0;
		int b = 0;
		for(int i=0;i<connections.size();i++){
			String conn = connections.get(i);
			if("callsService".equals(conn)){
				r = 255;
			}
			if("listensToService".equals(conn)){
				g = 255;
			}
			if("usesDbObject".equals(conn)){
				g = 255;
			}
			if("injectsService".equals(conn)){
				b = 255;
			}
			if("includesService".equals(conn)){
				r = 255;
				g = 255;
				b = 255;
			}
		}
		
		connColor = new Color(r, g, b);
		return connColor;
	}


	private void drawRect(GL gl1, int x, int y, double width, double height, Color c, float alpha) {
		GL2 gl = gl1.getGL2();
		gl.glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, alpha);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex2d(x, y);
		gl.glVertex2d(x + width, y);
		gl.glColor4f(1f, 1f, 1f, 1f);
		gl.glVertex2d(x + width, y + height);
		gl.glVertex2d(x, y + height);
		gl.glEnd();

		gl.glColor4f(0f, 0f, 0f, 1f);
		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glVertex2d(x, y);
		gl.glVertex2d(x + width, y);
		gl.glVertex2d(x + width, y + height);
		gl.glVertex2d(x, y + height);
		gl.glEnd();

	}

}
