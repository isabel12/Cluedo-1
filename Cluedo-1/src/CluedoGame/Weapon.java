package CluedoGame;

/**
 * Class represents a weapon in the cludeo game.
 * Contains a Type enum which can be used to specify the type this weapon is.
 * 
 * @author Troy Shaw
 *
 */
public class Weapon {

	public enum Type {
		Axe,
		BaseballBat,
		Candlestick,
		Dumbbell,
		Knife,
		Pistol,
		Poison,
		Rope,
		Trophy,
	}
	
	private Type type;
	
	/**
	 * Creates a Weapon object with the given type.
	 * @param type
	 */
	public Weapon(Type type) {
		this.type = type;
	}
	
	/**
	 * Gets the type of weapon.
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
}
