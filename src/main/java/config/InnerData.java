package config;

public class InnerData 
{
	private Facebook<?> facebook;
	
	

	public InnerData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InnerData(Facebook<?> facebook) {
		super();
		this.facebook = facebook;
	}

	public Facebook<?> getFacebook() {
		return facebook;
	}

	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}
	
	

}
