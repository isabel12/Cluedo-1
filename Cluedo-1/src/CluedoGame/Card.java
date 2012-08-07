package CluedoGame;


/**
 * The purpose of this interface is to tie together Character, Weapon, and Room enums so they can be stored together.
 * @author Izzi
 *
 */
public interface Card {
	public boolean isWeapon();
	public boolean isCharacter();
	public boolean isRoom();
}
