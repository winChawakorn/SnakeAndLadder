package game;

import java.io.Serializable;

public class FreezeSquare extends Square implements Serializable{

	
	public FreezeSquare(int number) {
		super(number);
	}


	@Override
	public String toString() {
		return "You reach Freeze square number " + super.getNumber() + ", you are skiped one turn";
	}

}
