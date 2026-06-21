package manager;

import model.GroupData;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends HelperBase{

    public  GroupHelper(ApplicationManager manager) {
        super(manager);
    }

    public void OpenGroupsPage() {
        if (!manager.isElementPresent(By.name("new"))) {
            click(By.linkText("groups"));
        }
    }


    public void modifyGroup(GroupData group, GroupData modifyGroup) {
        OpenGroupsPage();
        selectGroup(group);
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

    public void removeGroup(GroupData group) {
        OpenGroupsPage();
        selectGroup(group);
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

    private void selectGroup(GroupData group) {
        click(By.cssSelector(String.format("input[value='%s']",group.id())));
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

    public List<GroupData> getList() {
        var groups = new ArrayList<GroupData>();
        var spans = manager.driver.findElements(By.cssSelector("span.group")); //ищем через css
        for (var span : spans) {
            var name = span.getText(); //Вытаскиваем текст из поля
            var checkbox = span.findElement(By.name("selected[]")); //находим чекбокс! ОДИН потому findElement без S
            var id = checkbox.getAttribute("value");
            groups.add(new  GroupData().withId(id).withName(name));
        }

        return groups;
    }
}
