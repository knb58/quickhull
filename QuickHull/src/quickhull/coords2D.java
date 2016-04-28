
package quickhull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JComponent;

public class coords2D extends JComponent {
    
    private boolean coords[][];
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
            fileScanner = new Scanner(new File("C:\\Users\\dell\\Documents\\test.txt"));
        } catch (FileNotFoundException ex) {}
        
        while (fileScanner.hasNext()){
                xLoc=fileScanner.nextInt();
                yLoc=fileScanner.nextInt();
                coords[xLoc][yLoc] = true;
                coordinates+="("+ xLoc +","+ yLoc + ") "; 
        }
       //  System.out.println(coordinates);
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
         double size=  graphSize;
         int point=5;
         
         //Create Graph
//         graph= new Rectangle2D.Double(0, 0, frameX+buffer/2, frameY+buffer);
//         g2.setStroke(new BasicStroke(2));
//         g2.setColor(Color.LIGHT_GRAY);
//         g2.draw(graph);
//         
//         Line2D xLine, yLine;
//         for (int i=0; i<frameX+buffer/2; i+=1*point){
//             g2.setStroke(new BasicStroke(1));
//             xLine= new Line2D.Double(i, 0, i, frameY+buffer);
//             for (int j=0; j<frameY+buffer; j+=1*point){
//                yLine= new Line2D.Double(0, j ,frameX+buffer/2, j);
//                g2.draw(yLine);
//             }
//             g2.draw(xLine);
//         }
         
         
          for (int i=0; i<coords.length;  i++)
            for (int j=0; j<coords.length; j++) 
                if(coords[i][j]){
                    graphPoints= new Ellipse2D.Double(i*(frameX/size)+2*point, (-j+size)*(frameY/size),point,point);
                    g2.setColor(Color.BLACK);
                    g2.fill(graphPoints);
                }
    }
    
    
}
