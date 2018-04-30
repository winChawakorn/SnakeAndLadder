package game;

public class FreezeSquare extends SpecialSquare {

	public int skipedCount;
	
	public FreezeSquare(int number, int steps) {
		super(number, steps);
		skipedCount = 1;
	}

	@Override
	public String toString() {
		return "You reach Freeze square number" + super.getNumber() + ", you are skiped one turn";
	}

}
