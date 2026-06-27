package tests;

import common.CommonFunctions;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactInfoTests extends TestBase{
    @Test
    void testPhones() {
        var id = app.jdbc().getAnyContactId();
        if ( id == null) {
            var newContact = new ContactData()
                    .withFirstname(CommonFunctions.randomString(10))
                    .withLastname(CommonFunctions.randomString(10))
                    .withAddress("Адрес:" +CommonFunctions.randomString(10))
                    .withEmail(CommonFunctions.randomString(10) + "@" + "mail.com")
                    .withEmail2(CommonFunctions.randomString(10) + "@" + "mail.com")
                    .withEmail3(CommonFunctions.randomString(10) + "@" + "mail.com")
                    .withHome("+7" + CommonFunctions.randomString(10))
                    .withMobile("+7" + CommonFunctions.randomString(10))
                    .withWork("+7" + CommonFunctions.randomString(10));

            app.contact().CreateContact(newContact);
        }
        var contacts = app.hbn().getContactList();
        var expected = contacts.stream().collect(Collectors.toMap(ContactData::id, contact ->
            Stream.of(contact.home(),contact.mobile(),contact.work())
                    .filter(s -> s != null && ! "".equals(s))
                    .collect(Collectors.joining("\n"))
        ));

        var phones  = app.contact().getPhones();
        Assertions.assertEquals(expected, phones);
    }

    @Test
    void canSeeContactDetails(){
        var id = app.jdbc().getAnyContactId();
        if (id == null) {
            var newContact = new ContactData()
                    .withFirstname(CommonFunctions.randomString(10))
                    .withLastname(CommonFunctions.randomString(10))
                    .withAddress("Адрес:" + CommonFunctions.randomString(10))
                    .withEmail(CommonFunctions.randomString(10) + "@mail.com")
                    .withEmail2(CommonFunctions.randomString(10) + "@mail.com")
                    .withEmail3(CommonFunctions.randomString(10) + "@mail.com")
                    .withHome("+7" + CommonFunctions.randomString(10))
                    .withMobile("+7" + CommonFunctions.randomString(10))
                    .withWork("+7" + CommonFunctions.randomString(10));

            app.contact().CreateContact(newContact);
        }
        var contacts = app.hbn().getContactList();
        var contact = contacts.get(0);
        var info = app.contact().getContactInfoForComparison(contact);
        var expectedPhones = Stream.of(info.editHome(), info.editMobile(), info.editWork())
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining("\n"));

        var expectedEmails = Stream.of(info.editEmail(), info.editEmail2(), info.editEmail3())
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining("\n"));

        Assertions.assertEquals(info.mainPhones(), expectedPhones, "Телефоны не совпадают");
        Assertions.assertEquals(info.mainEmail(), expectedEmails, "Email не совпадают");
        Assertions.assertEquals(info.mainAddress(), info.editAddress(), "Адрес не совпадает");
    }
}
