package CluedoGame.Board;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import CluedoGame.*;


/**
 * This class represents a Cell that is a room.  It doesn't correspond to a position on the board, and may be connected to another room via a secret passage.
 * 
 * @author Izzi
 *
 */
public class RoomCell extends Cell {
	Room room;
	RoomCell secretPassage;
	List<CorridorCell> entrances;
	Point midPoint;

	public RoomCell(Room room, Point position) {
		super(position);
		this.room = room;	
		this.entrances = new ArrayList<CorridorCell>();
	}
	
	
	//===============================================================
	// Required for secret passage functionality
	//===============================================================
	
	/**
	 * Sets the given room to be connected via a secret passage
	 * @param room
	 */
	public void setSecretPassage(RoomCell room){
		this.secretPassage = room;
	}
	
	/**
	 * Returns the Cell at the end of the secret passage 
	 * @return
	 * @throws - UnsupportedOperationException if the room doesn't have a secret passage <--- this can be changed, but thought it might help with debugging.
	 */
	public Cell getSecretPassageDest(){
		if (this.secretPassage==null){
			throw new UnsupportedOperationException(room + "doesn't have a secret passage.");
		}
		return this.secretPassage;
	}
	
	
	/**
	 * This method is needed for finding paths.  If the start and destination are rooms with more than one entrance, 
	 * then need to check all combinations, so accessing via index is necessary.
	 * @return
	 */
	public List<CorridorCell> getEntrances(){
		return this.entrances;	
	}
	
	
	/**
	 * Adds the given cell as a neighbour.  It won't add itself or null as a neighbour.
	 * This method also adds the Cell to the room's list of entrances (providing it is a CorridorCell).
	 * 
	 * @param the cell to be added as the current Cell's neighbour.
	 * @throws IllegalArgumentException if neighbour is null, or itself.
	 */
	@Override
	public void connectTo(Cell neighbour) {
		// check the parameters
		if (neighbour == this)
			throw new IllegalArgumentException("A cell cannot be it's own neighbour.");
		if (neighbour == null)
			throw new IllegalArgumentException("A cell cannot have null as a neighbour.");
		
		// add to set of neighbours
		neighbours.add(neighbour);
		
		// add to set of entrances
		if (neighbour instanceof CorridorCell){
			entrances.add((CorridorCell) neighbour);
		}
	}
	
	//===============================================================
	// Required for Square interface
	//===============================================================
	
	/**
	 * Returns the room this cell corresponds to.
	 * @return
	 */
	public Room getRoom(){
		return this.room;
	}
	
	/**
	 * This method does nothing - currently the game doesn't care if a room is empty or not.
	 */
	@Override
	public void setBlocked(boolean isEmpty) {
		return;
	}
	
	
	/**
	 * Always returns true.
	 */
	@Override
	public boolean isRoom() {
		return true;
	}
	
	/**
	 * Returns true if the RoomCell is connected to another RoomCell via a secret passage
	 * @return
	 */
	public boolean isCornerRoom(){
		return this.secretPassage != null;
	}
	
	/**
	 * Always returns false.
	 */
	@Override
	public boolean isCorridor() {
		return false;
	}
	
	/**
	 * Always returns false.
	 */
	@Override
	public boolean isIntrigueSquare() {
		return false;
	}
	
	/**
	 * Always returns false.
	 */
	@Override
	public boolean isBlocked() {
		return false;
	}

	/**
	 * I've set this up for debugging purposes.  It can be changed later if necessary.
	 */
	@Override
	public String toString(){
		return this.room.toString();
	}

}
