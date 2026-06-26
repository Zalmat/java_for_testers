package tests;

import common.CommonFunctions;
import model.ContactData;
import model.GroupData;
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

    @Test
    void canRemoveContactFromGroup() {
        if (app.hbn().getGroupCount() == 0) {
            app.hbn().CreateGroup(new GroupData("", "Group", "Header", "Footer"));
        }
        var groups = app.hbn().getGroupList();

        // 2. Ищем или создаем контакт
        var contact = app.jdbc().getRandomContactWithoutGroup();
        if (contact == null) {
            var newContact = new ContactData()
                    .withFirstname(CommonFunctions.randomString(10))
                    .withLastname(CommonFunctions.randomString(10));
            contact = app.contact().CreateContact(newContact);

        }
        if (!app.jdbc().isContactInAnyGroup(contact)) {
            app.contact().addContactToGroup(contact);
        }
        for (var g : groups) {
            app.contact().removeContactFromGroup(contact, g);
        }
        var groupIdsAfterRemove = app.jdbc().getGroupIdsForContact(contact);
        Assertions.assertTrue(groupIdsAfterRemove.isEmpty(),
                "Контакт всё еще состоит в группах!");
    }

}
