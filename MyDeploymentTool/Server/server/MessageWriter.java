package server;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import common.Protocol;
import common.Writer;

public class MessageWriter extends Writer{

	public MessageWriter(OutputStream outputStream) {
		super (outputStream);
	}

	public void doDispatchFile(File file, String fileName) throws IOException {
		writeInt(Protocol.RP_FILE);
		writeString(fileName);
		writeLong(file.length());
		
		int nbPacket=0;
		long totalLength=file.length();
		long incr=0;
		while (incr<totalLength)
		{
			nbPacket++;
			incr+=Protocol.PACKET_SIZE;
		}

		writeInt(nbPacket);
		

		FileInputStream fis = new FileInputStream(file);
		byte[] data=new byte[Protocol.PACKET_SIZE];
		int lastByte=0;
		for (int i=0;i<nbPacket;i++)
		{
			lastByte=fis.read(data);
			writeByteArray(data, lastByte);
			send();
		}
		fis.close();
	}

	public void doDispatchDir(File dir, String dirName) throws IOException {
		writeInt(Protocol.RP_DIR);
		writeString(dirName);
		int nbElem=nbElem(dir);
		writeInt(nbElem);
		sendDir(dir,dirName);
	}

	public void sendDir(File dir, String prefix) throws IOException {
		prefix+="/";
		for (File file : dir.listFiles())
			if (file.isFile())
				doDispatchFile(file,prefix+file.getName());
			else
				doDispatchDir(file, prefix+file.getName());
	}


	public int nbElem(File f)
	{
		int cpt=0;
		for (int i=0;i<f.listFiles().length;i++)
			cpt++;
		return cpt;
	}

	public void doDispatchInfo(String info) {
		writeInt(Protocol.RP_INFO);
		writeString(info);
	}

	public void doRequestControl(String name) {
		writeInt(Protocol.RQ_CONTROL);
		writeString(name);
	}

	public void doUpdateControl(String addr, BufferedImage img) throws IOException {
		writeInt(Protocol.RP_CONTROL);
		writeString(addr);
		writeBufferedImage(img);
	}

	public void doStopControl() {
		writeInt(Protocol.RQ_STOP_CONTROL);
	}




}