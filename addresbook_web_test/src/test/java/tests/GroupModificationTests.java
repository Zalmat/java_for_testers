package tests;

import common.CommonFunctions;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Set;

public class GroupModificationTests extends TestBase{

    @Test
    void canModifyGroup(){
        if (app.hbn().getGroupCount() == 0) {
            app.hbn().CreateGroup(new GroupData("", "Имя группы", "Заголовок группы", "Футер группы"));
        }
        var oldGroups = app.hbn().getGroupList(); //1.было
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        var testData = new GroupData().withName(CommonFunctions.randomString(11));
        app.groups().modifyGroup(oldGroups.get(index), testData);
        var newGroups = app.hbn().getGroupList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.set(index, testData.withId(oldGroups.get(index).id()));
        Assertions.assertEquals(Set.copyOf(newGroups),Set.copyOf(expectedList)); //Сравниваем множества
    }
}
