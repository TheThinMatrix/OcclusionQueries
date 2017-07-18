package lensFlare;

import shaders.ShaderProgram;
import shaders.UniformFloat;
import shaders.UniformSampler;
import shaders.UniformVec4;
import utils.MyFile;

/**
 * Sets up the shader program for the rendering the lens flare. It gets the
 * locations of the 3 uniform variables, links the "in_position" variable to
 * attribute 0 of the VAO, and connects the sampler uniform to texture unit 0.
 * 
 * @author Karl
 *
 */
public class FlareShader extends ShaderProgram {

	private static final MyFile VERTEX_SHADER = new MyFile("lensFlare", "flareVertex.glsl");
	private static final MyFile FRAGMENT_SHADER = new MyFile("lensFlare", "flareFragment.glsl");

	protected UniformFloat brightness = new UniformFloat("brightness");
	protected UniformVec4 transform = new UniformVec4("transform");

	private UniformSampler flareTexture = new UniformSampler("flareTexture");

	public FlareShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "in_position");
		super.storeAllUniformLocations(brightness, flareTexture, transform);
		connectTextureUnits();
	}

	private void connectTextureUnits() {
		super.start();
		flareTexture.loadTexUnit(0);
		super.stop();
	}

}
