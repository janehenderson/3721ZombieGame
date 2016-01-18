package JaneVsZombie;

/**
 * Jane Class, represents the player character. Overrides the Character.moveX()
 * methods to account for the fact that Jane may move onto Tiles containing
 * Pears (which is normally considered an invalid Tile), which then causes her
 * to eat it, gain health, and win points.
 *
 * @author Jane Henderson, Evan MacNeil
 * @date October 16, 2014
 */
public class Jane extends JaneVsZombie.Character
{
	// Sets Jane's health to this value when she is instantiated
	public static final int INITIAL_HEALTH = 10;

	// Jane's health may not exceed this value.
	public static final int MAX_HEALTH = 20;

	// Jane's current health.
	private int health;

	/**
	 * Associates Jane with the specified Board at location (x, y)
	 */
	public Jane(Board board, final int x, final int y)
	{
		super(board, x, y);
		health = INITIAL_HEALTH;
	}

	/**
	 * Increases Jane's health, increases the game score, and moves the
	 * Pear elsewhere
	 *
	 * @param p the Pear to be "eaten."
	 */
	public void eatPear(Pear p)
	{
		if (p != null)
		{
			increaseHealth();
			board.increaseScore(p.getValue());
			board.placePearRandom(p);
		}
	}

	/**
	 * Moves Jane up on the Board. If there is a pear here, it is eaten.
	 */
	public void moveUp()
	{
		eatPear(board.getPearAt(x, y-1));
		super.moveUp();
	}

	/**
	 * Moves Jane down on the Board. If there is a pear here, it is eaten.
	 */
	public void moveDown()
	{
		eatPear(board.getPearAt(x, y+1));
		super.moveDown();
	}

	/**
	 * Moves Jane left on the Board. If there is a pear here, it is eaten.
	 */
	public void moveLeft()
	{
		eatPear(board.getPearAt(x-1, y));
		super.moveLeft();
	}

	/**
	 * Moves Jane right on the Board. If there is a pear here, it is eaten.
	 */
	public void moveRight()
	{
		eatPear(board.getPearAt(x+1, y));
		super.moveRight();
	}

	/**
	 * Increases Jane's health by 1.
	 */
	public void increaseHealth()
	{
		if (health < MAX_HEALTH)
			health++;
	}

	/**
	 * Reduces Jane's health by 1.
	 */
	public void reduceHealth()
	{
		if (health > 0)
			health--;
	}

	/**
	 * Returns Jane's current health.
	 *
	 * @return Jane's current health
	 */	
	public int getHealth()
	{
		return health;
	}

	/**
	 * Sets Jane's health to the specified value.
	 *
	 * @param h the value to which to set Jane's health
	 */
	public void setHealth(final int h)
	{
		if (h >= 0 && h <= MAX_HEALTH)
			health = h;
	}
}
