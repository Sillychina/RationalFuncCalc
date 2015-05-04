/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rationalCalculator;
 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
 
/**
 *
 * @author Arthur
 */
public class GraphObject extends JPanel{
    //Paintcomponent
    public Polynomial num;// = new Polynomial("5x^2-5");
    public Polynomial denom;// = new Polynomial("1");
     
    public RationalFunction fn;// = new RationalFunction(num,denom);
    public int padding;
    public int width;
    public int realWidth;
    public int height;
     
    public double xMin = 0;
    public double yMin = 0;
    public double xMax = 0;
    public double yMax = 0;
     
    private double yMinT; // temporailty swap yMin and yMax so that it is easier to graph  using java's unintuitive yVals 
    private double yMaxT;
     
    private double pixelWidth; // width of one pixel is equal to delta pixelWidth on a graph
    private double pixelHeight; // height of one pixel is equal to delta pixelHeigt on a graph
     
     
     
    public GraphObject(int p,int w, int h){
        this.padding = p;
        this.width = w;
        this.height = h-2;
        this.realWidth = w-2*p;
     
    }
     
    public Point getRealPixel (double x,double y){
        int xPos = (int)((x-xMin)/pixelWidth);
        int yPos = (int)((y-yMinT)/pixelHeight);
        return new Point(xPos,yPos);
    }
     
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, realWidth, height);
        yMinT = yMax;
        yMaxT = yMin;
         
        if (fn == null || xMin == xMax || yMin == yMax){
            g.setColor(Color.red);
             
        }else{
            g.setColor(Color.black);
        }
        g.drawRect(0,0,realWidth,height);
        //System.out.println("each hori pixel on the graph is equal to " + pixelWidth);
        //System.out.println("each verti pixel on the graph is equal to " + pixelHeight);
         
        //Draw lines to denote axis
        //Vertical y axis
        g.drawLine(realWidth/2, 0, realWidth/2, height);
         
        //hori x axis
        g.drawLine(0, height/2, realWidth, height/2);
         
        if (fn == null){
            return;
        }
        
        // make sure alpoints are there:
        fn.findPoints();
         
        pixelWidth = (double)(xMax - xMin )/ (double)(realWidth);
        pixelHeight = (double)(yMaxT - yMinT )/ (double)(height);
         
         
        //parallel lines to indicate x tick marks
        for(double xTickVal = xMin;xTickVal<=xMax;xTickVal+=(xMax-xMin)/10){
            if (xTickVal == 0) 
                continue;
            int xTickPos = (int)getRealPixel(xTickVal,0).x;
            g.drawLine(xTickPos,height/2-5,xTickPos,height/2+5);
            g.drawString(String.valueOf(xTickVal),xTickPos-10,height/2-10);
            //System.out.println(xTickPos);
        }
         
        //Parallel tick marks for yvalues
        int heigthTickValue = height/10;
        for(double yTickVal = yMin;yTickVal<=yMax;yTickVal+=(xMax-xMin)/10){
            if (yTickVal == 0) 
                continue;
            int yTickPos = (int)getRealPixel(0,yTickVal).y;
            g.drawLine(realWidth/2-5,yTickPos,realWidth/2+5,yTickPos);
            g.drawString(String.valueOf(yTickVal),realWidth/2+10,yTickPos+5);
            //System.out.println(yTickPos);
        }
         
        //Graph the function itself
        double[] xVals = new double[realWidth+5];
        double[] yVals = new double[realWidth+5];
        Point[] pointPos = new Point[realWidth+5];
 
        //Draw holes
        for (double hole:fn.holes){
             
            //Create temporary function to evaluate y-pos of hole
            Polynomial div = new Polynomial("x - " + (int)hole);
            System.out.println(" heyy");
            for (double coeff:div.arguments){
                System.out.println(coeff);
            }
            Polynomial numerAns = fn.numerator.divide(div)[0];
            RationalFunction temp = new RationalFunction(numerAns,fn.denominator.divide(denom)[0]);
            double yValHole = temp.evaluate(hole);
             
            System.out.println(yValHole);
            Point holePoint = getRealPixel(hole,yValHole);
            g.drawOval(holePoint.x-15,holePoint.y-15,30,30);
             
        }
         
         
        //Draw HA
        for (double ha:fn.asymptotes){
            int xPosVA = (int) getRealPixel(ha,0).x;
            g.drawLine(xPosVA,0,xPosVA,height);
        }
         
        //Get the positions of each pixel
        double deltaX = (double)(xMax-xMin)/realWidth;
        int counter = 0;
        for (double xVal = xMin;xVal<=xMax;xVal+=deltaX,counter++){
            //System.out.println(counter + " " + xVal);    
            xVals[counter] = xVal;
            yVals[counter] = fn.evaluate(xVal);
            pointPos[counter] = getRealPixel(xVals[counter],yVals[counter]);
             
            boolean passesVA = false;
            for(double VA : fn.asymptotes){
                if (yVals[counter-1] < VA && yVals[counter] > VA || yVals[counter] < VA && yVals[counter-1]>VA)
                    passesVA = true;
            }
             
            if (counter == 0 || passesVA)
                continue;
             
            g.drawLine((int)pointPos[counter-1].x, (int)pointPos[counter-1].y, (int)pointPos[counter].x, (int)pointPos[counter].y);
             
        }
         
         
         
         
         
    }
     
    public Dimension getPreferredSize(){
     
     
    return new Dimension(300,300);
    }
     
             
}