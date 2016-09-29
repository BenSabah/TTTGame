
/**
 * @author Ben Sabah.
 * 
 * TTTGame - this class make and handle an ascii Tic-Tac-Toe game.
 * 
 *         Happy cow says: "Muuuuuuu.."
 */

import java.awt.Point;

public class TTTGame {
	enum Player {
		X, O, NONE
	}

	/**
	 * All the needed fields.
	 */
	private Player winner;
	private boolean gameOver;
	private Point[] winIndexes;
	private Player currentPlayer;
	private Player[][] gameTable;

	public TTTGame() {
		resetGame();
	}

	public void resetGame() {
		winIndexes = null;
		gameTable = null;
		gameOver = false;
		winner = Player.NONE;
		currentPlayer = Player.X;
		gameTable = new Player[3][3];

		// Set the player's game table.
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				gameTable[x][y] = Player.NONE;
			}
		}
	}

	/**
	 * try to place a piece at the given indexes.
	 * 
	 * @param xPos
	 * @param yPos
	 * @return true if placed player successfully, false if failed.
	 */
	// Check if the cell input is valid and return if able to place.
	public boolean tryToPlacePiece(int xPos, int yPos) {
		if (isSelectionValid(xPos, yPos)) {
			gameTable[xPos][yPos] = currentPlayer;
			if (!isGameFinished()) {
				switchPlayer();
			}
			return true;
		}
		return false;
	}

	public boolean isGameFinished() {
		// check if game already ended.
		if (gameOver) {
			// System.out.println("ended cuz' already finished");
			return true;
		}

		// Check columns for a set |||.
		for (int x = 0; x < 3; x++) {
			if (gameTable[x][0] == gameTable[x][1] && gameTable[x][1] == gameTable[x][2]) {
				if (gameTable[x][0] != Player.NONE) {
					// System.out.println("colomn " + x + " won.");
					markWinnerIndexes(x, 0, x, 1, x, 2);
					winner = currentPlayer;
					gameOver = true;
					return true;
				}
			}
		}

		// Check rows for a set ---.
		for (int y = 0; y < 3; y++) {
			if (gameTable[0][y] == gameTable[1][y] && gameTable[1][y] == gameTable[2][y]) {
				if (gameTable[0][y] != Player.NONE) {
					// System.out.println("row " + y + " won.");
					markWinnerIndexes(0, y, 1, y, 2, y);
					winner = currentPlayer;
					gameOver = true;
					return true;
				}
			}
		}

		// Check diagonal \.
		if (gameTable[0][0] == gameTable[1][1] && gameTable[1][1] == gameTable[2][2]) {
			if (gameTable[0][0] != Player.NONE) {
				// System.out.println("top-left to right-buttom diagnal won.");
				markWinnerIndexes(0, 0, 1, 1, 2, 2);
				winner = currentPlayer;
				gameOver = true;
				return true;
			}
		}

		// Check diagonal /.
		if (gameTable[0][2] == gameTable[1][1] && gameTable[1][1] == gameTable[2][0]) {
			if (gameTable[0][2] != Player.NONE) {
				// System.out.println("right-left to top-buttom diagnal won.");
				markWinnerIndexes(0, 2, 1, 1, 2, 0);
				winner = currentPlayer;
				gameOver = true;
				return true;
			}
		}

		// Check if all the cells are filled.
		boolean isTheresEmptyCell = false;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (gameTable[x][y] == Player.NONE) {
					isTheresEmptyCell = true;
				}
			}
		}
		if (!isTheresEmptyCell) {
			winner = Player.NONE;
			gameOver = true;
			return true;
		}

		// If passed all tests, there are still available moves.
		return false;
	}

	// Return the player's enum if asked.
	public Player getWinner() {
		return winner;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * return the current player in given index.
	 * 
	 * @param xPos
	 * @param yPos
	 * @return the player of the given indexes.
	 */
	public Player getPieceInIndex(int xPos, int yPos) {
		if (xPos < 0 || xPos > 2 || yPos < 0 || yPos > 2) {
			return null;
		}
		return gameTable[xPos][yPos];
	}

	/**
	 * return the winner string at any oint of the game.
	 * 
	 * @return the winner string
	 */

	public String getWinnerString() {
		if (winner == Player.X || winner == Player.O) {
			return "המשחק נגמר, ה-" + winner + " ניצח!";
		}

		// if no winner check if game is over.
		if (isGameFinished()) {
			return "תיקו !";
		}
		return "אין עדיין מנצח";
	}

	// Return a array of 3 points that represent the winner's cells, returns
	// null if there's no winner.
	public Point[] getWinnerIndexes() {
		if (winIndexes == null) {
			return null;
		}

		Point[] tmp = new Point[3];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = new Point(winIndexes[i].x, winIndexes[i].y);
		}
		return tmp;
	}

	// Return a string that present the current game.
	public String getCurrentTable() {
		StringBuilder sb = new StringBuilder();
		char tmp = '-';
		for (int y = 0; y < 3; y++) {
			sb.append('|');

			for (int x = 0; x < 3; x++) {
				tmp = '-';
				if (gameTable[x][y] == Player.X) {
					tmp = 'x';
				}
				if (gameTable[x][y] == Player.O) {
					tmp = 'o';
				}
				sb.append(tmp + "|");
			}
			sb.append("\n");
		}
		sb.append("_______");
		return sb.toString();
	}

	// Switch player.
	private void switchPlayer() {
		currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;
	}

	private boolean isSelectionValid(int xPos, int yPos) {
		// Check if game over
		if (gameOver) {
			return false;
		}

		// Check if valid indexes.
		if (xPos < 0 || xPos > 2 || yPos < 0 || yPos > 2) {
			return false;
		}

		// Check if empty cell.
		if (gameTable[xPos][yPos] != Player.NONE) {
			return false;
		}
		return true;
	}

	// Store the 3 cells of the win.
	private void markWinnerIndexes(int x0, int y0, int x1, int y1, int x2, int y2) {
		winIndexes = new Point[3];
		winIndexes[0] = new Point(x0, y0);
		winIndexes[1] = new Point(x1, y1);
		winIndexes[2] = new Point(x2, y2);
	}
}