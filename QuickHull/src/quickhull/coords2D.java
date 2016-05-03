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

    ArrayList<Point2D.Double> coords2 = new ArrayList<>();
    ArrayList<Point2D.Double> convexHull, middle, points;
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
       // System.out.println(coordinates);
        SortingClass hull = new SortingClass();
        convexHull = hull.quickHull(coords2);
        middle = hull.getMiddle();
        points=hull.getPoints();
    }

    public String getCoordinates() {
        return coordinates;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Ellipse2D graphPoints;

        //Drawing the points
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
        Point2D.Double minX = middle.get(0);
        Point2D.Double maxX = middle.get(1);
        
        cHull = new Line2D.Double(
                minX.getX() * xMod + pMod + lMod, (-minX.getY() + graphSize) * yMod + lMod,
                maxX.getX() * xMod + pMod + lMod, (-maxX.getY() + graphSize) * yMod + lMod);
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(1));
        g2.draw(cHull);

        //System.out.println(middle+ "\n");
        int counter=0;
        Point2D.Double tempP=points.get(0);
        Point2D.Double midPoint;
        
        //point inside lines
        while(points.size() > 1){
            
            minX = middle.get(0);
            maxX = middle.get(1);
            System.out.println(points);
            midPoint = points.get(counter);
            
           // System.out.println("Point 1: "+ mid1+"  Point 2: "+mid2+ "   Recurisve Point: "+mid3+ " Temp val: "+ tempP);

            if (midPoint.getX() >= 0) {
               // System.out.println("Drawing");
                            System.out.println(points);                                                                                        //C:\\Users\\dell\\Documents\\test.txt

                    for(int a=0; a<counter; a++){
                        Point2D.Double tempVal= points.get(a);
                        if(points.get(a)!=midPoint){
                            if(tempVal.getX()<maxX.getX() && tempVal.getX()>midPoint.getX()){
                                maxX=tempVal;
                            }
                            else if (tempVal.getX()>minX.getX() && tempVal.getX()<midPoint.getX()){
                                minX=tempVal;
                             }
                        }
                    }
                
              //  System.out.println("Point 1: "+ mid1+"  Point 2: "+mid2+ "   Recurisve Point: "+mid3+ " Temp val: "+ tempP);

                double slope,  perpendic;
                //get slope of the lines
                slope = (maxX.getX() - minX.getX()==0) ? 0 : ((maxX.getY() - minX.getY()) / (maxX.getX() - minX.getX()));
                perpendic=((slope==0) ? 0 : 1/slope);
                //find the y intercept of the left point
                double mid1yIntercept = minX.getY() - (minX.getX() * slope);
                //find the y intercept of the intersecting point
                double PyIntercept = midPoint.getY() - (midPoint.getX() * (-perpendic));
                //derive the x and y values
                double xVal = ((slope+perpendic==0) ? midPoint.getX(): ((PyIntercept - mid1yIntercept) / (slope+perpendic)));
                double yVal = (slope * xVal) + mid1yIntercept;
               // System.out.println("XVAL= "+xVal+ "   YVAL=  "+ yVal);
              
                //draw perpendicular line
                cHull = new Line2D.Double(
                        midPoint.getX() * xMod + pMod + lMod, (-midPoint.getY() + graphSize) * yMod + lMod,
                        xVal * xMod + pMod + lMod, (-yVal + graphSize) * yMod + lMod);
                g2.setColor(Color.LIGHT_GRAY);
                g2.draw(cHull);

                g2.setColor(Color.DARK_GRAY);
                 cHull = new Line2D.Double(
                    minX.getX() * xMod + pMod + lMod, (-minX.getY() + graphSize) * yMod + lMod,
                    maxX.getX() * xMod + pMod + lMod, (-maxX.getY() + graphSize) * yMod + lMod);
                 g2.draw(cHull);

                    //draw from left point
                    cHull = new Line2D.Double(
                            minX.getX() * xMod + pMod + lMod, (-minX.getY() + graphSize) * yMod + lMod,
                            midPoint.getX() * xMod + pMod + lMod, (-midPoint.getY() + graphSize) * yMod + lMod);
                    g2.setColor(Color.RED);
                    g2.draw(cHull);

                    //draw from right point
                    cHull = new Line2D.Double(
                            midPoint.getX() * xMod + pMod + lMod, (-midPoint.getY() + graphSize) * yMod + lMod,
                            maxX.getX() * xMod + pMod + lMod, (-maxX.getY() + graphSize) * yMod + lMod);
                    g2.setColor(Color.MAGENTA);
                    g2.draw(cHull);

                   tempP = midPoint;
                   counter++;
                }
            else if(midPoint.getX() == -1){
                
                for (int j=0; j<counter+1; j++){
                    points.remove(0);
                }
                counter=0;
                tempP=points.get(0);
                
               System.out.println("Resetting tempP");
            }
            else if(midPoint.getX() == -2){
                    points.clear();
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
