package admin;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import admin.admindatas.*;


public class ApplicationAdmin implements NetworkListener, GUIListener, ControlListener {

	private CommandSession command;
	private MessagesSession messages;
	private Model model;
	private ModelListener gui;
	private ConnectFrame connectFrame;


	@Override
	public void lookForClients() {
		if (model.isConnected()) {
			List<Client> clients=command.doGetUsers(model.getName());
			updateClientsList(clients, false);
		} else {
			updateInfo("Cette opération nécessite d'être connecté.");
		}
	}


	@Override
	public void updateClientsList(List<Client> clients, boolean autoUpdate)
	{
		model.getClients().clear();
		for (int i=0;i<clients.size();i++)
			model.getClients().add(clients.get(i));
		if (gui != null)
			gui.updateClients(autoUpdate);		
	}

	public void start () {
		model = new Model ();
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				ConnectFrame cf = new ConnectFrame(ApplicationAdmin.this);
				cf.setVisible(true);
				connectFrame = cf;
			}
		});
	}

	public static void main(String[] args) {
		new ApplicationAdmin().start();
	}

	@Override
	public void requestConnection(String name) {
		if (model.isConnected()) {
			String n = model.getName() == null ? name : model.getName();
			command.doDisconnect(n);
			command.close();
			command = null;
			messages.close();
			messages = null;
			model.setConnected (false);
			connectFrame.updateStatus(model.getName()+" est déconnecté.");
			gui.exit();
			connectFrame.setVisible(true);
		} else {
			command = new CommandSession();
			command.open();
			if (command.doConnect (name)) {
				messages = new MessagesSession(name, this);
				messages.open();
				model.setName (name);
				model.setConnected (true);
				connectFrame.updateStatus(model.getName()+" est connecté.");
				connectFrame.setVisible(false);
				GUI g=new GUI(this, model);
				g.setVisible(true);
				gui=g;
				lookForClients();
				requestDirNames();
				requestFileNames(model.getDirs().get(0));
			} 
			else {
				command = null;
				connectFrame.updateStatus("Connexion impossible.");
			}
		}
	}

	@Override
	public void requestFileNames(String dir) {
		if (model.isConnected()) {
			List<App> apps=command.doGetApps(model.getName(),dir);
			model.updateApps(apps);
			gui.updateApps();
		} else {
			updateInfo("Cette opération nécessite d'être connecté.");
		}
	}

	@Override
	public void requestDirNames() {
		if (model.isConnected()) {
			List<String> dirs=command.doGetDirs(model.getName());
			model.updateDirs(dirs);
			gui.updateDirs();
		} else {
			updateInfo("Cette opération nécessite d'être connecté.");
		}
	}


	@Override
	public void requestInstall(String dir, List<App> apps, List<Client> clients)
	{
		if (model.isConnected()) 
		{
			boolean result=command.install(model.getName(),dir,apps,clients);
			if (result)
			{
				gui.updateInstall("Requête d'installation réussie.");
			}
			else
			{
				updateInfo("Requête d'installation échouée.");
			}
		} else {
			updateInfo("Cette opération nécessite d'être connecté.");
		}
	}


	@Override
	public void updateInfo(String info) {
		gui.updateStatus(info);
	}


	@Override
	public void requestControl(Client client) {
		if (model.isConnected()) {
			if (model.getControlFrame(client.getAddress())!=null)
			{
				updateInfo("Prise en main de "+client.getName()+" déjà en cours.");
			}
			else if (command.doControl(model.getName(), client.getAddress()))
			{
				model.getControlFrames().add(new ControlGUI(this, client));
				model.getControlFrame(client.getAddress()).setVisible(true);
				updateInfo("Prise en main de "+client.getName()+" réussie.");
			}
			else
			{
				updateInfo("Prise en main de "+client.getName()+" échouée.");
			}
		} 
		else 
		{
			updateInfo("Cette opération nécessite d'être connecté.");
		}
	}


	@Override
	public void updateControl(String clientAddr, BufferedImage img) {
		ControlGUI cg=model.getControlFrame(clientAddr);
		if (cg!=null)
			cg.updateImg(img);
	}


	@Override
	public void stopControl(Client client) {
		if (model.isConnected()) {
			command.stopControl(model.getName(), client.getAddress());
			model.removeControlFrame(client.getAddress());
			updateInfo("Prise en main de "+client.getName()+" arrêtée.");
		} else {
			updateInfo("Cette opération nécessite d'être connecté.");
		}
	}


}
