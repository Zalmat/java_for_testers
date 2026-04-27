package ru.stqa.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.geometry.figures.Rectangle;
import ru.stqa.geometry.figures.Square;

public class RectangleTests {

    @Test
        //Проверка исключения
    void cannotCreateRectangleWithNegativeSide(){
        try {
            new Rectangle(-5.0, 3.0);
            Assertions.fail();
        }
        catch (IllegalArgumentException exception) {
            //ok
        }

    }
}
