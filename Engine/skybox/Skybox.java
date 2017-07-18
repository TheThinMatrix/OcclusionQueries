package skybox;

import openglObjects.Vao;
import textures.Texture;

public class Skybox {
	
	private Vao cube;
	private Texture texture;
	
	public Skybox(Texture cubeMapTexture, float size){
		cube = CubeGenerator.generateCube(size);
		this.texture = cubeMapTexture;
	}
	
	public Vao getCubeVao(){
		return cube;
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public void delete(){
		cube.delete();
		texture.delete();
	}

}
