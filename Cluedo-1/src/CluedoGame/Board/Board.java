package CluedoGame.Board;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import CluedoGame.Character;
import CluedoGame.Player;
import CluedoGame.Room;

/**
 * The Board class is responsible for keeping track of player locations, and moving their locations when requested
 * via a valid route.
 * 
 * 
 * 
 * NOT SURE YET
 * I'm not sure if it should be responsible for setting the Player's location.  Eg.  Whether the Board is passed a player, and it gets the players character,
 * moves that character on the board, and then changes the Player's location to match its token on the board, or have the methods return the location moved to,
 * which the Player is responsible for remembering.
 * 
 * 
 * NOTE:  I've implemented it so that Point (x,y) is (row, col).  This may need changing!!!  Change this to match x,y!!!!!!
 * 
 * 
 * @author Izzi
 *
 */
public class Board {

	// board info
	//-----------
	private Cell[][] map; // a 2D array of Cell objects representing the map.  Every box will contain a Cell, although room cells will be duplicated.
	private static Map<Character, Cell> startingCells;  // a map to keep track of where characters start.  I won't make a new type of Cell for now.
	
	// dimensions of the board
	private static final int cols = 25; 
	private static final int rows = 29;

	// player info
	//------------
	private Set<Character> players; // a set of the characters on the board
	private Map<Character, Cell> playerPos; // <---------- Still not super sure about this implementation yet


	
	
	public Board(){
		this.map = new Cell[Board.rows][Board.cols];
		this.playerPos = new HashMap<Character, Cell>();
	}
	
	
	/**
	 * This adds the collection of players to the board, and updates the Player's position for them (not sure if this is bad style, we can change it!) 
	 * @param currPlayers
	 */
	public void addPlayers(Collection<Player> currPlayers){
		if (startingCells == null){
			System.out.println("The board hasn't been set up yet.  Call constructBoard(char[][]) first.");
			return;
		}
		
		// initialise the set of characters
		//---------------------------------
		this.players = new HashSet<Character>();
		
		// put the characters on the board at their starting locations, and update the Player's position
		//----------------------------------------------------------------------------------------------
		for (Player p: currPlayers){
			
			// add character to list of characters playing
			Character charac = p.getCharacter();
			players.add(charac); 
			
			// set player position to their default start position.
			CorridorCell start = (CorridorCell) startingCells.get(charac);
			setNewPlayerPosition(p, start);
		}
		
	}
	
	/**
	 * Helper method.  Changes the position of the character.  It updates the cell's 'empty' flag, 
	 * records the character's current position, and updates the Player.
	 * @param player
	 * @param newPos
	 */
	public void setNewPlayerPosition(Player player, Cell newPos){  // <-- change this to Square!
		Character character = player.getCharacter();
		
		// set current Cell to empty
		Cell currPos = playerPos.get(character);
		if (currPos != null){
			currPos.setEmpty(true);
		}
		
		// set new Cell as occupied
		newPos.setEmpty(false);
		
		// record character is in new cell
		playerPos.put(character, newPos);
		
		// update the Player's position
		player.setPosition(newPos);
		
	}
	
	


	//=============================================================================================
	// Ideas for wrapper methods
	//=============================================================================================

	
	public List<Cell> getBestPathTo(Player currentPlayer, Room room) {
		// TODO Auto-generated method stub
		//This one is probably needed.
		return null;
	}
	

