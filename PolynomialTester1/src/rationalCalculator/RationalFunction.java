package rationalCalculator;

import java.util.ArrayList;

public class RationalFunction {
    Polynomial numerator, denominator;
    ArrayList<Double> roots, asymptotes, holes;
    
    public RationalFunction(Polynomial numerator, Polynomial denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        numerator.getRoots();
        denominator.getRoots();
        for (int i = 0; i < numerator.roots.size(); i++) {
            double root = numerator.roots.get(i);
            if (denominator.roots.contains(root)) {
                if (!holes.contains(root)) holes.add(root);
            } else {
                roots.add(root);
            }
        }
        
        for (int i = 0; i < denominator.roots.size(); i++) {
            double root = numerator.roots.get(i);
            if (!numerator.roots.contains(root)) {
                if (!asymptotes.contains(root)) asymptotes.add(root);
            }
        }
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
        Polynomial top = fprimeg.subtract(fgprime);
        Polynomial bottom = denominator.multiply(denominator);
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
    
    @Override
    public String toString() {
        return "(" + numerator.toString() + ")/(" + denominator.toString() + ")";
    }
}
