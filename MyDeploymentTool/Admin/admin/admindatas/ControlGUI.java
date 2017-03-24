package admin.admindatas;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlGUI extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 123402515334371522L;
	
	
	private ControlListener listener;
	private Client client;
	private JPanel panel;
	
	public ControlGUI(ControlListener listener, Client client)
	{
		super(client.getName()+" : "+client.getAddress());
		this.client=client;
		this.listener=listener;
		setPreferredSize(new Dimension(1200,900));
		addStopControlOnClose();
		panel=new JPanel();
		this.add(panel);
		pack();
	}
	
	private void addStopControlOnClose()
	{
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				listener.stopControl(client);
			}
		});
	}
	
	public static Image scaleImage(Image source, int width, int height) {
	    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = (Graphics2D) img.getGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.drawImage(source, 0, 0, width, height, null);
	    g.dispose();
	    return img;
	}

	public void updateImg(BufferedImage img) throws IOException {
		int width=getWidth();
		int height=getHeight();
		Image finalImage=scaleImage(img, width, height-50);
		Icon iconScaled = new ImageIcon(finalImage);
		final JLabel jl = new JLabel(iconScaled);
		panel.removeAll();
		panel.add(jl);
		panel.repaint();
		panel.revalidate();
	}

	public Client getClient() {
		return client;
	}
}
