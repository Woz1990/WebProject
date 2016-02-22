package beRespond;

public class Vote {
	private Boolean positive; //TODO: check if it is possible to return true with boolean
	private String voteType;
	private int typeID;
	private int voterID;
	
	public Vote(Boolean positive, String voteType, int typeID, int voterID){
		this.positive = positive;
		this.voteType = voteType;
		this.typeID = typeID;
		this.voterID = voterID;
	}
	
	public Boolean ifPositive(){
		return positive;
	}
	
	public String getVoteType(){
		return voteType;
	}
	
	public int getTypeID(){
		return typeID;
	}
	
	public int getVoterID(){
		return voterID;
	}
	
}
