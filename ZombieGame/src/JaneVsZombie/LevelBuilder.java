package JaneVsZombie;

import JaneVsZombie.Tile;
import java.util.Random;

/**
 * LevelBuilder Class, has only public method LevelBuilder.build(int, int)
 * which constructs a 2D Tile array representing a game level. This array is
 * randomly generated and simulates a city street intersection with buildings
 * in each of the four corners.
 *
 * @author Jane Henderson, Evan MacNeil
 * @date October 16, 2014
 */
public class LevelBuilder
{
	public static final int MIN_ROOM_HEIGHT = 5;
	public static final int MIN_ROOM_WIDTH  = 5;
	public static final int ROAD_SIZE       = 3;



	/**
	 * Builds and returns a level, represented as a 2d Tile array.
	 * Simulates a city street intersection with buildings in each of the
	 * four corners.
	 *
	 * @param w the width of the level in Tiles
	 * @param h the height of the level in Tiles
	 * @return a 2d Tile array representing the level
	 */
	public static Tile[][] build(final int w, final int h)
	{
		return buildRandom(w, h);
	}



	/**
	 * Builds a level consisting simply of walls around the exterior,
	 * floors inside. Currently not used.
	 *
	 * @param w the width of the level in Tiles
	 * @param h the height of the level in Tiles
	 * @return a 2d Tile array representing the level
	 */
	private static Tile[][] buildTest(final int w, final int h)
	{
		// Initialize all tiles
		Tile level[][] = new Tile[h][w];
		for (int i = 0; i < h; i++)
			for (int j = 0; j < w; j++)
				level[i][j] = new Tile(j, i, Tile.TileType.TILE_FLOOR);
		
		// Build a wall around the outer edge of level
		setRect(level, 0, 0, w-1, h-1, Tile.TileType.TILE_WALL);
		
		return level;
	}



	/**
	 * Builds a random level, with buildings occupying the four corners.
	 *
	 * @param w the width of the level in Tiles
	 * @param h the height of the level in Tiles
	 * @return a 2d Tile array representing the level
	 */
	private static Tile[][] buildRandom(final int w, final int h)
	{
		// Initialize all tiles
		Tile level[][] = new Tile[h][w];
		for (int i = 0; i < h; i++)
			for (int j = 0; j < w; j++)
				level[i][j] = new Tile(j, i, Tile.TileType.TILE_FLOOR);
		
		// Make buildings
		int r = ROAD_SIZE;
		makeRoom(level, 0,     0,     w/2-r, h/2-r);
		makeRoom(level, w/2+r, 0,     w-1,   h/2-r);
		makeRoom(level, 0,     h/2+r, w/2-r, h-1);
		makeRoom(level, w/2+r, h/2+r, w-1,   h-1);

		// Fix outer walls
		r -= 1;
		setRect(level, 0,     0,     w-1,   h-1,   Tile.TileType.TILE_WALL);
		setRect(level, w/2-r, 0,     w/2+r, h-1,   Tile.TileType.TILE_FLOOR);
		setRect(level, 0,     h/2-r, w-1,   h/2+r, Tile.TileType.TILE_FLOOR);

		// Fix some inner walls
		for (int i = 1; i < h - 1; i++)
		{
			for (int j = 1; j < w - 1; j++)
			{
				int n = 0;
				if (level[i-1][j].getType() == Tile.TileType.TILE_WALL) n++;
				if (level[i+1][j].getType() == Tile.TileType.TILE_WALL) n++;
				if (level[i][j-1].getType() == Tile.TileType.TILE_WALL) n++;
				if (level[i][j+1].getType() == Tile.TileType.TILE_WALL) n++;
				if (n >= 3)
					setTile(level, j, i, Tile.TileType.TILE_WALL);
			}
		}

		
		return level;
	}



	/**
	 * Set all Tiles in a 2D array to the specified TileType
	 *
	 * @param tiles a 2D array of Tiles
	 * @param t the TileType to which to set the Tiles
	 */
	private static void setAll(Tile[][] tiles, final Tile.TileType t)
	{
		for (int i = 0; i < tiles.length; i++)
		{
			for (int j = 0; j < tiles[i].length; j++)
			{
				tiles[i][j].setType(t);
			}
		}
	}



