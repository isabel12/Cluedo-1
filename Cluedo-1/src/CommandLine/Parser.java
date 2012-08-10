package CommandLine;

import java.util.Scanner;

import CluedoGame.Card;
import CluedoGame.Character;
import CluedoGame.Room;
import CluedoGame.Weapon;


/**
 * Parses command line arguments and returns types of correct value.
 *
 * @author Troy 
 *
 */
public class Parser {

	/**
	 * Enum for different actions a user can take each turn.
	 * @author Troy Shaw
	 */
	public enum Command {
		RollDice,
		MoveTowards,
		PrintNotepad,
		MakeAccusation,
		MakeSuggestion,
		SelectCard,
		SecretPassage,
		EndTurn,
		PrintActions,
		PrintCards,
		PrintLocations,
		PrintStatus,
		Help,
		Undefined
	}

	/**
	 * Returns the enum that corresponds to the entered command
	 * @param str their command string
	 * @return the enum, or null if nothing matches
	 */
	public Command parseCommand(String str) {
		str = str.toLowerCase();

		if (str.startsWith("roll dice")) {
			return Command.RollDice;
		} 

		else if (str.startsWith("move towards")) {
			return Command.MoveTowards;
		} 

		else if (str.startsWith("print notepad")) {
			return Command.PrintNotepad;
		} 

		else if (str.startsWith("make suggestion")) {
			return Command.MakeSuggestion;
		} 

		else if (str.startsWith("make accusation")) {
			return Command.MakeAccusation;
		}

		else if (str.startsWith("select card")) {
			return Command.SelectCard;
		} 

		else if (str.startsWith("secret passage")) {
			return Command.SecretPassage;
		}

		else if (str.startsWith("end turn")) {
			return Command.EndTurn;
		}

		else if (str.startsWith("print cards")) {
			return Command.PrintCards;
		} 

		else if (str.startsWith("print locations")) {
			return Command.PrintLocations;
		}

		else if (str.startsWith("print status")) {
			return Command.PrintStatus;
		}

		else if (str.startsWith("print actions")) {
			return Command.PrintActions;
		}

		else if (str.startsWith("help")) {
			return Command.Help;
		}

		//they entered nothing that matches, so return undefined
		else return Command.Undefined;
	}


	/**
	 * Checks the given string for the occurrence of a weapon name, and returns that Weapon enum.
	 * The string only has to contain the given string in any location to return it.
	 *  
	 * @param str
	 * @return the enum for the Weapon, or null if not in string
	 */
	public Weapon parseWeapon(String str) {
		for (Weapon w: Weapon.values()) {
			if (matches(str, w.toString())) return w;
		}

		return null;
	}

	/**
	 * Checks the given string for the occurrence of a room, and returns that Room enum.
	 * The string only has to contain the given string in any location to return it.
	 * 
	 * @param str
	 * @return the enum for the Room, or null if not in string
	 */
	public Room parseRoom(String str) {
		for (Room r: Room.values()) {
			//we need to remove "room" from the given room because it matches a lot of rooms
			//we only search for things that uniquely identify the room (e.g "dining")
			//since we are searching for substrings
			String rStr = r.toString().toLowerCase();
			if (rStr.contains(" room")) rStr = rStr.split(" ")[0];

			if (matches(str, rStr)) return r;
		}

		return null;
	}

	/**
	 * Checks the given string for the occurrence of a character, and returns that Character.
	 * The string only has to contain the given string in any location to return it.
	 * 
	 * @param str
	 * @return the enum for the Character, or null if not in string
	 */
	public Character parseCharacter(String str) {
		for (Character c: Character.values()) {
			if (matches(str, c.toString())) return c;
		}

		return null;
	}

	/**
	 * Method parses the given string and returns whatever card it contains.
	 * Works the same as calling each individual parse method, and returning a match.
	 * Returns null if the given string doesn't contain any valid card name.
	 * 
	 * @param str
	 * @return a Card of the given name, or null
	 */
	public Card parseCard(String str) {
		Card card = parseCharacter(str);

		//must check for null and call parsing method again for each type

		if (card == null) {
			card = parseWeapon(str);
		}

		if (card == null) {
			card = parseRoom(str);
		}

		//if nothing matches, card will be null here

		return card;
	}

	/**
	 * Checks if any of the individual words in match is contained in string, disregarding case
	 * Eg string = scarlett, match = "Kasandra Scarlet" would return true.
	 * 
	 * Method is helpful to make the game easier to interact with through command line.
	 * 
	 * @param string
	 * @param match
	 * @return
	 */
	private boolean matches(String string, String match) {
		string = string.toLowerCase();
		match = match.toLowerCase();

		for (String s: match.split(" ")) {
			if (string.contains(s)) return true;
		}

		return false;
	}
}