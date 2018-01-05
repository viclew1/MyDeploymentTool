package admin;

import java.net.Socket;
import java.util.List;

import admin.admindatas.App;
import admin.admindatas.Client;
import common.Protocol;


public class CommandSession {

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
			return false;
		}
	}

	public boolean doDisconnect(String name) {
		try {
			writer.disconnect(name);
			writer.send();
			reader.receive();
			return reader.getDone();
		} catch (Exception e) {
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
			return false;
		}
	}
	
	public List<Client> doGetUsers(String name) {
		try {
			writer.getUsers(name);
			writer.send();
			reader.receive();
			return reader.getUsers();
		} catch (Exception e) {
			return null;
		}
	}

	public List<App> doGetApps(String name, String os) {
		try {
			writer.getApps(name,os);
			writer.send();
			reader.receive();
			return reader.getApps();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean install(String name, String os, List<App> apps, List<Client> clients) {
		try {
			writer.install(name,os,apps,clients);
			writer.send();
			reader.receive();
			return reader.getDone();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean doControl(String name, String address) {
		try {
			writer.control(name,address);
			writer.send();
			reader.receive();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void stopControl(String name, String address) {
		try {
			writer.stopControl(name,address);
			writer.send();
		} catch (Exception e) {
		}
	}

	public List<String> doGetDirs(String name)
	{
		try {
			writer.getDirs(name);
			writer.send();
			reader.receive();
			return reader.getDirs();
		} catch (Exception e) {
			return null;
		}
	}

}
