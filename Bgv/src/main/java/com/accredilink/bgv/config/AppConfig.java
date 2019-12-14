package com.accredilink.bgv.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import io.github.bonigarcia.wdm.WebDriverManager;

@Configuration
@PropertySource("classpath:selinum.properties")
public class AppConfig {

	@Value("${chrome.version}")
	public String chromeVersion;

	@Bean
	public WebDriver newChromeDriver() {
		WebDriver driver;
		WebDriverManager.chromedriver().version(chromeVersion).setup();
		driver = new ChromeDriver();
		return driver;

	}

}
