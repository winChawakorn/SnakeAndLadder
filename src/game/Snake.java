package game;

import java.io.Serializable;

public class Snake extends SpecialSquare implements Serializable {

	public Snake(int number, int destination) {
		super(number, destination);
	}

	@Override
	public String toString() {
		return "Oh no! you found a Snake!";
	}
}
