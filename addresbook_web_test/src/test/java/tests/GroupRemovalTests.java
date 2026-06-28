package tests;

import io.qameta.allure.Allure;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GroupRemovalTests extends TestBase {


    @Test
    public void canRemoveGroup() {
        Allure.step("Cheking precondition", step -> {
            if (app.hbn().getGroupCount() == 0) {
                app.hbn().CreateGroup(new GroupData("", "group name", "group hider", "group footer"));
            }
        });


        var oldGroups = app.hbn().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        app.groups().removeGroup(oldGroups.get(index));
        var newGroups = app.hbn().getGroupList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.remove(index);
        Comparator<GroupData> compareById = (o1, o2) ->
                Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        newGroups.sort(compareById);
        expectedList.sort(compareById);

        Allure.step("Validating results", step -> {
            Assertions.assertEquals(newGroups,expectedList);
        });

    }

    @Test
    public void canRemoveAllGroupsAtOnce(){
        if (app.hbn().getGroupCount() == 0) {
            app.hbn().CreateGroup(new GroupData("", "group name", "group hider", "group footer"));
        }
        app.groups().removeAllGroups();
        Assertions.assertEquals(0, app.hbn().getGroupCount());
    }
    @Test
    public void canRemoveAllGroupsAtOnceToDB(){
        if (app.hbn().getGroupCount() == 0) {
            app.hbn().CreateGroup(new GroupData("", "group name", "group hider", "group footer"));
        }
        app.groups().removeAllGroups();
        Assertions.assertEquals(0, app.hbn().getGroupCount());
    }

    @Test
    public void canRemoveGroupToDB() {
        if (app.hbn().getGroupCount() == 0) {
            app.hbn().CreateGroup(new GroupData("", "group name", "group hider", "group footer"));
        }
        //Используется принцип золотого эталона
        var oldGroups = app.hbn().getGroupList(); //1..было
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size()); //2. Случайным образом выбираем элемент из списка
        app.groups().removeGroup(oldGroups.get(index)); //3. Удаляем выбранное
        var newGroups = app.hbn().getGroupList(); //4. Получаем новое состояние после удаления
        // 5. Создается модель/обьект ожидаемого результата
        var expectedList = new ArrayList<>(oldGroups); //копируем
        expectedList.remove(index); //удаляем из копии аналогично с шагом 3
        Comparator<GroupData> compareById = (o1, o2) ->
                Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        newGroups.sort(compareById);
        expectedList.sort(compareById);
        Assertions.assertEquals(newGroups,expectedList); //Сравниваем
    }

}

