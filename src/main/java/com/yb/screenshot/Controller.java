package com.yb.screenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getScreenshoot(String url) {
		return file(url);
	}

	private byte[] file(String url) {
//		System.setProperty("webdriver.chrome.driver", "geckodriver");
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--headless");
		options.addArguments("--start-maximized");
		WebDriver driver = new FirefoxDriver(options);
		try {
			driver.get(url);
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			return Files.readAllBytes(srcFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
		return null;
	}

}
