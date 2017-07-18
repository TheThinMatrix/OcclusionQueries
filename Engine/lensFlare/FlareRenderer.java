package lensFlare;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector2f;

import openglObjects.Query;
import openglObjects.Vao;
import utils.OpenGlUtils;

/**
 * 
 * Renders 2D textured quads onto the screen.
 * 
 * @author Karl
 *
 */
public class FlareRenderer {

	// 4 vertex positions for a 2D quad.
	private static final float[] POSITIONS = { -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f };

	private static final float TEST_QUAD_WIDTH = 0.07f;
	private static final float TEST_QUAD_HEIGHT = TEST_QUAD_WIDTH * (float) Display.getWidth() / Display.getHeight();
	private static final float TOTAL_SAMPLES = (float) Math.pow(TEST_QUAD_WIDTH * Display.getWidth() * 0.5f, 2) * 4;

	// A VAO containing the quad's positions in attribute 0
	private final Vao quad;
	private final FlareShader shader;
	private final Query query;

	private float coverage = 0;
	
	/**
	 * Initializes the shader program, and creates a VAO for the quad, storing
	 * the data for the 4 quad vertices in attribute 0 of the VAO.
	 */
	public FlareRenderer() {
		this.shader = new FlareShader();
		this.query = new Query(GL15.GL_SAMPLES_PASSED);
		this.quad = Vao.create();
		quad.bind();
		quad.storeData(4, POSITIONS);
		quad.unbind();
	}

	/**
	 * Renders FlareTextures onto the screen at their positions, at the
	 * specified brightness.
	 * 
	 * @param flares
	 *            - An array of the FlareTextures that need to be rendered to
	 *            the screen.
	 * @param brightness
	 *            - The brightness that all the FlareTextures should be rendered
	 *            at.
	 */
	public void render(Vector2f sunScreenPos, FlareTexture[] flares, float brightness) {
		prepare(brightness);
		doOcclusionTest(sunScreenPos);
		OpenGlUtils.enableAdditiveBlending();
		OpenGlUtils.enableDepthTesting(false);
		for (FlareTexture flare : flares) {
			renderFlare(flare);
		}
		endRendering();
	}

	private void doOcclusionTest(Vector2f sunScreenCoords) {
		if(query.isResultReady()){
			int visibleSamples = query.getResult();
			this.coverage = Math.min(visibleSamples / TOTAL_SAMPLES, 1f);
		}
		if (!query.isInUse()) {
			GL11.glColorMask(false, false, false, false);
			GL11.glDepthMask(false);
			query.start();
			OpenGlUtils.enableDepthTesting(true);
			shader.transform.loadVec4(sunScreenCoords.x, sunScreenCoords.y, TEST_QUAD_WIDTH, TEST_QUAD_HEIGHT);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
			query.end();
			GL11.glColorMask(true, true, true, true);
			GL11.glDepthMask(true);
		}
	}

	/**
	 * Cleans up the shader program. To be used when the application is closed.
	 */
	public void cleanUp() {
		query.delete();
		shader.cleanUp();
	}

	/**
	 * Prepares for rendering the FlareTextures. Antialiasing is disabled as it
	 * isn't needed. Additive blending is enables so that transparent parts of
	 * the texture aren't rendered, and to give the entire texture a "glowing"
	 * look. Depth testing is also disabled because the flare textures should
	 * always be rendered on top of everything else in the scene. For this
	 * reason the lens flare effect MUST be rendered after rendering everything
	 * else in the 3D scene (But render before GUIs if you want the GUIs to go
	 * in front of the lens flare.) Backface culling also unnecessary here.
	 * 
	 * The shader program is also started, and the brightness value loaded up to
	 * it as a uniform variable. The quad's VAO is bound, ready for use, and its
	 * attribute 0 is enabled (attribute 0 contains the position data).
	 * 
	 * 
	 * @param brightness
	 *            - the brightness at which the flares are going to be rendered.
	 */
	private void prepare(float brightness) {
		OpenGlUtils.antialias(false);
		shader.start();
		shader.brightness.loadFloat(brightness * coverage);
		quad.bind(0);
	}

	/**
	 * Renders a single flare texture to the screen on a textured 2D quad.
	 * 
	 * The texture for this flare is first bound to texture unit 0. The x and y
	 * scale of the quad is then determined. The x scale is simply the scale
	 * value in the FlareTexture instance, and then the y scale is calculated by
	 * multiplying that by the aspect ratio of the display. This ensures that
	 * the quad is a square, and not a rectangle.
	 * 
	 * The position and scale is then loaded up the the shader. Finally, the
	 * quad is rendered using glDrawArrays (no index buffer used), and using
	 * GL_TRIANGLE_STRIP. This allows the quad to be specified using only 4
	 * vertex positions, instead of having to specify the 6 vertex positions for
	 * the 2 triangles. See GUI tutorial for more info.
	 * 
	 * @param flare
	 *            - The flare to be rendered.
	 */
	private void renderFlare(FlareTexture flare) {
		flare.getTexture().bindToUnit(0);
		float xScale = flare.getScale();
		float yScale = xScale * (float) Display.getWidth() / Display.getHeight();
		Vector2f centerPos = flare.getScreenPos();
		shader.transform.loadVec4(centerPos.x, centerPos.y, xScale, yScale);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
	}

	/**
	 * Unbind the quad VAO, stop the shader program, and undo any settings that
	 * were changed before rendering.
	 */
	private void endRendering() {
		quad.unbind(0);
		shader.stop();
		OpenGlUtils.disableBlending();
		OpenGlUtils.enableDepthTesting(true);
	}

}
