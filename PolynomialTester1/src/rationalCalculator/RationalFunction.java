package rationalCalculator;

public class RationalFunction {
    Polynomial numerator, denominator;
    
    public RationalFunction(Polynomial numerator, Polynomial denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    public RationalFunction derivative() {
        Polynomial top = numerator.derivative().multiply(denominator).subtract(numerator.multiply(denominator.derivative()));
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
