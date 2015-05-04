package rationalCalculator;

import java.util.ArrayList;
import java.util.Collections;

public class RationalFunction {
    Polynomial numerator, denominator;
    ArrayList<Double> roots, asymptotes, holes, criticalPoints;
    
    public RationalFunction(Polynomial numerator, Polynomial denominator) {
        // Set fields
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    public RationalFunction(String numerator, Polynomial denominator) {
        this(Input.parse(numerator), denominator);
    }
    
    public RationalFunction(Polynomial numerator, String denominator) {
        this(numerator, Input.parse(denominator));
    }
    
    public RationalFunction(String numerator, String denominator) {
        this(Input.parse(numerator), Input.parse(denominator));
    }
    
    public RationalFunction derivative() {
        Polynomial fprimeg = numerator.derivative().multiply(denominator);
        Polynomial fgprime = numerator.multiply(denominator.derivative());
        Polynomial top = new Polynomial(fprimeg.subtract(fgprime).toString());
        Polynomial bottom = new Polynomial(denominator.multiply(denominator).toString());
        return new RationalFunction(top, bottom);
    }
    
    // Methods involving a rational function and a polynomial
    public RationalFunction add(Polynomial p) {
        return new RationalFunction(numerator.add(p.multiply(denominator)), denominator);
    }
    
    public RationalFunction subtract(Polynomial p) {
        return new RationalFunction(numerator.subtract(p.multiply(denominator)), denominator);
    }
    
    public RationalFunction multiply(Polynomial p) {
        return new RationalFunction(numerator.multiply(p), denominator.multiply(p));
    }
    
    // Methods involving two rational functions
    
    public RationalFunction add(RationalFunction r) { // TODO: fix this so it doesn't generate holes
        return new RationalFunction(numerator.multiply(r.denominator).add(r.numerator.multiply(denominator)), denominator.multiply(r.denominator));
    }
    
    public RationalFunction subtract(RationalFunction r) { // TODO: fix this so it doesn't generate holes
        return new RationalFunction(numerator.multiply(r.denominator).subtract(r.numerator.multiply(denominator)), denominator.multiply(r.denominator));
    }
    
    public RationalFunction multiply(RationalFunction r) {
        return new RationalFunction(numerator.multiply(r.numerator), denominator.multiply(r.denominator));
    }
    
    public double evaluate(double x) {
        try {
            return numerator.evaluate(x) / denominator.evaluate(x);
        } catch (java.lang.ArithmeticException e) {
            return Double.NaN; // Asymptote or hole
        }
    }
    
    public double integrate(double xmin, double xmax) { // trapezoid method
        // Check that they aren't integrating across a vertical asymptote
        for (Double asymptote : asymptotes) {
            if (asymptote <= xmax && asymptote >= xmin) {
                System.out.println("Can't integrate across an asymptote");
                return Double.NaN;
            }
        }
        double total = 0;
        double step = (xmax - xmin) / 10000;
        for (double x = xmin + step; x < xmax - step; x+= step) {
            total += evaluate(x);
        }
        total += evaluate(xmin) / 2;
        total += evaluate(xmax) / 2;
        total = total * step;
        return total;
    }
    
    public String showRoots() {
        String ans = "";
        ArrayList<Double> roots = getRoots();
        for (int i = 0; i < roots.size(); i++) {
            ans += roots.get(i);
            if (!(i + 1 == roots.size())) ans += ", ";
        }
        return ans;
    }
    
    public String positiveIntervals() {
        String ans = "";
        // Create an arraylist of all the points where the function could cross the x axis
        ArrayList<Double> points = getCriticalPoints();
        
        // Special case: horizontal line
        if (points.size() == 0) {
            return evaluate(0) > 0 ? "(-infinity, infinity)" : "None";
        }
                
        // Initial test
        if (evaluate(points.get(0) - 1) > 0) ans += "(-infinity, " + points.get(0) + ")";
        
        for (int i = 1; i < points.size() - 1; i++) { // Check between each pair of points
            double x = (points.get(i) + points.get(i + 1)) / 2;
            if (evaluate(x) > 0) {
                if (!ans.equals("")) ans += " U ";
                ans += "(" + points.get(i) + ", " + points.get(i + 1) + ")";
            }
        }
        
        
        // Final test
        if (evaluate(points.get(points.size() - 1) + 1) > 0) {
            if (!ans.equals("")) ans += " U ";
            ans += "(" + points.get(points.size() - 1) + ", +infinity)";
        }
        
        return ans;
    }
    
    public String negativeIntervals() {
        String ans = "";
        // Create an arraylist of all the points where the function could cross the x axis
        ArrayList<Double> points = getCriticalPoints();
        
        // Special case: horizontal line
        if (points.size() == 0) {
            return evaluate(0) < 0 ? "(-infinity, infinity)" : "None";
        }
        
        // Initial test
        if (evaluate(points.get(0) - 1) < 0) ans += "(-infinity, " + points.get(0) + ")";
        
        for (int i = 1; i < points.size() - 1; i++) { // Check between each pair of points
            double x = (points.get(i) + points.get(i + 1)) / 2;
            if (evaluate(x) < 0) {
                if (!ans.equals("")) ans += " U ";
                ans += "(" + points.get(i) + ", " + points.get(i + 1) + ")";
            }
        }
        
        // Final test
        if (evaluate(points.get(points.size() - 1) + 1) < 0) {
            if (!ans.equals("")) ans += " U ";
            ans += "(" + points.get(points.size() - 1) + ", +infinity)";
        }
        
        return ans;
    }
    
    public void findPoints() {
        // Initialize arraylists
        roots = new ArrayList<>();
        asymptotes = new ArrayList<>();
        holes = new ArrayList<>();
        criticalPoints = new ArrayList<>();
        
        // get the roots of the numerator and denominator
        ArrayList<Double> numRoots = this.numerator.getRoots();
        ArrayList<Double> denRoots = this.denominator.getRoots();
        
        for (Double root : numRoots) {
            // add to critical points
            if (!criticalPoints.contains(root)) criticalPoints.add(root);
            
            // add to either holes or roots
            if (denRoots.contains(root)) {
                if (!holes.contains(root)) holes.add(root);
            } else {
                roots.add(root);
            }
        }
        
        for (Double root : denRoots) {
            // add to critical points
            if (!criticalPoints.contains(root)) criticalPoints.add(root);
            
            // add to asymptotes if not a hole (holes already accounted for)
            if (!numRoots.contains(root)) {
                if (!asymptotes.contains(root)) asymptotes.add(root);
            }
        }
        Collections.sort(roots);
        Collections.sort(holes);
        Collections.sort(asymptotes);
        Collections.sort(criticalPoints);
    }
    
    public ArrayList<Double> getRoots() {
        if (roots == null) findPoints();
        return this.roots;
    }
    
    public ArrayList<Double> getAsymptotes() {
        if (asymptotes == null) findPoints();
        return this.asymptotes;
    }
    
    public ArrayList<Double> getHoles() {
        if (holes == null) findPoints();
        return this.holes;
    }
    
    public ArrayList<Double> getCriticalPoints() { // points where the function may change from positive to negative or vice versa
        if (criticalPoints == null) findPoints();
        return criticalPoints;
    }
    
    public ArrayList<Double> changeOfSignPoints() {
        ArrayList<Double> ans = new ArrayList<>();
        // Create an arraylist of all the points where the function could cross the x axis
        ArrayList<Double> points = getCriticalPoints();
        
        // Special case: horizontal
        if (points.isEmpty()) return ans;
        
        points.add(points.get(points.size() - 1) + 1); // for testing the last point
        
        double current, last;
        current = evaluate(points.get(0) - 1);
        
        for (int i = 0; i < points.size() - 1; i++) {
            last = current;
            current = evaluate((points.get(i) + points.get(i + 1)) / (double) 2);
            if ((current > 0 && last < 0) || (current < 0 && last > 0)) { // if there was a change of sign, add it to the list
                ans.add(points.get(i));
            }
            
        }
        return ans;
    }
    
    public ArrayList<Double> getLocalMaxes() {
        ArrayList<Double> ans = new ArrayList<>();
        RationalFunction derivative = derivative();
        RationalFunction derivative2 = derivative.derivative();
        ArrayList<Double> derivRoots = derivative.getRoots();
        for (double point : derivRoots) {
            if (derivative2.evaluate(point) < 0) ans.add(point); // At each turning point, if the function is concave down it's a local max
        }
        
        return ans;
    }
    
    public ArrayList<Double> getLocalMins() {
        ArrayList<Double> ans = new ArrayList<>();
        RationalFunction derivative = derivative();
        RationalFunction derivative2 = derivative.derivative();
        ArrayList<Double> derivRoots = derivative.getRoots();
        
        for (double point : derivRoots) {
            if (derivative2.evaluate(point) > 0) ans.add(point); // At each turning point, if the function is concave up it's a local min
        }
        
        return ans;
    }
    
    @Override
    public String toString() {
        return "(" + numerator.toString() + ")/(" + denominator.toString() + ")";
    }
}
