package config;

public class Attachments {

	  String color;
	  String title;
	  public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle_Link() {
		return title_Link;
	}

	public void setTitle_Link(String title_Link) {
		this.title_Link = title_Link;
	}

	String title_Link;
	  
	  public Attachments(String color, String title, String title_Link) {
			super();
			this.color = color;
			this.title = title;
			this.title_Link = title_Link;
		}
	  public Attachments() {
			super();
			// TODO Auto-generated constructor stub
		}
}
