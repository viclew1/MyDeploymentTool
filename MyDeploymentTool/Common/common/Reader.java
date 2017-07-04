package common;


import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Reader {
	
	protected DataInputStream inputStream;
	protected int type;

	public Reader(InputStream inputStream) 
	{
		this.inputStream=new DataInputStream(inputStream);
	}
	
	public int getType()
	{
		return type;
	}
	
	protected boolean readBoolean()
	{
		try{
			int i=inputStream.readInt();
			if (i!=0)
				return true;
			return false;
		}
		catch(IOException e)
		{
			return false;
		}
	}
	
	protected int readInt()
	{
		try {
			return inputStream.readInt();
		} catch (IOException e) {
			return 0;
		}
	}
	
	protected long readLong()
	{
		try {
			return inputStream.readLong();
		} catch (IOException e) {
			return 0;
		}
	}
	
	protected String readString()
	{
		try {
			return inputStream.readUTF();
		} catch (IOException e) {
			return "";
		}
	}
	
	protected int readByte()
	{
		try {
			return inputStream.read();
		} catch (IOException e) {
			return 0;
		}
	}
	
	protected BufferedImage readBufferedImage()
	{
		try {
			return ImageIO.read(inputStream);
		} catch (IOException e) {
			return null;
		}
	}
	
	protected byte[] readByteArray()
	{
		try {
			int sz=inputStream.readInt();
			byte[] data = new byte[sz];
			inputStream.readFully(data);
			return data;
		} catch (IOException e) {
			return null;
		}
	}
}
