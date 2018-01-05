package admin;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import admin.admindatas.App;
import admin.admindatas.Client;
import common.Protocol;
import common.Reader;


public class CommandReader extends Reader {

	private boolean done=false;
	private List<Client> clients;
	private List<App> apps;
	private List<String> dirs;
	private int nbFiles,nbEchecs;
	private BufferedImage img;

	public CommandReader(InputStream inputStream) {
		super (inputStream);
	}

	public void receive() {
		type = readInt ();
		switch (type) {
		case Protocol.RP_OK:
			done=true;
			break;
		case Protocol.RP_KO:
			done=false;
			break;
		case Protocol.RP_CLIENTS:
			int nbCli=readInt();
			clients=new ArrayList<Client>();
			for (int i=0;i<nbCli;i++)
				clients.add(new Client(readString(), readString(), readBoolean(), readBoolean()));
			break;
		case Protocol.RP_APPS:
			int nbApps=readInt();
			apps=new ArrayList<App>();
			for (int i=0;i<nbApps;i++)
				apps.add(new App(readString(),readLong()));
			break;
		case Protocol.RP_DIR_NAMES:
			int nbDirs=readInt();
			dirs=new ArrayList<String>();
			for (int i=0;i<nbDirs;i++)
				dirs.add(readString());
			break;
		case Protocol.RP_INSTALL:
			nbFiles=readInt();
			nbEchecs=readInt();
			break;
		default:
			break;
		}
	}

	public boolean getDone() {
		return done;
	}

	public List<Client> getUsers() {
		return clients;
	}

	public List<App> getApps() {
		return apps;
	}
	
	public List<String> getDirs()
	{
		return dirs;
	}
	
	public int getNbFiles()
	{
		return nbFiles;
	}
	
	public int getNbEchecs()
	{
		return nbEchecs;
	}

	public BufferedImage getImg() {
		return img;
	}

}
