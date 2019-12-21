package com.accredilink.bgv.service;

import java.util.List;

import com.accredilink.bgv.pojo.BgDetails;
import com.accredilink.bgv.pojo.Image;
import com.accredilink.bgv.util.ResponseObject;

public interface BgCheckService {

	public ResponseObject submitEmployeeBg();
	
	public ResponseObject submitIndividualEmployeeBg(int employeeId);
	
	public List<BgDetails> verifyEmployeeBg();
	
	public List<Image> bgProofImages(int employeeId);

}
