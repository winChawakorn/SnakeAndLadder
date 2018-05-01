package game;

import java.io.Serializable;

public class BackWardSquare extends Square implements Serializable {

	private int distance;

	public BackWardSquare(int number) {
		super(number);
	}

	@Override
	public String toString() {
		return "You reach backward square number " + super.getNumber() + ". Move back to number "
				+ (super.getNumber() - this.distance);
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

}
