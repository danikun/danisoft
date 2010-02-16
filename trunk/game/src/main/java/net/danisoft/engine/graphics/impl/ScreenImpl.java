package net.danisoft.engine.graphics.impl;

import java.util.ResourceBundle;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


import net.danisoft.engine.graphics.Screen;

/**
 * Implementation of an standard OpenGL display.
 * 
 * @author Daniel García Pino
 *
 */
public class ScreenImpl implements Screen {
	
	/**
	 * Pixel height of the display.
	 */
	private int height;
	/**
	 * Pixel width of the display.
	 */
	private int width;
	/**
	 * Framerate of the screen.
	 */
	private int framerate;
	/**
	 * Fullscreen mode enabled or disabled.
	 */
	private boolean fullscreen;
	
	/**
	 * Class constructor.
	 * 
	 * @param framerate framerate of the screen.
	 * @param fullscreen fullscreen mode.
	 */
	public ScreenImpl(int framerate, boolean fullscreen){
		this.framerate = framerate;
		this.fullscreen = fullscreen;
		
		this.init();
	}

	/**
	 * Method used to print the current frame.
	 */
	public void print() {
		Display.update();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
	}

	/**
	 * Method that initialize the screen.
	 */
	public void init() {
		
		ResourceBundle bundle = null;
		
		bundle = ResourceBundle.getBundle("net.danisoft.conf.graphics");
		
		height = Integer.parseInt(bundle.getString("height"));
		width = Integer.parseInt(bundle.getString("width"));
		
		DisplayMode chosenMode = null;
		
		try {
		     DisplayMode[] modes = Display.getAvailableDisplayModes();
		 
		     for (int i=0;i<modes.length;i++) {
		          if ((modes[i].getWidth() == width) && (modes[i].getHeight() == height)) {
		               chosenMode = modes[i];
		               break;
		          }
		     }
		} catch (LWJGLException e) {     
		     Sys.alert("Error", "Unable to determine display modes.");
		     System.exit(0);
		}
		
		if (chosenMode == null) {
		     Sys.alert("Error", "Unable to find appropriate display mode.");
		     System.exit(0);
		}
		
		try {
		    Display.setDisplayMode(chosenMode);
		    Display.setTitle("An example title");
		    Display.create();
		} catch (LWJGLException e) {
		    Sys.alert("Error", "Unable to create display.");
		    System.exit(0);
		}
		
		/* select clearing (background) color */
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        /* initialize viewing values */
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, Display.getDisplayMode().getWidth(), 0.0, Display.getDisplayMode().getHeight(), -1.0, 1.0);
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);        
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
        GL11.glClearDepth(1);                                       
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        GL11.glViewport(0,0,Display.getDisplayMode().getWidth(),Display.getDisplayMode().getHeight());

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

	}

}
