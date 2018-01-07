package client.capture;

import java.awt.image.BufferedImage;
import java.net.Socket;

import common.Protocol;

public class CaptureSession
{

	private Socket connection;
	
	private CaptureWriter writer;
	
	public CaptureSession () {
	}

	public boolean close () {
		try {
			if (connection != null) connection.close();
			connection = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean open (String adminIp) {
		this.close();
		try {
			connection = new Socket(adminIp, Protocol.CAPTURE_PORT);
			writer = new CaptureWriter(connection.getOutputStream());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sendImage(BufferedImage img) {
		try {
			writer.control(img);
			return writer.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isSocketOk()
	{
		return !(connection==null || connection.isClosed());
	}


}

