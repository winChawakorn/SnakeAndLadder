package game;

import java.io.Serializable;

public class FreezeSquare extends Square implements Serializable {

	public int skipedCount;

	public FreezeSquare(int number) {
		super(number);
		skipedCount = 1;
	}

	@Override
	public String toString() {
		return "You reach Freeze square number" + super.getNumber() + ", you are skiped one turn";
	}

}
