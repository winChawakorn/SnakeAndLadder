package game;

public class Ladder extends SpecialSquare {

	public Ladder(int number, int destination) {
		super(number, destination);
	}

	@Override
	public String toString() {
		return "Great! you found a Ladder!. Move to number " + super.getDestination();
	}
}
