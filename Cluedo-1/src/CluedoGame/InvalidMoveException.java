package CluedoGame;

/**
 * Class used to indicate an invalid move has been attempted.
 * 
 * @author Troy Shaw
 *
 */
public class InvalidMoveException extends Exception{

	/**
	 * Contructs an InvalidMoveException with the given reason.
	 * @param reason
	 */
	public InvalidMoveException(String reason) {
		super(reason);
	}
}
