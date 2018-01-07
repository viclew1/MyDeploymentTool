package admin.capture;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import admin.NetworkListener;
import common.Protocol;

public class CaptureServer extends Thread 
{
	private ServerSocket server = null;
	private NetworkListener listener = null;

	public CaptureServer(NetworkListener listener) {
		super();
		this.listener = listener;
	}

	@Override
	public void run () {
		try {
			server = new ServerSocket (Protocol.CAPTURE_PORT);
			while (true) {
				Socket connection = server.accept();
				CaptureSession session = new CaptureSession (connection, listener);
				session.start();
			}
		} catch (SocketException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		try
		{
			server.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
