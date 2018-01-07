package server;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import common.Protocol;
import server.serverdatas.Client;

public class MessageSession {

	private Socket connection;
	private NetworkListener listener;
	
	private MessageReader reader;
	private MessageWriter writer;

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
			reader = new MessageReader(connection.getInputStream());
			writer = new MessageWriter(connection.getOutputStream());
			while (true)
			{
				reader.receive();
				switch (reader.getType()) {
				case Protocol.RQ_CONNECT_ADMIN:
					listener.connectMessagesAdmin(reader.getUserName(), this);
					writer.ok();
					break;
				case Protocol.RQ_CONNECT:
					listener.connectMessagesUser(connection.getInetAddress().getHostName(), connection.getInetAddress().getHostAddress(), this);
					writer.ok();
					break;
				default:
					break;
				}
				writer.send();
				return false;
			}
		}
		catch (Exception e) {
		}
		return false;
	}
	
	public void dispatchClients(List<Client> clients)
	{
		writer.clients(clients);
		writer.send();
	}

	public boolean dispatchFile (String path, String fileName) {
		try {
			File file=new File(path+fileName);
			if (file.isFile())
				writer.doDispatchFile(file, fileName);
			else writer.doDispatchDir(file, fileName);
			writer.send();
			reader.receive();
			return reader.getDone();
		}
		catch (Exception e) {
			return false;
		}
	}

	public void doDispatchInfos(String info) {
		try {
			writer.doDispatchInfo(info);
			writer.send();
		}
		catch (Exception e) {
			return;
		}
	}

	public boolean requestControl(String adminIp) {
		try {
			writer.doRequestControl(adminIp);
			writer.send();
			reader.receive();
			return reader.getDone();
		}
		catch (Exception e) {
			return false;
		}
	}

	public void stopControl() {
		try {
			writer.doStopControl();
			writer.send();
		}
		catch (Exception e) {
		}
	}

	public boolean isSocketOk()
	{
		return !(connection==null || connection.isClosed());
	}
}
