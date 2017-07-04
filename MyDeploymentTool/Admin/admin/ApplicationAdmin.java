package admin;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import admin.admindatas.*;
import common.Protocol;


public class ApplicationAdmin implements NetworkListener, GUIListener, ControlListener {

	private CommandSession command;
	private MessagesSession messages;
	private Model model;
	private ModelListener gui;


	@Override
	public void lookForClients() {
		if (model.isConnected()) {
			List<Client> clients=command.doGetUsers(model.getName());
			model.getClients().clear();
			for (int i=0;i<clients.size();i++)
				model.getClients().add(clients.get(i));
			gui.updateClients();

		} else {
			updateInfo("Cette op�ration n�cessite d'�tre connect�.");
		}
	}


	public void start () {
		Protocol.IPSERV=JOptionPane.showInputDialog("Adresse IP du serveur ?");
		if (Protocol.IPSERV==null || Protocol.IPSERV.equals(""))
			System.exit(0);
		model = new Model ();
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				GUI g = new GUI(ApplicationAdmin.this, model);
				g.setVisible(true);
				gui = g;
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
			gui.updateConnection(model.getName()+" est d�connect�.");
		} else {
			command = new CommandSession();
			command.open();
			if (command.doConnect (name)) {
				messages = new MessagesSession(name, this);
				messages.open();
				model.setName (name);
				model.setConnected (true);
				gui.updateConnection(model.getName()+" est connect�.");
			} 
			else {
				command = null;
				gui.updateConnection("Connexion impossible.");
			}
		}
	}

	@Override
	public void requestFileNames(String os) {
		if (model.isConnected()) {
			List<App> apps=command.doGetApps(model.getName(),os);
			model.updateApps(apps);
			gui.updatePath();
		} else {
			updateInfo("Cette op�ration n�cessite d'�tre connect�.");
		}
	}


	@Override
	public void requestInstall(String os, List<App> apps, List<Client> clients)
	{
		if (model.isConnected()) 
		{
			gui.loading();
			int[] result=command.install(model.getName(),os,apps,clients);
			if (result!=null)
			{
				gui.stopLoading();
				gui.updateInstall("Installation finie. ("+(result[0]-result[1])+"/"+result[0]+")");
			}
			else
			{
				gui.stopLoading();
				updateInfo("Installation �chou�e.");
			}
		} else {
			updateInfo("Cette op�ration n�cessite d'�tre connect�.");
		}
	}


	@Override
	public void updateInfo(String info) {
		gui.updateStatus(info);
	}


	@Override
	public void requestControl(Client client) {
		if (model.isConnected()) {
			gui.loading();
			if (model.getControlFrame(client.getAddress())!=null)
			{
				gui.stopLoading();
				updateInfo("Prise en main de "+client.getName()+" d�j� en cours.");
			}
			else if (command.doControl(model.getName(), client.getAddress()))
			{
				model.getControlFrames().add(new ControlGUI(this, client));
				model.getControlFrame(client.getAddress()).setVisible(true);
				gui.stopLoading();
				updateInfo("Prise en main de "+client.getName()+" r�ussie.");
			}
			else
			{
				gui.stopLoading();
				updateInfo("Prise en main de "+client.getName()+" �chou�e.");
			}
		} 
		else 
		{
			updateInfo("Cette op�ration n�cessite d'�tre connect�.");
		}
	}


	@Override
	public void updateControl(String clientAddr, BufferedImage img) {
		try {
			ControlGUI cg=model.getControlFrame(clientAddr);
			if (cg!=null)
				cg.updateImg(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void stopControl(Client client) {
		if (model.isConnected()) {
			command.stopControl(model.getName(), client.getAddress());
			model.removeControlFrame(client.getAddress());
			updateInfo("Prise en main de "+client.getName()+" arr�t�e.");
		} else {
			updateInfo("Cette op�ration n�cessite d'�tre connect�.");
		}
	}


}
