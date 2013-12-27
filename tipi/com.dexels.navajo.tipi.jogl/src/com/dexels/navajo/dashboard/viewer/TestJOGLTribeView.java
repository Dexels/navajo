package com.dexels.navajo.dashboard.viewer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.IntBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;

//import com.sun.media.rtsp.StatusMessage;
//import com.sun.opengl.util.BufferUtil;
//import com.sun.opengl.util.j2d.TextRenderer;
//import com.sun.opengl.util.j2d.TextureRenderer;
//import com.sun.opengl.util.texture.Texture;
//import com.sun.opengl.util.texture.TextureCoords;
//import com.sun.opengl.util.texture.TextureIO;

public class TestJOGLTribeView extends TipiDataComponentImpl implements GLEventListener {

	private static final long serialVersionUID = -7457560373249638162L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TestJOGLTribeView.class);
	private GLCanvas canvas;
	private Texture fireIcon;
	private Texture tribeIcon;
	private Point pickPoint, panPoint = new Point(0, 0);
	private float width, height, zoom = 0f;
	private float maxTribeRadius = 500f;
	private float tribeRadius = 0;
	private float roomRadius = 90f;
	// private float occupantSize = 15f;
	private Message rooms;
	private Message users;
	private int mouseOverTribe = -1;
	private int selectedTribe = -1;
//	private int selectedUser = -1;
	private PointRotationPanel rt;
	private float theta = 0f;
	private float pause_theta = 0f;
	private float aspx = 1f, aspy = .8f;
	int tribeModFactor = 100;
	Animator radiusAnim;
	Animator orbiter;

	protected String fpsText;
	protected int fpsWidth;
	protected long startTime;
	protected int frameCount;
	protected DecimalFormat format = new DecimalFormat("####.00");

	public static final int BUFSIZE = 512;
	public GLU glu = new GLU();
	public TextRenderer textRenderer;
	private ArrayList<Texture> tribeIcons = new ArrayList<Texture>();
	private HashMap<String, Message> exceptions = new HashMap<String, Message>();
	private HashMap<String, Message> navajoStatus = new HashMap<String, Message>();
	private ArrayList<Point2D.Float> stars;
	
	private float[] lightAmbient = {0.5f, 0.5f, 0.5f, 1.0f};
    private float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
    private float[] lightPosition = {0.0f, 0.0f, 2.0f, 1.0f};
    
