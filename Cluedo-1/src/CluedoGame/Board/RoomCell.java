package CluedoGame.Board;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import CluedoGame.*;


public class RoomCell extends Cell {
	Room room;
	RoomCell secretPassage;
	List<CorridorCell> entrances; 

	public RoomCell(Room room) {
		super();
		this.room = room;	
		this.entrances = new ArrayList<CorridorCell>();
	}
	
	/**
	 * Returns the room this cell corresponds to.
	 * @return
	 */
	public Room getRoom(){
		return this.room;
	}
	
	/**
	 * Sets the given room to be connected via a secret passage
	 * @param room
	 */
	public void setSecretPassage(RoomCell room){
		this.secretPassage = room;
	}
	
	/**
	 * Returns true if the RoomCell is connected to another RoomCell via a secret passage
	 * @return
	 */
	public boolean hasSecretPassage(){
		return this.secretPassage != null;
	}

	/**
	 * Returns the RoomCell at the end of the secret passage 
	 * @return
	 * @throws - UnsupportedOperationException if the room doesn't have a secret passage <--- this can be changed, but thought it might help with debugging.
	 */
	public Cell getSecretPassageDestination(){
		if (this.secretPassage==null){
			throw new UnsupportedOperationException(room + "doesn't have a secret passage.");
		}
		
		return this.secretPassage;
	}
	

	public void addEntrance(CorridorCell entrance){
		this.entrances.add(entrance);
	}
	
	public List<CorridorCell> getEntrances(){
		return Collections.unmodifiableList(entrances);
	}
	
	
	/**
	 * This method adds the entrance to its set of neighbours, and also to its List of entrances.
	 */
	@Override
	public void connectTo(Cell entrance){
		if (entrance == this || entrance == null){
			return;
		}
		// add it as a neighbour
		Point pos = entrance.getPosition();
		neighbours.put(pos,entrance);
		
		// also add it as an entrance
		if (entrance instanceof CorridorCell){
			entrances.add((CorridorCell)entrance);
		}
	}
	
	
	@Override
	public void addPosition(Point position) {
		if (position != null){
			this.position.add(position);
		}
	}
	
	/**
	 * This method is unsupported for RoomCell.
	 * @throws UnsupportedOperationException.
	 */
	@Override
	public void setEmpty(boolean isEmpty) {
		throw new UnsupportedOperationException("You can't set a RoomCell to be empty/not empty.");
	}
	
	/**
	 * This may need changing!!!  Returns the first position in its set of positions.
	 * Kind of unsupported.
	 */
	@Override
	public Point getPosition() {
		return position.get(0);
	}
	
	
	/**
	 * I've set this up for debugging purposes.  It can be changed later if necessary.
	 */
	@Override
	public String toString(){
		return this.room.toString();
	}



	

	
}
