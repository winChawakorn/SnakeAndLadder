package game;

import java.io.Serializable;
import java.util.Scanner;

public class ConsoleUI implements Serializable {

	private Scanner scanner = new Scanner(System.in);

	public void start() {
		int numPlayer = 0;
		while (true) {
			System.out.print("Please input the number of players(2 or 3 or 4): ");
			try {
				numPlayer = Integer.parseInt(scanner.nextLine());
				if (numPlayer < 2 || numPlayer > 4)
					throw new NumberFormatException();
				break;
			} catch (NumberFormatException e) {
				System.out.println("Number of players must be 2 or 3 or 4");
				System.out.println("----------------------------------------------------");
			}
		}
		Game game = new Game(numPlayer);
		while (!game.isEnd()) {
			System.out.println("------------------");
			System.out.println(game.currentPlayer().getName() + "'s turn");
			int position = game.currentPlayerPosition();
			System.out.println("Position: " + position);
			// TODO if player is on the FREEZE QUARE, print something and continue;
			if (game.currentPlayerSquare() instanceof FreezeSquare) {
				FreezeSquare freeze = (FreezeSquare) game.currentPlayerSquare();
				if(game.currentPlayerFreeze()){
					game.switchPlayer();
					game.currentPlayer().setFreeze(false);
					continue;
				}else
					game.currentPlayer().setFreeze(true);
			}
			System.out.println("Please hit enter to roll a die.");
			String enter = scanner.nextLine();
			int face = game.currentPlayerRollDice();
			if (!enter.equals("")) {
				face = Integer.parseInt(enter);
			}
			System.out.println("Dice face = " + face);
			game.currentPlayeMovePiece(face);
			if (game.currentPlayerWins()) {
				System.out.println(
						"New position: " + game.currentPlayerPosition() + " " + game.currentPlayerName() + " WINS!");
				game.end();
				while (true) {
					System.out.print("Menu (p)lay again, (r)eplay, (e)xit : ");
					String command = scanner.nextLine().toLowerCase();
					switch (command) {
					case "p":
						start();
						return;
					case "r":
						// TODO REPLAY
						return;
					case "e":
						System.out.println("Bye");
						return;
					default:
						System.out.println(command + " command not found");
						System.out.println("-----------------------------------------");
						break;
					}
				}
			} else {
				Square square = game.currentPlayerSquare();
				if (square instanceof SpecialSquare) {
					System.out.println(square.toString());
					SpecialSquare ss = (SpecialSquare) square;
					game.currentPlayeMovePiece(ss.getDestination() - game.currentPlayerPosition());
				}
				System.out.println("New position: " + game.currentPlayerPosition());
				game.switchPlayer();
			}
		}
	}

	public static void main(String[] args) {
		new ConsoleUI().start();
	}
}
