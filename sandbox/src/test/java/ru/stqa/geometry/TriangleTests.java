package ru.stqa.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.geometry.figures.Rectangle;
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

    //Негативный тест. Проверка стороны A
    @Test
    void cannotTriangleWidthNegativeSideA()
    {
        try {
            new Triangle(-13,14,15 );
            Assertions.fail();
        }
        catch (IllegalArgumentException exception) {
            //ok
        }
    }
    //Негативный тест. Проверка стороны B
    @Test
    void cannotTriangleWidthNegativeSideB()
    {
        try {
            new Triangle(13,-14,15 );
            Assertions.fail();
        }
        catch (IllegalArgumentException exception) {
            //ok
        }
    }
    //Негативный тест. Проверка стороны C
    @Test
     void cannotTriangleWidthNegativeSideC()
    {
        try {
            new Triangle(13,14,-15 );
            Assertions.fail();
        }
        catch (IllegalArgumentException exception) {
            //ok
        }
    }
    //Негативный тест. Проверка на неравенство
    @Test
    void cannotTriangleInequalityOfSidesAB()
    {
        try {
            new Triangle(3,3,7);
                    Assertions.fail();
        } catch (IllegalArgumentException exception){
            //ok
        }
    }

    //Негативный тест. Проверка на неравенство
    @Test
    void cannotTriangleInequalityOfSidesBC()
    {
        try {
            new Triangle(7,3,3);
            Assertions.fail();
        } catch (IllegalArgumentException exception){
            //ok
        }
    }

    //Негативный тест. Проверка на неравенство
    @Test
    void cannotTriangleInequalityOfSidesAC()
    {
        try {
            new Triangle(3,7,3);
            Assertions.fail();
        } catch (IllegalArgumentException exception){
            //ok
        }


    }
    @Test
    void testEquality(){
        var t1 = new Triangle(13.0, 14.0,15.0);
        var t2 = new Triangle(15.0, 13.0,14.0);
        Assertions.assertEquals(t1,t2);
    }

}


