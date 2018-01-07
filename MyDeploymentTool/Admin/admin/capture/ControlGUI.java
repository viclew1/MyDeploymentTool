package admin.capture;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import admin.admindatas.Client;
import admin.admindatas.ControlListener;

public class ControlGUI extends JFrame
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 123402515334371522L;


	private ControlListener listener;
	private Client client;
	private BufferedImage img;
	private JPanel panel;

	@SuppressWarnings("serial")
	public ControlGUI(ControlListener listener, Client client)
	{
		super(client.getName()+" : "+client.getAddress());
		this.client=client;
		this.listener=listener;
		setPreferredSize(new Dimension(1200,900));
		addStopControlOnClose();
		panel=new JPanel(){

			@Override
			public void paint(Graphics g)
			{
				super.getRootPane().updateUI();
				g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
			}
			
		};
		this.add(panel);
		pack();
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
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

	public void updateImg(BufferedImage img)
	{
		this.img = img;
	}

	public Client getClient() 
	{
		return client;
	}
}
