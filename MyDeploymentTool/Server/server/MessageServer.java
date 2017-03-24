package server;

import java.net.ServerSocket;
import java.net.Socket;

import common.Protocol;

public class MessageServer extends Thread {

	private ServerSocket server = null;
	private NetworkListener listener = null;

	public MessageServer(NetworkListener listener) {
		super();
		this.listener = listener;
	}

	public void run () {
		try {
			server = new ServerSocket (Protocol.MESSAGE_PORT);
			while (true) {
				Socket connection = server.accept();
				MessageSession session = new MessageSession (connection, listener);
				session.processConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
