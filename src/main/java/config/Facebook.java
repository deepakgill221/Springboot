package config;

import config.Attachments;
import java.util.List;

public class Facebook<Paylaod> 
{
	//private String type;
	//private String platform;
	//private String title;
	//private String imageUrl;
	//private String payLoad;
	private Attachments attachments;
	//private List<InnerButton> buttons;
	/*private List<Attachments> attachments;
	public List<Attachments> getAttachments() {
		return attachments;
	}


	public void setAttachments(List<Attachments> attachments) {
		this.attachments = attachments;
	}*/


	public Facebook(Attachments attachments) {
		super();
		this.attachments = attachments;
	}

	public Attachments getAttachments() {
		return attachments;
	}

	public void setAttachments(Attachments attachments) {
		this.attachments = attachments;
	}

	/*public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}*/


	//private String text="";
	//private List<Payload> payload;
	//private List<Payload> paylaod;
	
	public Facebook() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	//public Facebook(String type, String platform, String title, String imageUrl,  List<Paylaod> paylaod) {
		public Facebook( String attachments) {
		super();
		//this.attachments = attachments;
		//this.text = text;
		//this.title = title;
		//this.imageUrl = imageUrl;
		//this.paylaod = payload;
		//this.buttons = buttons;
	}

	/*public String getType() {
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
	*/
	/*public List<InnerButton> getButtons() {
		return buttons;
	}

	public void setButtons(List<InnerButton> buttons) {
		this.buttons = buttons;
	}*/

}



