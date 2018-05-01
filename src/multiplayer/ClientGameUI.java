package multiplayer;

import java.io.IOException;

import com.lloseng.ocsf.client.AbstractClient;

import game.Game;
import gui.GamePane;

public class ClientGameUI extends GamePane {

	private Client client;
	private int playerIndex;

	public ClientGameUI() throws IOException {
		super(new Game(2));
		roll.setEnabled(false);
		client = new Client("localhost", 3006);
		client.openConnection();
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

	// @Override
	// protected void move(int playerIndex, int toNumber, int fromNumber) {
	// super.move(playerIndex, toNumber, fromNumber);

	@Override
	protected void switchPlayer() {
		getGame().switchPlayer();
		freeze();
		if (getGame().currentPlayerIndex() == playerIndex)
			roll.setEnabled(true);
		turn.setForeground(getGame().currentPlayer().getColor());
		turn.setText(getGame().currentPlayerName() + "'s turn");
	}

	public class Client extends AbstractClient {

		public Client(String host, int port) {
			super(host, port);
		}

		@Override
		protected void handleMessageFromServer(Object o) {
			if (o instanceof Integer) {
				playerIndex = (int) o - 1;
				if (getGame().currentPlayerIndex() == playerIndex)
					roll.setEnabled(true);
			}
			if (o instanceof Game) {
				roll.setEnabled(false);
				setGame((Game) o);
				Game game = getGame();
				dice.setText(game.currentPlayerPosition() - game.previousPlayerPosition() + "");
				move(game.currentPlayerIndex(), game.currentPlayerPosition(), game.previousPlayerPosition());
				if (game.currentPlayerWins()) {
					roll.setEnabled(false);
					controller.remove(roll);
					controller.add(playAgain);
					turn.setText(game.currentPlayerName() + " WINS!");
				}
			}
		}
	}

}
