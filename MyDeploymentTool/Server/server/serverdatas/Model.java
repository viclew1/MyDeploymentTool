package server.serverdatas;

import java.util.ArrayList;
import java.util.List;


public class Model {

	private List<Client> clients=new ArrayList<Client>();
	private List<Client> admins=new ArrayList<Client>();

	public List<Client> getClients() {
		return clients;
	}
	
	public List<Client> getAdmins() {
		return admins;
	}
	
	public Client getClient(String address) {
    	for (Client c : getClients()) {
    		if (address.equals(c.getAddress()))
    			return c;
    	}
    	return null;
    }

	public Client getAdmin(String name) {
		for (Client c : getAdmins()) {
    		if (name.equals(c.getName()))
    			return c;
    	}
    	return null;
	}

}
