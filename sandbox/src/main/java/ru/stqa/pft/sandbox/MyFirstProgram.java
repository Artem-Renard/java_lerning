package ru.stqa.pft.sandbox;

public class MyFirstProgram {
	
    public static void main(String [] args) {
      hello ("world");
      hello ("SOVA");
      hello ("PUH");

      double d = 5;
      System.out.println("Площадь квадрата со стороной " + d +" = " + area (d));

      double a = 4;
      double b = 5;
      System.out.println("Площадь прямоугольника со сторонами " + a + " и " + b + " = " + area (a, b));
    }

    public static void hello (String somebody) {
      System.out.println("Hello, " + somebody + "!");
    }

    public static double area (double l) {
      return l * l;
    }

    public static double area (double a, double b) {
      return a * b;
    }
}