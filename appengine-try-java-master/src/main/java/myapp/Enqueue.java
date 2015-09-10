package myapp;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class Enqueue extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
	    List<BlobKey> blobKeys = blobs.get("file");
	    BlobKey b = blobKeys.get(0);
	    if (blobKeys == null || blobKeys.isEmpty()) {
	        try {
				resp.sendRedirect("/");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    } else {
	    	Queue queue = QueueFactory.getDefaultQueue();
	    	queue.add(TaskOptions.Builder.withUrl("/worker").param("file", b.getKeyString()));
	    	try {
				resp.sendRedirect("/");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
}
