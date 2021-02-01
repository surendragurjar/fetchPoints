package com.rewards.fetch.fetchPoints.service;

import java.util.List;

import com.rewards.fetch.fetchPoints.exception.LowBalanceException;
import com.rewards.fetch.fetchPoints.model.Record;

public interface PointsService {
	
	public List<Record> getPoints(String userName);

	public String addPoints(String userName, String payerName, int points) throws LowBalanceException;

	public String duductPoints(String userName, int deductPoints) throws LowBalanceException;

}
