package CluedoGame;

public abstract class Power {

	private String name;
	private ActivationTime activation;
	private Player wielder;
	
	// information needed to activate the power
	protected boolean activated;
	protected Player affected;
	protected Card cardInvolved;
	protected Square squareInvolved;
	
	
	/**
	 * Super constructor for Power.  Requires a name and an activationTime.
	 * @param name
	 * @param activation
	 */
	public Power(String name, ActivationTime activation){
		this.name = name;
		this.activation = activation;
		this.affected = null;
		this.cardInvolved = null;
		this.squareInvolved = null;
	}
	
	
	/**
	 * This method set the wielder of the power.  This can only be done once.
	 * @param player
	 * @throws IllegalStateException if the wielder has been set already.
	 */
	public void setWielder(Player player){
		if (this.wielder != null){
			throw new IllegalStateException("The wielder is already set.");
		}
		this.wielder = player;
	}
	
	/**
	 * This method sets the power as activated, and records in it 
	 * @param affected
	 * @param cardInvolved
	 * @param squareInvolved
	 */
	public void activate(Player affected, Card cardInvolved, Square squareInvolved){
		if (this.activated){
			throw new IllegalStateException("This power has already been used.");
		}
		
		this.affected = affected;
		this.cardInvolved = cardInvolved;
		this.squareInvolved = squareInvolved;
	}
	
	
	public String getName(){
		return this.name;
	}
	
	public ActivationTime getActivationTime(){
		return this.activation;
	}
	
	
	//=============================================================
	// Abstract methods
	//=============================================================
	
	/**
	 * This method returns a string describing the result of the power being activated.
	 * @return
	 * @throws IllegalStateException if power has not been activated.
	 */
	public abstract String getActivationResult();
	
	public abstract String getDescription();
	
	public abstract String getRules();
	
	
	
	//=============================================================
	// Enum for activationTime
	//=============================================================
	
	public enum ActivationTime{
		InRoomNotSuggested,
		InRoomSuggested,
		CalledToRefute,
		RefuteOccured,
		BeforeRolling,
		AfterRollingBeforeMove,
		AfterRollingAfterMove,
		EndOfTurn;
	}
	
}
