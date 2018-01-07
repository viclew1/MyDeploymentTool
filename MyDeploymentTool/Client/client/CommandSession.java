package client;

import java.net.Socket;

import common.Protocol;


public class CommandSession 
{

	private Socket connection;
	
	private CommandWriter writer;
	private CommandReader reader;
	
	public CommandSession () {
	}

	public boolean close () {
		try {
			if (connection != null) connection.close();
			connection = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean open () {
		this.close();
		try {
			connection = new Socket(Protocol.IPSERV, Protocol.COMMAND_PORT);
			writer = new CommandWriter(connection.getOutputStream());
			reader = new CommandReader(connection.getInputStream());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean doDisconnect() {
		try {
			writer.disconnect();
			writer.send();
			reader.receive();
			return reader.getDone();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean doConnect (String name) {
		try {
			writer.connect(name);
			writer.send();
			reader.receive();
			return reader.getDone();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