	//	QUESTION: should the paramters be a Point, instead of ints?
	/** 
	 * Move character to given x, y locations (or as close as possible) using the A* algorithm.
	 * 
	 * @param chara
	 * @param x
	 * @param y
	 * @return
	 */
	public void move(Player player, Point p) {
		//run A* to get best path in the form of a list of Direction from current
		//location to end location
		//List<Direction> path = getBestPath(player.getCharacter(), );	//need to make this method

		//subtract path.size() from charas move count or something here...

		//iterate over Direction, moving chara in that direction
		//this assumes that getBestPath() will return a path that isn't blocked, etc
		//for (Direction d: path) move(player, d);
	}

	
	/**
	 * Move character towards the specified room (or as close as possible) using the A* algorithm.
	 * 
	 * @param chara
	 * @param room
	 * @return
	 */
	public void move(Player player, Room room) {
		//get coordinates of given door, and call move(chara, x, y);
		//perhaps instead of using Room object, we use RoomNode or something
		//since a few rooms have more than 1 door, so we'd need to specify that somewhere
	}
	
	
	/**
	 * This method returns the optimum path from the player's position, and the point given.
	 * 
	 * Wrapper method for List<Cell> getBestPathTo(Cell s, Cell g).
	 * 
	 * @param player
	 * @param p
	 * @return
	 */
	public List<Cell> getBestPathTo(Player player, Point p){
		// get the start and goal cells
		Cell s = player.getPosition();
		Cell g = getCell(p);	
		return this.getBestPathTo(s,g);
	}
	
	
	//=============================================================================================
	// Path finding methods (woo!)
	//=============================================================================================
	
	
	/**
	 * 
	 * This method returns the optimum path in the form of a List<Cell>, where the first entry is the start, and the last the goal.
	 * 
	 * This method is a wrapper method for the A* search algorithm, whose heuristic getEstimate() algorithm can't handle rooms being in lots of places at once.
	 * It takes the start cell and the goal cell, checks whether either of them are rooms, and if so, it gets all optimum paths between the room entrances, and the 
	 * start/goal CorridorCell, picks the best one, then makes sure to include the room Cell in the list before returning.
	 * 
	 * @param s
	 * @param g
	 * @return
	 */
	public List<Cell> getBestPathTo(Cell s, Cell g){
		// to hold the best path
		List<Cell> bestPath = new ArrayList<Cell>();
		int bestSize = Integer.MAX_VALUE;
		
		// a. if both start and goal are RoomCells:
		//-----------------------------------------
		if (s instanceof RoomCell && g instanceof RoomCell){
			// get all the rooms entrances, and find best path between all start entrances, and goal entrances. 
			RoomCell start = (RoomCell)s;
			RoomCell goal = (RoomCell)g;
			List<CorridorCell> sEntrances = start.getEntrances();
			List<CorridorCell> gEntrances = goal.getEntrances();
			
			// check them all, and save the path if its better than already recorded
			for (int i = 0; i < sEntrances.size(); i++){
				for (int j = 0; j< gEntrances.size(); j++){
					List<Cell> path = this.getBestPathTo(sEntrances.get(i), gEntrances.get(j));
					if (path.size() < bestSize){
						bestPath = path;
						bestSize = path.size();
					}
				}
			}
			// add start room and goal room back into the final list
			bestPath.set(0, start);
			bestPath.add(goal);	
		}
		
		
		// b. if start is a RoomCell:
		//----------------------------
		else if (s instanceof RoomCell){
			RoomCell start = (RoomCell)s;
			List<CorridorCell> sEntrances = start.getEntrances();
			CorridorCell goal = (CorridorCell)g;

			// check them all, and save the path if its better than already recorded
			for (int i = 0; i < sEntrances.size(); i++){
				List<Cell> path = this.getBestPathTo(sEntrances.get(i), goal);
				if (path.size() < bestSize){
					bestPath = path;
					bestSize = path.size();
				}
			}
			// add start room back into the final list
			bestPath.set(0, start);
		}
		
		
		// c. if goal is a RoomCell:
		//--------------------------
		else if (g instanceof RoomCell){
			RoomCell goal = (RoomCell)g;
			List<CorridorCell> gEntrances = goal.getEntrances();
			CorridorCell start = (CorridorCell)s;

			// check them all, and save the path if its better than already recorded
			for (int i = 0; i < gEntrances.size(); i++){
				List<Cell> path = this.getBestPathTo(start, gEntrances.get(i));
				if (path.size() < bestSize){
					bestPath = path;
					bestSize = path.size();
				}
			}
			// add goal room back into the final list
			bestPath.add(goal);
			
		}
		
		
		// d. if both are corridors:
		//-----------------------
		else {
			bestPath = this.getBestPathTo((CorridorCell)s, (CorridorCell)g);
		}
		

		return bestPath;	
	}
	
	
	/**
	 * This method returns the optimum unobstructed path in the form of a List<Cell>, where the first entry is the starting Cell, and the last the goal Cell.
	 * 
	 * This method implements the slow version of the A* search algorithm,  and can only calculate paths between CorridorCells. It returns a list of the best path from the Player's current position to the cell at the given point.
	 * @param player
	 * @param p
	 * @return
	 */
	public List<Cell> getBestPathTo(CorridorCell start, CorridorCell goal){
		
		// 1. initialise everything	
		//-------------------------
		PriorityQueue<CellPathObject> fringe = new PriorityQueue<CellPathObject>();
		List<Cell> path = new ArrayList<Cell>();
		int bestToGoal = Integer.MAX_VALUE;
		Set<CorridorCell> pathMarked = new HashSet<CorridorCell>();
		
		// make a map that records best path to all Cells so far (initialized high)
		Map<Cell, Integer> bestPathTo = new HashMap<Cell, Integer>();
		for(int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){	
				Cell c = map[i][j];
				if (c instanceof CorridorCell){
					bestPathTo.put(c, Integer.MAX_VALUE);
				}
			}
		}
			
