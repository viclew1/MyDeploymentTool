package admin;

import java.io.IOException;
import java.net.Socket;

import common.Protocol;


public class MessagesSession extends Thread {

	private Socket connection;
	private String name;
	private NetworkListener listener;
	
	private MessagesWriter writer;
	private MessagesReader reader;

	public MessagesSession (String name, NetworkListener listener) {
		this.name = name;
		if (listener == null) throw new RuntimeException("listener cannot be null");
		this.listener = listener;
	}


	public boolean close () {
		this.interrupt();
		try {
			if (connection != null) connection.close();
			connection = null;
		} catch (IOException e) {
		}
		return true;
	}

	public boolean open () {
		this.close();
		try {
			connection = new Socket(Protocol.IPSERV, Protocol.MESSAGE_PORT);
			start ();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean operate() {
		boolean ok = false;
		try {
			reader.receive ();
			switch (reader.getType()) 
			{
			case Protocol.RP_INFO:
				listener.updateInfo(reader.getInfo());
				break;
			case Protocol.RP_CONTROL:
				listener.updateControl(reader.getClientAddr(),reader.getImg());
			default:
				break;
			}
			ok=true;
		}
		catch (IOException e) {
		}
		return ok;
	}

	public void run() {
		try {
			writer = new MessagesWriter (connection.getOutputStream());
			reader = new MessagesReader (connection.getInputStream());
			writer.connect(name);
			writer.send();
		}
		catch (IOException e) {
		}
		while (true) {
			if (! operate())
				break;
		}
		try {
			if (connection != null) connection.close();
		} catch (IOException e) {
		}
	}

}
