package mavenTesting;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Main {

    WebDriver driver;

    public Main() {
        //Firefox driver
        System.setProperty("webdriver.gecko.driver", "D:\\java\\webdriver\\geckodriver.exe");
        WebDriver object = new FirefoxDriver();
        driver = object;
        driver.manage().window().maximize();

        //Chrome driver
        //System.setProperty("webdriver.chrome.driver", "D:\\java\\webdriver\\chromedriver.exe");
        //WebDriver driver = new ChromeDriver();
    }

    public static void main (String[] args) throws InterruptedException {

        Main test = new Main();
        // Run tests
        test.addRemoveButton();
        test.checkbox();
        test.dropDown();
        test.entryAd();
        test.floatingMenu();
        test.inputs();

        test.afterTest();
    }

    @Test
    public void addRemoveButton() throws InterruptedException {
        driver.get("http://the-internet.herokuapp.com/add_remove_elements/");

        WebElement addButton = driver.findElement(By.cssSelector("div.example button[onClick*='addElement']"));
        addButton.click();
        addButton.click();
        addButton.click();

        Thread.sleep(2000);

        WebElement deleteButton = driver.findElement(By.cssSelector("div#elements button.added-manually:nth-child(3)"));
        deleteButton.click();
        deleteButton.click();
        deleteButton.click();
    }

    @Test
    public void checkbox() throws InterruptedException {
        driver.get("http://the-internet.herokuapp.com/checkboxes");

        WebElement form = driver.findElement((By.id("checkboxes")));
        List<WebElement> checkboxes = form.findElements(By.tagName("input"));

        //Check first checkbox
        checkboxes.get(0).click();
        Thread.sleep(1000);

        //UnCheck second checkbox
        checkboxes.get(1).click();

        //Check attributes of checkbox
        String checkBoxAttribute1 = checkboxes.get(0).getAttribute("checked");
        String checkBoxAttribute2 = checkboxes.get(1).getAttribute("checked");
        if (checkBoxAttribute1 == null) {
            System.out.println("Error! First checkbox is not selected");
        }

        if (checkBoxAttribute2 != null) {
            System.out.println("Second checkbox is selected");
        }
        driver.close();
    }

    @Test
    public void dropDown() throws InterruptedException {
        driver.get("http://the-internet.herokuapp.com/dropdown");


        WebElement dropdown = driver.findElement(By.id("dropdown"));
        // Crease select object
        Select select = new Select(driver.findElement(By.id("dropdown")));

        Thread.sleep(1000);

        // Select value
        select.selectByValue("1");

        Thread.sleep(2000);
        select.selectByIndex(2);

        String optionName = select.getFirstSelectedOption().getText();

        // Check if selected text is "Option 1"
        if (!optionName.equals("Option 1")) {
            System.out.println("Error! " + select.getFirstSelectedOption().getText() + " != Option 1" );
        }
    }

    @Test
    public void entryAd() throws InterruptedException {
        driver.get("http://the-internet.herokuapp.com/entry_ad");

        // This Thread sleep is important to wait button
        Thread.sleep(100);

        // Wait until modal window shown
        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("modal"))));

        WebElement adCloseButton = driver.findElement(By.className("modal-footer")).findElement((By.tagName("p")));

        Thread.sleep(3000);
        adCloseButton.click();

        Thread.sleep(1000);

        // Refresh page
        driver.navigate().refresh();

        // This Thread sleep is important to wait button
        Thread.sleep(2000);

        WebElement adCloseButtonForCheck = driver.findElement(By.id("modal"));

        // check if Ad element is displayed after reload
        if (adCloseButtonForCheck.isDisplayed()) {
            System.out.println("Error! Ad is shown again" );
        }
    }

    @Test
    public void floatingMenu() throws InterruptedException {
        driver.get("http://the-internet.herokuapp.com/floating_menu");

        // Define size of scroll abd default offset top of element
        int scrollHeight = 850;
        int defaultOffset = 37;

        Thread.sleep(1000);

        WebElement menuElement = driver.findElement(By.id("menu"));

        Thread.sleep(1000);

        // Scroll down
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("window.scrollBy(0," + scrollHeight + ")");

        Thread.sleep(1000);

        //Check if top position of menu is relative
        if (scrollHeight - defaultOffset != Integer.parseInt(menuElement.getAttribute("offsetTop"))) {
            System.out.println("Error! " + menuElement.getAttribute("offsetTop"));
        }

    }

    @Test
    public void inputs() throws InterruptedException {
        driver.get("http://the-internet.herokuapp.com/inputs");

        WebElement inputNumber = driver.findElement(By.className("example")).findElement(By.tagName("input"));
        Thread.sleep(1000);
        inputNumber.sendKeys("10asd");
        Thread.sleep(1000);

        //check if only numbers are accepted
        if (!inputNumber.getAttribute("value").equals("10")) {
            System.out.println("Error! Value should be 10");
        }
    }

    public void afterTest() {
        driver.close();
    }
}

