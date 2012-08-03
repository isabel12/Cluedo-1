package CommandLine;

/**
 * Parses command line arguments and returns types of correct value.
 * @author Troy Shaw
 *
 */
public class Parser {

	public enum Command {
		RollDice,
		MoveTowards,
		PrintCards,
		GetNotepad,
		MakeSuggestion,
		SelectCard,
		SecretPassage,
		EndTurn,
		GameStatus,
		Commands
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

		else if (str.startsWith("print cards")) {
			return Command.PrintCards;
		} 

		else if (str.startsWith("get notepad")) {
			return Command.GetNotepad;
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
		
		else if (str.startsWith("game status")) {
			return Command.GameStatus;
		}
		
		else if (str.startsWith("commands")) {
			return Command.Commands;
		}
		
		//they entered nothing that matches, so return null
		else return null;
	}
}
