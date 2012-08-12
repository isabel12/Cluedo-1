package TestCases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import CluedoGame.*;
import CluedoGame.Character;
import CluedoGame.CluedoGame.Command;

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
	 * Tests that all cards are contained in the decks of all players.
	 */
	@Test
	public void deckTests() {
		CluedoGame game = null;

		try {
			game = new CluedoGame(3);
		} catch (Exception e) {
			System.exit(-1);
		}


		List<Card> allCards = new ArrayList<Card>();

		for (int i = 0; i < 3; i++) {
			CluedoPlayer player = game.getCurrentPlayer();

			for (Card c: player.getCards()) {
				if (allCards.contains(c)) fail("Shouldn't be in deck!");
				allCards.add(c);
			}

			try {
				game.endTurn();
			} catch (InvalidMoveException e) {
				fail("Should be able to end turn");
			}
		}

		assertTrue("Decks should have the same amount of cards!", 
				allCards.size() == 
				Weapon.values().length + Character.values().length + Room.getMurderRooms().size());
	}

	@Test
	public void startStateTests() {
		CluedoGame game = null;

		try {
			game = new CluedoGame(3);
		} catch (Exception e) {
			fail();
		}

		try {
			game.drawIntrigueCard();
			fail("no players can spawn on an intrigue square");
		} catch (InvalidMoveException e) {
		}

		try {
			game.getRefuter();
			fail("Nobody to refute at start of game");
		} catch (InvalidMoveException e) {
		}

		assertTrue("No winner at start!", game.getWinner() == null);

		assertFalse("Nothing to refute at start!", game.isRefuting());

		try {
			game.moveSecretPassage();
			fail("Nobody spawns in a corner room!");
		} catch (InvalidMoveException e) {
		}

		assertFalse("Game can't start in game-over state", game.isGameOver());

		assertTrue("Player can't be null!", game.getCurrentPlayer() != null);

		assertTrue("Must be first players turn!", game.isTurn(game.getCurrentPlayer()));
	}

	/**
	 * Tests that the cards a player have are automatically crossed of their notebook.
	 */
	@Test
	public void notepadTests() {
		CluedoGame game = null;

		try {
			game = new CluedoGame(3);
		} catch (Exception e) {
			fail();
		}
		
		for (int i = 0; i < 3; i++) {
			CluedoPlayer p = game.getCurrentPlayer();
			
			for (Card c: p.getCards()) {
				assertTrue("Should be marked as innocent!", p.getNotepad().get(c));
			}
			
			try {
				game.endTurn();
			} catch (InvalidMoveException e) {
				fail();
			}
		}
	}

	/**
	 * Checks that the spawn position of all players are not on any square a player
	 * would normally have to move to (i.e rooms, intrigue square, etc)
	 */
	@Test
	public void spawnTests() {
		CluedoGame game = null;

		try {
			game = new CluedoGame(3);
		} catch (Exception e) {
			fail();
		}
		
		for (int i = 0; i < 3; i++) {
			try {
				for (Room r: game.getRoomSteps(game.getCurrentPlayer()).keySet()) {
					assertTrue("Should be able to go to any square!", game.getRoomSteps(game.getCurrentPlayer()).get(r) != 0);
				}
			} catch (InvalidMoveException e) {
				fail();
			}
		}
	}
	
	/**
	 * Tests that the returned commands contain what they should.
	 * First tests the command contains a 'roll' and 'end.
	 * The player then rolls. It then checks it doesn't contain 'roll' and now contains 'move'.
	 */
	@Test
	public void commandTests() {
		CluedoGame game = null;

		try {
			game = new CluedoGame(3);
		} catch (Exception e) {
			fail();
		}
		
		
		List<Command> cmd = new ArrayList<Command>();
		
		cmd.add(Command.Roll);
		cmd.add(Command.EndTurn);
		
		assertTrue("Should only contain 2 at start", game.getCommands().size() == 2);
		assertTrue("Should contain!", game.getCommands().containsAll(cmd));
		
		//now we roll
		try {
			game.rollDice();
		} catch (InvalidMoveException e) {
			fail();
		}
		
		cmd.clear();
		cmd.add(Command.Move);
		cmd.add(Command.EndTurn);
		
		assertTrue("Should only contain 2 at start", game.getCommands().size() == 2);
		assertTrue("Should contain!", game.getCommands().containsAll(cmd));
	}
	
	/**
	 * Tests that the game turn cycle works properly.
	 * Creates games of all valid sizes (from 3-6) then checks the players.
	 */
	@Test
	public void playerCycleTest() {
		CluedoGame game = null;

		for (int i = 3; i <= 6; i++) {
			try {
				game = new CluedoGame(i);
			} catch (Exception e) {
				fail();
			}
			
			CluedoPlayer p = game.getCurrentPlayer();
			for (int j = 0; j < i - 1; j++) {
				try {
					game.endTurn();
				} catch (InvalidMoveException e) {
					fail();
				}
				
				assertFalse("Should be different!", p == game.getCurrentPlayer());
			}
			try {
				game.endTurn();
			} catch (InvalidMoveException e) {
				fail();
			}
			assertEquals("Should be the same character!", p, game.getCurrentPlayer());
		}
	}
	
}
