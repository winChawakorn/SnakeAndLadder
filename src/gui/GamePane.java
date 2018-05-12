package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

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
import game.Command;
import game.FreezeSquare;
import game.Game;
import game.SpecialSquare;
import game.Square;

public class GamePane extends JPanel implements Observer {
	protected Game game;
	protected JTextArea currentStatus;
	protected Map<Integer, JLabel> players;
	protected JButton roll;
	protected JLabel turn;
	protected JTextField dice;
	protected JLabel controller;
	protected JButton playAgain;
	protected JButton mainMenu;
	private JButton replay;
	protected int face;
	protected int fromNumber;
	protected JLabel spectator;

	public GamePane(Game game) {
		super();
		this.game = game;
		players = new HashMap<>();
		init();
		game.addObserver(this);
		// roll.doClick();
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
		JLabel purple = new JLabel(new ImageIcon(this.getClass().getResource("/img/purple.png")));
		purple.setBounds(200, 645, 47, 61);
		players.put(3, purple);
		add(purple);
		JLabel board = new JLabel(new ImageIcon(this.getClass().getResource("/img/board.png")));
		board.setBounds(300, 0, 720, 720);
		add(board);

		controller = new JLabel(new ImageIcon(this.getClass().getResource("/img/forest.png")));
		controller.setLayout(new FlowLayout());
		controller.setBounds(0, 0, 300, 720);
		controller.setBackground(Color.PINK);
		controller.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
		// JLabel label = new JLabel("Snake and Ladder");
		JLabel label = new JLabel(new ImageIcon(this.getClass().getResource("/img/text_small.png")));
		// label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));

		spectator = new JLabel("You're spectator");
		spectator.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		spectator.setVisible(false);
		spectator.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

		Font font = new Font("Comic Sans MS", Font.PLAIN, 30);
		turn = new JLabel(game.currentPlayerName() + "'s turn", SwingConstants.CENTER);
		turn.setForeground(game.currentPlayer().getColor());
		turn.setFont(font);
		turn.setPreferredSize(new Dimension(300, 150));

		font = new Font("Comic Sans MS", Font.PLAIN, 20);
		currentStatus = new JTextArea("Click roll button to play");
		currentStatus.setPreferredSize(new Dimension(230, 170));
		currentStatus.setForeground(game.currentPlayer().getColor());
		currentStatus.setFont(font);
		currentStatus.setBackground(new Color(0, 0, 0, 0));
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
			if (!game.isReplay()) {
				red.setBounds(50, 645, 47, 61);
				blue.setBounds(100, 645, 47, 61);
				green.setBounds(150, 645, 47, 61);
				purple.setBounds(200, 645, 47, 61);
			}
			game.turnOnReplayMode();
			replay.setVisible(false);
		});

		mainMenu = new JButton("Main menu");
		mainMenu.setFont(font);
		mainMenu.setBackground(Color.WHITE);
		mainMenu.addActionListener((e) -> {
			GameUI.setPanel(new MenuPane());
		});

		JPanel rollPane = new JPanel();
		rollPane.setPreferredSize(new Dimension(300, 80));
		rollPane.setBackground(new Color(0, 0, 0, 0));
		roll = new JButton(new ImageIcon(this.getClass().getResource("/img/roll.png")));
		roll.setBackground(new Color(0, 0, 0, 0));
		roll.setPreferredSize(new Dimension(130, 60));
		addRollListener();

		controller.add(label);
		controller.add(spectator);
		controller.add(mainMenu);
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
			dice.setText(face + "");
			// if find backward square
			if (game.currentPlayerSquare() instanceof BackwardSquare)
				face = (-1) * face;
			game.updateHistory(face);
			// game move piece to final square
			if (fromNumber + face <= 100)
				game.currentPlayeMovePiece(face);
			else
				game.currentPlayeMovePiece((100 - (fromNumber + face) % 100) - fromNumber);

			currentStatus.setText(game.currentPlayerName() + " go from number " + fromNumber + " to number "
					+ game.currentPlayerPosition());
			move(game.currentPlayerIndex(), fromNumber + face, fromNumber);
			repaint();
			revalidate();
		});

	}

	protected void end() {
		roll.setEnabled(false);
		controller.remove(roll);
		controller.add(playAgain);
		controller.add(replay);
		controller.add(mainMenu);
		replay.setVisible(true);
		turn.setText(game.currentPlayerName() + " WINS!");
		game.end();
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
		if (toNumber > game.getBoardGoalNumber() && fromNumber == game.getBoardGoalNumber()) {
			move(playerIndex, 100 - toNumber % 100, -1);
			return;
		}
		if (toNumber < fromNumber) {
			move(playerIndex, toNumber, -1);
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
					Thread.sleep(200); // 10 millisec per 5 pixels
				} catch (InterruptedException e1) {
				}
				timer.stop();
				if (next != toNumber) // don't reach destination yet
					move(playerIndex, toNumber, next);
				else { // reach destination
					System.out.println(game.currentPlayerName() + "|" + game.currentPlayerPosition());
					Square cs = game.currentPlayerSquare();
					if (cs instanceof SpecialSquare) {
						SpecialSquare ss = (SpecialSquare) cs;
						move(playerIndex, ss.getDestination(), -1);
						currentStatus.setText(cs.toString());
						game.currentPlayeMovePiece(ss.getDestination() - ss.getNumber());
					} else if (cs instanceof BackwardSquare) {
						backward();
					} else {
						if (cs instanceof FreezeSquare)
							currentStatus.setText(cs.toString());
						if (!game.currentPlayerWins())
							switchPlayer();
						else
							end();
					}
				}
			}
			repaint();
			revalidate();

		});
		timer.start();
	}

	protected void backward() {
		BackwardSquare bs = (BackwardSquare) game.currentPlayerSquare();
		currentStatus.setText(bs.toString());
		roll.setEnabled(true);
//
//		if (!game.isReplay())
//			roll.doClick();

		if (game.isReplay()) {
			synchronized (game.getThread()) {
				game.getThread().notify();
			}
		}
		// wait for roll again
	}

	/**
	 * switch player when a current player's piece reach a destination
	 */
	protected void switchPlayer() {
		game.switchPlayer();
		freeze(); // check this player is on freeze square or not
		roll.setEnabled(true);
//		if (!game.isReplay())
//			roll.doClick();
		turn.setForeground(game.currentPlayer().getColor());
		turn.setText(game.currentPlayerName() + "'s turn");

		if (game.isReplay()) {
			synchronized (game.getThread()) {
				game.getThread().notify();
			}
		}
	}

	/**
	 * checking next players (more than one player because there is a case that
	 * more than one player are concurrent frozen) are frozen or not. If player
	 * is frozen skipped his turn.
	 */
	protected void freeze() {
		String whoskip = "";
		while (game.currentPlayerSquare() instanceof FreezeSquare) {
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
			currentStatus.setText(whoskip + " is/are skipped. This is " + game.currentPlayerName() + "'s turn.");
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Command) {
			Command c = (Command) arg;
			dice.setText((int) Math.abs(c.getFace()) + "");
			int fromNumber = c.getPosBeforeMove();
			currentStatus.setForeground(c.getPlayer().getColor());
			currentStatus.setText(c.getPlayer().getName() + " go from number " + fromNumber + " to number "
					+ game.currentPlayerPosition());
			move(c.getPlayerIndex(), fromNumber + c.getFace(), fromNumber);
		}
	}
}
