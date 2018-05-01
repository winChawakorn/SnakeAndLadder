package multiplayer;

import java.io.IOException;

import com.lloseng.ocsf.client.AbstractClient;

import game.Game;
import gui.GamePane;

public class ClientGameUI extends GamePane {

	Client client;

	public ClientGameUI() throws IOException {
		super(new Game(2));
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

	public class Client extends AbstractClient {

		public Client(String host, int port) {
			super(host, port);
		}

		@Override
		protected void handleMessageFromServer(Object msg) {
			if (msg instanceof Game) {
				roll.setEnabled(false);
				setGame((Game) msg);
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
