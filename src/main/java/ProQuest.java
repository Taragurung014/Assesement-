import com.google.common.base.Strings;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProQuest {

    WebDriver driver;

    @Test
    public void Test1() throws IOException {

        // Set property to run the test using chrome driver
        System.setProperty("webdriver.chrome.driver", "src/main/chromedriver.exe");
        driver = new ChromeDriver();

        // Maximize the browser
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //Navigate to the url
        driver.navigate().to("https://www.google.com/");

        // Locate the search box
        WebElement searchBox = driver.findElement(By.xpath("//input[@name='q']"));

        //Type "ProQuest" in the search box
        searchBox.sendKeys("ProQuest");
        searchBox.sendKeys(Keys.RETURN);
        List<WebElement> titles = driver.findElements(By.xpath("//a/h3"));

        // Creating a new file to store the list of web elements

        File newFile = new File("result.txt");
        newFile.createNewFile();
        FileWriter writeFile = new FileWriter(newFile);

        // Using for loop to read the list of titles from the search result

        for (WebElement result:titles)
        {
            if(!Strings.isNullOrEmpty(result.getText()))

            {
                System.out.println("Title :" + result.getText());
                writeFile.write("Title :" + result.getText());
            }
        }

        writeFile.close();

    }

    @Test

    public void Test2() throws IOException {
        Test1();

        // Locating the proQuest link
        WebElement proQuest = driver.findElement(By.xpath("//h3[contains(text(),'EBooks and Technology')]"));
        proQuest.click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class=\" fa  fa-2  fa-search \"]"))).click();

        // Locating the search box in the web page
        WebElement search = driver.findElement(By.xpath("//li//input[@name='searchKeyword']"));

        // Typing QA in the search box

        search.sendKeys("QA");
        search.sendKeys(Keys.RETURN);

        // Taking screenshot of the result
        TakesScreenshot screenshot = ((TakesScreenshot)driver);
        File  screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
    }

    // Close the browser after executing the test.

    @AfterTest
    public void CloseBrowser()
    {
        driver.quit();
    }

}
