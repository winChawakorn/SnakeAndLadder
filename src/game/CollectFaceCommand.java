package game;

public class CollectFaceCommand extends Command{

	public CollectFaceCommand(int face, int playerIndex,int posBeforeMove,Player player) {
		super(face, playerIndex,posBeforeMove,player);
	}

	@Override
	public void execute(Board board) {
		getPlayer().movePiece(board, getFace());
		
	}

}
