package config;

import config.Payload;
public class Attachments {

	  String type;
	  private List<Payload> payload;
	  public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Payload> getPayload() {
		return payload;
	}
	public void setPayload(List<Payload> payload) {
		this.payload = payload;
	}
	public Attachments(String color, String title, String title_Link) {
			super();
			this.type = type;
			this.payload = payload;
			
		}
	  public Attachments() {
			super();
			// TODO Auto-generated constructor stub
		}
}

