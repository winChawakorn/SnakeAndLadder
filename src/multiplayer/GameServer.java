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

	public GameServer(int port) {
		super(port);
		connections = new ArrayList<>();
	}

	@Override
	protected void handleMessageFromClient(Object o, ConnectionToClient client) {
		if (o instanceof Game) {
			sendToAllClients((Game) o);
		}
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		super.clientConnected(client);
		System.out.println(client.getInetAddress() + " connected");
		connections.add(client);
		try {
			client.sendToClient(getNumberOfClients());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected synchronized void clientDisconnected(ConnectionToClient client) {
		super.clientDisconnected(client);
		System.out.println("Someone disconnected");
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
