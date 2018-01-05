package admin;

import java.awt.image.BufferedImage;
import java.util.List;

import admin.admindatas.Client;

public interface NetworkListener {

	void updateInfo(String info);
	void updateControl(String clientName, BufferedImage bufferedImage);
	void updateClientsList(List<Client> clients, boolean autoUpdate);

}
