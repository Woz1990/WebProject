package beRespond;

public class Login {

	private String login_name;
	
	private String user_password;
	
	Login(String login_name, String user_password){
		this.login_name = login_name;
		this.user_password = user_password;
	}
	
	public String getLoginName(){
		return login_name;
	}
	
	public String getUserPassword(){
		return user_password;
	}
}
