package myapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.User;
import mls.property.Buy;
import mls.property.Rent;
import db.DBConnection;

public class ViewAllCustomersServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(ViewAllCustomersServlet.class.getName());
	
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        DBConnection conn = null;
        try{
        	conn = new DBConnection();
        	ArrayList<User> users = conn.getAllUsers();
        	//User u = users.get(0);
        	//resp.setContentType("text/plain");
	    	//resp.getWriter().println(u.toString());
        	req.setAttribute("users", users);
	        req.getRequestDispatcher("/view_customers.jsp").forward(req, resp);
	        conn.close();
        }
        catch(Exception e){
        	if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }
}