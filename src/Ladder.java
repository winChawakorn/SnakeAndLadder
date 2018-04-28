
public class Ladder extends SpecialSquare {

	public Ladder(int number, int steps) {
		super(number, steps);
		System.out.println("init steps = " + steps);
		System.out.println("super steps = " + super.getSteps());
	}

	@Override
	public String toString() {
		return "Ladder";
	}
}
