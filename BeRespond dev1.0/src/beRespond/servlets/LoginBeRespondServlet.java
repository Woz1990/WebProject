package beRespond.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import com.google.gson.Gson;

import beRespond.Constants;
import beRespond.Login;
import beRespond.Question;
import beRespond.User;
import beRespond.Vote;

/**
 * Servlet implementation class LoginBeRespondServlet
 */
public class LoginBeRespondServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	public LoginBeRespondServlet(){
		super();
	}
	
/*	int ChangeQuestionRating(int questionID, int voterID, int voteScore) throws SQLException{
		PreparedStatement stmt;
		ResultSet rs;
		stmt = connection.prepareStatement(Constants.FIND_QUESTION_BY_ID);
		stmt.setInt(1, questionID);
		rs = stmt.executeQuery();
		if(rs.getInt(2) == voterID){
			return Constants.WRONG_VOTE;
		}
		
	}*/
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("In servlet");
		Gson gson = new Gson();
		ResultSet rs;
		
		
		try
		{
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup(Constants.DB_DATASOURCE);
			connection = ds.getConnection();
			
			PrintWriter writer = response.getWriter();
			
			String pathInfo = request.getPathInfo();
 
			String[] splittedPathInfo = pathInfo.split("/");
			int len = splittedPathInfo.length;
			String operation = splittedPathInfo[1];
			
			PreparedStatement stmt;
			Login login;

			BufferedReader br = new BufferedReader(new InputStreamReader(
					request.getInputStream()));
			String jsonData = "";
			if (br != null) {
				jsonData = br.readLine();
			}
			switch (operation.toLowerCase()) {
			case("signup"):
				User user;
				System.out.println("Before from json");
				user = gson.fromJson(jsonData, User.class);
				System.out.println("" + user.getUserName());
				
				stmt = connection.prepareStatement(Constants.INSERT_NEW_USER_STMT);
				stmt.setString(1, user.getUserName());
				stmt.setString(2, user.getPassword());
				stmt.setString(3, user.getNickName());
				stmt.setString(4, user.getShortDescription());
				stmt.setString(5, user.getPhoto());
				stmt.executeUpdate(); 
				
				stmt = connection.prepareStatement(Constants.SELECT_USER_BY_NAME);
				stmt.setString(1, user.getUserName());
				rs = stmt.executeQuery();
				
				if(rs.next()){
					User user2 = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
					writer.println(gson.toJson(user2));
				}
				else {
					User user2 = new User("hhh", "hhh", "hhh", "hhh", "hhh");
					writer.println(gson.toJson(user2));
				}
				writer.close();
				break;
			case("vote"):
				Vote vote;
				vote = gson.fromJson(jsonData, Vote.class);
				int voteScore; //what was a vote, can be either 1 or -1
				if(vote.ifPositive()){
					voteScore = 1;
				}
				else voteScore = -1;
				if(vote.getVoteType() == Constants.QUESTION_TYPE){
					stmt = connection.prepareStatement(Constants.FIND_QUESTION_BY_ID);
					stmt.setInt(1, vote.getTypeID());
					rs = stmt.executeQuery();
					if(rs.getInt(1) == vote.getVoterID()){
						//TODO set error
					}
					//TODO: change user's rating; question's rating; topic's rating; then return to client
					//side user's rating, question's rating, user's topics rating
					/*int resultChangeRating;
					resultChangeRating = ChangeQuestionRating(vote.getTypeID(), vote.getVoterID(), voteScore);*/
				}
			case("newestquestions"):
				stmt = connection.prepareStatement(Constants.GET_NEWLY_SUBMITTED_QUESTIONS);
				rs = stmt.executeQuery();
			
				Collection<Question> questionsResult = new ArrayList<Question>(); 
				while (rs.next()){
					questionsResult.add(new Question(rs.getInt(1),rs.getString(2),rs.getDate(3), rs.getFloat(4)));
				}
				String queryResult = gson.toJson(questionsResult, Constants.QUESTION_COLLECTION);
				writer.println(queryResult);
	        	writer.close();
	        	break;
			}

				//
				// Now we create a Java object from JSON string
				//
			
		}

				//
				// Here we need to put code - verify that password and login are
				// correct
				//
				/*stmt = conn
						.prepareStatement(Constants.SELECT_USER_BY_PAIR_STMT);
				stmt.setString(1, loginPair.getLogin());
				stmt.setString(2, loginPair.getPassword());
				rs = stmt.executeQuery();*/

		
		//String userName = request.getParameter("login_name");
		//String password = request.getParameter("user_password");
		
		//response.setContentType("text/html");
		//response.setCharacterEncoding("UTF-8");
		//response.getWriter().write(userName + password);
		
		//response.sendError(HttpServletResponse.SC_NOT_FOUND);

		    	/*try {
		    		
		        	//obtain CustomerDB data source from Tomcat's context
		    		Context context = new InitialContext();
		    		BasicDataSource ds = (BasicDataSource)context.lookup(Constants.DB_DATASOURCE);
		    		Connection conn = ds.getConnection();
			    	PreparedStatement stmt;	

		    		String name;
		    		String password;
    				stmt = conn.prepareStatement(Constants.SELECT_USER_BY_NAME_STMT);
    				name = request.getParameter("login_name");
    				stmt.setString(1, name);
    				ResultSet rs = stmt.executeQuery();
    				
    				response.setContentType("text/plain");
    				if(!rs.next()) {
    					response.sendError(HttpServletResponse.SC_NOT_FOUND);
    					response.getWriter().write("name no found");
    				}
    				else{
    					password = request.getParameter("user_password");
    					if(rs.getString("Name") != password) {
    						response.sendError(245);
    						response.getWriter().write("wrong password 1");
    					}
    				}
    				rs.close();
    				stmt.close();
    				conn.close();
		    	}
				catch (SQLException | NamingException e) {
					//getServletContext().log("Error while querying for customers", e);
		    		//response.sendError(500);//internal server error
		    	}*/
			
			catch (Exception e) {
				getServletContext().log("Exception occurred in doPost", e);

			}
		
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		doGet(req, res);
	}

}
