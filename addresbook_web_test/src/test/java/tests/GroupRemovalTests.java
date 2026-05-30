package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupRemovalTests extends TestBase {


    @Test
    public void canRemoveGroup() {
        if (app.groups().getCount() == 0) {
            app.groups().CreateGroup(new GroupData("Имя группы", "Заголовок группы", "Футер группы"));
        }
        int groupCpunt = app.groups().getCount(); //Количество групп
        app.groups().removeGroup();
        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCpunt - 1,newGroupCount); //Проверяем, что новое количество групп на еденицу меньше

    }

    @Test
    public void canRemoveAllGroupsAtOnce(){
        if (app.groups().getCount() == 0) {
            app.groups().CreateGroup(new GroupData("Имя группы", "Заголовок группы", "Футер группы"));
        }
        app.groups().removeAllGroups();
        Assertions.assertEquals(0, app.groups().getCount());
    }

}

