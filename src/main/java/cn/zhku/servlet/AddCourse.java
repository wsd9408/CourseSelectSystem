package cn.zhku.servlet;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import cn.zhku.dao.DataAccessor;
import cn.zhku.modal.Course;
import cn.zhku.modal.Teacher;

public class AddCourse extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public AddCourse() {
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
		String classId = request.getParameter("classId");
		String className = request.getParameter("className");
		String type = request.getParameter("type");
		double score = Double.parseDouble(request.getParameter("score"));
		int teacherid = Integer.parseInt(request.getParameter("teacher"));
		int MaxStuNum = Integer.parseInt(request.getParameter("MaxStuNum"));	
		Teacher teacher = new Teacher();
		teacher.setId(teacherid);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Course course = new Course(classId, className, type, score, teacher, MaxStuNum, year);
		DataAccessor.saveNew(course);
		String msg = "创建成功";
		JSONObject obj = new JSONObject();
		obj.put("msg", msg);
		String ms = obj.toString();
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().print(ms);
	}
	public void init() throws ServletException {
		
	}

}
