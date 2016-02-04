package myapp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

public class CustomerDetailServlet extends HttpServlet {
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
//        	byte[] id = users.getId();
        	Date created = users.getCreated();
        	Date modified = users.getModified();
        	String moveReason = users.getMoveReason();
        	String currentLoc = users.getCurrentLoc();
        	Date moveDate = users.getMoveDate();
        	String housingType = users.getHousingType(); //possible enum
        	int beds = users.getBeds();
        	int baths = users.getBaths();
        	int workZip = users.getWorkZip();
        	int workZip2 = users.getWorkZip2();
        	double commuteTime = users.getCommuteTime();
        	String transMode = users.getTransMode(); //possible enum
        	String desiredLocs = users.getDesiredLocs();
        	String desiredRegion = users.getDesiredRegion();
        	String rentOrBuy = users.getRentOrBuy(); //possible enum
        	String promoCode = users.getPromoCode();
        	double priceRangeBuyMin = users.getPriceRangeBuyMin();
        	double priceRangeBuyMax = users.getPriceRangeBuyMin();
        	double monthlyMortgage = users.getMonthlyMortgage();
        	double downPayment = users.getDownPayment();
        	boolean needsSchool = users.getNeedsSchool();
        	double monthlyRent = users.getMonthlyRent();
        	boolean needsParking = users.getNeedsParking();
        	String schoolLevel = users.getSchoolLevel(); //possible enum
        	String schoolType = users.getSchoolType(); //possible enum
        	String moreQuestions = users.getMoreQuestions();
//        	private String currentLocLikesDislikes;
//        	private String importantActivities;
//        	private String importantAreas; //possible enum
//        	private int walkability;
//        	private int greenspace;
//        	private int restaurants;
//        	private int bars;
//        	private int amenities;
//        	private int safety;
//        	private String additionalInfo;
//        	private String previousSearch; //possible enum
//        	private String aptStyle; //possible enum
//        	private String schoolPerformance; //possible enum
//        	private String phone;
//        	private String summary;
//        	private String assigned;
//        	private String comments;
//        	private String currentStatus;
//        	private Date handOffDate;
//        	private ArrayList<Integer> matchedAreas;
            
        	
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