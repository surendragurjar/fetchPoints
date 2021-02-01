package com.rewards.fetch.fetchPoints.exception;

public class LowBalanceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1755188760799270787L;

	public LowBalanceException(String message) {
		super(message);
	}
}
