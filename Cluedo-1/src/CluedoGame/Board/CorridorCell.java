package CluedoGame.Board;

import java.awt.Point;
import java.util.Set;

import CluedoGame.Room;

/**
 * The main function of a CorridorCell is to offer pathFinding functionality to the Cell class.  
 * 
 * @author Izzi
 *
 */
public class CorridorCell extends Cell {
	
	private boolean isEmpty;
	private boolean isIntrigue;
	private Point position;
	
	// fields for the pathFinding function
	CorridorCell pathFrom;
	
	
	public CorridorCell(boolean intrigue) {
		super();
		this.isIntrigue = intrigue;
		this.isEmpty = true;
	}
	

	
	//===================================================================
	// Required for path finding
	//===================================================================
	
	public void resetPath(){
		this.pathFrom = null;
	}
	
	
	public void setPathFrom(CorridorCell from){
		this.pathFrom = from;
	}
	
	public CorridorCell getPathFrom(){
		return this.pathFrom;
	}
	
	/**
	 * Returns whether or not the cell is currently occupied by a character.
	 * @return
	 */
	public boolean isEmpty(){
		return this.isEmpty;
	}
	
	
	//====================================================================
	// Required for Square Interface
	//====================================================================

	/**
	 * Sets the 'isEmpty' status of the cell.
	 * @param isEmpty
	 */
	@Override
	public void setEmpty(boolean isEmpty){
		this.isEmpty = isEmpty;
	}
	
	/**
	 * Returns the position
	 * @return
	 */
	public Point getPosition(){
		return this.position;
	}
	
	/**
	 * Returns 'Corridor'.
	 */
	@Override
	public Room getRoom() {
		return Room.Corridor;
	}

	/**
	 * A corridor cell can only relate to one Point.  If the Cell already has a position, then it will be replaced.
	 * Nothing will happen if it is given null.
	 */
	@Override
	public void setPosition(Point position) {
			this.position = position;	
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


	
	
	

	/**
	 * I've set this up for debugging purposes.  It can be changed later if necessary.
	 */
	@Override
	public String toString(){
		String string = "(" + this.getPosition().x + "," + this.getPosition().y + ")";
		if (isIntrigue){
			string += " ?";
		}
		
		return string;
	}

}
