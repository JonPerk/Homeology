package myapp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import mls.ImportMLS;
import mls.area.Area;
import mls.area.AreaFactory;
import mls.property.Buy;
import mls.property.PropertyFactory;
import mls.property.Rent;

import org.json.JSONObject;

import db.DBConnection;
import excel.ConvertExcel;

public class MLSServlet  extends HttpServlet {
	private static final Logger log = Logger.getLogger(MLSServlet.class.getName());
	
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    	HashMap<Integer, Area> areaHash;
    	DBConnection conn = null;
		try {
			conn = new DBConnection();
			areaHash = conn.getAllAreas();
	    	ArrayList<Area> areas = new ArrayList<Area>(areaHash.values());
	    	//Area a = areas.get(0);
	    	//resp.setContentType("text/plain");
	    	//resp.getWriter().println(a.toString() + " " + a.getZips().isEmpty());
	    	req.setAttribute("areas", areas);
			req.getRequestDispatcher("admin_home_mls.jsp").forward(req, resp);
			conn.close();
		} catch (ServletException e) {
			if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (ClassNotFoundException e) {
			if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (SQLException e) {
			if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
    }

}