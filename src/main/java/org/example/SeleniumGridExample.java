package org.example;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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
        browser = "edge";
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

        // Abre la página de ejemplo
        driver.get("https://www.google.com");

        // Busca el campo de búsqueda y escribe algo
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium Grid");

        // Envía el formulario de búsqueda
        searchBox.submit();

        // Espera unos segundos para que los resultados se carguen
        Thread.sleep(3000);

        // Imprime el título de la página de resultados
        System.out.println("Título de la página de resultados: " + driver.getTitle());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}