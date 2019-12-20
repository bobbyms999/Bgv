package com.accredilink.bgv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accredilink.bgv.pojo.BgDetails;
import com.accredilink.bgv.pojo.Image;
import com.accredilink.bgv.service.BgCheckService;
import com.accredilink.bgv.util.ResponseObject;

@RestController
@RequestMapping("/submit")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BgCheckController {

	@Autowired
	private BgCheckService bgCheckService;

	@GetMapping("/bgsubmit")
	public ResponseObject bgSubmit() {
		return bgCheckService.submitEmployeeBg();
	}

	@GetMapping("/bgverify")
	public List<BgDetails> bgVerify() {
		return bgCheckService.verifyEmployeeBg();
	}
	@GetMapping("/bgproof/{employeeId}")
	public List<Image> bgProof(@PathVariable int employeeId){
		return bgCheckService.bgProofImages(employeeId);
	}
}
