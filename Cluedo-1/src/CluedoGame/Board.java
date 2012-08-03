package CluedoGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {

	/**
	 * Enum for use with Move() methods, and specifying direction in general.
	 */
	public enum Direction {
		North,
		South,
		East,
		West
	}
	
	//not too sure what data we need


	//not too sure what params are needed for constructor yet
	public Board() {
		//read the board data and create the map
		readFromFile();
	}



	//dunno about return types for move() methods yet
	//just initial planning


	/**
	 * Moves character in given direction. 
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
	
	/**
	 * Move chara to specified room (or as close as possible).
	 * 
	 * @param chara
	 * @param room
	 * @return
	 */
	public void move(Character chara, Room room) {
		//get coordinates of given door, and call move(chara, x, y);
		//perhaps instead of using Room object, we use RoomNode or something
		//since a few rooms have more than 1 door, so we'd need to specify that somewhere
		move(chara, room.getX(), room.getY());
	}

	




	/**
	 * Read the data from the map file into an internal data structure.
	 */
	private void readFromFile() {
		Scanner scan = new Scanner("map.data");

		while (scan.hasNextLine()) {
			//not too sure how we should process data yet.
			//need to set up appropriate objects to represent different square types
		}
	}

	//	for our reference...

	//	Key.
	//
	//	square directly adjacent to a door of that type
	//
	//	s = spa
	//	t = theatre
	//	l = living room
	//	o = observatory
	//	p = patio
	//	h = hall
	//	k = kitchen
	//	d = dining room
	//	g = guest house
	//	w = swimming pool
	//
	//	x = room square (not important)
	//
	//	? = question mark square
	//
	//	6 player start positions
	//
	//	b = blue	=	Eleanor Peacock
	//	u = purple	=	Victor Plum
	//	g = green	=	Jacob Green
	//	i = white	=	Diane White
	//	y = yellow	=	Jack Mustard
	//	e = red		=	Kasandra Scarlett
	//
	//	. = moveable square	(players can walk here)
}
