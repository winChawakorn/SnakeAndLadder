package game;

import java.io.Serializable;

public class Game implements Serializable {

	private Player[] players;
	private Die die;
	private Board board;
	private int currentPlayerIndex;
	private int previousPlayerPosition;
	private boolean ended;
	private int numPlayer;

	public Game(int numPlayer) {
		this.numPlayer = numPlayer;
		players = new Player[numPlayer];
		board = new Board();
		die = new Die();
		ended = false;
		for (int i = 0; i < numPlayer; i++) {
			players[i] = new Player("P" + (i + 1));
			board.addPiece(players[i].getPiece(), 0);
		}
	}

	public int getNumPlayer() {
		return numPlayer;
	}

	public void setNumPlayer(int numPlayer) {
		this.numPlayer = numPlayer;
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

	public int previousPlayerPosition() {
		return previousPlayerPosition;
	}

	public void switchPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
	}

	public int currentPlayerIndex() {
		return currentPlayerIndex;
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
		previousPlayerPosition = currentPlayerPosition();
		currentPlayer().movePiece(board, steps);
	}

	public Square currentPlayerSquare() {
		return board.getSquare(currentPlayer().getPiece());
	}

	public boolean currentPlayerWins() {
		return board.pieceIsAtGoal(currentPlayer().getPiece());
	}
}
