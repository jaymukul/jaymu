package com.temp.sample;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;


public class Config {

	public WebDriver driver=null ;
		
	/* Minimum requirement for test configuration */
	protected String testUrl; // Test url
	protected String seleniumHub; // Selenium hub IP
	protected String seleniumHubPort; // Selenium hub port
	protected String targetBrowser; // Target browser
	protected static String test_data_folder_path = null;
	protected static String screenshot_folder_path = null;
	public static String currentTest;
	
	public static String superAdmin="10001";
	public static String superAdminPass="admin@123";
	
	
	File file = new File("/home/discusit/Desktop/Manan/Softwares/moziladriver/geckodriver");
	
	public String path = file.getAbsolutePath();
	
	private String hostaddress;
	
	

    Enumeration e;
    						

	/**
	 * Configure the setup method
	 * 
	 * @param cosleepntext
	 * @throws InterruptedException
	 * @throws IOException
	 */

	/*@BeforeSuite(alwaysRun = true)
	public void serverConfig() throws InterruptedException, IOException {
		
			
		// Start Server--
		startAndStopServerAndNode("java -jar /home/discusit/Desktop/Manan/Softwares/selenium-server-standalone-3.7.1.jar -role hub & echo $!");
		System.out.println("Server Started ");
		// Register Node--
		startAndStopServerAndNode("java -Dwebdriver.chrome.driver=/home/discusit/Desktop/Manan/Chrome/33/chromedriver -jar selenium-server-standalone-3.7.1.jar -role node & echo $!");
		System.out.println("node Started");

		
		
		executesLinuxCommand(serverStart.getAbsolutePath());
		
		Thread.sleep(35000);
	}
*/
	/**
	 * Fetch Configuration From suite like url and seleniumPort etc.
	 * 
	 * @param testContext
	 * @throws IOException
	 */
	
	@BeforeTest(alwaysRun = true)
	public void fetchSuiteConfiguration(ITestContext testContext)throws IOException 
	{

		testUrl = testContext.getCurrentXmlTest().getParameter("url");
		System.out.println(testUrl);
		seleniumHub = testContext.getCurrentXmlTest().getParameter("selenium.host");
		seleniumHubPort = testContext.getCurrentXmlTest().getParameter("selenium.port");
		targetBrowser = testContext.getCurrentXmlTest().getParameter("browser");

	}

