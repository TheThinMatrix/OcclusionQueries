package shinyRenderer;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import openglObjects.Vao;
import scene.Entity;
import textures.Texture;
import utils.ICamera;
import utils.OpenGlUtils;

public class ShinyRenderer {
	
	private ShinyShader shader;

	public ShinyRenderer() {
		this.shader = new ShinyShader();
	}

	public void render(List<Entity> shinyEntities, Texture enviromap, ICamera camera, Vector3f lightDir) {
		prepare(camera, lightDir, enviromap);
		for (Entity entity : shinyEntities) {
			entity.getSkin().getDiffuseTexture().bindToUnit(0);
			Vao model = entity.getModel().getVao();
			model.bind(0, 1, 2);
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
			model.unbind(0, 1, 2);
		}
		finish();
	}
	
	public void cleanUp(){
		shader.cleanUp();
	}

	private void prepare(ICamera camera, Vector3f lightDir, Texture enviromap) {
		shader.start();
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
		shader.lightDirection.loadVec3(lightDir);
		shader.cameraPosition.loadVec3(camera.getPosition());
		enviromap.bindToUnit(1);
		OpenGlUtils.antialias(true);
		OpenGlUtils.disableBlending();
		OpenGlUtils.enableDepthTesting(true);
		OpenGlUtils.cullBackFaces(true);
	}

	private void finish() {
		shader.stop();
	}

}
