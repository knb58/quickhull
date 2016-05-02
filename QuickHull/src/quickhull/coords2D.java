package quickhull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JComponent;

public class coords2D extends JComponent {

    private boolean coords[][];
    ArrayList<Point> coords2 = new ArrayList<>();
    ArrayList<Point> convexHull, middle;
    private final int graphSize, buffer = 20;
    private double frameX, frameY;
    private String coordinates = "";
    private final int point = 5;

    public coords2D(String file, int size, double frameXSize, double frameYSize) {
        int xLoc, yLoc;
        graphSize = size + 1;
        frameX = frameXSize - buffer;//-buffer;
        frameY = frameYSize - 85-buffer;//-85-buffer;

        coords = new boolean[graphSize][graphSize];
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File("C:\\Users\\dell\\Documents\\test.txt"));
        } catch (FileNotFoundException ex) { System.exit(0);}

        while (fileScanner.hasNext()) {
            xLoc = fileScanner.nextInt();
            yLoc = fileScanner.nextInt();
            // coords[xLoc][yLoc] = true;
            coords2.add(new Point(xLoc, yLoc));
            coordinates += "(" + xLoc + "," + yLoc + ") ";
            //   System.out.println(coords2);
        }
        //  System.out.println(coordinates);
        SortingClass hull = new SortingClass();
        convexHull = hull.quickHull(coords2);
        middle= hull.getMiddle();
        
    }

    public String getCoordinates() {
        return coordinates;
    }

    @Override
    public void paintComponent(Graphics g) {
        //super.paintComponent(g);
        System.out.println("Starting to draw: ");
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D graph;
        Ellipse2D graphPoints;
        

         System.out.println(coords2);
        for (int i = 0; i < coords2.size(); i++) {
            Point temp = coords2.get(i);
            double tempX = temp.getX();
            double tempY = temp.getY();

            graphPoints = new Ellipse2D.Double(tempX * (frameX / graphSize) + 2 * point, (-tempY + graphSize) * (frameY / graphSize), point, point);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.fill(graphPoints);
        }
        System.out.println("Creating a Hull Class");
        
        System.out.println(convexHull);

        Point start = convexHull.get(0);
        Point temp = start;
        Line2D cHull;
        final double  xMod = (frameX / graphSize), yMod = frameY / graphSize;
        final double pMod = 2 * point, lMod = point / 2;
        System.out.println("Drawing the lines of the Hull ");

        for (int i = 1; i < convexHull.size(); i++) {
            Point temp2 = convexHull.get(i);

            cHull = new Line2D.Double(
                    temp.getX() * xMod + pMod + lMod, (-temp.getY() + graphSize) * yMod + lMod,
                    temp2.getX() * xMod + pMod + lMod, (-temp2.getY() + graphSize) * yMod + lMod);
            g2.draw(cHull);
            temp = temp2;

        }
        cHull = new Line2D.Double(
                start.getX() * xMod + pMod + lMod, (-start.getY() + graphSize) * yMod + lMod,
                temp.getX() * xMod + pMod + lMod, (-temp.getY() + graphSize) * yMod + lMod);
        g2.draw(cHull);

            
            for( int i=0; i<middle.size(); i+=2){
                Point mid1= middle.get(i);
                Point mid2= middle.get(i+1);
                cHull= new Line2D.Double(
                    mid1.getX()*xMod+pMod+lMod, (-mid1.getY()+graphSize)*yMod+lMod ,
                    mid2.getX()*xMod+pMod+lMod ,(-mid2.getY()+graphSize)*yMod+lMod);
                g2.setColor(Color.LIGHT_GRAY);
                g2.setStroke(new BasicStroke(1));
            g2.draw(cHull);
            }
    }

}
