package com.accredilink.bgv.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot {

	public static File takescreenshot(WebDriver driver,String filename) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(source, new File(".//sceenshots//"+filename+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return source;
	}
}
