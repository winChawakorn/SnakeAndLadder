package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Game;
import multiplayer.ClientGameUI;

/**
 * This menu pane provide users to choose number of player *
 */
public class MenuPane extends JPanel {

	private JPanel alert;
	private JLabel alertMsg;
	private JButton two;
	private JButton three;
	private JButton four;
	private JButton online;

	public MenuPane() {
		super();
		init();
	}

	private void init() {
		JLabel bg = new JLabel(new ImageIcon(this.getClass().getResource("/img/jungle.jpg")));
		bg.setLayout(new BorderLayout());
		bg.setBackground(new Color(60, 179, 113));
		bg.setLayout(new BorderLayout());
		JLabel label = new JLabel(new ImageIcon(this.getClass().getResource("/img/text.png")));
		label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		label.setForeground(Color.WHITE);

		JPanel center = new JPanel();
		center.setLayout(null);
		center.setBackground(new Color(0, 0, 0, 0));
		Font btnFont = new Font("Comic Sans MS", Font.PLAIN, 25);
		two = new JButton("2 players");
		two.setBackground(Color.DARK_GRAY);
		two.setForeground(Color.ORANGE);
		two.setFont(btnFont);
		two.setBounds(1150, 50, 200, 100);
		two.addActionListener((e) -> {
			GameUI.setPanel(new GamePane(new Game(2)));
		});
		three = new JButton("3 players");
		three.setBackground(Color.DARK_GRAY);
		three.setForeground(Color.ORANGE);
		three.setFont(btnFont);
		three.setBounds(1150, 175, 200, 100);
		three.addActionListener((e) -> {
			GameUI.setPanel(new GamePane(new Game(3)));
		});
		four = new JButton("4 players");
		four.setBackground(Color.DARK_GRAY);
		four.setForeground(Color.ORANGE);
		four.setFont(btnFont);
		four.setBounds(1150, 300, 200, 100);
		four.addActionListener((e) -> {
			GameUI.setPanel(new GamePane(new Game(4)));
		});
		online = new JButton("Online");
		online.setBackground(Color.DARK_GRAY);
		online.setForeground(Color.ORANGE);
		online.setFont(btnFont);
		online.setBounds(1150, 425, 200, 100);
		online.addActionListener((e) -> {
			try {
				GameUI.setPanel(new ClientGameUI());
			} catch (IOException e1) {
				alert("Server is closed");
			}
		});

		alert = new JPanel();
		alert.setBounds(775, 200, 400, 200);
		alert.setBackground(Color.PINK);
		alertMsg = new JLabel("ALERT!", SwingConstants.CENTER);
		alertMsg.setFont(btnFont);
		alertMsg.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		alertMsg.setPreferredSize(new Dimension(400, 80));
		JButton ok = new JButton("OK");
		ok.setBackground(Color.YELLOW);
		ok.setPreferredSize(new Dimension(150, 50));
		ok.addActionListener((e) -> {
			alert("");
		});
		JLabel empty = new JLabel("");
		empty.setPreferredSize(new Dimension(400, 30));
		alert.add(alertMsg);
		alert.add(empty);
		alert.add(ok);
		alert.setVisible(false);

		JLabel snake = new JLabel(new ImageIcon(this.getClass().getResource("/img/snake.png")));
		snake.setBounds(250, 0, 1000, 600);

		add(bg);
		bg.add(label, BorderLayout.NORTH);
		bg.add(center, BorderLayout.CENTER);
		center.add(alert);
		center.add(two);
		center.add(three);
		center.add(four);
		center.add(online);
		center.add(snake);
	}

	public void alert(String msg) {
		alert.setVisible(!alert.isVisible());
		two.setEnabled(!two.isEnabled());
		three.setEnabled(!three.isEnabled());
		four.setEnabled(!four.isEnabled());
		online.setEnabled(!online.isEnabled());
		alertMsg.setText(msg);
		repaint();
		revalidate();
	}
}
