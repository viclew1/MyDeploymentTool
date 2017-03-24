package admin;

import java.awt.image.BufferedImage;

public interface NetworkListener {

	void updateInfo(String info);

	void updateControl(String clientName, BufferedImage bufferedImage);
	

}
