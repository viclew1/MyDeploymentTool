package client;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import common.Protocol;
import common.Writer;

public class CommandWriter extends Writer {

	public CommandWriter(OutputStream outputStream) {
		super (outputStream);
	}

	public void connect(String name) {
		writeInt(Protocol.RQ_CONNECT);
		writeString(name);
	}
	
	public void disconnect() {
		writeInt(Protocol.RQ_DISCONNECT);
	}

	public void control(String admin, BufferedImage img) throws IOException {
		writeInt(Protocol.RP_CONTROL);
		writeString(admin);
		writeBufferedImage(img);
	}
	
	public void sendFile(File file) throws IOException {
		FileInputStream fis=new FileInputStream(file);
		BufferedInputStream bis=new BufferedInputStream(fis);
		writeLong(file.length());
		int byteToWrite=0;
		int count=0;
		while ((byteToWrite=bis.read()) !=-1) 
			{
				writeByte(byteToWrite);
				count++;
				if (count>=Protocol.PACKET_SIZE)
				{
					send();
					count=0;
				}
			}
		bis.close();
	}


}
