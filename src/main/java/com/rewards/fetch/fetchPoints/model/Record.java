package com.rewards.fetch.fetchPoints.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Record implements Comparable<Record> {
	
	private static final AtomicInteger count = new AtomicInteger(0); 
	
	private int id;

	private String payerName;
	
	private int points;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date transactionDate;
	
	private String userName;
	
	// additional attributes initialPoints, updatedTransactionId to keep records	
	private int initialPoints;
	
	private String updatedTransactionId;	

	public Record() {
		super();
		this.id = count.incrementAndGet(); 
	}

	public int getId() {
		return id;
	}
	
	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date date) {
		this.transactionDate = date;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getInitialPoints() {
		return initialPoints;
	}

	public void setInitialPoints(int initialPoints) {
		this.initialPoints = initialPoints;
	}
	
	public String getUpdatedTransactionId() {
		return updatedTransactionId;
	}

	public void setUpdatedTransactionId(String updatedTransactionId) {
		this.updatedTransactionId = updatedTransactionId;
	}
	
	@Override
	public String toString() {
		return "Record [id=" + id + ", payerName=" + payerName + ", points=" + points + ", date=" + transactionDate + ", userName="
				+ userName + ", initialPoints=" + initialPoints 
				+ ", updatedTransactionId=" + updatedTransactionId + "]";
	}
	
	@Override
	public int compareTo(Record object2) {
		return this.getTransactionDate().compareTo(object2.getTransactionDate());
	}

}
