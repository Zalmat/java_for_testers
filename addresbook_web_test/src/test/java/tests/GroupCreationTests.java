package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

public class GroupCreationTests extends TestBase {
//Генерируем проход по трём полям
    public static List<GroupData> groupProvider() {
        var result = new ArrayList<GroupData>();
        for (var name: List.of("", "group name")){
           for (var header : List.of("", "froup header")){
               for (var footer : List.of("", "footer name")) {
                   result.add(new GroupData(name, header, footer));
               }
           }
        }
        for (int i = 0; i < 5; i++)
        {
            result.add( new GroupData(randomString(i * 10), randomString(i * 10), randomString(i * 10)) );
        }
        return result;
    }

//    @ParameterizedTest
//    @ValueSource(strings = {"group name", "group name'"}) //фиксированная параметризация
//    public void CanCreateGroup(String name)  {
//        int groupCpunt = app.groups().getCount(); //Количество групп
//        app.groups().CreateGroup(new GroupData(name, "Хидер группы", "Футер группы"));
//        int newGroupCount = app.groups().getCount();
//        Assertions.assertEquals(groupCpunt +1,newGroupCount);
//    }

    @ParameterizedTest
    @MethodSource("groupProvider") //сгененрировали входящие параметры для теста
    public void CanCreateMultipleGroups(GroupData group)  {
        int groupCount = app.groups().getCount();
        app.groups().CreateGroup(group);
        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount + 1,newGroupCount);
    }

    @ParameterizedTest
    @MethodSource("negativeGroupProvider") //сгененрировали входящие параметры для теста
    public void CanNotCreateGroup(GroupData group)  {
        int groupCount = app.groups().getCount();
        app.groups().CreateGroup(group);
        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount,newGroupCount);
    }


    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData( "group name'", "", "")));
        return result;
    }
}
