package game;

public abstract class Command {
	private int face;
	private int playerIndex;
	private int posBeforeMove;
	private Player player;
	
	public int getFace() {
		return this.face;
	}
	
	public int getPlayerIndex() {
		return this.playerIndex;
	}
	
	public int getPosBeforeMove() {
		return this.posBeforeMove;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Command(int face, int playerIndex,int posBeforeMove, Player player) {
		this.playerIndex = playerIndex;
		this.face = face;
		this.posBeforeMove = posBeforeMove;
		this.player = player;
	}
	
	public abstract void execute(Board board);
}
