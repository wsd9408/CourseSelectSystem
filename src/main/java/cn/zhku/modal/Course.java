package cn.zhku.modal;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Course implements Serializable, Cloneable {
	@Id
	@GeneratedValue
	private Long id;

	private String courseId;
	private String courseName;
	private String courseType;
	private double score;
	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "tid", referencedColumnName = "id", nullable = false)
	private Teacher teacher;
	private int MaxStuNum;
	private int selectedStuNum;
	private int selectStuNum;
	private int year;
	public Course() {
		
	}
	public Course(String courseId, String courseName, String courseType,
			double score, Teacher teacher, int MaxStuNum, int year) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseType = courseType;
		this.score = score;
		this.teacher = teacher;
		this.MaxStuNum = MaxStuNum;
		this.year = year;
	}
	public Long getId() {
		return id;
	}

	public String getCourseId() {
		return courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getCourseType() {
		return courseType;
	}

	public double getScore() {
		return score;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public int getMaxStuNum() {
		return MaxStuNum;
	}

	public int getSelectedStuNum() {
		return selectedStuNum;
	}

	public int getSelectStuNum() {
		return selectStuNum;
	}

	public int getYear() {
		return year;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public void setMaxStuNum(int maxStuNum) {
		MaxStuNum = maxStuNum;
	}

	public void setSelectedStuNum(int selectedStuNum) {
		this.selectedStuNum = selectedStuNum;
	}

	public void setSelectStuNum(int selectStuNum) {
		this.selectStuNum = selectStuNum;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "ClassMsg [courseId=" + courseId + ", courseName=" + courseName
				+ ", type=" + courseType + ", score=" + score + ", teacher="
				+ teacher + ", limitStuNum=" + MaxStuNum
				+ ", selectedStuNum=" + selectedStuNum + ", selectStuNum="
				+ selectStuNum + "]";
	}
	public Object clone() {
		Object o = null;
		try {
			o = (Course) super.clone();// Object 中的clone()识别出你要复制的是哪一个对象。
		} catch (CloneNotSupportedException e) {
			System.out.println(e.toString());
		}
		return o;
	}
}
