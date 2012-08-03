package CluedoGame;

public class Character {

	public enum Name {
		DianeWhite,
		EleanorPeacock,
		JackMustard,
		JacobGreen,
		KasandraScarlet,
		VictorPlum
	}
	
	private Name name;
	
	public Character(Name name) {
		this.name = name;
	}
}
