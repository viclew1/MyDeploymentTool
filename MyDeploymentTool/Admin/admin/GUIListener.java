/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.util.List;

import admin.admindatas.App;
import admin.admindatas.Client;

public interface GUIListener {

	public void requestConnection(String name);

	public void lookForClients();

	public void requestFileNames(String os);

	public void requestInstall(String os, List<App> apps, List<Client> clients);

	public void requestControl(Client client);
	
}
