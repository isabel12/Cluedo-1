package CommandLine;

import java.util.InputMismatchException;
import java.util.Scanner;

import CluedoGame.CluedoGame;
import CluedoGame.Player;
import CommandLine.Parser.Command;


public class CMDGame {




	public static void main(String[] args) {
		System.out.println("Welcome to Cluedo!\n");

		//construct the game object
		CluedoGame game = new CluedoGame(getNumberPlayers());

		System.out.println("Starting a new game of Cluedo!");

		//play a real game now
		startGame(game);
	}


	/**
	 * Plays a command line based game of cluedo given the current game object.
	 * 
	 * @param game the game we wish to play
	 */
	public static void startGame(CluedoGame game) {
		Scanner scan = new Scanner(System.in);
		Parser parser = new Parser();
		String commandStr;
		Player player;
		Command command;
		// lots of game logic here

		while (game.hasNotEnded()) {

			//get the player whos turn it is
			player.getCurrentPlayer();


			//iterate while they haven't ended their turn
			while (game.isTurn(player)) {
				commandStr = scan.nextLine();
				command = parser.getCommand(commandStr);

				switch (command) {
				case Commands:
					break;
				case EndTurn:
					break;
				case GameStatus:
					break;
				case GetNotepad:
					break;
				case MakeSuggestion:
					break;
				case MoveTowards:
					break;
				case PrintCards:
					break;
				case RollDice:
					break;
				case SecretPassage:
					//check they are in corner room
					//check they haven't already 
					break;
				case SelectCard:
					break;
				default:
					break;

				}


			}
		}

	}


	/**
	 * Prompts the users and returns a number between 3 - 6 inclusive.
	 * 
	 * @return number of players
	 */
	private static int getNumberPlayers() {
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

		scan.close();
		return number;
	}


	//following methods called on player may either be implemented by Player or by CluedoGame
	//We'll need to discuss that once we get to coding that a bit more
	
	
	/**
	 * Prints current information about player.
	 * Includes:
	 * 	-	name
	 * 	-	current location
	 * 	-	can/ cannot move + #steps remaining
	 * 	-	
	 * @param player
	 */
	private static void printInformation(Player player) {
		System.out.println(player.getName() + "'s turn.");

		System.out.println("Currently in " + player.location());

	}

	/**
	 * Prints the actions the user can currently do in their turn
	 * Includes:
	 * 	-	roll dice
	 * 	-	move towards location
	 * 	-	make accusation/ final accusation
	 * 	-	move to corner room
	 * 	-	end turn
	 * @param player
	 */
	private static void printActions(Player player) {
		//all references to player may be done through game instead.
		//just provisional planning. All that matters is the functionality/ info from this
		//classes point of view, not where it gets it from.
		//
		if (!player.hasRolled()) {
			System.out.println("Roll dice");
		}

		if (player.stepsLeft() > 0 && !hasEnteredRoom()) {
			System.out.println("Move towards a location");
		}

		if (player.inRoom() && !player.hasMadeSuggestion()) {
			if (player.inRoom(PoolRoom) {
				System.out.println("Make final accusation");
			} else {
				System.out.println("Make suggestion");
			}
		}

		//if in a corner room, you're allowed to move through secret passage
		if (player.inCornerRoom() && !player.hasEnteredRoom()) {
			System.out.println("Move through secret passage");
		}

		//need to get conditions for being able to end a turn prematurely
		System.out.println("End turn");
	}

}
