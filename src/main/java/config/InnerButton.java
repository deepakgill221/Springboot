package config;

public class InnerButton 
{
	String type;
	String url;
    String title; 
	
	public InnerButton(String type, String url, String title) {
		super();
		this.type = type;
		this.url = url;
		this.title = title;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public InnerButton() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	

}
