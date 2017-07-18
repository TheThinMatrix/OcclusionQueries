package scene;

import textures.Texture;

public class Skin {

	private Texture diffuseTexture;
	private Texture extraInfoMap;
	
	private boolean transparent;
	
	public Skin(Texture diffuseTexture, Texture extraInfoMap){
		this.diffuseTexture = diffuseTexture;
		this.extraInfoMap = extraInfoMap;
	}
	
	public void delete(){
		diffuseTexture.delete();
		if(extraInfoMap!=null){
			extraInfoMap.delete();
		}
	}
	
	public void setTransparent(boolean transparent){
		this.transparent = transparent;
	}
	
	public boolean hasTransparency(){
		return transparent;
	}
	
	public boolean hasExtraMap(){
		return extraInfoMap!=null;
	}
	
	public Texture getDiffuseTexture(){
		return diffuseTexture;
	}
	
	public Texture getExtraInfoMap(){
		return extraInfoMap;
	}
	
}
