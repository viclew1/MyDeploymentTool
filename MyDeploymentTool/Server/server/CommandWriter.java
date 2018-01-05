package server;

import java.io.OutputStream;
import java.util.List;

import common.Protocol;
import common.Writer;
import server.serverdatas.App;
import server.serverdatas.Client;

public class CommandWriter extends Writer{

	public CommandWriter(OutputStream outputStream) {
		super(outputStream);
	}

	public void ok()
	{
		writeInt(Protocol.RP_OK);
	}

	public void ko()
	{
		writeInt(Protocol.RP_KO);
	}

	public void clients(List<Client> clients) {
		writeInt(Protocol.RP_CLIENTS);
		writeInt(clients.size());
		for (int i=0;i<clients.size();i++)
		{
			writeString(clients.get(i).getName());
			writeString(clients.get(i).getAddress());
			writeBoolean(clients.get(i).isBusy());
			writeBoolean(clients.get(i).isConnected());
		}
	}

	public void apps(List<App> apps) {
		writeInt(Protocol.RP_APPS);
		writeInt(apps.size());
		for (int i=0;i<apps.size();i++)
		{
			writeString(apps.get(i).getName());
			writeLong(apps.get(i).getSize());
		}
	}
	
	public void dirs(List<String> dirs)
	{
		writeInt(Protocol.RP_DIR_NAMES);
		writeInt(dirs.size());
		for (String name : dirs)
		{
			writeString(name);
		}
	}
}
