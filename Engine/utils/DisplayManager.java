package utils;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	private static final String TITLE = "Socuwan Scene";
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 100;
	
	private static long lastFrameTime;
	private static float delta;
	
	public static DisplayManager createDisplay(){
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.create(new PixelFormat().withDepthBits(24).withSamples(4));
			Display.setTitle(TITLE);
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.err.println("Couldn't create display!");
			System.exit(-1);
		}	
		GL11.glViewport(0,0, WIDTH, HEIGHT);
		return new DisplayManager();
	}
	
	private DisplayManager(){		
		lastFrameTime = getCurrentTime();
	}
	
	public void update(){
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
	}
	
	public float getFrameTime(){
		return delta;
	}
	
	public void closeDisplay(){
		Display.destroy();
	}
	
	private long getCurrentTime(){
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	
	

}
