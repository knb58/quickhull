/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickhull;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author dell
 */
public class bonusPanel {
    public bonusPanel(String userCoords, int FRAME_HEIGHT){
     //Create a text field to show coordinates & scroll bar to show all of them
                JTextField coordinates = new JTextField();
                coordinates.setBorder(new LineBorder(new Color(0, 0, 0, 0)));
                coordinates.setText(userCoords);
                coordinates.setEditable(false);
                JScrollPane co = new JScrollPane(coordinates);
                co.setPreferredSize(new Dimension(FRAME_HEIGHT, 50));

                JButton showCoords = new JButton("Show Coordinates");
                JLabel showStep = new JLabel("Show Step: ");
                JTextField stepNo = new JTextField(5);

                //add to panel
                JPanel show = new JPanel();
                show.add(showCoords);
                show.add(showStep);
                show.add(stepNo);
    }
}
