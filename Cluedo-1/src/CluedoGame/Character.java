package CluedoGame;

public enum Character implements Card {
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

	public String getShortName() {
		return getName().split(" ")[1];
	}
	public String getName(){
		return name;
	}
	
	@Override
	public String toString(){
		return getName();
	}
	
	@Override
	public boolean isWeapon(){
		return false;
	}

	@Override
	public boolean isCharacter() {
		return true;
	}

	@Override
	public boolean isRoom() {
		return false;
	}
}