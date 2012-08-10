package CluedoGame;

import java.util.List;
import java.util.ArrayList;

public enum Room implements Card {
	DiningRoom("Dining room"),
	GuestRoom("Guest room"),
	Hall("Hall"),
	Kitchen("Kitchen"),
	LivingRoom("Living room"),
	Observatory("Observatory"),
	Patio("Patio"),
	Spa("Spa"),
	Theatre("Theatre"),
	SwimmingPool("Swimming pool"),
	Intrigue("Intrigue square"),
	Corridor("Corridor");

	private final String room;
	private static List<Room> murderRooms;
	private static List<Room> rooms;

	Room(String room){
		this.room = room;
	}

	public String getName(){
		return room;
	}

	@Override
	public boolean isWeapon(){
		return false;
	}

	@Override
	public boolean isCharacter() {
		return false;
	}

	@Override
	public boolean isRoom() {
		return true;
	}

	@Override
	public String toString(){
		return getName();
	}

	/**
	 * Returns the list of Room where the murder could have happened.
	 * Does not include PoolRoom, Corridor, Intrigue
	 * @return
	 */
	public static List<Room> getMurderRooms() {
		//only generate the list once
		if (murderRooms == null) {
			murderRooms = new ArrayList<Room>();

			for (Room r: values()) murderRooms.add(r);

			murderRooms.remove(Corridor);
			murderRooms.remove(Intrigue);
			murderRooms.remove(SwimmingPool);
		}

		return murderRooms;
	}
	
	/**
	 * Returns the list of all Room.
	 * Does not include Corridor and Intrigue.
	 * @return
	 */
	public static List<Room> getRooms() {
		//only generate the list once
		if (rooms == null) {
			rooms = new ArrayList<Room>();

			for (Room r: values()) rooms.add(r);

			rooms.remove(Corridor);
			rooms.remove(Intrigue);
		}

		return rooms;
	}
}


