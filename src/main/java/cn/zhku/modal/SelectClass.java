package cn.zhku.modal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
@Entity
public class SelectClass{
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne(targetEntity=Student.class,fetch=FetchType.EAGER)
	@JoinColumn(name = "sid", referencedColumnName = "id", nullable = false)
	private Student stu;
	private double selectScore;
	@ElementCollection(targetClass=Course.class)
	@CollectionTable(name="selectmsg_table",joinColumns=@JoinColumn(name="id",nullable=false))
	@Column(name="selectclass_id")
	@OrderColumn(name="list_order")
	private List<Course> cmList = new ArrayList<Course>();
	private int year;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Student getStu() {
		return stu;
	}
	public void setStu(Student stu) {
		this.stu = stu;
	}
	public List<Course> getCmList() {
		return cmList;
	}
	public void setCmList(List<Course> cmList) {
		this.cmList = cmList;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getSelectScore() {
		return selectScore;
	}
	public void setSelectScore(double selectScore) {
		this.selectScore = selectScore;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cmList == null) ? 0 : cmList.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(selectScore);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((stu == null) ? 0 : stu.hashCode());
		result = prime * result + year;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectClass other = (SelectClass) obj;
		if (cmList == null) {
			if (other.cmList != null)
				return false;
		} else if (!cmList.equals(other.cmList))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(selectScore) != Double
				.doubleToLongBits(other.selectScore))
			return false;
		if (stu == null) {
			if (other.stu != null)
				return false;
		} else if (!stu.equals(other.stu))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
}
