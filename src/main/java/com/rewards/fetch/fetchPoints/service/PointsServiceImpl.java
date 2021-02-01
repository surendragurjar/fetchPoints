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

	// static variable to maintain transaction list
	public List<Record> lstTransactions = new ArrayList<>();

	@Override
	public String addPoints(String userName, String payerName, int points) throws PointsException {

		int updatedTransactionId = 0;

		if (points >= 0) {
			Record newRecord = new Record();
			newRecord.setUserName(userName);
			newRecord.setPayerName(payerName);
			newRecord.setInitialPoints(points);
			newRecord.setPoints(points);
			newRecord.setTransactionDate(new Date());
			lstTransactions.add(newRecord);

			updatedTransactionId = newRecord.getId();
		} else { // negative value in points hence needs special handling
			int negativePoints = Math.abs(points);

			// check if payer has balance points
			int totalPointsForPayer = lstTransactions.stream().filter(
					record -> (record.getUserName().equalsIgnoreCase(userName) && record.getPayerName().equalsIgnoreCase(payerName)))
					.mapToInt(a -> a.getPoints()).sum();

			if (totalPointsForPayer < negativePoints) {
				throw new PointsException(PointsConstanst.LOW_BALANCE_MESSAGE);
			} else {
				Record newRecord = new Record();
				newRecord.setUserName(userName);
				newRecord.setPayerName(payerName);
				newRecord.setInitialPoints(points);
				newRecord.setPoints(0);
				newRecord.setTransactionDate(new Date());
				lstTransactions.add(newRecord);

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
						record.setPoints(remainingPoints);
						record.setUpdatedTransactionId(String.valueOf(updatedTransactionId));

						negativePoints -= negativePoints;
						break;
					} else if (record.getPoints() < negativePoints) { // deduct the amount and iterate next element
						negativePoints -= record.getPoints();
						record.setPoints(0);
						record.setUpdatedTransactionId(String.valueOf(updatedTransactionId));
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
		
		if(deductPoints <= 0) {
			throw new PointsException(PointsConstanst.NEGATIVE_POINTS_MESSAGE);	
		}
		
		//List<Record> result = new ArrayList<>();

		int totalPoints = lstTransactions.stream().filter(a -> a.getUserName().equalsIgnoreCase(userName))
				.mapToInt(a -> a.getPoints()).sum();
		int updatedTransactionId = 0;

		if (totalPoints < deductPoints) {
			throw new PointsException(PointsConstanst.LOW_BALANCE_MESSAGE);
		} else {
			// adding entry to transactions for tracking purpose
			Record newRecord = new Record();
			newRecord.setUserName(userName);
			newRecord.setPayerName("Deduct Operation");
			newRecord.setInitialPoints(deductPoints);
			newRecord.setPoints(0);
			newRecord.setTransactionDate(new Date());
			lstTransactions.add(newRecord);

			updatedTransactionId = newRecord.getId();
		}

		// sort the transaction based on date
		Collections.sort(lstTransactions);

		for (Record record : lstTransactions) {
			if (record.getUserName().equalsIgnoreCase(userName)) {
				if (record.getPoints() >= deductPoints) {
					int remainingPoints = record.getPoints() - deductPoints;
					record.setPoints(remainingPoints);
					record.setUpdatedTransactionId(String.valueOf(updatedTransactionId));

					deductPoints = 0;
					break;
				} else if (record.getPoints() < deductPoints) {
					deductPoints -= record.getPoints();
					record.setPoints(0);
					record.setUpdatedTransactionId(String.valueOf(updatedTransactionId));
				}
			}
		}

		return "Success";
	}

	@Override
	public List<Record> getPoints(String userName) {
		List<Record> result = new ArrayList<>();
		for (Record record : lstTransactions) {
			if (record.getUserName().equalsIgnoreCase(userName)) {
				result.add(record);
			}
		}
		// result = (List<Record>) lstTransactions.stream().filter(a ->
		// a.getUserName().equalsIgnoreCase(userName));
		return result;
	}

}
