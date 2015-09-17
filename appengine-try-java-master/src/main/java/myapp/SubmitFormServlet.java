package myapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import form.ImportUsers;

public class SubmitFormServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(SubmitFormServlet.class.getName());
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("/src/main/java/Admin_home.jsp");
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    	StringBuffer jb = new StringBuffer();
    	String line = null;
    	try {
    		BufferedReader reader = req.getReader();
    		while ((line = reader.readLine()) != null)
    			jb.append(line);
    	} catch (Exception e) { 
    		log.log(Level.SEVERE, e.getLocalizedMessage(), e); 
    	}
    	log.log(Level.INFO, jb.toString());
    	DBConnection conn = null;
		try {
			conn = new DBConnection();
			ImportUsers iu = new ImportUsers();
	    	iu.addNewUser(jb.toString(), conn);
		} catch (ClassNotFoundException e) {
			if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (SQLException e) {
			if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (IllegalArgumentException e) {
			if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (JSONException e) {
			if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
    }
}
