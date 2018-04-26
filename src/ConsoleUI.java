import java.util.Scanner;

public class ConsoleUI {

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
			System.out.println("Position: " + game.currentPlayerPosition());
			System.out.println("Please hit enter to roll a die.");
			scanner.nextLine();
			int face = game.currentPlayerRollDice();
			System.out.println("Dice face = " + face);
			game.currentPlayeMovePiece(face);
			if (game.currentPlayerWins()) {
				System.out.println("Position: " + game.currentPlayerName() + " WINS!");
				game.end();
				while (true) {
					System.out.print("Menu (p)lay again, (r)eplay, (e)xit : ");
					String command = scanner.nextLine().toLowerCase();
					switch (command) {
					case "p":
						start();
						return;
					case "r":
						// TODO replay
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
				game.switchPlayer();
			}
		}
	}

	public static void main(String[] args) {
		new ConsoleUI().start();
	}
}
