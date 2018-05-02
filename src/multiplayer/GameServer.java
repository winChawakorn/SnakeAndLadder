package multiplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

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
			if (game.getNumPlayer() < 2 || game.getNumPlayer() > 4)
				return;
			if (!isStart)
				game = new Game(game.getNumPlayer());
			start();
			if (game.currentPlayerWins() || game.isEnd())
				end();
			sendToAllClients(game);
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
		if (players.contains(client)) {
			players.remove(client);
			end();
			game.end();
			sendToAllClients(game);
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