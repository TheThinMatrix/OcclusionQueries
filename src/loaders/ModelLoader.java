package loaders;

import objConverter.ModelData;
import objConverter.OBJFileLoader;
import openglObjects.Vao;
import scene.Model;
import utils.MyFile;

public class ModelLoader {

	protected Model loadModel(MyFile modelFile) {
		ModelData data = OBJFileLoader.loadOBJ(modelFile);
		Vao vao = Vao.create();
		vao.storeData(data.getIndices(), data.getVertexCount(), data.getVertices(), data.getTextureCoords(),
				data.getNormals());
		return new Model(vao);
	}

}
