package water;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import openglObjects.Vao;
import textures.Texture;
import utils.ICamera;
import utils.MyFile;
import utils.OpenGlUtils;

public class WaterRenderer {

	private static final MyFile DUDV_MAP = new MyFile("res", "waterDUDV.png");
	private static final MyFile NORMAL_MAP = new MyFile("res", "normal.png");
	// private static final float WAVE_SPEED = 0.03f;

	private Vao quad;
	private WaterShader shader;
	private WaterFrameBuffers fbos;

	private float moveFactor = 0;

	private Texture dudvTexture;
	private Texture normalMap;

	public WaterRenderer(WaterFrameBuffers fbos) {
		this.shader = new WaterShader();
		this.fbos = fbos;
		this.quad = QuadGenerator.generateQuad();
		this.normalMap = Texture.newTexture(NORMAL_MAP).create();
		this.dudvTexture = Texture.newTexture(DUDV_MAP).anisotropic().create();
	}

	public void render(List<WaterTile> water, ICamera camera, Vector3f lightDir) {
		prepareRender(camera, lightDir);
		for (WaterTile tile : water) {
			Matrix4f modelMatrix = createModelMatrix(tile.getX(), tile.getHeight(), tile.getZ(), WaterTile.TILE_SIZE);
			shader.modelMatrix.loadMatrix(modelMatrix);
			GL11.glDrawElements(GL11.GL_TRIANGLES, quad.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		finish();
	}

	public void cleanUp() {
		quad.delete();
		dudvTexture.delete();
		normalMap.delete();
		fbos.cleanUp();
		shader.cleanUp();
	}

	private void prepareRender(ICamera camera, Vector3f lightDir) {
		shader.start();
		shader.projectionMatrix.loadMatrix(camera.getProjectionMatrix());
		shader.viewMatrix.loadMatrix(camera.getViewMatrix());
		shader.cameraPosition.loadVec3(camera.getPosition());

		moveFactor += 0.0005f;
		moveFactor %= 1;
		shader.moveFactor.loadFloat(moveFactor);

		shader.lightDirection.loadVec3(lightDir);

		quad.bind(0);

		bindTextures();

		doRenderSettings();
	}

	private void bindTextures() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getReflectionTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractionTexture());
		dudvTexture.bindToUnit(2);
		normalMap.bindToUnit(3);
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractionDepthTexture());
	}

	private void doRenderSettings() {
		OpenGlUtils.enableDepthTesting(true);
		OpenGlUtils.antialias(false);
		OpenGlUtils.cullBackFaces(true);
		OpenGlUtils.enableAlphaBlending();
	}

	private void finish() {
		quad.unbind(0);
		shader.stop();
	}

	private Matrix4f createModelMatrix(float x, float y, float z, float scale) {
		Matrix4f modelMatrix = new Matrix4f();
		Matrix4f.translate(new Vector3f(x, y, z), modelMatrix, modelMatrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), modelMatrix, modelMatrix);
		return modelMatrix;
	}

}
