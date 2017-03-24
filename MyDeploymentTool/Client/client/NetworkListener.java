package client;

import java.awt.image.BufferedImage;
import java.net.UnknownHostException;

public interface NetworkListener {

	void notifyConnection() throws UnknownHostException;

	void processControl(String admin);

	boolean isControlled();

	void stopControl();

	BufferedImage takePicture();

}
