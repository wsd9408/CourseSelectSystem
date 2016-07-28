package cn.zhku.modal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SelectClassUser {
	@Id
	private int year;
	private String startTime;
	private String closeTime;
	private boolean state;
	private double limitScore;
	
	public SelectClassUser(int year, String startTime, String closeTime,
			boolean state,double limitScore) {
		super();
		this.year = year;
		this.startTime = startTime;
		this.closeTime = closeTime;
		this.state = state;
		this.limitScore = limitScore;
	}
	public SelectClassUser(){
		
	}
	public SelectClassUser( int year,double limitScore) {
		super();
		this.year = year;
		this.limitScore = limitScore;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public double getLimitScore() {
		return limitScore;
	}
	public void setLimitScore(double limitScore) {
		this.limitScore = limitScore;
	}
}
