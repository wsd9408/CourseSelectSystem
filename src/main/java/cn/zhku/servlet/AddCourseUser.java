package cn.zhku.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cn.zhku.dao.DataAccessor;
import cn.zhku.modal.Course;
import cn.zhku.modal.SelectClassUser;

public class AddCourseUser extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the object.
	 */
	public AddCourseUser() {
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
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		double score = Double.parseDouble(request.getParameter("score"));
		int year =Calendar.getInstance().get(Calendar.YEAR);
		SelectClassUser scu = new SelectClassUser(year,score);
		// 插入一条记录
		DataAccessor.saveNew(scu);
		List<?> us = DataAccessor.findAllClassByYear(SelectClassUser.class, year-1);
		Course temp = null;
		for(Object o : us){
			temp = new Course();
			Course c = (Course) o;
			temp = (Course) c.clone();
			temp.setId((long)0);
			temp.setYear(year); 
			DataAccessor.saveNew(temp); 
		}
		// 返回数据给ajax回调函数
		String msg = "新建成功！";
		JSONObject obj = new JSONObject();
		obj.put("msg", msg);
		String ms = obj.toString();
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().print(ms);
	}
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
