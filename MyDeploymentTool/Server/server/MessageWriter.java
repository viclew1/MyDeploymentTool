package server;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import common.Protocol;
import common.Writer;

public class MessageWriter extends Writer{

	public MessageWriter(OutputStream outputStream) {
		super (outputStream);
	}

	public void doDispatchFile(File file, String fileName) throws IOException {
		writeInt(Protocol.RP_FILE);
		writeString(fileName);
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

	public void doRequestPhoto() {
		writeInt(Protocol.RQ_PHOTO);
	}







}