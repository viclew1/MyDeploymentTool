package client;

import java.io.IOException;
import java.net.Socket;

import common.Protocol;


public class MessagesSession extends Thread {

	private Socket connection;
	private NetworkListener listener;
	
	private MessagesWriter writer;
	private MessagesReader reader;

	public MessagesSession (NetworkListener listener) {
		if (listener == null) throw new RuntimeException("listener cannot be null");
		this.listener = listener;
	}


	public boolean close () {
		this.interrupt();
		try {
			if (connection != null) connection.close();
			if (reader!= null) reader.closeAll();
			connection = null;
		} catch (IOException e) {
		}
		return true;
	}

	public boolean open () {
		this.close();
		try {
			connection = new Socket(Protocol.IPSERV, Protocol.MESSAGE_PORT);
			try {
				reader = new MessagesReader (connection.getInputStream(),listener);
				writer = new MessagesWriter (connection.getOutputStream());
				writer.connect();
				writer.send();
				reader.receive();
			}
			catch (IOException e) {
			}
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
			case Protocol.RP_FILE:
				if (reader.getDone())
					writer.ok();
				else writer.ko();
				break;
			case Protocol.RP_DIR:
				if (reader.getDone())
					writer.ok();
				else writer.ko();
				break;
			case Protocol.RQ_CONTROL:
				if (!listener.isControlled())
				{
					if (listener.processControl(reader.getAdmin())) 
						writer.ok();
					else 
						writer.ko();
				}
				else writer.ko();
				break;
			case Protocol.RQ_STOP_CONTROL:
				listener.stopControl();
				break;
			default:
				break;
			}
			writer.send();
			ok=true;
		}
		catch (IOException e) {
		}
		return ok;
	}

	public void run() {
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
