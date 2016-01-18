package JaneVsZombie;

import org.junit.*;

/**
 * JaneTest, tests the functionality of the Jane class within the JUnit4
 * framework.
 *
 * @author Jane Henderson, Evan MacNeil
 * @date October 16, 2014
 */
public class JaneTest
{
	Board b;
	Jane j;

	@Before
	public void beforeEach()
	{
		b = new Board();
		j = b.getJane();

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
	public void testHealth()
	{
		// Some of the below tests may only be valid if Jane's maximum health is at least 10
		Assert.assertTrue("Jane's maximum health is at least 10", Jane.MAX_HEALTH >= 10);

		// Test initial
		Assert.assertEquals("Jane's health is at its initial value", Jane.INITIAL_HEALTH, j.getHealth());

		// Test set
		j.setHealth(9);
		Assert.assertEquals("Jane's health is 9", 9, j.getHealth());

		// Test increase
		j.increaseHealth();
		Assert.assertEquals("Jane's health is increased to 10", 10, j.getHealth());

		// Test increase past maximum
		j.setHealth(Jane.MAX_HEALTH);
		j.increaseHealth();
		Assert.assertEquals("Jane's health has not gone past it's maximum", Jane.MAX_HEALTH, j.getHealth());

		// Test reduce
		j.setHealth(1);
		j.reduceHealth();
		Assert.assertEquals("Jane's health is reduced to 0", 0, j.getHealth());

		// Test reduce past minimum
		j.reduceHealth();
		Assert.assertEquals("Jane's health has not gone below ", 0, j.getHealth());
	}

	@Test
	public void testEat()
	{
		Pear p = b.getPears()[0];
		int x = j.getX();
		int y = j.getY();
		int s = b.getScore();
		int h = j.getHealth();

		p.setX(j.getX()-1);
		p.setY(j.getY());
		j.moveLeft();
		
		Assert.assertEquals("Jane moved onto the pear", x-1, j.getX());
		Assert.assertTrue("Pear has moved", (p.getX() != x-1) || (p.getY() != y));
		Assert.assertEquals("Jane has gained health", h+1, j.getHealth());
		Assert.assertEquals("Score has increased", s+p.getValue(), b.getScore());
	}
}
