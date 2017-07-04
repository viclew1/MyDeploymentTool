package admin;

import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import admin.admindatas.App;
import admin.admindatas.Client;
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
			connection = new Socket(Protocol.IPSERV, Protocol.COMMAND_PORT);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean doDisconnect(String name) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			w.disconnect(name);
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
	
	public List<Client> doGetUsers(String name) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			w.getUsers(name);
			w.send();
			CommandReader r = new CommandReader(connection.getInputStream());
			r.receive();
			return r.getUsers();
		} catch (Exception e) {
			return null;
		}
	}

	public List<App> doGetApps(String name, String os) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			w.getApps(name,os);
			w.send();
			CommandReader r = new CommandReader(connection.getInputStream());
			r.receive();
			return r.getApps();
		} catch (Exception e) {
			return null;
		}
	}

	public int[] install(String name, String os, List<App> apps, List<Client> clients) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			w.install(name,os,apps,clients);
			w.send();
			CommandReader r = new CommandReader(connection.getInputStream());
			r.receive();
			return new int[]{r.getNbFiles(),r.getNbEchecs()};
		} catch (Exception e) {
			return null;
		}
	}

	public boolean doControl(String name, String address) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			w.control(name,address);
			w.send();
			CommandReader r = new CommandReader(connection.getInputStream());
			r.receive();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void stopControl(String name, String address) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			w.stopControl(name,address);
			w.send();
		} catch (Exception e) {
		}
	}

}
