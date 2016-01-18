package JaneVsZombie;

/**
 * Zombie class. Overrides Character.moveX() since Zombies walk slower than
 * other Characters. The Zombie.moveX() methods allow the Zombie to move only
 * once every other time it attempts to move.
 *
 * @author Jane Henderson, Evan MacNeil
 * @date October 16, 2014
 */
public class Zombie extends JaneVsZombie.Character
{
	// From how far away a Zombie can "see" Jane. If Jane is beyond this
	//   distance, the Zombie will not move towards her.
	public static final int PURSUIT_DISTANCE = 5;

	// Whether or not the Zombie is permitted to move. This is toggled
	//   every time the Zombie attempts a move.
	private boolean canMove;

	/**
	 * Associates the Zombie to the given Board at the location (x, y).
	 * 
	 * @param board the Board with which to associate the Zombie
	 * @param x     x-coordinate
	 * @param y     y-coordinate
	 */
	public Zombie(Board board, final int x, final int y)
	{
		super(board, x, y);
		canMove = true;
	}

	/**
	 * Moves the Zombie up only if it is permitted to, then toggles its
	 * permission.
	 */
	public void moveUp()
	{
		if (canMove)
			super.moveUp();
		canMove = !canMove;
	}
	
	/**
	 * Moves the Zombie down only if it is permitted to, then toggles its
	 * permission.
	 */
	public void moveDown()
	{
		if (canMove)
			super.moveDown();
		canMove = !canMove;
	}
	
	/**
	 * Moves the Zombie left only if it is permitted to, then toggles its
	 * permission.
	 */
	public void moveLeft()
	{
		if (canMove)
			super.moveLeft();
		canMove = !canMove;
	}
	
	/**
	 * Moves the Zombie right only if it is permitted to, then toggles its
	 * permission.
	 */
	public void moveRight()
	{
		if (canMove)
			super.moveRight();
		canMove = !canMove;
	}
}
