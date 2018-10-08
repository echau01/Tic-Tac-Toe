package techclub.tictactoe;

import javax.swing.JButton;

public class Board {
	// Represents the state of each button in the buttons array. 
	// 0 represents that the button has no icon.
	// 1 represents that the button is an X.
	// 2 represents that the button is an O.
	// Each button's state is located in buttonStates[row][cell], where
	// row and cell indicate the location of the button on the board grid.
	private final byte[][] buttonStates;
	
	private static final Board board = new Board();	// singleton instance
	
	private Gui gui;
	
	/**
	 * Returns the singleton instance of the Board
	 */
	public static Board getInstance() {
		return board;
	}
	
	private Board() {
		this.buttonStates = new byte[3][3];
		this.gui = Gui.getInstance();
	}
	
	/**
	 * Updates the specified JButton's state in the buttonStates array. The isX parameter
	 * indicates whether the JButton is an X or an O; if it is true, the button is an X button.
	 * The row and cell parameters indicate which row and cell the button is located in.
	 * 
	 * The method also makes a call to {@link #checkIfGameEnded()}.
	 */
	public void updateButtonState(JButton button, boolean isX, int row, int cell) {
		if (isX) {
			buttonStates[row][cell] = 1;
		} else {
			buttonStates[row][cell] = 2;
		}
		checkIfGameEnded();
	}
	
	/**
	 * Checks if the game has ended, i.e. if a player won, or if the game ended in a tie.
	 */
	public void checkIfGameEnded() {
		// This method is an absolute mess but I don't know how to simplify it
		
		for (int i = 0; i < 3; i++) {
			// Check the 3 horizontal rows for a three-in-a-row
			if (buttonStates[i][0] != 0) {
				if (buttonStates[i][0] == buttonStates[i][1] && buttonStates[i][1] == buttonStates[i][2]) {
					byte state = buttonStates[i][0];
					if (state == 1) {	// X Wins
						gui.displayGameOverWindow("X wins the game!\nPlay again?");
					} else {	// O Wins
						gui.displayGameOverWindow("O wins the game!\nPlay again?");
					}
					gui.reset();
					return;	// exit the method
				}
			}
			
			// Check the 3 vertical rows for a three-in-a-row
			if (buttonStates[0][i] != 0) {
				if (buttonStates[0][i] == buttonStates[1][i] && buttonStates[1][i] == buttonStates[2][i]) {
					byte state = buttonStates[0][i];
					if (state == 1) {	// X Wins
						gui.displayGameOverWindow("X wins the game!\nPlay again?");
					} else {	// O Wins
						gui.displayGameOverWindow("O wins the game!\nPlay again?");
					}
					gui.reset();
					return;	// exit the method
				}
			}
		}
		
		// Check the 2 diagonals for a three-in-a-row
		if (buttonStates[1][1] != 0) {
			if ((buttonStates[0][0] == buttonStates[1][1] && buttonStates[1][1] == buttonStates[2][2])
					|| (buttonStates[0][2] == buttonStates[1][1] && buttonStates[1][1] == buttonStates[2][0])) {
				byte state = buttonStates[1][1];
				if (state == 1) {	// X Wins
					gui.displayGameOverWindow("X wins the game!\nPlay again?");
				} else {	// O Wins
					gui.displayGameOverWindow("O wins the game!\nPlay again?");
				}
				gui.reset();
				return;	// exit the method
			}
		}
		
		if (gameIsATie()) {	// The game ended in a tie.
			gui.displayGameOverWindow("The game is a draw!\nPlay again?");
			gui.reset();
		}
	}
	
	/**
	 * Checks if the game is a tie, i.e. if all elements of buttonStates are no
	 * longer equal to 0.
	 */
	private boolean gameIsATie() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (buttonStates[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Resets all of the values in the buttonStates array to 0.
	 */
	public void resetButtonStates() {
		for (byte[] row : buttonStates) {
			for (int j = 0; j < row.length; j++) {
				row[j] = 0;
			}
		}
	}
	
	public byte[][] getButtonStates() {
		return this.buttonStates;
	}
}
