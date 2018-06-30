package com.jaymukul.sample;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VerifyLogin {
	
	public WebDriver driver=null ;
  
	
@BeforeMethod	
public void runconfiguration() 
{
	  System.setProperty("webdriver.chrome.driver","C:\\Users\\Jay Mukul\\Downloads\\chromedriver_win32\\chromedriver.exe");
		 
		// Initialize browser
	driver=new ChromeDriver();
	
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	
	System.out.println("Step1:-Chrome Driver Instantiated for ToolsQA.<br>");
	
	driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
	
	System.out.println("Step2:-Go to System URL and Login the User.<br>");
	
	driver.manage().window().maximize();
		
}

 @Test
 public void LoginIntoDemoPractice() throws InterruptedException 
 {
	 
	 WebElement Username = driver.findElement(By.id("email"));
	 WebElement password = driver.findElement(By.id("passwd"));
	 WebElement submit = driver.findElement(By.id("SubmitLogin"));
	 
	 Username.sendKeys("jay.mukul@discus.solutions");
	 password.sendKeys("jay*12345");
	 
	 WebDriverWait wait = new WebDriverWait(driver, 15);

	 wait.until(ExpectedConditions.elementToBeClickable(submit));
	 submit.click();
	 
	 System.out.println("Login Success");
	 
 }
 
 @AfterMethod
 public void afterclose() 
 {
		
	 driver.close();
 }
 
}
