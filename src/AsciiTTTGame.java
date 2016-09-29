
/**
 * @author Ben Sabah.
 * 
 * RunAsciiTTTGame - this class starts an ascii Tic-Tac-Toe game and operate it.
 * 
 *         Happy cow says: "Muuuuuuu.."
 */

import java.util.Scanner;

public class AsciiTTTGame {
	public static void main(String[] args) {

		// Setting up the game.
		TTTGame curGame = new TTTGame();
		Scanner scan = new Scanner(System.in);
		System.out.println("This is a new TTT game");

		while (!curGame.isGameFinished()) {
			// Ask for index to place player sign.
			System.out.print("its " + curGame.getCurrentPlayer() + " turn, please type position: ");
			int xPos = scan.nextInt();
			int yPos = scan.nextInt();

			// try to place the sign and respond if not available.
			if (!curGame.tryToPlacePiece(xPos, yPos)) {
				System.out.println("that position is marked / unavailable, please try again.");
			}

			// Print the current game table.
			System.out.println(curGame.getCurrentTable());
		}

		// Close the scanner.
		scan.close();

		// Output according to result
		System.out.println(curGame.getWinnerString());
	}
}
