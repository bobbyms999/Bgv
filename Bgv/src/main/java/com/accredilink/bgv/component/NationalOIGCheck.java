package com.accredilink.bgv.component;

import java.io.File;

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
public class NationalOIGCheck implements OigCalls{

	private static final String URL = "https://exclusions.oig.hhs.gov/";

	@Autowired
	private WebDriver driver;

	@Autowired
	private EmployeeBgDetailsBuilder builder;

	public EmployeeBgDetails check(EmployeeAgency employeeAgency,String field1,String filed2) {
		driver.get(URL);
		driver.findElement(By.id("ctl00_cpExclusions_txtSPLastName")).sendKeys((field1!=null?field1:""));
		driver.findElement(By.id("ctl00_cpExclusions_txtSPFirstName")).sendKeys((filed2!=null?filed2:""));
		driver.findElement(By.id("ctl00_cpExclusions_ibSearchSP")).click();
		try {
			driver.findElement(By.xpath("//*[@id='ctl00_cpExclusions_P1']/strong")).getText();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			File image = ScreenShot.takescreenshot(driver);
			return builder.build(image, employeeAgency, "No Records Found From National OIG",1,"No Records Found In National OIG");
		} catch (NoSuchElementException e) {
			driver.findElement(By.linkText("Verify")).click();
			driver.findElement(By.id("ctl00_cpExclusions_txtSSN")).sendKeys(employeeAgency.getEmployee().getSsnNumber());
			driver.findElement(By.id("ctl00_cpExclusions_ibtnVerify")).click();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ie) {
				e.printStackTrace();
			}
			try {
				WebElement errorImage = driver.findElement(By.cssSelector("img[src='Images/verify-no-match.png']"));
				String imageName = errorImage.getAttribute("src");
				if ("https://exclusions.oig.hhs.gov/Images/verify-no-match.png".equals(imageName.trim())) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					File image = ScreenShot.takescreenshot(driver);
					return builder.build(image, employeeAgency, "No Records Found From National OIG",1,"No Records Found In National OIG");
				} else {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e2) {
						e2.printStackTrace();
					}
					File image = ScreenShot.takescreenshot(driver);
					return builder.build(image, employeeAgency, "SSN Number Matched",1,"Records Found In National OIG");
				}
			} catch (NoSuchElementException ex) {
				ex.printStackTrace();
				throw new AccredLinkAppException("Unable to process your request now");
			}
			
		}
	}
}
