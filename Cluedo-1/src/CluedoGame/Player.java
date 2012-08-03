package CluedoGame;

import java.util.Collections;
import java.util.List;

public class Player {

	//their piece on the board
	private Character character;
	
	//cards the player has 
	private List<Character> characterCards;
	private List<Room> roomCards;
	private List<Weapon> weaponCards;
	
	//location on the board maybe
	int x, y;
	
	//more data
	
	
	public Player(Character character) {
		this.character = character;
	}
}