		// 2. put start on the fringe
		//---------------------------
		fringe.add(new CellPathObject(start, null, 0, this.getEstimate(start, goal)));
		
		
		// 3. step through until find best path
		//-----------------------------------------
		while(fringe.size() != 0){
			// get next best node
			CellPathObject nextBest = fringe.poll();
			
			// if this route is shorter than the one recorded to this cell
			if(nextBest.getCostToHere() < bestPathTo.get(nextBest)){
				CorridorCell cell = nextBest.getCell();
				// mark the path between them, record the shorter route, and add to list of cells that have been changed
				cell.setPathFrom(nextBest.getFrom()); 
				bestPathTo.put(cell, nextBest.getCostToHere());
				pathMarked.add(cell);
				
				// break if we have reached the goal
				if(cell == goal){
					break; 
				}
				
				// add all neighbours whose paths are better than recorded, and less than best to goal, and empty.
				int toNeigh = nextBest.getCostToHere() + 1;
				for(Cell n: cell.getNeighbours()){
					if (n instanceof CorridorCell){
						CorridorCell neigh = (CorridorCell)n;
						if (toNeigh < bestPathTo.get(neigh) && toNeigh < bestToGoal && neigh.isEmpty()){
							int total = toNeigh + this.getEstimate(neigh, goal);
							fringe.add(new CellPathObject(neigh, cell, toNeigh, total));
							// if the neighbour is the goal, record new best time
							if (neigh == goal){
								bestToGoal = toNeigh;
							}
						}
					}
				}
			}	
		}	
		// when we get to here, we have found the shortest path.  
		
		
		// 4. Step through path from goal to start, putting each cell on the stack
		//------------------------------------------------------------------------
		Stack<Cell> reverser = new Stack<Cell>();
		CorridorCell node = goal;
		CorridorCell from = null;
		while((from = node.getPathFrom()) != null){
			reverser.push(node);
			node = from;
		}
		reverser.push(node);
		
		
		// 5. clear the paths recorded in all the cells
		//------------------------------------------		
		for(CorridorCell c: pathMarked){
			c.resetPath();
		}
		
