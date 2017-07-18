package skybox;

import org.lwjgl.opengl.GL11;

import openglObjects.Vao;
import utils.ICamera;
import utils.OpenGlUtils;

public class SkyboxRenderer {
	
	private SkyboxShader shader;
	
	public SkyboxRenderer(){
		this.shader = new SkyboxShader();
	}
	
	public void render(Skybox skybox, ICamera camera){
		prepare(skybox, camera);
		Vao model = skybox.getCubeVao();
		model.bind(0);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
		model.unbind(0);
		finish();
	}
	
	public void cleanUp(){
		shader.cleanUp();
	}
	
	private void prepare(Skybox skybox, ICamera camera){
		shader.start();
		GL11.glDepthMask(false);
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
		skybox.getTexture().bindToUnit(0);
		OpenGlUtils.disableBlending();
		OpenGlUtils.enableDepthTesting(true);
		OpenGlUtils.cullBackFaces(true);
		OpenGlUtils.antialias(false);
	}
	
	private void finish(){
		GL11.glDepthMask(true);
		shader.stop();
	}	

}
