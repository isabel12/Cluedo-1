package CluedoGame;

import java.util.List;

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

	
}
