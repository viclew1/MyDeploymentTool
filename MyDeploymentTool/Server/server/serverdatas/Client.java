package server.serverdatas;

import server.CommandSession;
import server.MessageSession;

public class Client {
	
	private String name,address;
	private CommandSession commandSession;
	private MessageSession messageSession;
	
	public Client(String name, String address)
	{
		this.name=name;
		this.address=address;
	}
	
	public String getName()
	{
		return name;
	}
	
	public CommandSession getCommandSession()
	{
		return commandSession;
	}
	
	public void setCommandSession(CommandSession cs)
	{
		if (commandSession!=null) commandSession.close();
		this.commandSession=cs;
	}

	public MessageSession getMessageSession() {
		return messageSession;
	}

	public void setMessageSession(MessageSession message) {
		if (messageSession!=null) messageSession.close();
		this.messageSession = message;
	}
	
	public boolean isConnected() {
		return commandSession != null;
	}

	public String getAddress() {
		return this.address;
	}

}
