package server;

public class Pusher extends Thread {

	private NetworkListener listener = null;

	public Pusher(NetworkListener listener) {
		super();
		this.listener = listener;
	}

	@Override
	public void run () {
		try {
			while (true) {
				listener.checkConnections();
				listener.processOrders();
				sleep(2000);
				/*listener.processClients();
				sleep(2000);*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
