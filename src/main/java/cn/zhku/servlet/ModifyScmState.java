package cn.zhku.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cn.zhku.dao.DataAccessor;
import cn.zhku.modal.SelectClassUser;

public class ModifyScmState extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public ModifyScmState() {
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

		// 取出登录页面传来的用户和密码
		boolean state = Boolean.parseBoolean(request.getParameter("state"));
		int year = Integer.parseInt(request.getParameter("year"));
		String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		// 到数据库查询用户名等于code的唯一用户
		String msg = null; 
		try {
			if(state){
				SelectClassUser scm = (SelectClassUser) DataAccessor.findById(SelectClassUser.class, Integer.valueOf(year));
				scm.setCloseTime("");
				scm.setStartTime(time);
				scm.setState(state);
				DataAccessor.update(scm);
				msg = "开放选课成功！";
			} else {
				SelectClassUser scm = (SelectClassUser) DataAccessor.findById(SelectClassUser.class, Integer.valueOf(year));
				scm.setCloseTime(time);
				scm.setState(state);
				DataAccessor.update(scm);
				msg = "关闭选课成功！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (state) {
				msg = "开放选课失败！";
			} else {
				msg = "关闭选课失败！";
			}
		}  
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
