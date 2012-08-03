package CluedoGame.Board;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import CluedoGame.Character;
import CluedoGame.Room;

/**
 * The Board class is responsible for keeping track of player locations, and moving their locations when requested
 * via a valid route.
 * 
 * @author Izzi
 *
 */
public class Board {

	// board info
	//-----------
	Cell[][] map; // a 2D array of Cell objects representing the map.  Every box will contain a Cell, although room cells will be duplicated.
	Map<Character, Cell> startingCells;  // a map to keep track of where characters start.  I won't make a new type of Cell for now.


	// player info
	//------------
	Set<Character> players; // a set of the characters on the board
	Map<Character, Cell> playerPos;



	public Board(){


	}






	//dunno about return types for move() methods yet
	//just initial planning


	/**
	 * Moves character in given direction by one square. 
	 * 
	 * @param chara
	 * @param dir
	 * @return
	 */
	public void move(Character chara, Direction dir) {

		switch (dir) {
		case North:
			//move north
			break;
		case South:
			//move south
			break;
		case East:
			//move east
			break;
		case West:
			//move west
			break;
		}
	}

	/**
	 * Move character to given x, y locations, using the A* algorithm.
	 * 
	 * @param chara
	 * @param x
	 * @param y
	 * @return
	 */
	public void move(Character chara, int x, int y) {
		//run A* to get best path in the form of a list of Direction from current
		//location to end location
		List<Direction> path = getBestPath(chara, x, y);	//need to make this method

		//subtract path.size() from charas move count or something here...

		//iterate over Direction, moving chara in that direction
		//this assumes that getBestPath() will return a path that isn't blocked, etc
		for (Direction d: path) move(chara, d);
	}

	private List<Direction> getBestPath(Character chara, int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}



	/**
	 * Move chara towards the specified room (or as close as possible).
	 * 
	 * @param chara
	 * @param room
	 * @return
	 */
	public void move(Character chara, Room room) {
		//get coordinates of given door, and call move(chara, x, y);
		//perhaps instead of using Room object, we use RoomNode or something
		//since a few rooms have more than 1 door, so we'd need to specify that somewhere
	}






	/**
	 * Read the data from the map file into an internal data structure.
	 */
	private void readFromFile() {

		try{
			Scanner scan = new Scanner(new File("map.data"));

			// read size of board (first line)
			//--------------------------------
			String line = scan.nextLine();
			Scanner lineScan = new Scanner(line);
			int cols = lineScan.nextInt();
			int rows = lineScan.nextInt();

			// make 2D array of char to refer to, and initialise this.map
			//----------------------------------
			char[][] rawData = new char[rows][cols]; 
			this.map = new Cell[rows][cols];

			// read data into array
			//---------------------		
			for(int i = 0; i < rows; i++){
				if (!scan.hasNextLine()){break;}
				line = scan.nextLine();
				for (int j = 0; j < cols; j++){	
					rawData[i][j] = line.charAt(j);
				}
			}	

			// Make all the RoomCell objects
			//----------------------
			RoomCell s = new RoomCell(Room.Spa);
			RoomCell t = new RoomCell(Room.Theatre);
			RoomCell l = new RoomCell(Room.LivingRoom);
			RoomCell o = new RoomCell(Room.Observatory);
			RoomCell p = new RoomCell(Room.Patio);
			RoomCell h = new RoomCell(Room.Hall);			
			RoomCell k = new RoomCell(Room.Kitchen);
			RoomCell d = new RoomCell(Room.DiningRoom);
			RoomCell g = new RoomCell(Room.GuestRoom);
			RoomCell w = new RoomCell(Room.SwimmingPool);


			// Put all Cells into the map, and add their positions
			//------------------------------------------------
			for(int i = 0; i < rows; i++){
				for (int j = 0; j < cols; j++){	
					char c = rawData[i][j];
					switch(c){
					case 'S':
						map[i][j] = s;
						s.addPosition(new Point(i,j));
						break;
					case 'T':
						map[i][j] = t;
						t.addPosition(new Point(i,j));
						break;
					case 'L':
						map[i][j] = l;
						l.addPosition(new Point(i,j));
						break;
					case 'O':
						map[i][j] = o;
						o.addPosition(new Point(i,j));
						break;
					case 'P':
						map[i][j] = p;
						p.addPosition(new Point(i,j));
						break;
					case 'H':
						map[i][j] = h;
						h.addPosition(new Point(i,j));
						break;
					case 'K':
						map[i][j] = k;
						k.addPosition(new Point(i,j));
						break;
					case 'D':
						map[i][j] = d;
						d.addPosition(new Point(i,j));
						break;
					case 'G':
						map[i][j] = g;
						g.addPosition(new Point(i,j));
						break;
					case 'W':
						map[i][j] = w;
						w.addPosition(new Point(i,j));
						break;		
					case '?':
						map[i][j] = new CorridorCell(true);
						map[i][j].addPosition(new Point(i,j));
						break;
					default: // otherwise assume its an intrigue, corridor, or entrance
						map[i][j] = new CorridorCell(false);
						map[i][j].addPosition(new Point(i,j));
						break;
					};	
				}
			}

			
			// Connect the entrance squares to the rooms
			//------------------------------------------
			for(int i = 0; i < rows; i++){
				for (int j = 0; j < cols; j++){	
					char c = rawData[i][j];
					Cell entrance = map[i][j];

					switch(c){
					case 's':
						entrance.connectTo(s);
						s.connectTo(entrance);
						break;
					case 't':
						entrance.connectTo(t);
						t.connectTo(entrance);
						break;
					case 'l':
						entrance.connectTo(l);
						l.connectTo(entrance);
						break;
					case 'o':
						entrance.connectTo(o);
						o.connectTo(entrance);
						break;
					case 'p':
						entrance.connectTo(p);
						p.connectTo(entrance);
						break;
					case 'h':
						entrance.connectTo(h);
						h.connectTo(entrance);
						break;
					case 'k':
						entrance.connectTo(k);
						k.connectTo(entrance);
						break;
					case 'd':
						entrance.connectTo(d);
						d.connectTo(entrance);
						break;
					case 'g':
						entrance.connectTo(g);
						g.connectTo(entrance);
						break;
					case 'w':
						entrance.connectTo(w);
						w.connectTo(entrance);
						break;
					};
				}
			}
			
			// Connect the corridor squares and the secret passages
			//-----------------------------------------------------
			
			









		} catch(IOException e){System.out.println(e);}



	}


	//===========================================================
	// Enum classes
	//===========================================================

	/**
	 * Enum for use with Move() methods, and specifying direction in general.
	 */
	public enum Direction {
		North,
		South,
		East,
		West
	}

	public static void main(String[] args){
		Board b = new Board();
		b.readFromFile();

	}


}
