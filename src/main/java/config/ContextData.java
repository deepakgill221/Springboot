package com.qc.api.response;

public class ContextData 
{
	private String name;
	private String lifespan;
	private String PolicyNumberOriginal;
	private InnerContextData parameters;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLifespan() {
		return lifespan;
	}
	public void setLifespan(String lifespan) {
		this.lifespan = lifespan;
	}
	public String getPolicyNumberOriginal() {
		return PolicyNumberOriginal;
	}
	public void setPolicyNumberOriginal(String policyNumberOriginal) {
		PolicyNumberOriginal = policyNumberOriginal;
	}
	public InnerContextData getParameters() {
		return parameters;
	}
	public void setParameters(InnerContextData parameters) {
		this.parameters = parameters;
	}
	
}
