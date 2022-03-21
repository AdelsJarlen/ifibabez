/*
 * A swing BoxLayout example with different BoxLayout alignment settings
 * LEFT_ALIGNMENT, CENTER_ALIGNMENT, RIGHT_ALIGNMENT respectively.
 */
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
public class LegesystemGUI extends Legesystem implements ActionListener {

    static JButton jb1;
    static JButton jb2;
    static JButton jb3;
    static JButton jb4;

    static JMenuBar mb;
    static JMenu m1;
    static JMenu m2;
    static JMenuItem m1_1;
    static JMenuItem m1_2;

    public LegesystemGUI() {
        super();
    } 
     
    public static void main(String[] args) {

        System.setProperty("apple.laf.useScreenMenuBar", "true");

        // Create and set up a frame window
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("IN1010 – Legesystem GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(250,200));
        
        // Define new buttons with different width on help of the ---
        jb1 = new JButton("Vis eksisterende elementer");
        jb1.addActionListener(new LegesystemGUI());      
        jb2 = new JButton("Legg til nye elementer");
        jb2.addActionListener(new LegesystemGUI());      
        jb3 = new JButton("Bruk en resept");
        jb3.addActionListener(new LegesystemGUI());      
        jb4 = new JButton("Skriv ut statistikk");
        jb4.addActionListener(new LegesystemGUI());      
            
        // Define the panel to hold the buttons 
        JPanel panel1 = new JPanel(new GridLayout(4,4));
        // JPanel panel2 = new JPanel();
        // JPanel panel3 = new JPanel();
        // JPanel panel4 = new JPanel();

        //Creating the MenuBar and adding components
        mb = new JMenuBar();
        m1 = new JMenu("Fil");
        m2 = new JMenu("Hjelp");
        mb.add(m1);
        mb.add(m2);
        m1_1 = new JMenuItem("Åpne fil...");
        m1_2 = new JMenuItem("Lagre som...");
        m1.add(m1_1);
        m1.add(m1_2);
        
        // Set up the title for different panels
        panel1.setBorder(BorderFactory.createTitledBorder("Hva vil du gjøre?"));
        panel1.setSize(new Dimension(250,250));
                
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
        frame.setJMenuBar(mb);
        
        // Set the window to be visible as the default to be false
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == jb1) {
            System.out.println("Yoooo");
        }
        
    }
}