//    private float z = -500f;  // Camera height

	@Override
	public Object createContainer() {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {

				canvas = new GLCanvas() {
					private static final long serialVersionUID = -6597323676724928801L;

					@Override
					public Dimension getMaximumSize() {
						return new Dimension(0, 0);
					}

					@Override
					public Dimension getMinimumSize() {
						return new Dimension(0, 0);
					}

					@Override
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
			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				logger.info("Clicked: " + mouseOverTribe);

				int tribe_id = (mouseOverTribe - mouseOverTribe % tribeModFactor) / 100;
//				int user_id = mouseOverTribe % tribeModFactor;

				if(mouseOverTribe == 99){
					if(orbiter.isRunning()){
						orbiter.stop();
						pause_theta = theta;
					}else{
						orbiter.start();
					}
					return;
				}
				
//				if (user_id > 0) {
//					selectedUser = user_id;
//				} else {
//					selectedUser = -1;
//				}
				if (tribe_id > -1 && selectedTribe != tribe_id) {
					focusOnTribe(tribe_id);
				} else if (tribe_id > -1 && selectedTribe == -1) {
					applyTribeFocus(tribe_id);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});

		canvas.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if (mouseOverTribe > -1) {
					canvas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					canvas.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
				pickPoint = e.getPoint();
				repaintCanvas();
			}
		});

		canvas.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {				
				int amt = e.getWheelRotation();
				if((maxTribeRadius - 10*amt) > 0){
					maxTribeRadius -= 10*amt;
				}
				if(e.isAltDown()){	
					int target = (int)(10*(aspy+(amt*.1f)));
					if(target > 0 && target < 10){
						aspy += amt*.1f;
					}
				}				
			}
		});

		return canvas;
	}

	public void setTheta(float theta) {
		this.theta = pause_theta + theta;
		int tribecount = rooms.getArraySize();
		float per_tribe = (float) ((Math.PI * 2f) / tribecount);
		int calculatedTribe = (int)(theta/ per_tribe);
		boolean log_on = true;
		
		logger.info("Selected: " + selectedTribe + ", calc: "+ calculatedTribe);
		if(selectedTribe != calculatedTribe && log_on){
			focusOnTribe(calculatedTribe);
		}
		repaintCanvas();
	}

	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		super.loadData(n, method);
		if ("RoomList".equals(method)) {
			rooms = n.getMessage("Room");
			if (orbiter != null) {
				orbiter.stop();
			}			
			PropertySetter orbitSetter = new PropertySetter(this, "theta", 0f, (float) (Math.PI * 2f));
			orbiter = new Animator(150000, Animator.INFINITE, Animator.RepeatBehavior.LOOP, orbitSetter);			
			orbiter.setAcceleration(0f);
			orbiter.setDeceleration(0f);
			orbiter.start();

		}
		if ("UserList".equals(method)) {
			clearExceptions();
			users = n.getMessage("User");
			ArrayList<Message> remove = new ArrayList<Message>();
			for (int i = 0; i < users.getArraySize(); i++) {
				if (users.getMessage(i) != null && users.getMessage(i).getProperty("Name") != null) {
					String label = users.getMessage(i).getProperty("Name").getValue();
					if (label.startsWith("~")) {
						remove.add(users.getMessage(i));
					}
				}
			}
			for (int i = 0; i < remove.size(); i++) {
				users.removeMessage(remove.get(i));
			}
			
			repaintCanvas();
		}
		if ("JabberException".equals(method)) {
			String user = n.getMessage("Jabber").getProperty("Occupant").getValue();
			addException(user, n.getMessage("__access__"));
		}
		if ("JabberService".equals(method)) {
			String user = n.getMessage("Jabber").getProperty("Occupant").getValue();
			if(n.getMessage("NavajoStatus") != null){
				Message status =  n.getMessage("NavajoStatus");
				navajoStatus.put(user, status);
			}
			repaintCanvas();
		}
	}

	private final void clearExceptions() {
		exceptions.clear();
	}

	private final void addException(String user, Message m) {
		exceptions.put(user, m);
	}

	public boolean hasExceptions(String user) {
		Iterator<String> it = exceptions.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (key.indexOf(user) > 0) {
				return true;
			}
		}
		return false;
	}

	private final void focusOnTribe(final int tribe_id) {
		try {
			if (radiusAnim == null || !radiusAnim.isRunning()) {
				users = null;
				radiusAnim = PropertySetter.createAnimator(300, this, "tribeRadius", tribeRadius, maxTribeRadius / 1.5f);
				radiusAnim.setAcceleration(.8f);
				radiusAnim.setDeceleration(.1f);
				radiusAnim.addTarget(new TimingTargetAdapter() {
					@Override
					public void end() {
						applyTribeFocus(tribe_id);
					}
				});
				radiusAnim.start();
			}
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}

	private final void applyTribeFocus(final int tribe_id) {
		selectedTribe = tribe_id;
		Animator radiusAnim = PropertySetter.createAnimator(1000, this, "tribeRadius", tribeRadius, maxTribeRadius);
		radiusAnim.setAcceleration(.1f);
		radiusAnim.setDeceleration(.8f);
		radiusAnim.start();
		try {
			Message tribe = rooms.getMessage(tribe_id);
			HashMap<String, Object> parms = new HashMap<String, Object>();
			parms.put("tribe", tribe.getProperty("Name").getValue());
			performTipiEvent("onSelectionChanged", parms, false);
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}
	
	private String getSelectedTribeId(){
		if(selectedTribe > -1){
			Message tribe = rooms.getMessage(selectedTribe);
			if(tribe != null){
				return tribe.getProperty("Id").getValue();
			}
		}
		return null;
	}

	/*
	 * Debug function for outputting the current Frames Per Second
	 */
//	private void displayFPSText(GLAutoDrawable drawable) {
//		if (++frameCount == 10) {
//			long endTime = System.currentTimeMillis();
//			float fps = 10.0f / (float) (endTime - startTime) * 1000;
//			frameCount = 0;
//			startTime = System.currentTimeMillis();
//			fpsText = format.format(fps);
//			int x = drawable.getWidth() - fpsWidth - 5;
//			int y = drawable.getHeight() - 30;
//			logger.info("FPS: " + fpsText);
//		}
//	}

	@Override
	protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, final TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);

	}

	@Override
	protected void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
