package loaders;

import scene.Entity;
import scene.Model;
import scene.Skin;
import utils.MyFile;

public class EntityLoader {
	
	private ModelLoader modelLoader;
	private SkinLoader skinLoader;
	private ConfigsLoader configsLoader;
	
	protected EntityLoader(ModelLoader modelLoader, SkinLoader skinLoader, ConfigsLoader configsLoader){
		this.modelLoader = modelLoader;
		this.skinLoader = skinLoader;
		this.configsLoader = configsLoader;
	}
	
	protected Entity loadEntity(MyFile entityFile){
		Model model = modelLoader.loadModel(new MyFile(entityFile, LoaderSettings.MODEL_FILE));
		Configs configs = configsLoader.loadConfigs(new MyFile(entityFile, LoaderSettings.CONFIGS_FILE));
		Skin skin = loadSkin(entityFile, configs);
		Entity entity = new Entity(model, skin);
		setEntityConfigs(entity, configs);
		return entity;
	}
	
	private Skin loadSkin(MyFile entityFile, Configs configs){
		Skin skin = null;
		MyFile diffuseFile = new MyFile(entityFile, LoaderSettings.DIFFUSE_FILE);
		if(configs.hasExtraMap()){
			skin = skinLoader.loadSkin(diffuseFile, new MyFile(entityFile, LoaderSettings.EXTRA_MAP_FILE));
		}else{
			skin = skinLoader.loadSkin(diffuseFile);
		}
		skin.setTransparent(configs.hasTransparency());
		return skin;
	}
	
	private void setEntityConfigs(Entity entity, Configs configs){
		entity.setCastsShadow(configs.castsShadow());
		entity.setHasReflection(configs.hasReflection());
		entity.setSeenUnderWater(configs.hasRefraction());
		entity.setImportant(configs.isImportant());
	}

}
