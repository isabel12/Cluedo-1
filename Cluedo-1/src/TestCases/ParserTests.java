package TestCases;

import static org.junit.Assert.*;

import org.junit.Test;

import CommandLine.Parser;
import CommandLine.Parser.Command;
import CluedoGame.Character;
import CluedoGame.Room;

public class ParserTests {


	@Test
	public void rollTests() {		
		String[] valid = 	{"roll dice", "rOlL DiCE", "ROLL DICE", "roll DICE   "};
		String[] invalid = 	{"roll", "roll die", "dsajk", "asdjk"}; 
		
		commandTest(valid, Command.RollDice);
		commandTest(invalid, Command.Undefined);
	}
	
	@Test
	public void moveTowardsTests() {		
		String[] valid = 	{"move towards", "mOvE toWards", "moVe Towards   "};
		String[] invalid = 	{"move", "fdankdsa", "dshja"}; 
		
		commandTest(valid, Command.MoveTowards);
		commandTest(invalid, Command.Undefined);
	}
	
	@Test
	public void characterTests() {
		String[] valid = 	{"plum", "victor plum", "victor", "victor plam", "vactor plum"};
		String[] invalid = 	{"victar plam", "victar plom"}; 
		
		characterTest(valid, Character.Plum);
		characterTest(invalid, null);
	}
	
	@Test
	public void roomTests() {
		String[] valid = 	{"hall", "hall Room", "hall ro", "the hall", "go to hall"};
		String[] invalid = 	{"hell room", "room"}; 
		
		roomTest(valid, Room.Hall);
		roomTest(invalid, null);
	}
	
	private void commandTest(String[] args, Command c) {
		Parser p = new Parser();
		for (String s: args) assertEquals(args + " not equals to " + c, p.parseCommand(s), c);
	}
	
	private void characterTest(String[] args, Character c) {
		Parser p = new Parser();
		for (String s: args) assertEquals(args + " not equals to " + c, p.parseCharacter(s), c);
	}
	
	private void roomTest(String[] args, Room r) {
		Parser p = new Parser();
		for (String s: args) assertEquals(args + " not equals to " + p, p.parseRoom(s), r);
	}
}
