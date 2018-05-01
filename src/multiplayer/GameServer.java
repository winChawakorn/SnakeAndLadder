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
		System.out.println("ocsf server started!");
		while (cmd != "e") {
			cmd = sc.nextLine();
			System.out.println(s.isListening());
		}
	}

}
