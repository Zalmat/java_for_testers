package manager;

import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase{

    public  ContactHelper(ApplicationManager contact) {
        super(contact);
    }


    public void CreateContact(ContactData contact) {
        OpenContactPageCreateForm();
        fillContactForm(contact);
        submitContactCreation();
        returnToContactPage();
    }

    public void removeContact(ContactData contact){
        returnToContactPage();
        SelectContact(contact);
        removeSelectedContact();
        returnToContactPage();
    }
    private void removeSelectedContact() {
        click(By.name("delete"));
    }

    private void SelectContact(ContactData contact) {
        click(By.cssSelector(String.format("input[type='checkbox'][value='%s']", contact.id())));
    }

    public void OpenContactPageCreateForm() {

            click(By.linkText("add new"));

    }

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

    private void submitContactCreation() {
        click(By.name("submit"));
    }

    private void returnToContactPage(){
        //click(By.name("home"));
        //click(By.xpath("/html/body/div[1]/div[3]/ul/li[1]/a"));
        click(By.xpath("//ul//*[text()='home']"));

    }
    public boolean isContactPresent() {
        //OpenContactPageCreateForm();
        returnToContactPage();
        return manager.isElementPresent(By.name("selected[]"));
    }

    public List<ContactData> getList() {
        var contact = new ArrayList<ContactData>();
        var entrys = manager.driver.findElements(By.cssSelector("tr[name='entry']"));
        for (var entry : entrys) {
            var checkbox = entry.findElement(By.cssSelector("input[type='checkbox']"));
            String id = checkbox.getAttribute("value");
            var str = entry.findElements(By.tagName("td"));
            String lastName = str.get(1).getText();
            String firstName = str.get(2).getText();
            contact.add(new ContactData()
                    .withId(id)
                    .withLastname(lastName)
                    .withFirstname(firstName));
        }
        return contact;
    }
    public void modifyContact(ContactData contact, ContactData modifyContact) {
        returnToContactPage();
        //OpenContactPageCreateForm();
        SelectContact(contact);
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
}
