package CluedoGame;


/**
 * The purpose of this interface is to tie together Character, Weapon, and Room enums so they can be stored together.
 * @author Izzi
 *
 */
public interface Card {
	/**
	 * Returns true if this card is a weapon.
	 * @return
	 */
	public boolean isWeapon();
	
	/**
	 * Returns true if this card is a character.
	 * @return
	 */
	public boolean isCharacter();
	
	/**
	 * Returns true if this card is a room.
	 * @return
	 */
	public boolean isRoom();
}
