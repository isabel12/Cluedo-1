package CluedoGame.Board;
import java.awt.Point;
import java.util.Collections;
import java.util.Set;

import CluedoGame.*;


public class RoomCell extends Cell {
	Room room;
	RoomCell secretPassage;

	public RoomCell(Room room) {
		super();
		this.room = room;	
	}
	
	/**
	 * Returns the room this cell corresponds to.
	 * @return
	 */
	Room getRoom(){
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
	
	@Override
	public void addPosition(Point position) {
		if (position != null){
			this.position.add(position);
		}
	}
	
	/**
	 * I've set this up for debugging purposes.  It can be changed later if necessary.
	 */
	@Override
	public String toString(){
		return this.room.toString();
	}
	
}
