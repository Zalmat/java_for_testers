public class Triangle {

    public static void main(String[] args) {

// 1. Создать класс Triangle для представления треугольников. Объекты этого класса должны содержать три свойства,
// которые соответствуют длинам сторон треугольника.
//
// 2. Реализовать методы для вычисления периметра и площади треугольника (для вычисления площади можно использовать
// формулу Герона).
//
// 3. Сделать тесты, которые проверяют, что реализованные методы правильно работают (на "хороших" данных,
// которые // соответствуют корректным треугольникам).
        System.out.println("Площадь треугольника со сторонами 12,34,38 = " + square(12,34,38));
        System.out.println("Периметр треугольника со сторонами 14,34,12 = " + perimeter(12,34,38));
    }


    public static double  square(double a, double b, double c){
        var halfPerimeter = getHalfPerimeter(a, b, c);


        return Math.sqrt( halfPerimeter * (halfPerimeter-a) * (halfPerimeter-b) * (halfPerimeter-c));
    }

    private static double getHalfPerimeter(double a, double b, double c) {
        return (a + b + c) / 2;
    }

    public static double perimeter(double a, double b, double c){
        return a+b+c;
    }

}
