package game;

public class CollectFaceCommand extends Command{

	public CollectFaceCommand(int face, Player player) {
		super(face, player);
	}

	@Override
	public void execute(Board board) {
		getPlayer().movePiece(board, getFace());
		
	}

}
