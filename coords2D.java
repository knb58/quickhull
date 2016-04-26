
package quickhull;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JComponent;

public class coords2D extends JComponent {

    public coords2D(String file, int size) {
        System.out.println("Arrived in coords2D  "+ file);
        int xLoc=0, yLoc=0;
        boolean coords[][]= new boolean[size+1][size+1];
        Scanner fileScanner = null;
        System.out.println("About to open the file");
        try {
            fileScanner = new Scanner(new File("C:\\Users\\dell\\Documents\\test.txt"));
        } catch (FileNotFoundException ex) {}
        System.out.println("Entering the while loop");
        
        String coordinates="";
        while (fileScanner.hasNext()){
                System.out.println("Inputting information");
                xLoc=fileScanner.nextInt();
                System.out.println(xLoc);
                yLoc=fileScanner.nextInt();
                System.out.println(yLoc);
                coords[xLoc][yLoc] = true;
                System.out.println("Put the info into the coords ");
                coordinates+="("+ xLoc +","+ yLoc + ") "; 
        }
         System.out.println(coordinates);
        System.out.println("I opened the file. going to print the boolean array");
        for( int i= 0; i < coords.length; i++){
            for( int j=0; j<coords.length; j++){
                if(coords[i][j])
                     System.out.println(coords[i][j]);
            }
        }
    }
    
    void paintComponent(Graphics g2, boolean coords[][]){
        
    }
    
    
}
