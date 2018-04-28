package game;

public abstract class SpecialSquare extends Square {

	private int steps;

	public SpecialSquare(int number, int steps) {
		super(number);
		this.steps = steps;
	}

	public int getSteps() {
		return steps;
	}

	public abstract String toString();
}
