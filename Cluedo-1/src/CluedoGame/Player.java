package CluedoGame;

import java.awt.Point;
import java.util.Collections;
import java.util.List;

import CluedoGame.Board.Cell;
import CluedoGame.Board.CorridorCell;
import CluedoGame.Board.RoomCell;


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

	/**
	 * IZZI
	 * We might want to change this later to be private...?  Right now, the Board calls this method.
	 * 
	 * I think the CluedoGame should call this method actually.
	 * @param position
	 */
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