//		gl.glMatrixMode(gl.GL_PROJECTION);
//		gl.glLoadIdentity();
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();  
        

		// Zooming
		float aspect_ratio = width / height;
		float zoom_height = zoom;
		zoom_height = zoom / aspect_ratio;

		gl.glOrtho(zoom, width - zoom, zoom_height, height - zoom_height, -10f, 1f);
		// Panning
		gl.glTranslatef(panPoint.x, panPoint.y, 0);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);

		if (pickPoint != null) {
			pickRect(gl);
		}
		
		drawStars(gl);
		
		TextureCoords tc = fireIcon.getImageTexCoords();
		float tx1 = tc.left();
		float ty1 = tc.top();
		float tx2 = tc.right();
		float ty2 = tc.bottom();

		fireIcon.enable();
		fireIcon.bind();

//		gl.glPushMatrix();
//		gl.glTranslatef(0,0,.01f);
		gl.glBegin(GL2.GL_QUADS);
		
		gl.glColor4f(1f, 1f, 1f, .8f);
		gl.glTexCoord2f(tx1, ty2);
		gl.glVertex3d(0, 0, 0f);
		gl.glTexCoord2f(tx1, ty1);
		gl.glVertex3d(0, 128, 0f);
		gl.glTexCoord2f(tx2, ty1);
		gl.glVertex3d(128, 128, 0f);
		gl.glTexCoord2f(tx2, ty2);
		gl.glVertex3d(128, 0, 0f);
		gl.glEnd();
		fireIcon.disable();
//		gl.glPopMatrix();
		
