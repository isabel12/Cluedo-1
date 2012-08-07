package CluedoGame.Board;

import java.awt.Point;

import CluedoGame.Room;

public interface Square {

	
	public boolean isCorridor();
	public boolean isRoom();
	public boolean isCornerRoom();
	
	public Point getPosition();
	public Room getRoom();
	
	
}
