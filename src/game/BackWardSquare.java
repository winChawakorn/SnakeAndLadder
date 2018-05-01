package game;

import java.io.Serializable;

public class BackWardSquare extends SpecialSquare implements Serializable {

	public BackWardSquare(int number, int destination) {
		super(number, destination);
	}

	@Override
	public String toString() {
		return "You reach backward square number " + super.getNumber() + ". Move back to number "
				+ super.getDestination();
	}

}
