package server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import server.serverdatas.App;
import server.serverdatas.Client;
import server.serverdatas.Model;
import server.serverdatas.InstallOrder;
import static server.serverdatas.Constantes.*;

public class ApplicationServeur implements NetworkListener 
{

	private CommandServer commands = null;
	private MessageServer message = null;
	private Pusher pusher = null;
	private Model model;

	public static void main(String[] args) 
	{
		for (String arg : args)
		{
			switch (arg)
			{
			case "-d":
				DEBUG = true;
				break;
			case "-h":
				displayHelp();
				System.exit(0);
				break;
			default:
				System.out.println("Argument : " + arg + " inconnu. Consultez l'aide.");
				displayHelp();
				System.exit(0);
				break;
			}
		}
		ApplicationServeur m = new ApplicationServeur ();
		displayStart();
		m.start();
	}
	
	public static void displayStart()
	{
		System.out.println("MyDeploymentTool SERVER STARTING");
	}
	
	public static void displayHelp()
	{
		System.out.println("MyDeploymentTool HELP");
		System.out.println("Arguments : ");
		System.out.println("    -d  : mode debug");
		System.out.println("    -h  : aide");
	}

	public void start () 
	{
		commands = new CommandServer(this);
		message = new MessageServer(this);
		pusher = new Pusher(this);

		model=new Model();
		model.getAdmins().add(new Client("test",null));
		model.getAdmins().add(new Client("admin",null));

		commands.start();
		message.start();
		pusher.start();
	}

	@Override
	public boolean connectCommandUser(String name,String address, CommandSession session) 
	{
		if (DEBUG) System.out.println("Trying to connect USER : " + address + " CommandSession");
		Client client = model.getClient(address);
		if (client == null)
		{
			if (DEBUG) System.out.println("USER does not exist yet, adding him");
			client=new Client(name,address);
			model.getClients().add(client);
		}
		if (client.isConnected()) 
		{
			if (DEBUG) System.out.println("USER is already connected, CONNECTION FAILED\n");
			return false;
		}
		client.setCommandSession(session);
		if (DEBUG) System.out.println("USER : CONNECTION SUCCESS\n");
		return true;
	}

	@Override
	public boolean connectCommandAdmin(String name,CommandSession session) 
	{
		if (DEBUG) System.out.println("Trying to connect ADMIN : " + name + " CommandSession");
		Client client = model.getAdmin(name);
		if (client == null)
		{
			if (DEBUG) System.out.println("ADMIN does not exist, CONNECTION FAILED\n");
			return false;
		}
		if (client.isConnected()) 
		{
			if (DEBUG) System.out.println("ADMIN is already connected, CONNECTION FAILED\n");
			return false;
		}
		client.setCommandSession(session);
		if (DEBUG) System.out.println("ADMIN : CONNECTION SUCCESS\n");
		return true;
	}

	@Override
	public boolean connectMessagesUser(String name, String address, MessageSession session) 
	{
		if (DEBUG) System.out.println("Trying to connect USER : " + name + " MessageSession");
		Client client = model.getClient(address);
		if (client == null)
		{
			if (DEBUG) System.out.println("USER does not exist, CONNECTION FAILED\n");
			return false;
		}
		if (!client.isConnected()) 
		{
			if (DEBUG) System.out.println("USER CommandSession is not connected, CONNECTION FAILED\n");
			return false;
		}
		client.setMessageSession(session);
		if (DEBUG) System.out.println("USER : CONNECTION SUCCESS\n");
		return true;

	}

	@Override
	public boolean connectMessagesAdmin(String name, MessageSession session) 
	{
		if (DEBUG) System.out.println("Trying to connect ADMIN : " + name + " MessageSession");
		Client client = model.getAdmin(name);
		if (client == null)
		{
			if (DEBUG) System.out.println("ADMIN does not exist, CONNECTION FAILED\n");
			return false;
		}
		if (!client.isConnected()) 
		{
			if (DEBUG) System.out.println("ADMIN CommandSession is not connected, CONNECTION FAILED\n");
			return false;
		}
		client.setMessageSession(session);
		if (DEBUG) System.out.println("ADMIN : CONNECTION SUCCESS\n");
		return true;

	}

	@Override
	public List<Client> processUsers(String name)
	{
		if (DEBUG) System.out.println("Fetching a list of users to ADMIN " + name);
		Client c=model.getAdmin(name);
		if (c==null) 
		{
			if (DEBUG) System.out.println("ADMIN does not exist, REQUEST FAILED\n");
			return null;
		}
		if (DEBUG) System.out.println("PROCESS USERS : REQUEST SUCCESS\n");
		return model.getClients();
	}

