package renderEngine;

import org.lwjgl.util.vector.Vector3f;

import entityRenderers.EntityRenderer;
import environmentMapRenderer.EnviroMapRenderer;
import scene.Scene;
import shinyRenderer.ShinyRenderer;
import skybox.SkyboxRenderer;
import textures.Texture;
import utils.DisplayManager;
import water.WaterFrameBuffers;
import water.WaterRenderer;

public class RenderEngine {

	private DisplayManager display;
	private MasterRenderer renderer;

	private RenderEngine(DisplayManager display, MasterRenderer renderer) {
		this.display = display;
		this.renderer = renderer;
	}

	public void update() {
		display.update();
	}

	public void renderScene(Scene scene) {
		renderer.renderScene(scene);
	}
	
	public void renderEnvironmentMap(Texture enviroMap, Scene scene, Vector3f center){
		EnviroMapRenderer.renderEnvironmentMap(enviroMap, scene, center, renderer);
	}

	public void close() {
		renderer.cleanUp();
		display.closeDisplay();
	}

	public static RenderEngine init() {
		DisplayManager display = DisplayManager.createDisplay();
		EntityRenderer basicRenderer = new EntityRenderer();
		WaterFrameBuffers waterFbos = new WaterFrameBuffers();
		SkyboxRenderer skyRenderer = new SkyboxRenderer();
		WaterRenderer waterRenderer = new WaterRenderer(waterFbos);
		ShinyRenderer shinyRenderer = new ShinyRenderer();
		MasterRenderer renderer = new MasterRenderer(basicRenderer, skyRenderer, waterRenderer, waterFbos,
				shinyRenderer);
		return new RenderEngine(display, renderer);
	}

}
