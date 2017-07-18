package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector4f;

import entityRenderers.EntityRenderer;
import scene.Scene;
import shinyRenderer.ShinyRenderer;
import skybox.SkyboxRenderer;
import utils.ICamera;
import water.WaterFrameBuffers;
import water.WaterRenderer;

public class MasterRenderer {
	
	private static final Vector4f NO_CLIP = new Vector4f(0, 0, 0, 1);

	private EntityRenderer entityRenderer;
	private ShinyRenderer shinyRenderer;
	private SkyboxRenderer skyRenderer;
	private WaterRenderer waterRenderer;
	private WaterFrameBuffers waterFbos;

	protected MasterRenderer(EntityRenderer entityRenderer, SkyboxRenderer skyRenderer, WaterRenderer waterRenderer, WaterFrameBuffers waterFbos, ShinyRenderer shinyRenderer) {
		this.entityRenderer = entityRenderer;
		this.skyRenderer = skyRenderer;
		this.waterRenderer = waterRenderer;
		this.waterFbos = waterFbos;
		this.shinyRenderer = shinyRenderer;
	}
	
	public void renderLowQualityScene(Scene scene, ICamera cubeMapCamera){
		prepare();
		entityRenderer.render(scene.getImportantEntities(), cubeMapCamera, scene.getLightDirection(), NO_CLIP);
		skyRenderer.render(scene.getSky(), cubeMapCamera);
	}

	protected void renderScene(Scene scene) {
		
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		renderWaterRefractionPass(scene);
		renderWaterReflectionPass(scene);
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		
		renderMainPass(scene);

	}

	protected void cleanUp() {
		entityRenderer.cleanUp();
		skyRenderer.cleanUp();
		waterRenderer.cleanUp();
		shinyRenderer.cleanUp();
	}

	private void prepare() {
		GL11.glClearColor(1, 1, 1, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	private void renderWaterReflectionPass(Scene scene){
		waterFbos.bindReflectionFrameBuffer();
		prepare();
		scene.getCamera().reflect(scene.getWaterHeight());
		entityRenderer.render(scene.getReflectedEntities(), scene.getCamera(), scene.getLightDirection(), new Vector4f(0,1,0,0.1f));
		skyRenderer.render(scene.getSky(), scene.getCamera());
		waterFbos.unbindCurrentFrameBuffer();
		scene.getCamera().reflect(scene.getWaterHeight());
	}
	
	private void renderWaterRefractionPass(Scene scene){
		waterFbos.bindRefractionFrameBuffer();
		prepare();
		entityRenderer.render(scene.getUnderwaterEntities(), scene.getCamera(), scene.getLightDirection(), new Vector4f(0,-1,0, 0));
		waterFbos.unbindCurrentFrameBuffer();
	}
	
	private void renderMainPass(Scene scene){
		prepare();
		entityRenderer.render(scene.getAllEntities(), scene.getCamera(), scene.getLightDirection(), NO_CLIP);
		shinyRenderer.render(scene.getShinyEntities(), scene.getEnvironmentMap(), scene.getCamera(), scene.getLightDirection());
		skyRenderer.render(scene.getSky(), scene.getCamera());
		waterRenderer.render(scene.getWater(), scene.getCamera(), scene.getLightDirection());
	}

}
