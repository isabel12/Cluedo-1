package CluedoGame.Board;

import java.awt.Point;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import CluedoGame.Square;

/**
 * The Cell class represents a square on the board that can be occupied by a Player's piece. 
 * It is primarily manipulated by the Board class.  The main functionality is that it has a 
 * set of neighbours to which it can travel to, allowing the board to be represented by a graph 
 * of Cells.
 * 
 * It can either be a RoomCell, or a CorridorCell (which could be an entrance to a room, a corridor 
 * square, or an intrigue square).
 * 
 * @author Izzi
 */
public abstract class Cell implements Square {
	protected Set<Cell> neighbours;
	protected Point position;

	/**
	 * Super constructor - this initiates the set of neighbours
	 * @param - point (col, row)
	 */
	public Cell(Point position) {
		neighbours = new HashSet<Cell>();
		this.position = position;
	}

	/**
	 * Adds the given cell as a neighbour.  It won't add itself or null as a neighbour.
	 * @param the cell to be added as the current Cell's neighbour.
	 * @throws IllegalArgumentException if neighbour is null, or itself.
	 */
	public void connectTo(Cell neighbour) {
		// check the parameters
		if (neighbour == this)
			throw new IllegalArgumentException("A cell cannot be it's own neighbour.");
		if (neighbour == null)
			throw new IllegalArgumentException("A cell cannot have null as a neighbour.");

		neighbours.add(neighbour);
	}


	/**
	 * Returns an unmodifiable version of the cell's neighbour cells.
	 */
	public Set<Cell> getNeighbours(){return Collections.unmodifiableSet(neighbours);}


	/**
	 * Returns the position of the Cell on the Board as a Point (col, row).
	 */
	public Point getPosition(){
		return this.position;
	}

	/**
	 * Sets the Cell as blocked.  This stops it being part of a path, or letting another 
	 * player move to it.
	 * @param isBlocked - whether the cell should be blocked or unblocked
	 */
	public abstract void setBlocked(boolean isBlocked);  	// <--- this method is used when moving the player, so needs to be accessible from Cell.



	/**
	 * Whether the cell is blocked or not.  If blocked, his stops it being part of a path, 
	 * or letting another player move to it.
	 */
	public abstract boolean isBlocked();


}
