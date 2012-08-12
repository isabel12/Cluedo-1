package TestCases;


import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import CluedoGame.*;
import CluedoGame.Character;
import CluedoGame.Board.Board;
import CluedoGame.Board.Cell;
import CluedoGame.Board.CorridorCell;
import CluedoGame.Board.RoomCell;

public class BoardTests {

	Board board;
	Set<Player> players = new HashSet<Player>();
	Player p1;
	Player p2;
	Player p3;
	
	Square spa;
	Square spaEntrance;
	Square theatre;
	Square swimmingPool;
	Square patio;
	
	/**
	 * Makes a set of players to be used constructing a new board.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		List<Weapon> weapon = new ArrayList<Weapon>();
		List<Room> room = new ArrayList<Room>();
		List<Character> chara = new ArrayList<Character>();

		p1 = new Player(Character.Scarlett, chara, weapon, room);
		p2 = new Player(Character.Green, chara, weapon, room);
		p3 = new Player(Character.Peacock, chara, weapon, room);
		players.add(p1);
		players.add(p2);
		players.add(p3);
		
		getNewBoard();
	}

	/**
	 * Testing that the board doesn't let players be moved to illegal Squares.
	 */
	@Test
	public void movementTests() {
		getNewBoard();
		
		
		// test set playerPosition works
		//----------------------------------------------
		Square newPos = board.getSquare(new Point(5,6));
		board.setPlayerPosition(p1, newPos);
		assertTrue(p1.getPosition().equals(newPos));
		
		// try setting position to current position
		//-----------------------------------------
		try{
			board.setPlayerPosition(p1, newPos);
			fail("Should have thrown exception.");
		} catch (IllegalArgumentException e){}
		
		// test can't set player position to occupied square
		//--------------------------------------------------
		try{
			board.setPlayerPosition(p2, newPos);
			fail("Should have thrown exception.");
		} catch (IllegalArgumentException e){}
		
		
		// test use secret passage
		//-------------------------
		
		// test it works when you can
		board.setPlayerPosition(p1, spa);
		board.useSecretPassage(p1);
		
		// test it doesn't when you can't
		board.setPlayerPosition(p1, theatre);
		try{
			board.useSecretPassage(p1);
		} catch (IllegalArgumentException e){}
		
		
	}
	
	
	/**
	 * Tests that path finding between squares works.
	 */
	@Test
	public void testPathFinding(){
		getNewBoard();
		
		// test path to room
		List<Square> path = board.getBestPathTo(p3, spa);
		if (path.size() != 9){
			fail("path size should be 9: " + path.size());
		}
		
		
		// test path is empty if blocked
		board.setPlayerPosition(p1, spaEntrance);
		path = board.getBestPathTo(p2, spa);
		if (path.size() != 0){
			fail("path size should be zero: " + path.size());
		}
		
		// test path to same square is length 1
		path = board.getBestPathTo(p1, spaEntrance);
		if (path.size() != 1){
			fail("path size should be 1: " + path.size());
		}
				
		// test path from entrance to room is length 2
		path = board.getBestPathTo(p1, spa);
		if (path.size() != 2){
			fail("path size should be 2: " + path.size());
		}
		
		// test path from room to entrance is length 2
		board.setPlayerPosition(p1, spa);
		path = board.getBestPathTo(p1, spaEntrance);
		if (path.size() != 2){
			fail("path size should be 2: " + path.size());
		}
		
		// test path from room to room is right length
		path = board.getBestPathTo(p1, theatre);
		if (path.size() != 10){
			board.drawPath(path);
			fail("path size should be 10: " + path.size());

		}
		
		// test paths from different entrances
		board.setPlayerPosition(p1, swimmingPool);
		
		path = board.getBestPathTo(p1, patio);
		if (path.size() != 6){
			board.drawPath(path);
			fail("path size should be 6: " + path.size());

		}
		
		path = board.getBestPathTo(p1, theatre);
		if (path.size() != 9){
			board.drawPath(path);
			fail("path size should be 9: " + path.size());

		}
		
		
	}
	
	/**
	 * Tests that finding the closest Intrigue square to the player works
	 */
	@Test
	public void testClosestIntrigue(){
		getNewBoard();
		
		List<Square> path = board.getBestPathTo(p3, Room.Intrigue);
		if (path.size() != 6){
			board.drawPath(path);
			fail("path size should be 6: " + path.size());
		}
		
		
	}
	
	
	
	
	
	
	
	
	// every corridor square should be connected to at least on other.
	// at most 4 others
	// if neighbour is CorridorCell, position differs by x or y, and only by 1
	@Test
	public void corridorConnectionTests(){
		getNewBoard();
		
		for (int row = 0; row < 29; row++  ){
			for (int col = 0; col < 25; col++){
				Square s = board.getSquare(new Point(col, row));
				
				if (s instanceof CorridorCell){
					CorridorCell corCell = (CorridorCell) s;
					Set<Cell> neighs = corCell.getNeighbours();
					
					// has between 1 and 4 neighbours
					assertTrue(neighs.size() > 0 && neighs.size() < 5);
					
					// 	// if neighbour is CorridorCell, position differs by x or y, and only by 1
					int x = s.getPosition().x;
					int y = s.getPosition().y;
					
					for (Cell neigh: neighs){
						
						if (neigh instanceof CorridorCell){
							
							int nX = neigh.getPosition().x;
							int nY = neigh.getPosition().y;
							
							if (x == nX ){
								assertTrue(Math.abs(y-nY) == 1);		
							}
							else if (y == nY ){
								assertTrue(Math.abs(x-nX) == 1);
							}
							else {
								fail("either x or Y positions must be the same: " + s.getPosition() + " " + neigh.getPosition());
							}
						}
					}
				}
				
			}
		}
			
	}

	
	
	/**
	 * Gets a newly set up version of the board.
	 * @return
	 */
	public void getNewBoard(){
		board = new Board(players);
		
		spa = board.getSquare(new Point(5,5));
		theatre = board.getSquare(new Point(10, 5));
		spaEntrance = board.getSquare(new Point (5,6));
		swimmingPool = board.getSquare(new Point(15,15));
		patio = board.getSquare(new Point(5,15));
	}
	
	
}
