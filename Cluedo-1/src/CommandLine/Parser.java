package CommandLine;

import java.util.List;

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

	public enum Command {
		RollDice,
		MoveTowards,
		PrintNotepad,
		MakeSuggestion,
		SelectCard,
		SecretPassage,
		EndTurn,
		PrintActions,
		PrintCards,
		PrintLocations,
		PrintStatus,
		Help,
	}

	/**
	 * Returns the enum that corresponds to the entered command
	 * @param str their command string
	 * @return the enum, or null if nothing matches
	 */
	public Command getCommand(String str) {
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
			return Command.PrintStatus;
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

		//they entered nothing that matches, so return null
		else return null;
	}


	//Need to figure out our class type for how we deal with suggestions and moving
	//don't want to parse until we have proper object to store as

	/**
	 * Parses given string and returns a list of Card.
	 * 
	 * list.get(0) is the Character of the accusation.
	 * list.get(1) is the Weapon of the accusation.
	 * list.get(2) is the optional location of the (final) accusation.
	 * 
	 * Returns null if parse fails.
	 * 
	 * @param str
	 * @return
	 */
	public Weapon parseWeapon(String str) {
		str = str.toLowerCase();

		for (Weapon w: Weapon.values()) {
			if (str.contains(w.toString())) return w;
		}

		return null;
	}

	/**
	 * Checks the given string for the occurrence of a room, and returns that room.
	 * The string only has to contain the given string in any location to return it.
	 * 
	 * e.g "move hall" -> Hall, "pool room" -> PoolRoom, "dshadsjka dsa Theatre sa" -> Theatre
	 * 
	 * @param str
	 * @return
	 */
	public Room parseRoom(String str) {
		str = str.toLowerCase();

		for (Room r: Room.values()) {
			if (str.contains(r.toString())) return r;
		}

		return null;
	}


	public Character parseCharacter(String str) {
		str = str.toLowerCase();

		for (Character c: Character.values()) {
			if (str.contains(c.toString())) return c;
		}

		return null;
	}
}
