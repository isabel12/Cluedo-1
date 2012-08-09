package CommandLine;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import CluedoGame.Card;
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
		//construct the game object
		System.out.println("Welcome to Cluedo!\n");
		game = new CluedoGame(getNumberPlayers());

		//play a real game now
		System.out.println("Starting a new game of Cluedo!");
		sleep(1000);
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
			player = game.getCurrentPlayer();

			// get the game state, and print message
			System.out.println(player + "'s turn!");
			sleep(1000);

			//iterate while they haven't ended their turn
			while (game.isTurn(player)) {
				System.out.println("What will " + player + " do? ([help] to print commands) ");

				commandStr = scan.nextLine();
				command = parser.getCommand(commandStr);

				switch (command) {
				case EndTurn:
					doEndTurn();
					break;
				case MakeAccusation:
					doMakeAccusation(commandStr);
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
		//game has finished
		
		System.out.println("Congratulations " + game.getWinner() + "!");
		System.out.println("You have won this game of Cluedo!");
	}





	//following methods called on player may either be implemented by Player or by CluedoGame
	//We'll need to discuss that once we get to coding that a bit more

	/**
	 * Attempts to make a final accusation. If this is wrong, the accusing player
	 * is eliminated from the game.
	 * @param accusation
	 */
	private void doMakeAccusation(String accusation) {
		Character chara = parser.parseCharacter(accusation);
		Weapon weapon = parser.parseWeapon(accusation);
		Room room = parser.parseRoom(accusation);

		try {
			boolean success = game.makeAccusation(chara, weapon, room);

			if (success) {
				System.out.println("Wow! You won!");
			} else {
				System.out.println("Oh no! \nYour accusation was wrong and now you're dead!");
			}

		} catch (InvalidMoveException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Attempts to make a suggestion
	 * 
	 * @param suggestion string to be parsed with suggestion params
	 */
	private void doMakeSuggestion(String suggestion) {
		Character chara = parser.parseCharacter(suggestion);
		Weapon weapon = parser.parseWeapon(suggestion);

		try {
			game.makeSuggestion(chara, weapon);

			//get refuting player
			Player refuter = game.getRefuter();

			if (refuter != null) {
				System.out.println(refuter + " must refute!");

				String commandStr;
				Scanner scan = new Scanner(System.in);
				
				Card refuteCard = null;

				//loops until the refuter gives a card that refutes
				while (game.isRefuting()) {
					commandStr = scan.nextLine();

					//we allow the refuter to print their current cards for reference
					if (commandStr.toLowerCase().contains("print cards")) {
						printCards(refuter);
					} else {
						refuteCard = parser.parseCard(commandStr);

						try {
							game.refuteSuggestion(refuteCard);
						} catch (InvalidMoveException e) {
							//this error deals with the player gives wrong card, etc
							System.out.println(e.getMessage());
						}
					}
				}
				
				//player has successfully refuted with refuteCard
				System.out.println(refuter + " successfully refuted with " + refuteCard);
				sleep(1000);
			} else {
				//the refuter was null which means nobody can refute
				System.out.println("Nobody could refute that!");
				sleep(1000);
			}


		} catch (InvalidMoveException e) {
			//this error deals with a player trying to refute when there isn't anything to refute
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Attempts to move towards a room on the map.
	 * @param location a string 
	 */
	private void doMoveTowards(String roomStr) {
		try {
			Room room = parser.parseRoom(roomStr);
			game.moveTowards(room);		
			System.out.println("Moving towards " + room.toString());
			sleep(1500);
			//not actually true. Need to get current location.
			System.out.println("You moved to " + room.toString());
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
			System.out.println("You rolled " + roll + "!");
			sleep(500);

			if (roll == 12){
				System.out.println("Wow! You rolled perfect!");
			} else if (roll > 8) {
				System.out.println("Nice roll!");
			} else if (roll > 4) {
				System.out.println("Not bad!");
			} else if (roll > 2) {
				System.out.println("Aww...");
			} else if (roll == 2) {
				System.out.println("Bad luck! Snake-eyes!");
			}
			sleep(500);
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
			System.out.println(player + " ends their turn.");
			sleep(1000);
		} catch (InvalidMoveException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Prints the given players notepad.
	 * @param player
	 */
	private void printNotepad(Player player) {
		System.out.println(player + "'s notepad:");
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
		try {
			System.out.println(player + "'s Locations:");

			Map<RoomCell, Integer> rooms = game.getRoomSteps(player);

			for (RoomCell c: game.getRoomSteps(player).keySet()) {
				System.out.println("\t" + c.getRoom() + ", " + rooms.get(c) + " steps away."); 
			}
		} catch (InvalidMoveException e) {
			System.out.println(e.getMessage());
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
		System.out.println(player + " has:");
		for (Card c: player.getCards())	System.out.println("\t" + c);
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

	private void sleep(int sleepTimer) {
		try {
			Thread.sleep(sleepTimer);
		} catch (InterruptedException e) {
			//if it fails, there is no delay between CMD prompts; no biggie
			//just ignore
		}
	}


	public static void main(String[] args) {
		new CMDGame();
	}
}
