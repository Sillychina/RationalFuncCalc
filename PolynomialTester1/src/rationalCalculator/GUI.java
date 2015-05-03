package rationalCalculator;
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
public class GUI extends JFrame{
    JFrame myPanel = new JFrame();
 
    ArrayList<JTextField> myFields;
    ArrayList<JLabel> myLabels;
    //User Input Fields
    JTextField numerField = new JTextField("",30);
    JTextField denomField = new JTextField("",30);
    JTextField xMinField = new JTextField("-10",30);
    JTextField xMaxField = new JTextField("10",30);
    JTextField yMinField = new JTextField("-10",30);
    JTextField yMaxField = new JTextField("10",30);
    JTextField xMinIntField = new JTextField("",30);
    JTextField xMaxIntField = new JTextField("",30);
    //Answer Fields
    JLabel yIntAns2;
    JLabel xIntAns2;
    JLabel derivAns2;
    JLabel integralAns2;
    JLabel minAns2;
    JLabel maxAns2;
    JLabel incrAns2;
    JLabel decrAns2;
    JLabel poiAns2;
    JLabel upAns2;
    JLabel downAns2;
    JLabel holeAns2;
    JLabel asymAns2;
      
     
    JButton submit;
    GraphObject graph;
         
    int graphY;
    int width;
    int graphHeight;
    int quarterW;
    int halfW;
    int height;
    int padding;
    int topBorder;

    
    public GUI(){
        //Set features
        width = 500;
        height = 1200;
        graphHeight = 300;
        padding = 20;
        quarterW = (width-2*padding)/4;
        halfW = (width-2*padding)/2;
        topBorder = 20;
         
        //Initialize GUI features
        graph = new GraphObject(padding,width,graphHeight);
        submit = new JButton("Submit");
             
         
         
        myLabels = new ArrayList<JLabel>();
         
        setLayout(null);    
     
        int yPos = 0;
     
        //Function section
        yPos+=padding;
        JLabel fLabel = new JLabel("Function");
        fLabel.setBounds(padding,yPos,220,20);
        myLabels.add(fLabel);
     
        yPos+=20;
        JLabel yLabel = new JLabel("y=");
        yLabel.setBounds(padding,yPos,220,20);
        myLabels.add(yLabel);
     
        yPos+=20;    
        numerField.setBounds(50, yPos-20,300,20);            
        denomField.setBounds(50, yPos,300,20);
        yPos+=20;
        submit.setBounds(380, yPos-40, 80, 40);
     
        //Intervals Section
     
        //Graph extrema
        JLabel interLabel = new JLabel("Intervals");
        interLabel.setBounds(padding,yPos,220,20);
        myLabels.add(interLabel);
        yPos+=20;
     
        int xPos = padding;
        JLabel xMinLabel = new JLabel("xMin");
        xMinLabel.setBounds(xPos, yPos,quarterW,20);
        myLabels.add(xMinLabel);
        xPos+=quarterW;
     
        JLabel xMaxLabel = new JLabel("xMax");
        xMaxLabel.setBounds(xPos, yPos,quarterW,20);
        myLabels.add(xMaxLabel);
     
        xPos+=quarterW;
     
        JLabel yMinLabel = new JLabel("yMin");
        yMinLabel.setBounds(xPos, yPos,quarterW,20);
        myLabels.add(yMinLabel);
     
        xPos+=quarterW;
        JLabel yMaxLabel = new JLabel("yMax");
        yMaxLabel.setBounds(xPos, yPos,quarterW,20);
        myLabels.add(yMaxLabel);
     
        yPos+=20;
        xPos=padding;
        xMinField.setBounds(xPos,yPos,quarterW-10,20);
        xPos+=quarterW;
        xMaxField.setBounds(xPos,yPos,quarterW-10,20);
        xPos+=quarterW;
        yMinField.setBounds(xPos,yPos,quarterW-10,20);
        xPos+=quarterW;
        yMaxField.setBounds(xPos,yPos,quarterW-10,20);
     
        //Integral Extrema
        yPos+=20;
        JLabel integrateLabel = new JLabel("Integrate");
        integrateLabel.setBounds(padding, yPos, 120, 20);
        myLabels.add(integrateLabel);
        yPos+=20;
        xPos=padding;
        JLabel fromLabel = new JLabel("From");
        fromLabel.setBounds(xPos, yPos, 120, 20);
        myLabels.add(fromLabel);
     
        xPos+=halfW;
        JLabel toLabel = new JLabel("To");
        toLabel.setBounds(xPos, yPos, 120, 20);
        myLabels.add(toLabel);
     
        yPos += 20;
        xPos = padding;
     
        xMinIntField.setBounds(xPos,yPos,halfW-10,20);
        xPos+=halfW;
        xMaxIntField.setBounds(xPos,yPos,halfW-10,20);
     
        yPos+=20;
        //Graph
        JLabel graphLabel = new JLabel("Graph");
        graphLabel.setBounds(padding,yPos,220,20);
        myLabels.add(graphLabel);
        yPos+=20;
     
        //Graph picture here
        graph.setBounds(padding,yPos,width-padding,graphHeight);
         
     
        //Important features & Answers
        yPos+=graphHeight;
        JLabel yIntAns = new JLabel("Y-intercept: ");
        yIntAns2 = new JLabel("");
        yIntAns.setBounds(padding,yPos,220,20);
        yIntAns2.setBounds(halfW,yPos,220,20);
        myLabels.add(yIntAns);
        myLabels.add(yIntAns2);
        yPos+=20;
     
        JLabel xIntAns = new JLabel("X-intercept(s): ");
        xIntAns2 = new JLabel("");
        xIntAns.setBounds(padding,yPos,220,20);
        xIntAns2.setBounds(halfW,yPos,220,20);
        myLabels.add(xIntAns);
        myLabels.add(xIntAns2);
        yPos += 20;
     
        JLabel derivAns = new JLabel("derivative: ");
        derivAns.setBounds(padding,yPos,220,20);
        myLabels.add(derivAns);
        derivAns2 = new JLabel("");
        derivAns2.setBounds(halfW,yPos,220,20);
        myLabels.add(derivAns2);
        yPos+=20;
     
        JLabel integralAns = new JLabel("integral: ");
        integralAns.setBounds(padding,yPos,220,20);
        myLabels.add(integralAns);
        integralAns2 = new JLabel("");
        integralAns2.setBounds(halfW,yPos,220,20);
        myLabels.add(integralAns2);
        yPos+=20;
     
        JLabel minAns = new JLabel("local min: ");
        minAns.setBounds(padding,yPos,220,20);
        myLabels.add(minAns);
        minAns2 = new JLabel("");
        minAns2.setBounds(halfW,yPos,220,20);
        myLabels.add(minAns2);
        yPos+=20;
     
        JLabel maxAns = new JLabel("local max: ");
        maxAns.setBounds(padding,yPos,220,20);
        myLabels.add(maxAns);
        maxAns2 = new JLabel("");
        maxAns2.setBounds(halfW,yPos,220,20);
        myLabels.add(maxAns2);
        yPos+=20;
     
        JLabel incrAns = new JLabel("increasing: ");
        incrAns.setBounds(padding,yPos,220,20);
        myLabels.add(incrAns);
        incrAns2 = new JLabel("");
        incrAns2.setBounds(halfW,yPos,220,20);
        myLabels.add(incrAns2);
        yPos+=20;
     
        JLabel decrAns = new JLabel("decreasing: ");
        decrAns.setBounds(padding,yPos,220,20);
        myLabels.add(decrAns);
        decrAns2 = new JLabel("");
        decrAns2.setBounds(halfW,yPos,220,20);
        myLabels.add(decrAns2);
        yPos+=20;
     
        JLabel poiAns = new JLabel("POI: ");
        poiAns.setBounds(padding,yPos,220,20);
        myLabels.add(poiAns);
        poiAns2 = new JLabel("");
        poiAns2.setBounds(halfW,yPos,220,20);
        myLabels.add(poiAns2);
        yPos+=20;
     
        JLabel upAns = new JLabel("concave up: ");
        upAns.setBounds(padding,yPos,220,20);
        myLabels.add(upAns);
        upAns2 = new JLabel("");
        upAns2.setBounds(halfW,yPos,220,20);
        myLabels.add(upAns2);
        yPos+=20;
     
        JLabel downAns = new JLabel("concave down: ");
        downAns.setBounds(padding,yPos,220,20);
        myLabels.add(downAns);
        downAns2 = new JLabel("");
        downAns2.setBounds(halfW,yPos,220,20);
        myLabels.add(downAns2);
        yPos+=20;
     
        JLabel holeAns = new JLabel("Holes: ");
        holeAns.setBounds(padding,yPos,220,20);
        myLabels.add(holeAns);
        holeAns2 = new JLabel("");
        holeAns2.setBounds(halfW,yPos,220,20);
        myLabels.add(holeAns2);
        yPos+=20;
     
        JLabel asymAns = new JLabel("Vertical Asymptotes: ");
        asymAns.setBounds(padding,yPos,220,20);
        myLabels.add(asymAns);
        asymAns2 = new JLabel("");
        asymAns2.setBounds(halfW,yPos,220,20);
        myLabels.add(asymAns2);
        yPos+=20;
         
         
        myFields = new ArrayList<JTextField>();
        myFields.add(numerField);
        myFields.add(denomField);
        myFields.add(xMinField);
        myFields.add(xMaxField);
        myFields.add(yMinField);
        myFields.add(yMaxField);
        myFields.add(xMinIntField);
        myFields.add(xMaxIntField);
     
        for(JLabel x:myLabels){
            add(x);
        }
        for(JTextField x:myFields){
        add(x);
        }
        add(submit);
     
        add(graph);
         
        intitializeWindow();        
    }
 
