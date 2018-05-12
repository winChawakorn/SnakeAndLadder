package multiplayer;

import java.awt.Color;
import java.awt.Dimension;
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

import game.BackwardSquare;
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
			playerIndex = -1;
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
			int numPlayer = Integer.parseInt(multiplayerStatus.getText().charAt(0) + "");
			if (numPlayer > 4)
				numPlayer = 4;
			game = new Game(numPlayer);
			try {
				client.sendToServer(game);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		start.setBackground(Color.YELLOW);
		start.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
		start.setPreferredSize(new Dimension(150, 70));
		multiplayerMenu.add(multiplayerStatus, new GridBagConstraints());
		multiplayerMenu.add(start, new GridBagConstraints());
		add(multiplayerMenu);
		super.init();
		roll.setVisible(false);
	}

	// @Override
	// protected void addReplayListener() {
	// super.addReplayListener();
	// try {
	// client.closeConnection();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	@Override
	protected void addRollListener() {
		roll.addActionListener((e) -> {
			roll.setEnabled(false);
			// fromNumber = game.currentPlayerPosition();

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
		if (game.currentPlayerIndex() == playerIndex) {
			roll.setEnabled(true);
			// roll.doClick();
		}
	}

	@Override
	protected void switchPlayer() {
		game.switchPlayer();
		freeze(); // check this player is on freeze square or not
		if (game.currentPlayerIndex() == playerIndex) {
			roll.setEnabled(true);
		}
		turn.setForeground(game.currentPlayer().getColor());
		turn.setText(game.currentPlayerName() + "'s turn");

		if (game.isReplay()) {
			synchronized (game.getThread()) {
				game.getThread().notify();
			}
		}
	}

	@Override
	protected void end() {
		super.end();
		controller.remove(playAgain);
		controller.remove(replay);
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
				}
				num = (int) o;
				if (playerIndex == -1)
					playerIndex = num - 1;
				multiplayerStatus.setText(num + " / 4 players");
				if (num == 1)
					start.setVisible(false);
				else
					start.setVisible(true);
				if (game.currentPlayerIndex() == playerIndex) {
					roll.setEnabled(true);
				}
				repaint();
				revalidate();
			}
			if (o instanceof Game) {
				remove(multiplayerMenu);
				roll.setVisible(true);
				repaint();
				revalidate();
				game = (Game) o;
				System.out.println("++++++++++++++++++++++++++++++++++ " + !game.isEnd());
				System.out.println(((Game) o).getNumPlayer());
				if (game.getNumPlayer() == -1) {
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
				face = game.getHistory().get(game.getHistory().size() - 1).getFace();
				dice.setText(Math.abs(face) + "");
				currentStatus.setForeground(game.currentPlayer().getColor());
				if (game.currentPlayerPosition() != game.previousPlayerPosition())
					currentStatus.setText(game.currentPlayerName() + " go from number " + game.previousPlayerPosition()
							+ " to number " + game.currentPlayerPosition());
				// System.out.println(game.currentPlayerName() + " go from number " +
				// game.previousPlayerPosition()
				// + " to number " + game.currentPlayerPosition());
				if (game.currentPlayerPosition() == 0)
					return;
				move(game.currentPlayerIndex(), game.previousPlayerPosition() + face, game.previousPlayerPosition());
				if (game.currentPlayerWins()) {
					playerIndex = 0;
					game.end();
					roll.setVisible(false);
				}
			}
		}
	}

}
