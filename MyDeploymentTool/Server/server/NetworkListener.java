package server;

import java.awt.image.BufferedImage;
import java.util.List;

import server.serverdatas.App;
import server.serverdatas.Client;

public interface NetworkListener {
	
	boolean connectMessagesUser(String userName, String address, MessageSession messageSession);
	boolean connectCommandUser(String name, String address, CommandSession session);
	boolean connectCommandAdmin(String name, CommandSession session);
	boolean connectMessagesAdmin(String name, MessageSession session);
	boolean disconnectUser(String name);
	List<App> processApps(String name, String dir);
	List<String> processDirs(String name);
	List<Client> processUsers(String name);
	boolean disconnectAdmin(String name);
	void processInstall(String name, String os, List<String> files, List<String> dests);
	boolean dispatchApp(Client client, String dir, String fileName);
	void dispatchInfos(String name, String info);
	boolean takeControl(String name, String dest);
	void sendCapture(String name, String admin, BufferedImage bufferedImage);
	void stopControl(String name, String dest);
	void processOrders();
	void processClients();
	void checkConnections();
}
