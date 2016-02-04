package myapp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.*;

import com.google.appengine.api.users.User;

import db.DBConnection;
import mls.property.Buy;
import mls.property.PropertyFactory;
import mls.property.Rent;

import user.ImportUsers;
import user.User;

public class ViewCustomerServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(MLSServlet.class.getName());
	
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    	String id = String.valueOf(req.getParameter("email"));
        DBConnection conn = null;
        try{
        	conn = new DBConnection();
        	
        	user.User users = conn.getUser(id);
        	String email = users.getEmail();
        	String fname = users.getFname();
        	String phone = users.getPhone();
            
	        req.setAttribute("users", users);
	        req.setAttribute("fname", fname);
	        req.setAttribute("email", email);
	        req.setAttribute("phone", phone);
	        req.getRequestDispatcher("/view_user.jsp").forward(req, resp);
	        conn.close();
        }
        catch(Exception e){
        	if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }
}