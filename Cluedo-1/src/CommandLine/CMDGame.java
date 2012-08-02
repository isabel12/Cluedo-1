package CommandLine;

import java.util.Scanner;


public class CMDGame {

	public static void main(String[] args) {
		//first ask how many players we want
		
		System.out.println("Welcome to Cluedo!\n");
		System.out.println("How many players want to play?");
		
		Scanner scan = new Scanner(System.in);
		
		int numPlayers = scan.nextInt();
		
		//construct the game objects
		//CluedoGame game = new CluedoGame(numPlayers);
		
		System.out.println("Starting game with " + numPlayers + " players!");
		
	}
}
