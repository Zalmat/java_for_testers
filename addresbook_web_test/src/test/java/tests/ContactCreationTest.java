package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {


    @ParameterizedTest
    @MethodSource("ContactData") //сгенерировали входящие параметры для теста
    public void CanCreateMultipleContact(ContactData contact) {
        var oldContact = app.contact().getList();
        app.contact().CreateContact(contact);
        var newContact = app.contact().getList();

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

    //Генерируем проход по трём полям с добавлением через .with*** для исключения БДСМа

    public static List<ContactData> ContactData() {
        var result = new ArrayList<ContactData>();
        for (var firstname : List.of("", "firstname")) {
            for (var middlename : List.of("", "middlename")) {
                for (var lastname : List.of("", "lastname")) {
                    result.add(new ContactData()
                            .withFirstname(firstname)
                            .withMiddlename(middlename)
                            .withLastname(lastname));
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            result.add(new ContactData()
                    .withFirstname(randomString(i * 10))
                    .withMiddlename(randomString(i * 10))
                    .withLastname(randomString(i * 10)));
        }
        return result;
    }

}
