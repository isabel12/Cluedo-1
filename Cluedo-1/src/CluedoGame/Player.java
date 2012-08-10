package CluedoGame;

import java.util.ArrayList;
import java.util.List;



public class Player {

	//their piece on the board
	private Character character;

	//the players cards 
	private List<Card> allCards;

	//location on the board maybe
	private Square position;

	public Player(Character character, 
			List<Character> characterCards, List<Weapon> weaponCards, List<Room> roomCards) {		
		this.character = character;

		//generate list for the list of all cards
		allCards = new ArrayList<Card>();

		allCards.addAll(characterCards);
		allCards.addAll(weaponCards);
		allCards.addAll(roomCards);
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

	public boolean inCornerRoom(){
		return this.position.isCornerRoom();
	}

	public boolean inRoom(){
		return this.position.isRoom();
	}

	public boolean onIntrigueSquare(){
		return this.position.isIntrigueSquare();
	}

	public List<Card> getCards() {
		//return a clone so that they don't have a reference to our data
		return new ArrayList<Card>(allCards);
	}

	public boolean hasCard(Card c) {
		return allCards.contains(c);
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
