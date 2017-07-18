package shinyRenderer;

import shaders.ShaderProgram;
import shaders.UniformMatrix;
import shaders.UniformSampler;
import shaders.UniformVec3;
import utils.MyFile;

public class ShinyShader extends ShaderProgram {

	private static final MyFile VERTEX_SHADER = new MyFile("shinyRenderer", "shinyVertex.txt");
	private static final MyFile FRAGMENT_SHADER = new MyFile("shinyRenderer", "shinyFragment.txt");

	protected UniformMatrix projectionViewMatrix = new UniformMatrix("projectionViewMatrix");
	protected UniformVec3 cameraPosition = new UniformVec3("cameraPosition");
	protected UniformVec3 lightDirection = new UniformVec3("lightDirection");

	private UniformSampler diffuseMap = new UniformSampler("diffuseMap");
	private UniformSampler enviroMap = new UniformSampler("enviroMap");

	public ShinyShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "in_position", "in_textureCoords", "in_normal");
		super.storeAllUniformLocations(projectionViewMatrix, diffuseMap, cameraPosition, lightDirection, enviroMap);
		connectTextureUnits();
	}

	private void connectTextureUnits() {
		super.start();
		diffuseMap.loadTexUnit(0);
		enviroMap.loadTexUnit(1);
		super.stop();
	}

}
