package admin.admindatas;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import static admin.admindatas.Constantes.*;

public class Icons
{

	public static final Icon DISCONNECTED_ICON, BUSY_ICON, AVAILABLE_ICON;
	
	static 
	{
		BufferedImage disconnectedImg = null, busyImg = null, availableImg = null;
		try
		{
			disconnectedImg = resize(ImageIO.read(Icons.class.getResourceAsStream("disconnected.png")), IMG_W, IMG_H);
			busyImg 		= resize(ImageIO.read(Icons.class.getResourceAsStream("busy.png")), IMG_W, IMG_H);
			availableImg 	= resize(ImageIO.read(Icons.class.getResourceAsStream("available.png")), IMG_W, IMG_H);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		DISCONNECTED_ICON = new ImageIcon(disconnectedImg);
		BUSY_ICON 		  = new ImageIcon(busyImg);
		AVAILABLE_ICON 	  = new ImageIcon(availableImg);
	}
	
	private static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}
	
}
