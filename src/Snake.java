
public class Snake extends SpecialSquare {

	public Snake(int number, int steps) {
		super(number, steps);
	}

	@Override
	public int getSteps() {
		if (super.getSteps() > 0)
			return super.getSteps() * -1;
		return super.getSteps();
	}

	@Override
	public String toString() {
		return "Snake";
	}
}
