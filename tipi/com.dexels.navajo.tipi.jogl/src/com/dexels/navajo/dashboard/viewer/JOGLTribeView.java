package com.dexels.navajo.dashboard.viewer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.IntBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import com.dexels.navajo.dashboard.viewer.images.ResourceLoader;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.awt.TextureRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;



public class JOGLTribeView extends TipiDataComponentImpl implements GLEventListener {
	private static final long serialVersionUID = -8665532903536132430L;
	private final String tribeIdentifier = "navajotribe";
//	private final static int CONFERENCE_ONTWIKKEL = 0;
	private final static int CONFERENCE_TEST = 1;
	private final static int CONFERENCE_ACCEPTATIE = 2;
	private final static int CONFERENCE_PRODUCTIE = 3;
	private final JOGLServerStatus serverStatus = new JOGLServerStatus();
	private int selectedConference = -1;
	private boolean selectConference = true;
	private float conferenceAlpha = 1f;

	private GLCanvas canvas;
	private Texture earth;
	private Texture moon;
	private Texture mars;
	private Texture sun;
	private Texture conferenceIcon;
	private Texture tribeIcon;
	private Point pickPoint, panPoint = new Point(0, 0);
	private float width, height, zoom = 0f;
	private float maxTribeRadius = 350f;
	private float tribeRadius = 0;
	private float roomRadius = 90f;
	private Message tribeRooms;
	private Message serverRooms;
	private Message users;
	private int mouseOverTribe = -1;
	private int mouseOverChild = -1;
	private int selectedTribe = -1;
	private int selectedSubTribe = -1;
	private int selectedUser = -1;
	private PointRotationPanel rt;
	private float theta = 0f;
	private float pause_theta = 0f;
	private float aspx = 1f, aspy = .8f;
	int tribeModFactor = 1000;
	int subTribeModFactor = 100;
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
	private ArrayList<String> tribeMessages = new ArrayList<String>();
	private String conferenceName = "";

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
				System.err.println("Clicked tribe: " + mouseOverTribe + ", child: " + mouseOverChild);

