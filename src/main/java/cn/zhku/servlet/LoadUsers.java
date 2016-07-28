package cn.zhku.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import cn.zhku.dao.DataAccessor;
import cn.zhku.modal.Teacher;
import cn.zhku.modal.User;
public class LoadUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadUsers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<?> us = DataAccessor.findAll(User.class);
		for(Object o : us){
			User u = (User) o;
			u.setType(u.getClass().getSimpleName());
		}
		JSONArray jsonArray = new JSONArray();   
		jsonArray.put(us);
		response.setCharacterEncoding("UTF-8"); 
		jsonArray.write(response.getWriter());
	}

}
