package scene;

public class Entity {
	
	private final Model model;
	private final Skin skin;
	
	private boolean castsShadow = true;
	private boolean hasReflection = true;
	private boolean seenUnderWater = false;
	private boolean isImportant = false;
	
	public Entity(Model model, Skin skin){
		this.model = model;
		this.skin = skin;
	}

	public Model getModel() {
		return model;
	}

	public Skin getSkin() {
		return skin;
	}
	
	public void delete(){
		model.delete();
		skin.delete();
	}

	public boolean isShadowCasting() {
		return castsShadow;
	}

	public void setCastsShadow(boolean shadow) {
		this.castsShadow = shadow;
	}
	
	public boolean isImportant(){
		return isImportant;
	}

	public boolean hasReflection() {
		return hasReflection;
	}

	public void setHasReflection(boolean reflects) {
		this.hasReflection = reflects;
	}
	
	public void setImportant(boolean isImportant) {
		this.isImportant = isImportant;
	}

	public boolean isSeenUnderWater() {
		return seenUnderWater;
	}

	public void setSeenUnderWater(boolean seenUnderWater) {
		this.seenUnderWater = seenUnderWater;
	}

}
