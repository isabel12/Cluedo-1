package CluedoGame;

import java.util.List;
import java.util.Map;


/**
 * This Interface describes the methods that a CluedoPlayer needs to implement in order
 * for the game to function - namely to provide information about itself, its cards, 
 * position etc.
 * 
 * It is meant to be used for CluedoGame, and CMDGame.
 * 
 * @author Izzi
 */
public interface CluedoPlayer {

	/**
	 * Returns the room this player is currently in.
	 * @return
	 */
	public Room getRoom();
	
	/**
	 * Returns this players character.
	 * @return
	 */
	public Character getCharacter();


	/**
	 * Gets this players position.
	 */
	public Square getPosition();

	/**
	 * Returns the list of Card this player has.
	 */
	public List<Card> getCards();
	
	/**
	 * Returns true if this player has the given card in their hand.
	 * @param card - the card to check for
	 */
	public boolean hasCard(Card card);
	
	/**
	 * Returns the notepad of Card -> Boolean.
	 * True means the item represented by the card definitely did not commit a murder
	 */
	public Map<Card, Boolean> getNotepad();
	

	//===================================================
	// Methods to describe the player's position
	//===================================================
	
	/**
	 * Returns true if the player is in a room that has a secret passage.
	 */
	public boolean inCornerRoom();
	
	
	/**
	 * Returns true if this player is in any room.
	 */
	public boolean inRoom();
	
	/**
	 * Returns true if this player is in any room a murder can happen in.
	 */
	public boolean inMurderRoom();
	
	/**
	 * Returns true if this player is in the accusation room (the pool-room).
	 */
	public boolean inFinalRoom();
	
	/**
	 * Returns true if this player is on an intrigue square.
	 */
	public boolean onIntrigueSquare();
}