	/**
	 * Constructs a building in the area given by the coordinates (x1, y1) and (x2, y2)
	 * Simulates rooms within a building by recursively calling itself on smaller areas.
	 * 
	 * @param tiles a 2D array of Tiles
	 * @param x1 x-coordinate of one corner of the room
	 * @param y1 y-coordinate of one corner of the room
	 * @param x2 x-coordinate of the opposite corner of the room
	 * @param y2 y-coordinate of the opposite corner of the room
	 */
	private static void makeRoom(Tile[][] tiles, final int x1, final int y1, final int x2, final int y2)
	{
		if (x2 < x1)
		{
			makeRoom(tiles, x2, y1, x1, y2);
			return;
		}
		if (y2 < y1)
		{
			makeRoom(tiles, x1, y2, x2, y1);
			return;
		}

		Random rng = new Random();
		int w = x2 - x1;
		int h = y2 - y1;
		// Make perimeter walls
		setRect(tiles, x1, y1, x2, y2, Tile.TileType.TILE_WALL);

		// Make a door in each wall
		setTile(tiles, rng.nextInt(w-1) + x1 + 1, y1, Tile.TileType.TILE_FLOOR);
		setTile(tiles, rng.nextInt(w-1) + x1 + 1, y2, Tile.TileType.TILE_FLOOR);
		setTile(tiles, x1, rng.nextInt(h-1) + y1 + 1, Tile.TileType.TILE_FLOOR);
		setTile(tiles, x2, rng.nextInt(h-1) + y1 + 1, Tile.TileType.TILE_FLOOR);

		// Make smaller rooms...
		boolean canDivV = false;
		boolean canDivH = false;
		int mode = 0;

		// If the room is wide enough, we can subdivide it vertically
		if (w >= 2 * MIN_ROOM_WIDTH - 2)
			canDivV = true;

		// If the room is tall enough, we can subdivide it horizontally
		if (h >= 2 * MIN_ROOM_HEIGHT - 2)
			canDivH = true;

		// If we can only divide vertically, we divide vertically
		// If we can only divide horizontally, we divide horizontally
		// If we can divide either way, choose at random
		if (canDivV && !canDivH)
			mode = 1;
		else if (!canDivV && canDivH)
			mode = 2;
		else if (canDivV && canDivH)
			mode = rng.nextInt(2)+1;

		if (mode == 1)
		{
			// Divide room vertically
			int x = x1 + (MIN_ROOM_WIDTH  - 1) + rng.nextInt(w + 1 - 2 * (MIN_ROOM_WIDTH  - 1));
			makeRoom(tiles, x1, y1, x,  y2);
			makeRoom(tiles, x,  y1, x2, y2);
		}
		else if (mode == 2)
		{
			// Divide room horizontally
			int y = y1 + (MIN_ROOM_HEIGHT - 1) + rng.nextInt(h + 1 - 2 * (MIN_ROOM_HEIGHT - 1));
			makeRoom(tiles, x1, y1, x2,  y);
			makeRoom(tiles, x1, y,  x2, y2);
		}
	}



	/**
	 * Sets all Tiles on the perimeter of a rectangle to the specified TileType
	 * 
	 * @param tiles a 2D array of Tiles
	 * @param x1 x-coordinate of one corner of the rectangle
	 * @param y1 y-coordinate of one corner of the rectangle
	 * @param x2 x-coordinate of the opposite corner of the rectangle
	 * @param y2 y-coordinate of the opposite corner of the rectangle
	 * @param t the TileType to which to set the Tiles
	 */
	private static void setRect(Tile[][] tiles, final int x1, final int y1, final int x2, final int y2, final Tile.TileType t)
	{
		if (x2 < x1)
		{
			setRect(tiles, x2, y1, x1, y2, t);
			return;
		}
		if (y2 < y1)
		{
			setRect(tiles, x1, y2, x2, y1, t);
			return;
		}

		for (int x = x1; x <= x2; x++)
		{
			setTile(tiles, x, y1, t);
			setTile(tiles, x, y2, t);
		}
		for (int y = y1; y <= y2; y++)
		{
			setTile(tiles, x1, y, t);
			setTile(tiles, x2, y, t);
		}
	}



	/**
	 * Set a single Tile to the specified TileType
	 * 
	 * @param tiles a 2D array of Tiles
	 * @param x x-coordinate of the Tile to be changed
	 * @param y y-coordinate of the Tile to be changed
	 * @param t the TileType to which to set the Tiles
	 */
	private static void setTile(Tile[][] tiles, final int x, final int y, final Tile.TileType t)
	{
		if (tiles == null)
			return;
		if (y < 0 || y >= tiles.length)
			return;
		if (tiles[y] == null)
			return;
		if (x < 0 || x >= tiles[y].length)
			return;

		tiles[y][x].setType(t);
	}
}
