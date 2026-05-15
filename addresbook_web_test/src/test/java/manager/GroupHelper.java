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
        removeSelectedGroup();
        returnToGroupsPage();
    }

    private void removeSelectedGroup() {
        click(By.name("delete"));
    }

    public boolean isGroupPresent() {
        OpenGroupsPage();
        return manager.isElementPresent(By.name("selected[]"));
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
}
