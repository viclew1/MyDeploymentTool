package client;
import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import client.capture.CaptureSession;
import client.clientdatas.ClientFrame;
import client.clientdatas.Icons;
import client.clientdatas.Model;
import common.Protocol;


public class ApplicationClient implements NetworkListener {

	private CommandSession command;
	private MessagesSession messages;
	private CaptureSession capture;
	private Model model;
	private ClientFrame clientFrame;

	@Override
	public void notifyConnection() {

		String name="";
		try {
			name = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (model.isConnected()) {
			command.doDisconnect();
			command.close();
			command = null;
			messages.close();
			messages = null;
			model.setConnected (false);
			clientFrame.updateStatus("Déconnecté.");
		} else {
			clientFrame.updateStatus("Connexion en cours ...");
			command = new CommandSession();
			command.open();
			if (command.doConnect (name)) {
				messages = new MessagesSession(this);
				messages.open();
				model.setConnected (true);
				clientFrame.updateStatus("Connexion réussie.");
			} 
			else {
				command = null;
				clientFrame.updateStatus("Connexion échouée.");
			}
		}
	}


	public void start () {
		Protocol.IPSERV=JOptionPane.showInputDialog("Adresse IP du serveur ?");
		if (Protocol.IPSERV==null || Protocol.IPSERV.equals(""))
			System.exit(0);
		JFileChooser chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Dossier de réception");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) 
		{ 
			System.exit(0);
		}
		Protocol.DEST_DIR=chooser.getSelectedFile().getAbsolutePath();
		Protocol.DEST_DIR+="/";

		clientFrame = new ClientFrame(this);
		clientFrame.setVisible(true);

		model = new Model ();
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				notifyConnection();
			}
		});
	}




	public static void main(String[] args) {
		new ApplicationClient().start();
	}


	private static final int BUFFER_SIZE = 20;
	private BufferedImage[] imageBuffer = new BufferedImage[BUFFER_SIZE];
	private int index = 0;
	private int lastScreenshotIndex = 0;
	private boolean captureTaken = false;

	@Override
	public boolean processControl(String adminIp) 
	{
		capture = new CaptureSession();
		if (!capture.open(adminIp))
			return false;
		model.setControlled(true);
		new Thread(new Runnable() 
		{

			@Override
			public void run() 
			{
				GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
				int width = gd.getDisplayMode().getWidth();
				int height = gd.getDisplayMode().getHeight();
				Rectangle r=new Rectangle(0, 0, (int)width, (int)height);
				Robot rob = null;
				try
				{
					rob = new Robot();
				} catch (AWTException e)
				{
					e.printStackTrace();
				}
				Point pointer;
				int xMouse, yMouse;

				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						while (model.isControlled())
						{
							if (captureTaken)
							{
								captureTaken = false;
								if (!capture.sendImage(imageBuffer[lastScreenshotIndex]))
									model.setControlled(false);
							} 
							else
								try {
									Thread.sleep(1);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
						}
					}
				}).start();

				while (model.isControlled())
				{
					BufferedImage newCapture = rob.createScreenCapture(r);
					pointer = MouseInfo.getPointerInfo().getLocation();
					xMouse = (int) pointer.getX();
					yMouse = (int) pointer.getY();
					newCapture.getGraphics().drawImage(Icons.MOUSE_IMAGE, xMouse, yMouse, 16, 24, null);
					imageBuffer[index] = newCapture;
					lastScreenshotIndex = index;
					captureTaken = true;
					index++;
					if (index == BUFFER_SIZE) index = 0;
				}
			}
		}).start();
		return true;
	}


	@Override
	public boolean isControlled() {
		return model.isControlled();
	}


	@Override
	public void stopControl() {
		model.setControlled(false);
	}


	@Override
	public void disconnect()
	{
		if (model.isConnected()) {
			command.doDisconnect();
			command.close();
			command = null;
			messages.close();
			messages = null;
			if (capture != null)
			{
				capture.close();
				capture = null;
			}
			model.setConnected (false);
		}
		clientFrame.updateStatus("Déconnecté.");
	}


	@Override
	public void updateDownload(int percent, String fileName, boolean finished)
	{
		if (!finished)
		{
			clientFrame.updateStatus(percent+" % : "+fileName);
		}
		else
		{
			clientFrame.updateStatus("TELECHARGEMENT TERMINE : "+fileName);
		}
	}


}
