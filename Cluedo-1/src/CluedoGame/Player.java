package CluedoGame;

import java.util.List;



public class Player {

	//their piece on the board
	private Character character;
	
	//cards the player has 
	private List<Character> characterCards;
	private List<Room> roomCards;
	private List<Weapon> weaponCards;
	
	//location on the board maybe
	private Square position;     
	
	//more data
	
	
	public Player(Character character) {
		this.character = character;
	}

	/**
	 * Returns the Character the Player is playing as.
	 * @return
	 */
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
	
	
	public boolean onIntrigueSquare(){
		return this.position.isIntrigueSquare();
	}
	
}
