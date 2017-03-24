package server.serverdatas;

import java.io.File;
import static common.DiversesOperations.*;


public class App {

	private String name;
	private long size;
	
	public App(String name, int size)
	{
		this.name=name;
		this.size=size;
	}
	
	public App(File f) {
		this.name=f.getName();
		this.size=tailleFichier(f);
	}

	public void setName(String name)
	{
		this.name=name;
	}
	
	public String getName()
	{
		return name;
	}

	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size=size;
	}
	
	
}
