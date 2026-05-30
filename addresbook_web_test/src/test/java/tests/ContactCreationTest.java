package tests;

import model.ContactData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;

public class ContactCreationTest extends TestBase {


    @ParameterizedTest
    @MethodSource("ContactData") //сгененрировали входящие параметры для теста
    public void CanCreateMultipleContact(ContactData contact) {
        app.contact().CreateContact(contact);
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
