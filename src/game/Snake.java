package game;

public class Snake extends SpecialSquare {

	public Snake(int number, int destination) {
		super(number, destination);
	}

	@Override
	public String toString() {
		return "Oh no! you found a Snake!. Move back to number "+ super.getDestination();
	}
}
