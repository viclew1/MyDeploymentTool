package server.serverdatas;

public class InstallOrder
{

	private final Client client;
	private final String os, fileName;
	private boolean succeeded = false;
	
	public InstallOrder(Client client, String os, String fileName)
	{
		this.client = client;
		this.os = os;
		this.fileName = fileName;
	}

	public boolean process()
	{
		return client.processOrder(this);
	}
	
	public void succeed()
	{
		this.succeeded = true;
	}
	
	public boolean isSuccesfullyDone()
	{
		return succeeded;
	}

	public String getFileName()
	{
		return fileName;
	}

	public String getOs()
	{
		return os;
	}
}
