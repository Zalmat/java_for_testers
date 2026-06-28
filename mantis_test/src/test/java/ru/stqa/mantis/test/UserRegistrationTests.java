package ru.stqa.mantis.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;
import ru.stqa.mantis.model.UserData;

import java.time.Duration;
import java.util.regex.Pattern;

public class UserRegistrationTests extends TestBase{


    @Test
    void canRegisterUser (){
        var user = CommonFunctions.randomString(8);
        var email = String.format("%s@localhost", user);
        app.jamesCli().addUser(email, "password");
        app.session().addNewUser(user, email);
        var messages = app.mail().receive(email, "password", Duration.ofSeconds(60));
        var text = messages.get(0).content();
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(text);
        String url = null;
        if (matcher.find()){
            url = text.substring(matcher.start(), matcher.end());
        }
        app.driver().get(url);
        app.session().finishedRegistration(user, "password");
        app.http().login(user, "password");
        Assertions.assertTrue(app.http().isLoggedIn());
    }

    @Test
    void canRegisterUserApi (){
        var user = CommonFunctions.randomString(8);
        var email = String.format("%s@localhost", user);
        app.jamesApi().addUser(email, "password");
        app.rest().addNewUser(new UserData()
                .withUserName(user)
                .withPassword("password")
                .withEmail(email)
                .withAccessLevel("reporter"));
        var messages = app.mail().receive(email, "password", Duration.ofSeconds(60));
        var text = messages.get(0).content();
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(text);
        String url = null;
        if (matcher.find()){
            url = text.substring(matcher.start(), matcher.end());
        }
        app.driver().get(url);
        app.session().finishedRegistration(user, "password");
        app.http().login(user, "password");
        Assertions.assertTrue(app.http().isLoggedIn());
    }
}