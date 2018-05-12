package game;

import java.io.Serializable;

public class CollectFaceCommand extends Command implements Serializable {

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
