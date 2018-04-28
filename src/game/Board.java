package game;

public class Board {
	public static final int SIZE = 64;

	private Square[] squares;

	public Board() {
		squares = new Square[SIZE];
		for (int i = 0; i < squares.length; i++) {
			squares[i] = new Square(i);
		}
		squares[4] = new Ladder(4, 8);
		squares[squares.length - 1].setGoal(true);
	}

	public void addPiece(Piece piece, int pos) {
		squares[pos].addPiece(piece);
	}

	public void movePiece(Piece piece, int steps) {
		int pos = getPiecePosition(piece);
		squares[pos].removePiece(piece);
		int newPose = pos + steps;
		if (newPose >= squares.length) {
			newPose = squares.length - 1;
		}
		addPiece(piece, newPose);
	}

	public int getPiecePosition(Piece piece) {
		for (Square s : squares) {
			if (s.hasPiece(piece)) {
				return s.getNumber();
			}
		}
		return -1;
	}

	public boolean pieceIsAtGoal(Piece piece) {
		return squares[getPiecePosition(piece)].isGoal();
	}

	public Square getSquare(Piece piece) {
		return squares[getPiecePosition(piece)];
	}

	// public boolean pieceIsOnSpecialSquare(Piece piece) {
	// return squares[getPiecePosition(piece)].
	// }
}
