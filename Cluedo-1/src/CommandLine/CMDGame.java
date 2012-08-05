package CommandLine;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import CluedoGame.CluedoGame;
import CluedoGame.InvalidMoveException;
import CluedoGame.Player;
import CluedoGame.Character;
import CluedoGame.Room;
import CluedoGame.Weapon;
import CluedoGame.Board.RoomCell;
import CommandLine.Parser.Command;


public class CMDGame {

	//player currently taking turn
	private Player player;

	//the current game object
	private CluedoGame game;

	//the parser object 
	private Parser parser;

	public CMDGame() {
		System.out.println("Welcome to Cluedo!\n");

		//construct the game object
		game = new CluedoGame(getNumberPlayers());

		System.out.println("Starting a new game of Cluedo!");

		//play a real game now
		startGame();
	}


	/**
	 * Plays a command line based game of cluedo given the current game object.
	 * 
	 * @param game the game we wish to play
	 */
	public void startGame() {
		Scanner scan = new Scanner(System.in);
		parser = new Parser();

		//the string the user types each prompt
		String commandStr = "";
		//the command extracted from their typed string
		Command command;


		//iterate while game hasn't ended
		while (!game.isGameOver()) {

			//get the player
			player = game.getNextPlayer();


			//iterate while they haven't ended their turn
			//			while (game.isTurn(player)) {
			while (true) {

				commandStr = scan.nextLine();

				command = parser.getCommand(commandStr);

				switch (command) {
				case EndTurn:
					doEndTurn();
					break;
				case MakeSuggestion:
					doMakeSuggestion(commandStr);
					break;
				case MoveTowards:
					doMoveTowards(commandStr);
					break;
				case RollDice:
					doRoll();
					break;
				case SecretPassage:
					doMoveSecretPassage();
					break;
				case PrintStatus:
					printStatus(player);
					break;
				case PrintActions:
					printActions(player);
					break;
				case PrintCards:
					printCards(player);
					break;
				case PrintLocations:
					printLocations(player);
					break;
				case PrintNotepad:
					printNotepad(player);
					break;
				case Help:
					printHelp();
					break;
				case Undefined:
				default:
					System.out.println("You entered an invalid command.");
					System.out.println("Use [help] or [print commands] for information.");
					break;

				}


			}
		}

	}





	//following methods called on player may either be implemented by Player or by CluedoGame
	//We'll need to discuss that once we get to coding that a bit more


