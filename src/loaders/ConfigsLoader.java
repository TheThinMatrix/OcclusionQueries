package loaders;

import java.io.BufferedReader;

import utils.MyFile;

public class ConfigsLoader {
	
	public Configs loadConfigs(MyFile configsFile){
		BufferedReader reader = null;
		Configs configs = new Configs();
		try {
			reader = configsFile.getReader();
			createConfigs(reader, configs);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Couldn't load configs file: " + configsFile);
		}
		return configs;
	}
	
	private void createConfigs(BufferedReader reader, Configs configs) throws Exception{
		configs.setExtraMap(readNextBoolean(reader));
		configs.setTransparency(readNextBoolean(reader));
		configs.setReflection(readNextBoolean(reader));
		configs.setRefraction(readNextBoolean(reader));
		configs.setCastsShadow(readNextBoolean(reader));
		configs.setImportant(readNextBoolean(reader));
	}
	
	private boolean readNextBoolean(BufferedReader reader) throws Exception{
		String line = reader.readLine();
		String bool = line.split(LoaderSettings.SEPARATOR)[1];
		return bool.equals(LoaderSettings.TRUE);
	}

}
