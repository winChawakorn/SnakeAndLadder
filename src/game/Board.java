package game;

public class Board {
	public static final int SIZE = 101;

	private Square[] squares;

	public Board() {
		squares = new Square[SIZE];
		for (int i = 0; i < squares.length; i++) {
			squares[i] = new Square(i);
		}
		squares[2] = new Ladder(2, 38);
		squares[7] = new Ladder(7, 14);
		squares[8] = new Ladder(8, 31);
		squares[15] = new Ladder(15, 26);
		squares[21] = new Ladder(21, 42);
		squares[16] = new Snake(16, 6);
		squares[28] = new Ladder(28, 84);
		squares[36] = new Ladder(36, 44);
		squares[46] = new Snake(46, 25);
		squares[49] = new Snake(49, 11);
		squares[51] = new Ladder(51, 67);
		squares[62] = new Snake(62, 19);
		squares[60] = new Snake(64, 60);
		squares[71] = new Ladder(71, 91);
		squares[74] = new Snake(74, 53);
		squares[78] = new Ladder(78, 98);
		squares[87] = new Ladder(87, 94);
		squares[89] = new Snake(89, 68);
		squares[92] = new Snake(92, 88);
		squares[95] = new Snake(95, 75);
		squares[99] = new Ladder(99, 80);
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
}