//		drawRooms(gl, gl.GL_RENDER);
//		 displayFPSText(drawable);
		gl.glFlush();
	}

	public void repaintCanvas() {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				canvas.repaint();
			}
		});
	}

	public void setTribeRadius(float radius) {
		this.tribeRadius = radius;
		repaintCanvas();
	}
	
	private final void drawStars(GL gl1){
		GL2 gl = gl1.getGL2();
		if(stars != null){
			gl.glBegin(GL.GL_POINTS);
			Random r = new Random(System.currentTimeMillis());
			for(int i=0;i<stars.size();i++){
				Point2D.Float star = stars.get(i);
				if(i%50 == 0){
					gl.glColor4f(1f, .6f, .6f, r.nextFloat());
				}else{
					gl.glColor4f(1f, 1f, 1f, .6f);
				}
				gl.glVertex3d(star.x, star.y, 0f);
				gl.glColor4f(1f, 1f, 1f, .3f);
				gl.glVertex3d(star.y, star.x, 0f);
			}
			
			gl.glEnd();
		}
	}
	
	/**
	 * @param gl  
	 */
	private final void compileStars(GL gl){
		try{
			int size = 2000;
			stars = new ArrayList<Point2D.Float>();
			Random r = new Random(System.currentTimeMillis());
			for(int i=0;i<2000;i++){
				stars.add(new Point2D.Float(size * r.nextFloat(), size*r.nextFloat()));
			}
		}catch (Exception e) {
			logger.error("Error: ",e);
		}
	}

	private final void drawRooms(GL gl1, int mode) {
		GL2 gl = gl1.getGL2();
		try {		
			if (rooms != null) {
				logger.info("aap");
				rt.setPointCount(rooms.getArraySize());
				rt.setScale(aspx, aspy);
				ArrayList<Point2D.Double> locations = rt.getPoints(theta);
				gl.glPushMatrix();
				gl.glTranslatef(width / 2, height / 2, 0f);

				for (int i = 0; i < rooms.getArraySize(); i++) {
					logger.info("Noot: " + i);
					if (mode == GL2.GL_SELECT) {
						gl.glLoadName(tribeModFactor * i);
					}

					Point2D.Double loc = locations.get(i);
					float radius = (i == selectedTribe ? tribeRadius : maxTribeRadius / 1.5f);

					gl.glBegin(GL.GL_LINES);
					gl.glColor4f(1f, 1f, 1f, 0f);
					gl.glVertex3d(0, 0, 0f);					
					gl.glColor4f(1f, 1f, 1f, 1f);
					if(i == selectedTribe){
						gl.glColor4f(0f, 1f, 0f, 1f);
					}
					gl.glVertex3d(radius * loc.x, radius * loc.y, 0f);
					gl.glEnd();
					gl.glColor4f(1f, 0f, 0f, .8f);

					TextureCoords tc = tribeIcon.getImageTexCoords();
					float tx1 = tc.left();
					float ty1 = tc.top();
					float tx2 = tc.right();
					float ty2 = tc.bottom();

					tribeIcon.enable();
					tribeIcon.bind();

					gl.glTranslatef((float) (radius * loc.x), (float) (radius * loc.y), 0f);
					gl.glBegin(GL2.GL_QUADS);
					gl.glColor4f(1f, 1f, 1f, 1f);
					gl.glTexCoord2f(tx1, ty2);
					gl.glVertex3d(-24, -24, 0f);
					gl.glTexCoord2f(tx1, ty1);
					gl.glVertex3d(-24, 24, 0f);
					gl.glTexCoord2f(tx2, ty1);
					gl.glVertex3d(24, 24, 0f);
					gl.glTexCoord2f(tx2, ty2);
					gl.glVertex3d(24, -24, 0f);
					gl.glEnd();
					tribeIcon.disable();
					gl.glTranslatef((float) (-radius * loc.x), (float) (-radius * loc.y), 0f);

					float text_scale = .8f;
					String label = "";
					Rectangle2D textBounds = textRenderer.getBounds(label);

					int occupants = (Integer) rooms.getMessage(i).getProperty("Occupants").getTypedValue();
					if (users != null && i == selectedTribe) {
						occupants = users.getArraySize();
					}

					gl.glPushMatrix();
					gl.glTranslatef((float) (radius * loc.x), (float) (radius * loc.y), 0f);
					PointRotationPanel prt = new PointRotationPanel(occupants);
					ArrayList<Point2D.Double> occLoc = prt.getPoints(Math.PI / 4f);
					for (int j = 0; j < occupants; j++) {					
						logger.info("miets: " + j);
						
						if (mode == GL2.GL_SELECT) {
							gl.glLoadName(tribeModFactor * i + j + 1);
						}
						Point2D.Double occLocation = occLoc.get(j);
						gl.glBegin(GL.GL_LINES);
						gl.glColor4f(1f, 1f, 1f, 0f);
						gl.glVertex3d(0, 0, 0);
						gl.glColor4f(1f, 1f, 1f, 1f);
						if(i == selectedTribe){
							gl.glColor4f(0f, 1f, 0f, 1f);
						}
						gl.glVertex3d(roomRadius * occLocation.x, roomRadius * occLocation.y, 0f);
						gl.glEnd();
						gl.glColor4f(0f, 1f, 0f, .8f);

						int index = i + j + 4;
						Texture tribeIcon = tribeIcons.get(index % 28);
						tc = tribeIcon.getImageTexCoords();
						tx1 = tc.left();
						ty1 = tc.top();
						tx2 = tc.right();
						ty2 = tc.bottom();

						tribeIcon.enable();
						tribeIcon.bind();
						gl.glTranslatef((float) (roomRadius * occLocation.x), (float) (roomRadius * occLocation.y), 0f);
						gl.glBegin(GL2.GL_QUADS);
						gl.glColor4f(1f, 1f, 1f, 1f);
						gl.glTexCoord2f(tx1, ty2);
						gl.glVertex3d(-24, -24, 0f);
						gl.glTexCoord2f(tx1, ty1);
						gl.glVertex3d(-24, 24, 0f);
						gl.glTexCoord2f(tx2, ty1);
						gl.glVertex3d(24, 24, 0f);
						gl.glTexCoord2f(tx2, ty2);
						gl.glVertex3d(24, -24, 0f);
						gl.glEnd();
						tribeIcon.disable();
						gl.glTranslatef((float) (-roomRadius * occLocation.x), (float) (-roomRadius * occLocation.y), 0f);

						if (users != null && i == selectedTribe) {
							text_scale = .6f;							
							if(users.getMessage(j) != null && users.getMessage(j).getProperty("Name") != null){
								label = users.getMessage(j).getProperty("Name").getValue();					
								textBounds = textRenderer.getBounds(label);
								drawString(gl, label, (int) (roomRadius * occLocation.x - (text_scale * textBounds.getWidth() / 2f)), (int) (roomRadius * occLocation.y - (text_scale * textBounds.getHeight() / .4f)), Color.white, text_scale);
								if(hasExceptions(label)){
									drawMessageBalloon(gl, "Fail!", (float)(roomRadius * occLocation.x), (float)(roomRadius * occLocation.y));
								}
								String tribeId = getSelectedTribeId();
								Message status = navajoStatus.get(tribeId + "/" + label);
								if(status !=null){
									drawStatus(gl, roomRadius * occLocation.x, roomRadius * occLocation.y, status);
								}
							}
						}
					}
					gl.glTranslatef((float) (-radius * loc.x), (float) (-radius * loc.y), 0f);
					gl.glPopMatrix();
					if (users != null && i == selectedTribe) {
						text_scale = .6f;
					}
					label = rooms.getMessage(i).getProperty("Name").getValue();
					textBounds = textRenderer.getBounds(label);
					drawString(gl, label, (int) (radius * loc.x - (text_scale * textBounds.getWidth() / 2f)), (int) (radius * loc.y - (text_scale * textBounds.getHeight() / .5f)), Color.white, text_scale);
				}
				
				if (mode == GL2.GL_SELECT) {
					gl.glLoadName(99);
				}

				TextureCoords tc = fireIcon.getImageTexCoords();
				float tx1 = tc.left();
				float ty1 = tc.top();
				float tx2 = tc.right();
				float ty2 = tc.bottom();

				fireIcon.enable();
				fireIcon.bind();

				gl.glBegin(GL2.GL_QUADS);
				gl.glColor4f(1f, 1f, 1f, .8f);
				gl.glTexCoord2f(tx1, ty2);
				gl.glVertex3d(-64, -64, 0f);
				gl.glTexCoord2f(tx1, ty1);
				gl.glVertex3d(-64, 64, 0f);
				gl.glTexCoord2f(tx2, ty1);
				gl.glVertex3d(64, 64, 0f);
				gl.glTexCoord2f(tx2, ty2);
				gl.glVertex3d(64, -64, 0f);
				gl.glEnd();
				fireIcon.disable();

				gl.glTranslatef(-width / 2, -height / 2, 0f);
				gl.glPopMatrix();
			}
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}
	
	private void drawStatus(GL gl, double xpos, double ypos, Message statusMsg) {
		Message kernel = statusMsg.getMessage("Kernel");
		String startTime = kernel.getProperty("Now").getValue();
		startTime = "Req/s at: " + startTime.substring(startTime.lastIndexOf(" ") + 1);
		String requestRate = kernel.getProperty("RequestRate").getValue().substring(0,5);
		Rectangle2D textBounds = textRenderer.getBounds(startTime);
		float text_scale = .6f;
		double y_offset = (text_scale * textBounds.getHeight() / .25f);
		int y_pos = (int) (ypos - y_offset);
		drawString(gl, startTime, (int) (xpos - (text_scale * textBounds.getWidth() / 2f)), y_pos, Color.gray, text_scale);
		textBounds = textRenderer.getBounds(requestRate);
		y_pos -= textBounds.getHeight();
		drawString(gl, requestRate, (int) (xpos - (text_scale * textBounds.getWidth() / 2f)), y_pos, Color.gray, text_scale);
	}

	private final void drawMessageBalloon(GL gl1, String message, float xpos, float ypos){
		GL2 gl = gl1.getGL2();
		gl.glBegin(GL.GL_LINES);
		gl.glColor4f(1f, 1f, 1f, 0f);
		gl.glVertex3d(xpos+20, ypos-5, 0f);
		gl.glColor4f(1f, 1f, 1f, 1f);
		gl.glVertex3d(xpos+40, ypos-10, 0f);
		gl.glEnd();
		drawString(gl, message, (int)(xpos+45), (int)(ypos-15), Color.red, .8f);
	}

	/*
	 * pickRect() sets up selection mode, name stack, and projection matrix for
	 * picking. Then the objects are drawn.
	 */

	private void pickRect(GL gl1) {
		GL2 gl = gl1.getGL2();

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
		glu.gluPickMatrix(pickPoint.x, viewport[3] - pickPoint.y,// 
				15.0, 15.0, viewport, 0);

		// Zooming
		gl.glOrtho(zoom, width - zoom, zoom_height, height - zoom_height, 0f, 1f);

		// Panning
		gl.glTranslatef(panPoint.x, panPoint.y, 0f);

		// Hier stadion tekenen Mode gl.GL_SELECT ----
		drawRooms(gl, GL2.GL_SELECT);

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glFlush();

		hits = gl.glRenderMode(GL2.GL_RENDER);
		selectBuffer.get(selectBuf);
		processHits(hits, selectBuf);
	}

	/*
	 * processHits prints out the contents of the selection array. And sets the
	 * selected seats un der the mouse location
	 */
	public void processHits(int hits, int buffer[]) {
		int i, j;
		int ii = -1,  names, ptr = 0;
		if (hits > 0) {
			for (i = 0; i < hits; i++) { /* for each hit */
				names = buffer[ptr];
				ptr++;
				ptr++;
				ptr++;
				for (j = 0; j < names; j++) { /* for each name */
					if (j == 0) /* set row and column */
						ii = buffer[ptr];
					else if (j == 1) {
//						jj = buffer[ptr];
					}
					ptr++;
				}
			}
		}
		mouseOverTribe = ii;
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL2.GL_TEXTURE_2D);
		
		loadTribeIcons(29);
		fireIcon = loadTexture("earth.png");
		tribeIcon = loadTexture("fire_small.png");
		float values[] = new float[2];
		gl.glGetFloatv(GL2.GL_LINE_WIDTH_GRANULARITY, values, 0);
		logger.info("GL2.GL_LINE_WIDTH_GRANULARITY value is " + values[0]);
		gl.glGetFloatv(GL2.GL_LINE_WIDTH_RANGE, values, 0);
		logger.info("GL.GL_LINE_WIDTH_RANGE values are " + values[0] + ", " + values[1]);
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_DONT_CARE);
		gl.glLineWidth(1.5f);
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepth(1.0);                                  //Enables Clearing Of The Depth Buffer
        gl.glEnable(GL.GL_DEPTH_TEST);                            //Enables Depth Testing
        gl.glDepthFunc(GL.GL_LEQUAL);                             //The Type Of Depth Test To Do
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  // Really Nice Perspective Calculations
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightAmbient, 0);		// Setup The Ambient Light
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, lightDiffuse, 0);		// Setup The Diffuse Light
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPosition, 0);	// Position The Light
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_LIGHTING);	
        gl.glShadeModel(GL2.GL_SMOOTH); 
		
		
		Font font = new Font("SansSerif", Font.PLAIN, 18);
		textRenderer = new TextRenderer(font, true, false);
		rt = new PointRotationPanel(10);
		
		compileStars(gl);
	}

	private final void loadTribeIcons(int count) {
		for (int i = 1; i < count + 1; i++) {
			tribeIcons.add(loadTexture("c" + i + ".png"));
		}
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int i, int x, int width, int height) {
		this.width = width;
		this.height = height;
		GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);
//		gl.glMatrixMode(GL.GL_PROJECTION);
//		gl.glLoadIdentity();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();  
        
		gl.glOrtho(0f, width, 0f, height, 0f, 1f);
	}

	/*
	 * Draw a string in OpenGL
	 */
	/**
	 * @param gl  
	 */
	private void drawString(GL gl, String text, int xpos, int ypos, Color color, float scale) {
		float r = color.getRed() / 255f;
		float g = color.getGreen() / 255f;
		float b = color.getBlue() / 255f;
		textRenderer.begin3DRendering();
		textRenderer.setColor(r, g, b, 1f);
		textRenderer.draw3D(text, xpos, ypos, -1f, scale);
		textRenderer.end3DRendering();
	}



	/*
	 * Utility function for loading the Texture for the rotate modifier
	 */
	private Texture loadTexture(String filename) {
        Texture t = null;
        try {
             t = TextureIO.newTexture(getClass().getResource(filename), false, ".png");
             t.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
             t.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        } catch (IOException e) {
            logger.info("Error loading " + filename);
        }
        return t;
    }
	
	public static void main(String[] args){
		try{
						
		}catch(Exception e){
			
		}
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		
	}

}
