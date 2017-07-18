package lensFlare;

import org.lwjgl.util.vector.Vector2f;

import textures.Texture;

public class FlareTexture {
	
	private final Texture texture;
	private final float scale;
	
	private Vector2f screenPos = new Vector2f();

	public FlareTexture(Texture texture, float scale){
		this.texture = texture;
		this.scale = scale;
	}
	
	public void setScreenPos(Vector2f newPos){
		this.screenPos.set(newPos);
	}
	
	public Texture getTexture() {
		return texture;
	}

	public float getScale() {
		return scale;
	}

	public Vector2f getScreenPos() {
		return screenPos;
	}
	
}
