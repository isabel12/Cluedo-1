package CluedoGame.Board;

import java.awt.Point;
import java.util.Set;

public class CorridorCell extends Cell {
	
	private boolean isEmpty;
	private boolean isIntrigue;
	
	CorridorCell pathFrom;
	
	
	public CorridorCell(boolean intrigue) {
		super();
		this.isIntrigue = intrigue;
		this.isEmpty = true;
	}
	
	
	/**
	 * Returns whether or not the Cell corresponds to an intrigue square.
	 * @return
	 */
	public boolean isIntrigue(){
		return this.isIntrigue;
	}
	
	/**
	 * Returns whether or not the cell is currently occupied by a character.
	 * @return
	 */
	public boolean isEmpty(){
		return this.isEmpty;
	}
	
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
		for(Point p: this.position){
			return p;
		}
		return null;
	}


	/**
	 * A corridor cell can only relate to one Point.  If the Cell already has a position, then it will be replaced.
	 * Nothing will happen if it is given null.
	 */
	@Override
	public void addPosition(Point position) {
		if (position != null){
			if (!this.position.isEmpty()){
				this.position.clear();
			}
			this.position.add(position);	
		}
	}
	
	
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
