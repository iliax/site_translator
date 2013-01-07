package ru.iliax.sitetr;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class DataTranslatorServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger("StaticDataServlet");
	
	// TODO cache it!
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		try {
			String urlStr = StaticData.getUrl();
			urlStr = cutUrl(urlStr, req);

			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(20000);
			
			resp.setContentType(conn.getContentType());
			
			BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				resp.getOutputStream().write(data, 0, count);
			}
			
		} catch (Exception e) {
			log.log(Level.WARNING, "ololo", e);
			resp.getWriter().print("Something wrong :(");
		}
	}

	 @Override
	 protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	 throws ServletException, IOException {
		 doGet(req, resp);
	 }

	String cutUrl(String url, HttpServletRequest req) {
		String reqPath = req.getServletPath();
		
		if (reqPath.equals("/")) {
			// if this is index page req
			return url;
		}

		if (url.endsWith("/")) {
			// reqPath starts with '/'
			url = url.substring(0, url.length() - 1);
		}
		return url + reqPath;
	}

}
