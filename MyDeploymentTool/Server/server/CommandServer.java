package server;

import java.net.ServerSocket;
import java.net.Socket;

import common.Protocol;

public class CommandServer extends Thread {

	private ServerSocket server = null;
	private NetworkListener listener = null;

	public CommandServer(NetworkListener listener) {
		super();
		this.listener = listener;
	}

	public void run () {
		try {
			server = new ServerSocket (Protocol.COMMAND_PORT);
			while (true) {
				Socket connection = server.accept();
				CommandSession session = new CommandSession (connection, listener);
				session.start ();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
