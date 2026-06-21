package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupRemovalTests extends TestBase {


    @Test
    public void canRemoveGroup() {
        if (app.groups().getCount() == 0) {
            app.groups().CreateGroup(new GroupData("", "Имя группы", "Заголовок группы", "Футер группы"));
        }
        //Используется принцип зполотого эталона
        var oldGroups = app.groups().getList(); //1.было
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size()); //2. Случайным образом выбираем элемент из списка
        app.groups().removeGroup(oldGroups.get(index)); //3. Удаляем выбранное
        var newGroups = app.groups().getList(); //4. Получаем новое состояние после удаления
        // 5. Создается модель/обьект ожидаемого результата
        var expectedList = new ArrayList<>(oldGroups); // копираем
        expectedList.remove(index); //удаляем из копии аналогично с шагом 3
        Assertions.assertEquals(newGroups,expectedList); //Сравниваем
    }

    @Test
    public void canRemoveAllGroupsAtOnce(){
        if (app.groups().getCount() == 0) {
            app.groups().CreateGroup(new GroupData("", "Имя группы", "Заголовок группы", "Футер группы"));
        }
        app.groups().removeAllGroups();
        Assertions.assertEquals(0, app.groups().getCount());
    }

}

