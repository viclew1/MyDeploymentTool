package server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import server.serverdatas.App;
import server.serverdatas.Client;
import server.serverdatas.Model;

public class Application implements NetworkListener {

	public final static String SERVER="C:/Users/vicle/Desktop/Serveur/";
	private CommandServer commands = null;
	private MessageServer message = null;
	private Model model;

	public static void main(String[] args) {
		Application m = new Application ();
		m.start();
	}

	public void start () {
		commands = new CommandServer(this);
		message = new MessageServer(this);
		model=new Model();
		commands.start();
		message.start();

		model.getAdmins().add(new Client("test",null));
		model.getAdmins().add(new Client("Utilisateur",null));
	}



	@Override
	public boolean connectCommandUser(String name,String address, CommandSession session) {
		Client client = model.getClient(address);
		if (client == null)
		{
			client=new Client(name,address);
			model.getClients().add(client);
		}
		if (client.isConnected()) return false;
		client.setCommandSession(session);
		return true;
	}

	@Override
	public boolean connectCommandAdmin(String name,CommandSession session) {
		Client client = model.getAdmin(name);
		if (client == null)
			return false;
		if (client.isConnected()) 
			return false;
		client.setCommandSession(session);
		return true;
	}

	@Override
	public boolean connectMessagesUser(String name, String address, MessageSession session) {
		Client client = model.getClient(address);
		if (client == null)
			return false;
		if (!client.isConnected()) return false;
		client.setMessageSession(session);
		return true;

	}

	@Override
	public boolean connectMessagesAdmin(String name, MessageSession session) {
		Client client = model.getAdmin(name);
		if (client == null)
			return false;
		if (!client.isConnected()) return false;
		client.setMessageSession(session);
		return true;

	}

	@Override
	public List<Client> processUsers(String name)
	{
		Client c=model.getAdmin(name);
		if (c==null) return null;

		return model.getClients();
	}

	@Override
	public boolean disconnectUser(String address) {
		Client client = model.getClient(address);
		if (client == null) return false;
		if (! client .isConnected()) return false;
		client.setCommandSession(null);
		client.setMessageSession(null);
		return true;
	}

	@Override
	public boolean disconnectAdmin(String name) {
		Client client = model.getAdmin(name);
		if (client == null) return false;
		if (! client .isConnected()) return false;
		client.setCommandSession(null);
		client.setMessageSession(null);
		return true;
	}

	@Override
	public List<App> processApps(String name, String os) {
		Client client = model.getAdmin(name);
		if (client == null) return null;
		if (! client .isConnected()) return null;
		List<App> apps=new ArrayList<App>();
		File dossier=new File(SERVER+os);
		for (File f : dossier.listFiles())
			apps.add(new App(f));
		return apps;
	}

	@Override
	public int processInstall(String name, String os, List<String> files, List<String> dests) {
		Client admin = model.getAdmin(name);
		if (admin == null) return 0;
		if (! admin.isConnected()) return 0;
		int echecs=0;
		for (String dest : dests)
			for (String fileName : files)
				if (dispatchApp(model.getClient(dest),os,fileName))
					dispatchInfos(name,"SUCCES : "+fileName+" sur : "+dest);
				else
				{
					echecs++;
					dispatchInfos(name,"ECHEC  : "+fileName+" sur : "+dest);
				}
		return echecs;
	}

	@Override
	public void dispatchInfos(String name, String info) {
		Client admin = model.getAdmin(name);
		if (! admin.isConnected()) return;
		admin.getMessageSession().doDispatchInfos(info);
	}

	@Override
	public boolean dispatchApp(Client client,String os, String fileName) {
		return client.getMessageSession().dispatchFile(SERVER+os+"/", fileName);
	}

	@Override
	public boolean takeControl(String name, String dest) {
		Client admin = model.getAdmin(name);
		if (admin == null) return false;
		if (! admin.isConnected()) return false;
		Client client=model.getClient(dest);
		if (!client.isConnected()) return false;
		return client.getMessageSession().requestControl(name);
	}

	@Override
	public void sendCapture(String addr, String adminName, BufferedImage img) {
		boolean ok=true;
		Client admin = model.getAdmin(adminName);
		if (admin == null) ok=false;
		if (! admin.isConnected()) ok=false;
		Client client=model.getClient(addr);
		if (!client.isConnected()) ok=false;
		if (!ok)
		{
			
			return;
		}
		admin.getMessageSession().updateControll(addr,img);
	}

	@Override
	public void stopControl(String name, String dest) {
		Client admin = model.getAdmin(name);
		if (admin == null) return;
		if (! admin.isConnected()) return;
		Client client=model.getClient(dest);
		if (!client.isConnected()) return;
		client.getMessageSession().stopControl();
	}

	@Override
	public BufferedImage getPhotoFromClient(String name, String dest) {
		Client admin = model.getAdmin(name);
		if (admin == null) return null;
		if (! admin.isConnected()) return null;
		Client client=model.getClient(dest);
		if (!client.isConnected()) return null;
		return client.getMessageSession().requestPhoto();
	}




}
