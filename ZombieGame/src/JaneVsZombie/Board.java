package JaneVsZombie;

import JaneVsZombie.*;
import java.util.Random;

/**
 * Board class. Stores the 2d array of Tiles representing the game's level.
 * Stores all the "pieces" on the Board such as the player character Jane, the
 * array of Zombie enemies, and the array of Pear collectibles. Keeps track of
 * the score. Has methods to move all the Zombies once according to the game
 * logic, and to determine whether a given Tile on the Board is "valid" (may be
 * moved onto by Zombies or Jane)
 *
 * @author Jane Henderson, Evan MacNeil
 * @date October 16, 2014
 */
public class Board
{
	public static final int WIDTH = 40;
	public static final int HEIGHT = 30;
	public static final int N_PEARS = 4;
	public static final int N_ZOMBIES = 32;
	public static final int PEAR_VALUE = 10;

	private Zombie[] horde;
	private Jane jane;
	private Tile[][] tiles;
	private Pear[] pears;
	private int score;
	


	/**
	 * Public constructor. Calls upon the LevelBuilder to construct a
	 * random level. Places Jane in the middle of the Board. Scatters
	 * Pears and Zombies randomly across the board.
	 */	
	public Board()
	{
		score = 0;
		
		// Build the board
		tiles = LevelBuilder.build(WIDTH, HEIGHT);

		// Place Jane in the middle of the board
		jane = new Jane(this, WIDTH/2, HEIGHT/2);

		// Initialize pears
		pears = new Pear[N_PEARS];
		for (int i = 0; i < N_PEARS; i++)
			pears[i] = new Pear(-1, -1, PEAR_VALUE);

		// Initialize zombies
		horde = new Zombie[N_ZOMBIES];
		for (int i = 0; i < N_ZOMBIES; i++)
			horde[i] = new Zombie(this, -1, -1);

		// Place pears
		for (int i = 0; i < N_PEARS; i++)
			placePearRandom(pears[i]);

		// Place zombies
		for (int i = 0; i < N_ZOMBIES; i++)
			placeZombieRandomFar(horde[i]);
	}
	


	/**
	 * Returns true if the location given by (x, y) is a valid location. A
	 * valid location is defined as one which does not contain Jane, a
	 * Zombie or a Pear, is on a passable Tile, and whose coordinates are
	 * within the Board's bounds.
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return true if the location (x, y) is a valid one
	 */
	public boolean isValidLocation(final int x, final int y)
	{
		if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
			return false;

		if (!tiles[y][x].isPassable())
			return false;

		if ( x == jane.getX() && y == jane.getY() )
			return false;

		for (int i = 0; i < N_ZOMBIES; i++)
			if ( x == horde[i].getX() && y == horde[i].getY() )
				return false;

		for (int i = 0; i < N_PEARS; i++)
			if ( x == pears[i].getX() && y == pears[i].getY() )
				return false;

		return true;
	}



	/**
	 * Moves all zombies once. Zombies only move if they are within a
	 * range of Jane.
	 */
	public void moveHorde()
	{
		int jx = jane.getX(), jy = jane.getY();

		for (int i = 0; i < N_ZOMBIES; i++)
		{
			int zx = horde[i].getX(), zy = horde[i].getY();
			int dx = Math.abs(zx-jx), dy = Math.abs(zy-jy);

			// If Zombie is too far away from Jane, do nothing
			if (dx + dy > Zombie.PURSUIT_DISTANCE)
				continue;

			// If Zombie is adjacent to Jane, it bites.
			else if (dx + dy == 1)
			{
				jane.reduceHealth();
			}
			// If Jane is farther horizontally than vertically
			else if (dx > dy)
			{
				// Move zombie left or right
				if (zx > jx)
					horde[i].moveLeft();
				else
					horde[i].moveRight();
			}
			// If Jane is farther vertically than horizontally
			else
			{
				// Move zombie up or down
				if (zy > jy)
					horde[i].moveUp();
				else
					horde[i].moveDown();
			}
		}
	}



