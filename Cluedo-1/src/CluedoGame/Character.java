package CluedoGame;

public enum Character {
	DianeWhite("Diane White"),
	EleanorPeacock("Eleanor Peacock"),
	JackMustard("Jack Mustard"),
	JacobGreen("Jacob Green"),
	KasandraScarlett("Kasandra Scarlett"),
	VictorPlum("Victor Plum");

	private final String name;

	Character(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
	
	@Override
	public String toString(){
		return getName();
	}
}





