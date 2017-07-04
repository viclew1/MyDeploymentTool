package server;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import common.Protocol;
import common.Reader;

public class CommandReader extends Reader{

	private String name,dest,os;
	private int x1,y1,x2,y2;
	private boolean startResearch;
	private List<String> files,dests;
	private BufferedImage img;

	public CommandReader(InputStream inputStream) {
		super(inputStream);
	}

	public void receive() throws IOException
	{
		type=readInt();
		switch(type)
		{
		case Protocol.RQ_CONNECT_ADMIN:
			name=readString();
			break;
		case Protocol.RQ_CONNECT:
			name=readString();
			break;
		case Protocol.RQ_DISCONNECT:
			break;
		case Protocol.RQ_DISCONNECT_ADMIN:
			name=readString();
			break;
		case Protocol.RQ_CLIENTS:
			name=readString();
			break;
		case Protocol.RQ_APPS:
			name=readString();
			os=readString();
			break;
		case Protocol.RQ_INSTALL:
			name=readString();
			os=readString();
			files=new ArrayList<String>();
			dests=new ArrayList<String>();
			int nbFiles=readInt();
			for (int i=0;i<nbFiles;i++)
				files.add(readString());
			int nbDests=readInt();
			for (int i=0;i<nbDests;i++)
				dests.add(readString());
			break;
		case Protocol.RQ_CONTROL:
			name=readString();
			dest=readString();
			break;
		case Protocol.RP_CONTROL:
			name=readString();
			img=readBufferedImage();
			break;
		case Protocol.RQ_STOP_CONTROL:
			name=readString();
			dest=readString();
			break;
		default:
			break;
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public int x1()
	{
		return x1;
	}
	
	public int y1()
	{
		return y1;
	}
	
	public int x2()
	{
		return x2;
	}
	
	public int y2()
	{
		return y2;
	}

	public boolean getStartResearch() {
		return startResearch;
	}

	public String getOS() {
		return os;
	}
	
	public List<String> getFiles(){
		return files;
	}
	
	public List<String> getDests(){
		return dests;
	}
	
	public String getDest(){
		return dest;
	}

	public BufferedImage getImg() {
		return img;
	}

}
