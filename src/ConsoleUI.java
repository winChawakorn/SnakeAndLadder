import java.util.Scanner;

public class ConsoleUI {

	private Scanner scanner = new Scanner(System.in);

	public void start(Game game) {
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
				System.out.println("Position: " + game.currentPlayerName() + "WINS!");
				game.end();
			} else {
				game.switchPlayer();
			}
		}
	}

	public static void main(String[] args) {
		new ConsoleUI().start(new Game());
	}
}
