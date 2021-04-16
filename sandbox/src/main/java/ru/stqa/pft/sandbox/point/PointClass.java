package ru.stqa.pft.sandbox.point;

public class PointClass {

  public static void main(String[] args) {
    Point p1 = new Point(2,3);
    Point p2 = new Point(6,6);
    System.out.println("Расстояние между " + p1 + " и " + p2 + " = " + p1.distance(p2));
    System.out.println(p1.distance(p2));
    System.out.println(p2.distance(p1));
  }
}
