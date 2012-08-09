package CluedoGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

	//all players in game. Useful for iterating over to get refuter since dead players
	//still have to refute if they can
	private List<Player> allPlayers;

	//players currently playing the game
	private Queue<Player> livePlayers;

	//players who made incorrect accusation and lost
	private List<Player> deadPlayers;

	//character who is taking current turn
	private Player currentPlayer;

	//current overall game state. True if a correct murder accusation or only 1 player left
	private boolean gameFinished;

	//current turn takers variables.
	private boolean hasRolled, hasSuggested, hasUsedSecretPassage, hasEnteredRoom, turnFinished;
	private int stepsRemaining;

	//refute player and if the game is currently in the 'refute' state
	private Player toRefute;
	private boolean refuteMode;
	private List<Card> suggestion;

	//solution contains 3 items. Do as a list for easy access to list.contains()
	private List<Card> solution;
	
	//once game is over, winner is stored here
	private Player winner;

	//the current board
	private Board board;


	/**
	 * Creates a CluedoGame object with the number of players.
	 * Players are assigned at random.
	 * 
	 * @param numPlayers number of players for this game
	 */
	public CluedoGame(int numPlayers) {
		if (numPlayers < 3 || numPlayers > 6) {
			// will have to do something here
		}

		//generate the players
		initialisePlayers(numPlayers);

		//generate board
		board = new Board(allPlayers);

		//initialise variables
		hasRolled = hasSuggested = hasEnteredRoom = hasUsedSecretPassage = false;
		gameFinished = refuteMode = turnFinished = false;
		stepsRemaining = 0;
		toRefute = null;
		suggestion = new ArrayList<Card>();

		//generate the murderer/ weapon/ location
		Random r = new Random();
		solution = new ArrayList<Card>();

		//generates a random number in range, then accesses from the array
		solution.add(Character.values()[r.nextInt(Character.values().length)]);
		solution.add(Weapon.values()[r.nextInt(Weapon.values().length)]);
		//must subtract 2 from room because of swimming pool and hallway
		solution.add(Room.values()[r.nextInt(Room.values().length - 2)]);

		//poll head of players as currentPlayer
		currentPlayer = livePlayers.poll();
	}

	/**
	 * Generates the players and stores them in allPlayers and livePlayers ready for play.
	 * Should only be called once by the constructor.
	 * 
	 * @param num the number of players we want to generate
	 */
	private void initialisePlayers(int num) {
		allPlayers = new ArrayList<Player>();
		livePlayers = new LinkedList<Player>();
		deadPlayers = new ArrayList<Player>();

		//the lists used to allocate players their cards
		List<Character> charas = new ArrayList<Character>();
		List<Weapon> weapons = new ArrayList<Weapon>();
		List<Room> rooms = new ArrayList<Room>();

		//the arraylist to store the players actual character
		List<Character> playerCharas = new ArrayList<Character>();

		//add values to the lists
		for (Character c: Character.values()) charas.add(c);
		for (Weapon w: Weapon.values()) weapons.add(w);
		for (Room r: Room.values()) rooms.add(r);

		//need to separately remove these since the murder cannot happen in either location
		rooms.remove(Room.SwimmingPool);
		rooms.remove(Room.Corridor);

		//first we allocate the players main character
		Collections.shuffle(charas);
		for (int i = 0; i < num; i++) {
			playerCharas.add(charas.get(i));
		}

		//shuffle the lists so it is random
		Collections.shuffle(weapons);
		Collections.shuffle(rooms);	
		Collections.shuffle(charas);

		//now we generate the player with their range of cards
		for (int i = 0; i < num; i++) {
			//should be fair, but doesn't equally distribute because of int rounding
			//can fix this later. Only unfair with 6 players
			//all cards are distrubuted though
			Player p = new Player(playerCharas.get(i),
					charas.subList(	i * charas.size()  / num, 	(i + 1) * charas.size()  / num),
					weapons.subList(i * weapons.size() / num, 	(i + 1) * weapons.size() / num),
					rooms.subList(	i * rooms.size()   / num, 	(i + 1) * rooms.size()   / num));
			//then add it to our two collections;
			allPlayers.add(p);
			livePlayers.add(p);
		}

		//to test players are being alocated cards properly
		//		for (Player p: allPlayers) {
		//			System.out.println("\n" + p + " has:");
		//			for (Card c: p.getCards()) {
		//				System.out.println("\t" + c);
		//			}
		//		}
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
		return livePlayers.size() == 1 || gameFinished;
	}

	/**
	 * Returns the winner of the game, or null if the game is still in progress.
	 * 
	 * @return the winner, or null
	 */
	public Player getWinner() {
		return winner;
	}
	
	/**
	 * Returns true if it is currently the given players turn.
	 * @param player
	 * @return 
	 */
	public boolean isTurn(Player player) {
		return currentPlayer != null && currentPlayer.equals(player);
	}

	/**
	 * Returns true if a player currently needs to refute a suggestion.
	 * @return
	 */
	public boolean isRefuting() {
		return refuteMode;
	}

	/**
	 * Returns the current player in the game turn cycle.
	 * If the last player has ended their turn, this advances to the next player.
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
	public Map<RoomCell, Integer> getRoomSteps(Player player) throws InvalidMoveException {
		if (gameFinished) {
			throw new InvalidMoveException("Cannot get best paths after game is finished!");
		} else if (!livePlayers.contains(player)) {
			throw new InvalidMoveException("Cannot get best paths for a dead player!");
		}

		//should be okay to return best path now
		//return board.getBestPathToAll(player);
		return null;	//filler until the method works
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
		if (gameFinished) {
			throw new InvalidMoveException("Cannot end turn after game is finished!");
		} else if (refuteMode){
			throw new InvalidMoveException("Cannot end turn while players are refuting!");
		} else if (turnFinished) {
			throw new InvalidMoveException("Turn is already over!");
		}
		//		else if (!hasRolled) {
		//			throw new InvalidMoveException(currentPlayer + " isn't allowed to end turn yet.");
		//		} else 

		//set variables back to default for new turn
		reinitializeNewTurn();

		//exchange players
		livePlayers.offer(currentPlayer);
		currentPlayer = livePlayers.poll();
	}


	/**
	 * Rolls the dice for the current player, returning the value they rolled.
	 * This game uses 2 dice, so value is between 2-12.
	 * @return
	 * @throws InvalidMoveException
	 */
	public int rollDice() throws InvalidMoveException {
		if (gameFinished) {
			throw new InvalidMoveException("Cannot roll after game is finished!");
		} if (turnFinished) {
			throw new InvalidMoveException("Cannot roll after ending turn!");
		} else if (hasRolled) {	
			throw new InvalidMoveException(currentPlayer + " has already rolled this turn");
		} else if (refuteMode) {
			throw new InvalidMoveException("Cannot roll while refuting!");
		} else if (hasSuggested) {
			throw new InvalidMoveException("Cannot roll after making a suggestion!");
		}

		//should be good to roll now
		Random random = new Random();
		for (int i = 0; i < 2; i++) {			//maybe a static int for # of dice
			stepsRemaining += random.nextInt(6) + 1;
		}

		//update variables
		hasRolled = true;

		return stepsRemaining;
	}


	public void makeSuggestion(Character chara, Weapon weapon) throws InvalidMoveException{
		if (gameFinished) {
			throw new InvalidMoveException("Cannot make a suggestion once game is finished!");
		} else if (hasSuggested) {
			throw new InvalidMoveException("You have already suggested this turn!");
			//		} else if (board.inRoom(currentPlayer)) {
			//			throw new InvalidMoveException("Must be in a room to suggest!");
		} else if (refuteMode) {
			throw new InvalidMoveException("Cannot make suggestion while refuting!");
		} else if (chara == null || weapon == null) {
			throw new InvalidMoveException("Suggestion was invalid! (null)");
		}

		//move the suggested player to that room
		//board.move
		
		//clear the current suggestion, then put in new suggestion
		suggestion.clear();
		suggestion.add(chara);
		suggestion.add(weapon);
//				suggestion.add(currrentPlayer.getRoom());

		//update variables
		hasSuggested = true;
		refuteMode = true;
	}


	public boolean makeAccusation(Character chara, Weapon weapon, Room room) throws InvalidMoveException {
		if (gameFinished) {
			throw new InvalidMoveException("Cannot make an accusation once game is finished!");
		} else if (deadPlayers.contains(currentPlayer)) {
			throw new InvalidMoveException("You have already accused this turn!");
			//		} else if (currentPlayer.inPoolRoom()) {
			//			throw new InvalidMoveException("Must be in pool-room to accuse!");
		} else if (refuteMode) {
			throw new InvalidMoveException("Cannot make accusation while refuting!");
		} else if (chara == null || weapon == null || room == null) {
			throw new InvalidMoveException("Made an invalid accusation! (null)");
		}

		
		//now we check their guess
		if (solution.contains(chara) && solution.contains(weapon) && solution.contains(room)) {
			//they guessed correctly. Update winner var and gameFinished var
			gameFinished = true;
			winner = currentPlayer;
			
			return true;
		} else {
			//they made incorrect accusation
			
			//add losing player to lose list
			deadPlayers.add(currentPlayer);
			
			if (livePlayers.size() == 1) {
				//last player wins be default since only one left
				gameFinished = true;
				winner = livePlayers.poll();
			} else {
				//game continues
				
				//get current player
				currentPlayer = livePlayers.poll();
				//reinitialize variables so they can take turn
				reinitializeNewTurn();
			}
		}
		return false;
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
		List<Square> path = board.getBestPathTo(currentPlayer, room);

		//then move as many steps as the player has left to that location
		for (Square s: path) {
			if (stepsRemaining == 0) break;

			//do move here
			board.setPlayerPosition(currentPlayer, s);

			stepsRemaining--;
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
			throw new InvalidMoveException("Cannot move once the game is over!");
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
		//		try {
		//			board.moveSecretPassage(currentPlayer);
		//		} catch (InvalidMoveException e) {
		//			//should never happen. If it does, we crash the game with an informative message
		//			throw new Error("Internal game errror.\n" + 
		//					currentPlayer.getCharacter().getName() + 
		//					" should have been able to go through secret passage from " +
		//					currentPlayer.getPosition().toString());
		//		}

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
		} else if (!suggestion.contains(card)) {
			throw new InvalidMoveException("Card was not part of the suggestion!");
		} else if (!toRefute.hasCard(card)) {
			throw new InvalidMoveException("Refuter doesn't have that card!");
		}

		//should be good at this point


		refuteMode = false;
		toRefute = null;
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
		//iterate through all players in allPlayers collection (not just live players!)

		int index = allPlayers.indexOf(currentPlayer);

		//loop performs a standard loop around the entire list 
		//the % just makes it wrap around when the index goes past 
		//it gets the player just after currentPlayer, then the next, all around till back at currentPlayer
		for (int i = (index + 1) % allPlayers.size(); i != index; i = (i + 1) % allPlayers.size()) {
			for (Card c: suggestion) {
				if (allPlayers.get(i).hasCard(c)) {
					//the player has the given card!
					toRefute = allPlayers.get(i);
					refuteMode = true;

					return toRefute;
				}
			}
		}

		//there is nobody who can refute, so set variables to false/ null, then return null
		refuteMode = false;
		toRefute = null;

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
		//		if (!inRoom && !hasRolled){return 1;}
		if (hasRolled && stepsRemaining > 0){return 2;}
		//		if (inRoom && !hasRolled){return 3;}
		if (currentPlayer.inCornerRoom() && !hasRolled){return 4;}  // <--- should we let people move if they have rolled, but haven't moved?
		if (turnFinished){return 5;}
		//if (hasRolled && !turnFinished && )  
		if (toRefute != null){return 7;}
		if (hasRolled && !turnFinished && currentPlayer.onIntrigueSquare()){return 6;}

		else return -1;
	}
	
	/**
	 * Method resets variables relating to an individual location back to default.
	 * Called from endTurn() after a succesfull endTurn() call, and makeAccusation() 
	 * when a player gives an incorrect accusation but the game continues.
	 */
	private void reinitializeNewTurn() {
		//reset variables
		turnFinished = hasUsedSecretPassage = hasRolled = hasSuggested = hasEnteredRoom = false;
		stepsRemaining = 0;
	}
}
