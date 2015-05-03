package rationalCalculator;

public class PolynomialTester1 {
    public static void main(String[] args) {

        
        //new GUI(); // start the gui
        testCase8();
    }    
        
    public static void testCase1() {
        
        Polynomial a = Input.parse("3x^2 + 2x + 1");
        Polynomial b = Input.parse("x^2 + 2x + 3");

        System.out.println("(" + a + ") + (" + b + ") = " + a.add(b));
        System.out.println("");
        
    }

    public static void testCase2() {

        Polynomial a = Input.parse("(x + 3)(x + 5)");
        System.out.println("(x + 3)(x + 5) parses to " + a);
        System.out.println("");

    }
    
    public static void testCase3(){
        
        Polynomial a = Input.parse("10x^5 + 9x^4 + 8x^3 + 7x^2 + 6x + 5");
        Polynomial b = Input.parse("5x^5 + 4x^4 + 3x^3 + 2x^2 + x + 0");
        
        System.out.println("(" + a + ") - (" + b + ") = " + a.subtract(b));
        System.out.println("");
    }

    public static void testCase4() {

        Polynomial a = Input.parse("(x+2)(x+3)");
        System.out.println(a);
        
        System.out.println(a + " at -2 is " + a.evaluate(-2));
        System.out.println(a + " at -3 is " + a.evaluate(-3));
        System.out.println(a + " at 5 is " + a.evaluate(5));
        System.out.println("");

    }
    
    public static void testCase5(){
        
        Polynomial a = Input.parse("x^3 + 1");
        Polynomial b = Input.parse("x+1");
        
        System.out.println(a);
        
        Polynomial[] div = b.divide(a);
        Polynomial quo = div[0];
        Polynomial rem = div[1];
        
        System.out.println(a + " / " + b + " = " + quo);
        System.out.println("Remainder: " + rem);
        System.out.println("");
        
    }

    public static void testCase6() {

        Polynomial a = Input.parse("(x+1)(x+1)(x+1)(x+1)(x+1)");
        System.out.println("a = " + a);

        Polynomial deriv = a.derivative();
        System.out.println("da/dx = " + deriv);

        Polynomial secDeriv = deriv.derivative();
        System.out.println("d^2a/dx^2 = " + secDeriv);
        System.out.println("");
        
    }
    
    
    public static void testCase7() {
        Polynomial a = new Polynomial("5x^2+3");
        Polynomial b = new Polynomial("3x^3+2x");
        Polynomial c = a.add(b);
        System.out.println(c);
        System.out.println(c.derivative());
        
        RationalFunction d = new RationalFunction(a, b);
        System.out.println(d);
        System.out.println(d.derivative());
    }
    
    public static void testCase8() {
        Polynomial a = new Polynomial("5x^2+5x-3");
        Polynomial b = new Polynomial("3x^3+2x");
        RationalFunction func = new RationalFunction(a, b);
        RationalFunction derivative1 = func.derivative();
        RationalFunction derivative2 = derivative1.derivative();
        //System.out.println("Derivative: " + derivative1.toString());   
        //System.out.println("Y Intercept: " +  func.evaluate(0.0));
        //System.out.println("X Intercepts: " + func.getRoots());

        System.out.println("Local minima: " + func.getLocalMins());
        System.out.println("Local maxima: " + func.getLocalMaxes());
        System.out.println("Increasing on " + derivative1.positiveIntervals());
        System.out.println("Decreasing on " + derivative1.negativeIntervals());
        System.out.println("Points of inflection: " + (derivative2.changeOfSignPoints()));
        System.out.println("Concave up on " + derivative2.positiveIntervals());
        System.out.println("Concave down on " + derivative2.negativeIntervals());

    }
    
    public static void testCase9() {
        Polynomial a = new Polynomial("5x+1");
        Polynomial b = new Polynomial("x+7");
        RationalFunction d = new RationalFunction(a, b);
        System.out.println(d.integrate(-1, 1));
    }
    
    public static void testCase10() {
        Polynomial a = new Polynomial("5x^2+5x-3");
        System.out.println(a.getRoots());
    }
}