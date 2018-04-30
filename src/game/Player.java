package game;

import java.awt.Color;

public class Player {
	private String name;
	private Piece piece;
	private Color color;

	public Player(String name,Color color) {
		this.name = name;
		this.color = color;
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
	
	public Color getColor() {
		return this.color;
	}
}
