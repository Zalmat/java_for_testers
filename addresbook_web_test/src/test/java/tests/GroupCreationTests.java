package tests;

import common.CommonFunctions;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupCreationTests extends TestBase {
//Генерируем проход по трём полям
    public static List<GroupData> groupProvider() throws IOException {
        var result = new ArrayList<GroupData>();
//        for (var name: List.of("", "group name")){
//           for (var header : List.of("", "froup header")){
//               for (var footer : List.of("", "footer name")) {
//                   result.add(new GroupData()
//                           .withName(name)
//                           .withHeader(header)
//                           .withFooter(footer));
//               }
//           }
//        }

        //Реализация построчного чтения. Начало
        var json = "";
        try (var reader = new FileReader("groups.json");
            var breader = new BufferedReader(reader)
        ) {
            var line = breader.readLine();
            while (line != null ) {
                json = json + line;
                line = breader.readLine();
            }
        }
        //Реализация построчного чтения. Начало
        //var json = Files.readString(Paths.get("groups.json")); //Чтение файла целиком
        ObjectMapper mapper = new ObjectMapper();
        var value = mapper.readValue(json, new TypeReference<List<GroupData>>(){});
        result.addAll(value);
        return result;
    }
    public static Stream<GroupData> randomGroups() throws IOException {
        //Генерируем используя соплаеры
        Supplier<GroupData> randomGroup = () -> new GroupData()
                .withName(CommonFunctions.randomString(10))
                .withHeader(CommonFunctions.randomString(20))
                .withFooter(CommonFunctions.randomString(30));
        return Stream.generate(randomGroup).limit(3);
    }
//    @ParameterizedTest
//    @ValueSource(strings = {"group name", "group name"}) //фиксированная параметризация
//    public void CanCreateGroup(String name)  {
//        int groupCount = app.groups().getCount(); //Количество групп
//        app.groups().CreateGroup(new GroupData(name, "Хидер группы", "Футер группы"));
//        int newGroupCount = app.groups().getCount();
//        Assertions.assertEquals(groupCount +1,newGroupCount);
//    }

    @ParameterizedTest
    @MethodSource("randomGroups")
    //@MethodSource("groupProvider") //сгененрировали входящие параметры для теста
    public void CanCreateGroups(GroupData group)  {
        var oldGroups = app.hbn().getGroupList(); //Получения списка уже из БД
        app.groups().CreateGroup(group);
        var newGroups = app.hbn().getGroupList();
        //Строим список групп которые не встречались в старом
        var extrasGroups = newGroups.stream().filter(g -> ! oldGroups.contains(g)).toList();
        var newId = extrasGroups.get(0).id();

        var maxId = newGroups.get(newGroups.size()-1).id();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.add(group.withId(maxId));
        Assertions.assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedList));
    }

    @ParameterizedTest
    @MethodSource("negativeGroupProvider") //сгененрировали входящие параметры для теста
    public void CanNotCreateGroup(GroupData group)  {
        var oldGroups = app.hbn().getGroupList();
        app.groups().CreateGroup(group);
        var newGroups = app.hbn().getGroupList();
        Assertions.assertEquals(newGroups, oldGroups);
    }


    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData("", "group name'", "", "")));
        return result;
    }
}
