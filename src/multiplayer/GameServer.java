package multiplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

import game.BackwardSquare;
import game.Game;

public class GameServer extends AbstractServer {

	private List<ConnectionToClient> connections;
	private List<ConnectionToClient> players;
	private Game game;
	private boolean isStart = false;

	public GameServer(int port) {
		super(port);
		connections = new ArrayList<>();
		players = new ArrayList<>();
	}

	public void start() {
		isStart = true;
	}

	public void end() {
		isStart = false;
		game = new Game(2);
		connections.clear();
		players.clear();
	}

	@Override
	protected void handleMessageFromClient(Object o, ConnectionToClient client) {
		if (o instanceof Game) {
			game = (Game) o;
			System.out.println("Recieve game " + game.currentPlayerName() + " at " + game.currentPlayerPosition()
					+ " from " + game.previousPlayerPosition());
			if (game.getNumPlayer() < 2 || game.getNumPlayer() > 4) {
				end();
				return;
			}
			if (!isStart) {
				game = new Game(game.getNumPlayer());
				start();
			} else if (game.currentPlayerWins() || game.isEnd())
				end();
			else {
				int face = game.currentPlayerRollDice();
				System.out.println("face = " + face);
				game.updateHistory(face);
				// if find backward square
				if (game.currentPlayerSquare() instanceof BackwardSquare)
					face = (-1) * face;
				// game move piece to final square
				if (game.currentPlayerPosition() + face <= 100)
					game.currentPlayeMovePiece(face);
				else
					game.currentPlayeMovePiece(
							(100 - (game.currentPlayerPosition() + face) % 100) - game.currentPlayerPosition());
				if (game.currentPlayerWins()) {
					System.out.println("EEEEENNNNNDDDDD");
					game.end();
					// end();
				}
			}
			System.out.println(game.isEnd() + " 3545341as53d15as31das65d4");
			sendToAllClients(game);
			if (game.isEnd())
				end();
		}
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		if (connections.contains(client))
			return;
		super.clientConnected(client);
		System.out.println(client.getInetAddress() + " connected");
		connections.add(client);
		try {
			if (isStart) {
				System.out.println("started");
				client.sendToClient(game);
			} else {
				sendToAllClients(connections.size());
				players.add(client);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected synchronized void clientDisconnected(ConnectionToClient client) {
		super.clientDisconnected(client);
		System.out.println("Someone disconnected");
		connections.remove(client);
		if (!isStart) {
			sendToAllClients(connections.size());
			if (players.contains(client))
				players.remove(client);
			return;
		}
		// if (game.isEnd()) {
		//
		// }
		System.out.println(!game.isEnd() + " asdasdasdsd");
		if (players.contains(client) && !game.isEnd()) {
			players.remove(client);
			// game.end();
			System.out.println(game.isEnd());
			game.setNumPlayer(-1);
			System.out.println(game.getNumPlayer());
			sendToAllClients(game);
			end();
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String cmd = "";
		GameServer s = new GameServer(3006);
		try {
			s.listen();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		System.out.println("Server started!");
		while (cmd != "e") {
			System.out.println("Press enter to see the server data");
			cmd = sc.nextLine();
			System.out.println(s.isListening() ? "Server is online" : "Server is offline");
			System.out.println(s.getNumberOfClients() + " players online");
			System.out.println("------------------------------------");
		}
	}

}