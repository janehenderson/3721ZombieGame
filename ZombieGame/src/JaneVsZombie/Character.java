package JaneVsZombie;

/**
 * Character Class, represents items on the Board which is able to move.
 *
 * @author Jane Henderson, Evan MacNeil
 * @date October 16, 2014
 */
public class Character
{
	// Reference to the Board with which this Character is associated
	protected Board board;

	// x- and y-coordinates on the Board
	protected int x, y;

	/**
	 * Associates the Character with a Board and places it at location
	 * (x, y)
	 * 
	 * @param board the Board with which to associate this Character
	 * @param x     the Character's initial x-coordinate
	 * @param y     the Character's initial y-coordinate
	 */
	public Character(Board board, final int x, final int y)
	{
		this.board = board;
		this.x = x;
		this.y = y;
	}

	/**
	 * Moves the character up only if this is a valid location to move to.
	 */
	public void moveUp()
	{
		if (board.isValidLocation(x, y - 1))
			y--;
	}
	
	/**
	 * Moves the character down only if this is a valid location to move to.
	 */
	public void moveDown()
	{
		if (board.isValidLocation(x, y + 1))
			y++;
	}
	
	/**
	 * Moves the character left only if this is a valid location to move to.
	 */
	public void moveLeft()
	{
		if (board.isValidLocation(x - 1, y))
			x--;
	}
	
	/**
	 * Moves the character right only if this is a valid location to move to.
	 */
	public void moveRight()
	{
		if (board.isValidLocation(x + 1, y))
			x++;
	}

	/**
	 * Returns the Character's x-coordinate.
	 *
	 * @return the Character's x-coordinate
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Returns the Character's y-coordinate.
	 *
	 * @return the Character's y-coordinate
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Sets the Character's x-coordinate to the specified value
	 *
	 * @param x the Character's new x-coordinate
	 */
	public void setX(final int x)
	{
		this.x = x;
	}

	/**
	 * Sets the Character's y-coordinate to the specified value
	 *
	 * @param y the Character's new y-coordinate
	 */
	public void setY(final int y)
	{
		this.y = y;
	}
}
