package admin;

import java.io.OutputStream;
import java.util.List;

import admin.admindatas.App;
import admin.admindatas.Client;
import common.Protocol;
import common.Writer;

public class CommandWriter extends Writer {

	public CommandWriter(OutputStream outputStream) {
		super (outputStream);
	}

	public void connect(String name) {
		writeInt(Protocol.RQ_CONNECT_ADMIN);
		writeString(name);
	}
	
	public void disconnect(String name) {
		writeInt(Protocol.RQ_DISCONNECT_ADMIN);
		writeString(name);
	}

	public void getUsers(String name) {
		writeInt(Protocol.RQ_CLIENTS);
		writeString(name);
	}
	
	public void getApps(String name, String os) {
		writeInt(Protocol.RQ_APPS);
		writeString(name);
		writeString(os);
	}

	public void install(String name, String os, List<App> apps, List<Client> clients) {
		writeInt(Protocol.RQ_INSTALL);
		writeString(name);
		writeString(os);
		writeInt(apps.size());
		for (App app : apps)
			writeString(app.getName());
		writeInt(clients.size());
		for (Client client : clients)
			writeString(client.getAddress());
	}

	public void control(String name, String address) {
		writeInt(Protocol.RQ_CONTROL);
		writeString(name);
		writeString(address);
	}

	public void stopControl(String name, String address) {
		writeInt(Protocol.RQ_STOP_CONTROL);
		writeString(name);
		writeString(address);
	}

	public void getDirs(String name)
	{
		writeInt(Protocol.RQ_DIR_NAMES);
		writeString(name);
	}
}
