package com.dexels.navajo.dashboard.viewer;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class JOGL3DTribeView extends TipiDataComponentImpl implements GLEventListener {

	private static final long serialVersionUID = -2008869951387194865L;
	private boolean lightingEnabled;				// Lighting ON/OFF
//    private boolean lightingChanged = false;		// Lighting changed
    private boolean blendingEnabled;				// Blending OFF/ON
//    private boolean blendingChanged = false;		// Blending changed
    
    protected String fpsText;
	protected int fpsWidth;
	protected long startTime;
	protected int frameCount;
	protected DecimalFormat format = new DecimalFormat("####.00");


    private int filter;				                // Which texture to use
    private int[] textures = new int[3];			// Storage For 3 Textures

    private float xrot = 20f;				// X Rotation
    private float yrot = 45f;				// Y Rotation

//    private float xspeed = 0.5f;				// X Rotation Speed
//    private boolean increaseX;
//    private boolean decreaseX;
//
//    private float yspeed = 0.3f;				// Y Rotation Speed
//    private boolean increaseY;
//    private boolean decreaseY;

    private float z = -500.0f;			// Depth Into The Screen
//    private boolean zoomIn;
//    private boolean zoomOut;

    private float[] lightAmbient = {0.5f, 0.5f, 0.5f, 1.0f};
    private float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
    private float[] lightPosition = {0.0f, 0.0f, 2.0f, 1.0f};
    
    Texture fireIcon;
    int fireTexture;

    private GLU glu = new GLU();
    GLCanvas canvas;
    
	public Object createContainer() {
		runSyncInEventThread(new Runnable() {
			public void run() {

				canvas = new GLCanvas() {
					private static final long serialVersionUID = -7213660598225377157L;

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
		
		canvas.addMouseMotionListener(new MouseMotionAdapter(){

			@Override
			public void mouseMoved(MouseEvent arg0) {
				repaintCanvas();
			}
			
		});

		/*
		 * Setup event listeners.
		 * 
		 * GLEventListener for processing OpenGL drawing events
		 * MouseEventListeners for interaction
		 */
		canvas.addGLEventListener(this);

		return canvas;
	}


    public void toggleBlending() {
        blendingEnabled = !blendingEnabled;
//        blendingChanged = true;
    }

    public void toggleLighting() {
        lightingEnabled = !lightingEnabled;
//        lightingChanged = true;
    }

    /**
	 * @param increase  
	 */
    public void increaseXspeed(boolean increase) {
//        increaseX = increase;
    }

    /**
	 * @param decrease  
	 */
    public void decreaseXspeed(boolean decrease) {
//        decreaseX = decrease;
    }

    /**
	 * @param increase  
	 */
    public void increaseYspeed(boolean increase) {
//        increaseY = increase;
    }

    /**
	 * @param decrease  
	 */
    public void decreaseYspeed(boolean decrease) {
//        decreaseY = decrease;
    }

    /**
	 * @param zoom  
	 */
    public void zoomIn(boolean zoom) {
//        zoomIn = zoom;
    }

    /**
	 * @param zoom  
	 */
    public void zoomOut(boolean zoom) {
//        zoomOut = zoom;
    }

    public void switchFilter() {
        filter = (filter + 1) % textures.length;
    }

    

    public void init(GLAutoDrawable glDrawable) {

        GL2 gl = glDrawable.getGL().getGL2();
  			
						

        gl.glEnable(GL.GL_TEXTURE_2D);							// Enable Texture Mapping
        fireIcon  = loadTexture("earth.png");
        
        gl.glShadeModel(GL2.GL_SMOOTH);                            //Enables Smooth Color Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);               //This Will Clear The Background Color To Black
        gl.glClearDepth(1.0);                                  //Enables Clearing Of The Depth Buffer
        gl.glEnable(GL.GL_DEPTH_TEST);                            //Enables Depth Testing
        gl.glDepthFunc(GL.GL_LEQUAL);                             //The Type Of Depth Test To Do
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);  // Really Nice Perspective Calculations
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightAmbient, 0);		// Setup The Ambient Light
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, lightDiffuse, 0);		// Setup The Diffuse Light
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPosition, 0);	// Position The Light
        gl.glEnable(GL2.GL_LIGHT1);								// Enable Light One

        gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);					// Full Brightness.  50% Alpha (new )
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);					// Set The Blending Function For Translucency (new )
        
       
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }



    public void display(GLAutoDrawable glDrawable) {
        GL2 gl = glDrawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();                                         //Reset The View
        gl.glTranslatef(0.0f, 0.0f, z);

        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);

        
//        TextureCoords tc = 
        fireIcon.getImageTexCoords();


        fireIcon.enable();
        fireIcon.bind();
        gl.glBegin(GL2.GL_QUADS);
        // Front Face
        
        gl.glTexCoord3f(0.0f, 0.0f, 0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord3f(1.0f, 0.0f, 0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord3f(1.0f, 1.0f, 0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord3f(0.0f, 1.0f, 0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        
        gl.glEnd();
        fireIcon.disable();

   
        // toggle lighting
//        if (lightingChanged) {
//            if (lightingEnabled)
//                gl.glEnable(GL.GL_LIGHTING);
//            else
//                gl.glDisable(GL.GL_LIGHTING);
//            lightingChanged = false;
//        }
//
//        // toggle blending
//        if (blendingChanged) {
//            if (blendingEnabled) {
//                gl.glEnable(GL.GL_BLEND);		// Turn Blending On
//                gl.glDisable(GL.GL_DEPTH_TEST);	// Turn Depth Testing Off
//            } else {
                gl.glDisable(GL.GL_BLEND);		// Turn Blending Off
                gl.glEnable(GL.GL_DEPTH_TEST);	// Turn Depth Testing On
//            }
//            blendingChanged = false;
//        }
                
                displayFPSText(glDrawable);
    }
    
    /*
	 * Debug function for outputting the current Frames Per Second
	 */
	/**
	 * @param drawable  
	 */
	private void displayFPSText(GLAutoDrawable drawable) {
		if (++frameCount == 10) {
			long endTime = System.currentTimeMillis();
			float fps = 10.0f / (endTime - startTime) * 1000;
			frameCount = 0;
			startTime = System.currentTimeMillis();
			fpsText = format.format(fps);
//			int x = drawable.getWidth() - fpsWidth - 5;
//			int y = drawable.getHeight() - 30;
			System.err.println("FPS: " + fpsText);
		}
	}

    public void reshape(GLAutoDrawable glDrawable, int x, int y, int w, int h) {
        final GL2 gl = glDrawable.getGL().getGL2();

        if (h <= 0) // avoid a divide by zero error!
            h = 1;
        final float a = (float) w / (float) h;
        gl.glViewport(0, 0, w, h);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, a, 1.0, 20.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();                                     // Reset The ModalView Matrix
    }

    /**
	 * @param glDrawable  
     * @param b 
     * @param b1 
	 */
    public void displayChanged(GLAutoDrawable glDrawable, boolean b, boolean b1) {
    }
    
	public void repaintCanvas() {
		runSyncInEventThread(new Runnable() {
			public void run() {
				canvas.repaint();
			}
		});
	}
    
	
	 private Texture loadTexture(String filename) {
	        Texture t = null;
	        try {
	             t = TextureIO.newTexture(getClass().getResource(filename), false, ".png");
	             t.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	             t.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	        } catch (IOException e) {
	            System.err.println("Error loading " + filename);
	        }
	        return t;
	    }


	@Override
	public void dispose(GLAutoDrawable arg0) {
		
	}


	

}
