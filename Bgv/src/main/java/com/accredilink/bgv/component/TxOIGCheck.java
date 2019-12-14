package com.accredilink.bgv.component;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.entity.EmployeeBgDetails;
import com.accredilink.bgv.util.ScreenShot;

@Component
public class TxOIGCheck {

	private static final String URL = "https://emr.dads.state.tx.us/DadsEMRWeb/emrRegistrySearch.jsp";

	@Autowired
	private WebDriver driver;

	@Autowired
	private EmployeeBgDetailsBuilder builder;

	public EmployeeBgDetails check(Employee employee) {

		int count = 2;
		String ssnResult = "SSN/EIN Does Not Match!!";
		EmployeeBgDetails bgDetails = null;

		driver.manage().window().maximize();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Launch URL
		driver.get("https://oig.hhsc.state.tx.us/oigportal/EXCLUSIONS.aspx");
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

		WebElement lastNameField = driver.findElement(By.id("dnn_ctr423_Search_txtLast1"));
		lastNameField.clear();
		lastNameField.sendKeys(employee.getLastName());

		WebElement firstNameField = driver.findElement(By.id("dnn_ctr423_Search_txtFirst1"));
		firstNameField.clear();
		firstNameField.sendKeys(employee.getFirstName());

		WebElement submit = driver.findElement(By.id("dnn_ctr423_Search_btnSearch"));
		submit.click();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

		List<WebElement> rowcount = driver.findElements(By.xpath("//*[@id=\"dnn_ctr423_Search_gv_Results\"]/tbody/tr"));
		js.executeScript("window.scrollBy(0,1000)");

		for (int i = 0; i <= rowcount.size() - 2; i++) {
			if (i == 10) {
				break;
			}
			WebElement rowlink = driver
					.findElement(By.id("dnn_ctr423_Search_gv_Results_imgBtnViewSSNVerification_" + i + ""));
			rowlink.click();
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			WebElement clikonssn;
			if (count <= 9) {
				clikonssn = driver.findElement(By.id("dnn_ctr423_Search_gv_Results_ctl0" + count + "_wmepubsvfy"));
			} else {
				clikonssn = driver.findElement(By.id("dnn_ctr423_Search_gv_Results_ctl" + count + "_wmepubsvfy"));
			}
			clikonssn.click();
			clikonssn.sendKeys(employee.getSsnNumber());
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

			WebElement clickonverify = driver.findElement(By.id("dnn_ctr423_Search_gv_Results_btnVerifySSN_" + i + ""));
			clickonverify.click();

			WebElement Results = driver.findElement(By.id("dnn_ctr423_Search_gv_Results_lblVerifiedResult_0"));
			if (Results.getText() != null && !Results.getText().equalsIgnoreCase(ssnResult)) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				File image = ScreenShot.takescreenshot(driver,"ssn");
				bgDetails = builder.build(image, employee, "SSN Number Matched");
				break;
			}
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
			if (i == rowcount.size() - 2) {
				File image = ScreenShot.takescreenshot(driver,"txssnnotmatch");
				bgDetails = builder.build(image, employee, "SSN Number Not Valid");
			}
		}
		if (rowcount.size() == 0) {
			File image = ScreenShot.takescreenshot(driver,"txusernotmatch");
			bgDetails = builder.build(image, employee, "No results found");
		}
		return bgDetails;
	}
}