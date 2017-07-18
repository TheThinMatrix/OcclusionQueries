package entityRenderers;

import shaders.ShaderProgram;
import shaders.UniformBoolean;
import shaders.UniformMatrix;
import shaders.UniformSampler;
import shaders.UniformVec3;
import shaders.UniformVec4;
import utils.MyFile;

public class EntityShader extends ShaderProgram {

	private static final MyFile VERTEX_SHADER = new MyFile("entityRenderers", "entityVertex.txt");
	private static final MyFile FRAGMENT_SHADER = new MyFile("entityRenderers",
			"entityFragment.txt");

	protected UniformMatrix projectionViewMatrix = new UniformMatrix("projectionViewMatrix");
	protected UniformBoolean hasExtraMap = new UniformBoolean("hasExtraMap");
	protected UniformVec3 lightDirection = new UniformVec3("lightDirection");
	protected UniformVec4 plane = new UniformVec4("plane");

	private UniformSampler diffuseMap = new UniformSampler("diffuseMap");
	private UniformSampler extraMap = new UniformSampler("extraMap");

	public EntityShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "in_position", "in_textureCoords", "in_normal");
		super.storeAllUniformLocations(projectionViewMatrix, diffuseMap, extraMap, hasExtraMap,
				lightDirection, plane);
		connectTextureUnits();
	}

	private void connectTextureUnits() {
		super.start();
		diffuseMap.loadTexUnit(0);
		extraMap.loadTexUnit(1);
		super.stop();
	}

}
