package CluedoGame.Board;

import java.awt.Point;
import CluedoGame.Room;

/**
 * The main function of a CorridorCell is to offer pathFinding functionality to the Cell class.  
 * 
 * @author Izzi
 *
 */
public class CorridorCell extends Cell {
	
	private boolean isBlocked;
	private boolean isIntrigue;
	
	// fields for the pathFinding function
	CorridorCell pathFrom;
	
	
	public CorridorCell(Point position, boolean intrigue) {
		super(position);
		this.isIntrigue = intrigue;
		this.isBlocked = false;
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
	public boolean isBlocked(){
		return this.isBlocked;
	}
	
	
	//====================================================================
	// Required for Square Interface
	//====================================================================

	/**
	 * Sets the 'isEmpty' status of the cell.
	 * @param isEmpty
	 */
	@Override
	public void setBlocked(boolean isEmpty){
		this.isBlocked = isEmpty;
	}
	
	/**
	 * Returns 'Corridor'.
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
