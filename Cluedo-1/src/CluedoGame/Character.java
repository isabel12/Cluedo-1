package CluedoGame;

/**
 * Enum represents a Character in the Cluedo game.
 * @author Troy Shaw
 *
 */
public enum Character implements Card {
	Scarlett("Kasandra Scarlett"),
	Mustard("Jack Mustard"),
	White("Diane White"),
	Green("Jacob Green"),
	Peacock("Eleanor Peacock"),
	Plum("Victor Plum");

	private final String name;

	/**
	 * Creates a character with the given name.
	 * @param name
	 */
	private Character(String name){
		this.name = name;
	}

	/**
	 * Gets the last name of this character.
	 * @return
	 */
	public String getShortName() {
		return getName().split(" ")[1];
	}
	
	/**
	 * Returns the name of this character.
	 * @return
	 */
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