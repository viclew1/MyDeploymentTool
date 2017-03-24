package client;

import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.Socket;
import common.Protocol;


public class CommandSession {

	private Socket connection;
	
	public CommandSession () {
	}

	public boolean close () {
		try {
			if (connection != null) connection.close();
			connection = null;
		} catch (Exception e) {
		}
		return true;
	}
	
	public boolean open () {
		this.close();
		try {
			connection = new Socket(InetAddress.getLocalHost().getHostAddress(), Protocol.COMMAND_PORT);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean doDisconnect() {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			w.disconnect();
			w.send();
			CommandReader r = new CommandReader(connection.getInputStream());
			r.receive();
			return r.getDone();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean doConnect (String name) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			w.connect(name);
			w.send();
			CommandReader r = new CommandReader(connection.getInputStream());
			r.receive();
			return r.getDone();
		} catch (Exception e) {
			return false;
		}
	}

	public void sendImage(String admin, BufferedImage img) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			w.control(admin,img);
			w.send();
			open();
		} catch (Exception e) {
		}
	}


}
