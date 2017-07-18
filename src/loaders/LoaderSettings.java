package loaders;

import utils.MyFile;

public class LoaderSettings {
	
	public static final MyFile RES_FOLDER = new MyFile("res");
	
	protected static final String ENTITIES_FOLDER = "entities";
	protected static final String SKYBOX_FOLDER = "skybox";
	protected static final String[] SKYBOX_TEX_FILES = {"posX.png", "negX.png", "posY.png", "negY.png", "posZ.png", "negZ.png"};
	protected static final float SKYBOX_SIZE = 100;
	protected static final String ENTITY_LIST_FILE = "entityList.txt";
	protected static final String CONFIGS_FILE = "configs.txt";
	protected static final String MODEL_FILE = "model.obj";
	protected static final String DIFFUSE_FILE = "diffuse.png";
	protected static final String EXTRA_MAP_FILE = "extra.png";
	
	protected static final String SEPARATOR = ";";
	protected static final String TRUE = "TRUE";
	
}
