package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ContactRemovalTests extends TestBase{
    @Test
    public void canRemoveContact(){
        if(!app.contact().isContactPresent()) {
            app.contact().CreateContact(new ContactData().withFirstname("Фирст нейм для удаления").withLastname("А я томат"));
        }
        var rnd = new Random();
        var oldContact = app.hbn().getContactList(); // Получить первоначальный список контактов контактов
        var index = rnd.nextInt(oldContact.size()); //2. Случайным образом выбираем элемент из списка
        app.contact().removeContact(oldContact.get(index));
        var newContact = app.hbn().getContactList();;
        var expectedList = new ArrayList<>(oldContact);

        Comparator<ContactData> compareById = (o1, o2) ->
                Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        newContact.sort(compareById);
        expectedList.sort(compareById);
        expectedList.remove(index);
        Assertions.assertEquals(newContact,expectedList); //Сравниваем
    }
}
