package game;

public class FreezeSquare extends Square {

	private int skipedCount;

	public FreezeSquare(int number) {
		super(number);
		skipedCount = 1;
	}
	
	public int getSkipedCount() {
		return skipedCount;
	}

	/**
	 * 
	 * @param skipedCount
	 *            is 1 current player must be skipped one turn, otherwise means
	 *            current player have already been skipped
	 */
	public void setSkipedCount(int skipedCount) {
		this.skipedCount = skipedCount;
	}


	@Override
	public String toString() {
		return "You reach Freeze square number " + super.getNumber() + ", you are skiped one turn";
	}

}
