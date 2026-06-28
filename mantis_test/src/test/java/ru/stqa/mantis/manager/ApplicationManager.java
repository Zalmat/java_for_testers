package ru.stqa.mantis.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

public class ApplicationManager {
    protected WebDriver driver;
    private String string;
    private Properties properties;
    private SessionHelper sessionHelper;

    public void init(String browser, Properties properties) {
        this.string = browser;
        this.properties = properties;

    }

    public WebDriver driver(){
        if (driver == null){
            if ("firefox".equals(string)){
                driver = new FirefoxDriver();
            } else if ("chrome".equals(string)){
                driver = new ChromeDriver();
            } else if ("edge".equals(string)){
                driver = new EdgeDriver();
            } else {
                throw new IllegalArgumentException(String.format("Неизвестный браузер: %s", string));
            }
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit)); //заменили driver.quit();
            driver.get(properties.getProperty("web.baseUrl"));
            driver.manage().window().maximize(); //Было driver.manage().window().setSize(new Dimension(1920, 1080));
        }
        return driver;
    }

    public SessionHelper session(){
        if (sessionHelper == null) {
            sessionHelper = new SessionHelper(this);
        }
        return sessionHelper;
    }

}
