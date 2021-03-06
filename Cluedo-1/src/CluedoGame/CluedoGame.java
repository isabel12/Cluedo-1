package CluedoGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;


import CluedoGame.Board.Board;


/**
 * Represents a Cluedo game.
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
	private List<CluedoPlayer> allPlayers;
	private Map<Character, CluedoPlayer> playerRefs; // map from Character to CluedoPlayer for easy lookup

	//players currently playing the game
	private Queue<CluedoPlayer> livePlayers;

	//players who made incorrect accusation and lost
	private List<CluedoPlayer> deadPlayers;

	//character who is taking current turn
	private CluedoPlayer currentPlayer;

	//current overall game state. True if a correct murder accusation or only 1 player left
	private boolean gameFinished;

	//current turn takers variables.
	private boolean hasRolled, hasSuggested, hasUsedSecretPassage, hasEnteredRoom, turnFinished;
	private int stepsRemaining;

	//refute player and if the game is currently in the 'refute' state
	private CluedoPlayer toRefute;
	private boolean refuteMode;
	private List<Card> suggestion;

	//solution contains 3 items. Do as a list for easy access to list.contains()
	private List<Card> solution;

	//once game is over, winner is stored here
	private CluedoPlayer winner;



	//the current board
	private Board board;


	/**
	 * Creates a CluedoGame object with the number of players.
	 * Players are assigned at random.
	 * 
	 * @param numPlayers number of players for this game
	 * @throws Exception if player number is invalid
	 */
	public CluedoGame(int numPlayers) throws Exception {
		if (numPlayers < 3 || numPlayers > 6) {
			throw new Exception("Cannot make a board with that many players!");
		}

		//generate the players
		initialisePlayers(numPlayers);

		
		//generate board
		Set<Player> setPlayers = new HashSet<Player>();
		for (CluedoPlayer p: this.allPlayers){
			setPlayers.add((Player)p);
		}
		board = new Board(new HashSet<Player>(setPlayers));
		

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
		//must get room through method since enum has non-valid murder rooms
		solution.add(Room.getMurderRooms().get(r.nextInt(Room.getMurderRooms().size())));

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
		allPlayers = new ArrayList<CluedoPlayer>();
		playerRefs = new HashMap<Character, CluedoPlayer>();
		livePlayers = new LinkedList<CluedoPlayer>();
		deadPlayers = new ArrayList<CluedoPlayer>();

		//the lists used to allocate players their cards
		List<Character> charas = new ArrayList<Character>();
		List<Weapon> weapons = new ArrayList<Weapon>();
		List<Room> rooms = new ArrayList<Room>();

		//the arraylist to store the players actual character
		List<Character> playerCharas = new ArrayList<Character>();

		//add values to the lists
		for (Character c: Character.values()) charas.add(c);
		for (Weapon w: Weapon.values()) weapons.add(w);
		for (Room r: Room.getMurderRooms()) rooms.add(r);	//excludes PoolRoom/ Corridor/ intrigue


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
			CluedoPlayer p = new Player(playerCharas.get(i),
					charas.subList(	i * charas.size()  / num, 	(i + 1) * charas.size()  / num),
					weapons.subList(i * weapons.size() / num, 	(i + 1) * weapons.size() / num),
					rooms.subList(	i * rooms.size()   / num, 	(i + 1) * rooms.size()   / num));
			//then add it to our collections;
			allPlayers.add(p);
			livePlayers.add(p);
			playerRefs.put(p.getCharacter(),p);
		}
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
		return gameFinished;
	}

	/**
	 * Returns the winner of the game, or null if the game is still in progress.
	 * 
	 * @return the winner or null
	 */
	public Character getWinner() {
		if (winner == null){ return null;}
		
		return winner.getCharacter();
	}

	/**
	 * Returns true if it is currently the given players turn.
	 * @param player
	 * @return 
	 */
	public boolean isTurn(Character player) {
		return currentPlayer != null && currentPlayer.getCharacter().equals(player);
	}

	/**
	 * Returns true if a player currently needs to refute a suggestion.
	 * @return
	 */
	public boolean isRefuting() {
		return refuteMode;
	}

	/**
	 * Returns the current player in the game turn cycle, or null if the game is over.
	 * 
	 * @return
	 */
	public Character getCurrentPlayer() {
		return currentPlayer.getCharacter();
	}


	/**
	 * Returns the smallest number of steps to each room on the map.
	 * For rooms with multiple doors, only the smallest path is given.
	 * The value is -1 if the room is blocked and cannot be reached.
	 * 
	 * @param player player we want info for
	 * @return 
	 */
	public Map<Room, Integer> getRoomSteps(Character player) throws InvalidMoveException {
		if (gameFinished) {
			throw new InvalidMoveException("Cannot get best paths after game is finished!");
		} else if (deadPlayers.contains(playerRefs.get(player))) {	//use dead players since current player is polled from livePlayers
			throw new InvalidMoveException("Cannot get best paths for a dead player!");
		}

		//should be okay to return best path now
		return board.getDistanceToAllRooms(player);
	}

	/**
	 * Ends the current players turn.
	 * If the player cannot end their turn because they haven't rolled, moved, etc,
	 * this method throws InvalidMoveException.
	 * 
	 * @throws InvalidMoveException if the player isn't allowed to end their turn
	 */
	public void endTurn() throws InvalidMoveException {
		if (gameFinished) {
			throw new InvalidMoveException("Cannot end turn after game is finished!");
		} else if (refuteMode){
			throw new InvalidMoveException("Cannot end turn while players are refuting!");
		} else if (turnFinished) {
			throw new InvalidMoveException("Turn is already over!");
		}

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
		for (int i = 0; i < 2; i++) {
			stepsRemaining += random.nextInt(6) + 1;
		}

		//update variables
		hasRolled = true;

		return stepsRemaining;
	}


	/**
	 * Makes a suggestion with the given character and weapon.
	 * Causes the game to enter 'refute-mode' if any player can refute.
	 * Game stays in refute-mode until that player refutes with a valid card.
	 * 
	 * @param chara
	 * @param weapon
	 * @throws InvalidMoveException
	 */
	public void makeSuggestion(Character chara, Weapon weapon) throws InvalidMoveException{
		if (gameFinished) {
			throw new InvalidMoveException("Cannot make a suggestion once game is finished!");
		} else if (hasSuggested) {
			throw new InvalidMoveException("You have already suggested this turn!");
		} else if (!currentPlayer.inRoom()) {
			throw new InvalidMoveException("Must be in a room to suggest!");
		} else if (currentPlayer.inFinalRoom()) {
			throw new InvalidMoveException("Cannot suggest from the pool-room!");
		} else if (refuteMode) {
			throw new InvalidMoveException("Cannot make suggestion while refuting!");
		} else if (chara == null || weapon == null) {
			throw new InvalidMoveException("Suggestion was invalid! (null)");
		}

		//move the suggested player to that room
		board.summonCharacter(chara, currentPlayer.getRoom());

		//clear the current suggestion, then put in new suggestion
		suggestion.clear();
		suggestion.add(chara);
		suggestion.add(weapon);
		suggestion.add(currentPlayer.getRoom());

		//update variables
		hasSuggested = true;
		refuteMode = true;
	}


	/**
	 * Makes a final murder accusation.
	 * 
	 * If the accusation was wrong, the accusing player is now dead, and that players
	 * turn automatically ends.
	 * 
	 * Returns true if the accusation was correct, at which point the game ends.
	 * Returns false if the accusation was incorrect. 
	 * If there is only 1 player left after an incorrect accusation, the game ends,
	 * and the last player is the winner by default.
	 * 
	 * @param chara character being accused
	 * @param weapon weapon being accused
	 * @param room room being accused
	 * @return true if accusation was correct, false otherwise
	 * @throws InvalidMoveException
	 */
	public boolean makeAccusation(Character chara, Weapon weapon, Room room) throws InvalidMoveException {
		if (gameFinished) {
			throw new InvalidMoveException("Cannot make an accusation once game is finished!");
		} else if (deadPlayers.contains(currentPlayer)) {
			throw new InvalidMoveException("A dead player cannot make an accusation!");
		} else if (!currentPlayer.inFinalRoom()) {
			throw new InvalidMoveException("Must be in pool-room to accuse!");
		} else if (refuteMode) {
			throw new InvalidMoveException("Cannot make accusation while refuting a suggestion!");
		} else if (chara == null || weapon == null || room == null) {
			throw new InvalidMoveException("Made an invalid accusation! (null)");
		}

		//now we check their guess
		if (solution.contains(chara) && solution.contains(weapon) && solution.contains(room)) {
			//they guessed correctly. Update winner var and gameFinished var
			gameFinished = true;
			winner = currentPlayer;
			currentPlayer = null;

			return true;
		} else {
			//they made incorrect accusation
			//add losing player to lose list
			deadPlayers.add(currentPlayer);

			if (deadPlayers.size() == allPlayers.size() - 1) {
				//last player wins be default since only one left
				gameFinished = true;
				winner = livePlayers.poll();
				currentPlayer = null;

				return false;
			} else {
				//game continues
				//get current player
				currentPlayer = livePlayers.poll();
				//reinitialize variables so they can take turn
				reinitializeNewTurn();

				return false;
			}
		}
	}


	/**
	 * Draws an intrigue card to the current players hand.
	 * 
	 * @throws InvalidMoveException
	 */
	public void drawIntrigueCard() throws InvalidMoveException{
		if (!currentPlayer.onIntrigueSquare()) {
			throw new InvalidMoveException("Player isn't standing on an intrigue square!");
		} 

		throw new InvalidMoveException("Not currently implemented");

		// it will need to check if the game is over if the timeCards kill a player.
		//intrigue card logic here

		//update variables
	}


	/**
	 * Moves the player as close to (or inside if possible) the given location.
	 * Will find the most efficient path to the desired room.
	 * 
	 * Returns false if you didn't have any steps remaining to make it all the way.
	 * 
	 * @param room	location to move to
	 * @return true if you moved all the way into the location
	 * @throws InvalidMoveException if the move fails
	 */
	public boolean moveTowards(Room room) throws InvalidMoveException {
		if (gameFinished) {
			throw new InvalidMoveException("Cannot move once game is finished!");
		} else if (!hasRolled) {
			throw new InvalidMoveException("Roll before moving!");
		} else if (stepsRemaining <= 0) {
			throw new InvalidMoveException("You have no more steps remaining!");
		} else if (refuteMode) {
			throw new InvalidMoveException("Cannot move while refuting!");
		} else if (hasSuggested) {
			throw new InvalidMoveException("Cannot move after making a suggestion!");
		} else if (hasEnteredRoom) {
			throw new InvalidMoveException("Cannot move after entering a room!");
		} else if (room == null) {
			throw new InvalidMoveException("Room given was null");
		}

		//should be okay to move, so perform logic
		//find the shortest path to a door of the given room
		List<Square> path = board.getBestPathTo(currentPlayer.getCharacter(), room);

		//there was no valid path to the given room
		if (path.size() == 0) {
			throw new InvalidMoveException("That path is blocked!");
		}

		//does the player have enough steps left to make it the whole way?
		boolean completedMove = false;
		if (path.size() - 1 <= stepsRemaining) completedMove = true;

		//then move as many steps as the player has left to that location
		for (int i = 1; i < path.size() && stepsRemaining != 0; i++) {
			//move
			board.setPlayerPosition(currentPlayer.getCharacter(), path.get(i));
			stepsRemaining--;
		}

		//update variables
		if (completedMove && path.get(path.size() - 1).isRoom()) {
			//if end of path is a room and they must have entered a room
			hasEnteredRoom = true;
		}

		return completedMove;
	}

	/**
	 * Draws a representation of the board to System.out.
	 */
	public void printMap() {
		if (board != null) board.drawBoard();
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
		try {
			board.useSecretPassage(currentPlayer.getCharacter());
		} catch (IllegalArgumentException e) {
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
		if (gameFinished) {
			throw new InvalidMoveException("Cannot refute once game is finished!");
		} else if (!refuteMode) {
			throw new InvalidMoveException("There is no suggestion to refute!");
		} else if (!toRefute.hasCard(card)) {
			throw new InvalidMoveException("Refuter doesn't have that card!");
		} else if (!suggestion.contains(card)) {
			throw new InvalidMoveException("Card was not part of the suggestion!");
		}
		
		//update the players notepad so that the card is marked as not a suspect
		currentPlayer.getNotepad().put(card, true);
		
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
	public Character getRefuter() throws InvalidMoveException {
		if (!refuteMode) {
			throw new InvalidMoveException("There's no suggestion to refute!");
		}

		//perform logic to get refuter
		//iterate through all players in allPlayers collection (not just live players!)
		int index = allPlayers.indexOf(currentPlayer);

		//loop performs a standard loop around the entire list 
		//the % just makes it wrap around when the index goes past the end of list
		//it gets the player just after currentPlayer, then the next, all around till back at currentPlayer
		for (int i = (index + 1) % allPlayers.size(); i != index; i = (i + 1) % allPlayers.size()) {
			for (Card c: suggestion) {
				if (allPlayers.get(i).hasCard(c)) {
					//the player has the given card!
					toRefute = allPlayers.get(i);
					refuteMode = true;

					return toRefute.getCharacter();
				}
			}
		}

		//there is nobody who can refute, so set variables to false/ null, then return null
		refuteMode = false;
		toRefute = null;

		return null;
	}

	/**
	 * Returns the given player's notepad.
	 * @param player
	 * @return
	 */
	public Map<Card, Boolean> getNotepad(Character player) {
		return playerRefs.get(player).getNotepad();
	}
	
	/**
	 * Returns the cards of the given player.
	 * @param player
	 * @return
	 */
	public List<Card> getCards(Character player){
		return playerRefs.get(player).getCards();
	}
	
	/**
	 * Returns the position of the given character on the board
	 * @param chara
	 * @return
	 */
	public Square getPosition(Character chara){
		return playerRefs.get(chara).getPosition();
	}
	
	public enum Command {
		Roll,
		MakeAccusation,
		MakeSuggestion,
		Move,
		EnterSecretPassage,
		DrawIntrigue,
		Refute,
		EndTurn,
	}

	/**
	 * Returns the moves the current player can take.
	 * @return
	 */
	public List<Command> getCommands(){
		List<Command> toReturn = new ArrayList<Command>();

		if (gameFinished) return toReturn;
		if (refuteMode) {
			toReturn.add(Command.Refute);
			return toReturn;
		}
		if (!hasRolled) toReturn.add(Command.Roll);
		if (!hasSuggested && currentPlayer.inMurderRoom()) toReturn.add(Command.MakeSuggestion);
		if (!hasSuggested && currentPlayer.inFinalRoom()) toReturn.add(Command.MakeAccusation);
		if (!hasEnteredRoom && currentPlayer.inCornerRoom()) toReturn.add(Command.EnterSecretPassage);
		if (currentPlayer.onIntrigueSquare()) toReturn.add(Command.DrawIntrigue);
		if (stepsRemaining > 0) toReturn.add(Command.Move);
		toReturn.add(Command.EndTurn);

		return toReturn;
	}

	/**
	 * Resets the current turn takers variables so the next player can take their turn.
	 * Called from endTurn() after a succesfull endTurn() call, and makeAccusation() 
	 * when a player gives an incorrect accusation but the game continues.
	 */
	private void reinitializeNewTurn() {
		//reset variables
		turnFinished = hasUsedSecretPassage = hasRolled = hasSuggested = hasEnteredRoom = false;
		stepsRemaining = 0;
	}
}
