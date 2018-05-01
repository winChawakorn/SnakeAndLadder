package multiplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

import game.Game;

public class GameServer extends AbstractServer {

	List<ConnectionToClient> connections;
	boolean isStart = false;

	public GameServer(int port) {
		super(port);
		connections = new ArrayList<>();
	}

	public void start() {
		isStart = true;
		sendToAllClients(new Game(connections.size()));
	}

	public void end() {
		isStart = false;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if (msg instanceof Game) {
			sendToAllClients((Game) msg);
		}
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		super.clientConnected(client);
		System.out.println(client.getInetAddress() + " connected");
		connections.add(client);
		if (isStart) {
			try {
				client.sendToClient("The game is started");
			} catch (IOException e) {
			}
			clientDisconnected(client);
		}
	}

	@Override
	protected synchronized void clientDisconnected(ConnectionToClient client) {
		super.clientDisconnected(client);
		System.out.println(client.getInetAddress() + " disconnected");
		connections.remove(client);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String cmd = "";
		GameServer s = new GameServer(3006);
		try {
			s.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server started!");
		while (cmd != "e") {
			cmd = sc.nextLine();
			System.out.println(s.isListening());
			System.out.println(s.getNumberOfClients() + "players online");
		}
	}

}
