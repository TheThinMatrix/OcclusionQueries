package loaders;

public class Configs {
	
	private boolean hasExtraMap = false;
	private boolean hasTransparency = false;
	private boolean hasReflection = false;
	private boolean hasRefraction = false;
	private boolean castsShadow = false;
	private boolean important = false;
	
	protected void setExtraMap(boolean hasExtraMap){
		this.hasExtraMap = hasExtraMap;
	}
	
	protected void setTransparency(boolean transparent){
		this.hasTransparency = transparent;
	}
	
	protected void setReflection(boolean hasReflection){
		this.hasReflection = hasReflection;
	}
	
	protected void setRefraction(boolean hasRefraction){
		this.hasRefraction = hasRefraction;
	}
	
	protected void setCastsShadow(boolean shadow){
		this.castsShadow = shadow;
	}
	
	protected void setImportant(boolean important){
		this.important = important;
	}

	protected boolean hasExtraMap() {
		return hasExtraMap;
	}

	protected boolean hasTransparency() {
		return hasTransparency;
	}
	
	protected boolean isImportant() {
		return important;
	}

	protected boolean hasReflection() {
		return hasReflection;
	}

	protected boolean hasRefraction() {
		return hasRefraction;
	}

	protected boolean castsShadow() {
		return castsShadow;
	} 

}
