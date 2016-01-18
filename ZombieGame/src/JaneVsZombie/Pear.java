package JaneVsZombie;

/**
 * Pear Class. Has an (x, y) coordinate to store its location on the Board, and
 * a value to be added to the game score when picked up by Jane.
 *
 * @author Jane Henderson, Evan MacNeil
 * @date October 16, 2014
 */
public class Pear
{
	// x- and y-coordinates of the Pear
	protected int x, y;

	// Number of points to be added to the game score when picked up
	protected int value;

	/**
	 * Creates a new Pear at location (x, y) and with the specified value.
	 *
	 * @param x     x-coordinate of the new Pear
	 * @param y     y-coordinate of the new Pear
	 * @param value the Pear's point value
	 */
	public Pear(final int x, final int y, final int value)
	{
		this.value = value;
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the Pear's point value.
	 * 
	 * @return the Pear's point value.
	 */
	public int getValue()
	{
		return value;
	}

	/**
	 * Returns the Pear's x-coordinate
	 * 
	 * @return the Pear's x-coordinate
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Returns the Pear's y-coordinate
	 * 
	 * @return the Pear's y-coordinate
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Sets the Pear's point value
	 * 
	 * @param v the Pear's new point value
	 */
	public void setValue(final int v)
	{
		this.value = v;
	}

	/**
	 * Sets the Pear's x-coordinate
	 * 
	 * @param x the Pear's new x-coordinate
	 */
	public void setX(final int x)
	{
		this.x = x;
	}

	/**
	 * Sets the Pear's y-coordinate
	 * 
	 * @param y the Pear's new y-coordinate
	 */
	public void setY(final int y)
	{
		this.y = y;
	}
}
