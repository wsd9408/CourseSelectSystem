package cn.zhku.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cn.zhku.dao.DataAccessor;
import cn.zhku.modal.Course;
import cn.zhku.modal.SelectClass;

public class DelSelectCourse extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public DelSelectCourse() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}


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
		String msg = "";
		Long id = Long.parseLong(request.getParameter("id"));
		String code = request.getParameter("code");
	
		Course cmsg = (Course) DataAccessor.findById(Course.class, id);
		int selectedNum = cmsg.getSelectedStuNum();
		int selectNum = cmsg.getSelectStuNum();
		double score = cmsg.getScore();
		int year = cmsg.getYear();
		SelectClass sc = (SelectClass) DataAccessor.findByUserCode(code, year);
		double selectScore = sc.getSelectScore();
		sc.getCmList().remove(cmsg);
		cmsg.setSelectedStuNum(selectedNum-1);
		cmsg.setSelectStuNum(selectNum+1);
		sc.setSelectScore(selectScore-score);
		try {
			DataAccessor.update(sc);
			DataAccessor.update(cmsg);
			msg = "退选成功！";
		} catch (Exception e) {
			msg = "退选失败！";
			e.printStackTrace();
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
