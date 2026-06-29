package manager;

import io.qameta.allure.Step;
import manager.hbm.ContactRecord;
import manager.hbm.GroupRecord;
import model.ContactData;
import model.GroupData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HibernateHelper extends HelperBase {
    private SessionFactory sessionFactory;

    public HibernateHelper(ApplicationManager manager) {
        super(manager);
        //Создание фабрики сессий
        sessionFactory = new Configuration()
                        .addAnnotatedClass(ContactRecord.class)
                        .addAnnotatedClass(GroupRecord.class)
                        .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:mysql://localhost/addressbook?zeroDateTimeBehavior=convertToNull")
                        .setProperty(AvailableSettings. JAKARTA_JDBC_USER, "root")
                        .setProperty(AvailableSettings. JAKARTA_JDBC_PASSWORD, "")
                        // Create a new SessionFactory
                        .buildSessionFactory();
    }
    // Аналог подхода convertContactList. Краткий варинат.
    static List<GroupData> convertList(List<GroupRecord> records) {
         return records.stream().map(HibernateHelper::convert).collect(Collectors.toList());
    }

    static List<ContactData> convertContactList(List<ContactRecord> records) {
        List<ContactData> result = new ArrayList<>();
        for (var record : records){
            result.add(convert(record));
        }
        return result;
    }

    @Step
    private static ContactData convert(ContactRecord record){
        return new ContactData()
                .withId("" + record.id)
                .withFirstname(record.firstname != null ? record.firstname : "")
                .withLastname(record.lastname != null ? record.lastname : "")
                .withAddress(record.address != null ? record.address : "")
                .withMiddlename(record.middlename != null ? record.middlename : "")
                .withNickname(record.nickname != null ? record.nickname : "")
                .withTitle(record.title != null ? record.title : "")
                .withCompany(record.company != null ? record.company : "")
                .withHome(record.home != null ? record.home : "")
                .withMobile(record.mobile != null ? record.mobile : "")
                .withWork(record.work != null ? record.work : "")
                .withFax(record.fax != null ? record.fax : "")
                .withEmail(record.email != null ? record.email : "")
                .withEmail2(record.email2 != null ? record.email2 : "")
                .withEmail3(record.email3 != null ? record.email3 : "")
                .withHomepage(record.homepage != null ? record.homepage : "");
    }

    @Step
    private static GroupData convert(GroupRecord record) {
        return new GroupData("" + record.id, record.name, record.header, record.footer);
    }

    @Step
    private static GroupRecord convert(GroupData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new GroupRecord(Integer.parseInt(id), data.name(), data.hider(), data.footer());
    }

    @Step
    private static ContactRecord convert(ContactData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new ContactRecord(
                Integer.parseInt(id),
                data.firstname() != null ? data.firstname() : "",
                data.lastname() != null ? data.lastname() : "",
                data.address() != null ? data.address() : "",
                data.middlename() != null ? data.middlename() : "",
                data.nickname() != null ? data.nickname() : "",
                data.company() != null ? data.company() : "",
                data.title() != null ? data.title() : "",
                data.home() != null ? data.home() : "",
                data.mobile() != null ? data.mobile() : "",
                data.work() != null ? data.work() : "",
                data.fax() != null ? data.fax() : "",
                data.email() != null ? data.email() : "",
                data.email2() != null ? data.email2() : "",
                data.email3() != null ? data.email3() : "",
                data.homepage() != null ? data.homepage() : ""

        );
    }
    @Step
    public List<GroupData> getGroupList(){
        return convertList(sessionFactory.fromSession(session -> {
            //"from GroupRecord" это не название таблицы, а название класса
            return session.createQuery("from GroupRecord", GroupRecord.class).list();
        }));
    }
    @Step
    public List<ContactData> getContactList() {
        return convertContactList(sessionFactory.fromSession(session -> {
            return session.createQuery("from ContactRecord", ContactRecord.class).list();
        }));
    }
    @Step
    public long getGroupCount() {
        return sessionFactory.fromSession(session -> {
            //"from GroupRecord" это не название таблицы, а название класса
            return session.createQuery("select count (*) from GroupRecord", Long.class).getSingleResult();
        });
    }
    @Step
    public long getContactCount() {
        return sessionFactory.fromSession(session -> {
            //"from GroupRecord" это не название таблицы, а название класса
            return session.createQuery("select count (*) from ContactRecord", Long.class).getSingleResult();
        });
    }

    @Step
    public void CreateGroup(GroupData groupData) {

        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convert(groupData));
            session.getTransaction().commit();
        });
    }
    @Step
    public void CreateContact(ContactData contactData) {

        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convert(contactData));
            session.getTransaction().commit();
        });
    }
    @Step
    public List<ContactData> getContactsInGroup(GroupData group) {
        return sessionFactory.fromSession(session -> {
            return convertContactList(session.find(GroupRecord.class, group.id()).contacts); //в новой версии вместо get(), теперь find()
        });
    }


}
