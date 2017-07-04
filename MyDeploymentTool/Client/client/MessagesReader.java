package client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import common.Protocol;
import common.Reader;

public class MessagesReader extends Reader {

	public MessagesReader(InputStream inputStream, NetworkListener listener) {
		super(inputStream);
		this.listener=listener;
	}

	private NetworkListener listener;
	private boolean done;
	private String admin;

	public void receive() throws IOException {
		type = readInt ();
		done=false;
		switch (type)
		{
		case Protocol.RP_FILE:
			readFile(Protocol.DEST_DIR);
			done=true;
			break;

		case Protocol.RP_DIR:
			readDir(Protocol.DEST_DIR);
			done=true;
			break;

		case Protocol.RQ_CONTROL:
			admin=readString();
			break;

		default:
			break;
		}

	}

	public void readFile(String destFolder) throws IOException
	{
		String fileName=readString();
		File f=new File(destFolder+fileName);
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);

		double totalSize=readLong();
		int nbPacket=readInt();
		double currentSize=0;
		
		double d;
		if (totalSize!=0)
		{
			d=100/totalSize;
		}
		else
		{
			d=0;
		}

		listener.updateDownload(0,fileName,false);
	
		byte[] data;
		for (int i=0;i<nbPacket;i++)
		{
			data=readByteArray();
			fos.write(data);
			currentSize+=data.length;
			listener.updateDownload((int)(currentSize*d), fileName, false);
		}
		
		listener.updateDownload(0, fileName, true);
		fos.close();
	}

	public void readDir(String destFolder) throws IOException
	{
		File folder=new File(destFolder+readString());
		folder.mkdir();
		int nbElem=readInt();
		for (int i=0;i<nbElem;i++)
		{
			int fileType=readInt();
			switch (fileType)
			{
			case Protocol.RP_FILE:
				readFile(destFolder);
				break;
			case Protocol.RP_DIR:
				readDir(destFolder);
				break;
			default:
				break;
			}
		}
	}


	public boolean getDone() {
		return done;
	}

	public String getAdmin() {
		return admin;
	}


}
