package CluedoGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
	private Queue<Player> livePlayers;

	//players who made incorrect accusation and lost
	private List<Player> losingPlayers;

	//character who is taking current turn
	private Player currentPlayer;

	//current overall game state. True if a correct murder accusation or only 1 player left
	private boolean gameFinished;

	//current turn takers variables.
	private boolean hasRolled, hasSuggested, hasUsedSecretPassage, hasEnteredRoom, turnFinished;
	private int stepsRemaining;


	//refute player and if the game is currently in the 'refute' state
	private Player toRefute = null;
	private boolean refuteMode;



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
		livePlayers = new LinkedList<Player>();
		
		//generate players given int
		//generate cards
		//give equally players the cards
		//generate board
		//initialise variables
		//
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
	public Player getCurrentPlayer() {
		return currentPlayer;
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
		if (!livePlayers.contains(player)) {
			throw new InvalidMoveException("Cannot get best paths for a dead player!");
		} else if (gameFinished) {
			throw new InvalidMoveException("Cannot get best paths after game is finished!");
		}
		
		//should be okay to return best path now
		return board.getBestPathToAll(player);
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
		} else if (refuteMode){
			throw new InvalidMoveException("Cannot end turn while players are refuting!");
		} if ()
		
		//should be okay now
		
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
		} if (turnFinished) {
			throw new InvalidMoveException("Cannot roll after ending turn!");
		} else if (gameFinished) {
			throw new InvalidMoveException("Cannot roll after game is finished!");
		} else if (refuteMode) {
			throw new InvalidMoveException("Cannot roll while refuting!");
		}

		//should be good to roll now
		Random random = new Random();
		stepsRemaining = (random.nextInt(6) * 2) + 1;	//maybe a static final int for #dice

		//update variables
		hasRolled = true;							//change flag to true since they've rolled now


		return stepsRemaining;
	}


	public void makeSuggestion(Character chara, Weapon weapon) throws InvalidMoveException{
		if (gameFinished) {
			throw new InvalidMoveException("Cannot make a suggestion once game is finished!");
		} else if (hasSuggested) {
			throw new InvalidMoveException("You have already suggested this turn!");
		} else if (board.inRoom(currentPlayer)) {
			throw new InvalidMoveException("Must be in a room to suggest!");
		} else if (refuteMode) {
			throw new InvalidMoveException("Cannot make suggestion while refuting!");
		}

		//logic here


		//update variables
		hasSuggested = true;
		refuteMode = true;
	}

	public void makeSuggestion(Character chara, Weapon weapon, Room room) throws InvalidMoveException {
		if (gameFinished) {
			throw new InvalidMoveException("Cannot make an accusation once game is finished!");
		} else if (hasSuggested) {
			throw new InvalidMoveException("You have already accused this turn!");
		} else if (currentPlayer.inPoolRoom()) {
			throw new InvalidMoveException("Must be in pool-room to accuse!");
		} else if (refuteMode) {
			throw new InvalidMoveException("Cannot make accusation while refuting!");
		}

		//logic here
		//check given accusation matches the actual murder stuff
		//if it matches, that player is declared the winner!

		//if not, that player loses and is removed from alivePlayers to losingPlayers
	}


	public void drawIntrigueCard() throws InvalidMoveException{
		if (!currentPlayer.onIntrigueSquare()) {
			throw new InvalidMoveException("Player isn't standing on an intrigue square!");
		}

		// it will need to check if the game is over if the timeCards kill a player.
		//intrigue card logic here

		//update variables

	}


	/**
	 * Moves the player as close to (or inside if possible) the given room.
	 * Will find the most efficient path to the desired room.
	 * 
	 * @param room
	 * @throws InvalidMoveException if the player has no steps left
	 */
	public void moveTowards(Room room) throws InvalidMoveException {
		if (!hasRolled) {
			throw new InvalidMoveException("Roll before moving!");
		} else if (stepsRemaining <= 0) {
			throw new InvalidMoveException("You have no more steps remaining!");
		} else if (gameFinished) {
			throw new InvalidMoveException("Cannot move once game is finished!");
		} else if (refuteMode) {
			throw new InvalidMoveException("Cannot move while refuting!");
		} else if (hasSuggested) {
			throw new InvalidMoveException("Cannot move after making a suggestion!");
		} else if (hasEnteredRoom) {
			throw new InvalidMoveException("Cannot move after entering a room!");
		}

		//should be okay to move, so perform logic
		//find the shortest path to a door of the given room
		List<Cell> path = board.getBestPathTo(currentPlayer, room);

		//then move as many steps as the player has left to that location
		for(int i = 0; i < stepsRemaining; i++){
			Cell square = path.get(i);
			if (square instanceof CorridorCell){
				// if intrigue board, only move to here (so the UI class can ask if the player wants to pick a card up)
			}
		}

		//update variables

	}

	/**
	 * Moves the player through the secret entrance of the room they are in, if it exists, and they are able to.
	 * 
	 * @throws InvalidMoveException if player isn't in corner room or has already moved this turn
	 */
	public void moveSecretPassage() throws InvalidMoveException {
		if (gameFinished) {
			throw new InvalidMoveException("Game is over!");
		} else if (!currentPlayer.inCornerRoom()) {
			throw new InvalidMoveException("Current location doesn't have a secret passageway!");
		} else if (hasEnteredRoom) {  
			throw new InvalidMoveException("Already moved this turn!");
		} else if (turnFinished) {
			throw new InvalidMoveException("Cannot move after ending turn!");
		} else if (hasSuggested) {
			throw new InvalidMoveException("Cannot move after making suggestion!");
		} else if (refuteMode) {
			throw new InvalidMoveException("Cannot move while refuting!");
		} else if (hasUsedSecretPassage) {
			throw new InvalidMoveException("Already moved through a secret passage!");
		}

		//move the player
		try {
			board.moveSecretPassage(currentPlayer);
		} catch (InvalidMoveException e) {
			//should never happen. If it does, we crash the game with an informative message
			throw new Error("Internal game errror.\n" + 
					currentPlayer.getCharacter().getName() + 
					" should have been able to go through secret passage from " +
					currentPlayer.getPosition().toString());
		}

		//update variables
		hasUsedSecretPassage = true;
		hasEnteredRoom = true;
	}

	/**
	 * Refutes the current suggestion with the given card.
	 * Refutes with the current refuter, obtained by getRefuter()
	 * 
	 * This method must be passed a card the refuter actually has.
	 * The card must also be part of the suggestion.
	 * 
	 * The game ensures the player returned by getRefuter() can logically refute the suggestion.
	 * 
	 * @param card 
	 * @throws InvalidMoveException 
	 */
	public void refuteSuggestion(Card card) throws InvalidMoveException {
		if (!refuteMode) {
			throw new InvalidMoveException("There is no suggestion to refute!");
		} else if (gameFinished) {
			throw new InvalidMoveException("Cannot refute once game is finished!");
		} else if (!solution.contains(card)) {
			throw new InvalidMoveException("Card was not part of the suggestion!");
		} else if (toRefute.hasCard(card)) {
			throw new InvalidMoveException("Refuter doesn't have that card!");
		}


	}

	/**
	 * Returns the player who is the only logical player who can refute suggestion.
	 * i.e skips over players who can't refute clockwise
	 * 
	 * @return player to refute. null if no-one can refute
	 * @throws InvalidMoveException if not in refute state
	 */
	public Player getRefuter() throws InvalidMoveException {
		if (!refuteMode) {
			throw new InvalidMoveException("There's no suggestion to refute!");
		}

		//perform logic to get refuter
		//iterate through players in player queue

		//if the player has no cards contained in the suggestion/ accusation move to next
		//if no players can refute, return null, and refuteMode is set to false

		//if a player does have any card contained in accusation, return that player
		//return that player



		//assume here there is no refuter
		//change variable and return null
		refuteMode = false;

		return null;
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
	 * 7 - someone has to refute
	 * 
	 * @return
	 */
	public int getGameStatus(){
		if (!inRoom && !hasRolled){return 1;}
		if (hasRolled && stepsRemaining > 0){return 2;}
		if (inRoom && !hasRolled){return 3;}
		if (currentPlayer.inCornerRoom() && !hasRolled){return 4;}  // <--- should we let people move if they have rolled, but haven't moved?
		if (turnFinished){return 5;}
		if (toRefute != null){return 7;}
		if (hasRolled && !turnFinished && currentPlayer.onIntrigueSquare()){return 6;}

		else return -1;


	}

}
