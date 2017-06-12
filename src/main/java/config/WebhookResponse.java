package config;

import java.util.List;

public class WebhookResponse {
    private String speech;
    private String displayText;
    private List<ContextData> contextOut;

    private final String source = "java-webhook";

	public WebhookResponse(String speech, String displayText, List<ContextData> contextOut) {
		super();
		this.speech = speech;
		this.displayText = displayText;
		this.contextOut = contextOut;
	}

    public String getSpeech() {
        return speech;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getSource() {
        return source;
    }

	public List<ContextData> getContextOut() {
		return contextOut;
	}

	public void setContextOut(List<ContextData> contextOut) {
		this.contextOut = contextOut;
	}
	
}
