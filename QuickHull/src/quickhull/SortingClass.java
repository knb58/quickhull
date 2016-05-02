package quickhull;

import java.awt.Point;
import java.util.ArrayList;

public class SortingClass {
    private ArrayList<Point> middle = new ArrayList<>();
    private ArrayList<Point> convexHull = new ArrayList<>();
    
    public ArrayList<Point> quickHull(ArrayList<Point> points) {
        
        if (points.size() < 3)
            return (ArrayList) points.clone();
 
        int minPoint = -1, maxPoint = -1;
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
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
        Point A = points.get(minPoint);
        Point B = points.get(maxPoint);
        convexHull.add(A);
        convexHull.add(B);
        middle.add(A);
        middle.add(B);
        points.remove(A);
        points.remove(B);
 
        ArrayList<Point> leftSet = new ArrayList<>();
        ArrayList<Point> rightSet = new ArrayList<>();
        
        //Populate the left and right subsets
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            if (pointLocation(A, B, p) == -1)
                leftSet.add(p);
            else if (pointLocation(A, B, p) == 1)
                rightSet.add(p);
        }
        
        //Begin Recursive calls on subsets
        hullSet(A, B, rightSet, convexHull);
        middle.add(new Point(-1,-1));
        hullSet(B, A, leftSet, convexHull);
        
        return convexHull;
    }
    
    public ArrayList<Point> getMiddle(){
        return middle;
    }
    
    public ArrayList<Point> getHull(){
        return convexHull;
    }
 
    public int distance(Point A, Point B, Point C) {
        int ABx = B.x - A.x;
        int ABy = B.y - A.y;
        int num = ABx * (A.y - C.y) - ABy * (A.x - C.x);
        if (num < 0)
            num = -num;
        
        return num;
    }
 
    public void hullSet(Point A, Point B, ArrayList<Point> set,  ArrayList<Point> hull) {
        int insertPosition = hull.indexOf(B);
        
        //base cases
        if (set.isEmpty())
            return;
        if (set.size() == 1) {
            Point p = set.get(0);
            set.remove(p);
            hull.add(insertPosition, p);
            return;
        }
        int dist = Integer.MIN_VALUE;
        int furthestPoint = -1;
        
        //find the furthest point from the line
        for (int i = 0; i < set.size(); i++) {
            Point p = set.get(i);
            int distance = distance(A, B, p);
            if (distance > dist) {
                dist = distance;
                furthestPoint = i;
            }
        }
        Point P = set.get(furthestPoint);
        set.remove(furthestPoint);
        middle.add(P);
        hull.add(insertPosition, P);
 
        // Determine who's to the left of AP
        ArrayList<Point> leftSetAP = new ArrayList<>();
        for (int i = 0; i < set.size(); i++) {
            Point M = set.get(i);
            if (pointLocation(A, P, M) == 1) 
                leftSetAP.add(M);
        }
 
        // Determine who's to the left of PB
        ArrayList<Point> leftSetPB = new ArrayList<>();
        for (int i = 0; i < set.size(); i++) {
            Point M = set.get(i);
            if (pointLocation(P, B, M) == 1)
                leftSetPB.add(M);
        }
        
        //recursive call
        hullSet(A, P, leftSetAP, hull);
        hullSet(P, B, leftSetPB, hull);
    }
 
    public int pointLocation(Point A, Point B, Point P) {
        int cp1 = (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
        if (cp1 > 0)
            return 1;
        else if (cp1 == 0)
            return 0;
        else
            return -1;
    }
}
