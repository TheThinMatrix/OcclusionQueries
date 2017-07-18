package entityRenderers;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import openglObjects.Vao;
import scene.Entity;
import scene.Skin;
import utils.ICamera;
import utils.OpenGlUtils;

public class EntityRenderer {
	
	private EntityShader shader;

	public EntityRenderer() {
		this.shader = new EntityShader();
	}

	public void render(List<Entity> entities, ICamera camera, Vector3f lightDir, Vector4f clipPlane) {
		prepare(camera, lightDir, clipPlane);
		for (Entity entity : entities) {
			prepareSkin(entity.getSkin());
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

	private void prepare(ICamera camera, Vector3f lightDir, Vector4f clipPlane) {
		shader.start();
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
		shader.lightDirection.loadVec3(lightDir);
		shader.plane.loadVec4(clipPlane);
		OpenGlUtils.antialias(true);
		OpenGlUtils.disableBlending();
		OpenGlUtils.enableDepthTesting(true);
	}

	private void finish() {
		shader.stop();
	}

	private void prepareSkin(Skin skin) {
		skin.getDiffuseTexture().bindToUnit(0);
		if (skin.hasExtraMap()) {
			skin.getExtraInfoMap().bindToUnit(1);
		}
		shader.hasExtraMap.loadBoolean(skin.hasExtraMap());
		OpenGlUtils.cullBackFaces(!skin.hasTransparency());
	}

}
