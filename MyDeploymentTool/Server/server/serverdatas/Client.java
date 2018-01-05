package server.serverdatas;

import java.util.ArrayList;
import java.util.List;

import server.CommandSession;
import server.MessageSession;
import static server.serverdatas.Constantes.*;

public class Client {

	private final String name,address;
	private boolean processing = false;
	private CommandSession commandSession;
	private MessageSession messageSession;
	private final List<InstallOrder> todoList;
	private final List<InstallOrder> doneList;


	public Client(String name, String address)
	{
		this.name=name;
		this.address=address;
		this.todoList = new ArrayList<InstallOrder>();
		this.doneList = new ArrayList<InstallOrder>();
	}

	public String getName()
	{
		return name;
	}

	public void processTodoOrder()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				if (processing || !isBusy())
					return;
				processing = true;
				while (!todoList.isEmpty() && isConnected() && isMessageConnected())
				{
					InstallOrder todo = todoList.get(0);
					boolean success = processOrder(todo);
					todoList.remove(0);
					doneList.add(todo);
					if (success)
						todo.succeed();
					else
						break;
				}
				processing = false;
			}
		}).start();
	}

	public void addOrder(InstallOrder order)
	{
		todoList.add(order);
	}

	public boolean processOrder(InstallOrder order)
	{
		return messageSession.dispatchFile(SERVER+order.getOs()+"/", order.getFileName());
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
	
	public boolean isMessageConnected() {
		return messageSession != null;
	}

	public String getAddress() {
		return this.address;
	}

	public boolean isBusy()
	{
		return !todoList.isEmpty();
	}
}
