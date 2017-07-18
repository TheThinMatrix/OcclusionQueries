package scene;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import skybox.Skybox;
import textures.Texture;
import utils.ICamera;
import water.WaterTile;

public class Scene {

	// storing the entities multiple times in these various lists isn't great, but
	// it's okay for this very simple scene with just a few object.
	
	private List<Entity> standardEntities = new ArrayList<Entity>();
	private List<Entity> reflectableEntities = new ArrayList<Entity>();
	private List<Entity> underwaterEntities = new ArrayList<Entity>();
	private List<Entity> importantEntities = new ArrayList<Entity>();
	private List<Entity> shinyEntities = new ArrayList<Entity>();
	
	private List<WaterTile> waterTiles = new ArrayList<WaterTile>();
	
	private ICamera camera;
	private Vector3f lightDirection = new Vector3f(0, -1, 0);
	private Skybox sky;
	
	private Texture environmentMap;
	
	private float waterHeight = -0.1f;//should set elsewhere

	public Scene(ICamera camera, Skybox sky) {
		this.camera = camera;
		this.sky = sky;
		environmentMap = Texture.newEmptyCubeMap(256);
		waterTiles.add(new WaterTile(-20, 6, waterHeight));
		waterTiles.add(new WaterTile(-10, 6, waterHeight));
		waterTiles.add(new WaterTile(0, 6, waterHeight));
		waterTiles.add(new WaterTile(10, 6, waterHeight));
	}

	public void setLightDirection(Vector3f direction) {
		direction.normalise();
		this.lightDirection.set(direction);
	}
	
	public Texture getEnvironmentMap(){
		return environmentMap;
	}
	
	public float getWaterHeight(){
		return waterHeight;
	}

	public List<WaterTile> getWater() {
		return waterTiles;
	}

	public void addTerrain(Entity terrain) {
		standardEntities.add(terrain);
		importantEntities.add(terrain);
		reflectableEntities.add(terrain);
		underwaterEntities.add(terrain);
	}
	
	public void addShiny(Entity entity){
		if(entity.isSeenUnderWater()){
			underwaterEntities.add(entity);
		}
		if(entity.hasReflection()){
			reflectableEntities.add(entity);
		}
		shinyEntities.add(entity);
	}

	public void addEntity(Entity entity) {
		standardEntities.add(entity);
		if(entity.isSeenUnderWater()){
			underwaterEntities.add(entity);
		}
		if(entity.hasReflection()){
			reflectableEntities.add(entity);
		}
		if(entity.isImportant()){
			importantEntities.add(entity);
		}
	}

	public Skybox getSky() {
		return sky;
	}

	public Vector3f getLightDirection() {
		return lightDirection;
	}

	public ICamera getCamera() {
		return camera;
	}
	
	public List<Entity> getReflectedEntities() {
		return reflectableEntities;
	}
	
	public List<Entity> getImportantEntities() {
		return importantEntities;
	}
	
	public List<Entity> getShinyEntities() {
		return shinyEntities;
	}
	
	public List<Entity> getUnderwaterEntities() {
		return underwaterEntities;
	}

	public List<Entity> getAllEntities() {
		return standardEntities;
	}

	public void delete() {
		sky.delete();
		for (Entity entity : standardEntities) {
			entity.delete();
		}
		environmentMap.delete();
	}

}