	/**
	 * Make Browser Launch from This method.
	 * 
	 * @param method
	 * @throws MalformedURLException
	 */
	
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method) throws MalformedURLException 
	{

		currentTest = method.getName(); // get Name of current test.

		URL remote_grid = new URL("http://" + seleniumHub + ":"	+ seleniumHubPort + "/wd/hub");

		String SCREENSHOT_FOLDER_NAME = "screenshots";
		String TESTDATA_FOLDER_NAME = "test_data";

		test_data_folder_path = new File(TESTDATA_FOLDER_NAME).getAbsolutePath();
		screenshot_folder_path = new File(SCREENSHOT_FOLDER_NAME).getAbsolutePath();

		DesiredCapabilities capability = null;
		
		// For Firefox Browser
		
		if (targetBrowser == null || targetBrowser.contains("firefox")) 
		{
			
			System.out.println("==firefox Driver==");
	
			System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
			
			FirefoxProfile profile = new FirefoxProfile();
			
			
			// Extra preference for Firefox
			profile.setPreference("dom.max_chrome_script_run_time", "999");
			profile.setPreference("dom.max_script_run_time", "999");
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.download.useDownloadDir", true);
			profile.setPreference("browser.download.manager.showWhenStarting",	false);
			//profile.setEnableNativeEvents(true); --- >>> Removing while upgrade selenium version to 3.6.0
			profile.setPreference("network.http.use-cache", false);
			
					
			capability = DesiredCapabilities.firefox();
			capability.setCapability("marionette", false);
			capability.setJavascriptEnabled(true);
			capability.setCapability(FirefoxDriver.PROFILE, profile);
			
			
					
		}

		// For Chrome Browser
		
		else if (targetBrowser.contains("chrome")) 
		{
			
			System.out.println("==chrome Driver==");
	
			//-------For without headless ---->>>>
										
			/*capability = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("disable-infobars");
			options.addArguments("disable-extensions");
			capability.setCapability(ChromeOptions.CAPABILITY, options);*/
			
						
			
			
			//-------For Headless ---->>>>
			
			 
             
			 capability=DesiredCapabilities.chrome();
			 ChromeOptions chromeOptions = new ChromeOptions(); 		 
			 //chromeOptions.addArguments("--headless"); 
			 chromeOptions.addArguments("--disable-gpu");
			 chromeOptions.addArguments("--user-agent=New User Agent");
			 chromeOptions.addArguments("--disable-infobars"); 
			 //chromeOptions.addArguments("window-size=2100x1800");
			 
			 
			 Map<String, Object> prefs = new HashMap<String, Object>(); 
			 prefs.put("credentials_enable_service", false); 
			 prefs.put("profile.password_manager_enabled", false); 
			 chromeOptions.setExperimentalOption("prefs", prefs); 
			 capability.setCapability(ChromeOptions.CAPABILITY,chromeOptions);
			
					

		}
		
		/*else if(targetBrowser.contains("phantom"))
		{
			capability = DesiredCapabilities.phantomjs();
			List<String> cliArgsCap = new ArrayList<String>();
			cliArgsCap.add("--load-images=false");
			cliArgsCap.add("--webdriver-loglevel=NONE");
			//cliArgsCap.add("--web-security=false");
			cliArgsCap.add("--ssl-protocol=any");
			cliArgsCap.add("--ignore-ssl-errors=true");
			capability.setJavascriptEnabled(true);
			capability.setCapability(CapabilityType.TAKES_SCREENSHOT, false);
			capability.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
			driver = new PhantomJSDriver(capability);
			driver.manage().window().setSize(new Dimension(1800, 1000));
						
		}*/
		
		// change Remote Grid
		
 		System.out.println(remote_grid);
 		
 		driver = new RemoteWebDriver(remote_grid, capability);
				
		driver.get(testUrl);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
		
	}

	/**
	 * Take Screenshot after Test Fail and attach it to Report.
	 * 
	 * @param testResult
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult)
	{
		try {
			String testName = testResult.getName();

			if (!testResult.isSuccess()) 
			{
				/* Print test result to Console */
				System.out.println("TEST FAILED - " + testName);
				System.out.println("ERROR MESSAGE: "	+ testResult.getThrowable());
				Reporter.setCurrentTestResult(testResult);

				//testResult.getMethod().getMethodName();
				/* Make a screenshot for test that failed */
				
				String screenshotName = testName + getCurrentTimeStampString();

				System.out.println(getCurrentTimeStampString());
				Reporter.log("<br> <b>Please look to the screenshot -</b>");
				makeScreenshot(driver, screenshotName);
			}
			else 
			{
				
				System.out.println("TEST PASSED - " + testName + "\n");
			}
			
			/*Reporter.log("<br/> Logout Successfully Performed.");
			driver.findElement(By.xpath("html/body/div[1]/nav/div/div[3]/div[2]/a[2]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//a[text()='Logout']")).click();*/
			
			driver.manage().deleteAllCookies();
			driver.quit();
		
		}
		catch (Throwable throwable)
		{

		}
	}
	
	/*@AfterSuite(alwaysRun=true)
	public void endServer() throws IOException, InterruptedException
	{
		// Stop the Server and Node Process.
		//startAndStopServerAndNode("pkill -f 'java -jar /home/discusit/Desktop/selenium-server-standalone-2.48.2.jar'");
		executesLinuxCommand(serverStop.getAbsolutePath());
	
	}*/
	
	/**
	 * Get Current Time Stamp For Screenshot name.
	 * 
	 * @return
	 */
	public  String getCurrentTimeStampString()
	{

		Date myDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String myDateString = sdf.format(myDate);
		return myDateString;
	}
	
	public static String getCurrentdateString()
	{
		
		Date myDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMM");
		String myDateString = sdf.format(myDate);
		return myDateString;
	}

	/**
	 * Takes screenshot and adds it to TestNG report.
	 * 
	 * @param driver
	 * WebDriver instance.
	 */
	
	public void makeScreenshot(WebDriver driver, String screenshotName) 
	{

		WebDriver augmentedDriver = new Augmenter().augment(driver);

		/* Take a screenshot */
		File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		String nameWithExtention = screenshotName + ".png";

		/* Copy screenshot to specific folder */
		try {

			String reportFolder = "test-output" + File.separator;
			String screenshotsFolder = "screenshots";
			File screenshotFolder = new File(reportFolder + screenshotsFolder);
			if (!screenshotFolder.getAbsoluteFile().exists()) 
			{
				screenshotFolder.mkdir();
			}
			FileUtils.copyFile(screenshot, new File(reportFolder+ File.separator + screenshotsFolder + File.separator+ nameWithExtention).getAbsoluteFile());
		} 
		catch (IOException e) 
		{
			Reporter.log("Failed to capture screenshot: " + e.getMessage());
		}
		// Add ScreenShot Link to Report
		Reporter.log(getScreenshotLink(nameWithExtention, nameWithExtention));
	}

	/**
	 * Method for Getting Link To TestNG Report of ScreenShot.
	 * 
	 * @param screenshot_name
	 * @param link_text
	 * @return
	 */
	public String getScreenshotLink(String screenshot_name, String link_text)
	{
		//for IP Address
		
		try
    	{
    		e = NetworkInterface.getNetworkInterfaces();
    		} 
    	catch (SocketException e1) 
    	{
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	}
    	    while(e.hasMoreElements())
    		  {
    		     NetworkInterface n = (NetworkInterface) e.nextElement();
    		     Enumeration ee = n.getInetAddresses();
    		     while (ee.hasMoreElements())
    		     {
    		         InetAddress i = (InetAddress) ee.nextElement();
    		         if(i.isSiteLocalAddress())
    		         {
    		        	 hostaddress=i.getHostAddress();
    		        	 System.out.println(hostaddress);
    		          	 break;
    		         }
    		     }
    		  }
		
		
		Reporter.log("<br><Strong><font color=#FF0000>--Failed</font></strong>");
		return "<a href='http://"+hostaddress+"/aia/screenshots/" + screenshot_name+ "' target='_new'>" + link_text + "</a>";
	}

	/**
	 * to Print Message in console
	 * 
	 * @param steplog
	 * @param message
	 * @return
	 */
	public static int log(int steplog, String message) {
		Reporter.log("<br />Step " + steplog + ":-" + message);
		steplog++;
		return steplog;
	}

	/**
	 * 
	 * Start Server and node process for selenium Grid.
	 * 
	 * @param argument
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void startAndStopServerAndNode(String argument) throws IOException,InterruptedException 
	{
		String[] command = { "/bin/sh", "-c", argument };
		try {
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();
			InputStream i = p.getInputStream();
			byte[] b = new byte[16];
			i.read(b, 0, b.length);
			// System.out.println(new String(b));

		} catch (Exception e) {
			System.out.println(e);
		}
			
	}
	
	
	

}
