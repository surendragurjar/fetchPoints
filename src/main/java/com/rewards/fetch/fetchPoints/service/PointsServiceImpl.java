package com.rewards.fetch.fetchPoints.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rewards.fetch.fetchPoints.constants.PointsConstanst;
import com.rewards.fetch.fetchPoints.exception.PointsException;
import com.rewards.fetch.fetchPoints.model.Record;

@Service
public class PointsServiceImpl implements PointsService {

	// variable to maintain transaction list
	public List<Record> lstTransactions = new ArrayList<>();

	@Override
	public List<Record> getPoints(String userName) {
		List<Record> result = new ArrayList<>();
		for (Record record : lstTransactions) {
			if (record.getUserName().equalsIgnoreCase(userName)) {
				result.add(record);
			}
		}
		return result;
	}

	@Override
	public String addPoints(String userName, String payerName, int points) throws PointsException {

		int updatedTransactionId = 0;

		if (points >= 0) {
			Record newRecord = addRecord(userName, payerName, points, points);
			updatedTransactionId = newRecord.getId();
		} else { // negative value in points hence needs special handling
			int negativePoints = Math.abs(points);

			// check if payer has balance points
			int totalPointsForPayer = lstTransactions.stream()
					.filter(record -> (record.getUserName().equalsIgnoreCase(userName)
							&& record.getPayerName().equalsIgnoreCase(payerName)))
					.mapToInt(a -> a.getPoints()).sum();

			if (totalPointsForPayer < negativePoints) {
				throw new PointsException(PointsConstanst.LOW_BALANCE_MESSAGE);
			} else {
				Record newRecord = addRecord(userName, payerName, points, points);
				updatedTransactionId = newRecord.getId();
			}

			// sort the transaction based on date
			Collections.sort(lstTransactions);

			// iterate transaction list and deduct amount from payer balance points
			for (Record record : lstTransactions) {
				if (record.getUserName().equalsIgnoreCase(userName)
						&& record.getPayerName().equalsIgnoreCase(payerName)) {
					if (record.getPoints() >= negativePoints) { // deduct the amount and break
						int remainingPoints = record.getPoints() - negativePoints;
						updateRecord(record, remainingPoints, updatedTransactionId);

						negativePoints -= negativePoints;
						break;
					} else if (record.getPoints() < negativePoints) { // deduct the amount and iterate next element
						negativePoints -= record.getPoints();
						updateRecord(record, 0, updatedTransactionId);
					}
				}
				if (negativePoints == 0) {
					break;
				}
			}
		}

		return "Success";
	}

	@Override
	public String duductPoints(String userName, int deductPoints) throws PointsException {
		
		int updatedTransactionId = 0;

		if (deductPoints <= 0) {
			throw new PointsException(PointsConstanst.NEGATIVE_POINTS_MESSAGE);
		}

		// calculate total points 
		int totalPoints = lstTransactions.stream().filter(a -> a.getUserName().equalsIgnoreCase(userName))
				.mapToInt(a -> a.getPoints()).sum();
		
		// throw exception if points are less then total points 
		if (totalPoints < deductPoints) {
			throw new PointsException(PointsConstanst.LOW_BALANCE_MESSAGE);
		} else {
			// adding entry to transactions for tracking purpose			
			Record newRecord = addRecord(userName, "Deduct Operation", 0, deductPoints);
			updatedTransactionId = newRecord.getId();
		}

		// sort the transaction based on date
		Collections.sort(lstTransactions);

		for (Record record : lstTransactions) {
			if (record.getUserName().equalsIgnoreCase(userName)) {
				if (record.getPoints() >= deductPoints) {
					int remainingPoints = record.getPoints() - deductPoints;
					updateRecord(record, remainingPoints, updatedTransactionId);

					deductPoints = 0;
					break;
				} else if (record.getPoints() < deductPoints) {
					deductPoints -= record.getPoints();
					updateRecord(record, 0, updatedTransactionId);
				}
			}
		}

		return "Success";
	}	
	
	private Record addRecord(String userName, String payerName, int points, int initialPoints) {
		Record newRecord = new Record();
		newRecord.setUserName(userName);
		newRecord.setPayerName(payerName);		
		newRecord.setPoints(points);
		newRecord.setTransactionDate(new Date());
		
		//set initial point for tracking purpose
		newRecord.setInitialPoints(initialPoints);
		
		// add record to transaction list 
		lstTransactions.add(newRecord);
		return newRecord;
	}
	
	private void updateRecord(Record record, int points, int updatedTransactionId) {
		record.setPoints(points);
		record.setUpdatedTransactionId(String.valueOf(updatedTransactionId));
	}

}
