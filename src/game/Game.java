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
	private boolean replayMode;
	private Thread thread;
	private List<Command> faceHistorys;
	private int tick;

	public Game(int numPlayer) {
		this.numPlayer = numPlayer;
		faceHistorys = new ArrayList<>();
		players = new Player[numPlayer];
		board = new Board();
		die = new Die();
		ended = false;
		Color [] colorlist = {Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW};
		String[] nameList = {"RED","BLUE","GREEN","YELLOW"};
		for (int i = 0; i < numPlayer; i++) {
			players[i] = new Player(nameList[i], colorlist[i]);
			board.addPiece(players[i].getPiece(), 0);
		}
	}

	public void turnOnReplayMode() {
		replayMode = true;
		tick = 0;
		
		ended = false;
//		while (!ended) {
//			setChanged();
//			notifyObservers();
//		}
		
	}
	
	public boolean replayMode() {
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
		if (replayMode) {
			return faceHistorys.get(tick++).getFace();
		}
		int face = currentPlayer().roll(die);
		faceHistorys.add(new CollectFaceCommand(face, currentPlayer()));
		return face;
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
	
	
}
