package ru.stqa.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.geometry.figures.Rectangle;
import ru.stqa.geometry.figures.Square;
import ru.stqa.geometry.figures.Triangle;



public class SquareTests {

    @Test
    void  canCalculateArea(){

        var s = new Square(5.0);
        double result = s.area();
        Assertions.assertEquals(25.0, s.area());
    }

    @Test
    void canCalculatePerimeter(){
        Assertions.assertEquals(20.0, new Square(5.0).perimenter());
    }
}
