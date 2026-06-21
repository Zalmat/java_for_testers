package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ContactModificationTests extends TestBase{

    @Test
    void canModifyContact(){
        if(!app.contact().isContactPresent()) {
            app.contact().CreateContact(new ContactData().withFirstname("Firstname").withLastname("Lastname"));
        }
        var rnd = new Random();
        var oldContact = app.contact().getList();
        var index = rnd.nextInt(oldContact.size());
        var testData = new ContactData().withFirstname("modify Firstname");
        app.contact().modifyContact(oldContact.get(index), testData);
        var newContact = app.contact().getList();
        var expectedList = new ArrayList<>(oldContact);

        expectedList.set(index, testData.withId(oldContact.get(index).id()));
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id())); //сравниваем индентификаторы групп
        };
        newContact.sort(compareById);
        expectedList.sort(compareById);
        Assertions.assertEquals(newContact,expectedList); //Сравниваем
    }
}