    public void intitializeWindow(){
        setTitle("RationalCalculator 2015");
        setSize(width,height);
        setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
         
        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Do the calculations with the fields
                //Get the fields 
                double gxMin = 0;
                double gxMax = 0;
                double gyMin = 0;
                double gyMax = 0;
                double ixMin = 0;
                double ixMax = 0;
                double intMin = 0;
                double intMax = 0;
                String n = "";
                String d = "";
                try{
                    gxMin = Double.parseDouble(xMinField.getText());
                    gxMax = Double.parseDouble(xMaxField.getText());
                    gyMin = Double.parseDouble(yMinField.getText());
                    gyMax = Double.parseDouble(yMaxField.getText());
                    ixMin = Double.parseDouble(xMinIntField.getText());
                    ixMax = Double.parseDouble(xMaxIntField.getText());
                    intMin = Double.parseDouble(xMinIntField.getText());
                    intMax = Double.parseDouble(xMaxIntField.getText());
                    n = numerField.getText();
                    d = denomField.getText();
                }catch(Exception ex){
                    System.out.println("Please re-enter your function");
                    return;
                }
                 
                RationalFunction func;
                Polynomial num = new Polynomial(n); 
                Polynomial den;
                 
                if (d.equals("")){
                    den = new Polynomial("1"); // Default to 1 on the denominator if none entered                 
                }
                else{
                    den = new Polynomial(d);
                }  
                func = new RationalFunction(num, den);
                graph.fn = func;
                 
                RationalFunction derivative1 = func.derivative();
                RationalFunction derivative2 = derivative1.derivative();
                 
                //Graph method
                graph.xMin = gxMin;
                graph.xMax = gxMax;
                graph.yMin = gyMin;
                graph.yMax = gyMax;
                graph.repaint();
                 
                derivAns2.setText(derivative1.toString());   
                yIntAns2.setText(String.valueOf(func.evaluate(0.0)));
                xIntAns2.setText(func.showRoots());

                minAns2.setText(func.getLocalMins().toString());
                maxAns2.setText(func.getLocalMaxes().toString());
                incrAns2.setText(derivative1.positiveIntervals());
                decrAns2.setText(derivative1.negativeIntervals());
                poiAns2.setText(derivative2.changeOfSignPoints().toString().equals("[]") ? "none" : derivative2.changeOfSignPoints().toString());
                upAns2.setText(derivative2.positiveIntervals());
                downAns2.setText(derivative2.negativeIntervals());
                integralAns2.setText("" + func.integrate(intMin,intMax)); 
                 
                String h = "";
                String a = "";
                 
                for(double hole:func.getHoles()){
                    h+=hole+", ";
                }
                for(double asym:func.getAsymptotes()){
                    a+=asym+", ";
                }
                 
                try{
                    holeAns2.setText(h.substring(0,h.length()-2));
                }catch(Exception ex){
                    holeAns2.setText("None");
                }
                try{
                    asymAns2.setText(h.substring(0,h.length()-2));
                }catch(Exception ex){
                    asymAns2.setText("None");
                }
                 
                 
            }
            }
        );
         
                 
    }
     
     
 
}