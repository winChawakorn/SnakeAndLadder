package game;

import java.io.Serializable;

public class BackwardSquare extends Square implements Serializable {

	private int distance;

	public BackwardSquare(int number) {
		super(number);
	}

	@Override
	public String toString() {
		return "Backward square!!\n Roll dice again and go back.";
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

}
