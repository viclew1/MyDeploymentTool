package client;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

import common.Protocol;
import common.Writer;


public class MessagesWriter extends Writer {

	public MessagesWriter(OutputStream outputStream) {
		super (outputStream);
	}

	public void connect() {
		writeInt(Protocol.RQ_CONNECT);
	}
	
	public void ok() {
		writeInt(Protocol.RP_OK);
	}
	
	public void ko() {
		writeInt(Protocol.RP_KO);
	}

	public void photo(BufferedImage img) {
		writeInt(Protocol.RP_PHOTO);
		writeBufferedImage(img);
	}

}
