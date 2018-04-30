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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import game.Game;
import game.Piece;
import game.Player;

public class GamePane extends JPanel {
	private Game game;
	private Map<Player, JLabel> players;
	private JButton roll;

	public GamePane(Game game) {
		super();
		this.game = game;
		players = new HashMap<>();
		init();
	}

	private void init() {
		setLayout(null);

		JLabel red = new JLabel(new ImageIcon(this.getClass().getResource("/img/red.png")));
		red.setBounds(50, 645, 47, 61);
		players.put(game.currentPlayer(), red);
		game.switchPlayer();
		add(red);
		JLabel blue = new JLabel(new ImageIcon(this.getClass().getResource("/img/blue.png")));
		blue.setBounds(100, 645, 47, 61);
		players.put(game.currentPlayer(), blue);
		game.switchPlayer();
		add(blue);
		if (game.getNumPlayer() >= 3) {
			JLabel green = new JLabel(new ImageIcon(this.getClass().getResource("/img/green.png")));
			green.setBounds(150, 645, 47, 61);
			players.put(game.currentPlayer(), green);
			game.switchPlayer();
			add(green);
		}
		if (game.getNumPlayer() == 4) {
			JLabel yellow = new JLabel(new ImageIcon(this.getClass().getResource("/img/yellow.png")));
			yellow.setBounds(200, 645, 47, 61);
			players.put(game.currentPlayer(), yellow);
			game.switchPlayer();
			add(yellow);
		}
		JLabel board = new JLabel(new ImageIcon(this.getClass().getResource("/img/board.png")));
		board.setBounds(300, 0, 700, 720);
		add(board);

		JPanel controller = new JPanel();
		controller.setBounds(0, 0, 300, 720);
		controller.setBackground(Color.PINK);
		controller.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
		JLabel label = new JLabel("Snake and Ladder");
		label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));

		Font font = new Font("Comic Sans MS", Font.PLAIN, 30);
		JLabel turn = new JLabel(game.currentPlayerName() + "'s turn", SwingConstants.CENTER);
		turn.setForeground(Color.BLUE);
		turn.setFont(font);
		turn.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));
		turn.setPreferredSize(new Dimension(300, 180));

		JPanel dicePane = new JPanel();
		dicePane.setPreferredSize(new Dimension(300, 120));
		dicePane.setBackground(new Color(0, 0, 0, 0));
		JTextField dice = new JTextField("0", SwingConstants.CENTER);
		dice.setEditable(false);
		dice.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		dicePane.add(dice);

		roll = new JButton("Roll");
		roll.setFont(font);
		roll.setBackground(Color.LIGHT_GRAY);
		roll.addActionListener((e) -> {
			roll.setEnabled(false);
			int face = game.currentPlayerRollDice();
			game.currentPlayeMovePiece(face);
			dice.setText(face + "");
			move(game.currentPlayer(), game.currentPlayerPosition());
			game.switchPlayer();
		});

		controller.add(label);
		controller.add(turn);
		controller.add(dicePane);
		controller.add(roll);
		add(controller);
	}

	/**
	 * move piece of input player
	 * @param player who his piece be moved
	 * @param number of squares to move
	 */
	private void move(Player player, int number) {
		Timer timer = new Timer(10, null);
		timer.addActionListener((e) -> {
			JLabel piece = players.get(player);
			int dx = 5;
			int numDestination = number % 10;
			if (number % 10 == 0)
				numDestination = 10;
			if (((number - 1) / 10) % 2 != 0)
				numDestination = 10 - ((number - 1) % 10);
			int destination = 240 + (numDestination * 70);
			boolean xIsDone = piece.getX() >= destination;
			if (piece.getX() > destination) {
				dx = -5;
				xIsDone = piece.getX() <= destination;
			}
			if (!xIsDone) {
				piece.setLocation(piece.getX() + dx, piece.getY());
			}
			int rowNum = 10 - ((number - 1) / 10);
			boolean yIsDone = piece.getY() <= (rowNum * 70) - 55;

			if (!yIsDone)
				piece.setLocation(piece.getX(), piece.getY() - 5);
			if (xIsDone && yIsDone) {
				timer.stop();
				roll.setEnabled(true);
			}
		});
		timer.start();
	}
}
