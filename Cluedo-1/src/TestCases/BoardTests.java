package TestCases;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import CluedoGame.*;
import CluedoGame.Character;
import CluedoGame.Board.Board;

public class BoardTests {

	Board board;
	Set<Player> players = new HashSet<Player>();
	
	
	/**
	 * Makes a set of players to be used constructing a new board.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		List<Weapon> weapon = new ArrayList<Weapon>();
		List<Room> room = new ArrayList<Room>();
		List<Character> chara = new ArrayList<Character>();

		Player p1 = new Player(Character.Scarlett, chara, weapon, room);
		Player p2 = new Player(Character.Green, chara, weapon, room);
		Player p3 = new Player(Character.Peacock, chara, weapon, room);
		players.add(p1);
		players.add(p2);
		players.add(p3);
	}

	@Test
	public void movementTests() {
		// test moving 
		
		
		
		fail("Not yet implemented");
	}
	
	
	// every corridor square should be connected to at least on other.
	// at most 4 others
	// if neighbour is CorridorCell, position differs by x or y, and only by 1
	@Test
	public void corridorConnectionTests(){
		getNewBoard();
		
		for (int row = 0; row <  )
		
		
		
		
		
	}

	
	/**
	 * Gets a newly set up version of the board.
	 * @return
	 */
	public void getNewBoard(){
		board = new Board(players);
	}
	
	
}
