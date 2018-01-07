package admin.admindatas;

import java.util.ArrayList;
import java.util.List;

import admin.capture.ControlGUI;

public class Model {

    private String name;
    private boolean connected;
    private List<App> apps=new ArrayList<App>();
    private List<String> dirs=new ArrayList<String>();
    private List<ControlGUI> controlFrames=new ArrayList<ControlGUI>();
    private List<Client> clients=new ArrayList<Client>();

    public String getName() {
        return name == null ? "" : name;
    }

    public boolean isConnected() {
        return connected;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

	public List<Client> getClients() {
		return clients;
	}
	
	public void updateApps(List<App> apps)
	{
		this.apps=apps;
	}

	public List<App> getApps() {
		return apps;
	}

	public List<Client> getSelectedClients() {
		List<Client> SelectedClients=new ArrayList<Client>();
		if (clients==null) return SelectedClients;
		for (Client client : clients)
			if (client.isSelected())
				SelectedClients.add(client);
		return SelectedClients;
	}

	public List<App> getSelectedApps() {
		List<App> selectedApps=new ArrayList<App>();
		if (apps==null) return selectedApps;
		for (App app : apps)
			if (app.isSelected())
				selectedApps.add(app);
		return selectedApps;
	}

	public ControlGUI getControlFrame(String clientAddr) {
		for (ControlGUI cg : controlFrames)
			if (cg.getClient().getAddress().equals(clientAddr))
				return cg;
		return null;
	}

	public List<ControlGUI> getControlFrames() {
		return controlFrames;
	}

	public void removeControlFrame(String address) {
		for (ControlGUI cg : controlFrames)
			if (cg.getClient().getAddress().equals(address))
			{
				controlFrames.remove(cg);
				return;
			}
	}

	public void updateDirs(List<String> dirs)
	{
		this.dirs=dirs;
	}
	
	public List<String> getDirs()
	{
		return this.dirs;
	}


}