package myapp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.*;

import db.DBConnection;
import mls.property.Buy;
import mls.property.PropertyFactory;
import mls.property.Rent;

public class ViewAreaServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(MLSServlet.class.getName());
	
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = Integer.valueOf(req.getParameter("areaId"));
        DBConnection conn = null;
        try{
        	conn = new DBConnection();
        	ArrayList<Rent> rents = conn.getAreaRents(id);
            ArrayList<Buy> buys = conn.getAreaBuys(id);
            for(int i = 0; i < rents.size(); i++){
            	if(rents.get(i) != null && rents.get(i).getPrice() == 0)
            		rents.remove(i);
            }
            for(int i = 0; i < buys.size(); i++){
            	if(buys.get(i) != null && buys.get(i).getPrice() == 0)
            		buys.remove(i);
            }
	        //Rent r = rents.get(0);
	        //resp.setContentType("text/plain");
	    	//resp.getWriter().println(r.toString());
	        req.setAttribute("rents", rents);
	        req.setAttribute("buys", buys);
	        req.getRequestDispatcher("/view_area.jsp").forward(req, resp);
	        conn.close();
        }
        catch(Exception e){
        	if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    
}