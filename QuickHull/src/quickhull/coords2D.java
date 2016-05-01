
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
    ArrayList <Point> coords2= new ArrayList<>();
    ArrayList<Point> convexHull;
    private int graphSize, buffer=20;
    private double frameX, frameY;
    private String coordinates="";

    public coords2D(String file, int size, double frameXSize, double frameYSize) {
        int xLoc, yLoc;
        buffer=20;  graphSize=size+1;  
        frameX=frameXSize-20;//-buffer;
        frameY=frameYSize-40;//-85-buffer;
        
        coords= new boolean[graphSize][graphSize];
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File("/Users/Cam/Desktop/Algorithms/Project 4/quickhull/QuickHull/src/quickhull/test.txt"));
        } catch (FileNotFoundException ex) {}
        
        while (fileScanner.hasNext()){
                xLoc=fileScanner.nextInt();
                yLoc=fileScanner.nextInt();
               // coords[xLoc][yLoc] = true;
                coords2.add(new Point(xLoc, yLoc));
                coordinates+="("+ xLoc +","+ yLoc + ") "; 
             //   System.out.println(coords2);
        }
//         System.out.println(coordinates);
    }
    
    public String getCoordinates(){
        return coordinates;
    }
    
    @Override
    public void paintComponent(Graphics g){
        //super.paintComponent(g);
         Graphics2D g2 = (Graphics2D) g;
         Rectangle2D graph;
         Ellipse2D graphPoints;
         final double size=  graphSize;
         int point=5;
         
         
            for(int i=0; i<coords2.size(); i++){
                Point temp= coords2.get(i);
                final double tempX= temp.getX();
                final double tempY=temp.getY();
  
                graphPoints= new Ellipse2D.Double(tempX*(frameX/size)+2*point, (-tempY+size)*(frameY/size), point, point);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(2));
                g2.fill(graphPoints);
            }
           
            SortingClass hull= new SortingClass();
            convexHull= hull.quickHull(coords2) ;
            
            Point start= convexHull.get(0);
            Point temp= start;
             Line2D cHull;
            final double xMod= (frameX/size), yMod= frameY/size;
            final double pMod=2*point, lMod=point/2;
            
            for(int i=1; i<convexHull.size(); i++){
                Point temp2= convexHull.get(i);
                
                cHull= new Line2D.Double(
                        temp.getX()*xMod+pMod+lMod, (-temp.getY()+size)*yMod+lMod, 
                        temp2.getX()*xMod+pMod+lMod, (-temp2.getY()+size)*yMod+lMod);
                g2.draw(cHull);
                temp=temp2;
                
            }
            cHull= new Line2D.Double(
                    start.getX()*xMod+pMod+lMod, (-start.getY()+size)*yMod+lMod ,
                    temp.getX()*xMod+pMod+lMod ,(-temp.getY()+size)*yMod+lMod);
            g2.draw(cHull);

    }
    
    
}
