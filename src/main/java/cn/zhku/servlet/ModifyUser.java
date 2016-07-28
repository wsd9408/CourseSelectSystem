package cn.zhku.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import cn.zhku.dao.DataAccessor;
import cn.zhku.modal.Student;
import cn.zhku.modal.Teacher;
import cn.zhku.modal.User;

public class ModifyUser extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the object.
	 */
	public ModifyUser() {
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
		long  id = new Long(request.getParameter("id")).longValue();
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		int age = Integer.parseInt(request.getParameter("age"));
		String info = request.getParameter("info");
		String type = request.getParameter("type");		
		String password = request.getParameter("password");
		User u = null;
		if(type.equals("老师")){
			u = new Teacher(code, name, sex, age, password, info);
			u.setId(id);
		}else{
			u = new Student(code,  name,  sex,  age,  password, info);
			u.setId(id);
		}
		// 插入一条记录
		DataAccessor.update(u);
		// 返回数据给ajax回调函数
		String msg = "修改信息成功！";
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
