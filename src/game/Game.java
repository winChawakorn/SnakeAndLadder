package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Game {

	private Player[] players;
	private Die die;
	private Board board;
	private int currentPlayerIndex;
	private boolean ended;
	private List<Color> colorlist;

	public Game(int numPlayer) {
		players = new Player[numPlayer];
		board = new Board();
		die = new Die();
		ended = false;
		colorlist = new ArrayList<Color>();
		colorlist.add(Color.RED);
		colorlist.add(Color.BLUE);
		colorlist.add(Color.GREEN);
		colorlist.add(Color.YELLOW);
		
		for (int i = 0; i < numPlayer; i++) {
			players[i] = new Player("P" + (i + 1),colorlist.get(i));
			board.addPiece(players[i].getPiece(), 0);
		}
	}

	public int getNumPlayer() {
		return players.length;
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

	public Square currentPlayerSquare() {
		return board.getSquare(currentPlayer().getPiece());
	}

	public boolean currentPlayerWins() {
		return board.pieceIsAtGoal(currentPlayer().getPiece());
	}
}
