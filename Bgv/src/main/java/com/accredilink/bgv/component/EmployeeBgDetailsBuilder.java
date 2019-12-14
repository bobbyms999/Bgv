package com.accredilink.bgv.component;

import java.io.File;

import org.springframework.stereotype.Component;

import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.entity.EmployeeBgDetails;

@Component
public class EmployeeBgDetailsBuilder {

	public EmployeeBgDetails build(File image, Employee employee, String message) {

		EmployeeBgDetails bgDetails = new EmployeeBgDetails();
		bgDetails.setBgResult(message);
		return bgDetails;
	}

}
