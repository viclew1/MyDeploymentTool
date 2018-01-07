package admin.capture;

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


	public void receive() throws IOException {
		type = readInt ();
		switch (type)
		{
		case Protocol.RQ_DISCONNECT:
			
			break;
		case Protocol.RP_CONTROL:
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

}
