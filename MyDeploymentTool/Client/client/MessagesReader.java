package client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import common.Protocol;
import common.Reader;

public class MessagesReader extends Reader {

	public MessagesReader(InputStream inputStream) {
		super(inputStream);
	}


	private boolean done;
	private String admin;

	public void receive() throws IOException {
		type = readInt ();
		done=false;
		switch (type)
		{
		case Protocol.RP_FILE:
			readFile("C:/Users/vicle/Desktop/destTest/");
			done=true;
			break;

		case Protocol.RP_DIR:
			readDir("C:/Users/vicle/Desktop/destTest/");
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
		FileOutputStream fos = null;
		fos = new FileOutputStream(f);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		long nbBytes=readLong();

		for (int i=0;i<nbBytes;i++)
			bos.write(readByte());

		bos.close();
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
