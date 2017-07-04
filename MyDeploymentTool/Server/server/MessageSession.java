package server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import common.Protocol;

public class MessageSession {

	private Socket connection;
	private NetworkListener listener;

	public MessageSession(Socket connection, NetworkListener listener) {
		this.connection = connection;
		this.listener = listener;
		if( listener == null) throw new RuntimeException("listener cannot be null");
	}

	public void close () {
		try {
			if (connection != null)
				connection.close();
		} catch (IOException e) {
		}
		connection = null;
	}

	public boolean processConnection () {
		try {
			MessageReader reader = new MessageReader(connection.getInputStream());
			reader.receive();
			switch (reader.getType()) {
			case Protocol.RQ_CONNECT_ADMIN:
				listener.connectMessagesAdmin(reader.getUserName(), this);
				break;
			case Protocol.RQ_CONNECT:
				listener.connectMessagesUser(connection.getInetAddress().getHostName(), connection.getInetAddress().getHostAddress(), this);
				break;
			default:
				break;
			}
			return false;
		}
		catch (Exception e) {
		}
		return false;
	}

	public boolean dispatchFile (String path, String fileName) {
		try {
			MessageWriter w = new MessageWriter(connection.getOutputStream());
			MessageReader r = new MessageReader(connection.getInputStream());
			File file=new File(path+fileName);
			if (file.isFile())
				w.doDispatchFile(file, fileName);
			else w.doDispatchDir(file, fileName);
			w.send();
			r.receive();
			return r.getDone();
		}
		catch (Exception e) {
			return false;
		}
	}

	public void doDispatchInfos(String info) {
		try {
			MessageWriter w = new MessageWriter(connection.getOutputStream());
			w.doDispatchInfo(info);
			w.send();
		}
		catch (Exception e) {
			return;
		}
	}

	public boolean requestControl(String name) {
		try {
			MessageWriter w = new MessageWriter(connection.getOutputStream());
			MessageReader r = new MessageReader(connection.getInputStream());
			w.doRequestControl(name);
			w.send();
			r.receive();
			return r.getDone();
		}
		catch (Exception e) {
			return false;
		}
	}

	public void updateControll(String addr, BufferedImage img) {
		try {
			MessageWriter w = new MessageWriter(connection.getOutputStream());
			w.doUpdateControl(addr,img);
			w.send();
		}
		catch (Exception e) {
		}
	}

	public void stopControl() {
		try {
			MessageWriter w = new MessageWriter(connection.getOutputStream());
			w.doStopControl();
			w.send();
		}
		catch (Exception e) {
		}
	}
}
