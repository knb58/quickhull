package quickhull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JComponent;

public class coords2D extends JComponent {

    ArrayList<Point> coords2 = new ArrayList<>();
    ArrayList<Point> convexHull, middle;
    private final int graphSize, buffer = 20;
    private double frameX, frameY;
    private String coordinates = "";
    private final int point = 5;

    public coords2D(String file, int size, double frameXSize, double frameYSize) {
        int xLoc, yLoc;
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
            xLoc = fileScanner.nextInt();
            yLoc = fileScanner.nextInt();
            coords2.add(new Point(xLoc, yLoc));
            coordinates += "(" + xLoc + "," + yLoc + ") ";
        }
        SortingClass hull = new SortingClass();
        convexHull = hull.quickHull(coords2);
        middle = hull.getMiddle();
    }

    public String getCoordinates() {
        return coordinates;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Ellipse2D graphPoints;

        for (int i = 0; i < coords2.size(); i++) {
            Point temp = coords2.get(i);
            double tempX = temp.getX();
            double tempY = temp.getY();

            graphPoints = new Ellipse2D.Double(tempX * (frameX / graphSize) + 2 * point, (-tempY + graphSize) * (frameY / graphSize), point, point);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.fill(graphPoints);
        }

        Point start = convexHull.get(0);
        Point temp = start;
        Line2D cHull;
        final double xMod = (frameX / graphSize), yMod = frameY / graphSize;
        final double pMod = 2 * point, lMod = point / 2;

       

        //draw line from largest to smallest x
        Point mid1 = middle.get(0);
        Point mid2 = middle.get(1);
        cHull = new Line2D.Double(
                mid1.getX() * xMod + pMod + lMod, (-mid1.getY() + graphSize) * yMod + lMod,
                mid2.getX() * xMod + pMod + lMod, (-mid2.getY() + graphSize) * yMod + lMod);
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(1));
        g2.draw(cHull);

        Point tempP = middle.get(2);

        //point inside lines
        for (int i = 2; i < middle.size(); i++) {
            mid1 = middle.get(0);
            mid2 = middle.get(1);
            
            Point mid3 = middle.get(i);

            if (mid3.getX() != -1) {
                
                if (temp != mid3) {
                    if (mid3.getX() < tempP.getX()) {
                        mid2 = tempP;
                    } 
                    else {
                        mid1 = tempP;
                    }
                }
                
                //get slope of the lines
                double slope = ((mid2.getY() - mid1.getY()) / (mid2.getX() - mid1.getX()));
                //find the y intercept of the left point
                double mid1yIntercept = mid1.getY() - (mid1.getX() * slope);
                //find the y intercept of the intersecting point
                double PyIntercept = mid3.getY() - (mid3.getX() * (-1/slope));
                //derive the x and y values
                double xVal = (PyIntercept - mid1yIntercept) / (slope+1/slope);
                double yVal = (slope * xVal) + mid1yIntercept;
               
                //draw perpendicular line
                cHull = new Line2D.Double(
                        mid3.getX() * xMod + pMod + lMod, (-mid3.getY() + graphSize) * yMod + lMod,
                        xVal * xMod + pMod + lMod, (-yVal + graphSize) * yMod + lMod);
                g2.draw(cHull);
                
                cHull = new Line2D.Double(
                mid1.getX() * xMod + pMod + lMod, (-mid1.getY() + graphSize) * yMod + lMod,
                mid2.getX() * xMod + pMod + lMod, (-mid2.getY() + graphSize) * yMod + lMod);
                g2.draw(cHull);

                //draw from left point
                cHull = new Line2D.Double(
                        mid1.getX() * xMod + pMod + lMod, (-mid1.getY() + graphSize) * yMod + lMod,
                        mid3.getX() * xMod + pMod + lMod, (-mid3.getY() + graphSize) * yMod + lMod);
                g2.draw(cHull);

                //draw from right point
                cHull = new Line2D.Double(
                        mid3.getX() * xMod + pMod + lMod, (-mid3.getY() + graphSize) * yMod + lMod,
                        mid2.getX() * xMod + pMod + lMod, (-mid2.getY() + graphSize) * yMod + lMod);
                g2.draw(cHull);

                tempP = mid3;
            }
            else{
                tempP=middle.get(i+1);  
            }
        }
         for (int i = 1; i < convexHull.size(); i++) {
            Point temp2 = convexHull.get(i);

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
