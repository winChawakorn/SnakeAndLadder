package multiplayer;

import java.io.IOException;

import com.lloseng.ocsf.client.AbstractClient;

import game.Game;
import gui.GamePane;

public class ClientGameUI extends GamePane {

	MyClient client;

	public ClientGameUI() throws IOException {
		super(new Game(2));
		if (roll.getActionListeners().length > 0)
			roll.removeActionListener(roll.getActionListeners()[0]);
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
		client = new MyClient("localhost", 3006);
		client.openConnection();
	}

	public class MyClient extends AbstractClient {

		public MyClient(String host, int port) {
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
