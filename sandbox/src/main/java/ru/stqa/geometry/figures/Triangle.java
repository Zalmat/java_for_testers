package ru.stqa.geometry.figures;

import java.util.Objects;

public record Triangle(
        double a,
        double b,
        double c) {

    public Triangle {
        if (a < 0 || b < 0 || c < 0) {
            throw new IllegalArgumentException("Одна из сторон имеет отрицательное значение");
        }
        if (((a + b) <= c) || ((b + c) <= a) || ((c + a) <= b)) {
            throw new IllegalArgumentException("Проверка на неравенство не пройдена");
        }

    }


    public double perimeter() {
        return this.a + this.b + this.c;
    }

    public double area() {
        var halfPerimeter = getHalfPerimeter(this.a, this.b, this.c);
        return Math.sqrt(halfPerimeter * (halfPerimeter - a) * (halfPerimeter - b) * (halfPerimeter - c));
    }

    private static double getHalfPerimeter(double a, double b, double c) {
        return (a + b + c) / 2;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return (Double.compare(this.a, triangle.a) == 0 && Double.compare(this.b, triangle.b) == 0 && Double.compare(this.c, triangle.c) == 0) ||
                (Double.compare(this.a, triangle.a) == 0 && Double.compare(this.c, triangle.b) == 0 && Double.compare(this.b, triangle.c) == 0) ||
                (Double.compare(this.b, triangle.a) == 0 && Double.compare(this.a, triangle.b) == 0 && Double.compare(this.c, triangle.c) == 0) ||
                (Double.compare(this.b, triangle.a) == 0 && Double.compare(this.c, triangle.b) == 0 && Double.compare(this.a, triangle.c) == 0) ||
                (Double.compare(this.c, triangle.a) == 0 && Double.compare(this.a, triangle.b) == 0 && Double.compare(this.b, triangle.c) == 0) ||
                (Double.compare(this.c, triangle.a) == 0 && Double.compare(this.b, triangle.b) == 0 && Double.compare(this.a, triangle.c) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }
}


