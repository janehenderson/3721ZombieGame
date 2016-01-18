package JaneVsZombie;

import org.junit.*;

/**
 * CharacterTest, tests the functionality of the Character class within the
 * JUnit4 framework.
 *
 * @author Jane Henderson, Evan MacNeil
 * @date October 16, 2014
 */
public class CharacterTest
{
	Board b;
	JaneVsZombie.Character c;

	@Before
	public void beforeEach()
	{
		int x = Board.WIDTH/2;
		int y = Board.HEIGHT/2;

		b = new Board();
		
		// Move all zombies off the board
		Zombie[] h = b.getHorde();
		for (int i = 0; i < Board.N_ZOMBIES; i++)
			b.placeZombie(h[i], -1, -1);

		// Move all pears off the board
		Pear[] p = b.getPears();
		for (int i = 0; i < Board.N_PEARS; i++)
			b.placePear(p[i], -1, -1);

		// Move Jane off the board
		b.getJane().setX(-1);
		b.getJane().setY(-1);

		Assert.assertTrue("Initial location is a valid location)", b.isValidLocation(x,y));
		Assert.assertTrue("Left is a valid location)", b.isValidLocation(x-1,y));
		Assert.assertTrue("Up-Left is a valid location)", b.isValidLocation(x-1,y-1));
		Assert.assertTrue("Up is a valid location)", b.isValidLocation(x,y-1));

		c = new JaneVsZombie.Character(b, x, y);
	}

	@After
	public void afterEach()
	{
	}

	@Test
	public void testMoveInCircle()
	{
		int x = Board.WIDTH/2;
		int y = Board.HEIGHT/2;

		c.moveLeft();
		Assert.assertEquals("Character moved left", x-1, c.getX());
		Assert.assertEquals("Character moved left", y,   c.getY());

		c.moveUp();
		Assert.assertEquals("Character moved up", x-1, c.getX());
		Assert.assertEquals("Character moved up", y-1, c.getY());

		c.moveRight();
		Assert.assertEquals("Character moved right", x,   c.getX());
		Assert.assertEquals("Character moved right", y-1, c.getY());

		c.moveDown();
		Assert.assertEquals("Character moved down", x, c.getX());
		Assert.assertEquals("Character moved down", y, c.getY());
	}

	@Ignore
	public void testMoveIntoCharacter()
	{
		// Characters are not able to see other Characters except
		// through the Board. The Board does not keep a record of
		// Characters, only Zombies and Janes. Whether a location is
		// legal to move into is determined by the board. Movement into
		// other characters is undefined.
	}

	@Test
	public void testMoveIntoPear()
	{
		int x = Board.WIDTH/2;
		int y = Board.HEIGHT/2;
		
		b.placePear(b.getPears()[0], x-1, y);
		c.moveLeft();
		Assert.assertEquals("Character did not move through pear", x, c.getX());
	}

	@Test
	public void testMoveIntoWall()
	{
		c.setX(1);
		c.setY(1);
		c.moveLeft();
		Assert.assertEquals("Character did not move through wall", 1, c.getX());
	}

	@Test
	public void testMoveIntoZombie()
	{
		int x = Board.WIDTH/2;
		int y = Board.HEIGHT/2;
		
		b.placeZombie(b.getHorde()[0], x-1, y);
		c.moveLeft();
		Assert.assertEquals("Character did not move through pear", x, c.getX());
	}

	@Test
	public void testMoveOffBoard()
	{
		// Move off left side of board
		c.setX(0);
		c.setY(Board.HEIGHT/2);
		c.moveLeft();
		Assert.assertEquals("Character did not move off left edge", 0, c.getX());

		// Move off top side of board
		c.setX(Board.WIDTH/2);
		c.setY(0);
		c.moveUp();
		Assert.assertEquals("Character did not move off top edge", 0, c.getY());

		// Move off right side of board
		c.setX(Board.WIDTH-1);
		c.setY(Board.HEIGHT/2);
		c.moveRight();
		Assert.assertEquals("Character did not move off right edge", Board.WIDTH-1, c.getX());

		// Move off bottom side of board
		c.setX(Board.WIDTH/2);
		c.setY(Board.HEIGHT-1);
		c.moveDown();
		Assert.assertEquals("Character did not move off bottom edge", Board.HEIGHT-1, c.getY());
	}
}
