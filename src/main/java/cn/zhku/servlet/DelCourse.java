package cn.zhku.servlet;

import java.io.IOException;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import cn.zhku.dao.DataAccessor;

public class DelCourse extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public DelCourse() {
		super();
	}


	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id = Long.valueOf(request.getParameter("id"));
		String sql = "delete from Course x where x.id=:id";
		DataAccessor.getManager().getTransaction().begin();// 开启事务
		Query q = DataAccessor.getManager().createQuery(sql);
		q.setParameter("id", id);
		q.executeUpdate();
		DataAccessor.getManager().getTransaction().commit();// 提交事务
		String msg = "删除成功！";
		JSONObject obj = new JSONObject();
		obj.put("msg", msg);
		String ms = obj.toString();
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().print(ms);
	}
	public void init() throws ServletException {
		
	}

}
