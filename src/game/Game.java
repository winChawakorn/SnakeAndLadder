package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.io.Serializable;

public class Game extends Observable implements Serializable {

	private Player[] players;
	private Die die;
	private Board board;
	private int currentPlayerIndex;
	private int previousPlayerPosition;
	private boolean ended;
	private int numPlayer;
	private List<Command> faceHistorys;
	private Thread thread;
	private boolean replayMode;

	public Game(int numPlayer) {
		this.numPlayer = numPlayer;
		faceHistorys = new ArrayList<>();
		players = new Player[numPlayer];
		board = new Board();
		die = new Die();
		ended = false;
		replayMode = false;
		Color[] colorlist = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW };
		String[] nameList = { "RED", "BLUE", "GREEN", "YELLOW" };
		for (int i = 0; i < numPlayer; i++) {
			players[i] = new Player(nameList[i], colorlist[i]);
			board.addPiece(players[i].getPiece(), 0);
		}
	}

	public void resetGame() {
		ended = false;
		for (int i = 0; i < numPlayer; i++) {
			board.movePiece(players[i].getPiece(), (-1) * board.getPiecePosition(players[i].getPiece()));
		}
	}
	
	public Thread getThread() {
		return thread;
	}
	
	public void turnOnReplayMode() {
		resetGame();
		thread = new Thread() {
			@Override
			public void run() {
				for (int i = 0; i< faceHistorys.size() ; i++) {
					Command command = faceHistorys.get(i);
					command.execute(board);
					System.out.println((i+1) + ": " + command.getFace() + "-" + command.getPlayer().getName() + "-"
							+ command.getPosBeforeMove());
					setChanged();
					notifyObservers(command);
//					waitFor(410 * (int)Math.abs(command.getFace()) + 700);
//					waitFor(20000);
					try {
						threadCondWait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					if(i == faceHistorys.size()-1)
						replayMode = false;
				}
			}
			
			public synchronized void threadCondWait() throws InterruptedException{
		           wait();//Comminucate with notify()
		        
		     }
		};
		if (!replayMode) {
			replayMode = true;
			thread.start();
		}
	}
	
	public boolean isReplay(){
		return replayMode;
	}

	public int getNumPlayer() {
		return numPlayer;
	}

	public void setNumPlayer(int numPlayer) {
		this.numPlayer = numPlayer;
	}

	public boolean isEnd() {
		return ended;
	}

	public void end() {
		ended = true;
	}

	public Player currentPlayer() {
		return players[currentPlayerIndex];
	}

	public int previousPlayerPosition() {
		return previousPlayerPosition;
	}

	public void switchPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
	}

	public int currentPlayerIndex() {
		return currentPlayerIndex;
	}

	public String currentPlayerName() {
		return currentPlayer().getName();
	}

	public int currentPlayerPosition() {
		return board.getPiecePosition(currentPlayer().getPiece());
	}

	public int currentPlayerRollDice() {
		int face = currentPlayer().roll(die);
		return face;
	}

	public void updateHistory(int face) {
		Command c = new CollectFaceCommand(face, currentPlayerIndex, currentPlayerPosition(), currentPlayer());
		faceHistorys.add(c);
	}

	public void currentPlayeMovePiece(int steps) {
		previousPlayerPosition = currentPlayerPosition();
		currentPlayer().movePiece(board, steps);
	}

	public Square currentPlayerSquare() {
		return board.getSquare(currentPlayer().getPiece());
	}

	public boolean currentPlayerWins() {
		return board.pieceIsAtGoal(currentPlayer().getPiece());
	}

	public boolean currentPlayerFreeze() {
		return currentPlayer().isFreeze();
	}

	public int getBoardGoalNumber() {
		return board.getGoalNumber();
	}

	private void waitFor(long delayed) {
		try {
			Thread.sleep(delayed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
