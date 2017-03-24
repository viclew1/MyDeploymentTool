package admin.admindatas;

public class App {

	private String name;
	private long size;
	private boolean selected=false;
	
	public App(String name, long size)
	{
		this.name=name;
		this.size=size;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public String getName()
	{
		return name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size=size;
	}
	
	
}
