import sun.org.mozilla.javascript.internal.ast.NewExpression;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.lang.Math;

/**
 * Created by IntelliJ IDEA.
 * User: Haw
 * Date: 07.04.12
 * Time: 13:30
 * To change this template use File | Settings | File Templates.
 */
public class PlotEllipsis {
    public static void main(String args[]) {
        final int N = 600;
        JFrame f = new JFrame("Ellipsis");
        f.setSize(N,N);
        f.setLocation(N,N);
        f.setResizable(false);
        JPanel plot = new JPanel(){
            ArrayList<Point> points = new ArrayList<Point>();
            int xC = 300;
            int yC = 300;
            int a = 50;
            int b = 200;
            int i = 0;
//            Point center;
            Point pressed;
            Point released;
            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        pressed = new Point(e.getPoint());
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        released = new Point(e.getPoint());
                        Point center = new Point(pressed.x + (released.x - pressed.x)/2,pressed.y + (released.y - pressed.y)/2);
                        int a = Math.abs(center.x - pressed.x);
                        int b = Math.abs(center.y - pressed.y);
                        System.out.println("Center = (" + center.x + " ; " + center.y + ") ; a = " + a + "; b = " + b + ";");
                        GenerateEllipsis(center.x,center.y,a,b);
                        //GenerateEllipsis(399,149,68,24);
                        repaint();
                    }
                });
            }
            void GenerateEllipsis(int xC, int yC, int a, int b) {
                 int a2 = a*a;
                 int b2 = b*b;
                 int ds = 4 * a2;
                 int dt = 4 * b2;
                 int dxt = (int)Math.round(a2 / Math.sqrt(a2+b2));
                 int xInc = 1;
                 int yInc = -1;
                 PlotArcInOctant(xInc,yInc,xC,yC,a,b,a2,b2,dt,ds,dxt);
                 yInc = 1;
                 PlotArcInOctant(xInc,yInc,xC,yC,a,b,a2,b2,dt,ds,dxt);
                 xInc = -1;
                 PlotArcInOctant(xInc,yInc,xC,yC,a,b,a2,b2,dt,ds,dxt);
                 yInc = -1;
                 PlotArcInOctant(xInc,yInc,xC,yC,a,b,a2,b2,dt,ds,dxt);
                 repaint();
            }
            void PlotArcInOctant(int xInc, int yInc,int xC, int yC,int a, int b, int a2, int b2, int dt,int ds,int dxt) {
                 int t = 0;
                 int s = -4 * a2 * b;
                 int e = -s/2 - 2*b2 - a2;
                 int ca = -6 * b2;
                 int cd = ca - 4 * a2; 
                 
                 int x = xC;
                 int y = yC - yInc*b;
                 points.add(new Point(x,y));
                 for(int i = 1; i< dxt; i++) {
                     x = x+xInc;
                     if (e>=0) {
                         e = e+t+ca;
                         t = t-dt;
                     } else {
                         y = y + yInc;
                         e = e+t-s+cd;
                         t = t - dt;
                         s = s + ds;
                     }
                     points.add(new Point(x,y));
                 }
                 int dyt = Math.abs(y - yC);
                 e = e - t/2 - s/2 - b2 - a2;
                 ca = -6*a2;
                 cd = ca - 4 * b2;
                 for(int i = 1; i< dyt; i++) {
                     y = y + yInc;
                     if(e<=0) {
                         e = e - s + ca;
                         s = s + ds;
                     } else {
                         e = e - s + t + cd;
                         s = s + ds;
                         t = t - dt;
                         x = x + xInc;
                     }
                     points.add(new Point(x, y));
                 }
            }
            public void paint(Graphics g) {
                 super.paint(g);
                 g.setColor(Color.RED);
                 for(Point item : points){
                     g.drawLine((int)item.x,(int)item.y,(int)item.x,(int)item.y);
                 }
                //points.clear();
            }
        };
        f.add(plot);
        f.setVisible(true);
    }
}
