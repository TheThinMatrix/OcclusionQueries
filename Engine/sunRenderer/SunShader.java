package sunRenderer;

import shaders.ShaderProgram;
import shaders.UniformMatrix;
import shaders.UniformSampler;
import utils.MyFile;

public class SunShader extends ShaderProgram {

	private static final MyFile VERTEX_SHADER = new MyFile("sunRenderer", "sunVertex.glsl");
	private static final MyFile FRAGMENT_SHADER = new MyFile("sunRenderer", "sunFragment.glsl");
	
	protected UniformSampler sunTexture = new UniformSampler("sunTexture");
	protected UniformMatrix mvpMatrix = new UniformMatrix("mvpMatrix");

	public SunShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "in_position");
		super.storeAllUniformLocations(sunTexture, mvpMatrix);
		connectTextureUnits();
	}

	private void connectTextureUnits() {
		super.start();
		sunTexture.loadTexUnit(0);
		super.stop();
	}

}
