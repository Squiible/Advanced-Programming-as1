package UI;
 

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.*;
import javax.swing.border.TitledBorder;


public class DrawCanvas extends JComponent {

    private final Font TimeRomanBold = new Font("TimesRoman", Font.BOLD, 25 );
    //Image to draw
    private Image image;
    //Graphics2D object ==> used to draw on
    private Graphics2D g2;
    //Mouse coordinates
    private int currentX, currentY, oldX, oldY;
    public DrawCanvas() {
        setDoubleBuffered(false);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Draw A Digit from 0 to 9",
                TitledBorder.CENTER,
                TitledBorder.TOP, TimeRomanBold, Color.BLACK));
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                //save coordinates x,y when mouse is pressed
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                //coordinates x,y when drag mouse
                currentX = e.getX();
                currentY = e.getY();

                if (g2 != null) {
                    g2.setStroke(new BasicStroke(10));
                    //draw line if g2 is not null
                    g2.drawLine(oldX, oldY, currentX, currentY);
                    //refresh draw area to repaint
                    repaint();
                    //store current coordinates x,y as old x,y
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        if (image == null) {
            //if image to draw is null create one
            image = createImage(getSize().width, getSize().height);
            g2 = (Graphics2D) image.getGraphics();
            //enable antialiasing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //clear draw area
            clear();
        }

        g.drawImage(image, 0, 0, null);
    }

    public void clear() {
      //draw white on entire draw area to clear
        g2.setPaint(Color.white);
     
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.black);
        repaint();
    }
    
    //get Image created
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}