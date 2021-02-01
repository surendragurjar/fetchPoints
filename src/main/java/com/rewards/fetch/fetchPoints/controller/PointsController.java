package com.rewards.fetch.fetchPoints.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.fetch.fetchPoints.exception.PointsException;
import com.rewards.fetch.fetchPoints.model.Record;
import com.rewards.fetch.fetchPoints.service.PointsService;

@RestController
public class PointsController {

	@Autowired
	private PointsService pointService;

	@GetMapping("/points")
	public List<Record> getPoints(@RequestParam(value = "userName", defaultValue = "") String userName) {
		return pointService.getPoints(userName);
	}

	@PostMapping("/addPoints")
	public String addPoints(@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "payerName", defaultValue = "") String payerName,
			@RequestParam(value = "points", defaultValue = "0") int points) throws PointsException {
		return pointService.addPoints(userName, payerName, points);
	}

	@PostMapping("/deductPoints")
	public String deductPoints(@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "points", defaultValue = "0") int points) throws PointsException {
		return pointService.duductPoints(userName, points);
	}

}
