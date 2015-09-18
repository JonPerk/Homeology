package myapp;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mls.ImportMLS;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import db.DBConnection;
import excel.ConvertExcel;
import exception.SpreadSheetSizeException;

public class Worker extends HttpServlet {
	private static final Logger log = Logger.getLogger(Worker.class.getName());
	
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    	BlobKey bk = new BlobKey(req.getParameter("file"));
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
		long inxStart = 0;
		long inxEnd = 1024;
		boolean flag = false;
		do {
		    try {
		        byte[] b = blobstoreService.fetchData(bk,inxStart,inxEnd);
		        out.write(b);
		        if (b.length < 1024){
		            flag = true;
		        }
		        inxStart = inxEnd + 1;
		        inxEnd += 1025;

		    } catch (Exception e) {
		        flag = true;
		    }
		} while (!flag);
		byte[] bytes = new byte[1024];
		ByteArrayInputStream is = new ByteArrayInputStream(out.toByteArray());
		/*while(is.read(bytes, start, end) != -1){
			out.write(bytes);
		}*/
		ConvertExcel ce = new ConvertExcel();
		ImportMLS im = new ImportMLS();
		DBConnection conn = null;
		try {
			conn = new DBConnection();
			JSONObject json = ce.CSVtoJSON(is, conn);
			im.doImport(json, conn);
			conn.close();
		} catch (IllegalArgumentException e) {
			if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (ClassNotFoundException e) {
			if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (JSONException e) {
			if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (SQLException e) {
			if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (SpreadSheetSizeException e) {
			if(conn != null)
				conn.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			if(conn != null)
				conn.close();
		}
    }
}
