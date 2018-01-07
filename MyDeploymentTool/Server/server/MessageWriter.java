package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import common.Protocol;
import common.Writer;
import server.serverdatas.Client;

public class MessageWriter extends Writer{

	public MessageWriter(OutputStream outputStream) {
		super (outputStream);
	}

	public void ok()
	{
		writeInt(Protocol.RP_OK);
	}
	
	public void clients(List<Client> clients) {
		writeInt(Protocol.RP_CLIENTS);
		writeInt(clients.size());
		for (int i=0;i<clients.size();i++)
		{
			writeString(clients.get(i).getName());
			writeString(clients.get(i).getAddress());
			writeBoolean(clients.get(i).isBusy());
			writeBoolean(clients.get(i).isControlled());
			writeBoolean(clients.get(i).isConnected());
		}
	}

	public boolean doDispatchFile(File file, String fileName) throws IOException {
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
			boolean ok = send();
			if (!ok)
			{
				fis.close();
				return false;
			}
		}
		fis.close();
		return true;
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
			{
				boolean ok = doDispatchFile(file,prefix+file.getName());
				if (!ok)
					return;
			}
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

	public void doRequestControl(String adminIp) {
		writeInt(Protocol.RQ_CONTROL);
		writeString(adminIp);
	}

	public void doStopControl() {
		writeInt(Protocol.RQ_STOP_CONTROL);
	}




}