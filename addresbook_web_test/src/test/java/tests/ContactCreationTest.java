package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactCreationTest extends TestBase{

    @Test
    public void canCreateContact() {
        app.contact().CreateContact(new ContactData().withFirstname("ТЕЕЕЕСтЬ"));
    }
}
