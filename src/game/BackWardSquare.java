package game;

public class BackWardSquare extends Square {

	private int distance;

	public BackWardSquare(int number) {
		super(number);
	}

	@Override
	public String toString() {
		return "You reach backward square number " + super.getNumber() + ". Roll dice again. To find how many squares you have to go back. ";
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

}
