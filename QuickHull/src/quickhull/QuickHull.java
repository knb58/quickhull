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
public class QuickHull extends JFrame {

    public QuickHull() {

        //create file input area
        JLabel dir = new JLabel("Please input the path of the text file.");
        final JTextField fileField = new JTextField(20);
        dir.setAlignmentX(CENTER_ALIGNMENT);
        
        //create graph size area
        final JTextField size = new JTextField(4);
        size.setText("50");
        size.setAlignmentX(CENTER_ALIGNMENT);
        
        JLabel sizeSet = new JLabel("Set graph size:");
        sizeSet.setAlignmentX(CENTER_ALIGNMENT);
        
        JLabel sizeDefault = new JLabel("(Default is 50)");
        sizeDefault.setFont(new Font("SANS_SERIF", Font.PLAIN, 10));
        sizeDefault.setAlignmentX(CENTER_ALIGNMENT);
        
        //Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(CENTER_ALIGNMENT);

        //create a listener for the submit button
        class ButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {
                String file = fileField.getText();
                double graph = Double.parseDouble(size.getText());
                dispose(); //gets rid of the open file location gui  

                //Create a frame to show the user
                JFrame frame = new JFrame();
                final int FRAME_WIDTH = 500;
                final int FRAME_HEIGHT = 500;
                frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
                frame.setTitle("Quick Hull");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);

                coords2D coords = new coords2D(file, graph, FRAME_WIDTH, FRAME_HEIGHT);

           //    bonusPanel bonus= new bonusPanel(userCoords, FRAME_HEIGHT);

                frame.add(coords);
               // frame.add(bonus, BorderLayout.SOUTH);
                frame.setVisible(true);
            }
        }

        //add listener to button
        ActionListener listener = new ButtonListener();
        submitButton.addActionListener(listener);

        //add to panel 
        JPanel fileLoc = new JPanel();
        fileLoc.add(dir);
        fileLoc.add(fileField);
        fileLoc.add(sizeSet);
        fileLoc.add(size);
        fileLoc.add(sizeDefault);
        fileLoc.add(submitButton, BorderLayout.SOUTH);
        
        add(fileLoc); //add to the frame
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
