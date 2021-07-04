import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class PacManEater extends Applet implements KeyListener, Runnable {
   int xVal = 135;
   int yVal = 135;
   int degrees = 45;
   int arc = 270;
   int score = 0;
   
   int xRand = 250;
   int yRand = 250;
   
   int d = 20;
   int increment = 0;
   
   int xBad = 80;
   int prevX = 0;
   int yBad = 80;
   int prevY = 0;
   
   Thread t = null;
   
   
   public void start() {
       t = new Thread(this);
       t.start();
   }
   
   public void init() {
       setBackground(Color.black);
       addKeyListener(this);
   }
   
   public void keyPressed(KeyEvent e) {
       int key = e.getKeyCode();
       
       if (key == KeyEvent.VK_UP) {
           yVal -= 10;
           degrees = 135;
       } else if (key == KeyEvent.VK_DOWN) {
           yVal += 10;
           degrees = 315;
       } else if (key == KeyEvent.VK_LEFT) {
           xVal -= 10;
           degrees = 225;
       } else if (key == KeyEvent.VK_RIGHT) {
           xVal += 10;
           degrees = 45;
       }
       
       repaint();
   }
   
   public void keyReleased(KeyEvent e) {}
   public void keyTyped(KeyEvent e) {}
   
   
   
   public void run() {
       while (true) {
           if (arc == 270) arc = 360;
           else if (arc == 360) arc = 270;
           repaint();
           
           try {
               t.sleep(100);
           } catch (Exception e) {}
           
       }
   }
   
   
   public void paint(Graphics g) {
       g.setColor(Color.yellow);
       g.fillArc(xVal, yVal, 30, 30, degrees, arc);
       
       
       int x = xVal + 15;
       int y = yVal + 15;
       
       if ( (x > xRand && x < xRand + 20) && (y > yRand && y < yRand + 20) ) {
           
           score ++;
           xRand = (int) (Math.random() * 300) + 100;
           yRand = (int) (Math.random() * 300) + 100;
       }
       
       if ( (x > xBad && x < xBad + d) && (y > yBad && y < yBad + d) ) {
           if (score > 0) score --;
           else score = 0;
       }
       
       
       
       g.setColor(Color.green);
       g.fillOval(xRand, yRand, 20, 20);
       
       while (true) {
           prevX = xBad;
           prevY = yBad;
           
           xBad = (int) (Math.random() * 25) + prevX - 12;
           yBad = (int) (Math.random() * 25) + prevY - 12;
           
           if ( ((xBad > 0) && (xBad + d < 500)) && ((yBad > 0) && (yBad + d < 500)) ) {
               g.setColor(Color.red);
               d = 20 + (increment / 6);
               g.fillOval(xBad, yBad, d, d);
               increment += 1;
               break;
               
           } else {
               if (xBad <= 0) xBad += (d/2);
               if (yBad <= 0) yBad += (d/2);
               if (xBad >= 500) xBad -= (d/2);
               if (yBad <= 500) yBad -= (d/2);
           }
           
       }
           
       
       g.setColor(Color.white);
       g.drawString("Score: " + Integer.toString(score), 20, 20);
       g.drawString("Green: 1 pt", 400, 20);
       g.drawString("Avoid the red!", 400, 40);
       
       
       
   }
   
}
