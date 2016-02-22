package beRespond;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.reflect.TypeToken;

public interface Constants {

	public final String USERS = "users";
	public final String USERS_FILE = USERS + ".json";
	public final String NAME = "name";
	/*public final Type CUSTOMER_COLLECTION = new TypeToken<Collection<Customer>>() {}.getType();*/
	//derby constants
	public final String DB_NAME = "BeRespondDB";
	public final String DB_DATASOURCE = "java:comp/env/jdbc/BeRespondDatasource";
	public final String PROTOCOL = "jdbc:derby:"; 
	//sql statements
	public final String CREATE_USERS_TABLE = "CREATE TABLE USER(ID INT	not NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
			+"UName VARCHAR(10) not NULL, " 
			+"UPassword VARCHAR(8) not NULL, "
			+"UNickName VARCHAR(20) not NULL, "
			+"Description VARCHAR(400), "
			+"Photo VARCHAR(100), "
			+"SubmittedQuestionsNumber INT DEFAULT 0 CHECK (SubmittedQuestionsNumber = 0), "
			+"SubmittedQuestionsAverage FLOAT DEFAULT 0 CHECK (SubmittedQuestionsAverage), "
			+"SubmittedAnswersNumber INT DEFAULT 0 CHECK (SubmittedAnswersNumber = 0), "
			+"SubmittedAnswersAverage FLOAT DEFAULT 0 CHECK (SubmittedAnswersAverage))"
			+"Rating FLOAT DEFAULT 0 CHECK (Rating = 0))";
	
	public final String CREATE_QUESTIONS_TABLE = "CREATE TABLE QUESTION(ID INT	not NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
			+"QText VARCHAR(300) not NULL, "
			+"SubmissionDate DATE not NULL, "
			+"SubmissionTime TIME not NULL, "
			+"UserId INT not NULL, "
			+"VotingScore INT DEFAULT 0 CHECK (VotingScore = 0), "
			+"AnswersNumber INT DEFAULT 0 CHECK (AnswersNumber = 0), "
			+"AnswersAverage FLOAT DEFAULT 0 CHECK (AnswersNumber = 0))"
			+"Rating FLOAT DEFAULT 0 CHECK (Rating = 0))";
	
	public final String CREATE_ANSWERS_TABLE = "CREATE TABLE ANSWER(ID INT	not NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
			+"AText VARCHAR(300) not NULL, "
			+"QuestionId INT not NULL, "
			+"UserID INT not NULL, "
			+"SubmissionDate DATE not NULL, "
			+"SubmissionTime TIME not NULL, "
			+"Rating FLOAT DEFAULT 0 CHECK (Rating = 0))";
	
	public final String CREATE_TOPICS_TABLE = "CREATE TABLE TOPIC(ID INT	not NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
			+"TName VARCHAR(20) not NULL, "
			+"UserID INT not NULL, "
			+"Rating FLOAT DEFAULT 0 CHECK (Rating = 0))";
	
	public final String INSERT_NEW_USER_STMT = "INSERT INTO USER(UName, UPassword, UNickName, "
													+"Description, Photo) VALUES(?, ?, ?, ?, ?)";
	
	public final String SELECT_USER_BY_NAME = "SELECT Uname, UPassword, UNickName, Description, "
																+"Photo, Rating FROM USER WHERE UName=?";
	
	public final String FIND_QUESTION_BY_ID = "SELECT  UserId, VotingScore, AnswersNumber, AnswersAverage "
													+"FROM QUESTION WHERE ID=?";
	
	public final String GET_NEWLY_SUBMITTED_QUESTIONS ="SELECT UserId, QText, SubmissionDate, SubmissionTime, "
															+ "Rating FROM QUESTION WHERE AnswersNumber=0 "
															+ "ORDER BY SubmissionDate DATE DESC, SubmissionTime DESC "
															+ "FETCH FIRST 20 ROWS ONLY";
	
	public final String QUESTION_TYPE = "question";
	public final String ANSWER_TYPE = "answer";
	
	public final int WRONG_VOTE = -1;
	
	public final Type QUESTION_COLLECTION = new TypeToken<Collection<Question>>() {}.getType();
	
	/*public final String SELECT_ALL_CUSTOMERS_STMT = "SELECT * FROM CUSTOMER";
	public final String SELECT_CUSTOMER_BY_NAME_STMT = "SELECT * FROM CUSTOMER "
			+ "WHERE Name=?";*/
}
