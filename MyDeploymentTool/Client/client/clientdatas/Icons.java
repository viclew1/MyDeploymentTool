package client.clientdatas;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Icons
{

	public static BufferedImage MOUSE_IMAGE;
	
	static 
	{
		try
		{
			MOUSE_IMAGE = ImageIO.read(Icons.class.getResourceAsStream("mouse.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
