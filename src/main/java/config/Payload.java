package config;

import java.util.List;

public class Payload {

	private String template_type;
	public String getTemplate_type() {
		return template_type;
	}

	public void setTemplate_type(String template_type) {
		this.template_type = template_type;
	}

	public List<InnerButton> getButtons() {
		return buttons;
	}

	public void setButtons(List<InnerButton> buttons) {
		this.buttons = buttons;
	}

	private List<InnerButton> buttons;
	
	public Payload() {
		super();
		// TODO Auto-generated constructor stub
	}
}
