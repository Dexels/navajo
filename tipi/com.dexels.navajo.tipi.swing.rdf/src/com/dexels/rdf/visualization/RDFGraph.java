package com.dexels.rdf.visualization;

import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.jogamp.opengl.util.awt.TextRenderer;

// http://www.navajo.nl/navajo/service/club_ProcessQueryClub 

public class RDFGraph extends TipiDataComponentImpl implements GLEventListener {
	private static final long serialVersionUID = -3851994275022643825L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(RDFGraph.class);
	
	public static HashMap<String, String> namespaces = new HashMap<String, String>();
	private HashMap<String, RDFObject> rdfObjects = new HashMap<String, RDFObject>();

	GLCanvas canvas;
	RDFGraphPainter painter = new RDFGraphPainter(this);
	TextRenderer textRenderer;
	public float width, height, zoom = .1f;

	// Panning
	public Point previousPanPoint = new Point(0, 0);
	public Point panStartPoint = new Point(0, 0);
	public Point panPoint = new Point(0, 0);
	public Point mouseLocation;

	// Zooming
	public GLU glu = new GLU();
	private float zoomIncrement;
//	private float previousZoom;
//	private Point zoomPoint = new Point(0, 0);

	// Drawing
	private Point pickPoint = new Point(0, 0);
	private int selectedRDFObject = -1;
	private RDFObject currentRDFObject = null;
	public float radius = 400f;
	public float max_radius = 400f;
	public Animator anim;

