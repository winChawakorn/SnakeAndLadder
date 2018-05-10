package game;

public abstract class Command {
	private int face;
	private Player player;
	
	public int getFace() {
		return this.face;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Command(int face, Player player) {
		this.player = player;
		this.face = face;
	}
	
	public abstract void execute(Board board);
}
