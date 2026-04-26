package ru.stqa.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.geometry.figures.Triangle;

public class TriangleTests {
    @Test
    void canTrianglePerimeter(){
        Assertions.assertEquals(42.0, new Triangle(13,14,15).perimeter());
    }

    @Test
    void canTriangleArea(){
        Assertions.assertEquals(84.0, new Triangle(13,14,15).area());
    }
}
