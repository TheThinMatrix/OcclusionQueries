package sunRenderer;

import org.lwjgl.util.vector.Vector3f;

import textures.Texture;

public class Sun {

	private static final float SUN_DIS = 50;// fairly arbitrary - but make sure
											// it doesn't go behind skybox

	private final Texture texture;

	private Vector3f lightDirection = new Vector3f(0, -1, 0);
	private float scale;

	public Sun(Texture texture, float scale) {
		this.texture = texture;
		this.scale = scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void setDirection(Vector3f dir) {
		lightDirection.set(dir);
		lightDirection.normalise();
	}

	public Texture getTexture() {
		return texture;
	}

	public Vector3f getLightDirection() {
		return lightDirection;
	}

	public float getScale() {
		return scale;
	}

	/**
	 * Calculates a position for the sun, based on the light direction. The
	 * distance of the sun from the camera is fairly arbitrary, although care
	 * should be taken to ensure it doesn't get rendered outside the skybox.
	 * 
	 * @param camPos - The camera's position.
	 * @return The 3D world position of the sun.
	 */
	public Vector3f getWorldPosition(Vector3f camPos) {
		Vector3f sunPos = new Vector3f(lightDirection);
		sunPos.negate();
		sunPos.scale(SUN_DIS);
		return Vector3f.add(camPos, sunPos, null);
	}

}
