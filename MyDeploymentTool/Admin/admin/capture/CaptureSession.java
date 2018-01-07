package admin.capture;

import java.io.IOException;
import java.net.Socket;

import common.Protocol;
import admin.NetworkListener;

public class CaptureSession extends Thread{

	private Socket connection;
	private NetworkListener listener;

	private CaptureReader reader;


	public CaptureSession(Socket connection, NetworkListener listener) {
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
				case Protocol.RP_CONTROL:
					listener.updateControl(connection.getInetAddress().getHostAddress(), reader.getImg());
					break;
				case -1 : break;
				default: return false;
				}
			}
		} catch (IOException e) {
			return false;
		}
	}

	public void run() {
		try {
			reader = new CaptureReader(connection.getInputStream());
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
