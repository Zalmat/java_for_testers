package manager;

import model.ContactData;
import org.openqa.selenium.By;

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

    public void removeContact(){
        returnToContactPage();
        SelectContact();
        removeSelectedContact();
        returnToContactPage();
    }
    private void removeSelectedContact() {
        click(By.name("delete"));
    }

    private void SelectContact() {
        click(By.name("selected[]"));
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
        OpenContactPageCreateForm();
        return manager.isElementPresent(By.name("selected[]"));
    }
}
