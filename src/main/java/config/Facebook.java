package config;


import java.util.List;

public class Facebook<Paylaod> 
{
	private String type;
	private String platform;
	private String title;
	private String imageUrl;
	//private String payLoad;
	//private List<InnerButton> buttons;
	private List<Payload> payload;
	private List<Payload> paylaod;
	
	public Facebook() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Facebook(String type, String platform, String title, String imageUrl,  List<Paylaod> paylaod) {
		super();
		this.type = type;
		this.platform = platform;
		this.title = title;
		this.imageUrl = imageUrl;
		this.paylaod = payload;
		//this.buttons = buttons;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public List<Payload> getPayload() {
		return payload;
	}


	public void setPayload(List<Payload> payload) {
		this.payload = payload;
	}
	
	/*public List<InnerButton> getButtons() {
		return buttons;
	}
	public void setButtons(List<InnerButton> buttons) {
		this.buttons = buttons;
	}*/

}
