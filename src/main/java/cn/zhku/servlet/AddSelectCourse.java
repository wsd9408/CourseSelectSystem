package cn.zhku.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import cn.zhku.dao.DataAccessor;
import cn.zhku.modal.Course;
import cn.zhku.modal.SelectClass;
import cn.zhku.modal.SelectClassUser;
import cn.zhku.modal.Student;

public class AddSelectCourse extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public AddSelectCourse() {
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

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String msg = "";
		long id = Long.parseLong(request.getParameter("id"));
		System.out.println(id);
		String code = request.getParameter("code");
		Long stuId = Long.valueOf(request.getParameter("stuId"));
		Course cmsg = (Course) DataAccessor.findById(Course.class, id);
		int limitNum = cmsg.getMaxStuNum();
		int selectNum = cmsg.getSelectedStuNum();
		int selectStuNum = cmsg.getSelectStuNum();
		double score = cmsg.getScore();
		int year = cmsg.getYear();
		SelectClassUser scm = (SelectClassUser) DataAccessor.findById(SelectClassUser.class, year);
		double maxSore = scm.getLimitScore();
		SelectClass sc = (SelectClass) DataAccessor.findByUserCode(code, year);
		try {
			if(sc == null ){
				sc = new SelectClass();
				Student stu = new Student();
				stu.setId(stuId);
				sc.setStu(stu);
				sc.setYear(year);
				List<Course> cmList = null;
				if(score <= maxSore){
					if(selectNum+1 <= limitNum){
						sc.setSelectScore(score);
						cmList = new ArrayList<Course>();
						cmList.add(cmsg);
						sc.setCmList(cmList);
						cmsg.setSelectedStuNum(selectNum+1);
						cmsg.setSelectStuNum(limitNum-1);
						DataAccessor.saveNew(sc);
						DataAccessor.update(cmsg);
						msg = "选课成功。";
					} else {
						msg="选课失败，原因：该课程人数已满。";
					}
				} else {
					msg = "选课失败，原因：突破学分上限.";
				}
			} else {

				double selectScore = sc.getSelectScore(); //已选学分
				selectScore += score;
				if(selectScore <= maxSore){
					if(selectNum+1 <= limitNum){ 
						if(!sc.getCmList().contains(cmsg)){
							sc.setSelectScore(selectScore);
							sc.getCmList().add(cmsg);
							cmsg.setSelectedStuNum(selectNum+1);
							cmsg.setSelectStuNum(limitNum-1);
							DataAccessor.update(sc);
							DataAccessor.update(cmsg);
							msg = "选课成功。";
						} else {
							msg = "选课失败，你已经选过这门课程！";
						}
					} else {
						msg="选课失败，原因：该课程人数已满。";
					}
				} else {
					msg = "选课失败，原因：突破学分上限.";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		JSONObject obj = new JSONObject();
		obj.put("msg", msg);
		String ms = obj.toString();
		System.out.println(obj);
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
