package ru.iliax.sitetr;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ru.iliax.sitetr.StaticData.*;

@SuppressWarnings("serial")
public class PropertiesServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(PropertiesServlet.class
			.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/plain");
		
		Map<String, Object> props = getProperties();
		if (req.getParameter("url") == null) {
			resp.getWriter().println(
					props.get("lastdate") + " : "
							+ props.get("url"));
		} else {
			props.put("lastdate", new Date());
		}

		for (Object key : req.getParameterMap().keySet()) {
			props.put(key.toString(), req.getParameter((String) key));
		}

		log.info("new props : " + props.toString());
		saveOrUpdateProps(props);
		resp.getWriter().print("Ok! v.1.29");
	}

}
