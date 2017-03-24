package client.clientdatas;


public class Model {

    private String name;
    private boolean connected,controlled;

    public String getName() {
        return name == null ? "" : name;
    }

    public boolean isConnected() {
        return connected;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

	public boolean getControlled() {
		return controlled;
	}
	
	public void setControlled(boolean controlled) {
		this.controlled=controlled;
	}

}