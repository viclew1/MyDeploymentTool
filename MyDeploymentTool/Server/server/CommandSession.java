package server;

import java.io.IOException;
import java.net.Socket;

import common.Protocol;

public class CommandSession extends Thread{

	private Socket connection;
	private NetworkListener listener;
	private CommandWriter writer;
	private CommandReader reader;

	public CommandSession(Socket connection, NetworkListener listener) {
		this.connection = connection;
		this.listener = listener;
		if( listener == null) throw new RuntimeException("listener cannot be null");
	}

	public boolean operate() {
		try {

			while (true)
			{
				reader.receive ();

				switch (reader.getType ()) {
				case 0 : return false; // socket closed
				case Protocol.RQ_CONNECT_ADMIN:
					if (listener.connectCommandAdmin(reader.getName(), this))
						writer.ok();
					else writer.ko();
					break;
				case Protocol.RQ_CONNECT:
					if (listener.connectCommandUser(reader.getName(),connection.getInetAddress().getHostAddress(), this))
						writer.ok();
					else writer.ko();
					break;
				case Protocol.RQ_DISCONNECT:
					if (listener.disconnectUser(connection.getInetAddress().getHostAddress()))
						writer.ok();
					else writer.ko();
					break;
				case Protocol.RQ_DISCONNECT_ADMIN:
					if (listener.disconnectAdmin(reader.getName()))
						writer.ok();
					else writer.ko();
					break;
				case Protocol.RQ_CLIENTS:
					writer.clients(listener.processUsers(reader.getName()));
					break;
				case Protocol.RQ_APPS:
					writer.apps(listener.processApps(reader.getName(),reader.getDir()));
					break;
				case Protocol.RQ_DIR_NAMES:
					writer.dirs(listener.processDirs(reader.getName()));
					break;
				case Protocol.RQ_INSTALL:
					listener.processInstall(reader.getName(), reader.getDir(), reader.getFiles(),reader.getDests());
					writer.ok();
					break;
				case Protocol.RQ_CONTROL:
					if (listener.takeControl(connection.getInetAddress().getHostAddress(), reader.getName(),reader.getDest()))
						writer.ok();
					else
						writer.ko();
					break;
				case Protocol.RQ_STOP_CONTROL:
					listener.stopControl(reader.getName(),reader.getDest());
					break;
				case -1 : break;
				default: return false;
				}
				writer.send ();
			}
		} catch (IOException e) {
			return false;
		}
	}

	public void run() {
		try {
			writer = new CommandWriter (connection.getOutputStream());
			reader = new CommandReader (connection.getInputStream());
			while (true) {
				if (! operate())
					break;
			}
			if (connection != null) connection.close();
		} catch (IOException e) {
		}
	}

	public void close () {
		this.interrupt();
		try {
			if (connection != null)
				connection.close();
		} catch (IOException e) {
		}
		connection = null;
	}

	public boolean isSocketOk()
	{
		return !(connection==null || connection.isClosed());
	}

}
