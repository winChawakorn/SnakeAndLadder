package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import game.BackwardSquare;
import game.Board;
import game.FreezeSquare;
import game.Game;
import game.SpecialSquare;
import game.Square;

public class GamePane extends JPanel {
	private Game game;
	private JTextArea currentStatus;
	protected Map<Integer, JLabel> players;
	protected JButton roll;
	protected JLabel turn;
	protected JTextField dice;
	protected JPanel controller;
	protected JButton playAgain;
	private JButton replay;
	protected int face;
	protected int fromNumber;

	public GamePane(Game game) {
		super();
		setGame(game);
		players = new HashMap<>();
		init();
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return this.game;
	}

	protected void init() {
		setLayout(null);

		JLabel red = new JLabel(new ImageIcon(this.getClass().getResource("/img/red.png")));
		red.setBounds(50, 645, 47, 61);
		players.put(0, red);
		add(red);
		JLabel blue = new JLabel(new ImageIcon(this.getClass().getResource("/img/blue.png")));
		blue.setBounds(100, 645, 47, 61);
		players.put(1, blue);
		add(blue);
		JLabel green = new JLabel(new ImageIcon(this.getClass().getResource("/img/green.png")));
		green.setBounds(150, 645, 47, 61);
		players.put(2, green);
		add(green);
		JLabel yellow = new JLabel(new ImageIcon(this.getClass().getResource("/img/yellow.png")));
		yellow.setBounds(200, 645, 47, 61);
		players.put(3, yellow);
		add(yellow);
		JLabel board = new JLabel(new ImageIcon(this.getClass().getResource("/img/board.png")));
		board.setBounds(300, 0, 720, 720);
		add(board);

		controller = new JPanel();
		controller.setBounds(0, 0, 300, 720);
		controller.setBackground(Color.PINK);
		controller.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
		JLabel label = new JLabel("Snake and Ladder");
		label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));

		Font font = new Font("Comic Sans MS", Font.PLAIN, 30);
		turn = new JLabel(game.currentPlayerName() + "'s turn", SwingConstants.CENTER);
		turn.setForeground(game.currentPlayer().getColor());
		turn.setFont(font);
		turn.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));
		turn.setPreferredSize(new Dimension(300, 180));

		font = new Font("Comic Sans MS", Font.PLAIN, 20);
		currentStatus = new JTextArea("Click roll button to play");
		currentStatus.setPreferredSize(new Dimension(230, 170));
		currentStatus.setForeground(game.currentPlayer().getColor());
		currentStatus.setFont(font);
		currentStatus.setBackground(Color.PINK);
		currentStatus.setLineWrap(true);
		currentStatus.setWrapStyleWord(true);
		currentStatus.setEditable(false);
		currentStatus.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

		JPanel dicePane = new JPanel();
		dicePane.setPreferredSize(new Dimension(300, 120));
		dicePane.setBackground(new Color(0, 0, 0, 0));
		dice = new JTextField("0", SwingConstants.CENTER);
		dice.setEditable(false);
		dice.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		dicePane.add(dice);

		playAgain = new JButton("Play again");
		playAgain.setFont(font);
		playAgain.setBackground(Color.WHITE);
		playAgain.addActionListener((e) -> {
			GameUI.setPanel(new GamePane(new Game(game.getNumPlayer())));
		});

		replay = new JButton("Watch replay");
		replay.setFont(font);
		replay.setBackground(Color.WHITE);
		replay.addActionListener((e) -> {
			// TODO
		});

		JPanel rollPane = new JPanel();
		rollPane.setPreferredSize(new Dimension(300, 80));
		rollPane.setBackground(new Color(0, 0, 0, 0));
		roll = new JButton("Roll");
		roll.setFont(font);
		roll.setBackground(Color.LIGHT_GRAY);
		addRollListener();

		controller.add(label);
		controller.add(turn);
		controller.add(dicePane);
		controller.add(roll);
		controller.add(currentStatus);
		add(controller);
	}

	protected void addRollListener() {
		roll.addActionListener((e) -> {
			currentStatus.setForeground(game.currentPlayer().getColor());
			roll.setEnabled(false);
			fromNumber = game.currentPlayerPosition();
			face = game.currentPlayerRollDice();
//			face = 102;
			dice.setText(face + "");
			if (game.currentPlayerSquare() instanceof BackwardSquare) {
				face = (-1) * face;
				move(game.currentPlayerIndex(), game.currentPlayerPosition() + face, -1);

			} else {
				move(game.currentPlayerIndex(), game.currentPlayerPosition() + face, game.currentPlayerPosition());

			}
			game.currentPlayeMovePiece(100-(game.currentPlayerPosition()+face)%100);
			currentStatus
			.setText("You go from number " + fromNumber + " to number " + game.currentPlayerPosition());
			// win or not
			if (game.currentPlayerWins()) {
				roll.setEnabled(false);
				controller.remove(roll);
				controller.add(playAgain);
				controller.add(replay);
				turn.setText(game.currentPlayerName() + " WINS!");
				game.end();
			}
		});
	}

	/**
	 * move piece of input player
	 * 
	 * @param playerIndex
	 *            who his piece be moved
	 * @param number
	 *            of squares to move
	 */
	protected void move(int playerIndex, int toNumber, int fromNumber) {
		// piece go back when go more than Board size
		if(toNumber > game.getBoardGoalNumber() && fromNumber == game.getBoardGoalNumber()){
			move(playerIndex, 100 - toNumber%100, -1);
			return;
		}
		Timer timer = new Timer(10, null);
		timer.addActionListener((e) -> {
			int next = fromNumber + 1;
			if (fromNumber == -1)
				next = toNumber;
			JLabel piece = players.get(playerIndex);
			int dx = 5;
			int numDestination = next % 10;
			if (next % 10 == 0)
				numDestination = 10;
			if (((next - 1) / 10) % 2 != 0)
				numDestination = 10 - ((next - 1) % 10);
			int destinationX = 250 + (numDestination * 70);
			boolean xIsDone = piece.getX() >= destinationX;
			if (piece.getX() > destinationX) {
				dx = -5;
				xIsDone = piece.getX() <= destinationX;
			}
			if (!xIsDone) {
				piece.setLocation(piece.getX() + dx, piece.getY());
			}
			int rowNum = 10 - ((next - 1) / 10);
			int destinationY = (rowNum * 70) - 55;
			boolean yIsDone = piece.getY() <= destinationY;
			int dy = -5;
			if (piece.getY() < destinationY) {
				yIsDone = piece.getY() >= destinationY;
				dy = 5;
			}
			if (!yIsDone)
				piece.setLocation(piece.getX(), piece.getY() + dy);
			// when piece already one square
			if (xIsDone && yIsDone) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
				}
				timer.stop();
				if (next != toNumber) // don't reach destination yet
					move(playerIndex, toNumber, next);
				else { // reach destination
					Square cs = game.currentPlayerSquare();
					if (cs instanceof SpecialSquare) {
						SpecialSquare ss = (SpecialSquare) cs;
						move(playerIndex, ss.getDestination(), -1);
						currentStatus.setText(cs.toString());
						game.currentPlayeMovePiece(ss.getDestination() - ss.getNumber());
					} else if (cs instanceof BackwardSquare) {
						BackwardSquare bs = (BackwardSquare) cs;
						currentStatus.setText(bs.toString());
						roll.setEnabled(true);
						// wait for roll again
					} else {
						if (cs instanceof FreezeSquare)
							currentStatus.setText(cs.toString());
						if(!game.isEnd())
							switchPlayer();
						
					}
				}
			}
		});
		timer.start();
	}

	/**
	 * switch player when a current player's piece reach a destination
	 */
	protected void switchPlayer() {
		game.switchPlayer();
		freeze(); // check this player is on freeze square or not
		roll.setEnabled(true);
		turn.setForeground(game.currentPlayer().getColor());
		turn.setText(game.currentPlayerName() + "'s turn");
	}

	/**
	 * checking next players (more than one player because there is a case that
	 * more than one player are concurrent frozen) are frozen or not. If player
	 * is frozen skipped his turn.
	 */
	protected void freeze() {
		String whoskip = "";
		while (game.currentPlayerSquare() instanceof FreezeSquare) {
			FreezeSquare fs = (FreezeSquare) game.currentPlayerSquare();
			if (game.currentPlayerFreeze()) {
				game.currentPlayer().setFreeze(false);
				break;
			} else {
				game.currentPlayer().setFreeze(true);
				whoskip += game.currentPlayerName() + ", ";
				game.switchPlayer();
			}
		}

		if (!whoskip.equals("")) {
			currentStatus.setForeground(game.currentPlayer().getColor());
			currentStatus.setText(whoskip + " is/are skipped. Now turn is yours.");
		}
	}
}
