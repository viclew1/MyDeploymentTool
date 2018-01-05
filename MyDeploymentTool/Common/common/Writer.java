package common;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Writer {

	protected OutputStream outputStream;
	private ByteArrayOutputStream baos=new ByteArrayOutputStream();
	private DataOutputStream dos=new DataOutputStream(baos);

	public Writer(OutputStream outputStream) 
	{
		this.outputStream=outputStream;
	}

	protected void writeBufferedImage(BufferedImage img)
	{
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( img, "jpg", baos );
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			writeByteArray(imageInByte, imageInByte.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void writeByte(int b)
	{
		try {
			dos.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void writeInt(int i)
	{
		try {
			dos.writeInt(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void writeLong(long l) {
		try {
			dos.writeLong(l);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void writeString(String s)
	{
		try {
			dos.writeUTF(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void writeBoolean(boolean b)
	{
		try {
			if (b)
				dos.writeInt(1);
			else
				dos.writeInt(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void writeByteArray(byte[] data, int length)
	{
		try {
			dos.writeInt(length);
			if (data.length!=length)
			{
				data=Arrays.copyOf(data, length);
			}
			dos.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean send()
	{
		byte[] message=baos.toByteArray();
		try{
			outputStream.write(message);
			outputStream.flush();
			baos=new ByteArrayOutputStream();
			dos=new DataOutputStream(baos);
			return true;
		}
		catch(SocketException e)
		{
			System.out.println("Send failed : Socket closed");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		baos=new ByteArrayOutputStream();
		dos=new DataOutputStream(baos);
		return false;
	}
}
