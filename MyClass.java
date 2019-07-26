package newpackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MyClass {
	static Properties prop = new Properties();

	static WebDriver driver = null;
	
	public static void main(String[] args) throws InterruptedException {
		File file = new File("C:\\Users\\609561780\\workspace\\newproject\\src\\newpackage\\datafile.properties");

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// load properties file
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// setting chromedriver
		System.setProperty("webdriver.chrome.driver", "C://SeleniumDrivers//chromedriver_win32//chromedriver.exe");
		driver = new ChromeDriver();
		login();
		mailBox();
		driver.close();
	}

	public static void login() {

		// maximizing the window
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		// opening the URL
		driver.get("https://www.gmail.com/");
		System.out.println("opened the URL");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		// to get and print the title
		System.out.println(driver.getTitle());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		// to set username in the input box
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(prop.getProperty("username"));
		System.out.println("Entered the username");
		driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// to click on the Next button
		driver.findElement(By.xpath("//*[@id='identifierNext']")).click();
		System.out.println("Clicked on the next button");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// to set password in the password box
		driver.findElement(By.xpath("//input[@class='whsOnd zHQkBf']")).sendKeys(prop.getProperty("password"));
		System.out.println("Entered the password");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// to click on the Next button
		driver.findElement(By.xpath("//*[@id='passwordNext']")).click();
		System.out.println("Clicked on the next button");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void mailBox() {
		// to store all unread emails
		List<WebElement> unreademail = driver.findElements(By.xpath("//*[@class='zF']"));
		String MyMailer = "Likkeshwaran";
		Boolean checkMailClicked = false;
		for (int i = 0; i < unreademail.size(); i++) {
			if (unreademail.get(i).isDisplayed()) {
				// If a particular unread mail matches the sender that we want
				// to click open,if yes click the mail
				if (unreademail.get(i).getText().equals(MyMailer)) {
					System.out.println("Yes we got mail from " + MyMailer);
					checkMailClicked = true;
					try {
						Thread.sleep(15000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("unreademail.get(i).getText()");
					unreademail.get(i).click();
					System.out.println("Clicked on the mail from" + MyMailer);
					break;
				} else if (i == (unreademail.size() - 1)) {
					System.out.println("No we dint receive an unread mail from" + MyMailer);
				}
			}
		}

		// If the sender mail is found and clicked open then check if a
		// particular line is present in the subject line
		if (checkMailClicked) {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			if (driver.findElement(By.xpath("//h2[@class='hP']")) != null) {
				WebElement subject = driver.findElement(By.xpath("//h2[@class='hP']"));
				String text = subject.getText();
				System.out.println("Subject of the mail is" + text);

				if (text.contains("2 hours to go")) {
					System.out.println("Found the matching string in the email");
				} else {
					System.out.println("Dint find a matching string in the email");
				}
			} else {
				System.out
						.println("Didnot print the subject since the mail from the particular sender was not received");
			}

		}
	}
}
