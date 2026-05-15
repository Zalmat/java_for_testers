package tests;

import manager.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {

    protected static ApplicationManager app;

    @BeforeEach
    public void setUp() {
        //Если инициализация ещё не выполнялась, инициализируем
        if (app == null) {
            app = new ApplicationManager();
        }
        app.init(System.getProperty("browser","firefox")); //Вызываем через мозилу, если не запрошено иное
    }

}
