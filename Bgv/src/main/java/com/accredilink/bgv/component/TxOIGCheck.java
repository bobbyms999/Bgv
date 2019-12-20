package com.accredilink.bgv.component;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accredilink.bgv.entity.EmployeeAgency;
import com.accredilink.bgv.entity.EmployeeBgDetails;
import com.accredilink.bgv.util.ScreenShot;

@Component
public class TxOIGCheck implements OigCalls{

	private static final String URL = "https://oig.hhsc.state.tx.us/oigportal/EXCLUSIONS.aspx";

	@Autowired
	private WebDriver driver;

	@Autowired
	private EmployeeBgDetailsBuilder builder;

	public EmployeeBgDetails check(EmployeeAgency employeeAgency,String field1,String filed2) {

		int count = 2;
		String ssnResult = "SSN/EIN Does Not Match!!";
		EmployeeBgDetails bgDetails = null;

		driver.manage().window().maximize();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.get(URL);
		

		WebElement lastNameField = driver.findElement(By.id("dnn_ctr423_Search_txtLast1"));
		lastNameField.clear();
		lastNameField.sendKeys((field1 != null ? field1 : ""));

		WebElement firstNameField = driver.findElement(By.id("dnn_ctr423_Search_txtFirst1"));
		firstNameField.clear();
		firstNameField.sendKeys((filed2 != null ? filed2 : ""));

		WebElement submit = driver.findElement(By.id("dnn_ctr423_Search_btnSearch"));
		submit.click();
		

		List<WebElement> rowcount = driver.findElements(By.xpath("//*[@id=\"dnn_ctr423_Search_gv_Results\"]/tbody/tr"));
		js.executeScript("window.scrollBy(0,1000)");

		for (int i = 0; i <= rowcount.size() - 2; i++) {
			try {
				if (i == 10) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					File image = ScreenShot.takescreenshot(driver);
					bgDetails = builder.build(image, employeeAgency, "No Records Found From Texas OIG",2,"No Records Found In Texas OIG");
					break;
				}
				WebElement rowlink = driver
						.findElement(By.id("dnn_ctr423_Search_gv_Results_imgBtnViewSSNVerification_" + i + ""));
				rowlink.click();
				
				WebElement clikonssn;
				if (count <= 9) {
					clikonssn = driver.findElement(By.id("dnn_ctr423_Search_gv_Results_ctl0" + count + "_wmepubsvfy"));
				} else {
					clikonssn = driver.findElement(By.id("dnn_ctr423_Search_gv_Results_ctl" + count + "_wmepubsvfy"));
				}
				clikonssn.click();
				clikonssn.sendKeys(employeeAgency.getEmployee().getSsnNumber());
			

				WebElement clickonverify = driver
						.findElement(By.id("dnn_ctr423_Search_gv_Results_btnVerifySSN_" + i + ""));
				clickonverify.click();

				WebElement Results = driver.findElement(By.id("dnn_ctr423_Search_gv_Results_lblVerifiedResult_0"));
				if (Results.getText() != null && !Results.getText().equalsIgnoreCase(ssnResult)) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					File image = ScreenShot.takescreenshot(driver);
					bgDetails = builder.build(image, employeeAgency, "SSN Number Matched",2,"Records Found In Texas OIG");
					break;
				}
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				count++;
				if (i == rowcount.size() - 2) {
					
					File image = ScreenShot.takescreenshot(driver);
					bgDetails = builder.build(image, employeeAgency, "No Records Found From Texas OIG",2,"No Records Found In Texas OIG");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (rowcount.size() == 0) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			File image = ScreenShot.takescreenshot(driver);
			bgDetails = builder.build(image, employeeAgency, "No Records Found From Texas OIG",2,"No Records Found In Texas OIG");
		}
		return bgDetails;
	}
}