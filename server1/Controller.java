// importing java into the class
//CONTROLLER CLASS
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/controler-00")
public class Controller extends HttpServlet {
	DAO aa; // controller class, which extends from the DAO class
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("started"); // print message
		aa = new DAO();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_OK); // void method being implemented doGet method
		
		String reqString = req.getParameter("tagID");
		Sensordata data = null; // require string method, sensor data if it equals null
		
		if (reqString != null) {
			boolean valid = aa.isUserValid(reqString); // boolean valid check
			
			if (valid == true) {
				data = new Sensordata(reqString, "Open", "nawazm");
				// send command to open the door
				PrintWriter writer = new PrintWriter(resp.getOutputStream());
				writer.write("true"); // you command string there
				writer.close(); // if it is right the door will open
			} else {
				System.out.println("invalid"); // print message invalid
				data = new Sensordata(reqString, "closed", "nawazm"); // new data is shown if it is invalid, so in this case the door is closed
			}
			if (data != null)
				aa.updateSensorTable(data);
		} else {
			System.out.println("nothing to check"); // print message, once it has been updated
		}
	}
}// close class