	/**
	 * Moves a Pear to the given coordinates. The move is performed whether
	 * or not (x, y) is a valid location.
	 * 
	 * @param p A reference to the Pear to move
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public void placePear(Pear p, final int x, final int y)
	{
		p.setX(x);
		p.setY(y);
	}



	/**
	 * Moves a Pear to a random valid location.
	 * 
	 * @param p A reference to the Pear to move
	 */
	public void placePearRandom(Pear p)
	{
		Random rng = new Random();
		int x = rng.nextInt(WIDTH);
		int y = rng.nextInt(HEIGHT);

		if (!isValidLocation(x, y))
			placePearRandom(p);
		else
			placePear(p, x, y);
	}

	
	
	/**
	 * Moves a Zombie to the given coordinates. The move is performed
	 * whether or not (x, y) is a valid location.
	 *
	 * @param z A reference to the Zombie to move
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public void placeZombie(Zombie z, final int x, final int y)
	{
		z.setX(x);
		z.setY(y);
	}



	/**
	 * Places a Zombie at a random valid location.
	 *
	 * @param z A reference to the Zombie to move
	 */
	public void placeZombieRandom(Zombie z)
	{
		Random rng = new Random();
		int x = rng.nextInt(WIDTH);
		int y = rng.nextInt(HEIGHT);

		if (!isValidLocation(x, y))
			placeZombieRandom(z);
		else
			placeZombie(z, x, y);
	}



	/**
	 * Places a Zombie at a random valid location with the added
	 * restriction that it cannot be placed so close to Jane that it will
	 * chase her.
	 *
	 * @param z A reference to the Zombie to move
	 */
	public void placeZombieRandomFar(Zombie z)
	{
		placeZombieRandom(z);

		int dx = jane.getX() - z.getX();
		int dy = jane.getY() - z.getY();

		dx = dx < 0 ? -dx : dx;
		dy = dy < 0 ? -dy : dy;

		if (dx + dy <= Zombie.PURSUIT_DISTANCE)
			placeZombieRandomFar(z);
	}



	/**
	 * Returns the 2d-array of Board Tiles
	 * 
	 * @return the 2d-array of Board Tiles
	 */
	public Tile[][] getTiles()
	{
		return tiles;
	}



	/**
	 * Returns the array of Zombies
	 *
	 * @return the array of Zombies
	 */
	public Zombie[] getHorde()
	{
		return horde;
	}



	/**
	 * Returns a reference to the Pear located at (x, y)
	 *
	 * @return a reference to the Pear located at (x, y), or null if there
	 *         is no pear there.
	 */
	public Pear getPearAt(final int x, final int y)
	{
		for (int i = 0; i < N_PEARS; i++)
			if (pears[i].getX() == x && pears[i].getY() == y)
				return pears[i];

		return null;
	}



	/**
	 * Returns the array of Pears
	 *
	 * @param the array of Pears
	 */
	public Pear[] getPears()
	{
		return pears;
	}



	/**
	 * Returns a reference to Jane, the player avatar
	 *
	 * @return a reference to the player's character
	 */
	public Jane getJane()
	{
		return jane;
	}



	/**
	 * Returns the current score
	 *
	 * @return the current score
	 */
	public int getScore()
	{
		return score;
	}



	/**
	 * Returns a reference to the Zombie located at (x, y)
	 *
	 * @return a reference to the Zombie located at (x, y), or null if
	 *         there is no Zombie there
	 */
	public Zombie getZombieAt(final int x, final int y)
	{
		for (int i = 0; i < N_ZOMBIES; i++)
			if (horde[i].getX() == x && horde[i].getY() == y)
				return horde[i];

		return null;
	}



	/**
	 * Increases the game score by some amount
	 *
	 * @param amount the amount by which to increase the score
	 */
	public void increaseScore(final int amount)
	{
		score += amount;
	}



	/**
	 * Sets the game score to the specified value
	 *
	 * @param score the value to which the score should be set
	 */
	public void setScore(final int score)
	{
		this.score = score;
	}
}
