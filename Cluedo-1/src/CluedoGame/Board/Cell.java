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

/**
 * The Cell class represents a square on the board. Currently it is indexed by a set of positions (Rooms will have a set of them).
 * At the moment, it can either be a RoomCell, or a CorridorCell (entrance to a room, a corridor square, or a questionMark square).
 * 
 * @author Izzi
 */
public abstract class Cell {
	protected Map<Point,Cell> neighbours;
	List<Point> position;

	/**
	 * Super constructor - this initiates the set of neighbours
	 */
	public Cell() {
		neighbours = new HashMap<Point,Cell>();
		position = new ArrayList<Point>();
	}
	
	
	/**
	 * Adds the given cell as a neighbour.  It won't add itself or null as a neighbour.
	 * @param cell
	 */
	public void connectTo(Cell neighbour) {
		if (neighbour == this || neighbour == null){
			return;
		}
		
		for (Point pos: neighbour.getPositions()){
			neighbours.put(pos,neighbour);
		}
	}

	
	/**
	 * Returns the neighbour at the given position.  
	 * The method currently returns null if the cell doesn't have a neighbour at the given point (Let me know if this needs to change!)
	 * 
	 * @param pos - the position of the neighbour
	 * @return - the neighbour at the given point (null if not a neighbour)
	 */
	public Cell getNeighbour(Point pos){
		if (neighbours.containsKey(pos)){
			return neighbours.get(pos);
		}
		else {
			return null;
		}
	}
	
	public Set<Cell> getNeighbours(){
		Collection<Cell> coll = neighbours.values();
		Set<Cell> neighs = new HashSet<Cell>();
		neighs.addAll(coll);
		return neighs;
	}
	
	/**
	 * Thinking I should make this abstract, so CorridorCell can't have more than one.  We'll see
	 * @param position
	 */
	public abstract void addPosition(Point position);
	
	public abstract void setEmpty(boolean isEmpty);
	
	public abstract Point getPosition();
	
	
	
	/**
	 * Returns a set of the positions relating to the cell.
	 * @return
	 */
	public Set<Point> getPositions(){
		Set<Point> positions = new HashSet<Point>();
		position.addAll(this.position);
		return positions;
	}
	

	
	
	

	
}
