package beRespond.listeners;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import beRespond.Constants;

/**
 * Application Lifecycle Listener implementation class DataBaseCreating
 *
 */
public class DataBaseCreating implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public DataBaseCreating() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    private boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        if(e.getSQLState().equals("X0Y32")) {
            exists = true;
        } else {
            exists = false;
        }
        return exists;
    }
	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
    	ServletContext cntx = event.getServletContext();
    	
    	try{
    		
    		//obtain CustomerDB data source from Tomcat's context
    		Context context = new InitialContext();
    		BasicDataSource ds = (BasicDataSource)context.lookup(Constants.DB_DATASOURCE);
    		Connection conn = ds.getConnection();
    		
    		boolean created = false;
    		try{
    			//create Customers table
    			Statement stmt = conn.createStatement();
    			stmt.executeUpdate(Constants.CREATE_TOPICS_TABLE);
    			//commit update
        		conn.commit();
        		stmt.close();
    		}catch (SQLException e){
    			//check if exception thrown since table was already created (so we created the database already 
    			//in the past
    			created = tableAlreadyExists(e);
    			if (!created){
    				throw e;//re-throw the exception so it will be caught in the
    				//external try..catch and recorded as error in the log
    			}
    		}
    		
    		//if no database exist in the past - further populate its records in the table
    		if (!created){
    			System.out.println("Creation");
    		}	/*
    			//populate customers table with customer data from json file
    			Collection<Customer> customers = loadCustomers(cntx.getResourceAsStream(File.separator +
    														   AppConstants.CUSTOMERS_FILE));
    			PreparedStatement pstmt = conn.prepareStatement(AppConstants.INSERT_CUSTOMER_STMT);
    			for (Customer customer : customers){
    				pstmt.setString(1,customer.getName());
    				pstmt.setString(2,customer.getCity());
    				pstmt.setString(3,customer.getCountry());
    				pstmt.executeUpdate();
    			} */

    			//commit update
    			conn.commit();
    			//close statements
    			//pstmt.close();
    		

    		//close connection
    		conn.close();
    		}

    		catch (SQLException e) {
    		//log error 
    		cntx.log("Error during database initialization " + e.getErrorCode(),e);
    	}
    	catch (NamingException e) {
    		//log error 
    		cntx.log("Error during database initialization naming exception",e);
    	}
    }


	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
