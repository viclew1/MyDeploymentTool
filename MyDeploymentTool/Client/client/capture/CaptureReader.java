package client.capture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import common.Protocol;
import common.Reader;

public class CaptureReader extends Reader {

	public CaptureReader(InputStream inputStream) {
		super(inputStream);
	}

	private BufferedImage img;
	private String clientAddr;
	private boolean done;


	public void receive() throws IOException {
		type = readInt ();
		done = false;
		switch (type)
		{
		case Protocol.RP_OK:
			done = true;
			break;
		case Protocol.RP_CONTROL:
			clientAddr=readString();
			img=readBufferedImage();
			break;
		default:
			break;
		}
	}
	
	public BufferedImage getImg()
	{
		return img;
	}


	public String getClientAddr() {
		return clientAddr;
	}

	public boolean getDone()
	{
		return done;
	}
}
