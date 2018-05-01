package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Game;
import multiplayer.ClientGameUI;
import multiplayer.GameClient;

/**
 * This menu pane provide users to choose number of player
 * 
 * @author vittunyutamaeprasart
 *
 */
public class MenuPane extends JPanel {

	public MenuPane() {
		super();
		init();
	}

	private void init() {
		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		setBackground(new Color(60, 179, 113));
		setLayout(new BorderLayout());
		JLabel label = new JLabel("Snake and Ladder", SwingConstants.CENTER);
		label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		label.setForeground(Color.WHITE);

		JPanel center = new JPanel();
		center.setLayout(null);
		center.setBackground(new Color(0, 0, 0, 0));
		Font btnFont = new Font("Comic Sans MS", Font.PLAIN, 25);
		JButton two = new JButton("2 players");
		two.setBackground(Color.DARK_GRAY);
		two.setForeground(Color.ORANGE);
		two.setFont(btnFont);
		two.setBounds(350, 50, 200, 100);
		two.addActionListener((e) -> {
			GameUI.setPanel(new GamePane(new Game(2)));
		});
		JButton three = new JButton("3 players");
		three.setBackground(Color.DARK_GRAY);
		three.setForeground(Color.ORANGE);
		three.setFont(btnFont);
		three.setBounds(350, 175, 200, 100);
		three.addActionListener((e) -> {
			GameUI.setPanel(new GamePane(new Game(3)));
		});
		JButton four = new JButton("4 players");
		four.setBackground(Color.DARK_GRAY);
		four.setForeground(Color.ORANGE);
		four.setFont(btnFont);
		four.setBounds(350, 300, 200, 100);
		four.addActionListener((e) -> {
			GameUI.setPanel(new GamePane(new Game(4)));
		});
		JButton online = new JButton("Online");
		online.setBackground(Color.DARK_GRAY);
		online.setForeground(Color.ORANGE);
		online.setFont(btnFont);
		online.setBounds(350, 425, 200, 100);
		online.addActionListener((e) -> {
			try {
				GameUI.setPanel(new ClientGameUI());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		add(label, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		center.add(two);
		center.add(three);
		center.add(four);
		center.add(online);
	}
}
