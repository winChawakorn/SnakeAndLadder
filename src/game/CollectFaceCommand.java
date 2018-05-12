package game;

public class CollectFaceCommand extends Command {

	public CollectFaceCommand(int face, int playerIndex, int posBeforeMove, Player player) {
		super(face, playerIndex, posBeforeMove, player);
	}

	@Override
	public void execute(Board board) {
		if (getPosBeforeMove() + getFace() <= 100)
			getPlayer().movePiece(board, getFace());
		else
			getPlayer().movePiece(board, (100 - (getPosBeforeMove() + getFace()) % 100) - getPosBeforeMove());

	}

}
