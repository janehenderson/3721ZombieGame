package JaneVsZombie;

import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;

/**
 * Controller Class to handle key input from the view, to update the model
 * 
 * @author Jane Henderson & Evan MacNeil
 *
 */
public class Controller {

	// class variables
	private Board board;
	private GUIView view;
	
	/**
	 * Controller constructor
	 */
	public Controller() 
	{
		board = new Board();
		view = new GUIView(board, this);
	} // end constructor

	/**
	 * method requestMoveUp
	 * 
	 * method requests for jane to move up in model, and moves zombies
	 * Checks to see if the game over condition has been reached
	 */
	public void requestMoveUp() 
	{
		Jane jane = board.getJane();
		
		jane.moveUp();
		board.moveHorde();
		checkGameOver();
	} // end requestMoveUp
	
	/**
	 * method requestMoveDown
	 * 
	 * method requests for jane to move down in model, and moves zombies
	 * Checks to see if the game over condition has been reached
	 */
	public void requestMoveDown() 
	{
		Jane jane = board.getJane();
		
		jane.moveDown();
		board.moveHorde();
		checkGameOver();
	} // end requestMoveDown
	
	
	/**
	 * method requestMoveLeft
	 * 
	 * method requests for jane to move up in model, and moves zombies
	 * Checks to see if the game over condition has been reached
	 */
	public void requestMoveLeft() 
	{
		Jane jane = board.getJane();
		
		jane.moveLeft();
		board.moveHorde();
		checkGameOver();		
	} // end requestMoveLeft
	
	/**
	 * method requestMoveRight
	 * 
	 * method requests for jane to move up in model, and moves zombies
	 * Checks to see if the game over condition has been reached
	 */
	public void requestMoveRight() 
	{
		Jane jane = board.getJane();
		
		jane.moveRight();
		board.moveHorde();
		checkGameOver();
	} // end requestMoveRight
	
	/**
	 * method gameOver
	 * 
	 * Method if user loses, to create a dialog box and end game
	 * if user chooses to play again, it creates a new board
	 * if user chooses not to play again, it exits
	 */
	public void gameOver()
	{
		int k = JOptionPane.showConfirmDialog(
			view,
			"Would you like to play again?",
			"You Lose!",
			JOptionPane.YES_NO_OPTION);

		if (k == JOptionPane.YES_OPTION) 
			newGame();
		if (k == JOptionPane.NO_OPTION) 
			quitGame();
	}

	/**
	 * Checks if Jane's health has reached zero. If so, proceeds to the
	 * game over menu.
	 */
	public void checkGameOver()
	{
		if (board.getJane().getHealth() == 0)
			gameOver();
	}
	
	/**
	 * Constructs a new Board, also passing a reference of it to the view.
	 */
	public void newGame()
	{
		board = new Board();
		view.setBoard(board);
	}

	/**
	 * Exits the game.
	 */
	public void quitGame()
	{
		view.setVisible(false);
		view.dispose();
		System.exit(0);
	}

	/**
	 * onPress method
	 * 
	 * Takes key input of arrow keys to move character
	 * 
	 * @param KeyEvent e
	 */
	public void onPress(KeyEvent e)
	{
		int key = e.getKeyCode();
		switch (key)
		{
		case KeyEvent.VK_KP_UP:
		case KeyEvent.VK_UP:
			requestMoveUp();
			break;
		case KeyEvent.VK_KP_DOWN:
		case KeyEvent.VK_DOWN:
			requestMoveDown();
			break;
		case KeyEvent.VK_KP_LEFT:
		case KeyEvent.VK_LEFT:
			requestMoveLeft();
			break;
		case KeyEvent.VK_KP_RIGHT:
		case KeyEvent.VK_RIGHT:
			requestMoveRight();
			break;
		case KeyEvent.VK_N:
			newGame();
			break;
		case KeyEvent.VK_Q:
		case KeyEvent.VK_ESCAPE:
			quitGame();
			break;
		}
	} // end onPress
} // end Controller class
