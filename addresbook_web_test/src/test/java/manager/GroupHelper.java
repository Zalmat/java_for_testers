package manager;

import model.GroupData;
import org.openqa.selenium.By;

public class GroupHelper extends HelperBase{

    public  GroupHelper(ApplicationManager manager) {
        super(manager);
    }

    public void OpenGroupsPage() {
        if (!manager.isElementPresent(By.name("new"))) {
            click(By.linkText("groups"));
        }
    }


    public void modifyGroup(GroupData modifyGroup) {
        OpenGroupsPage();
        selectGroup();
        initGroupModification();
        fillGroupForm(modifyGroup);
        submitGroupModification();
        returnToGroupsPage();
    }

    public void CreateGroup(GroupData group) {
        OpenGroupsPage();
        initGroupCreation();
        fillGroupForm(group);
        submitGroupCtreation();
        returnToGroupsPage();
    }

    private void submitGroupCtreation() {
        click(By.name("submit"));
    }



    private void initGroupCreation() {
        click(By.name("new"));
    }

    public void removeGroup() {
        OpenGroupsPage();
        selectGroup();
        removeSelectedGroups();
        returnToGroupsPage();
    }

    private void removeSelectedGroups() {
        click(By.name("delete"));
    }


    private void submitGroupModification() {
        click(By.name("update"));
    }

    private void fillGroupForm(GroupData group) {
        type(By.name("group_name"), group.name());
        type(By.name("group_header"), group.hider());
        type(By.name("group_footer"), group.footer());
    }

    private void initGroupModification() {
        click(By.name("edit"));
    }

    private void selectGroup() {
        click(By.name("selected[]"));
    }
    private void returnToGroupsPage() {
        click(By.linkText("group page"));
    }

    public int getCount() {
        OpenGroupsPage(); //открыть группу
        return manager.driver.findElements(By.name("selected[]")).size(); //найти и вернуть кол-во чекбоксов

    }

    public void removeAllGroups() {
        OpenGroupsPage();
        selectAllGroups();
        removeSelectedGroups();
    }

    private void selectAllGroups() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox : checkboxes){ //перебираем найденные элименты
            checkbox.click();
        }
    }
}
