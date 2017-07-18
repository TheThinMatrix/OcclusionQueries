package water;

import shaders.ShaderProgram;
import shaders.UniformFloat;
import shaders.UniformMatrix;
import shaders.UniformSampler;
import shaders.UniformVec3;
import utils.MyFile;

public class WaterShader extends ShaderProgram {

	private static final MyFile VERTEX_SHADER = new MyFile("water", "waterVertex.txt");
	private static final MyFile FRAGMENT_SHADER = new MyFile("water", "waterFragment.txt");

	protected UniformMatrix modelMatrix = new UniformMatrix("modelMatrix");
	protected UniformMatrix viewMatrix = new UniformMatrix("viewMatrix");
	protected UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");

	protected UniformFloat moveFactor = new UniformFloat("moveFactor");
	protected UniformVec3 cameraPosition = new UniformVec3("cameraPosition");
	protected UniformVec3 lightDirection = new UniformVec3("lightDirection");

	private UniformSampler reflectionTexture = new UniformSampler("reflectionTexture");
	private UniformSampler refractionTexture = new UniformSampler("refractionTexture");
	private UniformSampler dudvMap = new UniformSampler("dudvMap");
	private UniformSampler normalMap = new UniformSampler("normalMap");
	private UniformSampler depthMap = new UniformSampler("depthMap");

	public WaterShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "position");
		super.storeAllUniformLocations(modelMatrix, viewMatrix, projectionMatrix, moveFactor,
				cameraPosition, lightDirection, reflectionTexture, refractionTexture,
				dudvMap, normalMap, depthMap);
		connectTextureUnits();
	}

	private void connectTextureUnits() {
		super.start();
		reflectionTexture.loadTexUnit(0);
		refractionTexture.loadTexUnit(1);
		dudvMap.loadTexUnit(2);
		normalMap.loadTexUnit(3);
		depthMap.loadTexUnit(4);
		super.stop();
	}

}
