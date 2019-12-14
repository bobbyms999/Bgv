package com.accredilink.bgv.component;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.entity.EmployeeBgDetails;
import com.accredilink.bgv.exception.AccredLinkAppException;
import com.accredilink.bgv.util.ScreenShot;

@Component
public class DadsEMRCheck {

	private static final String URL = "https://emr.dads.state.tx.us/DadsEMRWeb/emrRegistrySearch.jsp";

	@Autowired
	private WebDriver driver;

	@Autowired
	private EmployeeBgDetailsBuilder builder;

	public EmployeeBgDetails check(Employee employee) {
		EmployeeBgDetails bgDetails=null;
		driver.get(URL);
		driver.findElement(By.id("socialSecurityNumber0")).sendKeys(employee.getSsnNumber());
		String cssSelectorOfButton = "input[type='submit']";
		List<WebElement> loginButton = driver.findElements(By.cssSelector(cssSelectorOfButton));
		loginButton.get(1).click();
		try {
			if (driver.getPageSource().contains("No Results Found")) {
				File image = ScreenShot.takescreenshot(driver,"dads ssnotmatch");
				bgDetails=builder.build(image, employee, "No Results Found");
			} else {
				File image = ScreenShot.takescreenshot(driver,"ssn");
				bgDetails=builder.build(image, employee, "SSN Number Matched");
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
