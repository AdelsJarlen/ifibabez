/*
 * A swing BoxLayout example with different BoxLayout alignment settings
 * LEFT_ALIGNMENT, CENTER_ALIGNMENT, RIGHT_ALIGNMENT respectively.
 */
 
import javax.swing.*;
import java.awt.*;
 
public class LegesystemGUI {
 
    public static void main(String[] args) {
        // Create and set up a frame window
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("IN1010 – Legesystem GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(250,200));
         
        // Define new buttons with different width on help of the ---
        JButton jb1 = new JButton("Vis eksisterende elementer");        
        JButton jb2 = new JButton("Legg til nye elementer");
        JButton jb3 = new JButton("Bruk en resept");
        JButton jb4 = new JButton("Skriv ut statistikk");
         
        // Define the panel to hold the buttons 
        JPanel panel1 = new JPanel(new GridLayout(4,4));
        // JPanel panel2 = new JPanel();
        // JPanel panel3 = new JPanel();
        // JPanel panel4 = new JPanel();
         
        // Set up the title for different panels
        panel1.setBorder(BorderFactory.createTitledBorder("Hva vil du gjøre?"));
        panel1.setSize(new Dimension(500,250));
         
        // Set up the BoxLayout
         
        // Add the buttons into the panel with three different alignment options
        jb1.setAlignmentX(Component.CENTER_ALIGNMENT);
        jb2.setAlignmentX(Component.CENTER_ALIGNMENT);
        jb3.setAlignmentX(Component.CENTER_ALIGNMENT);
        jb4.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel1.add(jb1);
        panel1.add(jb2);
        panel1.add(jb3);
        panel1.add(jb4);
         
        // Add the three panels into the frame
        frame.setLayout(new FlowLayout());
        frame.add(panel1);
         
        // Set the window to be visible as the default to be false
        frame.pack();
        frame.setVisible(true);
         
    }
 
}