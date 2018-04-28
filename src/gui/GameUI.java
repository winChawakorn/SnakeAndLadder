package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameUI extends JFrame {

	public GameUI() {
		super("Snake and Ladder");
		init();
	}

	private void init() {
		setResizable(false);
		setSize(1000, 800);
		changePanel(new MenuPane());
	}

	private void changePanel(JPanel pane) {
		getContentPane().removeAll();
		repaint();
		getContentPane().add(pane);
		pane.setVisible(true);
		pane.setFocusable(true);
		pane.requestFocusInWindow();
	}

	private void run() {
		setVisible(true);
	}

	public static void main(String[] args) {
		new GameUI().run();
	}
}
