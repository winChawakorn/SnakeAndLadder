package game;

public class FreezeSquare extends Square{

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
