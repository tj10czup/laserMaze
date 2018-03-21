import java.awt.event.*;
import java.awt.*;
import javax.swing.*; 
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 
 * @author Tyler Czuprinski
 * @version Spring 2018
 */

public class movingLaser extends JPanel implements ActionListener{
    int fwidth, fheight;

    int x1;
    int y1;

    int x2;
    int y2;

    boolean up = false;
    boolean down = true;
    boolean left = false;
    boolean right = true;

    Timer t = new Timer(10, (ActionListener) this);

    //
    JButton button = new JButton("FIRE");
    //

    ArrayList<Line> lines = new ArrayList<>();
    ////////////////////////////////////////////////////////////////////////////   

    /**
     * Contructor for objects of class movingLaser
     */
    public movingLaser(int x1, int y1, int x2, int y2){
        setPreferredSize(new Dimension(800, 500));
        setOpaque(true);
        fwidth = getPreferredSize().width;
        fheight = getPreferredSize().height;
        setBackground( Color.WHITE );
        setFocusable(true);

        t.addActionListener(this);
        button.addActionListener(this);

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void animateLine(Graphics2D g){
        g.setColor(Color.RED);
        g.drawLine(x1, y1, x2, y2);
    }

    public void actionPerformed(ActionEvent ev) {

        if(ev.getSource() == button){
            t.start();
        }
        else if (ev.getSource() == t){
            if(x2 >= getPreferredSize().width){
                right = false;
                left = true;
                lines.add(new Line(x1, y1, x2, y2));
                y1 = y2;
                x1 = x2;

            }
            if(x2 <= 7){
                right = true;
                left = false;
                lines.add(new Line(x1, y1, x2, y2));
                y1 = y2;
                x1 = x2;
            }
            if(y2 >= getPreferredSize().height){
                up = true;
                down = false;
                lines.add(new Line(x1, y1, x2, y2));
                y1 = y2;
                x1 = x2;
            }
            if(y2 <= 10){
                up = false;
                down = true;
                lines.add(new Line(x1, y1, x2, y2));
                y1 = y2;
                x1 = x2;
            }
            if(up){
                y2 = y2 - 10;
            }
            if(down){
                y2 = y2 + 10;
            }
            if(left){
                x2 = x2 - 10;
            }
            if(right){
                x2 = x2 + 10;
            }
            try{
                Thread.sleep(10);
            } catch (Exception exc){}
        }

        repaint();
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("movingLaserPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        movingLaser panel = new movingLaser(0, 300, 0, 300);
        frame.getContentPane().add(panel);

        //
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);
        panel.button.setBounds(755, 455, 45, 45);
        panel.add(panel.button);
        panel.setVisible(true);
        //

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void paintComponent(Graphics newG){
        super.paintComponent(newG);
        Graphics2D g2d = (Graphics2D)newG;
        g2d.setStroke(new BasicStroke(3));
        for(Line lin : lines){
            g2d.setColor(Color.RED);
            g2d.drawLine(lin.startx, lin.starty, lin.endx, lin.endy);
        }
        animateLine(g2d);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
    }

    public class Line{
        int startx;
        int starty;
        int endx;
        int endy;
        public Line(int x1, int y1, int x2, int y2){
            startx = x1;
            starty = y1;
            endx = x2;
            endy = y2;
        }

    }

}

