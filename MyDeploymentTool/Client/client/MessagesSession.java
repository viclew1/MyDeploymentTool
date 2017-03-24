package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import common.Protocol;


public class MessagesSession extends Thread {

	private Socket connection;
	private NetworkListener listener;

	public MessagesSession (NetworkListener listener) {
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
			connection = new Socket(InetAddress.getLocalHost().getHostAddress(), Protocol.MESSAGE_PORT);
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
			MessagesReader r = new MessagesReader (connection.getInputStream());
			MessagesWriter w = new MessagesWriter (connection.getOutputStream());
			r.receive ();
			switch (r.getType()) 
			{
			case Protocol.RP_FILE:
				if (r.getDone())
					w.ok();
				else w.ko();
				break;
			case Protocol.RP_DIR:
				if (r.getDone())
					w.ok();
				else w.ko();
				break;
			case Protocol.RQ_CONTROL:
				if (!listener.isControlled())
				{
					w.ok();
					listener.processControl(r.getAdmin());
				}
				else w.ko();
				break;
			case Protocol.RQ_STOP_CONTROL:
				listener.stopControl();
				break;
			case Protocol.RQ_PHOTO:
				w.photo(listener.takePicture());
				break;
			default:
				break;
			}
			w.send();
			ok=true;
		}
		catch (IOException e) {
		}
		return ok;
	}

	public void run() {
		try {
			MessagesWriter w = new MessagesWriter (connection.getOutputStream());
			w.connect();
			w.send();
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
