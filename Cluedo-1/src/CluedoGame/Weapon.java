package CluedoGame;

/**
 * Class represents a weapon in the cludeo game.
 * Contains a Type enum which can be used to specify the type this weapon is.
 * 
 * @author Troy Shaw
 *
 */

	public enum Weapon implements Card {
		Axe("Axe"),
		BaseballBat("Baseball Bat"),
		Candlestick("Candle Stick"),
		Dumbbell("Dumbbell"),
		Knife("Knife"),
		Pistol("Knife"),
		Poison("Prison"),
		Rope("Rope"),
		Trophy("Trophy");
		
		
		private final String weapon;

		Weapon(String weapon){
			this.weapon = weapon;
		}
		
		@Override
		public String toString(){
			return weapon;
		}
	}