package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URL;
import java.time.Duration;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases  { // Lets us read the data "extends ExcelDataProvider"
        ChromeDriver driver;
        SoftAssert softAssert = new SoftAssert();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        @Test(priority = 1, enabled = true)
        public void testCase01() throws Exception {
                System.out.println("Testcase01 started");
                // driver.get("https://www.youtube.com/");
                Boolean URL = driver.getCurrentUrl().contains("youtube");
                Assert.assertTrue(URL, "Youtube link is not correctly opened");

                 // Locate the About link at the bottom of the sidebar and Click on "About"
                By aboutLocator = By.xpath("//a[contains(text(), 'About')]");
                Wrappers.scrollToViewport(driver, aboutLocator);
                Wrappers.clickAW(driver, aboutLocator);

                // Print the message displayed on the screen
                By messageLocator = By.xpath("//section[contains(@class, 'about__content')]");
                Wrappers.scrollToViewport(driver, messageLocator);
                Wrappers.getDisplayedMessage(driver, messageLocator);
                System.out.println("Testcase01 ended");
        }

        @Test(priority = 2, enabled = true)
        public void testCase02() throws Exception {
                System.out.println("Testcase02 started");
                

                 // Navigate to the "Films" tab
                By filmsTab = By.xpath("//a[contains(@title, 'Films') or contains(@title, 'Movies')]");
                Wrappers.scrollToViewport(driver, filmsTab);
                Wrappers.clickAW(driver, filmsTab);

                 // Scroll to the extreme right within the "Top Selling" section
                String section = "Top selling";
                Wrappers.scrollToExtreme(driver, By.xpath("//span[contains(text(), '"+ section +"')]/ancestor::div[contains(@class, 'item-section')]//button[contains(@aria-label, 'Next')]"));

                // Last movie
                By lastMovie = By.xpath("(//span[contains(text(), '"+ section +"')]/ancestor::div[contains(@class, 'item-section')]//ytd-grid-movie-renderer)[last()]");

                
                By filmCertificationLocator = By.xpath("//ytd-grid-movie-renderer[contains(@class,'style-scope')][16]//ytd-badge-supported-renderer/div[2]/p");
                String filmCertification = Wrappers.getElementText(driver, lastMovie, filmCertificationLocator);
               
                softAssert.assertEquals(filmCertification, "U/A", "The movie certification is not marked as 'A'");

                By movieGenreLocator = By.xpath(".//span[contains(@class, 'metadata')]");
                String movieGenre = Wrappers.getElementText(driver, lastMovie, movieGenreLocator);
                
                softAssert.assertTrue(movieGenre.contains("Comedy") || movieGenre.contains("Indian cinema"), "The movie genre is neither 'Comedy' nor 'Animation'");
        
                softAssert.assertAll();

        }

        @Test(priority = 3, enabled = true)
        public void testCase03() throws InterruptedException {

                System.out.println("Testcase03 started");

                By musicTab = By.xpath("//a[contains(@title, 'Music')]");
                Wrappers.scrollToViewport(driver, musicTab);
                Wrappers.clickAW(driver, musicTab);

                By showmore = By.xpath("(//span[contains(text(),'Biggest Hits')]/ancestor::div[@id='dismissible']//button)[1]");
                Wrappers.scrollToExtreme(driver, showmore);
                
                String lastplaylist = driver.findElement(By.xpath("//span[text()='Bollywood Dance Hitlist']"))
                                .getText();
                                
                System.out.println(lastplaylist);
                WebElement songs = driver.findElement(By.xpath(
                                "//span[text()='Bollywood Dance Hitlist']/../../../../../../a/yt-collection-thumbnail-view-model/yt-collections-stack/div/div[3]/yt-thumbnail-view-model/yt-thumbnail-overlay-badge-view-model/yt-thumbnail-badge-view-model/badge-shape/div[2]"));

                String text = songs.getText().replaceAll("[^0-9]", "");
                int number = Integer.parseInt(text);
               
                softAssert.assertTrue(number <= 50, "Number is not less or equal to 50");
                softAssert.assertAll();
                System.out.println("Testcase03 ended");
                
        
                // Locate the first section of playlists
              /*   By firstSection = By.xpath("(//ytd-item-section-renderer)[1]");
                Wrappers.scrollToViewport(driver, firstSection);

                // Scroll to the extreme right within the first section
                Wrappers.scrollToExtreme(driver, By.xpath("(//ytd-item-section-renderer)[1]//button[contains(@aria-label, 'Next')]"));

                // Last playlist
                By lastPlaylist = By.xpath("((//ytd-item-section-renderer)[1]//ytd-compact-station-renderer)[last()]");

                // Print the name of the playlist
                By playlistNameLocator = By.xpath(".//h3");
                String playlistName = Wrappers.getElementText(driver, lastPlaylist, playlistNameLocator);
                System.out.println(playlistName);

                // Count the number of tracks listed in the playlist
                By numberOfTrackLocator = By.xpath(".//p[contains(@id, 'video-count')]");
                int numberOfTracks = Integer.parseInt(Wrappers.getElementText(driver, lastPlaylist, numberOfTrackLocator).replaceAll("[\\D]", ""));
                System.out.println("Count : "+numberOfTracks);

                // Soft assert whether the number of tracks listed is less than or equal to 50
                softAssert.assertTrue((numberOfTracks <= 50), "The number of tracks listed is more than 50");

                




               /* WebElement Music = wait.until(ExpectedConditions
                                .elementToBeClickable(By.xpath("//yt-formatted-string[text()='Music']")));
                Wrappers.clickOnBtn(driver, Music);
                Thread.sleep(3000);
                WebElement showmore = driver.findElement(
                                By.xpath("(//span[contains(text(),'Biggest Hits')]/ancestor::div[@id='dismissible']//button)[1]"));
                Wrappers.clickOnBtn(driver, showmore);

                String lastplaylist = driver.findElement(By.xpath("//span[text()='Bollywood Dance Hitlist']"))
                                .getText();
                System.out.println(lastplaylist);
                WebElement songs = driver.findElement(By.xpath(
                                "//span[text()='Bollywood Dance Hitlist']/../../../../../../a/yt-collection-thumbnail-view-model/yt-collections-stack/div/div[3]/yt-thumbnail-view-model/yt-thumbnail-overlay-badge-view-model/yt-thumbnail-badge-view-model/badge-shape/div[2]"));

                String text = songs.getText().replaceAll("[^0-9]", "");
                int number = Integer.parseInt(text);
                SoftAssert softAssert = new SoftAssert();
                softAssert.assertTrue(number <= 50, "Number is not less or equal to 50");
                */
        }

        @Test(priority = 4, enabled = true)
        public void testCase04() {
                System.out.println("Testcase04 started");
                By newsTab = By.xpath("//a[contains(@title, 'News')]");
                Wrappers.scrollToViewport(driver, newsTab);
                Wrappers.clickAW(driver, newsTab);

                // Locate the "Latest News Posts" section
                By latestNewsPosts = By.xpath("//span[contains(text(), 'Latest news post')]");
                Wrappers.scrollToViewport(driver, latestNewsPosts);

                // Retrieve and print the body and the number of likes for each of the first 3
                // news posts
                int numberOfNewsPosts = 3;
                By firstNNewsLocator = By.xpath(
                                "(//span[contains(text(), 'Latest news post')]/ancestor::ytd-rich-section-renderer//ytd-post-renderer)[position() <= "+ numberOfNewsPosts + "]");
                Wrappers.getBodyAndViewCount(driver, firstNNewsLocator);
                System.out.println("Testcase04 ended");
        }

        @Test(priority = 5, enabled = true, description = "Verify video views count", dataProvider = "excelData", dataProviderClass = ExcelDataProvider.class)
        public void TestCase05(String searchTerms) {
                System.out.println("Testcase05 started");
                // Search for the item
                By searchBox = By.xpath("//div[@id='center']//div/form/input");
                Wrappers.sendKeysAW(driver, searchBox, searchTerms);

                // Scroll through the search results until the total views for the videos reach
                // 10 crore
                long totalCount = 10_00_00_000;
                Wrappers.scrollTillVideoCountReaches(driver, totalCount);
                System.out.println("Testcase05 ended");
        }

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeMethod
        public void driverGet() {
                driver.get("https://www.youtube.com");
        }

        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                // driver.close();
                driver.quit();

        }
}