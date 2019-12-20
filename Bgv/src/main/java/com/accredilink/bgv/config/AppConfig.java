package com.accredilink.bgv.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:selinum.properties")
public class AppConfig {

	@Bean
	public WebDriver newChromeDriver() {
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\TECRA\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
		return driver;

	}

}
