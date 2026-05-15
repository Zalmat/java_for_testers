package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupModificationTests extends TestBase{

    @Test
    void canModifyGroup(){
        if (!app.groups().isGroupPresent()) {
            app.groups().CreateGroup(new GroupData("Имя группы", "Заголовок группы", "Футер группы"));
        }
        app.groups().modifyGroup(new GroupData().withName("modify name"));
    }
}
