package CluedoGame;

import java.util.ArrayList;
import java.util.List;


/**
 * Class represents a player in the Cluedo game.
 * @author Troy Shaw
 *
 */
public class Player {

	//their piece on the board
	private Character character;
	
	//cards the player has 
	private List<Character> characterCards;
	private List<Weapon> weaponCards;
	private List<Room> roomCards;
	
	//cards as a big list
	private List<Card> allCards;
	
	//location on the board maybe
	private Square position;     
	
	//more data
	
	/**
	 * Contructs a player with the given character and cards.
	 * 
	 * @param character
	 * @param characterCards
	 * @param weaponCards
	 * @param roomCards
	 */
	public Player(Character character, 
			List<Character> characterCards, List<Weapon> weaponCards, List<Room> roomCards) {
		
		this.character = character;	
		this.characterCards = characterCards;
		this.weaponCards = weaponCards;
		this.roomCards = roomCards;
		
		//generate list for the list of all cards
		allCards = new ArrayList<Card>();
		
		allCards.addAll(characterCards);
		allCards.addAll(weaponCards);
		allCards.addAll(roomCards);
	}


	//=====================================================
	// Cards, Character, Position
	//=====================================================
	
	/**
	 * Returns the room this player is currently in.
	 * @return
	 */
	public Room getRoom(){
		return this.position.getRoom();
	}
	
	/**
	 * Returns this players character.
	 * @return
	 */
	public Character getCharacter() {
		return this.character;
	}

	/**
	 * sets this players position.
	 * @param position
	 */
	public void setPosition(Square position) {
		this.position = position;
	}
	
	/**
	 * Gets this players position.
	 * @return
	 */
	public Square getPosition(){
		return this.position;
	}

	/**
	 * Returns the list of Card this player has.
	 * @return
	 */
	public List<Card> getCards() {
		//return a clone so that they don't have a reference to our data
		return new ArrayList<Card>(allCards);
	}
	
	/**
	 * Returns true if this player has the given card in their hand.
	 * @param c
	 * @return
	 */
	public boolean hasCard(Card c) {
		return allCards.contains(c);
	}
	

	//===================================================
	// Methods to describe the player's position
	//===================================================
	public boolean inCornerRoom(){
		return this.position.isCornerRoom();
	}
	/**
	 * Returns true if this player is in any room.
	 * @return
	 */
	public boolean inRoom(){
		return this.position.isRoom();
	}
	
	/**
	 * Returns true if this player is in any room a murder can happen in.
	 * @return
	 */
	public boolean inMurderRoom(){
		return this.position.isRoom() && !this.position.isFinalRoom();
	}
	
	/**
	 * Returns true if this player is in the accusation room (the pool-room).
	 * @return
	 */
	public boolean inFinalRoom(){
		return this.position.isFinalRoom();
	}
	
	/**
	 * Returns true if this player is on an intrigue square.
	 * @return
	 */
	public boolean onIntrigueSquare(){
		return this.position.isIntrigueSquare();
	}
	
	

	
	/**
	 * Returns true if the characters representing these players on the board
	 * are equal.
	 */
	@Override
	public boolean equals(Object o){
		if (o instanceof Player){
			Player other = (Player) o;
			return this.getCharacter().equals(other.getCharacter());
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return this.character.hashCode();
	}
	
	public String toString() {
		return character.getName();
	}
}
