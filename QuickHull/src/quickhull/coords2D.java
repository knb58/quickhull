package quickhull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JComponent;

public class coords2D extends JComponent {

    ArrayList<Point2D.Double> coords2 = new ArrayList<>();
    ArrayList<Point2D.Double> convexHull, middle;
    private final int graphSize, buffer = 20;
    private double frameX, frameY;
    private String coordinates = "";
    private final int point = 5;

    public coords2D(String file, int size, double frameXSize, double frameYSize) {
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
            Point2D.Double temp = coords2.get(i);
            double tempX = temp.getX();
            double tempY = temp.getY();

            graphPoints = new Ellipse2D.Double(tempX * (frameX / graphSize) + 2 * point, (-tempY + graphSize) * (frameY / graphSize), point, point);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.fill(graphPoints);
        }

        Point2D.Double start = convexHull.get(0);
        Point2D.Double temp = start;
        Line2D cHull;
        final double xMod = (frameX / graphSize), yMod = frameY / graphSize;
        final double pMod = 2 * point, lMod = point / 2;

       

        //draw line from largest to smallest x
        Point2D.Double mid1 = middle.get(0);
        Point2D.Double mid2 = middle.get(1);
        cHull = new Line2D.Double(
                mid1.getX() * xMod + pMod + lMod, (-mid1.getY() + graphSize) * yMod + lMod,
                mid2.getX() * xMod + pMod + lMod, (-mid2.getY() + graphSize) * yMod + lMod);
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(1));
        g2.draw(cHull);

        Point2D.Double tempP = middle.get(2);

        //point inside lines
        for (int i = 2; i < middle.size(); i++) {
            mid1 = middle.get(0);
            mid2 = middle.get(1);
            
            Point2D.Double mid3 = middle.get(i);
            System.out.println("Point 1: "+ mid1+"  Point 2: "+mid2+ "   Recurisve Point: "+mid3+ " Temp val: "+ tempP);

            if (mid3.getX() != -1) {
                
                if (tempP != mid3) {
                    System.out.println("Temp and the 3rd point are different.");
                    if (mid3.getX() < tempP.getX()) {
                        System.out.println("Making temp the 2nd point.");
                        mid2 = tempP;
                    } 
                    else if(mid3.getX() > tempP.getX()){
                        System.out.println("Making temp the 1st point.");
                        mid1 = tempP;
                    }
                    System.out.println("Couldn't figure it out.");
                }
                System.out.println("Point 1: "+ mid1+"  Point 2: "+mid2+ "   Recurisve Point: "+mid3+ " Temp val: "+ tempP);

                
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
                System.out.println("Resetting tempP");
            }
        }
        
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