				if (selectConference) {
					selectedConference = mouseOverTribe;
					switch (selectedConference) {
					case CONFERENCE_ACCEPTATIE:
						conferenceIcon = earth;
						conferenceName = "acceptatie";
						break;
					case CONFERENCE_TEST:
						conferenceIcon = moon;
						conferenceName = "test";
						break;
					case CONFERENCE_PRODUCTIE:
						conferenceIcon = sun;
						conferenceName = "productie";
						break;
					default:
						conferenceIcon = mars;
						conferenceName = "ontwikkel";
					}
					HashMap<String, Object> parms = new HashMap<String, Object>();
					parms.put("conference", conferenceName);
					try {
						tribeMessages.clear();
						performTipiEvent("onConferenceSelected", parms, false);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					animateToConference();
					return;
				}
				int tribe_id = (mouseOverTribe - mouseOverTribe % tribeModFactor) / tribeModFactor;
				int user_id = mouseOverChild % tribeModFactor;

				if (mouseOverTribe == 9999) {
					try {
						HashMap<String, Object> parms = new HashMap<String, Object>();
						performTipiEvent("onDisconnect", parms, false);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					animateFromConference();
					return;
				}

				
				if (user_id > 0 && user_id < subTribeModFactor) {
					selectedUser = user_id;
					try {
						
						int index = user_id - 2;
						
						Message user = null;
						if(index >= 0){
							user = users.getMessage(index);
						}						
						
						if (user != null) {
							Message tribe = tribeRooms.getMessage(tribe_id);
							if (selectedSubTribe > 0) {
								ArrayList<Message> sub = getServerRoomsForTribe(tribe.getProperty("Name").getValue());

								if (sub.size() > (selectedSubTribe - 1)) {
									tribe = sub.get(selectedSubTribe - 1);
								}
							}
							serverStatus.setStatusNavajo(null);
							String name = user.getProperty("Jid").getValue();
							serverStatus.setServerName(user.getProperty("Name").getValue());
							HashMap<String, Object> parms = new HashMap<String, Object>();
							parms.put("text", "postman");
							parms.put("recipient", name);
							performTipiEvent("onTalk", parms, false);
							System.err.println("Just talked POSTMAN to recipient: " + name + ", this is user " + selectedUser);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					selectedUser = -1;
				}
				if (tribe_id > -1 && (selectedTribe != tribe_id || user_id > subTribeModFactor - 1)) {
					users = null;
					focusOnTribe(tribe_id, user_id < subTribeModFactor ? -1 : user_id);
				} else if (tribe_id > -1 && selectedTribe == -1) {
					users = null;
					applyTribeFocus(tribe_id, user_id < subTribeModFactor ? -1 : user_id);
				} else if (tribe_id > -1 && user_id == -1) {
					users = null;
					focusOnTribe(tribe_id);
				}
			}

			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		});

		canvas.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {

			}

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
			public void mouseWheelMoved(MouseWheelEvent e) {
				int amt = e.getWheelRotation();
				if ((maxTribeRadius - 10 * amt) > 0) {
					maxTribeRadius -= 10 * amt;
				}
				if (e.isAltDown()) {
					int target = (int) (10 * (aspy + (amt * .1f)));
					if (target > 0 && target < 10) {
						aspy += amt * .1f;
					}
				}
			}
		});

		return canvas;
	}

	public void setTheta(float theta) {
		this.theta = pause_theta + theta;
		int tribecount = tribeRooms.getArraySize();
		float per_tribe = (float) ((Math.PI * 2f) / tribecount);
		int calculatedTribe = (int) (theta / per_tribe);
		boolean log_on = false;

		if (selectedTribe != calculatedTribe && log_on) {
			focusOnTribe(calculatedTribe);
		}
		repaintCanvas();
	}

	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		super.loadData(n, method);
		if ("RoomList".equals(method)) {

			Message rooms = n.getMessage("Room");
			splitRoomsData(rooms);
			startOrbiter();
		}
		if ("UserList".equals(method)) {

//			int current = 0;
//			if (users != null) {
//				current = users.getArraySize();
//			}

			clearExceptions();

			Message newUsers = n.getMessage("User");
			ArrayList<Message> remove = new ArrayList<Message>();
			for (int i = 0; i < newUsers.getArraySize(); i++) {
				if (newUsers.getMessage(i) != null && newUsers.getMessage(i).getProperty("Name") != null) {
					String label = newUsers.getMessage(i).getProperty("Name").getValue();
					if (!label.startsWith("server") && !label.startsWith("#")) {
						remove.add(newUsers.getMessage(i));
					}
				}
			}
			for (int i = 0; i < remove.size(); i++) {
				newUsers.removeMessage(remove.get(i));
			}

			if (users != null) {
				for (int i = 0; i < newUsers.getArraySize(); i++) {
					Message nu = newUsers.getMessage(i);
					String user = nu.getProperty("Name").getValue();
					if (!containsUser(users, user)) {
						tribeMessages.add("Server " + user + " has joined the tribe");
					}
				}

				for (int i = 0; i < users.getArraySize(); i++) {
					Message nu = users.getMessage(i);
					String user = nu.getProperty("Name").getValue();
					if (!containsUser(newUsers, user)) {
						tribeMessages.add("Server " + user + " has left the tribe");
					}
				}
			}

			users = newUsers;
			// System.err.println("Userlist: current number of users: " +
			// current + "new size: " + users.getArraySize());
			repaintCanvas();
		}
		if ("JabberException".equals(method)) {
			String user = n.getMessage("Jabber").getProperty("Occupant").getValue();
			addException(user, n.getMessage("__event__"));
			HashMap<String, Object> parms = new HashMap<String, Object>();
			parms.put("user", user);
			performTipiEvent("onException", parms, false);
		}
		if ("InitJabber".equals(method)) {
			String user = n.getMessage("Jabber").getProperty("Occupant").getValue();
			if (n.getMessage("NavajoStatus") != null) {
				Message status = n.getMessage("NavajoStatus");
				navajoStatus.put(user, status);
				if (selectedUser > -1 && user.endsWith(serverStatus.getServerName())) {
					serverStatus.setStatusNavajo(n);
					HashMap<String, Object> parms = new HashMap<String, Object>();
					performTipiEvent("onStatus", parms, false);
				}
			}

			repaintCanvas();
		}
		if ("navajo/ProcessGetServerLoad".equals(method)) {
			serverStatus.setServerLoad(n);
		}

	}

	private boolean containsUser(Message usersArray, String user) {
		for (int i = 0; i < usersArray.getArraySize(); i++) {
			Message u = usersArray.getMessage(i);
			String name = u.getProperty("Name").getValue();
			if (name.equals(user)) {
				return true;
			}
		}
		return false;
	}

	private void animateToConference() {
		tribeRooms = null;
		serverRooms = null;
		PropertySetter conferenceSetter = new PropertySetter(this, "conferenceFocus", 0f, 1f);
		Animator conferenceAnimator = new Animator(1000, conferenceSetter);
		conferenceAnimator.setAcceleration(.4f);
		conferenceAnimator.setDeceleration(.2f);
		conferenceAnimator.addTarget(new TimingTargetAdapter() {
			public void end() {
				// startOrbiter();
				selectConference = false;
			}
		});

		conferenceAnimator.start();
	}

	private void animateFromConference() {
		tribeRooms = null;
		serverRooms = null;
		users = null;
		selectedTribe = -1;
		selectedSubTribe = -1;
		selectConference = true;
		if (orbiter != null) {
			orbiter.stop();
		}
		orbiter = null;
		PropertySetter conferenceSetter = new PropertySetter(this, "conferenceFocus", 1f, 0f);
		Animator conferenceAnimator = new Animator(1000, conferenceSetter);
		conferenceAnimator.setAcceleration(.4f);
		conferenceAnimator.setDeceleration(.2f);
		conferenceAnimator.addTarget(new TimingTargetAdapter() {
			public void end() {
				selectedConference = -1;
			}
		});

		conferenceAnimator.start();
	}

	public void setConferenceFocus(float value) {
		conferenceAlpha = 1f - value;
		repaintCanvas();
	}

	private final void startOrbiter() {
		if (orbiter == null) {
			PropertySetter orbitSetter = new PropertySetter(this, "theta", 0f, (float) (Math.PI * 2f));
			orbiter = new Animator(150000, Animator.INFINITE, Animator.RepeatBehavior.LOOP, orbitSetter);
			orbiter.setAcceleration(0f);
			orbiter.setDeceleration(0f);
		}
		if (!orbiter.isRunning()) {
			orbiter.start();
		}
	}

	private void splitRoomsData(Message rooms) {
		Navajo tn = NavajoFactory.getInstance().createNavajo();
		tribeRooms = NavajoFactory.getInstance().createMessage(tn, "Room", Message.MSG_TYPE_ARRAY);
		Navajo sn = NavajoFactory.getInstance().createNavajo();
		serverRooms = NavajoFactory.getInstance().createMessage(sn, "Room", Message.MSG_TYPE_ARRAY);

		for (int i = 0; i < rooms.getArraySize(); i++) {
			if (isTribeRoom(rooms.getMessage(i))) {
				tribeRooms.addMessage(rooms.getMessage(i).copy(tn));
			} else {
				serverRooms.addMessage(rooms.getMessage(i).copy(sn));
			}
		}
		try {
			tn.addMessage(tribeRooms);
			sn.addMessage(serverRooms);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getRoomName(Message room) {
		String result = "";
		try {
			result = room.getProperty("Name").getValue();
			if (result.indexOf("-") > -1) {
				result = result.substring(result.indexOf("-") + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private ArrayList<Message> getServerRoomsForTribe(String tribeRoomName) {
		ArrayList<Message> result = new ArrayList<Message>();

		String tribeName = tribeRoomName.substring(tribeRoomName.indexOf("-") + 1);

		for (int i = 0; i < serverRooms.getArraySize(); i++) {
			Message current = serverRooms.getMessage(i);
			String currentName = current.getProperty("Name").getValue();
			currentName = currentName.substring(0, currentName.indexOf("-"));
			if (tribeName.equals(currentName)) {
				result.add(current);
			}
		}
		return result;

	}

	private boolean isTribeRoom(Message room) {
		if (room != null && room.getProperty("Name") != null) {
			return room.getProperty("Name").getValue().startsWith(tribeIdentifier);
		}
		return false;
	}

	private final void clearExceptions() {
		exceptions.clear();
	}

	private final void addException(String user, Message m) {
		System.err.println(">>>>>>>>>>>>>>>>> Added exception for user " + user);
		try {
			m.write(System.err);
		} catch (Exception e) {
			e.printStackTrace();
		}
		exceptions.put(user, m);

		tribeMessages.add(user + ", exception in service " + m.getProperty("Webservice").getValue());
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
		focusOnTribe(tribe_id, -1);
	}

	private final void focusOnTribe(final int tribe_id, final int sub_tribe) {
		try {
			if (radiusAnim == null || !radiusAnim.isRunning()) {
				users = null;
				radiusAnim = PropertySetter.createAnimator(300, this, "tribeRadius", tribeRadius, maxTribeRadius / 1.5f);
				radiusAnim.setAcceleration(.8f);
				radiusAnim.setDeceleration(.1f);
				radiusAnim.addTarget(new TimingTargetAdapter() {
					public void end() {
						applyTribeFocus(tribe_id, sub_tribe);
					}
				});
				radiusAnim.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final void applyTribeFocus(final int tribe_id, int sub_tribe) {
		selectedTribe = tribe_id;
		if (sub_tribe > 0) {
			selectedSubTribe = sub_tribe / subTribeModFactor;
		} else {
			selectedSubTribe = -1;
		}
		Animator radiusAnim = PropertySetter.createAnimator(1000, this, "tribeRadius", tribeRadius, maxTribeRadius);
		radiusAnim.setAcceleration(.1f);
		radiusAnim.setDeceleration(.8f);
		radiusAnim.start();
		try {

			Message tribe = tribeRooms.getMessage(tribe_id);
			if (selectedSubTribe > 0) {
				ArrayList<Message> sub = getServerRoomsForTribe(tribe.getProperty("Name").getValue());

				if (sub.size() > (selectedSubTribe - 1)) {
					tribe = sub.get(selectedSubTribe - 1);
				}
			}

			HashMap<String, Object> parms = new HashMap<String, Object>();
			parms.put("tribe", tribe.getProperty("Name").getValue());
			performTipiEvent("onSelectionChanged", parms, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getSelectedTribeId() {
		if (selectedTribe > -1) {
			Message tribe = tribeRooms.getMessage(selectedTribe);
			if (tribe != null) {
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
//			// System.err.println("FPS: " + fpsText);
//		}
//	}

	private final void processCommand(String command) {
		try {
			URL url = new URL(command);
			serverStatus.setPostman(url);
			if (command.indexOf("http://") > -1) {
				command = command.substring(7);
			}
			HashMap<String, Object> parms = new HashMap<String, Object>();
			parms.put("serverName", serverStatus.getServerName());
			parms.put("serverURL", command);
			performTipiEvent("onPostman", parms, false);
		} catch (Exception e) {
			System.err.println("Command is not an URL");
		}
	}

	@Override
	protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, final TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
		if ("commandReceived".equals(name)) {
			String command = (String) compMeth.getEvaluatedParameterValue("command", event);
			processCommand(command);
		}
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
	}

	@Override
	protected Object getComponentValue(String name) {
		if ("selectedServer".equals(name)) {
			return serverStatus.getServerName();
		}

		return super.getComponentValue(name);
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
		// Panning
		gl.glTranslatef(panPoint.x, panPoint.y, 0f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);

		if (pickPoint != null) {
			pickRect(gl);
		}

		drawStars(gl);
		if (selectConference) {
			drawConferenceSelect(gl, GL2.GL_RENDER);
		} else {
			gl.glColor4f(1f, 0f, 0f, .5f);
			drawRooms(gl, GL2.GL_RENDER);
			drawTribeMessages(gl);
		}

		// Draw selected user
		if (selectedUser > -1) {
			serverStatus.draw(gl, width, height, GL2.GL_RENDER);
		}
		// displayFPSText(drawable);
		gl.glFlush();
	}

	public void repaintCanvas() {
		runSyncInEventThread(new Runnable() {
			public void run() {
				canvas.repaint();
			}
		});
	}

	public void setTribeRadius(float radius) {
		this.tribeRadius = radius;
		repaintCanvas();
	}

	private final void drawTribeMessages(GL gl) {
		int max_index = tribeMessages.size();
		if (tribeMessages.size() > 5) {
			max_index = 5;
		}

		int count = 0;
		for (int i = tribeMessages.size() - max_index; i < tribeMessages.size(); i++) {			
			float scale = .8f;
			String message = tribeMessages.get(i);
			Rectangle2D textBounds = textRenderer.getBounds(message);
			drawString(gl, message, (int) ((width / 2) - ((scale * textBounds.getWidth()) / 2)), (int) (20 + (count * textBounds.getHeight() * scale)), Color.white, .8f, scale);
			count++;
		}
	}

	private final void drawStars(GL gl1) {
		GL2 gl = gl1.getGL2();
		if (stars != null) {
			gl.glBegin(GL.GL_POINTS);
			Random r = new Random(System.currentTimeMillis());
			for (int i = 0; i < stars.size(); i++) {
				Point2D.Float star = stars.get(i);
				if (i % 50 == 0) {
					gl.glColor4f(1f, .6f, .6f, r.nextFloat());
				} else {
					gl.glColor4f(1f, 1f, 1f, .6f);
				}
				gl.glVertex2d(star.x, star.y);
				gl.glColor4f(1f, 1f, 1f, .3f);
				gl.glVertex2d(star.y, star.x);
			}
			gl.glEnd();
		}
	}

	private final void compileStars(GL gl) {
		try {
			int size = 2000;
			stars = new ArrayList<Point2D.Float>();
			Random r = new Random(System.currentTimeMillis());
			for (int i = 0; i < 2000; i++) {
				stars.add(new Point2D.Float(size * r.nextFloat(), size * r.nextFloat()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final void drawRooms(GL gl1, int mode) {
		GL2 gl = gl1.getGL2();
		try {
			gl.glPushMatrix();
			gl.glTranslatef(width / 2, height / 2, 0f);
			// If there are tribes in this conference
			if (tribeRooms != null) {
				rt.setPointCount(tribeRooms.getArraySize());
				rt.setScale(aspx, aspy);
				ArrayList<Point2D.Double> locations = rt.getPoints(theta);

				// The outer loop draws the tribe fire icons
				for (int i = 0; i < tribeRooms.getArraySize(); i++) {
					// Load the name for the tribe
					if (mode == GL2.GL_SELECT) {
						gl.glLoadName(tribeModFactor * i);
					}
					// Get the locations on the unity circle for the tribes in
					// this conference
					// So they are equally spaced around the center
					Point2D.Double loc = locations.get(i);
					float radius = (i == selectedTribe ? tribeRadius : maxTribeRadius / 1.5f);

					// Draw a line from the center to the current tribe
					gl.glBegin(GL.GL_LINES);
					gl.glColor4f(1f, 1f, 1f, 0f);
					gl.glVertex2d(0, 0);
					gl.glColor4f(1f, 1f, 1f, 1f);
					if (i == selectedTribe) {
						gl.glColor4f(0f, 1f, 0f, 1f);
					}
					gl.glVertex2d(radius * loc.x, radius * loc.y);
					gl.glEnd();
					gl.glColor4f(1f, 0f, 0f, .8f);

					// Move to tribe location and draw the icon
					gl.glTranslatef((float) (radius * loc.x), (float) (radius * loc.y), 0f);
					drawIcon(gl, tribeIcon);
					gl.glTranslatef((float) (-radius * loc.x), (float) (-radius * loc.y), 0f);

					float text_scale = 1f;
					String label = "";
					Rectangle2D textBounds = textRenderer.getBounds(label);

					// Determine how many occupants/servers there are in the
					// current tribe
					ArrayList<Message> servers = getServerRoomsForTribe(tribeRooms.getMessage(i).getProperty("Name").getValue());
					int occupants = servers.size();

					// Only count users when we are drawing a tribe. Users in a
					// subtribe are drawn inside the loop.
					if (users != null && i == selectedTribe && selectedSubTribe == -1) {
						occupants += users.getArraySize();
					}

					// Draw the occupants around this tribe, also equally spaced
					// using a rotationpanel
					gl.glPushMatrix();
					gl.glTranslatef((float) (radius * loc.x), (float) (radius * loc.y), 0f);
					PointRotationPanel prt = new PointRotationPanel(occupants);
					ArrayList<Point2D.Double> occLoc = prt.getPoints(Math.PI / 4f);
					for (int j = 0; j < occupants; j++) {

						// Push the name of the occupant, distinct between
						// server or occupant
						if (mode == GL2.GL_SELECT && j < servers.size() && servers.size() > 0) {
							gl.glPushName(tribeModFactor * i + subTribeModFactor * (j + 1));
						} else {
							gl.glPushName(tribeModFactor * i + j + 1);
						}

						// Draw a line from the center of the tribe to the
						// current server/occupant
						Point2D.Double occLocation = occLoc.get(j);
						gl.glBegin(GL.GL_LINES);
						gl.glColor4f(1f, 1f, 1f, 0f);
						gl.glVertex2d(0, 0);
						gl.glColor4f(1f, 1f, 1f, 1f);
						if (i == selectedTribe) {
							gl.glColor4f(0f, 1f, 0f, 1f);
						}
						gl.glVertex2d(roomRadius * occLocation.x, roomRadius * occLocation.y);
						gl.glEnd();
						gl.glColor4f(0f, 1f, 0f, .8f);

						// Get an icon, move to the occupant/server location and
						// draw the icon
						int index = i + j + 4;
						Texture tribeIcon = tribeIcons.get(index % 28);
						gl.glTranslatef((float) (roomRadius * occLocation.x), (float) (roomRadius * occLocation.y), 0f);
						drawIcon(gl, tribeIcon);
						gl.glTranslatef((float) (-roomRadius * occLocation.x), (float) (-roomRadius * occLocation.y), 0f);

						// Release name. (everything after this is not
						// selectable, with the mouse)
						if (mode == GL2.GL_SELECT) {
							gl.glPopName();
						}

						// If we have servers (= subTribes) and are drawing
						// them, draw the servername ( and occupants if
						// selected)
						if (j < servers.size() && servers.size() > 0) {
							if (i == selectedTribe) {
								text_scale = .6f;
							}
							// Draw the name
							if (servers.get(j) != null && servers.get(j).getProperty("Name") != null) {
								label = getRoomName(servers.get(j));
								textBounds = textRenderer.getBounds(label);
								drawString(gl, label, (int) (roomRadius * occLocation.x - (text_scale * textBounds.getWidth() / 2f)), (int) (roomRadius * occLocation.y - (text_scale * textBounds.getHeight() / .4f)), Color.white, 1f, text_scale);
							}

							// Draw users in this tribe.
							if (users != null && selectedSubTribe > -1) {

								// Draw all users in this subtribe, equally
								// spaced around the center
								PointRotationPanel prt_sub = new PointRotationPanel(users.getArraySize());
								ArrayList<Point2D.Double> occLoc_sub = prt_sub.getPoints(Math.PI / 2f);
								gl.glTranslatef((float) (roomRadius * occLocation.x), (float) (roomRadius * occLocation.y), 0f);
								for (int k = 0; k < users.getArraySize(); k++) {

									// Draw a line from the center of the
									// subtribe to this occupant
									Point2D.Double occLoc_subPnt = occLoc_sub.get(k);
									gl.glBegin(GL.GL_LINES);
									gl.glColor4f(1f, 1f, 1f, 0f);
									gl.glVertex2d(0, 0);
									gl.glColor4f(0f, 0f, 1f, 1f);
									gl.glVertex2d(roomRadius * occLoc_subPnt.x, roomRadius * occLoc_subPnt.y);
									gl.glEnd();

									// Move to occupant location and draw an
									// icon
									index = i + j + k;
									Texture subTribeIcon = tribeIcons.get(index % 28);
									gl.glTranslatef((float) (roomRadius * occLoc_subPnt.x), (float) (roomRadius * occLoc_subPnt.y), 0f);
									drawIcon(gl, subTribeIcon);
									gl.glTranslatef((float) (-roomRadius * occLoc_subPnt.x), (float) (-roomRadius * occLoc_subPnt.y), 0f);

									// draw occupant name
									label = users.getMessage(k).getProperty("Name").getValue();
									textBounds = textRenderer.getBounds(label);
									drawString(gl, label, (int) (roomRadius * occLoc_subPnt.x - (text_scale * textBounds.getWidth() / 2f)), (int) (roomRadius * occLoc_subPnt.y - (text_scale * textBounds.getHeight() / .4f)), Color.white, 1f, text_scale);
								}
								gl.glTranslatef((float) (-roomRadius * occLocation.x), (float) (-roomRadius * occLocation.y), 0f);
							}
						}

						// We are drawing tribe occupants
						if (j >= servers.size() && users != null && i == selectedTribe) {
							text_scale = .6f;

							// Draw name and status
							if (users.getMessage(j - servers.size()) != null && users.getMessage(j - servers.size()).getProperty("Name") != null) {
								label = users.getMessage(j - servers.size()).getProperty("Name").getValue();
								textBounds = textRenderer.getBounds(label);
								drawString(gl, label, (int) (roomRadius * occLocation.x - (text_scale * textBounds.getWidth() / 2f)), (int) (roomRadius * occLocation.y - (text_scale * textBounds.getHeight() / .4f)), Color.white, 1f, text_scale);
								// if (hasExceptions(label)) {
								// drawMessageBalloon(gl, "Fail!", (float)
								// (roomRadius * occLocation.x), (float)
								// (roomRadius * occLocation.y));
								// }
								String tribeId = getSelectedTribeId();
								Message status = navajoStatus.get(tribeId + "/" + label);
								if (status != null) {
									drawStatus(gl, roomRadius * occLocation.x, roomRadius * occLocation.y, status);
								}
							}
						}

					}
					gl.glTranslatef((float) (-radius * loc.x), (float) (-radius * loc.y), 0f);
					gl.glPopMatrix();

					// Draw the name of this tribe
					label = getRoomName(tribeRooms.getMessage(i));
					textBounds = textRenderer.getBounds(label);
					drawString(gl, label, (int) (radius * loc.x - (text_scale * textBounds.getWidth() / 2f)), (int) (radius * loc.y - (text_scale * textBounds.getHeight() / .5f)), Color.white, 1f, text_scale);
				}
			}

			// Set selection name for the conference (globe)
			if (mode == GL2.GL_SELECT) {
				gl.glLoadName(9999);
			}

			// Draw icon for the conference.
			drawIcon(gl, conferenceIcon);

			gl.glTranslatef(-width / 2, -height / 2, 0f);
			gl.glPopMatrix();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Utility function that draws an icon centered around the current GL
	 * location
	 */
	private final void drawIcon(GL gl1, Texture t) {
		GL2 gl = gl1.getGL2();
		TextureCoords tc = t.getImageTexCoords();
		float tx1 = tc.left();
		float ty1 = tc.top();
		float tx2 = tc.right();
		float ty2 = tc.bottom();

		double hw = t.getWidth() / 2;
		double hh = t.getHeight() / 2;
		t.enable();
		t.bind();

		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(1f, 1f, 1f, 1f);
		gl.glTexCoord2f(tx1, ty2);
		gl.glVertex2d(-hw, -hh);
		gl.glTexCoord2f(tx1, ty1);
		gl.glVertex2d(-hw, hh);
		gl.glTexCoord2f(tx2, ty1);
		gl.glVertex2d(hw, hh);
		gl.glTexCoord2f(tx2, ty2);
		gl.glVertex2d(hw, -hh);
		gl.glEnd();
		t.disable();
	}

	/*
	 * Draw status text for the current server, based on input from
	 * InitNavajoStatus Pushed over XMPP
	 */
	private void drawStatus(GL gl, double xpos, double ypos, Message statusMsg) {
		Message kernel = statusMsg.getMessage("Kernel");
		String startTime = kernel.getProperty("Now").getValue();
		startTime = "Req/s at: " + startTime.substring(startTime.lastIndexOf(" ") + 1);
		String requestRate = kernel.getProperty("RequestRate").getValue();
		if (kernel.getProperty("RequestRate").getValue().length() > 5) {
			requestRate = requestRate.substring(0, 5);
		}
		Rectangle2D textBounds = textRenderer.getBounds(startTime);
		float text_scale = .6f;
		double y_offset = (text_scale * textBounds.getHeight() / .25f);
		int y_pos = (int) (ypos - y_offset);
		drawString(gl, startTime, (int) (xpos - (text_scale * textBounds.getWidth() / 2f)), y_pos, Color.gray, 1f, text_scale);
		textBounds = textRenderer.getBounds(requestRate);
		y_pos -= textBounds.getHeight();
		drawString(gl, requestRate, (int) (xpos - (text_scale * textBounds.getWidth() / 2f)), y_pos, Color.gray, 1f, text_scale);
	}

//	private final void drawMessageBalloon(GL gl1, String message, float xpos, float ypos) {
//		GL2 gl = gl1.getGL2();
//		gl.glBegin(GL.GL_LINES);
//		gl.glColor4f(1f, 1f, 1f, 0f);
//		gl.glVertex2d(xpos + 20, ypos - 5);
//		gl.glColor4f(1f, 1f, 1f, 1f);
//		gl.glVertex2d(xpos + 40, ypos - 10);
//		gl.glEnd();
//		drawString(gl, message, (int) (xpos + 45), (int) (ypos - 15), Color.red, 1f, .8f);
//	}

	/*
	 * Draw the planet icons that select a conference.
	 */
	private final void drawConferenceSelect(GL gl1, int mode) {
		GL2 gl = gl1.getGL2();
		try {
			// Four textures
			float offset = width / 5f;

			// Mars , ontwikkel
			TextureCoords tc = mars.getImageTexCoords();
			float tx1 = tc.left();
			float ty1 = tc.top();
			float tx2 = tc.right();
			float ty2 = tc.bottom();

			mars.enable();
			mars.bind();
			if (mode == GL2.GL_SELECT) {
				gl.glLoadName(0);
			}

			float pos = offset;
			float alpha = conferenceAlpha;
			if (selectedConference == 0) {
				float center = width / 2;
				float perc = 1f - conferenceAlpha;
				float diff = center - pos;
				pos += perc * diff;
				alpha = 1f - perc * .2f;
			}

			gl.glPushMatrix();
			gl.glTranslatef(pos, height / 2, 0f);
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor4f(1f, 1f, 1f, alpha);
			gl.glTexCoord2f(tx1, ty2);
			gl.glVertex2d(-64, -64);
			gl.glTexCoord2f(tx1, ty1);
			gl.glVertex2d(-64, 64);
			gl.glTexCoord2f(tx2, ty1);
			gl.glVertex2d(64, 64);
			gl.glTexCoord2f(tx2, ty2);
			gl.glVertex2d(64, -64);
			gl.glEnd();
			mars.disable();
			String label = "Ontwikkel";
			Rectangle2D bounds = textRenderer.getBounds(label);
			drawString(gl, label, (int) (-bounds.getWidth() / 2f), -100, Color.white, conferenceAlpha, 1f);
			gl.glPopMatrix();

			// Moon, test
			tc = moon.getImageTexCoords();
			tx1 = tc.left();
			ty1 = tc.top();
			tx2 = tc.right();
			ty2 = tc.bottom();

			moon.enable();
			moon.bind();
			if (mode == GL2.GL_SELECT) {
				gl.glLoadName(1);
			}

			pos = 2 * offset;
			alpha = conferenceAlpha;
			if (selectedConference == 1) {
				float center = width / 2;
				float perc = 1f - conferenceAlpha;
				float diff = center - pos;
				pos += perc * diff;
				alpha = 1f - perc * .2f;
			}

			gl.glPushMatrix();
			gl.glTranslatef(pos, height / 2, 0f);
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor4f(1f, 1f, 1f, alpha);
			gl.glTexCoord2f(tx1, ty2);
			gl.glVertex2d(-64, -64);
			gl.glTexCoord2f(tx1, ty1);
			gl.glVertex2d(-64, 64);
			gl.glTexCoord2f(tx2, ty1);
			gl.glVertex2d(64, 64);
			gl.glTexCoord2f(tx2, ty2);
			gl.glVertex2d(64, -64);
			gl.glEnd();
			moon.disable();
			label = "Test";
			bounds = textRenderer.getBounds(label);
			drawString(gl, label, (int) (-bounds.getWidth() / 2f), -100, Color.white, conferenceAlpha, 1f);
			gl.glPopMatrix();

			// Earth, Acceptatie
			tc = earth.getImageTexCoords();
			tx1 = tc.left();
			ty1 = tc.top();
			tx2 = tc.right();
			ty2 = tc.bottom();

			earth.enable();
			earth.bind();
			if (mode == GL2.GL_SELECT) {
				gl.glLoadName(2);
			}
			pos = 3 * offset;
			alpha = conferenceAlpha;
			if (selectedConference == 2) {
				float center = width / 2;
				float perc = 1f - conferenceAlpha;
				float diff = center - pos;
				pos += perc * diff;
				alpha = 1f - perc * .2f;
			}

			gl.glPushMatrix();
			gl.glTranslatef(pos, height / 2, 0f);
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor4f(1f, 1f, 1f, alpha);
			gl.glTexCoord2f(tx1, ty2);
			gl.glVertex2d(-64, -64);
			gl.glTexCoord2f(tx1, ty1);
			gl.glVertex2d(-64, 64);
			gl.glTexCoord2f(tx2, ty1);
			gl.glVertex2d(64, 64);
			gl.glTexCoord2f(tx2, ty2);
			gl.glVertex2d(64, -64);
			gl.glEnd();
			earth.disable();
			label = "Acceptatie";
			bounds = textRenderer.getBounds(label);
			drawString(gl, label, (int) (-bounds.getWidth() / 2f), -100, Color.white, conferenceAlpha, 1f);
			gl.glPopMatrix();

			// Sun, Productie
			tc = sun.getImageTexCoords();
			tx1 = tc.left();
			ty1 = tc.top();
			tx2 = tc.right();
			ty2 = tc.bottom();

			sun.enable();
			sun.bind();
			if (mode == GL2.GL_SELECT) {
				gl.glLoadName(3);
			}

			pos = 4 * offset;
			alpha = conferenceAlpha;
			if (selectedConference == 3) {
				float center = width / 2;
				float perc = 1f - conferenceAlpha;
				float diff = center - pos;
				pos += perc * diff;
				alpha = 1f - perc * .2f;
			}

			gl.glPushMatrix();
			gl.glTranslatef(pos, height / 2, 0f);
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor4f(1f, 1f, 1f, alpha);
			gl.glTexCoord2f(tx1, ty2);
			gl.glVertex2d(-64, -64);
			gl.glTexCoord2f(tx1, ty1);
			gl.glVertex2d(-64, 64);
			gl.glTexCoord2f(tx2, ty1);
			gl.glVertex2d(64, 64);
			gl.glTexCoord2f(tx2, ty2);
			gl.glVertex2d(64, -64);
			gl.glEnd();
			sun.disable();
			label = "Productie";
			bounds = textRenderer.getBounds(label);
			drawString(gl, label, (int) (-bounds.getWidth() / 2f), -100, Color.white, conferenceAlpha, 1f);
			gl.glPopMatrix();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * pickRect() sets up selection mode, name stack, and projection matrix for
	 * picking. Then the objects are drawn.
	 */

	private void pickRect(GL gl1) {
		GL2 gl = gl1.getGL2();
		int selectBuf[] = new int[BUFSIZE];
		IntBuffer selectBuffer = IntBuffer.allocate(BUFSIZE); // BufferUtil.newIntBuffer(BUFSIZE);
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
		glu.gluPickMatrix((double) pickPoint.x, (double) (viewport[3] - pickPoint.y),// 
				15.0, 15.0, viewport, 0);

		// Zooming
		gl.glOrtho(zoom, width - zoom, zoom_height, height - zoom_height, 0f, 1f);

		// Panning
		gl.glTranslatef(panPoint.x, panPoint.y, 0f);

		if (selectConference) {
			drawConferenceSelect(gl, GL2.GL_SELECT);
		} else {
			drawRooms(gl, GL2.GL_SELECT);
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
	 * selected seats un der the mouse location
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
		mouseOverChild = jj;
		mouseOverTribe = ii;
		// System.err.println("Clicked tribe: " + mouseOverTribe + ", child: " +
		// mouseOverChild);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		loadTribeIcons(29);
		earth = loadTexture("earth.png");
		moon = loadTexture("moon.png");
		mars = loadTexture("mars.png");
		sun = loadTexture("sun.png");

		conferenceIcon = earth;

		tribeIcon = loadTexture("fire_small.png");
		float values[] = new float[2];
		gl.glGetFloatv(GL2.GL_LINE_WIDTH_GRANULARITY, values, 0);
		System.out.println("GL.GL_LINE_WIDTH_GRANULARITY value is " + values[0]);
		gl.glGetFloatv(GL2.GL_LINE_WIDTH_RANGE, values, 0);
		System.out.println("GL.GL_LINE_WIDTH_RANGE values are " + values[0] + ", " + values[1]);
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_DONT_CARE);
		gl.glLineWidth(1.5f);
		gl.glClearColor(0f, 0f, 0f, 0f);

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
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0f, width, 0f, height, 0f, 1f);
	}

	/*
	 * Draw a string in OpenGL
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


	/*
	 * Utility function for loading the Texture for the rotate modifier
	 */
	private final Texture loadTexture(String fileName) {
		try {
			BufferedImage icon = ImageIO.read(ResourceLoader.class.getResource(fileName));
			TextureRenderer c1tx = new TextureRenderer(icon.getWidth(), icon.getHeight(), true);
			Graphics2D c1Graphics = c1tx.createGraphics();
			c1Graphics.drawImage(icon, 0, 0, icon.getWidth(), icon.getHeight(), null);
			c1Graphics.dispose();
			return c1tx.getTexture();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		try {

		} catch (Exception e) {

		}
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		
	}

}
