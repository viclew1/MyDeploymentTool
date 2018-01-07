package client.capture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import common.Protocol;
import common.Writer;

public class CaptureWriter extends Writer {

	public CaptureWriter(OutputStream outputStream) {
		super (outputStream);
	}

	public void control(BufferedImage img) throws IOException {
		writeInt(Protocol.RP_CONTROL);
		writeBufferedImage(img);
	}
	
}
