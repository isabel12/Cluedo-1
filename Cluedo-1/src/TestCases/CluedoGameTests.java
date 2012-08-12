package TestCases;

import static org.junit.Assert.*;

import org.junit.Test;

import CluedoGame.CluedoGame;
import CluedoGame.CluedoGame.Command;
import CluedoGame.InvalidMoveException;

public class CluedoGameTests {

	@Test
	public void invalidNumberTest() {
		for (int i = -5; i < 25; i++) {
			if (i < 3 || i > 6) {
				try {
					new CluedoGame(i);
					fail("Should not be allowed");
				} catch (Exception e) {
					//means exception was caught
				}
			} else
				try {
					new CluedoGame(i);
				} catch (Exception e) {
					fail("Should be ok!");
				}

		}
	}

	/*
	 * Tests that rolling is available at the start of a turn.
	 * Tests that after rolling, rolling is not available.
	 */
	@Test 
	public void rollTest() {
		CluedoGame game = null;

		try {
			game = new CluedoGame(3);
		} catch (Exception e) {
			fail();
		}

		assertTrue(game.getCommands().contains(Command.Roll));

		try {
			game.rollDice();
		} catch (InvalidMoveException e) {
			fail();
		}

		assertFalse(game.getCommands().contains(Command.Roll));

		try {
			game.rollDice();
			fail();
		} catch (InvalidMoveException e) {
			//means test passed
		}
	}

	@Test
	public void suggestTests() {
		CluedoGame game = null;

		try {
			game = new CluedoGame(3);
		} catch (Exception e) {
			fail();
		}

		assertFalse(game.getCommands().contains(Command.MakeSuggestion));
		assertFalse(game.getCommands().contains(Command.MakeAccusation));

		try {
			game.makeAccusation(null, null, null);
			fail();
		} catch (InvalidMoveException e) {
			//means was succes
		}

		try {
			game.makeSuggestion(null, null);
			fail();
		} catch (InvalidMoveException e) {
			//means was succes
		}

	}

	/**
	 * 
	 */
	@Test
	public void notepadTests() {
		CluedoGame game = null;
		
		try {
			game = new CluedoGame(3);
		} catch (Exception e) {
			System.exit(-1);
		}
		
		
	}
	
}
