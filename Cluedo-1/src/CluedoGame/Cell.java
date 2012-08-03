package CluedoGame;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The Cell class represents a square on the board.  It can either be a Room, an entrance to a room, a corridor square, or a questionMark square.
 * 
 * @author Izzi
 *
 */
public class Cell {
	Type type;  //<--- not sure how to implement this yet.  Inheritance? Enum?
	Map<Point, Cell> neighbours;
	boolean isEmpty;
	Point position;

	public Cell(Type type, Point poisition) {
		this.type = type;
		this.position = position;
		
		neighbours = new HashMap<Point, Cell>();
	}
	
	
	public void addNeighbour(Cell cell) {
		neighbours.put(cell.getPosition(), cell);
	}
	
	public Point getPosition() {
		return position;
	}
}