	/**
	 * Attempts to make a suggestion/ accusation.
	 * Method automatically handles optional third room param when accusing from pool-room.
	 * @param suggestion string to be parsed with suggestion params
	 */
	private void doMakeSuggestion(String suggestion) {
		Character chara = parser.parseCharacter(suggestion);
		Weapon weapon = parser.parseWeapon(suggestion);
		Room room = parser.parseRoom(suggestion);


		try {
			game.makeSuggestion(chara, weapon, room);

			//will need logic here to iterate over players to allow refute
		} catch (InvalidMoveException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Attempts to move towards a room on the map.
	 * @param location a string 
	 */
	private void doMoveTowards(String roomStr) {
		//first parse the locations given by string
		Room room = parser.parseRoom(roomStr);
		
		if (room == null) {
			System.out.println("You entered an invalid room.");
		} else {
			System.out.println("Moving towards " + room.toString());
		}
		
		try {
			game.moveTowards(room);
			System.out.println("You moved...");	
			//will need to refine their move info; steps taken, how far from location, etc
		} catch (InvalidMoveException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Attempts to roll the dice for the current player.
	 */
	private void doRoll() {		
		try {
			int roll = game.rollDice();
			System.out.println("You rolled a " + roll);
		} catch (InvalidMoveException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Attempts to move through secret passage.
	 */
	private void doMoveSecretPassage() {
		try {
			game.moveSecretPassage();
			System.out.println("You move to...");
		} catch (InvalidMoveException e) {
			System.out.println(e.getMessage());
		}
	}


	/**
	 * Attempts to end the current players turn.
	 */
	private void doEndTurn() {
		try {
			game.endTurn();
			System.out.println("You end your turn.");
		} catch (InvalidMoveException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Prints the given players notepad.
	 * @param player
	 */
	private void printNotepad(Player player) {
		//need to implement notebook
	}

	/**
	 * Prints the actions the user can currently do on their turn
	 * Includes:
	 * 	-	roll dice
	 * 	-	move towards location
	 * 	-	make accusation/ final accusation
	 * 	-	move to corner room
	 * 	-	end turn
	 * @param player
	 */
	private void printActions(Player player) {
		//all references to player may be done through game instead.
		//just provisional planning. All that matters is the functionality/ info from this
		//classes point of view, not where it gets it from.

		//this method may need simplification by performing logic in CluedoGame

		//		if (!player.hasRolled()) {
		//			System.out.println("Roll dice");
		//		}
		//
		//		if (player.stepsLeft() > 0 && !hasEnteredRoom()) {
		//			System.out.println("Move towards a location");
		//		}
		//
		//		if (player.inRoom() && !player.hasMadeSuggestion()) {
		//			if (player.inRoom(PoolRoom) {
		//				System.out.println("Make final accusation");
		//			} else {
		//				System.out.println("Make suggestion");
		//			}
		//		}
		//
		//		//if in a corner room, you're allowed to move through secret passage
		//		if (player.inCornerRoom() && !player.hasEnteredRoom()) {
		//			System.out.println("Move through secret passage");
		//		}

		//need to get conditions for being able to end a turn prematurely
		System.out.println("End turn");
	}

	/**
	 * Prints the locations and number of optimal steps relative to given player.
	 * @param player
	 */
	private void printLocations(Player player) {
		System.out.println("Locations:");

		Map<RoomCell, Integer> rooms = game.getRoomSteps(player);

		for (RoomCell c: rooms.keySet()) {
			System.out.println("\t" + c.getRoom() + ", " + rooms.get(c) + " steps away."); 
		}
	}

	/**
	 * Prints the players status, such as what room they're in, # steps left, etc.
	 * @param player player we are printing from
	 */
	private void printStatus(Player player) {
		//will write this method once we have a better idea of params to print.
	}

	/**
	 * Prints the given players cards.
	 * @param player the player we are printing from
	 */
	private void printCards(Player player) {
		System.out.println("You have:");
		//for (Card c: player.getCards())	System.out.println("\t" + c);
	}

	/**
	 * Prints a generic help message listing all commands.
	 */
	private void printHelp() {
		System.out.println("Enter following commands to progress through game:");
		System.out.println();
		System.out.println("roll dice\t-\trolls the dice.");
		System.out.println("move towards [location]\t-\tmoves the player towards [location]");
		System.out.println("get notepad\t-\tdisplays the notepad to help solve the murder");
		System.out.println("make suggestion [character] [weapon] [room*]\t-\tmakes a suggestion" +
				"or accusation. [room] must be specified when accusing from pool room.");
		System.out.println("select card [card]\t-\tused to refute a murder suggestion");
		System.out.println("secret passage\t-\tmoves the player through the secret passage");
		System.out.println("end turn\t-\tends the current players turn");
		System.out.println("print status\t-\tprints the current players status, their room, etc");
		System.out.println("help\t-\tdisplays this help message");
	}



	/**
	 * Prompts the users and returns a number between 3 - 6 inclusive.
	 * Used when instantiating the CluedoGame.
	 * 
	 * @return number of players
	 */
	private int getNumberPlayers() {
		Scanner scan = new Scanner(System.in);
		int number = 0;

		System.out.println("How many people want to play?");

		do {	
			System.out.println("Number must be between 3 and 6 inclusive.");

			try {
				number = scan.nextInt();
			} catch (InputMismatchException e) {
				//default to a value which will cause loop to iterate again
				number = 0;
				//get rid of scanner data so it doesn't block
				scan.skip(".*");
			}
		} while (number < 3 || number > 6);

		//scan.close();
		return number;
	}



	public static void main(String[] args) {
		new CMDGame();
	}
}
