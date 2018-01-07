package admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import admin.admindatas.Client;
import common.Protocol;
import common.Reader;

public class MessagesReader extends Reader {

	public MessagesReader(InputStream inputStream) {
		super(inputStream);
	}

	private String info="";
	private String clientName;
	private List<Client> clients;


	public void receive() throws IOException {
		type = readInt ();
		switch (type)
		{
		case Protocol.RP_OK:
			break;
		case Protocol.RP_CLIENTS:
			int nbCli=readInt();
			clients=new ArrayList<Client>();
			for (int i=0;i<nbCli;i++)
				clients.add(new Client(readString(), readString(), readBoolean(), readBoolean(), readBoolean()));
			break;
		case Protocol.RP_INFO:
			info=readString();
			break;
		default:
			break;
		}
	}
	
	
	public String getInfo()
	{
		return info;
	}
	
	public String getClientName()
	{
		return clientName;
	}

	public List<Client> getUsers() {
		return clients;
	}

}
