package quickhull;


import java.awt.geom.Point2D;
import java.util.ArrayList;

public class SortingClass {
    
     ArrayList<Point2D.Double> middle = new ArrayList<>();
     ArrayList<Point2D.Double> convexHull = new ArrayList<>();
     ArrayList<Point2D.Double> midPoints= new ArrayList<>();
    
    public ArrayList<Point2D.Double> quickHull(ArrayList<Point2D.Double> points) {
        
        if (points.size() < 3)
            return (ArrayList) points.clone();
 
        int minPoint = -1, maxPoint = -1;
        double minX = Integer.MAX_VALUE;
        double maxX = Integer.MIN_VALUE;
        //Get the min and max X values
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).x < minX) {
                minX = points.get(i).x;
                minPoint = i;
            }
            if (points.get(i).x > maxX) {
                maxX = points.get(i).x;
                maxPoint = i;
            }
        }
        
        //Add these values to the convex hull
        Point2D.Double A = points.get(minPoint);
        Point2D.Double B = points.get(maxPoint);
        convexHull.add(A);
        convexHull.add(B);
        middle.add(A);
        middle.add(B);
        points.remove(A);
        points.remove(B);
 
        ArrayList<Point2D.Double> leftSet = new ArrayList<>();
        ArrayList<Point2D.Double> rightSet = new ArrayList<>();
        
        //Populate the left and right subsets
        for (int i = 0; i < points.size(); i++) {
            Point2D.Double p = points.get(i);
            if (pointLocation(A, B, p) == -1)
                leftSet.add(p);
            else if (pointLocation(A, B, p) == 1)
                rightSet.add(p);
        }
        
        //Begin Recursive calls on subsets
        hullSet(A, B, rightSet, convexHull);
        midPoints.add(new Point2D.Double(-1,-1));
        hullSet(B, A, leftSet, convexHull);
        midPoints.add(new Point2D.Double(-2,-2));        
        return convexHull;
    }
    
    public ArrayList<Point2D.Double> getMiddle(){
        return middle;
    }
    
    public ArrayList<Point2D.Double> getHull(){
        return convexHull;
    }
    public ArrayList<Point2D.Double> getPoints(){
        return midPoints;
    }
 
    public double distance(Point2D.Double A, Point2D.Double B, Point2D.Double C) {
        double ABx = B.x - A.x;
        double ABy = B.y - A.y;
        double num = ABx * (A.y - C.y) - ABy * (A.x - C.x);
        if (num < 0)
            num = -num;
        
        return num;
    }
 
    public void hullSet(Point2D.Double A, Point2D.Double B, ArrayList<Point2D.Double> set,  ArrayList<Point2D.Double> hull) {
        int insertPosition = hull.indexOf(B);
        
        //base cases
        if (set.isEmpty())
            return;
        if (set.size() == 1) {
            Point2D.Double p = set.get(0);
            set.remove(p);
            hull.add(insertPosition, p);
            return;
        }
        double dist = Integer.MIN_VALUE;
        int furthestPoint = -1;
        
        //find the furthest point from the line
        for (int i = 0; i < set.size(); i++) {
            Point2D.Double p = set.get(i);
            double distance = distance(A, B, p);
            if (distance > dist) {
                dist = distance;
                furthestPoint = i;
            }
        }
        Point2D.Double P = set.get(furthestPoint);
        set.remove(furthestPoint);
        hull.add(insertPosition, P);
 
        // Determine who's to the left of AP
        ArrayList<Point2D.Double> leftSetAP = new ArrayList<>();
        for (int i = 0; i < set.size(); i++) {
            Point2D.Double M = set.get(i);
            if (pointLocation(A, P, M) == 1) 
                leftSetAP.add(M);
        }
 
        // Determine who's to the left of PB
        ArrayList<Point2D.Double> leftSetPB = new ArrayList<>();
        for (int i = 0; i < set.size(); i++) {
            Point2D.Double M = set.get(i);
            if (pointLocation(P, B, M) == 1)
                leftSetPB.add(M);
        }
        
        //recursive call
        midPoints.add(P); 
        hullSet(A, P, leftSetAP, hull);
        hullSet(P, B, leftSetPB, hull);
    }
 
    public int pointLocation(Point2D.Double A, Point2D.Double B, Point2D.Double P) {
        double cp1 = (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
        if (cp1 > 0)
            return 1;
        else if (cp1 == 0)
            return 0;
        else
            return -1;
    }
}
