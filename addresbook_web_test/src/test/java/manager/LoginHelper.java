package manager;

import org.openqa.selenium.By;

public class LoginHelper extends HelperBase{



    public  LoginHelper(ApplicationManager manager) {
        super(manager);
    }

    void login(String user, String password) {
        type(By.name("user"), user);
        type(By.name("pass"), password);
        click(By.xpath("//input[@value=\'Login\']"));
        /* Конструкция до упрощения
        manager.driver.findElement(By.name("user")).sendKeys(user);
        manager.driver.findElement(By.name("pass")).sendKeys(password);
        manager.driver.findElement(By.xpath("//input[@value=\'Login\']")).click();
       */
    }
}
