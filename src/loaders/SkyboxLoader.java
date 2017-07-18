package loaders;

import skybox.Skybox;
import textures.Texture;
import utils.MyFile;

public class SkyboxLoader {

	protected Skybox loadSkyBox(MyFile skyboxFolder) {
		MyFile[] textureFiles = getSkyboxTexFiles(skyboxFolder);
		Texture cubeMap = Texture.newCubeMap(textureFiles);
		return new Skybox(cubeMap, LoaderSettings.SKYBOX_SIZE);
	}

	private MyFile[] getSkyboxTexFiles(MyFile skyboxFolder) {
		MyFile[] files = new MyFile[LoaderSettings.SKYBOX_TEX_FILES.length];
		for (int i = 0; i < files.length; i++) {
			files[i] = new MyFile(skyboxFolder, LoaderSettings.SKYBOX_TEX_FILES[i]);
		}
		return files;
	}

}
