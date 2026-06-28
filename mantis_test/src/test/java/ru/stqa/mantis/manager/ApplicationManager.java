package ru.stqa.mantis.manager;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Properties;

public class ApplicationManager {

    private WebDriver driver;
    protected WebDriverWait wait;
    private String string;
    private Properties properties;
    private SessionHelper sessionHelper;
    private HttpSessionHelper httpSessionHelper;
    private JamesCliHelper jamesCliHelper;
    private MailHelper mailHelper;
    private JamesApiHelper jamesApiHelper;
    private RestApiHelper restApiHelper;
    private SoapApiHelper soapApiHelper;


    public void init(String browser, Properties properties) {
        this.string = browser;
        this.properties = properties;

    }

    public WebDriver driver() {
        if (driver == null){
            if ("firefox".equals(string)) {
                driver = new FirefoxDriver();
            } else if ("chrome".equals(string)) {
                driver = new ChromeDriver();
            } else if ("safari".equals(string)) {
                driver = new SafariDriver();
            } else {
                throw new IllegalArgumentException(String.format("Unknow browser %f", string));
            }
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            driver.get(properties.getProperty("web.baseUrl"));
            driver.manage().window().setSize(new Dimension(1400, 816));
        }
        return driver;
    }

    public SessionHelper session(){
        if (sessionHelper == null){
            sessionHelper = new SessionHelper(this);
        }
        return sessionHelper;
    }

    public HttpSessionHelper http() {
        if (httpSessionHelper == null){
            httpSessionHelper = new HttpSessionHelper(this);
        }
        return httpSessionHelper;
    }

    public JamesCliHelper jamesCli() {
        if (jamesCliHelper == null){
            jamesCliHelper = new JamesCliHelper(this);
        }
        return jamesCliHelper;
    }

    public MailHelper mail() {
        if (mailHelper == null){
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }


    public JamesApiHelper jamesApi() {
        if (jamesApiHelper == null){
            jamesApiHelper = new JamesApiHelper(this);
        }
        return jamesApiHelper;
    }

    public RestApiHelper rest() {
        if (restApiHelper == null){
            restApiHelper = new RestApiHelper(this);
        }
        return restApiHelper;
    }

    public SoapApiHelper soap() {
        if (soapApiHelper == null){
            soapApiHelper = new SoapApiHelper(this);
        }
        return soapApiHelper;
    }

    public String property(String name){
        return properties.getProperty(name);
    }
}