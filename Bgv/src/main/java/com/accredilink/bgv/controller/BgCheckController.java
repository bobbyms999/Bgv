package com.accredilink.bgv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accredilink.bgv.component.TxOIGCheck;
import com.accredilink.bgv.service.BgCheckService;
import com.accredilink.bgv.util.ResponseObject;

@RestController
@RequestMapping("/submit")
public class BgCheckController {

	@Autowired
	private BgCheckService bgCheckService;

	@GetMapping("/bgcheck/{employeeId}")
	public ResponseObject check(@PathVariable int employeeId) {
		return bgCheckService.submitBg(employeeId);
	}
}
