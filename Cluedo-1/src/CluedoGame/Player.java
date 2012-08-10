package CluedoGame;

import java.util.ArrayList;
import java.util.List;



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
	
	public Room getRoom(){
		return this.position.getRoom();
	}
	
	public Character getCharacter() {
		return this.character;
	}

	public void setPosition(Square position) {
		this.position = position;
	}
	
	public Square getPosition(){
		return this.position;
	}

	public List<Card> getCards() {
		//return a clone so that they don't have a reference to our data
		return new ArrayList<Card>(allCards);
	}
	
	public boolean hasCard(Card c) {
		return allCards.contains(c);
	}
	

	//===================================================
	// Methods to describe the player's position
	//===================================================
	public boolean inCornerRoom(){
		return this.position.isCornerRoom();
	}
	
	public boolean inRoom(){
		return this.position.isRoom();
	}
	
	public boolean inMurderRoom(){
		return this.position.isRoom() && !this.position.isFinalRoom();
	}
	
	public boolean inFinalRoom(){
		return this.position.isFinalRoom();
	}
	
	public boolean onIntrigueSquare(){
		return this.position.isIntrigueSquare();
	}
	
	

	
	/**
	 * Two players are equal if their characters are equal.
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
