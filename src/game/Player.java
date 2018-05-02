package game;

import java.awt.Color;

import java.io.Serializable;

public class Player implements Serializable {
	private String name;
	private Piece piece;
	private Color color;
	private boolean freeze;

	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
		piece = new Piece();
		freeze = false;
	}

	public boolean isFreeze() {
		return freeze;
	}

	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
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

	public Color getColor() {
		return this.color;
	}
}
