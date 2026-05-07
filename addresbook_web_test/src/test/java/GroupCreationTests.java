import org.junit.jupiter.api.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void CanCreateGroup()  {
        OpenGroupsPage();
        CreateGroup("Имя группы", "Хидер группы", "Футер группы");

    }

    @Test
    public void CanCreateGroupWidthEmptyName()  {
        OpenGroupsPage();
        CreateGroup("", "", "");

    }
}
