package main;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import extra.Camera;
import lensFlare.FlareManager;
import lensFlare.FlareTexture;
import loaders.LoaderSettings;
import loaders.SceneLoader;
import loaders.SceneLoaderFactory;
import renderEngine.RenderEngine;
import scene.Scene;
import sunRenderer.Sun;
import sunRenderer.SunRenderer;
import textures.Texture;
import utils.MyFile;

public class MainApp {

	public static void main(String[] args) {

		RenderEngine engine = RenderEngine.init();
		SceneLoader loader = SceneLoaderFactory.createSceneLoader();
		Scene scene = loader.loadScene(new MyFile(LoaderSettings.RES_FOLDER, "Socuwan Scene"));

		engine.renderEnvironmentMap(scene.getEnvironmentMap(), scene, new Vector3f(0, 2, 0));

		MyFile flareFolder = new MyFile("res", "lensFlare");
		//loading textures for lens flare
		Texture texture1 = Texture.newTexture(new MyFile(flareFolder, "tex1.png")).normalMipMap().create();
		Texture texture2 = Texture.newTexture(new MyFile(flareFolder, "tex2.png")).normalMipMap().create();
		Texture texture3 = Texture.newTexture(new MyFile(flareFolder, "tex3.png")).normalMipMap().create();
		Texture texture4 = Texture.newTexture(new MyFile(flareFolder, "tex4.png")).normalMipMap().create();
		Texture texture5 = Texture.newTexture(new MyFile(flareFolder, "tex5.png")).normalMipMap().create();
		Texture texture6 = Texture.newTexture(new MyFile(flareFolder, "tex6.png")).normalMipMap().create();
		Texture texture7 = Texture.newTexture(new MyFile(flareFolder, "tex7.png")).normalMipMap().create();
		Texture texture8 = Texture.newTexture(new MyFile(flareFolder, "tex8.png")).normalMipMap().create();
		Texture texture9 = Texture.newTexture(new MyFile(flareFolder, "tex9.png")).normalMipMap().create();
		Texture sun = Texture.newTexture(new MyFile(flareFolder, "sun.png")).normalMipMap().create();

		//set up lens flare
		FlareManager lensFlare = new FlareManager(0.16f, new FlareTexture(texture6, 1f),
				new FlareTexture(texture4, 0.46f), new FlareTexture(texture2, 0.2f), new FlareTexture(texture7, 0.1f), new FlareTexture(texture1, 0.04f),
				new FlareTexture(texture3, 0.12f), new FlareTexture(texture9, 0.24f), new FlareTexture(texture5, 0.14f), new FlareTexture(texture1, 0.024f), new FlareTexture(texture7, 0.4f),
				new FlareTexture(texture9, 0.2f), new FlareTexture(texture3, 0.14f), new FlareTexture(texture5, 0.6f), new FlareTexture(texture4, 0.8f),
				new FlareTexture(texture8, 1.2f));

		//init sun and set sun direction
		Sun theSun = new Sun(sun, 40);
		SunRenderer sunRenderer = new SunRenderer();
		theSun.setDirection(WorldSettings.LIGHT_DIR);

		while (!Display.isCloseRequested()) {
			((Camera) scene.getCamera()).move();
			engine.renderScene(scene);
			sunRenderer.render(theSun, scene.getCamera());
			lensFlare.render(scene.getCamera(), theSun.getWorldPosition(scene.getCamera().getPosition()));
			engine.update();
		}

		lensFlare.cleanUp();
		sunRenderer.cleanUp();
		scene.delete();
		engine.close();

	}

}
