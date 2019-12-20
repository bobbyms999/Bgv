package com.accredilink.bgv.component;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accredilink.bgv.entity.EmployeeAgency;
import com.accredilink.bgv.entity.EmployeeBgDetails;
import com.accredilink.bgv.exception.AccredLinkAppException;
import com.accredilink.bgv.util.ScreenShot;

@Component
public class DadsEMRCheck{

	private static final String URL = "https://emr.dads.state.tx.us/DadsEMRWeb/emrRegistrySearch.jsp";

	@Autowired
	private WebDriver driver;

	@Autowired
	private EmployeeBgDetailsBuilder builder;

	public EmployeeBgDetails check(EmployeeAgency employeeAgency) {
		EmployeeBgDetails bgDetails = null;
		driver.get(URL);
		driver.findElement(By.id("socialSecurityNumber0")).sendKeys(employeeAgency.getEmployee().getSsnNumber());
		String cssSelectorOfButton = "input[type='submit']";
		List<WebElement> loginButton = driver.findElements(By.cssSelector(cssSelectorOfButton));
		loginButton.get(1).click();
		try {
			if (driver.getPageSource().contains("No Results Found")) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				File image = ScreenShot.takescreenshot(driver);
				bgDetails = builder.build(image, employeeAgency, "No Records Found From DAD'S EMR", 3,"No Records Found In DAD'S EMR");
			} else {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				File image = ScreenShot.takescreenshot(driver);
				bgDetails = builder.build(image, employeeAgency, "SSN Number Matched", 3,"Records Found In DAD'S EMR");
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			throw new AccredLinkAppException("Unable to process your request now");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bgDetails;
	}

}