	@Override
	public void processClients()
	{
		if (DEBUG) System.out.println("Processing a list of users to every ADMIN\n");
		for (Client admin : model.getAdmins())
		{
			if (admin.isConnected())
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						admin.getMessageSession().dispatchClients(model.getClients());
					}
				}).start();
		}
	}

	@Override
	public boolean disconnectUser(String address) 
	{
		if (DEBUG) System.out.println("Trying to disconnect USER : " + address);
		Client client = model.getClient(address);
		if (client == null) 
		{
			if (DEBUG) System.out.println("USER does not exist, DISCONNECTION FAILED\n");
			return false;
		}
		if (! client .isConnected()) 
		{
			if (DEBUG) System.out.println("USER is not connected, DISCONNECTION FAILED\n");
			return false;
		}
		client.setCommandSession(null);
		client.setMessageSession(null);
		if (DEBUG) System.out.println("USER : DISCONNECTION SUCCESS\n");
		return true;
	}

	@Override
	public boolean disconnectAdmin(String name) 
	{
		if (DEBUG) System.out.println("Trying to disconnect ADMIN : " + name);
		Client admin = model.getAdmin(name);
		if (admin == null) 
		{
			if (DEBUG) System.out.println("ADMIN does not exist, DISCONNECTION FAILED\n");
			return false;
		}
		if (! admin .isConnected()) 
		{
			if (DEBUG) System.out.println("ADMIN is not connected, DISCONNECTION FAILED\n");
			return false;
		}
		admin.setCommandSession(null);
		admin.setMessageSession(null);
		if (DEBUG) System.out.println("ADMIN : DISCONNECTION SUCCESS\n");
		return true;
	}

	@Override
	public List<App> processApps(String name, String dir) 
	{
		if (DEBUG) System.out.println("Dispatching dir : " + dir + " content to ADMIN : " + name);
		Client admin = model.getAdmin(name);
		if (admin == null) 
		{
			if (DEBUG) System.out.println("ADMIN does not exist, REQUEST FAILED\n");
			return new ArrayList<>();
		}
		if (! admin .isConnected()) 
		{
			if (DEBUG) System.out.println("ADMIN is not connected, REQUEST FAILED\n");
			return new ArrayList<>();
		}
		List<App> apps=new ArrayList<App>();
		File dossier=new File(SERVER+dir);
		if (!dossier.getAbsolutePath().startsWith(SRV_FILE.getAbsolutePath()))
			return apps;
		for (File f : dossier.listFiles())
			apps.add(new App(f));
		if (DEBUG) System.out.println("ADMIN : PROCESS APPS SUCCESS\n");
		return apps;
	}

	@Override
	public List<String> processDirs(String name) 
	{
		if (DEBUG) System.out.println("Dispatching dirs names to ADMIN : " + name);
		Client admin = model.getAdmin(name);
		if (admin == null) 
		{
			if (DEBUG) System.out.println("ADMIN does not exist, REQUEST FAILED\n");
			return new ArrayList<>();
		}
		if (! admin .isConnected()) 
		{
			if (DEBUG) System.out.println("ADMIN is not connected, REQUEST FAILED\n");
			return new ArrayList<>();
		}
		List<String> dirs=new ArrayList<String>();
		File dossier=new File(SERVER);
		for (File f : dossier.listFiles())
			dirs.add(f.getName());
		if (DEBUG) System.out.println("ADMIN : PROCESS DIRS SUCCESS\n");
		return dirs;
	}

	@Override
	public void processInstall(String name, String dir, List<String> files, List<String> dests) 
	{
		if (DEBUG) System.out.println("Treating install order from ADMIN : " + name);
		Client admin = model.getAdmin(name);
		if (admin == null) 
		{
			if (DEBUG) System.out.println("ADMIN does not exist, REQUEST FAILED\n");
			return;
		}
		if (! admin.isConnected()) 
		{
			if (DEBUG) System.out.println("ADMIN is not connected, REQUEST FAILED\n");
			return;
		}
		if (DEBUG) System.out.println("FILES : ");
		if (DEBUG)
			for (String f : files)
				System.out.println("   - " + f);
		if (DEBUG) System.out.println("DESTS : ");
		if (DEBUG)
			for (String f : dests)
				System.out.println("   - " + f);
		for (String dest : dests)
		{
			Client client = model.getClient(dest);
			if (client == null) continue;
			for (String fileName : files)
			{
				client.addOrder(new InstallOrder(client, dir, fileName));
			}
		}
		if (DEBUG) System.out.println("ADMIN : PROCESS INSTALL SUCCESS\n");
	}

	@Override
	public void dispatchInfos(String name, String info) 
	{
		if (DEBUG) System.out.println("Dispatching info to ADMIN : " + name);
		Client admin = model.getAdmin(name);
		if (admin == null) 
		{
			if (DEBUG) System.out.println("ADMIN does not exist, REQUEST FAILED\n");
			return;
		}
		if (! admin.isConnected()) 
		{
			if (DEBUG) System.out.println("ADMIN is not connected, REQUEST FAILED\n");
			return;
		}
		admin.getMessageSession().doDispatchInfos(info);
		if (DEBUG) System.out.println("ADMIN : DISPATCH INFOS SUCCESS\n");
	}

	@Override
	public boolean dispatchApp(Client client,String dir, String fileName) 
	{
		if (client == null)
		{
			if (DEBUG) System.out.println("Dispatching app : " + dir + "/" + fileName + " to USER : " + client);
			if (DEBUG) System.out.println("USER does not exist, REQUEST FAILED\n");
			return false;
		}


		if (DEBUG) System.out.println("Dispatching app : " + dir + "/" + fileName + " to USER : " + client.getAddress());
		if (!client.isConnected()) 
		{
			if (DEBUG) System.out.println("USER is not connected, REQUEST FAILED\n");
			return false;
		}
		boolean ok = client.getMessageSession().dispatchFile(SERVER+dir+"/", fileName);
		if (DEBUG) System.out.println("SERVER : DISPATCH APP SUCCESS\n");
		return ok;
	}

	@Override
	public boolean takeControl(String name, String dest) 
	{
		if (DEBUG) System.out.println("Treating control order from ADMIN : " + name);
		Client admin = model.getAdmin(name);
		if (admin == null) 
		{
			if (DEBUG) System.out.println("ADMIN does not exist, REQUEST FAILED\n");
			return false;
		}
		if (! admin.isConnected()) 
		{
			if (DEBUG) System.out.println("ADMIN is not connected, REQUEST FAILED\n");
			return false;
		}
		Client client=model.getClient(dest);
		if (client == null) 
		{
			if (DEBUG) System.out.println("USER does not exist, REQUEST FAILED\n");
			return false;
		}
		if (!client.isConnected()) 
		{
			if (DEBUG) System.out.println("USER is not connected, REQUEST FAILED\n");
			return false;
		}
		boolean ok = client.getMessageSession().requestControl(name);
		if (DEBUG) System.out.println("SERVER : TAKE CONTROLL SUCCESS\n");
		return ok;
	}

	@Override
	public void sendCapture(String addr, String adminName, BufferedImage img) 
	{
		if (DEBUG) System.out.println("Treating capture sending from USER : " + addr + " to ADMIN : " + adminName);
		Client admin = model.getAdmin(adminName);
		if (admin == null) 
		{
			if (DEBUG) System.out.println("ADMIN does not exist, REQUEST FAILED\n");
			return;
		}
		if (! admin.isConnected()) 
		{
			if (DEBUG) System.out.println("ADMIN is not connected, REQUEST FAILED\n");
			return;
		}
		Client client=model.getClient(addr);
		if (client == null) 
		{
			if (DEBUG) System.out.println("USER does not exist, REQUEST FAILED\n");
			return;
		}
		if (!client.isConnected()) 
		{
			if (DEBUG) System.out.println("USER is not connected, REQUEST FAILED\n");
			return;
		}
		admin.getMessageSession().updateControll(addr,img);
		if (DEBUG) System.out.println("SERVER : SEND CAPTURE SUCCESS\n");
	}

	@Override
	public void stopControl(String name, String dest) 
	{
		if (DEBUG) System.out.println("Treating stop control request from ADMIN : " + name + " on USER : " + dest);
		Client admin = model.getAdmin(name);
		if (admin == null) 
		{
			if (DEBUG) System.out.println("ADMIN does not exist, REQUEST FAILED\n");
			return;
		}
		if (! admin.isConnected()) 
		{
			if (DEBUG) System.out.println("ADMIN is not connected, REQUEST FAILED\n");
			return;
		}
		Client client=model.getClient(dest);
		if (client == null) 
		{
			if (DEBUG) System.out.println("USER does not exist, REQUEST FAILED\n");
			return;
		}
		if (!client.isConnected()) 
		{
			if (DEBUG) System.out.println("USER is not connected, REQUEST FAILED\n");
			return;
		}
		client.getMessageSession().stopControl();
		if (DEBUG) System.out.println("SERVER : STOP CONTROL SUCCESS\n");
	}

	@Override
	public void processOrders()
	{
		for (Client cli : model.getClients())
		{
			if (cli.isConnected())
				cli.processTodoOrder();
		}
	}

	@Override
	public void checkConnections()
	{
		for (Client cli : model.getClients())
			if (cli.isConnected() || cli.isMessageConnected())
				if (!cli.getCommandSession().isSocketOk())
					disconnectUser(cli.getAddress());
		for (Client admin : model.getAdmins())
			if (admin.isConnected() || admin.isMessageConnected())
				if (!admin.getCommandSession().isSocketOk())
					disconnectAdmin(admin.getAddress());
	}


}
