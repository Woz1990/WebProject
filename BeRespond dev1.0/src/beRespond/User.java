package beRespond;

public class User {
	
	private String userName;
	
	private String password;
	
	private String nickName;
	
	private String shortDescription;
	
	private String photo;
	
	private Float rating;
	
	public User(String userName, String password, String nickName,
			String shortDescription, String photo){
		this.userName = userName;
		this.password = password;
		this.nickName = nickName;
		this.shortDescription = shortDescription;
		this.photo = photo;
		this.rating = 0f;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getNickName(){
		return nickName;
	}
	
	public String getShortDescription(){
		return shortDescription;
	}
	
	public String getPhoto(){
		return photo;
	}
	
	public Float getRating(){
		return rating;
	}

}
