package admin;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import admin.admindatas.*;


public class Application implements NetworkListener, GUIListener, ControlListener {

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
			updateInfo("Cette opération nécessite d'être connecté.");
		}
	}


	public void start () {
		model = new Model ();
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				GUI g = new GUI(Application.this, model);
				g.setVisible(true);
				gui = g;
			}
		});
	}

	public static void main(String[] args) {
		new Application().start();
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
			gui.updateConnection(model.getName()+" est déconnecté.");
		} else {
			command = new CommandSession();
			command.open();
			if (command.doConnect (name)) {
				messages = new MessagesSession(name, this);
				messages.open();
				model.setName (name);
				model.setConnected (true);
				gui.updateConnection(model.getName()+" est connecté.");
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
			updateInfo("Cette opération nécessite d'être connecté.");
		}
	}


	@Override
	public void requestInstall(String os, List<App> apps, List<Client> clients)
	{
		if (model.isConnected()) {
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
				updateInfo("Installation échouée.");
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
				updateInfo("Prise en main de "+client.getName()+" déjà en cours.");
			else if (command.doControl(model.getName(), client.getAddress()))
			{
				model.getControlFrames().add(new ControlGUI(this, client));
				model.getControlFrame(client.getAddress()).setVisible(true);
				updateInfo("Prise en main de "+client.getName()+" réussie.");
			}
			else
				updateInfo("Prise en main de "+client.getName()+" échouée.");
		} else {
			updateInfo("Cette opération nécessite d'être connecté.");
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
			updateInfo("Prise en main de "+client.getName()+" arrêtée.");
		} else {
			updateInfo("Cette opération nécessite d'être connecté.");
		}
	}


	@Override
	public void takePicture(List<Client> clients) {
		if (model.isConnected()) {
			for (Client client : clients)
			{
				BufferedImage img=command.takePicture(model.getName(), client.getAddress());
				if (img!=null)
				{
					int i=0;
					File f=new File("C:/Users/vicle/Desktop/Photos/Photo"+i+".png");
					while (f.exists())
					{
						i++;
						f=new File("C:/Users/vicle/Desktop/Photos/Photo"+i+".png");
					}
					try {
						ImageIO.write(img,"PNG",f);
					} catch (IOException e) {
						e.printStackTrace();
					}
					updateInfo("Photo de "+client.getName()+" effectuée.");
				}
				else
					updateInfo("Photo de "+client.getName()+" échouée.");
			}
		} else {
			updateInfo("Cette opération nécessite d'être connecté.");
		}
	}


}
