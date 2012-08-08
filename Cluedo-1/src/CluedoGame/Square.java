package CluedoGame;

import java.awt.Point;

import CluedoGame.Room;

/**
 * This interface represents a square on the Board.  It can either be a corridor, a room, or a room with a secret passage.
 * Corridor squares correspond to a Point (col, row) which is a position on the board  starting from top Left.  All squares correspond to 
 * a 'Room' (this is the 'Corridor').
 * 
 * @author Izzi
 *
 */
public interface Square {

	/**
	 * Whether the Square is a corridor square or not.
	 */
	public boolean isCorridor();
	
	/**
	 * Whether the Square is a room square or not.
	 */
	public boolean isRoom();
	
	/**
	 * Whether the Square is a corner room (with a secret passage) or not.
	 */
	public boolean isCornerRoom();
	
	/**
	 * Whether the square is an intrigue square or not.
	 */
	public boolean isIntrigueSquare();
	
	/**
	 * The position of the Square on the Board (col, row).  
	 * @return - NOTE: haven't decided what this should return if its a Room.
	 */
	public Point getPosition();
	
	/**
	 * The 'Room' that the square corresponds to.
	 * @return - returns 'Corridor' if the room isn't technically a room.
	 */
	public Room getRoom();
	
}
