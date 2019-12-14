package com.accredilink.bgv.component;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.entity.EmployeeBgDetails;
import com.accredilink.bgv.util.ScreenShot;

@Component
public class NationalOIGCheck {

	private static final String URL = "https://exclusions.oig.hhs.gov/";

	@Autowired
	private WebDriver driver;

	@Autowired
	private EmployeeBgDetailsBuilder builder;

	public EmployeeBgDetails check(Employee employee) {
		driver.get(URL);
		driver.findElement(By.id("ctl00_cpExclusions_txtSPLastName")).sendKeys(employee.getLastName());
		driver.findElement(By.id("ctl00_cpExclusions_txtSPFirstName")).sendKeys(employee.getFirstName());
		driver.findElement(By.id("ctl00_cpExclusions_ibSearchSP")).click();
		try {
			String errorMessage = driver.findElement(By.xpath("//*[@id='ctl00_cpExclusions_P1']/strong")).getText();
			File image = ScreenShot.takescreenshot(driver,"us not match");
			return builder.build(image, employee, errorMessage);
		} catch (NoSuchElementException e) {
			driver.findElement(By.linkText("Verify")).click();
			driver.findElement(By.id("ctl00_cpExclusions_txtSSN")).sendKeys(employee.getSsnNumber());
			driver.findElement(By.id("ctl00_cpExclusions_ibtnVerify")).click();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ie) {
				e.printStackTrace();
			}
			try {
				WebElement errorImage = driver.findElement(By.cssSelector("img[src='Images/verify-no-match.png']"));
				String imageName = errorImage.getAttribute("src");
				if ("https://exclusions.oig.hhs.gov/Images/verify-no-match.png".equals(imageName.trim())) {
					File image = ScreenShot.takescreenshot(driver,"natssnnotmatch");
					return builder.build(image, employee, imageName);
				} else {
					File image = ScreenShot.takescreenshot(driver,"ssn");
					return builder.build(image, employee, "SSN Number Matched");
				}
			} catch (NoSuchElementException ex) {
				File image = ScreenShot.takescreenshot(driver,"e");
				return builder.build(image, employee, "SSN Number Matched");
			}
		}
	}
}
