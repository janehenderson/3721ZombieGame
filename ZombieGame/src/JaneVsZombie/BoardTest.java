package JaneVsZombie;

import org.junit.*;

/**
 * BoardTest class, tests the Board class' functionality within the JUnit4
 * framework.
 *
 * @author Jane Henderson, Evan MacNeil
 * @date October 16, 2014
 */
public class BoardTest
{
	Board b;

	@Before
	public void beforeTest()
	{
		b = new Board();
		
		Pear[] p = b.getPears();
		for (int i = 0; i < Board.N_PEARS; i++)
			b.placePear(p[i], -1, -1);

		Zombie[] h = b.getHorde();
		for (int i = 0; i < Board.N_ZOMBIES; i++)
			b.placeZombie(h[i], -1, -1);
	}
	
	@After
	public void afterTest()
	{
	}

	@Test
	public void testPlacePear()
	{
		Pear p = b.getPears()[0];
		b.placePear(p, 1, 1);
		Assert.assertEquals("Pear's x-coordinate is 1", 1, p.getX());
		Assert.assertEquals("Pear's y-coordinate is 1", 1, p.getY());
	}

	@Test
	public void testPlaceZombie()
	{
		Zombie z = b.getHorde()[0];
		b.placeZombie(z, 1, 1);
		Assert.assertEquals("Zombie's x-coordinate is 1", 1, z.getX());
		Assert.assertEquals("Zombie's y-coordinate is 1", 1, z.getY());
	}

	@Test
	public void testGetPearAt()
	{
		Pear p = b.getPears()[0];
		b.placePear(p, 1, 1);

		Pear q = b.getPearAt(1, 1);
		Pear r = b.getPearAt(2, 2);

		Assert.assertEquals("Two references to the same pear", p, q);
		Assert.assertNull("There is no pear at (2, 2)", r);
	}

	@Test
	public void testGetZombieAt()
	{
		Zombie z = b.getHorde()[0];
		b.placeZombie(z, 1, 1);

		Zombie x = b.getZombieAt(1, 1);
		Zombie y = b.getZombieAt(2, 2);

		Assert.assertEquals("Two references to the same zombie", z, x);
		Assert.assertNull("There is no zombie at (2, 2)", y);
	}

 	@Test
	public void testMoveHordeBarbieNear()
	{
		if (Zombie.PURSUIT_DISTANCE < 3)
			Assert.fail("Zombies are not near");

		// Set up zombies three spaces from Jane in each direction
		Jane j = b.getJane();
		Zombie[] h = b.getHorde();
		
		b.placeZombie(h[0], j.getX() - 3, j.getY());
		b.placeZombie(h[1], j.getX(), j.getY() - 3);
		b.placeZombie(h[2], j.getX() + 3, j.getY());
		b.placeZombie(h[3], j.getX(), j.getY() + 3);

		// Move all zombies twice
		b.moveHorde();
		b.moveHorde();

		// The zombies should be two spaces away from Jane.
		int d0 = j.getX() - h[0].getX();
		int d1 = j.getY() - h[1].getY();
		int d2 = h[2].getX() - j.getX();
		int d3 = h[3].getY() - j.getY();

		Assert.assertEquals("", 2, d0);
		Assert.assertEquals("", 2, d1);
		Assert.assertEquals("", 2, d2);
		Assert.assertEquals("", 2, d3);
	}

	@Test
	public void testMoveHordeBarbieFar()
	{
		if (Zombie.PURSUIT_DISTANCE >= 10)
			Assert.fail("Zombies are not far");

		// Set up zombies three spaces from Jane in each direction
		Jane j = b.getJane();
		Zombie[] h = b.getHorde();
		
		b.placeZombie(h[0], j.getX() - 10, j.getY());
		b.placeZombie(h[1], j.getX(), j.getY() - 10);
		b.placeZombie(h[2], j.getX() + 10, j.getY());
		b.placeZombie(h[3], j.getX(), j.getY() + 10);

		// Move all zombies twice
		b.moveHorde();
		b.moveHorde();

		// The zombies should be two spaces away from Jane.
		int d0 = j.getX() - h[0].getX();
		int d1 = j.getY() - h[1].getY();
		int d2 = h[2].getX() - j.getX();
		int d3 = h[3].getY() - j.getY();

		Assert.assertEquals("", 10, d0);
		Assert.assertEquals("", 10, d1);
		Assert.assertEquals("", 10, d2);
		Assert.assertEquals("", 10, d3);
	}

	@Test
	public void testValidLocation()
	{
		int w = Board.WIDTH;
		int h = Board.HEIGHT;
		
		Jane j = b.getJane();
		Zombie z = b.getHorde()[0];
		Pear p = b.getPears()[0];

		b.placeZombie(z, j.getX()-1, j.getY());
		b.placePear(p, j.getX()+1, j.getY());

		Assert.assertEquals("(0, 0) is wall",
			b.getTiles()[0][0].getType(), Tile.TileType.TILE_WALL);
		Assert.assertFalse("A wall is not a valid location",
			b.isValidLocation(0, 0) );
		Assert.assertFalse("A pear is not a valid location",
			b.isValidLocation(p.getX(), p.getY()) );
		Assert.assertFalse("A zombie is not a valid location",
			b.isValidLocation(z.getX(), z.getY()) );
		Assert.assertFalse("Space under Jane is not a valid location",
			b.isValidLocation(j.getX(), j.getY()) );
		Assert.assertFalse("(-1, 0) is not a valid location",
			b.isValidLocation(-1, 0) );
		Assert.assertFalse("(0, -1) is not a valid location",
			b.isValidLocation(0, -1) );
		Assert.assertFalse("(w, 0) is not a valid location",
			b.isValidLocation(w, 0) );
		Assert.assertFalse("(0, h) is not a valid location",
			b.isValidLocation(0, h) );
		Assert.assertEquals("Space above Jane is a floor",
			b.getTiles()[j.getY()-1][j.getX()].getType(), Tile.TileType.TILE_FLOOR);
		Assert.assertTrue("Space above Jane is a valid location",
			b.isValidLocation(j.getX(), j.getY()-1) );
	}

	@Test
	public void testIncreaseScore()
	{
		b.setScore(0);
		b.increaseScore(10);
		Assert.assertEquals("Score is 10", 10, b.getScore());
	}
}
