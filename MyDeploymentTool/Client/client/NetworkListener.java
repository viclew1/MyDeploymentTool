package client;

import java.net.UnknownHostException;

public interface NetworkListener {

	public void notifyConnection() throws UnknownHostException;
	public boolean processControl(String adminIp);
	public boolean isControlled();
	public void stopControl();
	public void disconnect();
	
	public void updateDownload(int percent, String fileName, boolean finished);
	
}
