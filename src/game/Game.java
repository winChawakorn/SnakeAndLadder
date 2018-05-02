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
	private List<Color> colorlist;
	private int numPlayer;
	private boolean notOver;
	private boolean replayMode;
	private Thread thread;



	public Game(int numPlayer) {
		this.numPlayer = numPlayer;
		players = new Player[numPlayer];
		board = new Board();
		die = new Die();
		ended = false;
		colorlist = new ArrayList<Color>();
		colorlist.add(Color.RED);
		colorlist.add(Color.BLUE);
		colorlist.add(Color.GREEN);
		colorlist.add(Color.YELLOW);

		for (int i = 0; i < numPlayer; i++) {
			players[i] = new Player("P" + (i + 1), colorlist.get(i));
			board.addPiece(players[i].getPiece(), 0);
		}
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
		return currentPlayer().roll(die);
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
	
//	public void start() {
//		currentPlayer().movePiece(board, (-1)*currentPlayerPosition());
//		notOver = true;
//		thread = new Thread() {
//			@Override
//			public void run() {
//				while (notOver) {
//					if(replayMode)
//						injectHistoryCommand();
//					currentPlayer().move();
//					checkCollisions();
//					setChanged();
//					notifyObservers();
//					waitFor(500);
//				}
//			}
//		};
//		thread.start();
//	}

//	private void injectHistoryCommand() {
//		for (Command history : histories) {
//			if (history.getTick() == tick) {
//				history.execute();
//			}
//		}
//	}
//	
//	private void checkCollisions() {
//		for (Enemy e : enemies) {
//			if (e.hit(player)) {
//				notOver = false;
//			}
//		}
//	}
//	
//	private void waitFor(long delayed) {
//		try {
//			Thread.sleep(delayed);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	
}
