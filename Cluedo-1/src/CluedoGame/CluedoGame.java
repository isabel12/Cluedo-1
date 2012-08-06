package CluedoGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import CluedoGame.Board.Board;
import CluedoGame.Board.Cell;
import CluedoGame.Board.CorridorCell;
import CluedoGame.Board.RoomCell;

/**
 * Represents an actual Cluedo game.
 * One instantiates the game by supplying the number of players.
 * 
 * The game is played by calling appropriate methods, and querying the resulting state.
 * 
 * @author Troy Shaw
 *
 */
public class CluedoGame {

	//players currently playing the game
	private List<Player> livePlayers;

	//players who made incorrect accusation and lost
	private List<Player> losingPlayers;

	//character who is taking current turn
	private Player currentPlayer;

	//current turn takers variables. Have they rolled, made a murder suggestion, how many steps left, etc.
	private boolean hasRolled;
	private int stepsRemaining;  //<--- this should be cleared when we enter a room
	private boolean inRoom;

	
	private boolean turnFinished; // <------madeSuggestion isn't necessary, because will go straight to turnFinished.
	
	
	//need to store murder room/ weapon/ character in some data structure
	//some data structure here
	List<Card> solution; // <--------------- this could be just a list in a set format (Character, weapon, room)

	//the current board
	private Board board;

	
	

	
	


	//more data...
	
	



	
	



	/**
	 * Creates a CluedoGame object with the number of players.
	 * Players are assigned at random.
	 * 
	 * @param numPlayers number of players for this game
	 */
	public CluedoGame(int numPlayers) {
		livePlayers = new ArrayList<Player>();
	}

	
	//---------------------------
	//methods called from CMDGame
	//---------------------------

	/**
	 * Returns true if the game is finished.
	 * A game continues until there is only 1 player left, or somebody makes
	 * a correct accusation from the pool-room.
	 * 
	 * @return true if game is over
	 */
	public boolean isGameOver() {
		return livePlayers.size() == 1;
		//also will need to check if a player has made a correct pool-room accusation
	}
	

	/**
	 * Returns true if it currently the given players turn.
	 * @param player
	 * @return 
	 */
	public boolean isTurn(Player player) {
		return currentPlayer.equals(player);
	}
	
	/**
	 * Returns the next player in the game turn cycle.
	 * 
	 * @return
	 */
	public Player getNextPlayer() {
		//will need a cyclic queue to store current players
		//perhaps a queue or LinkedList, getting first, then putting at end after each turn
		return null;	//fille for now
	}
	
	
	/**
	 * Returns the smallest number of steps to each room on the map.
	 * For rooms with multiple doors, only the smallest path is given.
	 * 
	 * 
	 * @param player
	 * @return
	 */
	public Map<RoomCell, Integer> getRoomSteps(Player player) {
		// this method will call a similar method in board to get this.
		
		
		
		return null;
	}
	
	/**
	 * Ends the current players turn.
	 * If the player cannot end their turn because they haven't rolled, moved, etc,
	 * this method throws InvalidMoveException.
	 * 
	 * @throws InvalidMoveException if the player isn't allowed to end their turn
	 */
	public void endTurn() throws InvalidMoveException {

		//will need other checks
		if (!hasRolled) {
			throw new InvalidMoveException(currentPlayer + " isn't allowed to end turn yet.");
		} else {
			
		}
	}
	
	
	/**
	 * Rolls the dice for the current player, returning the value they rolled.
	 * This game uses 2 dice, so value is between 2-12.
	 * @return
	 * @throws InvalidMoveException if the player has already rolled.
	 */
	public int rollDice() throws InvalidMoveException {
		if (hasRolled) {	
			throw new InvalidMoveException(currentPlayer + " has already rolled this turn");
		} else {
			Random random = new Random();
			stepsRemaining = (random.nextInt(6) * 2) + 1;	//maybe a static final int for #dice
			
			hasRolled = true;							//change flag to true since they've rolled now
			
			return stepsRemaining;
		}
	}


	public void makeSuggestion(Character chara, Weapon weapon) throws InvalidMoveException{
		
	}
	
	public void makeSuggestion(Character chara, Weapon weapon, Room room) throws InvalidMoveException {
		// in this method, we need to check if game is over.
	}
	
	
	public void drawIntrigueCard() throws InvalidMoveException{
		// this will only work if the current player is on an intrigue card square, and they have rolled.
		

		// it will need to check if the game is over if the timeCards kill a player.
	}
	
	
	/**
	 * Moves the player as close to (or inside if possible) the given room.
	 * Will find the most efficient path to the desired room.
	 * 
	 * @param room
	 * @throws InvalidMoveException if the player has no steps left
	 */
	public void moveTowards(Room room) throws InvalidMoveException {
		//first need to check player has moves left/ can move at all
		
		//then find the shortest path to a door of the given room
		List<Cell> path = board.getBestPathTo(currentPlayer, room);
		
		//then move as many steps as the player has left to that location
		for(int i = 0; i < stepsRemaining; i++){
			Cell square = path.get(i);
			if (square instanceof CorridorCell){
				// if intrigue board, only move to here (so the UI class can ask if the player wants to pick a card up)
			}
		}
	}
	
	/**
	 * Moves the player through the secret entrance of the room they are in, if it exists, and they are able to.
	 * 
	 * @throws InvalidMoveException if player isn't in corner room or has already moved this turn
	 */
	public void moveSecretPassage() throws InvalidMoveException {
		if (currentPlayer.inCornerRoom()) {		// filler. Need to be able to check position is a corner room
			throw new InvalidMoveException("Current location doesn't have a secret passageway!");
		} else if (hasRolled && stepsRemaining == 0) {  
			throw new InvalidMoveException("Already moved this turn!");
		} else if (turnFinished) { // <---- the idea is after a suggestion, the turnFinished status is set
			throw new InvalidMoveException("Cannot move after making a suggestion!");
		}
		//other checks maybe
		
		//now need a method similar too
		//currentPlayer.enterSecretPassage();
		//or
		//board.moveSecretPassage(currentPlayer);
	}
	
	/**
	 * The idea for this method is to communicate what main stage the game is at, so the UI class can communicate with the player.
	 * The main useful states are:
	 * 
	 * 1 - inCorridor, notRolled
	 * 2 - rolled, moves remaining 
	 * 3 - inRoom, notRolled
	 * 4 - inCornerRoom, notRolled
	 * 5 - turnFinished
	 * 6 - rolled, Location is intrigue
	 * 
	 * @return
	 */
	public int getGameStatus(){
		if (!inRoom && !hasRolled){return 1;}
		if (hasRolled && stepsRemaining > 0){return 2;}
		if (inRoom && !hasRolled){return 3;}
		if (currentPlayer.inCornerRoom() && !hasRolled){return 4;}  // <--- should we let people move if they have rolled, but haven't moved?
		if (turnFinished){return 5;}
		//if (hasRolled && !turnFinished && )
		
		
		
		
	}
	
}
