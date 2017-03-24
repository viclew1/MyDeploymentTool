package admin.admindatas;


public class Client {
	
	private String name,address;
    private boolean selected=false;
	
	public Client(String name, String address)
	{
		this.name=name;
		this.address=address;
	}
	
	public String getName()
	{
		return name;
	}

	public String getAddress() {
		return this.address;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
