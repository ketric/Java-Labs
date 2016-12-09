package assignment.basics;

import static java.lang.System.*;
import static java.lang.Math.*;

/*
 * Basic use of classes
 */
public class PointAndTriangle {

    public static void main(String[] args) {
        new PointAndTriangle().program();
    }

    void program() {

        // Printing result from solutions. Expected outcome as comment
        Point p = new Point(1, 2, 3);
        out.println(p.distance(p));  // 0.0

        Point A = new Point(0, 0, 0);
        Point B = new Point(0, 1, 0);
        Point C = new Point(1, 0, 0);
        Triangle t = new Triangle(A, B, C);

        out.println(new Point(0, 0, 0).distance(new Point(1, 0, 0)));  // 1.0
        out.println(t.area());   // 0.49999999999999983


    }

    // ---------- Write your classes below this line ----------------------------

    // A class for points in 3D here
    class Point {
        // State
        double coorX;
        double coorY;
        double coorZ;

        // Constructors
        Point(double x, double y, double z) {
            this.coorX = x;
            this.coorY = y;
            this.coorZ = z;
        }

        // Behaviors
        double distance(Point anotherpoint){
            double x = anotherpoint.coorX - this.coorX;
            double y = anotherpoint.coorY - this.coorY;
            double z = anotherpoint.coorZ - this.coorZ;
            return (Math.sqrt(x*x + y*y + z*z));
        }
    }


    // A class for a Triangle in 3D here
    class Triangle {
        // State
        Point coorA;
        Point coorB;
        Point coorC;

        // Constructors
        Triangle(Point coorA, Point coorB, Point coorC) {
            this.coorA = coorA;
            this.coorB = coorB;
            this.coorC = coorC;
        }

        // Behaviors
        double area(){
            double AB = this.coorA.distance(this.coorB);
            double AC = this.coorA.distance(this.coorC);
            double BC = this.coorB.distance(this.coorC);
            double s = (AB + AC + BC) / 2.0;
            double area = Math.sqrt(s*(s - AB)*(s - AC)*(s - BC));
            return area;
        }

    }


}
