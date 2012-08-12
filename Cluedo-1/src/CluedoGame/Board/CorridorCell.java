package CluedoGame.Board;

import java.awt.Point;
import CluedoGame.Room;

/**
 * The main function of a CorridorCell is to offer pathFinding functionality to
 * the Cell class. It remembers the previous Cell in the path, which can be set
 * or cleared as requested.
 * 
 * A corridor cell can be an intrigue square or a normal corridor square.
 * 
 * @author Izzi
 * 
 */
public class CorridorCell extends Cell {

	private boolean isBlocked;
	private boolean isIntrigue;

	// fields for the pathFinding function
	CorridorCell pathFrom;

	
	/**
	 * Constructs a new Corridor Cell.
	 * 
	 * @param position - point (col, row)
	 * @param intrigue - true if the Cell is an intrigue square
	 */
	public CorridorCell(Point position, boolean intrigue) {
		super(position);
		this.isIntrigue = intrigue;
		this.isBlocked = false;
	}

	// ===================================================================
	// Required for path finding
	// ===================================================================

	/**
	 * Clears the path saved if there was one.
	 */
	public void resetPath() {
		this.pathFrom = null;
	}

	/**
	 * Saves a path from this Cell, to the previous Cell in a path.
	 * 
	 * @param from
	 *            - the previous cell in the path.
	 */
	public void setPathFrom(CorridorCell from) {
		this.pathFrom = from;
	}

	/**
	 * Returns the previous Cell in the path saved.
	 * 
	 * @return - null if no path is saved.
	 */
	public CorridorCell getPathFrom() {
		return this.pathFrom;
	}

	/**
	 * Returns whether or not the cell is currently occupied by a character.
	 * 
	 * @return - true if the cell is occupied, false otherwise.
	 */
	public boolean isBlocked() {
		return this.isBlocked;
	}

	// ====================================================================
	// Required for Square Interface
	// ====================================================================

	/**
	 * Sets the 'isEmpty' status of the cell.
	 * 
	 * @param isEmpty
	 */
	@Override
	public void setBlocked(boolean isEmpty) {
		this.isBlocked = isEmpty;
	}

	/**
	 * Always returns 'Room.Corridor'.
	 */
	@Override
	public Room getRoom() {
		return Room.Corridor;
	}

	@Override
	public boolean isCorridor() {
		return true;
	}

	@Override
	public boolean isRoom() {
		return false;
	}

	@Override
	public boolean isCornerRoom() {
		return false;
	}

	@Override
	public boolean isIntrigueSquare() {
		return this.isIntrigue;
	}

	@Override
	public boolean isFinalRoom() {
		return false;
	}

	/**
	 * Prints the coordinates (col, row), with a ? following if it is an
	 * intrigue square.
	 */
	@Override
	public String toString() {
		String string = "(" + this.getPosition().x + "," + this.getPosition().y
				+ ")";
		if (isIntrigue) {
			string += " ?";
		}
		return string;
	}

}
