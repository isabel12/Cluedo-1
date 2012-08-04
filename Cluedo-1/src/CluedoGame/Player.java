package CluedoGame;

import java.awt.Point;
import java.util.Collections;
import java.util.List;

import CluedoGame.Board.Cell;

public class Player {

	//their piece on the board
	private Character character;
	
	//cards the player has 
	private List<Character> characterCards;
	private List<Room> roomCards;
	private List<Weapon> weaponCards;
	
	//location on the board maybe
	private Cell position;    // <-------- I've changed this to Cell for now.  
	
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

	/**
	 * IZZI
	 * We might want to change this later to be private...?  Right now, the Board calls this method.
	 * @param position
	 */
	public void setPosition(Cell position) {
		this.position = position;
	}
	
	public Cell getPosition(){
		return this.position;
	}
	
	
}
