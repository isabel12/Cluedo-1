package CluedoGame;

public class Room {
	public enum Type {
		DiningRoom,
		GuestRoom,
		Hall,
		Kitchen,
		LivingRoom,
		Observatory,
		Patio,
		Spa,
		Theatre,
		SwimmingPool
	}
	
	private Type type;
	
	/**
	 * Creates a Room object with the given type.
	 * @param type
	 */
	public Room(Type type) {
		this.type = type;
	}
	
	/**
	 * Gets the type of room.
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
}
