package CluedoGame.Board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import CluedoGame.Room;
import CluedoGame.Square;

/**
 * The Cell class represents a square on the board that can be occupied by a Player's piece. 
 * It is primarily manipulated by the Board class.  The main functionality is that it has a set of neighbours to which 
 * it can travel to, allowing the board to be represented by a graph of Cells.
 * 
 * It can either be a RoomCell, or a CorridorCell (which could be an entrance to a room, a corridor square, or a questionMark square).
 * 
 * @author Izzi
 */
public abstract class Cell implements Square {
	protected Set<Cell> neighbours;

	/**
	 * Super constructor - this initiates the set of neighbours
	 */
	public Cell() {
		neighbours = new HashSet<Cell>();
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
	public Set<Cell> getNeighbours(){
		return Collections.unmodifiableSet(neighbours);
	}
	
	public abstract Point getPosition();
	
	public abstract Room getRoom();
	
	public abstract void setPosition(Point position);
	
	public abstract void setEmpty(boolean isEmpty);  	// <--- this method is used when moving the player, so needs to be accessible from Cell.
														// I don't think we need a getEmpty() method here.  We only use it for pathfinding, and that only uses CorridorCells.
	
	
	
}
