package cn.zhku.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.zhku.dao.DataAccessor;
import cn.zhku.modal.Teacher;
import cn.zhku.modal.User;

/**
 * Servlet implementation class Login
 */

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	static boolean writeonce = false; 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(!writeonce){// 第一次运行的时候写入，如果多次重启服务器.需注释本代码或者删除数据库user表记录。
			writeonce = true;
		}

		// 取出登录页面传来的用户和密码
		String code = request.getParameter("username");
		String password = request.getParameter("password");
		// 到数据库查询用户名等于code的唯一用户
		TypedQuery<User> q = DataAccessor.getManager().createQuery(
				"select x from User x where x.code=:code ", User.class);
		String nextPage = "index.html";
		q.setParameter("code", code);
		try {
			User user = q.getSingleResult();
			// 对用户的登录情况进行判断，登录不成功，跳转回到登录页面
			if (password == null || !user.getPassword().equals(password)) {//
				PrintWriter out = response.getWriter();//通过servlet的doget方法获取response对象，通过getWriter方法获取PrintWriter对象
				out.flush();//清空缓存
				out.println("<script>");//输出script标签
				out.println("alert('login failed');");//js语句：输出alert语句
				out.println("history.back();");//js语句：输出网页回退语句
				out.println("</script>");//输出script结尾标签
				out.close();	
			}
			if(user instanceof Teacher){
				nextPage = "teacher_main.html?id="+code;
			}else {
				nextPage = "student_main.html?id="+code;
			}
		} catch (Exception e) {
			PrintWriter out = response.getWriter();//通过servlet的doget方法获取response对象，通过getWriter方法获取PrintWriter对象
			out.flush();//清空缓存
			out.println("<script>");//输出script标签
			out.println("alert('login failed');");//js语句：输出alert语句
			out.println("history.back();");//js语句：输出网页回退语句
			out.println("</script>");//输出script结尾标签
			out.close();
		}
		finally{
			response.sendRedirect(nextPage);
		}
	}
}
