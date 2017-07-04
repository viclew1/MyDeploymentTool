package server;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import common.Protocol;
import common.Reader;

public class MessageReader extends Reader {

	private String userName;
	private boolean done;
	private BufferedImage img;
	
	public MessageReader(InputStream inputStream) {
		super (inputStream);
	}

	public void receive() {
		type = readInt ();
		switch (type) {
		case Protocol.RQ_CONNECT:
			break;
		case Protocol.RQ_CONNECT_ADMIN:
			userName=readString();
			break;
		case Protocol.RP_OK:
			done=true;
			break;
		case Protocol.RP_KO:
			done=false;
			img=null;
			break;
		default:
			break;
		}
	}

	public String getUserName() {
		return userName;
	}

	public boolean getDone() {
		return done;
	}

	public BufferedImage getImg() {
		return img;
	}
	
}
