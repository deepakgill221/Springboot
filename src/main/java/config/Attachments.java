package config;
import java.util.List;
import config.Payload;
public class Attachments {

	  String type;
	  private Payload  payload;
	 // private List<Payload> payload;
	  public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/*public List<Payload> getPayload() {
		return payload;
	}
	public void setPayload(List<Payload> payload) {
		this.payload = payload;
	}*/
	public Attachments(Payload payload) {
		super();
		this.payload = payload;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	public Attachments(String type, Payload payload) {
			super();
			this.type = type;
			//this.payload = payload;
			
		}
	  public Attachments() {
			super();
			// TODO Auto-generated constructor stub
		}
}

