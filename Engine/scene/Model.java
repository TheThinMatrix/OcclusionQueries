package scene;

import openglObjects.Vao;

public class Model {
	
	private final Vao vao;
	
	public Model(Vao vao){
		this.vao = vao;
	}
	
	public Vao getVao(){
		return vao;
	}
	
	public void delete(){
		vao.delete();
	}

}
