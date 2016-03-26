
/**
 * @author Ben Sabah.
 * 
 * GuiTTTGame - this class make and handle a GUI tic-tac-toe game.
 * 
 *         Happy cow says: "Muuuuuuu.."
 */

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToggleButton;

@SuppressWarnings("serial")
public class GuiTTTGame extends JFrame {
	TTTGame curGame = new TTTGame();
	private JPanel panel = new JPanel();
	private JButton resetButton = new JButton();
	private Color buttonBaseColor = resetButton.getBackground();
	private JToggleButton[][] buttons = new JToggleButton[3][3];
	static Point screenSize = new Point(225, 265);

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		String mode = (args.length != 0) ? args[0] : "";
		GuiTTTGame tttGame = new GuiTTTGame(mode);
	}

	public GuiTTTGame(String mode) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(550, 250);
		this.setSize(screenSize.x, screenSize.y);
		this.setTitle("איקס עיגול");
		setButtons();
		this.add(panel);
		this.setVisible(true);
		this.setResizable(false);
	}

	private void setButtons() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				buttons[x][y] = new JToggleButton();
				buttons[x][y].setSize(50, 50);
				buttons[x][y].addActionListener(new ButtonMonitor());
				buttons[x][y].setLocation(30 + x * 55, 10 + y * 55);
				this.add(buttons[x][y]);
			}
		}

		resetButton.setText("אפס משחק");
		resetButton.setSize(200, 50);
		resetButton.setLocation(10, 178);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				resetGame();
			}
		});
		this.add(resetButton);
	}

	private void resetGame() {
		curGame.resetGame();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				buttons[x][y].setText("");
				buttons[x][y].setEnabled(true);
				buttons[x][y].setBackground(buttonBaseColor);

				if (buttons[x][y].isSelected()) {
					buttons[x][y].doClick();
				}
			}
		}
		resetButton.setText("אפס משחק");
	}

	private class ButtonMonitor implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// Getting the button that created the event.
			JToggleButton curButton = (JToggleButton) event.getSource();
			boolean selected = curButton.getModel().isSelected();

			// TODO add more buttons here.
			if (selected) {
				// If pressed, rename & deactivate the button.
				curButton.setText(curGame.getCurrentPlayer() + "");
				curButton.setEnabled(false);

				// Check which button was pressed.
				for (int x = 0; x < 3; x++) {
					for (int y = 0; y < 3; y++) {
						if (curButton == buttons[x][y]) {
							curGame.tryToPlacePiece(x, y);
							colorButton(curButton, Color.BLACK);
							break;
						}
					}
				}

				// push-down all the buttons if the game is over.
				if (curGame.isGameFinished()) {
					finishTasks();
				}
			}
		}
	}

	private void finishTasks() {
		// Painting the winning buttons.
		Point[] winIdexes = curGame.getWinnerIndexes();
		if (winIdexes != null) { // just in case.
			colorButton(buttons[winIdexes[0].x][winIdexes[0].y], Color.RED);
			colorButton(buttons[winIdexes[1].x][winIdexes[1].y], Color.RED);
			colorButton(buttons[winIdexes[2].x][winIdexes[2].y], Color.RED);
		}
		setAllGameButtonsTo(false);
		resetButton.setText(curGame.getWinnerHebrewString());
	}

	private void colorButton(JToggleButton curButton, Color color) {
		if (curButton != null && color != null) {
			curButton.setBackground(color);
		}
	}

	private void setAllGameButtonsTo(boolean state) {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				buttons[x][y].setEnabled(state);
			}
		}
	}
}