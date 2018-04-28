package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Game;

public class GamePane extends JPanel {
	private Game game;

	public GamePane(Game game) {
		super();
		this.game = game;
		init();
	}

	private void init() {
		setLayout(null);
		ImageIcon boardImage = new ImageIcon(this.getClass().getResource("/img/board.png"));
		JLabel board = new JLabel(boardImage);
		board.setBounds(300, 0, 720, 720);
		add(board);

		JPanel controller = new JPanel();
		controller.setBounds(0, 0, 300, 720);
		controller.setBackground(Color.PINK);
		controller.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		JLabel label = new JLabel("Snake and Ladder");
		label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));

		JLabel turn = new JLabel("Player1 turn");
		turn.setForeground(Color.BLUE);
		turn.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		controller.add(label);
		controller.add(turn);
		add(controller);
	}
}
