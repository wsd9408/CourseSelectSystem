package cn.zhku.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import cn.zhku.modal.Course;
import cn.zhku.modal.SelectClass;
import cn.zhku.modal.SelectClassUser;
import cn.zhku.modal.User;
/**
 * 数据库访问工具类
 * @author RICK
 *
 */
public class DataAccessor {
	
	private static EntityManager _manager;
	public static EntityManager getManager(){
		if(_manager == null){
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("mydb");
			_manager = factory.createEntityManager();
		}
		return _manager;
	}
	public static void saveNew(Object entity){
		DataAccessor.getManager().getTransaction().begin();// 开启事务
		DataAccessor.getManager().merge(entity);
		DataAccessor.getManager().flush();
		DataAccessor.getManager().getTransaction().commit();// 提交事务
		DataAccessor.getManager().clear();
	}
	
	public static void update(Object entity){
		DataAccessor.getManager().clear();
		DataAccessor.getManager().getTransaction().begin();// 开启事务
		DataAccessor.getManager().merge(entity);
		DataAccessor.getManager().flush();
		DataAccessor.getManager().getTransaction().commit();// 提交事务
	}
	
	public static <T> List<?> findByStuCode(String code){
		String jpql = "select x from User x where x.code = :code";
		TypedQuery<?> q = DataAccessor.getManager().createQuery(jpql, User.class).setParameter("code", code);
		List<?> list = q.getResultList();
		return list;
		
	}
	public static <T> List<?> findAll(Class<T> resultClass){
		String sql = String.format("select x from %s x", resultClass.getSimpleName());
		TypedQuery<?> q = DataAccessor.getManager().createQuery(sql, resultClass);
		return q.getResultList();
	}
	public static void delete(Class<?> entityClass,Long id){
		Object ent = DataAccessor.getManager().find(entityClass, id);
		DataAccessor.getManager().remove(ent);
	}
	public static Object findById(Class<?> entityClass,Serializable id){
		Object entity = DataAccessor.getManager().find(entityClass, id);
		return entity;
	}
	public static <T> Object findByUserCode(String code,int year){
		String jpql = "select x from SelectClass x where x.stu.code = :code and x.year=:year";
		TypedQuery<?> q = DataAccessor.getManager().createQuery(jpql, SelectClass.class).setParameter("code", code).setParameter("year", year);
		List<?> list = q.getResultList();
		if(list.size() == 0){
			return null;
		} else {
			return list.get(0); 
		}
	}
	
	public static <T> List<?>  findByUserCode(String code){
		String jpql = "select x from SelectClass x where x.stu.code = :code ";
		TypedQuery<?> q = DataAccessor.getManager().createQuery(jpql, SelectClass.class).setParameter("code", code);
		List<?> list = q.getResultList();
		return list;
	}
	
	public static <T> List<?> findAllUserByParam(String code,String name,String all){
		TypedQuery<?> q  = null;
		if(all == ""){
			String jpql = "select x from User x where x.code like ?0 and x.name like ?1";
			q = DataAccessor.getManager().createQuery(jpql, User.class).setParameter(0, "%"+code+"%").setParameter(1, "%"+name+"%");
		}  else {
			String jpql = "select x from User x where x.code like ?1 or x.name like ?2";
			Field[] fields = User.class.getDeclaredFields();
			for(int i = 3;i<fields.length-1;i++){
				if(fields[i].getType().toString().equals("int")){
					String temp = " or cast(x."+fields[i].getName() + " as string )like ?"+i;
					jpql += temp;
				} else {
					String temp = " or x."+fields[i].getName() + " like ?"+i;
					jpql += temp;
				}
			}
			q = DataAccessor.getManager().createQuery(jpql, User.class);
			for(int i = 1;i<fields.length-1;i++){
				q.setParameter(i, "%"+all+"%");
			}
		}
		return q.getResultList();
	}



	public static <T> List<?> findAllClassByParam(String code,String name,String keyw){
		TypedQuery<?> q  = null;
		if(keyw == ""){
			String jpql = "select x from Course x where x.courseId like ?0 and x.courseName like ?1";
			q = DataAccessor.getManager().createQuery(jpql, Course.class).setParameter(0, "%"+code+"%").setParameter(1, "%"+name+"%");
		}  else {
			String jpql = "select x from Course x where x.courseId like ?0 and x.courseName like ?1";
			Field[] fields = Course.class.getDeclaredFields();
			for(int i = 2;i<fields.length-1;i++){
				if(fields[i].getType().toString().equals("int") || fields[i].getType().toString().equals("double")){
					String temp = " or cast(x."+fields[i].getName() + " as string ) like ?"+i;
					jpql += temp;
				} else if(fields[i].getName().equals("teacher")) {
					String temp = " or x."+fields[i].getName() + ".name like ?"+i;
					jpql += temp;
				} else {
					String temp = " or x."+fields[i].getName() + " like ?"+i;
					jpql += temp;
				}
				
			}
			q = DataAccessor.getManager().createQuery(jpql, Course.class);
			for(int i = 0;i<fields.length-1;i++){
				q.setParameter(i, "%"+keyw+"%");
			}
		}
		return q.getResultList();
	}
	
	public static <T> List<?> findAllClassByYear(Class<?> resultClass,int year){
		String jpql = "select x from Course x where x.year = :year";
		TypedQuery<?> q = DataAccessor.getManager().createQuery(jpql, Course.class).setParameter("year", year);
		List<?> list = q.getResultList();
		return list;
	}
	
	
	public static <T> Object findIsSelect(){
		String jpql = "select x from SelectClassUser x where x.state = :state";
		TypedQuery<?> q = DataAccessor.getManager().createQuery(jpql, SelectClassUser.class).setParameter("state", true);
		List<?> list = q.getResultList();
		if(list.size() == 0){
			return null;
		} else {
			return list.get(0); 
		}
	}
	
}


