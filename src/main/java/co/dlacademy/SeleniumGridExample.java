package co.dlacademy;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.net.URL;

public class SeleniumGridExample {
    private WebDriver driver;

    @Parameters("browser")
    @BeforeMethod
    public void setup(String browser) throws Exception {

        MutableCapabilities capabilities;

        if (browser.equalsIgnoreCase("chrome")) {
            capabilities = new MutableCapabilities();
            capabilities.setCapability("browserName", "chrome");
        } else if (browser.equalsIgnoreCase("firefox")) {
            capabilities = new MutableCapabilities();
            capabilities.setCapability("browserName", "firefox");
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions edgeOptions = new EdgeOptions();
            capabilities = edgeOptions;
        } else {
            throw new IllegalArgumentException("Navegador no válido");
        }


        URL hubUrl = new URL("http://localhost:4444/wd/hub");

        // Crea una instancia remota del controlador de Selenium conectándose al Selenium Hub en Docker
        driver = new RemoteWebDriver(hubUrl, capabilities);
    }

    @Test
    public void testExample() throws InterruptedException {

        driver.get("https://www.saucedemo.com/");

        WebElement campoUsuario = driver.findElement(By.xpath("//input[@id='user-name']"));
        WebElement campoPassword = driver.findElement(By.id("password"));
        WebElement botonLogin = driver.findElement(By.id("login-button"));

        campoUsuario.sendKeys("standard_user");
        campoPassword.sendKeys("secret_sauce");
        botonLogin.click();

        WebElement tituloHomePage = driver.findElement(By.xpath("//span[contains(.,'Products')]"));

        Assert.assertEquals("Products", tituloHomePage.getText());
        driver.close();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}