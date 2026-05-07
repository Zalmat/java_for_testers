import org.junit.jupiter.api.Test;

public class GroupRemovalTests extends TestBase {


    @Test
    public void canRemoveGroup() {
        OpenGroupsPage();
        if (!isGroupPresent()) {
            CreateGroup("Имя группы", "Заголовок группы", "Футер группы");
        }
        removeGroup();

    }

}

