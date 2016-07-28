package cn.zhku.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import cn.zhku.dao.DataAccessor;
import cn.zhku.modal.Course;

public class LoadCourse extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7171489815177118256L;

	/**
	 * Constructor of the object.
	 */
	public LoadCourse() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(null, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String yearStr = request.getParameter("year");
		List<?> us = null;
		if(yearStr == null || yearStr.equals("")){
			us = DataAccessor.findAll(Course.class);
		} else {
			us = DataAccessor.findAllClassByYear(Course.class,Integer.parseInt(yearStr));
		}
		JSONArray jsonArray = new JSONArray();   
		//us.remove(0);
		jsonArray.put(us);
		response.setCharacterEncoding("UTF-8"); 
		jsonArray.write(response.getWriter());
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
