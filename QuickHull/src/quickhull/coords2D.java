package quickhull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JComponent;

public class coords2D extends JComponent {

    ArrayList<Point2D.Double> coords2 = new ArrayList<>(), convexHull, middle, points;
    private final int buffer = 20, point = 5;
    private double graphSize, frameX, frameY;

    public coords2D(String file, double size, double frameXSize, double frameYSize) {
        double xLoc, yLoc;
        graphSize = size + 1;
        frameX = frameXSize - buffer;   
        frameY = frameYSize - 60 - buffer;

        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File(file));   //C:\\Users\\dell\\Documents\\test.txt
        } catch (FileNotFoundException ex) {
            System.exit(0);
        }

        while (fileScanner.hasNext()) {
            xLoc = fileScanner.nextDouble(); 
            yLoc = fileScanner.nextDouble();
            coords2.add(new Point2D.Double(xLoc, yLoc));
        }
        SortingClass hull = new SortingClass();
        convexHull = hull.quickHull(coords2);
        middle = hull.getMiddle();
        points=hull.getPoints();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Ellipse2D graphPoints;

        //Drawing the points
        for (int i = 0; i < coords2.size(); i++) {
            Point2D.Double temp = coords2.get(i);
            double tempX = temp.getX(), tempY = temp.getY();

            graphPoints = new Ellipse2D.Double(
                    tempX * (frameX / graphSize) + 2 * point,
                    (-tempY + graphSize) * (frameY / graphSize), point, point);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.fill(graphPoints);
        }
       
        Line2D cHull;
        final double xMod = (frameX / graphSize), yMod = frameY / graphSize, pMod = 2 * point, lMod = point / 2;

        //draw line from largest to smallest x
        Point2D.Double minX = middle.get(0), maxX = middle.get(1);

        cHull = new Line2D.Double(
                minX.getX() * xMod + pMod + lMod, (-minX.getY() + graphSize) * yMod + lMod,
                maxX.getX() * xMod + pMod + lMod, (-maxX.getY() + graphSize) * yMod + lMod);
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(1));
        g2.draw(cHull);

        int counter = 0;
        Point2D.Double midPoint;

        //point inside lines
        while (points.size() > 1) {
            minX = middle.get(0);  
            maxX = middle.get(1);
            midPoint = points.get(counter);

            if (midPoint.getX() >= 0) {
                //get the min and max X values
                for (int a = 0; a < counter; a++) {
                    Point2D.Double tempVal = points.get(a);
                    if (points.get(a) != midPoint) {
                        if (tempVal.getX() < maxX.getX() && tempVal.getX() > midPoint.getX()) {
                            maxX = tempVal;
                        } 
                        else if (tempVal.getX() > minX.getX() && tempVal.getX() < midPoint.getX()) {
                            minX = tempVal;
                        }
                    }
                }

                double slope = (maxX.getX() - minX.getX() == 0) ? 0 : ((maxX.getY() - minX.getY()) / (maxX.getX() - minX.getX()));
                double perpendic = ((slope == 0) ? 0 : 1 / slope);
                double mid1yIntercept = minX.getY() - (minX.getX() * slope);  //find the y intercept of the left point
                double PyIntercept = midPoint.getY() - (midPoint.getX() * (-perpendic));  //find the y intercept of the intersecting point
                double xVal = ((slope + perpendic == 0) ? midPoint.getX() : ((PyIntercept - mid1yIntercept) / (slope + perpendic)));
                double yVal = (slope * xVal) + mid1yIntercept; //derive the x and y values

                //draw perpendicular line
                cHull = new Line2D.Double(
                        midPoint.getX() * xMod + pMod + lMod, (-midPoint.getY() + graphSize) * yMod + lMod,
                        xVal * xMod + pMod + lMod, (-yVal + graphSize) * yMod + lMod);
                g2.setColor(Color.LIGHT_GRAY);
                g2.draw(cHull);
                
                g2.setColor(Color.DARK_GRAY);
                //draw from left point
                cHull = new Line2D.Double(
                        minX.getX() * xMod + pMod + lMod, (-minX.getY() + graphSize) * yMod + lMod,
                        midPoint.getX() * xMod + pMod + lMod, (-midPoint.getY() + graphSize) * yMod + lMod);
                g2.draw(cHull);

                //draw from right point
                cHull = new Line2D.Double(
                        midPoint.getX() * xMod + pMod + lMod, (-midPoint.getY() + graphSize) * yMod + lMod,
                        maxX.getX() * xMod + pMod + lMod, (-maxX.getY() + graphSize) * yMod + lMod);
                g2.draw(cHull);

                counter++;
            } 
            else if (midPoint.getX() == -1) {
                for (int i = 0; i < counter + 1; i++) 
                    points.remove(0);
                
                counter = 0;
            } 
            else if (midPoint.getX() == -2) {
                points.clear();
            }
        }
        Point2D.Double start = convexHull.get(0), temp = start;

        //draw outside Hull
        for (int i = 1; i < convexHull.size(); i++) {
            Point2D.Double temp2 = convexHull.get(i);

            cHull = new Line2D.Double(
                    temp.getX() * xMod + pMod + lMod, (-temp.getY() + graphSize) * yMod + lMod,
                    temp2.getX() * xMod + pMod + lMod, (-temp2.getY() + graphSize) * yMod + lMod);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.draw(cHull);
            temp = temp2;
        }
        cHull = new Line2D.Double(
                start.getX() * xMod + pMod + lMod, (-start.getY() + graphSize) * yMod + lMod,
                temp.getX() * xMod + pMod + lMod, (-temp.getY() + graphSize) * yMod + lMod);
        g2.draw(cHull);
    }
}
