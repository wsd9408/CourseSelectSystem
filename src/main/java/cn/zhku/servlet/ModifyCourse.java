package cn.zhku.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import cn.zhku.dao.DataAccessor;
import cn.zhku.modal.Course;
import cn.zhku.modal.Teacher;

public class ModifyCourse extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public ModifyCourse() {
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
		Long id = Long.valueOf(request.getParameter("id"));
		String courseId = request.getParameter("classId");
		String courseName = request.getParameter("className");
		String courseType = request.getParameter("type");
		double score = Double.parseDouble(request.getParameter("score"));
		int teacherid = Integer.parseInt(request.getParameter("teacher"));
		int MaxStuNum = Integer.parseInt( request.getParameter("MaxStuNum"));	
		Teacher teacher = new Teacher();
		teacher.setId(teacherid);
		Course course = (Course) DataAccessor.findById(Course.class, id);
		course.setCourseId(courseId);
		course.setCourseName(courseName);
		course.setCourseType(courseType);
		course.setScore(score);
		course.setTeacher(teacher);
		course.setMaxStuNum(MaxStuNum);
		// 插入一条记录
		DataAccessor.update(course);
		// 返回数据给ajax回调函数
		String msg = "修改成功！";
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
