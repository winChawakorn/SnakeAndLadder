
public class Game {

	private Player[] players;
	private Die die;
	private Board board;
	private int currentPlayerIndex;
	private boolean ended;

	public Game() {
		players = new Player[2];
		players[0] = new Player("P1");
		players[1] = new Player("P2");
		die = new Die();
		board = new Board();
		ended = false;

		board.addPiece(players[0].getPiece(), 0);
		board.addPiece(players[1].getPiece(), 0);
	}

	public boolean isEnd() {
		return ended;
	}

	public void end() {
		ended = true;
	}

	public Player currentPlayer() {
		return players[currentPlayerIndex];
	}

	public void switchPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
	}

	public String currentPlayerName() {
		return currentPlayer().getName();
	}

	public int currentPlayerPosition() {
		return board.getPiecePosition(currentPlayer().getPiece());
	}

	public int currentPlayerRollDice() {
		return currentPlayer().roll(die);
	}

	public void currentPlayeMovePiece(int steps) {
		currentPlayer().movePiece(board, steps);
	}

	public boolean currentPlayerWins() {
		return board.pieceIsAtGoal(currentPlayer().getPiece());
	}
}
