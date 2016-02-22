package beRespond;

import java.util.Date;

public class Question {
	private int questionSubmitterID;
	private String text;
	private Date date;
	private int ID;
	private float rating;
	
	public Question(int ID, String text, Date date, float rating){
		this.ID = ID;
		this.text = text;
		this.date = date;
		this.rating = rating;
	}
	
}
