package JaneVsZombie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * GUIView class
 * 
 * Class to construct and update the GUI used to view the game
 * 
 * @author Jane Henderson & Evan MacNeil
 *
 */
public class GUIView extends JFrame {

	// class variables
	private Board board;
	private Controller cntrl;
	JPanel[][] positions;
	int h = Board.HEIGHT;
	int w = Board.WIDTH;
	JPanel layout, grid, info;
	JTextField score;
	JTextField health;
	Tile[][] tileArray;
	Pear[] pear;
	Zombie[] horde;
	Jane jane;
	
	/**
	 * GUIView constructor
	 * 
	 * @param Board bd, Controller ct
	 */
	public GUIView(Board bd, Controller ct) {
		
		super("Jane vs. Zombie");
		positions = new JPanel[h][w];
		board = bd;
		cntrl = ct;

		layout = new JPanel(new BorderLayout(0, 0));
		grid = new JPanel(new GridLayout(h, w));
		info = new JPanel(new FlowLayout());

		grid.setPreferredSize(new Dimension(16 * Board.WIDTH, 16 * Board.HEIGHT));
		info.setPreferredSize(new Dimension(16 * Board.WIDTH, 32));
		info.setBackground(Color.BLACK);

		layout.add(grid, BorderLayout.PAGE_START);
		layout.add(info, BorderLayout.PAGE_END);
		
		setPreferredSize(new Dimension(16 * Board.WIDTH, 16 * Board.HEIGHT + 32));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.createDisplay();
		this.requestFocusInWindow();

		// add key listener
		this.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e)
			{
				cntrl.onPress(e);
				updateDisplay();
			}
			public void keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){}
		});// end of adding key listener
	} // end constructor

	/**
	 * createDisplay method
	 * 
	 * Method to initialize and create componenets of the GUI
	 */
	public void createDisplay() {
		
		tileArray = board.getTiles();
		pear = board.getPears();
		horde = board.getHorde();
		jane = board.getJane();
	
		int x;
		int y; 
		
		// nested for loop to add JPanels for all grid areas, using row first order
		for (int i = 0; i < h; i++) 
		{
			for (int j = 0; j < w; j++) 
			{
				positions[i][j] = new JPanel();
				positions[i][j].setBackground(Color.BLACK);
				grid.add(positions[i][j]);
			}
		} // end nested for loop

		// nested for loop to change color of different tile types, using row first order
		for (int i = 0; i < h; i++) 
		{
			for (int j = 0; j < w; j++) 
			{
				if (tileArray[i][j].getType() == Tile.TileType.TILE_FLOOR)
				{
					positions[i][j].setBackground(Color.BLACK);
				}
				else 
				{
					positions[i][j].setBackground(Color.BLUE);
				}
			}
		} // end nested for loop
		
		// for all pears to change color of position occupying pear
		for (int i = 0; i < pear.length; i++) 
		{
			y = pear[i].getY();
			x = pear[i].getX();
			
			positions[y][x].setBackground(Color.YELLOW);
		} // end for
		
		// for all zombies, to change color of position occupying zombie
		for (int i = 0; i < horde.length; i++) 
		{
			y = horde[i].getY();
			x = horde[i].getX();
			
			positions[y][x].setBackground(Color.RED);
		} // end for
		
		y = jane.getY();
		x = jane.getX();
		
		// set position of jane to pink
		positions[y][x].setBackground(Color.PINK);
		
		// score and health fields
		score = new JTextField("Score: " + board.getScore());
		health = new JTextField("Health: " + jane.getHealth());
		
		score.setEditable(false);
		health.setEditable(false);
		
		// add score and health
		info.add(score);
		info.add(health);

		setResizable(false);
		setContentPane(layout);

		pack();
		this.requestFocusInWindow();
		setVisible(true);
	} // end createDisplay
	
	
	/**
	 * updateDisplay method
	 * 
	 * Method to redraw the display after the Board changes.
	 */
	public void updateDisplay() {
		
		tileArray = board.getTiles();
		pear = board.getPears();
		horde = board.getHorde();
		jane = board.getJane();
		
		// nested for loop to change color of different tile types, using row first order
		for (int i = 0; i < h; i++) 
		{
			for (int j = 0; j < w; j++)
			{
				if (tileArray[i][j].getType() == Tile.TileType.TILE_FLOOR)
				{
					positions[i][j].setBackground(Color.BLACK);
				}
				else 
				{
					positions[i][j].setBackground(Color.BLUE);
				}
			}
		} // end nested for
		
		int x;
		int y; 
		
		// for all pears to change color of position occupying pear
		for (int i = 0; i < pear.length; i++) 
		{
			y = pear[i].getY();
			x = pear[i].getX();
		
			positions[y][x].setBackground(Color.YELLOW);
		} // end for 
		
		// for all zombies, to change color of position occupying zombie
		for (int i = 0; i < horde.length; i++) 
		{
			y = horde[i].getY();
			x = horde[i].getX();
		
			positions[y][x].setBackground(Color.RED);
		} // end for
		
		y = jane.getY();
		x = jane.getX();
		
		// set position of jane to pink
		positions[y][x].setBackground(Color.PINK);
		
		// set score and health fields
		score.setText("Score: " + board.getScore());
		health.setText("Health: " + jane.getHealth());
		
		// if health of jane is 0, end game
		/*if (jane.getHealth() == 0) 
		{
			//cntrl.endGame(this);
			cntrl.endGame
		}*/
			
		pack();
		this.requestFocusInWindow();

	} // end updateDisplay

	/**
	 * Sets the Board to be displayed.
	 *
	 * @param b the new Board to be displayed
	 */
	public void setBoard(Board b)
	{
		board = b;
	}
	
} // end GUIView class
