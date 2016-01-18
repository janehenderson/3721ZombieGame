package JaneVsZombie;

import org.junit.*;

/**
 * ZombieTest, tests the functionality of the Zombie class within the JUnit4
 * framework.
 *
 * @author Jane Henderson, Evan MacNeil
 * @date October 16, 2014
 */
public class ZombieTest
{
	Board b;
	Zombie z;

	@Before
	public void beforeEach()
	{
		b = new Board();
		z = b.getHorde()[0];

		// Move all zombies off the board
		Zombie[] h = b.getHorde();
		for (int i = 0; i < Board.N_ZOMBIES; i++)
			b.placeZombie(h[i], -1, -1);

		// Move all pears off the board
		Pear[] p = b.getPears();
		for (int i = 0; i < Board.N_PEARS; i++)
			b.placePear(p[i], -1, -1);
	}

	@After
	public void afterEach()
	{
	}

	@Test
	public void testMoveTwice()
	{
		// Move zombie to middle-left space;
		b.placeZombie(z, 0, Board.HEIGHT/2);
		z.moveRight();
		Assert.assertEquals("Zombie has moved right once", 1, z.getX());
		z.moveRight();
		Assert.assertEquals("Zombie has not moved a second time", 1, z.getX());
	}
}