		// 6. Add the Cells in order to the list from start to goal and return
		//--------------------------------------------------------------------
		while(!reverser.isEmpty()){
			path.add(reverser.pop());
		}
		return path;
	}
	
	
	/**
	 * Returns the optimal path (ignoring walls etc) between two Cells.
	 * Calculated by horizontal difference + vertical difference (so it doesn't try go diagonally).
	 * 
	 * Note: This should never be given a RoomCell - only an entrance to one.
	 * 
	 * @param start - the starting Cell. 
	 * @param goal - the end Cell.
	 * @throws - IllegalArgumentException if given a RoomCell
	 * @return
	 */
	private int getEstimate(Cell start, Cell goal){
		if (start instanceof RoomCell || goal instanceof RoomCell){
			throw new IllegalArgumentException("Shouldn't be given a RoomCell.");
		}
		
		int sx = start.getPosition().x;
		int gx = goal.getPosition().x;
		int sy = start.getPosition().y;
		int gy = goal.getPosition().y;
		
		return (Math.abs(sx - gx) + Math.abs(sy - gy)); 
	}
	
	
	
	public Cell getCell(Point p){
		int row = p.x;
		int col = p.y;
		
		if (!(row >= 0 && row < Board.rows && cols >= 0 && cols < Board.cols)){
			throw new IllegalArgumentException("Point (row, col) is out of bounds.");
		}
		
		return map[row][col];
		
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
	public void move(Player player, Direction dir) {
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

	//=============================================================================================
	// Query methods??
	//=============================================================================================


	
	
	
	
	
	
	
	//=============================================================================================
	// Private methods
	//=============================================================================================
	

	/**
	 * This method reads in the map.data file, and constructs a 2D array of char. 
	 * It checks the dimensions of the Board against those in the file (file reading will fail if different).
	 * 
	 * @return - will return null, and print a message if the file reading failed.
	 */
	private char[][] readFromFile() {
		char[][] rawData = null;
		
		try{
			
			Scanner scan = new Scanner(new File("map.data"));

			// read size of board (first line)
			//--------------------------------
			String line = scan.nextLine();
			Scanner lineScan = new Scanner(line);
			int cols = lineScan.nextInt();
			int rows = lineScan.nextInt();
			if (cols != Board.cols || rows != Board.rows){
				throw new IOException("The board dimensions in the file don't match. They should be: " + Board.cols + " "+ Board.rows);
			}
			
			

			// initialise this.map and 2D
			//----------------------------------
			rawData = new char[Board.rows][Board.cols]; 
			this.map = new Cell[Board.rows][Board.cols];

			// read cells into array
			//---------------------		
			for(int i = 0; i < Board.rows; i++){
				if (!scan.hasNextLine()){break;}
				line = scan.nextLine();
				for (int j = 0; j < Board.cols; j++){	
					rawData[i][j] = line.charAt(j);
				}
			}	

								
		} catch(IOException e){System.out.println(e);}
		
		return rawData;

	}


	/**
	 * Reads the data from the given 2D char array and constructs the Board (a 2D array of Cells each representing a square on the board).  
	 * This method puts the cells into the map array, connects the Cells together to form a traversible graph, and puts the characters
	 * that are playing on their starting positions.
	 */
	private void constructBoard(char[][] rawData) {
		// initialise the Board structure, and startingCells map
		this.map = new Cell[Board.rows][Board.cols];
		this.startingCells = new HashMap<Character, Cell>();
		
		
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
					
				default: // otherwise assume its a corridor, starting point, or entrance
					map[i][j] = new CorridorCell(false);
					map[i][j].addPosition(new Point(i,j));				
					// if its a starting point, add it to the 'startingCells' map
					if (java.lang.Character.isDigit(c)){
						int index = Integer.parseInt(java.lang.Character.toString(c));
						Board.startingCells.put(Character.values()[index-1], map[i][j]);
					}
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

		// connect corridor squares
		for(int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){	
				Cell c = map[i][j];
				if (c instanceof CorridorCell){
					this.connectCorridors((CorridorCell)c);
				}
			}
		}
		
		// connect secret passages
		s.setSecretPassage(g);
		g.setSecretPassage(s);
		k.setSecretPassage(o);
		o.setSecretPassage(k);
	}
	
	
	/**
	 * This method connects the given corridor to all 
	 * @param corridor
	 */
	private void connectCorridors(CorridorCell corridor){
		// get coordinates of corridor
		Point position = corridor.getPosition();
		int x = position.x;
		int y = position.y;
		
		// for each Point (i,j) surrounding corridor:
		for (int i = x-1; i <= x+1; i++){
			for (int j = y-1; j <= y+1; j++){
				boolean withinBounds = i>=0 && i<this.rows && j>=0 && j<this.cols;
				// as long as (i,j) is within bounds of the board AND
				// the Cell at that pos is a CorridorCell AND
				// the cell isn't itself, then connect that cell to 'corridor' 
				if (withinBounds){
					Cell other = map[i][j];
					if (other instanceof CorridorCell && other != corridor){
						corridor.connectTo(other);
						other.connectTo(corridor);
					}
				}
			}	
		}
	}
	

	/**
	 * Enum for use with Move() methods, and specifying direction in general.
	 */
	public enum Direction {
		North,
		South,
		East,
		West
	}

	
	/** 
	 * For testing :)
	 * @param args
	 */
	public static void main(String[] args){
		Board b = new Board();
		b.constructBoard(b.readFromFile());
		
		// create a set of players
		Set<Player> players = new HashSet<Player>();	
		players.add(new Player(Character.Scarlett));
		players.add(new Player(Character.Green));
		
		// add players to the board
		b.addPlayers(players);
		
		System.out.println("done!");
		
		

	}





}
