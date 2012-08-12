package CluedoGame.Board;

import CluedoGame.*;
import CluedoGame.Character;


/**
 * This interface describes a character that can be moved on the Board.
 * 
 * It has a Character that it represents, and its position can be changed to 
 * a new Square.
 * 
 * @author Izzi
 *
 */
public interface MoveableCharacter {
	
	/**
	 * Returns the Character the player is playing as
	 */
	public Character getCharacter();

	
	/**
	 * Moves the player to the new position given.
	 * @param newPosition
	 */
	public void setPosition(Square newPosition);
	
	
	/**
	 * Returns the Square the MoveableCharacter is currently in.
	 */
	public Square getPosition();

}
