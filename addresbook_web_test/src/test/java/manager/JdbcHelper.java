package manager;

import model.ContactData;
import model.GroupData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcHelper extends HelperBase {
    public JdbcHelper(ApplicationManager manager) {super(manager);}

    public List<GroupData> getGroupList() {
        var groups = new ArrayList<GroupData>();  //1. Создаем пустой список
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root","");
             var statement = conn.createStatement();
             var result = statement.executeQuery(
                     "SELECT group_id, group_name, group_footer, group_header " +
                             "FROM group_list"))
        {

            while (result.next()) {
                groups.add(new GroupData()
                        .withId(result.getString("group_id"))
                        .withFooter(result.getString("group_footer"))
                        .withHeader(result.getString("group_header"))
                        .withName(result.getString("group_name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groups;
    }

    public void checkConsistency() {
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root","");
             var statement = conn.createStatement();
             var result = statement.executeQuery(
                     " SELECT * FROM `address_in_groups` ag " +
                             " LEFT JOIN addressbook ab ON ab.id = ag.id " +
                             " WHERE ab.id IS NULL"))
        {

            if (result.next()) {
                throw new IllegalStateException("БД повереждена");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public ContactData getRandomContactWithoutGroup() {
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
             var statement = conn.createStatement();
             var result = statement.executeQuery(
                     "SELECT ab.id, ab.firstname, ab.lastname, ab.address, ab.middlename " +
                             "FROM addressbook ab " +
                             "WHERE ab.id NOT IN (" +
                             "    SELECT DISTINCT id FROM address_in_groups" +
                             ") " +
                             "ORDER BY RAND() " +
                             "LIMIT 1")) {

            if (result.next()) {
                return new ContactData()
                        .withId(String.valueOf(result.getInt("id")))
                        .withFirstname(result.getString("firstname"))
                        .withLastname(result.getString("lastname"))
                        .withAddress(result.getString("address"))
                        .withMiddlename(result.getString("middlename"));
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isContactInAnyGroup(ContactData contact) {
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
             var statement = conn.createStatement();
             var result = statement.executeQuery(
                     "SELECT COUNT(*) as count FROM address_in_groups " +
                             "WHERE id = " + Integer.parseInt(contact.id()))) {

            if (result.next()) {
                return result.getInt("count") > 0;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Возвращаем ID групп которые нужно отвязать
    public List<Integer> getGroupIdsForContact(ContactData contact) {
        var groupIds = new ArrayList<Integer>();
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
             var statement = conn.createStatement();
             var result = statement.executeQuery(
                     "SELECT group_id FROM address_in_groups " +
                             "WHERE id = " + Integer.parseInt(contact.id()))) {

            while (result.next()) {
                groupIds.add(result.getInt("group_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groupIds;
    }
    //Костыль..
    public ContactData getContactByFirstnameLastname(String firstname, String lastname) {
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
             var statement = conn.createStatement();
             var result = statement.executeQuery(
                     "SELECT id, firstname, lastname, address, middlename " +
                             "FROM addressbook " +
                             "WHERE firstname = '" + firstname + "' " +
                             "AND lastname = '" + lastname + "'")) {

            if (result.next()) {
                return new ContactData()
                        .withId(String.valueOf(result.getInt("id")))
                        .withFirstname(result.getString("firstname"))
                        .withLastname(result.getString("lastname"))
                        .withAddress(result.getString("address"))
                        .withMiddlename(result.getString("middlename"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAnyContactId() {
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
             var statement = conn.createStatement();
             var result = statement.executeQuery(
                     "SELECT id FROM addressbook LIMIT 1")) {

            if (result.next()) {
                return result.getString("id"); 
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
