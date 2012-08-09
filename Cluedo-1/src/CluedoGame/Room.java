package CluedoGame;

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
		
}
	

