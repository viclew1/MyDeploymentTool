package server;

import java.awt.image.BufferedImage;
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

	public void installResult(int nbDests, int nbFiles, int nbEchecs) {
		writeInt(Protocol.RP_INSTALL);
		writeInt(nbDests*nbFiles);
		writeInt(nbEchecs);
	}

}
