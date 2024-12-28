// Here we are importing java into the class
// DAO CLASS
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// import array list for all the rfid data
import java.util.ArrayList;

import com.google.gson.Gson;

// Creating DAO class, to connect to the database
public class DAO {
	// Static string, connection being set to null 
	private static final String dbConnection = null;

	// getting db connection
	public static Connection getDBConnection() {
		// db connection and setting it to null
		Connection dbConnection = null;

		// Connection to mysql workbench,user, password
		String user = "nawazm";
		String password = "fersdOin7";
		// Ensure port 6306 is being used instead of 3306
		String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/nawazm";

		// trying to get connection, using the try and catch method
		try {
			Class.forName("com.mysql.jdbc.Driver");//string is altered to mysql driver
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage()); // message is being printed
		} // close, if class not found
		// using try to connect to the database
		try {
			String dbURL = "jdbc:sqlite:doorlock.sqlite"; // url which is being used to locate the database
			dbConnection = DriverManager.getConnection(url, user, password); // database connection is returned, this is located from the drive manager
			return dbConnection; // DB connection is brought back
		} catch (SQLException e) { // catch SQL
			System.out.println(e.getMessage()); // message is being printed
			// console
		} // close catch exception e
		return dbConnection; // database connection is being returned
	} // public connection closed

	// public array list is being created
	public static ArrayList<Sensordata> getAllDoorLockStatus() throws SQLException {
		Connection dbConnection = null; // database connection = null
		Statement statement = null; // statement object 
		ResultSet resultset = null; // result set
		String query = "SELECT * FROM doorlock;"; // select query is being implemented to select all from the doorlock table
		Sensordata temp = null; // sensor data temp being set to null

		ArrayList<Sensordata> allRFIDData = new ArrayList<>(); // ArrayList<sensordata> gathering all rfid data

		// try and catch method is once again being executed to gain connection to the database
		try {
			dbConnection = getDBConnection(); // database connection
			statement = dbConnection.createStatement(); // statement for database connection
			System.out.println(query); // query result is shown
			// SQL query is then executed
			resultset = statement.executeQuery(query);
			// while loop
			while (resultset.next()) {
				// string is being created for all the results
				String sensorname = resultset.getString("sensorname");
				String sensorvalue = resultset.getString("sensorvalue");
				String userid = resultset.getString("userid");

				// creating temp for the RFIDSensorData
				temp = new Sensordata(sensorname, sensorvalue, userid);
				// adding temporary RFIDDATA
				allRFIDData.add(temp);
			} // close while loop
		} // try method is being closed
		// creating catch exception e and creating finally
		catch (SQLException e) {
			System.out.println(e.getMessage()); // print message
		} finally {
			// if resultset is not = null
			if (resultset != null) {
				resultset.close();
			} // close if statement for result set
			// if statement is put in here, if it is not equal to null
			if (statement != null) {
				statement.close();
			} // close if statement
			// if dbConnection is put in here, if it is not equal to null 
			if (dbConnection != null) {
				dbConnection.close();
			} // close statement for database connection
		} // close finally statement
		return allRFIDData; // all RFIDData is returned
	} // close Arraylist

	// here a boolean is being created, to see if the user is valid
	public boolean isUserValid(String tagvalidate) {
		Connection dbConnection = null; // db connection
		Statement statement = null; // statement
		ResultSet resultset = null; // resultset

		try {
			dbConnection = getDBConnection(); // here we are inputting a try statement to get a db connection
			statement = dbConnection.createStatement(); // a create statement is being implemented here

			// select statement is implemented in sql which is then highlighted and brings the tag id from the table
			String sql = "SELECT * FROM doorvalidate WHERE tagvalidate = '" + tagvalidate + "';";
			// sql is then executed
			resultset = statement.executeQuery(sql);
			System.out.println(sql); // query then displays the result
			System.out.println(">> Debug: All RFID data displayed");
		
			if (resultset.next()) {
				// if the result is correct it will return as true
				if (resultset.getString("tagvalidate").equals(tagvalidate)) {
					System.out.println(resultset.getString("tagvalidate"));// print line statement
					return true; // return true statement
				}
			}
				
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} // catch statement is closed
		// return false if user can't open the door
		return false;
	} //try method is closed
	// static method is created to update sensor table
	public static void updateSensorTable(Sensordata rfidtag){
		Connection dbConnection = null; // db connection object 
		Statement statement = null; // statement object
		ResultSet resultset = null; // resultset object
		// try method is being implemented to get db connection
		try {
			dbConnection = getDBConnection(); // db connection
			statement = dbConnection.createStatement(); // statement

			// update sql, with name, value, user id, using INSERT statement
			String updateSQL = 
					"insert into doorlock(sensorname,sensorvalue, userid) VALUES ('" +rfidtag.getSensorname() +"','"+ rfidtag.getSensorvalue() + "', '"+ rfidtag.getUserid() + "')";

			// message is being printed
			System.out.println("DEBUG: Update: " + updateSQL);
			statement.executeUpdate(updateSQL);
			System.out.println("DEBUG: Update successful ");
		} catch (SQLException se) {
			// if problem occurs, then a failure message will appear
			System.out.println(se);
			System.out.println("\nDEBUG: Update error - see error trace above for help. ");
			return;
		} // catch statement is closed
		return;
	} // Close static void update sensor table if everything is fine
	
	
	// private method is put in place to get sensor data
	private String retrieveSensorData(String sensorname) {
		Connection dbConnection = null; // db connection object 
		Statement statement = null; // statement object 
		ResultSet resultset = null; // resultset object 
		Gson gson = new Gson(); // gson 

		// sql statement to select everything from the specified table 
		String selectSQL = "select * from doorlock where sensorname='" + 
				sensorname + "' order by timeinserted asc";
		ResultSet rs; // result set

		// ArrayList of RFID tags 
		// gets all the results
		ArrayList<Sensordata> allRFIDTags = new ArrayList<Sensordata>();

		try {	        
			// try method
			rs = statement.executeQuery(selectSQL);
			
			//sensor data RFIDTag
			Sensordata RFIDTag = new Sensordata("unknown", "unknown");
			// while statement
			while (rs.next()) {
				// whatever the result, it is then displayed, with all the key attributes down below listed
				RFIDTag.setSensorname(rs.getString("sensorname"));
				RFIDTag.setSensorvalue(rs.getString("sensorvalue"));
				RFIDTag.setUserid(rs.getString("userid"));
				RFIDTag.setSensordate(rs.getString("Timeinserted"));
				
				
				// Arraylist of sensors
				allRFIDTags.add(RFIDTag); // All RFIDTags added to the db
				System.out.println(RFIDTag.toString());
			} // Close while loop
		} catch (SQLException ex) {
			// Print message
			System.out.println("Error in SQL " + ex.getMessage());
		} // Close catch statement
		// all RFIDTags are converted to json arrays, this is then retrieved by the user
		String allRFIDTagsJson = gson.toJson(allRFIDTags);

		// return method
		return allRFIDTagsJson;
	} // Close string to retrieve sensor data
} // Close DAO class







