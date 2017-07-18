package loaders;

import java.io.BufferedReader;
import java.io.IOException;

import extra.Camera;
import main.WorldSettings;
import scene.Entity;
import scene.Scene;
import skybox.Skybox;
import utils.ICamera;
import utils.MyFile;

public class SceneLoader {

	private EntityLoader entityLoader;
	private SkyboxLoader skyLoader;

	public SceneLoader(EntityLoader entityLoader, SkyboxLoader skyLoader) {
		this.entityLoader = entityLoader;
		this.skyLoader = skyLoader;
	}

	public Scene loadScene(MyFile sceneFile) {
		MyFile sceneList = new MyFile(sceneFile, LoaderSettings.ENTITY_LIST_FILE);
		BufferedReader reader = getReader(sceneList);
		MyFile[] terrainFiles = readEntityFiles(reader, sceneFile);
		MyFile[] shinyFiles = readEntityFiles(reader, sceneFile);
		MyFile[] entityFiles = readEntityFiles(reader, sceneFile);
		closeReader(reader);
		Skybox sky = skyLoader.loadSkyBox(new MyFile(sceneFile, LoaderSettings.SKYBOX_FOLDER));
		return createScene(terrainFiles, entityFiles, shinyFiles, sky);
	}

	private Scene createScene(MyFile[] terrainFiles, MyFile[] entityFiles, MyFile[] shinyFiles, Skybox sky){
		ICamera camera = new Camera();
		Scene scene = new Scene(camera, sky);
		scene.setLightDirection(WorldSettings.LIGHT_DIR);
		addEntities(scene, entityFiles);
		addShinyEntities(scene, shinyFiles);
		addTerrains(scene, terrainFiles);
		return scene;
	}
	
	private void addEntities(Scene scene, MyFile[] entityFiles){
		for(MyFile file : entityFiles){
			Entity entity = entityLoader.loadEntity(file);
			scene.addEntity(entity);
		}
	}
	
	private void addShinyEntities(Scene scene, MyFile[] entityFiles){
		for(MyFile file : entityFiles){
			Entity entity = entityLoader.loadEntity(file);
			scene.addShiny(entity);
		}
	}
	
	private void addTerrains(Scene scene, MyFile[] terrainFiles){
		for(MyFile file : terrainFiles){
			Entity entity = entityLoader.loadEntity(file);
			scene.addTerrain(entity);
		}
	}
	
	private BufferedReader getReader(MyFile file) {
		try {
			return file.getReader();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Couldn't find scene file: " + file);
			System.exit(-1);
			return null;
		}
	}
	
	private void closeReader(BufferedReader reader){
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private MyFile[] readEntityFiles(BufferedReader reader, MyFile sceneFile) {
		try {
			String line = reader.readLine();
			String[] names = line.split(LoaderSettings.SEPARATOR);
			MyFile[] files = new MyFile[names.length];
			for(int i=0;i<files.length;i++){
				files[i] = new MyFile(sceneFile, names[i]);
			}
			return files;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't read scene file: "+sceneFile);
			System.exit(-1);
			return null;
		}
	}

}
