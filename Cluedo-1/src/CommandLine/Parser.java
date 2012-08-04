package CommandLine;

import java.util.List;

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
	public List<Card> parseSuggestion(String str) {
		return null;
	}
	
	/**
	 * Parses given string and returns the location it refers to.
	 * Returns null if location is invalid.
	 * @param str
	 * @return
	 */
	public Location parseLocation(String str) {
		str = str.toLowerCase();
		
		//gets rid of "move towards" at start of str, and removes whitespace
		if (str.startsWith("move towards")) {
			str = str.split("move towards")[1].trim();
		}
		
		
		
		
		return null;
	}
}
