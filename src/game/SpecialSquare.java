package game;

public abstract class SpecialSquare extends Square {

	private int destination;

	public SpecialSquare(int number, int destination) {
		super(number);
		this.destination = destination;
	}

	public int getDestination() {
		return destination;
	}

	public abstract String toString();
}
