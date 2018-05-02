package multiplayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.lloseng.ocsf.client.AbstractClient;

import game.Game;
import gui.GamePane;
import gui.GameUI;
import gui.MenuPane;

public class ClientGameUI extends GamePane {

	private Client client;
	private int playerIndex = -1;
	private JLabel multiplayerStatus;
	private JPanel multiplayerMenu;
	private JButton start;

	public ClientGameUI() throws IOException {
		super(new Game(2));
		client = new Client("localhost", 3006);
		client.openConnection();
		if (mainMenu.getActionListeners().length > 0)
			mainMenu.removeActionListener(mainMenu.getActionListeners()[0]);
		mainMenu.addActionListener((e) -> {
			GameUI.setPanel(new MenuPane());
			try {
				client.closeConnection();
			} catch (IOException e1) {
			}
		});
	}

	@Override
	protected void init() {
		multiplayerMenu = new JPanel();
		multiplayerMenu.setBounds(0, 0, GameUI.getFrame().getWidth(), GameUI.getFrame().getHeight());
		multiplayerMenu.setBackground(new Color(255, 255, 255, 200));
		multiplayerMenu.setLayout(new GridBagLayout());
		multiplayerStatus = new JLabel("Welcome!", SwingConstants.CENTER);
		multiplayerStatus.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
		multiplayerStatus.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));
		start = new JButton("Start");
		start.addActionListener((e) -> {
			setGame(new Game(Integer.parseInt(multiplayerStatus.getText().charAt(0) + "")));
			try {
				client.sendToServer(getGame());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		start.setBackground(Color.LIGHT_GRAY);
		start.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		multiplayerMenu.add(multiplayerStatus, new GridBagConstraints());
		multiplayerMenu.add(start, new GridBagConstraints());
		add(multiplayerMenu);
		super.init();
		roll.setVisible(false);
	}

	@Override
	protected void addRollListener() {
		roll.addActionListener((e) -> {
			Game game = getGame();
			roll.setEnabled(false);
			fromNumber = game.currentPlayerPosition();
			face = game.currentPlayerRollDice();
			game.currentPlayeMovePiece(face);
			try {
				client.sendToServer(game);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}

	@Override
	protected void backward() {
		super.backward();
		roll.setEnabled(false);
		if (getGame().currentPlayerIndex() == playerIndex)
			roll.setEnabled(true);
	}

	@Override
	protected void switchPlayer() {
		getGame().switchPlayer();
		freeze();
		if (getGame().currentPlayerIndex() == playerIndex)
			roll.setEnabled(true);
		turn.setForeground(getGame().currentPlayer().getColor());
		turn.setText(getGame().currentPlayerName() + "'s turn");
	}

	@Override
	protected void end() {
		super.end();
		controller.remove(playAgain);
	}

	public class Client extends AbstractClient {

		public Client(String host, int port) {
			super(host, port);
		}

		private int num = 0;

		@Override
		protected void handleMessageFromServer(Object o) {
			if (o instanceof Integer) {
				if ((int) o < num && playerIndex > 0) {
					playerIndex--;
					return;
				}
				num = (int) o;
				if (playerIndex == -1)
					playerIndex = num - 1;
				multiplayerStatus.setText(num + " / 4 players");
				if (num == 1)
					start.setVisible(false);
				else
					start.setVisible(true);
				if (getGame().currentPlayerIndex() == playerIndex)
					roll.setEnabled(true);
				repaint();
				revalidate();
			}
			if (o instanceof Game) {
				remove(multiplayerMenu);
				roll.setVisible(true);
				repaint();
				revalidate();
				setGame((Game) o);
				Game game = getGame();
				if (game.isEnd()) {
					MenuPane menu = new MenuPane();
					GameUI.setPanel(menu);
					menu.alert("Another player leave the game");
					try {
						client.closeConnection();
					} catch (IOException e1) {
					}
					return;
				}
				if (playerIndex >= game.getNumPlayer() || playerIndex < 0) {
					spectator.setVisible(true);
				}
				if (game.currentPlayerIndex() != playerIndex)
					roll.setEnabled(false);
				dice.setText(game.currentPlayerPosition() - game.previousPlayerPosition() + "");
				if (game.currentPlayerPosition() == 0)
					return;
				move(game.currentPlayerIndex(), game.currentPlayerPosition(), game.previousPlayerPosition());
				if (game.currentPlayerWins()) {
					playerIndex = 0;
					end();
				}
			}
		}
	}

}
