package CluedoGame;

public enum Room implements Card {
		DiningRoom("Dining Room"),
		GuestRoom("Guest Room"),
		Hall("Hall"),
		Kitchen("Kitchen"),
		LivingRoom("Living Room"),
		Observatory("Ovservatory"),
		Patio("Patio"),
		Spa("Spa"),
		Theatre("Theatre"),
		SwimmingPool("Swimming Pool");


		private final String room;

		Room(String room){
			this.room = room;
		}

		public String getName(){
			return room;
		}
		
		@Override
		public String toString(){
			return getName();
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
		
}
	

