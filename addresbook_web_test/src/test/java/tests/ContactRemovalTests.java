package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase{
    @Test
    public void canRemoveContact(){
        if(!app.contact().isContactPresent()) {
            app.contact().CreateContact(new ContactData().withFirstname("Фирст нейм для удаления"));
        }
        app.contact().removeContact();
    }
}
