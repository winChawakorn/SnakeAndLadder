package game;

import java.io.Serializable;

public class BackwardSquare extends Square implements Serializable {

	public BackwardSquare(int number) {
		super(number);
	}

	@Override
	public String toString() {
		return "Backward square!!\n Roll dice again and go back.";
	}

}
