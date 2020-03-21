package co.pragra.ca.hotel;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Write a code which should work on both
 * firefox or chrome. firefox or chrome should be configurable.
 *
 */


public class HomePage {
    WebDriver driver;

    @BeforeSuite
    @Parameters("browser")
    public void setUp(@Optional  String browser) {
        if(null==browser){
            browser = "CHROME";
        }
        if(browser.equalsIgnoreCase("CHROME")){
            System.setProperty("webdriver.chrome.driver", "/Users/atinsingh/Downloads/chromedriver");
            driver = new ChromeDriver();
        } else if(browser.equalsIgnoreCase("FIREFOX")){
            System.setProperty("webdriver.gecko.driver", "/Users/atinsingh/Downloads/geckodriver");
            driver = new FirefoxDriver();
        }
    }

    @BeforeTest
    public void setUp2() {
        driver.get("https://ca.hotels.com/");
    }


    @Test
    public void testHome() throws InterruptedException {

        WebElement search = driver.findElement(By.name("q-destination"));
        search.sendKeys("Auckland");
        search.sendKeys(Keys.ESCAPE);
        driver.findElement(By.name("q-localised-check-in")).click();
        Thread.sleep(2000);


        String checkin_date = "2020-2-26";
        String checkoutDate = "2020-3-15";


        driver.findElement(By.xpath("//tr//td[@data-date='" + checkin_date + "']/a")).click();

        driver.findElement(By.name("q-localised-check-out")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//tr//td[@data-date='" + checkoutDate + "']/a")).click();

        String nights = driver.findElement(By.xpath("//span[@id='qf-0q-nights']/span/span")).getText();
        Assert.assertEquals(20,Integer.parseInt(nights));


        WebElement noRooms = driver.findElement(By.id("qf-0q-rooms"));
        Select roomSelect = new Select(noRooms);

        roomSelect.selectByVisibleText("7");




        // driver.findElement(By.xpath("//tbody[@class='autosuggest-city']//tr[1]/td/div[@class='autosuggest-category-result']")).click();
        Thread.sleep(8000);
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
    }
}
