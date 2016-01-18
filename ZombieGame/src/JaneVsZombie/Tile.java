package JaneVsZombie;

/**
 * Tile class, represents a discrete space within the game board. Tiles have the
 * property of being "passable" or not, which determines whether a Character or
 * Pear may occupy this space.
 *
 * @author Jane Henderson, Evan MacNeil
 * @date October 16, 2014
 */
public class Tile
{
	// Enumerated list of possible types of tiles. Each may be graphically
	//   represented differently.
	public enum TileType
	{
		TILE_FLOOR,
		TILE_WALL
	}

	// Whether or not a Character or Pear may occupy this space
	private boolean passable;

	// x- and y- coordinates on the game Board
	private int x, y; 

	// This Tile's type from amongst the enumerated list above
	private TileType type;

	/**
	 * Initializes the Tiles x- and y- coordinates and TileType.
	 *
	 * @param x x-coordinate of the Tile
	 * @param y y-coordinate of the Tile
	 * @param t the Tile's TileType
	 */
	public Tile(final int x, final int y, final TileType t)
	{
		this.x = x;
		this.y = y;
		setType(t);
	}

	/**
	 * Returns true if this is a "passable" tile.
	 * 
	 * @return true if this is a passable tile
	 */
	public boolean isPassable()
	{
		return passable;
	}

	/**
	 * Returns the Tile's TileType
	 *
	 * @return the Tile's TileType
	 */
	public TileType getType()
	{
		return type;
	}

	/**
	 * Sets the Tile's TileType. If the new type is TILE_FLOOR, then the
	 * Tile becomes passable. If the new type is TILE_WALL, then the Tile
	 * becomes no longer passable.
	 *
	 * @param t the Tile's new TileType
	 */
	public void setType(final TileType t)
	{
		type = t;
		if (t == TileType.TILE_FLOOR)
			passable = true;
		else if (t == TileType.TILE_WALL)
			passable = false;
	}
}
