package game;

import java.io.Serializable;

public class Player implements Serializable {
	private String name;
	private Piece piece;

	public Player(String name) {
		this.name = name;
		piece = new Piece();
	}

	public int roll(Die die) {
		die.roll();
		return die.getFace();
	}

	public String getName() {
		return name;
	}

	public void movePiece(Board board, int steps) {
		board.movePiece(piece, steps);
	}

	public Piece getPiece() {
		return piece;
	}
}
