package game;

import java.io.Serializable;

public class Ladder extends SpecialSquare implements Serializable {

	public Ladder(int number, int destination) {
		super(number, destination);
	}

	@Override
	public String toString() {
		return "Great! you found a Ladder!";
	}
}
