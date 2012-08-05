package CluedoGame;

import java.util.List;
import java.util.Random;

import CluedoGame.Board.Board;

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
	private boolean hasRolled, hasSuggested, hasMovedThroughRoom;
	private int stepsLeft;
	
	
	//need to store murder room/ weapon/ character in some data structure
	//some data structure here

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
			stepsLeft = (random.nextInt(6) * 2) + 1;	//maybe a static final int for #dice
			
			hasRolled = true;							//change flag to true since they've rolled now
			
			return stepsLeft;
		}
	}


	/**
	 * Moves the player through the secret entrance of the room they are in, if it exists, and they are able to.
	 * 
	 * @throws InvalidMoveException if player isn't in corner room or has already moved this turn
	 */
	public void moveSecretPassage() throws InvalidMoveException {
		if (currentPlayer.getPosition().equals(null)) {		// filler. Need to be able to check position is a corner room
			throw new InvalidMoveException("Current location doesn't have a secret passageway!");
		} else if (hasMovedThroughRoom) {
			throw new InvalidMoveException("Already moved this turn!");
		} else if (hasSuggested) {
			throw new InvalidMoveException("Cannot move after making a suggestion!");
		}
		//other checks maybe
		
		//now need a method similar too
		//currentPlayer.enterSecretPassage();
		//or
		//board.moveSecretPassage(currentPlayer);
	}
}
