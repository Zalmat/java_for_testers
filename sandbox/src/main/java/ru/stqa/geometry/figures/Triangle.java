package ru.stqa.geometry.figures;

public record Triangle(
        double a,
        double b,
        double c) {


    public double perimeter() {
        return this.a + this.b + this.c;
    }

    public double area(){
            var halfPerimeter = getHalfPerimeter(this.a, this.b, this.c);
            return Math.sqrt( halfPerimeter * (halfPerimeter-a) * (halfPerimeter-b) * (halfPerimeter-c));
            }

   private static double getHalfPerimeter(double a, double b, double c) {
           return (a + b + c) / 2;
            }
    }


