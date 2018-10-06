package techclub.tictactoe;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Gui extends JFrame implements ActionListener {
	// Note: The only objects allowed to have this class as their ActionListener are the 9 buttons.
	
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private GridLayout layout;
	
	// Indicates whether it is X's turn
	private boolean xTurn;
	
	// The buttons array contains the 9 buttons on the board. It is a 3 x 3
	// array, where each inner array is a row on the board. The elements
	// of each inner array represent a cell in the row.
	private JButton[][] buttons;
	
	private Board board;	// this is also a singleton
	
	/** The singleton instance */
	private static Gui instance;
	
	/**
	 * Creates and returns the singleton instance if it has not already been created. If 
	 * the instance is not null, then the method simply returns the instance.
	 */
	public static Gui createOrGetInstance() {
		if (instance == null) {
			instance = new Gui();
			instance.initializeGui();
		}
		return instance;
	}
	
	private Gui() {}
	
	/** Initializes the Gui with the specified frame width and height. */
	private void initializeGui() {
		if (buttons == null) {
			this.setPreferredSize(new Dimension(400, 400));
			this.pack();
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setTitle("Tic Tac Toe");
			this.setVisible(true);
		
			this.panel = new JPanel();
			this.layout = new GridLayout(3, 3);
			this.panel.setLayout(layout);
			this.add(panel);
			
			board = Board.getInstance();
			
			// X starts first.
			xTurn = true;
			
			createButtons();
		}
	}
	
	private void createButtons() {
		this.buttons = new JButton[3][3];
		for (int row = 0; row < 3; row++) {
			for (int cell = 0; cell < 3; cell++) {
				JButton button = new JButton();
				buttons[row][cell] = button;
				button.addActionListener(this);
				button.setActionCommand(row + " " + cell);
				panel.add(buttons[row][cell]);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		int row = Integer.valueOf(button.getActionCommand().split(" ")[0]);
		int cell = Integer.valueOf(button.getActionCommand().split(" ")[1]);
		if (board.getButtonStates()[row][cell] == 0) {
			if (xTurn) {
				buttons[row][cell].setIcon(new ImageIcon(this.getClass().getResource("/X.png")));
				xTurn = false;
			} else {
				buttons[row][cell].setIcon(new ImageIcon(this.getClass().getResource("/O.png")));
				xTurn = true;
			}
			board.updateButtonState(button, !xTurn, row, cell);
		}
	}
	
	/**
	 * Resets all button icons to <code>null</code>, and calls {@link Board#resetButtonStates()}.
	 */
	public void reset() {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].setIcon(null);
			}
		}
		board.resetButtonStates();
	}
	
	/**
	 * Displays a popup window that indicates how the game ended - either in a tie or a win for one player.
	 * The players get to choose whether to play again or not. If the "No" button is pressed, the program
	 * exits. Nothing happens if the user tries to close the window using the "X" button in the top
	 * right hand corner.
	 */
	public void displayGameOverWindow(String message) {
		JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION);
		JDialog dialog = pane.createDialog("Game Over!");
		dialog.setContentPane(pane);
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);
		
		if ((int) pane.getValue() == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
	}
}
