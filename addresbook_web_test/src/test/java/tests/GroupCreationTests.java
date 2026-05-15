package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void CanCreateGroup()  {
        app.groups().CreateGroup(new GroupData("Имя группы", "Хидер группы", "Футер группы"));

    }

    @Test
    public void CanCreateGroupWidthEmptyName()  {
        app.groups().CreateGroup(new GroupData());

    }


    @Test
    public void CanCreateGroupWidthNameOnly()  {
        app.groups().CreateGroup(new GroupData().withName("some name"));
    }
}
