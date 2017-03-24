package admin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import common.Protocol;
import common.Reader;

public class MessagesReader extends Reader {

	public MessagesReader(InputStream inputStream) {
		super(inputStream);
	}

	private String info="";
	private String clientName;
	private BufferedImage img;
	private String clientAddr;


	public void receive() throws IOException {
		type = readInt ();
		switch (type)
		{
		case Protocol.RP_INFO:
			info=readString();
			break;
		case Protocol.RP_CONTROL:
			clientAddr=readString();
			img=readBufferedImage();
			break;
		default:
			break;
		}
	}
	
	
	public String getInfo()
	{
		return info;
	}
	
	public String getClientName()
	{
		return clientName;
	}

	public BufferedImage getImg()
	{
		return img;
	}


	public String getClientAddr() {
		return clientAddr;
	}

}
