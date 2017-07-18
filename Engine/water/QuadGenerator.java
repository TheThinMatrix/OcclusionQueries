package water;

import openglObjects.Vao;

public class QuadGenerator {
	
	private static final int VERTEX_COUNT = 4;
	private static final float[] VERTICES = {0, 0, 1, 0, 1, 1, 0, 1};
	private static final int[] INDICES = {0,3,1,1,3,2};

	public static Vao generateQuad() {
		Vao vao = Vao.create();
		vao.storeData(INDICES, VERTEX_COUNT, VERTICES);
		return vao;
	}

}
