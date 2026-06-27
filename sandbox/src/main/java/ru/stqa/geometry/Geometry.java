package ru.stqa.geometry;

import ru.stqa.geometry.figures.Rectangle;
import ru.stqa.geometry.figures.Square;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Geometry {
    public static void main(String[] args) {
        //Соплайеры/генераторы
        Supplier<Square> randomSquare = () -> new Square(new Random().nextDouble(100.0)); //Генерируем случайные числа
        var squares = Stream.generate(randomSquare).limit(10); //Через Лимит ограничиваем количество итераций
        squares.peek(Square::printSquareArea).forEach(Square::printPerimeter);



//Вариант 4. Упрощение отображения
//        var squares = List.of(new Square(7.0),new Square(5.0),new Square(3.0));
//        squares.forEach(Square::printSquareArea);

// Вариант решения 2
//        for (Square square : squares) {
//            Square.printSquareArea(square);
//        }

//Вариант решения 1
//        Square.printSquareArea(new Square(7.0));
//        Square.printSquareArea(new Square(5.0));
//        Square.printSquareArea(new Square(3.0));

//        Rectangle.printRectangleArea(3.0,5.0);
//           Rectangle.printRectangleArea(7.0,9.0);
//Вариант решения 3
//        Consumer<Square> print = (square) -> {
//            Square.printSquareArea(square);
//        };
//        squares.forEach(print);
//Эволюция решения 3.1
//        Consumer<Square> print = (square) -> Square.printSquareArea(square);
//        squares.forEach(print);
//Эволюция решения 3.2
//          Consumer<Square> print = Square::printSquareArea;
//          squares.forEach(print);


    }


}
