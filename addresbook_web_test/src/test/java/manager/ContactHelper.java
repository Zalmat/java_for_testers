package manager;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import model.ContactData;
import model.ContactInfoData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    /**
     * Создаёт новый контакт через UI и возвращает объект с заполненным ID,
     * полученным из базы данных.
     */
    @Step("Создаёт новый контакт через UI и возвращает объект с заполненным ID, полученным из базы данных.")
    public ContactData CreateContact(ContactData contact) {
        openContactPageCreateForm();
        fillContactForm(contact);
        submitContactCreation();
        returnToContactPage();

        return Allure.step("Найти созданный контакт по имени: " + contact.firstname() + " " + contact.lastname(), () -> {
            return findCreatedContact(contact);
        });
    }

    /**
     * Создаёт новый контакт с привязкой к группе через UI и возвращает объект
     * с заполненным ID.
     */
    @Step("Создать контакт в группе ")
    public ContactData CreateContact(ContactData contact, GroupData group) {
        openContactPageCreateForm();
        fillContactForm(contact);
        selectGroup(group);
        submitContactCreation();
        returnToContactPage();

        return findCreatedContact(contact);
    }

    @Step("Найти созданный контакт")
    private ContactData findCreatedContact(ContactData contact) {
        // Получаем все контакты из базы
        var allContacts = manager.hbn().getContactList();

        // Ищем контакт с максимальным ID (самый новый)
        return allContacts.stream()
                .max(Comparator.comparingInt(c -> Integer.parseInt(c.id())))
                .orElseThrow(() -> new RuntimeException("Не удалось найти созданный контакт"));
    }

    @Step
    public void removeContact(ContactData contact) {
        returnToContactPage();
        selectContact(contact);
        removeSelectedContact();
        returnToContactPage();
    }

    @Step
    private void removeSelectedContact() {
        click(By.name("delete"));
    }

    @Step
    private void selectContact(ContactData contact) {
        click(By.cssSelector(String.format("input[type='checkbox'][value='%s']", contact.id())));
    }

    public List<ContactData> getList() {
        var contacts = new ArrayList<ContactData>();
        var entries = manager.driver.findElements(By.cssSelector("tr[name='entry']"));
        for (var entry : entries) {
            var checkbox = entry.findElement(By.cssSelector("input[type='checkbox']"));
            String id = checkbox.getAttribute("value");
            var cells = entry.findElements(By.tagName("td"));
            String lastName = cells.get(1).getText();
            String firstName = cells.get(2).getText();
            contacts.add(new ContactData()
                    .withId(id)
                    .withLastname(lastName)
                    .withFirstname(firstName));
        }
        return contacts;
    }


    public boolean isContactPresent() {
        returnToContactPage();
        return manager.isElementPresent(By.name("selected[]"));
    }

    @Step
    public void modifyContact(ContactData contact, ContactData modifyContact) {
        returnToContactPage();
        selectContact(contact);
        initContactModification(contact);
        fillContactForm(modifyContact);
        submitContactModification();
        returnToContactPage();
    }

    private void submitContactModification() {
        click(By.cssSelector("input[name='update']"));
    }

    private void initContactModification(ContactData contact) {
        click(By.cssSelector(String.format("a[href='edit.php?id=%s']", contact.id())));
    }

    @Step
    public void addContactToGroup(ContactData contact) {
        returnToContactPage();
        selectContact(contact);
        selectFirstGroupToAdd();
        submitContactToGroup();
        returnToContactPage();
    }

    @Step
    private void selectFirstGroupToAdd() {
        var select = new Select(manager.driver.findElement(By.name("to_group")));
        var options = select.getOptions();
        if (options.size() > 1) {
            select.selectByIndex(1);
        }
    }

    @Step
    private void submitContactToGroup() {
        click(By.name("add"));
    }

    @Step
    public void removeContactFromGroup(ContactData contact, GroupData group) {
        returnToContactPage();
        selectGroupToShow(group);
        selectContact(contact);
        submitRemoveContactToGroup();
        returnToContactPage();
    }

    @Step
    private void
    selectGroupToShow(GroupData group) {
        var select = new Select(manager.driver.findElement(By.name("group")));
        select.selectByValue(group.id());
    }

    @Step
    private void submitRemoveContactToGroup() {
        click(By.name("remove"));
    }

    @Step
    public void openContactPageCreateForm() {
        click(By.linkText("add new"));
    }

    @Step
    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstname());
        type(By.name("middlename"), contact.middlename());
        type(By.name("lastname"), contact.lastname());
        type(By.name("nickname"), contact.nickname());
        type(By.name("title"), contact.title());
        type(By.name("company"), contact.company());
        type(By.name("address"), contact.address());
        type(By.name("home"), contact.home());
        type(By.name("mobile"), contact.mobile());
        type(By.name("work"), contact.work());
        type(By.name("email"), contact.email());
        type(By.name("email2"), contact.email2());
        type(By.name("email3"), contact.email3());
        type(By.name("homepage"), contact.homepage());
        if (contact.photo() != null && !contact.photo().isEmpty()) {
            attach(By.name("photo"), contact.photo());
        }
    }

    @Step
    private void submitContactCreation() {
        click(By.name("submit"));
    }

    private void returnToContactPage() {
        click(By.xpath("//ul//*[text()='home']"));
    }

    private void selectGroup(GroupData group) {
        new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
    }

    public String getPhones(ContactData contact) {
        return manager.driver.findElement(By.xpath(
                String.format("//input[@id='%s']/../..//td[6]", contact.id()))).getText();
    }
    public String getEmail(ContactData contact) {
        return manager.driver.findElement(By.xpath(
                String.format("//input[@id='%s']/../..//td[5]", contact.id()))).getText();
    }
    public String getAddress(ContactData contact) {
        return manager.driver.findElement(By.xpath(
                String.format("//input[@id='%s']/../..//td[4]", contact.id()))).getText();
    }

    public String getAddressEditPage() {
        return manager.driver.findElement(By.name("address")).getAttribute("value");
    }

    public String getHomeEditPage() {
        return manager.driver.findElement(By.name("home")).getAttribute("value");
    }

    public String getMobileEditPage() {
        return manager.driver.findElement(By.name("mobile")).getAttribute("value");
    }

    public String getWorkEditPage() {
        return manager.driver.findElement(By.name("work")).getAttribute("value");
    }

    public String getEmailEditPage() {
        return manager.driver.findElement(By.name("email")).getAttribute("value");
    }

    public String getEmail2EditPage() {
        return manager.driver.findElement(By.name("email2")).getAttribute("value");
    }

    public String getEmail3EditPage() {
        return manager.driver.findElement(By.name("email3")).getAttribute("value");
    }

    @Step
    public ContactInfoData getContactInfoForComparison(ContactData contact) {
        returnToContactPage();
        var mainPhones = getPhones(contact);
        var mainEmail = getEmail(contact);
        var mainAddress = getAddress(contact);
        initContactModification(contact);
        var editAddress = getAddressEditPage();
        var editHome = getHomeEditPage();
        var editMobile = getMobileEditPage();
        var editWork = getWorkEditPage();
        var editEmail = getEmailEditPage();
        var editEmail2 = getEmail2EditPage();
        var editEmail3 = getEmail3EditPage();
        returnToContactPage();
        
        return new ContactInfoData(
                mainPhones,
                mainEmail,
                mainAddress,
                editHome,
                editMobile,
                editWork,
                editEmail,
                editEmail2,
                editEmail3,
                editAddress
        );
    }

    public Map<String,String> getPhones() {
        var result = new HashMap<String,String>();
        List <WebElement> rows = manager.driver.findElements(By.name("entry"));
        for (WebElement row : rows) {
            var id = row.findElement(By.tagName("input")).getAttribute("id");
            var phones = row.findElements(By.tagName("td")).get(5).getText();
            result.put(id, phones);
        }
        return result;
    }
}