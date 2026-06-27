package tests;

import common.CommonFunctions;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {


    @ParameterizedTest
    @MethodSource("ContactData") //сгенерировали входящие параметры для теста
    public void CanCreateMultipleContact(ContactData contact) {
        var oldContact = app.hbn().getContactList();
        app.contact().CreateContact(contact);
        var newContact = app.hbn().getContactList();

        Comparator<ContactData> compareById = (o1, o2) ->
                Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));

        newContact.sort(compareById);

        var expectedList = new ArrayList<>(oldContact);
        String newId = newContact.get(newContact.size() - 1).id();

        var expectedContact = new ContactData()
                .withId(newId)
                .withFirstname(contact.firstname())
                .withMiddlename("")
                .withLastname(contact.lastname());
        expectedList.add(expectedContact);
        expectedList.sort(compareById);
        Assertions.assertEquals(newContact, expectedList);
    }

    @Test
    void canCreateContact() {
        var contact = new ContactData()
                .withFirstname(CommonFunctions.randomString(10))
                .withLastname(CommonFunctions.randomString(10))
                .withPhoto(randomFile("src/resources/images"));

        app.contact().CreateContact(contact);
    }

    //Генерируем проход по трём полям с добавлением через .with*** для исключения БДСМа

    public static List<ContactData> ContactData() throws IOException {
        var result = new ArrayList<ContactData>();
//        for (var firstname : List.of("", "firstname")) {
//            for (var middlename : List.of("", "middlename")) {
//                for (var lastname : List.of("", "lastname")) {
//                    result.add(new ContactData()
//                            .withFirstname(firstname)
//                            .withMiddlename(middlename)
//                            .withLastname(lastname).withPhoto(null));
//
//                }
//            }
//        }
//        for (int i = 0; i < 5; i++) {
//            result.add(new ContactData()
//                    .withFirstname(CommonFunctions.randomString(i * 10))
//                    .withMiddlename(CommonFunctions.randomString(i * 10))
//                    .withLastname(CommonFunctions.randomString(i * 10))
//                    .withPhoto(null));
//        }
//        return result;
        var json = Files.readString(Paths.get("contact.json"));
        ObjectMapper mapper = new ObjectMapper();
        var value = mapper.readValue(json, new TypeReference<List<ContactData>>(){});
        result.addAll(value);
        return result;
    }

    @Test
    void canCreateContactInGroup() {
        var contact = new ContactData()
                .withFirstname(CommonFunctions.randomString(10))
                .withLastname(CommonFunctions.randomString(10))
                .withPhoto(randomFile("src/resources/images"));

        if (app.hbn().getGroupCount() == 0) {
            app.hbn().CreateGroup(new GroupData("", "Имя группы", "Заголовок группы", "Футер группы"));
        }
        var group = app.hbn().getGroupList().get(0);

        var oldRelated = app.hbn().getContactsInGroup(group); //Получаем список контактов которые входят в заданную группу
        app.contact().CreateContact(contact, group);
        var newRelated = app.hbn().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() +1,newRelated.size());
    }

    @Test
    void canAddContactToGroup() {

        if (app.hbn().getGroupCount() == 0) {
            app.hbn().CreateGroup(new GroupData("", "Group", "Header", "Footer"));
        }
        var contact = app.jdbc().getRandomContactWithoutGroup();
        if (contact == null) {
            var newContact = new ContactData()
                    .withFirstname(CommonFunctions.randomString(10))
                    .withLastname(CommonFunctions.randomString(10));
            app.contact().CreateContact(newContact);
            //Получаем контакт
            contact = app.jdbc().getContactByFirstnameLastname(
                    newContact.firstname(), newContact.lastname()
            );
        }
        Assertions.assertFalse(app.jdbc().isContactInAnyGroup(contact));
        app.contact().addContactToGroup(contact);
        Assertions.assertTrue(app.jdbc().isContactInAnyGroup(contact));
    }


}

