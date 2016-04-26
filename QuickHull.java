
package quickhull;

import java.awt.BorderLayout;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Kirsten Baker, Cameron Reilly
 */
public class QuickHull extends JFrame{
    
    public QuickHull(){
        
        JPanel fileLoc = new JPanel();
        
        //create file input area
        JLabel dir = new JLabel("Please input the path of the text file.");
        JTextField fileField = new JTextField(20);
        
        //create graph size area
        JTextField size = new JTextField(4);
        size.setText("50");
        JLabel sizeSet = new JLabel("Set graph size:");
        JLabel sizeDefault = new JLabel("(Default is 50)");
        sizeDefault.setFont(new Font("SANS_SERIF", Font.PLAIN, 10));
        
         //Submit button
        JButton submitButton = new JButton("Submit");
        
        dir.setAlignmentX(CENTER_ALIGNMENT);
        size.setAlignmentX(CENTER_ALIGNMENT);
        sizeSet.setAlignmentX(CENTER_ALIGNMENT);
        sizeDefault.setAlignmentX(CENTER_ALIGNMENT);
        submitButton.setAlignmentX(CENTER_ALIGNMENT);
        
       //create a listener for the submit button
        class ButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                String file = fileField.getText();
                int graph = Integer.parseInt(size.getText());
                dispose(); //gets rid of the open file location gui                
                
                JFrame frame = new JFrame();
                final int FRAME_WIDTH = 500;
                final int FRAME_HEIGHT = 500;
                frame.setSize(FRAME_HEIGHT, FRAME_WIDTH);
                frame.setTitle("Crazy Maze Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                coords2D coords= new coords2D(file, graph);
                frame.add(coords);
                frame.setResizable(true);
                frame.setVisible(true);
                
            }
        }
         
        //add listener to button
        ActionListener listener = new ButtonListener();
        submitButton.addActionListener(listener);
        
        //add to the panel 
        fileLoc.add(dir);
        fileLoc.add(fileField);
        fileLoc.add(sizeSet);
        fileLoc.add(size);
        fileLoc.add(sizeDefault);
        fileLoc.add(submitButton, BorderLayout.SOUTH);
        
        //add to the frame
        add(fileLoc);
    }
   
    public static void main(String[] args) {
        //create GUI
        JFrame fileFrame = new QuickHull();
        fileFrame.setTitle("Open the Text File");
        fileFrame.setSize(250, 150);
        fileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fileFrame.setLocationRelativeTo(null);
        fileFrame.setResizable(false);
        fileFrame.setVisible(true);
    } 
 }

