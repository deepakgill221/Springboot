package config;

public class Elements {

	 private String title;
	 private String img_url;
	 
	 public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public Elements(String title, String img_url) {
			super();
			this.title = title;
			this.img_url = img_url;
			
		}
	  public Elements() {
			super();
			// TODO Auto-generated constructor stub
		}
}
