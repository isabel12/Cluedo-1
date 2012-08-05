package CluedoGame;

public enum Character {
	
	Scarlett("Kasandra Scarlett"),
	Mustard("Jack Mustard"),
	White("Diane White"),
	Green("Jacob Green"),
	Peacock("Eleanor Peacock"),
	Plum("Victor Plum");

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