	public Object createContainer() {
		runSyncInEventThread(new Runnable() {
			public void run() {

				canvas = new GLCanvas() {
					private static final long serialVersionUID = 1L;

					public Dimension getMaximumSize() {
						return new Dimension(0, 0);
					}

					public Dimension getMinimumSize() {
						return new Dimension(0, 0);
					}

					public Dimension getPreferredSize() {
						return new Dimension(0, 0);
					}
				};
			}
		});

		/*
		 * Setup event listeners.
		 * 
		 * GLEventListener for processing OpenGL drawing events
		 * MouseEventListeners for interaction
		 */
		canvas.addGLEventListener(this);

		canvas.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				mouseLocation = e.getPoint();
				pickPoint = e.getPoint();
				if (e.isAltDown() && e.getButton() != MouseEvent.BUTTON3) {
					panStartPoint = e.getPoint();
					return;
				}
				if (selectedRDFObject > -1) {
					focusOnRDFObject(selectedRDFObject);
				}

			}

			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isAltDown()) {
					previousPanPoint = new Point(panPoint.x, panPoint.y);
				}
			}
		});

		canvas.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				mouseLocation = e.getPoint();
				pickPoint = e.getPoint();
				if (e.isAltDown()) {
					panTo(e.getPoint());
				}
			}

			public void mouseMoved(MouseEvent e) {
				pickPoint = e.getPoint();
				repaintCanvas();
			}
		});

		canvas.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.isControlDown()) {
					int amt = e.getWheelRotation();
					if ((radius - 20 * amt) > 0) {
						radius -= 20 * amt;
						max_radius = radius;
					}
				}else{
//					zoomPoint = e.getPoint();
					zoom(-e.getWheelRotation());
				}
				repaintCanvas();
			}
		});

		return canvas;
	}

	public void panTo(Point p) {

		// Get the diff between start of drag and the current mousePos.
		int dx = p.x - panStartPoint.x;
		int dy = panStartPoint.y - p.y;

		// Calculate scaling factor based on the zoomlevel and dimensions of the
		// view
		float the_magic_number = 2 - (width / zoom);
		float scale = zoom * (the_magic_number / width);

		// Adjust and store the actual amount to pan.
		panPoint.x = previousPanPoint.x - (int) (scale * dx);
		panPoint.y = previousPanPoint.y - (int) (scale * dy);

		repaintCanvas();
	}
	
	public void zoom(int factor) {
//		previousZoom = zoom;
		float previousIncrement = zoomIncrement;
		float max_depth = 100f;
		float min_increment = 1f;
		float maxZoomAbs = canvas.getWidth() / 2f;

		zoomIncrement = (-max_depth / maxZoomAbs) * Math.abs(zoom) + max_depth;

		if (zoomIncrement < min_increment) {
			zoomIncrement = previousIncrement;
		}

		if (!((zoom + factor * zoomIncrement) < -maxZoomAbs) && !((zoom + factor * zoomIncrement) > maxZoomAbs)) {
			zoom += factor * zoomIncrement;
		}

		repaintCanvas();
	}

	private void focusOnRDFObject(int id) {
		try {
			panPoint = new Point(0,0);
			previousPanPoint = new Point(0,0);
			zoom(0);
			Iterator<String> it = rdfObjects.keySet().iterator();
			while (it.hasNext()) {
				String url = it.next();
				RDFObject obj = rdfObjects.get(url);
				if (obj.getId() == id) {
					HashMap<String, Object> parms = new HashMap<String, Object>();
					parms.put("url", url);
					performTipiEvent("onObjectChanged", parms, false);
					return;
				}
			}
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}

	public void repaintCanvas() {
		runSyncInEventThread(new Runnable() {
			public void run() {
				canvas.repaint();
			}
		});
	}

	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		// Zooming
		float aspect_ratio = width / height;
		float zoom_height = zoom;
		zoom_height = zoom / aspect_ratio;

		gl.glOrtho(zoom, width - zoom, zoom_height, height - zoom_height, 0f, 1f);

		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		// Panning
		gl.glTranslatef(panPoint.x, panPoint.y, 0f);

		if (pickPoint != null) {
			pickRect(gl);
		}

		painter.paintRDFOBjectDeep(gl, GL2.GL_RENDER, currentRDFObject, 1f, 0, width / 2, height / 2, null);

	}

	/*
	 * pickRect() sets up selection mode, name stack, and projection matrix for
	 * picking. Then the objects are drawn.
	 */

	private void pickRect(GL2 gl) {
		int BUFSIZE = 512;
		int selectBuf[] = new int[BUFSIZE];
		IntBuffer selectBuffer = IntBuffer.allocate(BUFSIZE);
		int hits;
		int viewport[] = new int[4];
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
		gl.glSelectBuffer(BUFSIZE, selectBuffer);
		gl.glRenderMode(GL2.GL_SELECT);
		gl.glInitNames();
		gl.glPushName(0);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();

		// Zooming
		float aspect_ratio = width / height;
		float zoom_height = zoom;
		zoom_height = zoom / aspect_ratio;

		/* create 5x5 pixel picking region near cursor location */
		glu.gluPickMatrix(pickPoint.x, viewport[3] - pickPoint.y, 10.0, 10.0, viewport, 0);

		// Zooming
		gl.glOrtho(zoom, width - zoom, zoom_height, height - zoom_height, 0f, 1f);

		// Panning
		gl.glTranslatef(panPoint.x, panPoint.y, 0f);

		// Drawing in SELECTION MODE
		if (rdfObjects.size() > 0) {
			painter.paintRDFOBjectDeep(gl, GL2.GL_SELECT, currentRDFObject, 1f, 0, width / 2, height / 2, null);
		}

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glFlush();

		hits = gl.glRenderMode(GL2.GL_RENDER);
		selectBuffer.get(selectBuf);
		processHits(hits, selectBuf);
	}

	/*
	 * processHits prints out the contents of the selection array. And sets the
	 * selected seats under the mouse location
	 */
	public void processHits(int hits, int buffer[]) {
		int i, j;
		int ii = -1, jj = -1, names, ptr = 0;
		if (hits > 0) {
			for (i = 0; i < hits; i++) { /* for each hit */
				names = buffer[ptr];
				ptr++;
				ptr++;
				ptr++;
				for (j = 0; j < names; j++) { /* for each name */
					if (j == 0) /* set row and column */
						ii = buffer[ptr];
					else if (j == 1)
						jj = buffer[ptr];
					ptr++;
				}
			}
		}
		// logger.info("ii: " + ii + ", jj " + jj);
		if (ii > -1) {
			selectedRDFObject = jj;
		} else {
			selectedRDFObject = -1;
		}
	}

	/**
	 * 
	 * @param drawable
	 * @param modeChanged
	 * @param deviceChanged
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
	}

	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		float values[] = new float[2];
		gl.glGetFloatv(GL2.GL_LINE_WIDTH_GRANULARITY, values, 0);
		logger.info("GL.GL_LINE_WIDTH_GRANULARITY value is " + values[0]);
		gl.glGetFloatv(GL2.GL_LINE_WIDTH_RANGE, values, 0);
		logger.info("GL.GL_LINE_WIDTH_RANGE values are " + values[0] + ", " + values[1]);
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_DONT_CARE);
		gl.glLineWidth(1.5f);
		
//		gl.glClearColor(.15f, .2f, .4f, 1f);
		gl.glClearColor(1f, 1f, 1f, 1f);

		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
		textRenderer = new TextRenderer(font, true, false);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int i, int x, int width, int height) {
		this.width = width;
		this.height = height;
		GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0f, width, 0f, height, 0f, 1f);
		repaintCanvas();
	}

	@Override
	protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, final TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
		if ("setNamespace".equals(name)) {
			String url = (String) compMeth.getEvaluatedParameterValue("url", event);
			String type = (String) compMeth.getEvaluatedParameterValue("ns", event);
			namespaces.put(type, url);
			logger.info("Namespace added: " + type + ": " + url);
		}
		if("setViewDepth".equals(name)){
			int depth = (Integer) compMeth.getEvaluatedParameterValue("depth", event);
			painter.setMaxDepth(depth);
		}
		if("clear".equals(name)){
			clear();
		}
	}
	
	private void clear(){
		logger.info("Clearing..");
		rdfObjects.clear();
		currentRDFObject = null;
		previousPanPoint = new Point(0,0);
		panPoint = new Point(0,0);
		zoom(0);		
		repaintCanvas();
	}

	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		super.loadData(n, method);
		if ("rdf/InitGetConnections".equals(method)) {
			parseRDFData(n);
			animateRadius();
		}
		if ("rdf/ProcessGetObjectDetails".equals(method) && n.getMessage("ScriptDetails") != null) {					
			currentRDFObject.setProperties(n.getMessage("ScriptDetails"));
			repaintCanvas();
		}
	}

	private void animateRadius() {
		radius = 0;
		if (anim != null && anim.isRunning()) {
			return;
		} else {
			anim = PropertySetter.createAnimator(1000, this, "radius", radius, max_radius);
			anim.setAcceleration(.1f);
			anim.setDeceleration(.6f);
			anim.start();
		}
	}

	public void setRadius(float rad) {
		radius = rad;
		repaintCanvas();
	}

	private void parseRDFData(Navajo n) {
		 try{
		 n.write(System.err);
		 }catch(Exception e){}

		Message object = n.getMessage("Object");
		Message objects = n.getMessage("Objects");
		Message subjects = n.getMessage("Subjects");

		// Do we know what object we are talking about
		String url = object.getProperty("Object").getValue();
		if (url != null && !"".equals(url)) {
			RDFObject rdf = getRDFObject(url);
			if (rdf == null) {
				rdf = createRDFObject(url);
			}
			currentRDFObject = rdf;

			if (rdf != null) {
				if (!rdfObjects.containsValue(rdf)) {
					rdfObjects.put(url, rdf);
				}

				// Add objects
				for (int i = 0; i < objects.getArraySize(); i++) {
					Message mObj = objects.getMessage(i);
					String u = mObj.getProperty("Object").getValue();
					String connection = mObj.getProperty("Predicate").getValue();
					RDFObject rdfO = getRDFObject(u);
					if (rdfO == null) {
						rdfO = createRDFObject(u);
					}
					if (rdfO != null && !rdf.getObjects().contains(rdfO)) {
						rdf.addObject(rdfO);
					}
					if (rdfO != null && !rdfObjects.containsKey(u)) {
						rdfObjects.put(u, rdfO);
					}
					rdf.addConnection(rdfO, connection);
				}

				// Add subjects
				for (int i = 0; i < subjects.getArraySize(); i++) {
					Message mObj = subjects.getMessage(i);
					String u = mObj.getProperty("Subject").getValue();
					String connection = mObj.getProperty("Predicate").getValue();
					RDFObject rdfO = getRDFObject(u);
					if (rdfO == null) {
						rdfO = createRDFObject(u);
					}
					if (rdfO != null && !rdf.getSubjects().contains(rdfO)) {
						rdf.addSubject(rdfO);
					}
					if (rdfO != null && !rdfObjects.containsKey(u)) {
						rdfObjects.put(u, rdfO);
					}
					rdf.addConnection(rdfO, connection);
				}
			}
			repaintCanvas();
		}
	}

	private RDFObject createRDFObject(String url) {
		String[] nat = getNameAndType(url);
		RDFObject rdf = null;
		if (nat != null) {
			rdf = new RDFObject(url, nat[0], nat[1]);
			rdf.setId(rdfObjects.size());
		} else {
			logger.info("WARNING: Not created, no name/type determined for [" + url + "]");
		}
		return rdf;
	}

	private RDFObject getRDFObject(String url) {
		return rdfObjects.get(url);
	}

	private String[] getNameAndType(String objectURL) {
		String[] pair = new String[2];

		Iterator<String> it = namespaces.keySet().iterator();
		int max_length = 0;
		while (it.hasNext()) {
			String key = it.next();
			String url = namespaces.get(key);
			if (objectURL.startsWith(url) && url.length() > max_length) {
				max_length = url.length();
				pair[1] = key;
				pair[0] = objectURL.substring(max_length);
			}
		}
		if (pair[1] == null || pair[0] == null) {
			return null;
		}
		return pair;
	}

	/**
	 * 
	 * @param gl
	 * @param text
	 * @param xpos
	 * @param ypos
	 * @param color
	 * @param alpha
	 * @param scale
	 */
	public void drawString(GL gl, String text, int xpos, int ypos, Color color, float alpha, float scale) {
		float r = color.getRed() / 255f;
		float g = color.getGreen() / 255f;
		float b = color.getBlue() / 255f;
		textRenderer.begin3DRendering();

		textRenderer.setColor(r, g, b, alpha);
		textRenderer.draw3D(text, xpos, ypos, -1f, scale);
		textRenderer.end3DRendering();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
	}

}
